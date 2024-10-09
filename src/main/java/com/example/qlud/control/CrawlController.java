package com.example.qlud.control;

import com.example.qlud.model.*;
import com.example.qlud.repo.ProductRepo;
import com.example.qlud.requets.CrawlRequest;
import com.example.qlud.response.CrawlResponse;
import com.example.qlud.response.MessageResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/app")
public class CrawlController {

    @Autowired
    ProductRepo repo;

    @PostMapping("/crawl")
    public ResponseEntity<?> crawlData(@RequestBody CrawlRequest request) throws IOException {
//        File htmlFile = new File("E:\\data.html");
//        Document doc = Jsoup.parse(htmlFile, "UTF-8");
        String decodedData = URLDecoder.decode(request.getCookieData(), "UTF-8");
        String rawHtmlData = getHtmlWeb(request.getCrawlUrl(), decodedData);
        if(rawHtmlData != null){
            Document doc = Jsoup.parse(rawHtmlData);
            String title = doc.title();
            Elements elements = doc.select("script");
            String OtherProduct = "\"skuModel\":\\{.*?\\}\\}";
            String data = "";
            String price = "";
            String detailUrl = "";
            String otherProduct = "";
            String otherProduct2 = "";
            String electrixOtherProduct = "";
            String ListImage = "";
            String videoJson = "";
            for (Element element: elements) {
                String scriptContent = element.html();
                if (scriptContent.contains("window.__GLOBAL_DATA") || scriptContent.contains("window.__INIT_DATA")) {
                    String dataSection = extractDataSection(scriptContent, "\"data\":[{", "}]}");
                    String propsList = extractDataSection(scriptContent, "\"propsList\":[{", "}]}");
                    data += dataSection + propsList;
                    price += extractDataSection(scriptContent, "\"currentPrices\":[{", "}]}");
                    ListImage += extractDataSection(scriptContent, "\"offerImgList\":[", "]");
                    videoJson += extractDataSection(scriptContent,"\"video\":{","}");
                    Pattern pattern = Pattern.compile(OtherProduct,Pattern.DOTALL);
                    Matcher matcher = pattern.matcher(scriptContent);
                    while (matcher.find()) {
                        otherProduct += matcher.group();

                    }
                    otherProduct2 += extractDataSection(scriptContent,"\"distributeSkuModel\":{","}}");
                    electrixOtherProduct += extractDataSection(scriptContent,"\"mixParam\":{", "}]}");
                    detailUrl += extractDataSection(scriptContent,"\"detailUrl\":","}}");
                }
            }
            if(price.isEmpty() && data.isEmpty() && otherProduct.isEmpty() && ListImage.isEmpty() && videoJson.isEmpty() && otherProduct2.isEmpty() && electrixOtherProduct.isEmpty() && detailUrl.isEmpty()){
                if(rawHtmlData.contains("captcha")){
                    return new ResponseEntity<>(new MessageResponse("Sorry bot got captcha see you late"), HttpStatus.BAD_REQUEST);
                }else if(rawHtmlData.contains("login")){
                    return new ResponseEntity<>(new MessageResponse("Cookie is expired change cookie please"), HttpStatus.BAD_REQUEST);
                }else{
                    return new ResponseEntity<>(new MessageResponse("No data found"), HttpStatus.BAD_REQUEST);
                }
            }else{
                CrawlResponse result = new CrawlResponse();
                if(!price.isEmpty()){
                    result.setProductPrices(convertStringPriceToObject(price));
                }
                if(!otherProduct.isEmpty() || !otherProduct2.isEmpty() || !electrixOtherProduct.isEmpty()){
                    if(otherProduct.contains("skuProps")){
                        result.setProductColorAndSize(convertStringOtherProductToObject(otherProduct));
                    }else if(otherProduct2.contains("skuProps")){
                        result.setProductColorAndSize(convertStringOtherProductToObject2(otherProduct2));
                    }else{
                        result.setProductColorAndSize(getElectricOtherProduct(electrixOtherProduct));
                    }
                }
                if(!data.isEmpty()){
                    result.setProductDetail(convertProductInfoFromProductInfoString(data));
                }
                if(!ListImage.isEmpty()){
                    result.setImageProductList(convertImageListFromImageListJson(ListImage));
                }
                if(!videoJson.isEmpty()){
                    result.setVideoUrl(getVideoUrlFromVideoJson(videoJson));
                }

                if(!detailUrl.isEmpty()){
                    result.setProductImageDetail(getDetail(detailUrl));
                }

                result.setOfferId(getOfferIdFromUrl(request.getCrawlUrl()));
                result.setProductTitle(title);
                result.setLink(request.getCrawlUrl());
//                System.out.println(result);
                Optional<Product> productOpt = repo.findById(result.getOfferId());
                if(productOpt.isPresent()){
                    Product p = productOpt.get();
                    repo.save(TransferDataFromResponseToObject(p, result));
                }else{
                    Product p = new Product();
                    p.setOfferId(getOfferIdFromUrl(request.getCrawlUrl()));
                    Product newProduct = TransferDataFromResponseToObject(p,result);
                    repo.save(newProduct);
                }
                return ResponseEntity.ok(result);
            }
        }else{
            return new ResponseEntity<>(new MessageResponse("No raw Data get"), HttpStatus.BAD_REQUEST);
        }
    }

