'use strict'
angular.module('app').factory('CrawlService', ['$http', '$q',function ($http,$q){
    const factory = {
        crawlData: crawlData
    };

    function crawlData(crawl){
        console.log('crawl data start')
        const deferred = $q.defer()
        const headers = {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('accessToken')
        }
        $http.post('http://192.168.113.231:8080/app/crawl', crawl, {headers:headers})
            .then(
                function (res){
                    console.log('crawl success')
                    console.log(res.data)
                    deferred.resolve(res.data);
                }, function (err){
                    console.error('crawl error')
                    deferred.reject(err);
                }
            )
        return deferred.promise;
    }

    return factory;
}])
.controller('CrawlController',['CrawlService','dialogService','$scope','$window','$http',function(CrawlService,dialogService,$scope,$window,$http){
    const self = this;

    self.isShow = false;
    self.cookieValue = '';
    self.crawl={
        crawlUrl : '',
        cookieData : ''
    }
    self.ImageList=[];
    self.ImageDetailList = [];
    self.currentPriceList=[];
    self.originalPriceList=[];
    self.colorList=[];
    self.sizeList=[];
    self.mapList=[];
    self.detailList=[];
    self.title='';
    self.priceLabel='';
    self.colorLabel='';
    self.sizeLable='';
    self.detailLabel='';
    self.showVideo = false;
    self.videoLabel='';
    self.videoUrl ='';
    self.imageDetailLabel='';
    self.productUrl='';
    self.offterId='';
    self.imageDetailList=[];
    self.imagePerPage = 4;
    self.currentPage = 0;
    self.currentImages=[];
    self.currentImage = '';
    self.colorNull = false;
    self.sizeNull = false;
    self.showImgBtn = false;
    self.showImgData = false;

    self.crawlData = crawlData

    function crawlData(crawl){

        self.ImageList=[];
        self.ImageDetailList = [];
        self.currentPriceList=[];
        self.originalPriceList=[];
        self.colorList=[];
        self.sizeList=[];
        self.mapList=[];
        self.detailList=[];
        self.title='';
        self.priceLabel='';
        self.colorLabel='';
        self.sizeLable='';
        self.detailLabel='';
        self.showVideo = false;
        self.videoLabel='';
        self.videoUrl ='';
        self.imageDetailLabel='';
        self.productUrl='';
        self.offterId='';
        self.imageDetailList=[];
        self.imagePerPage = 4;
        self.currentPage = 0;
        self.currentImages=[];
        self.currentImage = '';
        self.colorNull = false;
        self.sizeNull = false;
        self.showImgBtn = false;
        self.showImgData = false;

        const cookieData = encodeURIComponent(self.cookieValue);
        self.crawl.cookieData = cookieData
        CrawlService.crawlData(self.crawl)
            .then(
                function (res){
                    console.log('response data: ', res);
                     self.ImageList = res.imageProductList;
                     self.ImageDetailList = res.productImageDetail;
                     self.currentPriceList = res.productPrices.currentPrice;
                     self.originalPriceList = res.productPrices.originalPrice;
                     if(res.productColorAndSize.productColor && res.productColorAndSize.productColor.colors && res.productColorAndSize.productColor.colors.length > 0){
                         console.log('color size > 0')
                         self.colorNull = true;
                         self.colorList = res.productColorAndSize.productColor.colors || [];
                         self.colorLabel=res.productColorAndSize.productColor.name;
                         if(res.productColorAndSize.productColor.colors.imageUrl !== "" || res.productColorAndSize.productColor.colors.imageUrl !== null || res.productColorAndSize.productColor.colors.imageUrl !== undefined){
                             console.log("Đã chạy vào check image")
                             self.showImgBtn = true;
                             self.showImgData = true;
                         }else{
                             console.log("ko chạy vào check image")
                             self.showImgBtn = false;
                             self.showImgData = false;
                         }
                     }

                    if(res.productColorAndSize.productSize && res.productColorAndSize.productSize.size && res.productColorAndSize.productSize.size.length > 0){
                        console.log('size size > 0')
                        self.sizeNull=true
                        self.sizeLable=res.productColorAndSize.productSize.name;
                        self.sizeList = res.productColorAndSize.productSize.size || [];
                    }
                    self.mapList = res.productColorAndSize.productInfoMap;
                    self.detailList = res.productDetail.productInfos;
                    self.title= res.productTitle;
                    self.priceLabel=res.productPrices.name;
                    self.detailLabel=res.productDetail.name;
                    self.videoLabel='Video Demo';
                    if(res.videoUrl !== null && res.videoUrl !== '' && res.videoUrl !== undefined && res.videoUrl){
                        self.showVideo=true;
                        self.videoUrl = res.videoUrl;
                    }
                    self.imageDetailLabel='商品描述';
                    self.imageDetailList = res.productImageDetail;
                    self.productUrl=res.link;
                    self.offterId=res.offerId;
                    self.currentImage = self.ImageList[0];
                    const [colorName1,sizeName1 ] = self.mapList[0].name.split('&gt;');
                    let nameFoundInColorList = self.colorList.some(item => item.name === colorName1);
                    let nameFoundInSizeList = self.sizeList.some(item => item.name === colorName1);
                    if(nameFoundInColorList && !nameFoundInSizeList){
                        // console.log("có chạy vào đây")
                        self.showImgBtn = false;
                    }else if(nameFoundInSizeList && !nameFoundInColorList){
                        // console.log("cũng chạy vào đây")
                        self.showImgBtn = true;
                    }
                    self.updateCurrentImage();
                    self.processShowData();
                    self.isShow=true;
                }, function (err){
                    dialogService.showErrorDialog("Crawl Error", err.message)
                }
            )
    };

    //    Hiển thị hình ảnh
    self.updateCurrentImage = function (){
        self.currentImages = self.ImageList.slice(
            self.currentPage * self.imagePerPage,
            (self.currentPage + 1) * self.imagePerPage
        );
    };

    self.changeImage = function (image){
        self.currentImage = image;
    };

    self.nextPage = function (){
        if(self.currentPage < Math.floor(self.ImageList.length / self.imagePerPage)){
            self.currentPage++;
            self.updateCurrentImage();
        }
    };
    self.previousPage = function (){
        if(self.currentPage > 0){
            self.currentPage--;
            self.updateCurrentImage();
        }
    };
    self.updateCurrentImage();
    console.log(self.ImageList);
    console.log(self.currentImage)
    console.log(self.currentImages)
    //    End hiển thị hình ảnh

    // Hiển thị bảng size
    self.processShowData = function (){
        self.showDataList = [];
        console.log("showDataList after reset: " ,self.showDataList)
        const prices = self.currentPriceList.map(item => parseFloat(item.price));
        const maxPrice = Math.max(...prices);
//    Nếu chỉ có productColor mà không có productSize
        if(self.colorNull && !self.sizeNull) {
            self.showDataList = []
            self.colorList.forEach(item1 => {
                self.mapList.forEach(item2 => {
                    if (item1.name.trim() === item2.name.trim()) {
                        let newData = {
                            imgUrl: item1.imageUrl,
                            name: item1.name,
                            canBookCount: item2.canBookCount,
                            saleCount: item2.canBookCount === 0 ? "缺货" : item2.saleCount,
                            price: item2.discountPrice !== null && item2.discountPrice !== undefined && item2.discountPrice !== '0.00' && item2.discountPrice
                                ? item2.discountPrice
                                : (item2.currentPrice !== null && item2.currentPrice !== undefined && item2.currentPrice !== '0.00' && item2.currentPrice
                                    ? item2.currentPrice
                                    : maxPrice)
                        };
                        self.showDataList.push(newData);
                    }
                })
                console.log("List data show")
                console.log(self.showDataList);
            })
        }
//    Nếu chỉ có productSize mà không có productColor
        if(!self.colorNull && self.sizeNull){
            self.showDataList = [];
            self.sizeList.forEach(item1 =>{
                self.mapList.forEach(item2 =>{
                    if(item1.trim() === item2.name.trim()){
                        let newData = {
                            imgUrl: '',
                            name: item2.name,
                            canBookCount: item2.canBookCount,
                            saleCount: item2.canBookCount === 0 ? "缺货" : item2.saleCount,
                            price: item2.discountPrice !== null && item2.discountPrice !== undefined && item2.discountPrice !== '0.00' && item2.discountPrice
                                ? item2.discountPrice
                                : (item2.currentPrice !== null && item2.currentPrice !== undefined && item2.currentPrice !== '0.00' && item2.currentPrice
                                    ? item2.currentPrice
                                    : maxPrice)
                        };
                        self.showDataList.push(newData);
                    }
                })
            })
            console.log("List data show")
            console.log(self.showDataList);
        }
//    Nếu có cả productColor và productSize
        if(self.colorNull && self.sizeNull){
            self.showDataList = [];
            console.log("size and color sizre > 0")
            self.selectColor = function (color){
                self.showDataList = [];
                console.log('call select color')
                if(!self.showImgBtn){
                    console.log("có show img ");
                    self.sizeList.forEach(item1 =>{
                        self.mapList.forEach(item2 =>{
                                const [colorName,sizeName] = item2.name.split('&gt;');
                            // console.log("name of btn: " + color + " || color name sau khi phân tách: " + colorName + " || color Name của be gửi: " + item1.name + " || name sau khi phan tách: " + sizeName);
                                if(color.trim() === colorName.trim() && item1.trim() === sizeName){
                                    let newData = {
                                        imgUrl: '',
                                        name: item1,
                                        canBookCount: item2.canBookCount,
                                        saleCount: item2.canBookCount === 0 ? "缺货" : item2.saleCount,
                                        price: item2.discountPrice !== null && item2.discountPrice !== undefined && item2.discountPrice !== '0.00' && item2.discountPrice
                                            ? item2.discountPrice
                                            : (item2.currentPrice !== null && item2.currentPrice !== undefined && item2.currentPrice !== '0.00' && item2.currentPrice
                                                ? item2.currentPrice
                                                : maxPrice)
                                    };
                                    self.showDataList.push(newData);
                                }
                            }
                        )
                    })
                }else{
                    console.log("color thu được ",color);
                    console.log("không show img");
                    self.colorList.forEach(item1 =>{
                        self.mapList.forEach(item2 =>{
                                const [colorName,sizeName] = item2.name.split('&gt;');
                                // console.log("name of btn: " + color + " || color name sau khi phân tách: " + colorName + " || color Name của be gửi: " + item1.name + " || name sau khi phan tách: " + sizeName);
                                if(color.trim() === colorName.trim() && item1.name.trim() === sizeName){
                                    let newData = {
                                        imgUrl: '',
                                        name: item1.name,
                                        canBookCount: item2.canBookCount,
                                        saleCount: item2.canBookCount === 0 ? "缺货" : item2.saleCount,
                                        price: item2.discountPrice !== null && item2.discountPrice !== undefined && item2.discountPrice !== '0.00' && item2.discountPrice
                                            ? item2.discountPrice
                                            : (item2.currentPrice !== null && item2.currentPrice !== undefined && item2.currentPrice !== '0.00' && item2.currentPrice
                                                ? item2.currentPrice
                                                : maxPrice)
                                    };
                                    self.showDataList.push(newData);
                                }
                            }
                        )
                    })
                }
                return self.showDataList;
            }
            console.log("List data show");
            console.log(self.showDataList);
            if(!self.showImgBtn){
                self.selectColor(self.colorList[0].name);
            }else{
                self.selectColor(self.sizeList[0]);
            }
        }

    //    Nếu không có cả color and size
        if(!self.colorNull && !self.sizeNull){
            self.showDataList = [];
            self.mapList.forEach(item => {
                let newData = {
                    imgUrl: '',
                    name: item.name,
                    canBookCount: item.canBookCount,
                    saleCount: item.canBookCount === 0 ? "缺货" : item.saleCount,
                    price: item.discountPrice !== null && item.discountPrice !== undefined && item.discountPrice !== '0.00' && item.discountPrice
                        ? item.discountPrice
                        : (item.currentPrice !== null && item.currentPrice !== undefined && item.currentPrice !== '0.00' && item.currentPrice
                            ? item.currentPrice
                            : maxPrice)
                };
                self.showDataList.push(newData);
            })
        }
        if (!$scope.$$phase) {
            $scope.$apply();
        }
    }

//    Trường hợp đặc biệt : Điện thoại đổi vị trí của size và color


     self.processShowData();


}])