package com.example.qlud.control;

import com.example.qlud.DTO.ProductDTO;
import com.example.qlud.model.Color;
import com.example.qlud.model.Price;
import com.example.qlud.model.Product;
import com.example.qlud.repo.ProductRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/app")
public class CrawlController {

    @Autowired
    ProductRepo repo;

    private String getHtmlWeb(String url){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", "cookie");
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

    @GetMapping("/crawl")
    public ResponseEntity<?> crawlData(){
        try{
            File htmlFile = new File("E:\\data2.html");
            Document doc = Jsoup.parse(htmlFile, "UTF-8");
            Elements elements = doc.select("script");
            String OtherProduct = "\"skuModel\":\\{.*?\\}\\}";
            String data = "";
            String price = "";
            String otherProduct = "";
            String json = "";
            for (Element element: elements) {
                String scriptContent = element.html();
                if (scriptContent.contains("window.__GLOBAL_DATA") || scriptContent.contains("window.__INIT_DATA")) {
                    String dataSection = extractDataSection(scriptContent, "\"data\":[{", "}]}");
                    String propsList = extractDataSection(scriptContent, "\"propsList\":[{", "}]}");
                    data += dataSection + propsList;
                    price += extractDataSection(scriptContent, "\"currentPrices\":[{", "}]}");
                    Pattern pattern = Pattern.compile(OtherProduct,Pattern.DOTALL);
                    Matcher matcher = pattern.matcher(scriptContent);
                    while (matcher.find()) {
                        otherProduct += matcher.group();

                    }
                }
            }

            System.out.println(json);
            Product result = new Product();
            result.setPrice(convertStringPriceToObject(price));
            result.setColors(convertStringOtherProductToObject(otherProduct));
            ProductDTO productDTO = convertProductInfoFromProductInfoString(otherProduct);
            result.setProductId(productDTO.getProductId());
            result.setMaterial(productDTO.getMaterial());
            result.setProductBrand(productDTO.getProductBrand());
            result.setProductModel(productDTO.getProductModel());
            result.setProductSaleArea(productDTO.getProductSaleArea());
            result.setProductSize(productDTO.getProductSize());
            result.setProductType(productDTO.getProductType());
            result.setProductRelease(productDTO.getProductRelease());
            repo.save(result);
            return ResponseEntity.ok(result);
        }catch (IOException e){
            e.printStackTrace();
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }

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

    private List<Price> convertStringPriceToObject(String priceString){
        String jsonString = "{" + priceString + "}";
        List<Price> priceList = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray currentPrices = jsonObject.getJSONArray("currentPrices");
        for (int i = 0; i < currentPrices.length(); i++) {
            JSONObject priceInfo = currentPrices.getJSONObject(i);
            String price = priceInfo.getString("price");
            String beginAmount = priceInfo.getInt("beginAmount") + "";
            Price priceObject = new Price(price,beginAmount);
            priceList.add(priceObject);
        }
        return priceList;
    }

    private List<Color> convertStringOtherProductToObject(String otherProduct) throws JsonProcessingException {
        String other = otherProduct.trim();
        if (other.endsWith(",")) other = other.substring(0, other.length() - 1);
        String jsonString = "{" + other + "}}" ;
        List<Color> otherProductList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonString);
        JsonNode skuInfoMapOriginal = rootNode.path("skuModel").path("skuInfoMapOriginal");
        if (skuInfoMapOriginal.isMissingNode()) {
            skuInfoMapOriginal = rootNode.path("skuModel").path("skuInfoMap");
        }
        Iterator<Map.Entry<String, JsonNode>> fields = skuInfoMapOriginal.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> field = fields.next();
            String specAttrs = field.getValue().path("specAttrs").asText("N/A");
            String price = field.getValue().path("price").asText("0.00");
            String saleCount = field.getValue().path("saleCount").asInt(0) + "";
            String canBookCount = field.getValue().path("canBookCount").asInt(0) + "";
            String imageUrl = "N/A";
            JsonNode skuProps = rootNode.path("skuModel").path("skuProps");
            if (skuProps.isArray() && skuProps.size() > 0) {
                for (JsonNode prop : skuProps) {
                    if (prop.path("fid").asInt() == 3216) {
                        for (JsonNode value : prop.path("value")) {
                            String colorName = specAttrs.split(">")[0].trim();
                            String valueName = value.path("name").asText().trim();
                            if (valueName.equals(ConvertStringToStandard(colorName))) {
                                imageUrl = value.path("imageUrl").asText("N/A");
                                break;
                            }
                        }
                    }
                }
            }
            Color otherProductObject = new Color(imageUrl,specAttrs,canBookCount,price,saleCount);
            otherProductList.add(otherProductObject);
        }
        return otherProductList;
    }

    private String ConvertStringToStandard(String NameString){
        int index = NameString.indexOf("&gt");
        String rs;
        if(index != -1){
            rs = NameString.substring(0,index);
        }else{
            rs = NameString;
        }
        return rs;

    }

    private ProductDTO convertProductInfoFromProductInfoString(String productInfoString){
        String jsonString = "{" + productInfoString + "}";
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray dataArray = null;
        if (jsonObject.has("data")) {
            dataArray = jsonObject.getJSONArray("data");
        } else if (jsonObject.has("propsList")) {
            dataArray = jsonObject.getJSONArray("propsList");
        }
        String pId = "",pBrand = "", pModel = "",pStyle = "",pMetarial ="",pSaleArea ="",pColor="",pSize="",pRelease = "";
        for (int i= 0 ; i< dataArray.length();i++){
            JSONObject item = dataArray.getJSONObject(i);
            String name = item.getString("name");

            switch (name){
                case "产品编号":
                    pId += item.getString("value");
                    break;
                case "货号":
                    pId += item.getString("value");
                    break;
                case "品牌":
                    pBrand += item.getString("value");
                    break;
                case "图案":
                    pModel += item.getString("value");
                    break;
                case "型号":
                    pModel += item.getString("value");
                    break;
                case "面料名称":
                    pMetarial += item.getString("value");
                    break;
                case "主面料成分":
                    pMetarial += item.getString("value");
                    break;
                case "主面料成分含量":
                    pMetarial += item.getString("value");
                    break;
                case "主面料成分2":
                    pMetarial += item.getString("value");
                    break;
                case "主面料成分2含量":
                    pMetarial += item.getString("value");
                    break;
                case "材质":
                    pMetarial += item.getString("value");
                    break;
                case "主要下游销售地区2":
                    pSaleArea += item.getString("value");
                    break;
                case "主要下游销售地区1":
                    pSaleArea += item.getString("value");
                    break;
                case "主要销售地区":
                    pSaleArea += item.getString("value");
                    break;
                case "颜色":
                    pColor += item.getString("value");
                    break;
                case "尺码":
                    pSize += item.getString("value");
                    break;
                case "尺寸":
                    pSize += item.getString("value");
                    break;
                case "上市年份/季节":
                    pRelease += item.getString("value");
                    break;
                case "风格":
                    pStyle += item.getString("value");
            }
        }
        ProductDTO productInfo = new ProductDTO(pId,pStyle,pMetarial,pSaleArea,pModel,pSize,pBrand,pColor,pRelease);
        return productInfo;
    }

}