    public List<String> getDetail(String json){
        json = "{" + json;
        JSONObject jsonObject = new JSONObject(json);
        String urlString = jsonObject.getString("detailUrl");
        List<String> productImgList = new ArrayList<>();
        try{
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
//            System.out.println("Response Code: " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                String dataText = String.valueOf(response);
                int startIndex = dataText.indexOf("var") + 4;
                String contentString = dataText.substring(startIndex, dataText.length() - 1);
                String cleanedInput = contentString.replace("\\\"", "\"");
                String htmlContent = cleanedInput.substring(contentString.indexOf("content\":\"") + 10, cleanedInput.lastIndexOf("\""));
                Document doc = Jsoup.parse(htmlContent);
                Elements imgs = doc.select("img");
                for (Element img : imgs) {
//                    System.out.println(img.attr("src"));
                    productImgList.add(img.attr("src"));
                }
            } else {
//                System.out.println("GET request failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productImgList;
    }

    private String getHtmlWeb(String url,String cookie){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", cookie);
        httpHeaders.add("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/129.0.0.0 Safari/537.36");
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String > res = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
        );
        if(res.getStatusCode() == HttpStatus.OK){
            String html = res.getBody();
            return html;
        }else{
            return null;
        }
    }

    private ProductColorAndSize getElectricOtherProduct(String electricJsonString){
        String json = "{" + electricJsonString.trim() + "}";
        System.out.println(json);
        ProductColorAndSize productColorAndSize = new ProductColorAndSize();
        List<ProductInfoMap> infoMapList = new ArrayList<>();
        ProductInfoMap infoMap = new ProductInfoMap();
        JSONObject jsonObject = new JSONObject(json);
        int canBookCount = jsonObject.getInt("canBookedAmount");
        if(jsonObject.has("name")){
            infoMap.setName(jsonObject.getString("name"));
        }else{
            infoMap.setName("采购量");
        }
        if(jsonObject.has("saleCount")){
            infoMap.setSaleCount(jsonObject.getInt("saleCount"));
        }else{
            infoMap.setSaleCount(0);
        }
        infoMap.setCanBookCount(canBookCount);
        infoMapList.add(infoMap);
        productColorAndSize.setProductInfoMap(infoMapList);
        return productColorAndSize;
    }

    public static String extractDataSection(String content, String start, String end) {
        int startIndex = content.indexOf(start);
        if (startIndex != -1) {
            int endIndex = content.indexOf(end, startIndex);
            if (endIndex != -1) {
                return content.substring(startIndex, endIndex + end.length());
            }
        }
        return "";
    }

    private Product TransferDataFromResponseToObject(Product product, CrawlResponse res){
        product.setProductTitle(res.getProductTitle());
        product.setLink(res.getLink());
        product.setProductDetail(res.getProductDetail());
        product.setProductColorAndSize(res.getProductColorAndSize());
        product.setProductPrices(res.getProductPrices());
        product.setImageProductList(res.getImageProductList());
        product.setVideoUrl(res.getVideoUrl());
        product.setProductImageDetail(res.getProductImageDetail());
        return product;
    }

