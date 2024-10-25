'use strict'
angular.module('app').factory('CrawlDataService', ['$http', '$q',function ($http,$q){
    const factory={
        getAllData:getAllData,
        searchData:searchData,
    };

    // const crawlData = [];

    function getAllData(){
        const deferred = $q.defer();
        const headers = {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('accessToken')
        }
        $http.get('http://192.168.113.231:8080/app/crawl-data',{headers:headers})
            .then(
                function (res){
                    console.log('Get all Data');
                    // console.log(res.data);
                    // crawlData.length = 0;
                    // Array.prototype.push.apply(crawlData, res.data);
                    deferred.resolve(res.data)
                },
                function (err){
                    console.log("error: " + err.message())
                    deferred.reject(err);
                }
            );
        return deferred.promise;
    }

    function searchData(keyvalue){
        const deferred = $q.defer();
        const headers = {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('accessToken')
        }
        $http.post('http://192.168.113.231:8080/app/search?key=' + keyvalue,{},{headers:headers})
            .then(
                function (res){
                    console.log("search success");
                    console.log(res.data);
                    deferred.resolve(res.data);
                },function (err){
                    console.log('Search Error: ', err.message);
                    deferred.reject(err);
                }
            );
        return deferred.promise;
    }
    return factory;
}])
.controller('CrawlDataController',['CrawlDataService','dialogService','$scope','$window','$http',function(CrawlDataService,dialogService,$scope,$window,$http){
    const self = this;
    self.crawlDatas = [];
    self.showColor = false;
    self.showSize = false;
    self.key = '';
    self.submit = submit;

    loadAllData();

    function loadAllData(){
        CrawlDataService.getAllData().then(
            function (response){
                // console.log('data nhan duoc: ', response);
                self.crawlDatas = [];
                if(response.productColorAndSize !== undefined && response.productColorAndSize !== null && response.productColorAndSize!== '' &&response.productColorAndSize.productColor && response.productColorAndSize.productColor.colors && response.productColorAndSize.productColor.colors.length > 0){
                    self.showColor = true;
                }
                if(response.productColorAndSize !== undefined && response.productColorAndSize !== null && response.productColorAndSize!== '' &&response.productColorAndSize.productSize && response.productColorAndSize.productSize.size && response.productColorAndSize.productSize.size.length > 0){
                    self.showSize = true;
                }
                response.forEach(res => {
                    // console.log("size data: ", res.productColorAndSize.productSize.size);
                    let crawlData = {
                        pTitle : res.productTitle,
                        pLink : res.link,
                        pImg: res.imageProductList[0],
                        pPrice: res.productPrices?.currentPrice?.length > 0
                            ? res.productPrices.currentPrice.map(item => `${item.price} for ${item.priceAmount}`).join(', ')
                            : '',
                        pColor: res.productColorAndSize?.productColor?.colors?.length > 0
                            ? res.productColorAndSize.productColor.colors.map(item => item.name).join(', ')
                            : '',
                        pSize: res.productColorAndSize?.productSize?.size?.length ? res.productColorAndSize.productSize.size.join(', ') : '',
                    }
                        self.crawlDatas.push(crawlData);
                });
                // console.log(self.crawlDatas);

            },function (err){
                dialogService.showErrorDialog("Error", err.message);
            }
        )
    }

    function submit(key){
        console.log('call submit')
        CrawlDataService.searchData(self.key).then(
            function (response){
                console.log('data search: ', response);
                self.crawlDatas = [];
                if(response.productColorAndSize !== undefined && response.productColorAndSize !== null && response.productColorAndSize!== '' &&response.productColorAndSize.productColor && response.productColorAndSize.productColor.colors && response.productColorAndSize.productColor.colors.length > 0){
                    self.showColor = true;
                }
                if(response.productColorAndSize !== undefined && response.productColorAndSize !== null && response.productColorAndSize!== '' &&response.productColorAndSize.productSize && response.productColorAndSize.productSize.size && response.productColorAndSize.productSize.size.length > 0){
                    self.showSize = true;
                }
                response.forEach(res => {
                    let crawlData = {
                        pTitle : res.productTitle,
                        pLink : res.link,
                        pImg: res.imageProductList[0],
                        pPrice: res.productPrices?.currentPrice?.length > 0
                            ? res.productPrices.currentPrice.map(item => `${item.price} for ${item.priceAmount}`).join(', ')
                            : '',
                        pColor: res.productColorAndSize?.productColor?.colors?.length > 0
                            ? res.productColorAndSize.productColor.colors.map(item => item.name).join(', ')
                            : '',
                        pSize: res.productColorAndSize?.productSize?.size?.length ? res.productColorAndSize.productSize.size.join(', ') : '',
                    }
                    self.crawlDatas.push(crawlData);
                });
                console.log(self.crawlDatas);
            }, function (err){
                console.log("err: ", err);
                dialogService.showErrorDialog("Error", err.message);
            }
        )
    }

}])