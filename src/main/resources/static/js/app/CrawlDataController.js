'use strict'
angular.module('app').factory('CrawlDataService', ['$http', '$q',function ($http,$q){
    const factory={
        getAllData:getAllData,
        searchData:searchData,
    };

    // const crawlData = [];

    function getAllData(page, size){
        const deferred = $q.defer();
        const headers = {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('accessToken')
        }
        $http.get('http://192.168.113.231:8080/app/crawl-data?page='+page+'&size='+size,{headers:headers})
            .then(
                function (res){
                    console.log('Get all Data');
                    console.log(res.data);
                    deferred.resolve(res.data)
                },
                function (err){
                    console.log("error: " + err.message())
                    deferred.reject(err);
                }
            );
        return deferred.promise;
    }

    function searchData(keyvalue,page,size){
        console.log(keyvalue);
        const deferred = $q.defer();
        const headers = {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('accessToken')
        }
        $http.post('http://192.168.113.231:8080/app/search?key=' + keyvalue + '&page='+page+'&size='+size,{},{headers:headers})
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
    self.currentPage = 0;
    self.indexSize = 25;
    self.totalPage = 100;
    self.pageToShow = 5;
    self.listPage = [];
    self.isSearching = false;
    self.goToPage = goToPage;
    self.submit = submit;

    loadAllData(self.currentPage,self.indexSize);
    showNavigate();

    function loadAllData(page,size){
        dialogService.checkToken().then((isValid)=>{
            if(isValid){
                CrawlDataService.getAllData(page,size).then(
                    function (response){
                        // console.log('data nhan duoc: ', response);
                        self.crawlDatas = [];
                        if(response.content.productColorAndSize !== undefined && response.content.productColorAndSize !== null && response.content.productColorAndSize!== '' &&response.content.productColorAndSize.productColor && response.content.productColorAndSize.productColor.colors && response.content.productColorAndSize.productColor.colors.length > 0){
                            self.showColor = true;
                        }
                        if(response.content.productColorAndSize !== undefined && response.content.productColorAndSize !== null && response.content.productColorAndSize!== '' &&response.content.productColorAndSize.productSize && response.content.productColorAndSize.productSize.size && response.content.productColorAndSize.productSize.size.length > 0){
                            self.showSize = true;
                        }
                        self.totalPage = response.totalPages;
                        response.content.forEach(res => {
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
                        updatePagination();

                        // console.log(self.crawlDatas);

                    },function (err){
                        dialogService.showErrorDialog("Error", err.message);
                    }
                )
            }else{
                console.log("Không hợp lệ");
            }
        }).catch((err)=>{
            console.log(err);
        })
    }

    function submit(){
        dialogService.checkToken().then((isValid)=>{
            if(isValid){
                console.log('call submit')
                CrawlDataService.searchData(self.key,self.currentPage,self.indexSize).then(
                    function (response){
                        console.log('data search: ', response);
                        self.crawlDatas = [];
                        self.isSearching = true;
                        self.totalPage = response.totalPages;
                        if(response.content.productColorAndSize !== undefined && response.content.productColorAndSize !== null && response.content.productColorAndSize!== '' &&response.content.productColorAndSize.productColor && response.content.productColorAndSize.productColor.colors && response.content.productColorAndSize.productColor.colors.length > 0){
                            self.showColor = true;
                        }
                        if(response.content.productColorAndSize !== undefined && response.content.productColorAndSize !== null && response.content.productColorAndSize!== '' &&response.content.productColorAndSize.productSize && response.content.productColorAndSize.productSize.size && response.content.productColorAndSize.productSize.size.length > 0){
                            self.showSize = true;
                        }
                        response.content.forEach(res => {
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
                        updatePagination();
                        console.log(self.crawlDatas);
                    }, function (err){
                        console.log("err: ", err);
                        dialogService.showErrorDialog("Error", err.message);
                    }
                )
            }else{
                console.log("Error");
            }
        }).catch((err)=>{
            console.log(err)
        })
    }

    function updatePagination(){
        self.listPage = [];
        self.listPage = showNavigate();
    }

    function showNavigate(){
        let startPage, endPage;
        if(self.currentPage <= 3){
            startPage = 1;
            endPage = Math.min(self.totalPage, self.pageToShow);
        }else if(self.currentPage + 2 >= self.totalPage){
            startPage = Math.max(1, self.totalPage - self.pageToShow + 1);
            endPage = self.totalPage;
        }else{
            startPage = self.currentPage - 2;
            endPage = self.currentPage + 2;
        }

        let pages = [];
        for(let i = startPage ; i < endPage ;i++){
            pages.push(i);
        }
        return pages;
    }

    function goToPage(page){
        if(page !== self.currentPage) {
            self.currentPage = page;
            if(self.isSearching){
                submit()
            }else{
                loadAllData(page, self.indexSize);
            }
        }
    }

}])