    private ProductPrice convertStringPriceToObject(String priceString){
        String jsonString = "{" + priceString + "}";
        ProductPrice productPrice = new ProductPrice();
        List<Price> priceList = new ArrayList<>();
        List<Price> originalPriceList = new ArrayList<>();
        productPrice.setName("价格");
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray currentPrices = jsonObject.getJSONArray("currentPrices");
        for (int i = 0; i < currentPrices.length(); i++) {
            JSONObject priceInfo = currentPrices.getJSONObject(i);
            String price = priceInfo.getString("price");
            String beginAmount = priceInfo.getInt("beginAmount") + "";
            Price priceObject = new Price(price,beginAmount);
            priceList.add(priceObject);
        }

        if(jsonObject.has("originalPrices")){
            JSONArray originalPrices = jsonObject.getJSONArray("originalPrices");
            for(int i =0 ; i< originalPrices.length();i++){
                JSONObject originalPriceInfo = currentPrices.getJSONObject(i);
                String price = originalPriceInfo.getString("price");
                String beginAmount = originalPriceInfo.getInt("beginAmount") + "";
                Price priceObject = new Price(price,beginAmount);
                originalPriceList.add(priceObject);
            }
        }
        productPrice.setCurrentPrice(priceList);
        productPrice.setOriginalPrice(originalPriceList);
        return productPrice;
    }

    private ProductColorAndSize convertStringOtherProductToObject(String otherProduct){
        String other = otherProduct.trim();
        if (other.endsWith(",")) other = other.substring(0, other.length() - 1);
        String jsonString = "{" + other + "}}" ;
        ProductColorAndSize productColorAndSize = new ProductColorAndSize();
        ProductColor productColor = new ProductColor();
        ProductSize productSize = new ProductSize();
        JSONObject otherObject = new JSONObject(jsonString);
        JSONObject modelObject = otherObject.getJSONObject("skuModel");
        JSONArray skuPropsArray = modelObject.getJSONArray("skuProps");
       if(skuPropsArray.length() > 1){
           for (int i = 0; i< skuPropsArray.length();i++){
               JSONObject colorAndSizeObject = skuPropsArray.getJSONObject(i);
               JSONArray colorAndSizeArray = skuPropsArray.getJSONObject(i).getJSONArray("value");

               if(colorAndSizeArray.getJSONObject(0).has("imageUrl") || colorAndSizeObject.getInt("fid") == 3216){
                   productColor = getProductColor(colorAndSizeArray,colorAndSizeObject);
               }else{
                   productSize = getProductSize(colorAndSizeObject,colorAndSizeArray);
               }
           }
       }else{
           JSONObject colorAndSizeObject = skuPropsArray.getJSONObject(0);
           JSONArray colorAndSizeArray = skuPropsArray.getJSONObject(0).getJSONArray("value");
           productColor.setId(colorAndSizeObject.getInt("fid"));
           productColor.setName("颜色");
           productSize.setId(colorAndSizeObject.getInt("fid"));
           productSize.setName("尺码");
           List<Color> colors = new ArrayList<>();
           List<String> sizes = new ArrayList<>();
           for (int i = 0;i < colorAndSizeArray.length();i++){
               JSONObject object = colorAndSizeArray.getJSONObject(i);
               if(object.has("imageUrl")){
                   String name = object.getString("name");
                   String imageUrl = object.getString("imageUrl");
                   Color color = new Color(name, imageUrl);
                   colors.add(color);
               }else{
                   sizes.add(object.getString("name"));
               }
           }
           productColor.setColors(colors);
           productSize.setSize(sizes);
       }
//Tạo đối tượng Product Info Map trong dối tượng ProductColorAndSize
        JSONObject skuInfoMapObject = modelObject.getJSONObject("skuInfoMap");
        Iterator<String> keys = skuInfoMapObject.keys();
        List<ProductInfoMap> productInfoMaps = new ArrayList<>();
        while (keys.hasNext()){
            String key = keys.next();
            JSONObject productInfoMapsObject = skuInfoMapObject.getJSONObject(key);
            ProductInfoMap productInfoMap = new ProductInfoMap();
            productInfoMap.setId(productInfoMapsObject.getInt("skuId") + "");
            productInfoMap.setName(productInfoMapsObject.getString("specAttrs"));
            productInfoMap.setCanBookCount(productInfoMapsObject.getInt("canBookCount"));
            productInfoMap.setSaleCount(productInfoMapsObject.getInt("saleCount"));
            if(productInfoMapsObject.has("discountPrice")){
                productInfoMap.setDiscountPrice(productInfoMapsObject.getString("discountPrice"));
            }else{
                productInfoMap.setDiscountPrice("0.00");
            }
            if(productInfoMapsObject.has("price")){
                productInfoMap.setCurrentPrice(productInfoMapsObject.getString("price"));
            }else{
                productInfoMap.setCurrentPrice("0.00");
            }
            productInfoMaps.add(productInfoMap);
        }

        productColorAndSize.setProductColor(productColor);
        productColorAndSize.setProductSize(productSize);
        productColorAndSize.setProductInfoMap(productInfoMaps);
        return productColorAndSize;
    }

    private ProductColorAndSize convertStringOtherProductToObject2(String otherProduct){
        String other = otherProduct.trim();
        if (other.endsWith(",")) other = other.substring(0, other.length() - 1);
        String jsonString = "{" + other + "}}" ;
        ProductColorAndSize productColorAndSize = new ProductColorAndSize();
        ProductColor productColor = new ProductColor();
        ProductSize productSize = new ProductSize();
        JSONObject otherObject = new JSONObject(jsonString);
        JSONObject modelObject = otherObject.getJSONObject("distributeSkuModel");
        JSONArray skuPropsArray = modelObject.getJSONArray("skuProps");
        if(skuPropsArray.length() > 1){
            for (int i = 0; i< skuPropsArray.length();i++){
                JSONObject colorAndSizeObject = skuPropsArray.getJSONObject(i);
                JSONArray colorAndSizeArray = skuPropsArray.getJSONObject(i).getJSONArray("value");

                if(colorAndSizeArray.getJSONObject(0).has("imageUrl") || colorAndSizeObject.getInt("fid") == 3216){
                    productColor = getProductColor(colorAndSizeArray,colorAndSizeObject);
                }else{
                    productSize = getProductSize(colorAndSizeObject,colorAndSizeArray);
                }
            }
        }else{
            JSONObject colorAndSizeObject = skuPropsArray.getJSONObject(0);
            JSONArray colorAndSizeArray = skuPropsArray.getJSONObject(0).getJSONArray("value");
            productColor.setId(colorAndSizeObject.getInt("fid"));
            productColor.setName("颜色");
            productSize.setId(colorAndSizeObject.getInt("fid"));
            productSize.setName("尺码");
            List<Color> colors = new ArrayList<>();
            List<String> sizes = new ArrayList<>();
            for (int i = 0;i < colorAndSizeArray.length();i++){
                JSONObject object = colorAndSizeArray.getJSONObject(i);
                if(object.has("imageUrl")){
                    String name = object.getString("name");
                    String imageUrl = object.getString("imageUrl");
                    Color color = new Color(name, imageUrl);
                    colors.add(color);
                }else{
                    sizes.add(object.getString("name"));
                }
            }
            productColor.setColors(colors);
            productSize.setSize(sizes);
        }

        JSONObject skuInfoMapObject = modelObject.getJSONObject("skuInfoMap");
        Iterator<String> keys = skuInfoMapObject.keys();
        List<ProductInfoMap> productInfoMaps = new ArrayList<>();
        while (keys.hasNext()){
            String key = keys.next();
            JSONObject productInfoMapsObject = skuInfoMapObject.getJSONObject(key);
            ProductInfoMap productInfoMap = new ProductInfoMap();
            productInfoMap.setId(productInfoMapsObject.getInt("skuId") + "");
            productInfoMap.setName(productInfoMapsObject.getString("specAttrs"));
            productInfoMap.setCanBookCount(productInfoMapsObject.getInt("canBookCount"));
            productInfoMap.setSaleCount(productInfoMapsObject.getInt("saleCount"));
            if(productInfoMapsObject.has("discountPrice")){
                productInfoMap.setDiscountPrice(productInfoMapsObject.getString("discountPrice"));
            }else{
                productInfoMap.setDiscountPrice("0.00");
            }
            if(productInfoMapsObject.has("price")){
                productInfoMap.setCurrentPrice(productInfoMapsObject.getString("price"));
            }else{
                productInfoMap.setCurrentPrice("0.00");
            }
            productInfoMaps.add(productInfoMap);
        }

        productColorAndSize.setProductColor(productColor);
        productColorAndSize.setProductSize(productSize);
        productColorAndSize.setProductInfoMap(productInfoMaps);
        return productColorAndSize;
    }

    private ProductColor getProductColor(JSONArray jsonArray, JSONObject jsonObject){
        ProductColor productColor = new ProductColor();
        productColor.setId(jsonObject.getInt("fid"));
        productColor.setName(jsonObject.getString("prop"));
        List<Color> colorList = new ArrayList<>();
        for (int i=0; i<jsonArray.length();i++){
            String name = jsonArray.getJSONObject(i).getString("name");
            String imageUrl = "";
            if(jsonArray.getJSONObject(i).has("imageUrl")){
                imageUrl = jsonArray.getJSONObject(i).getString("imageUrl");
            }
            Color color = new Color(name, imageUrl);
            colorList.add(color);
        }
        productColor.setColors(colorList);
        return productColor;
    }

    private ProductSize getProductSize(JSONObject jsonObject, JSONArray jsonArray){
        ProductSize productSize = new ProductSize();
        productSize.setId(jsonObject.getInt("fid"));
        productSize.setName(jsonObject.getString("prop"));
        List<String> sizeList = new ArrayList<>();
        for (int i = 0; i< jsonArray.length();i++){
            sizeList.add(jsonArray.getJSONObject(i).getString("name"));
        }
        productSize.setSize(sizeList);
        return productSize;
    }

    private String getOfferIdFromUrl(String urlString){
       int startIndex = urlString.indexOf("offer/") + 6;
       int endIndex =urlString.indexOf(".html");
       String offerId = urlString.substring(startIndex,endIndex);
       return offerId;
    }

    private ProductDetail convertProductInfoFromProductInfoString(String productInfoString){
        String jsonString = "{" + productInfoString;
//        System.out.println("convertProductInfoFromProductInfoString");
//        System.out.println(jsonString);
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray dataArray = null;
        if (jsonObject.has("data")) {
            dataArray = jsonObject.getJSONArray("data");
        } else if (jsonObject.has("propsList")) {
            dataArray = jsonObject.getJSONArray("propsList");
        }
        ProductDetail productDetail = new ProductDetail();
        productDetail.setName("商品属性");
        List<ProductInfo> listProductInfos = new ArrayList<>();
        for (int i= 0 ; i< dataArray.length();i++){
            JSONObject item = dataArray.getJSONObject(i);
            int fid = 0;
            if(item.has("fid")){
                fid = item.getInt("fid");
            }
            String name = item.getString("name");
            String value = item.getString("value");
            ProductInfo info = new ProductInfo(fid,name,value);
            listProductInfos.add(info);
        }
        productDetail.setProductInfos(listProductInfos);
        return productDetail;
    }

    private List<String> convertImageListFromImageListJson(String imageListJson){
        String otherJson = imageListJson.trim();
        if (otherJson.endsWith(",")) otherJson = otherJson.substring(0, otherJson.length() - 1);
        String jsonString = "{" + otherJson + "}";
//        System.out.println(jsonString);
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray jsonArray = jsonObject.getJSONArray("offerImgList");
        List<String> listImage = new ArrayList<>();
        if(jsonArray.length() > 0){
            for(int i = 0; i<jsonArray.length();i++){
                listImage.add(jsonArray.getString(i));
            }
            return listImage;
        }
        return null;
    }

    private String getVideoUrlFromVideoJson(String videoJson){
        String jsonString = "{" + videoJson + "}";
        JSONObject ObjectJson = new JSONObject(jsonString);
        JSONObject videoJsonObject = ObjectJson.getJSONObject("video");
        if (videoJsonObject.has("videoUrl")) {
            String videoUrl = videoJsonObject.getString("videoUrl");
            return videoUrl;
        } else {
            return null;
        }
    }

}
