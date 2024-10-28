
angular.module('app').factory('LogDataService', ['$http', '$q',function ($http,$q){
    const factory={
        getAllData:getAllData,
        getAllData2: getAllData2,
        searchData:searchData,
    };

    function getAllData2(page,size){
        const deferred = $q.defer();
        const headers = {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('accessToken')
        }

        $http.get('http://192.168.113.231:8080/app/all-log?page='+page+'&size='+size,{headers:headers})
            .then(
                function (res){
                    console.log("Get all data: ", res.data);
                    deferred.resolve(res.data);
                }, function (err){
                    console.log("Error", err);
                    deferred.reject(err.message);
                }
            );
        return deferred.promise;
    }

    function getAllData(page,size){
        const deferred = $q.defer();
        const headers = {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('accessToken')
        }
        $http.get('http://192.168.113.231:8080/app/all-log?page=' + page + '&size=' + size,{headers:headers})
            .then(
                function (res){
                    console.log('Get data: ', res);
                    console.log('Get all Data');
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
        const deferred = $q.defer();
        const headers = {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('accessToken')
        }
        $http.post('http://192.168.113.231:8080/app/search-log?key=' + keyvalue + "&page=" + page + '&size=' + size,{},{headers:headers})
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
    .controller('LogDataController',['LogDataService','dialogService','$scope','$state',function(LogDataService,dialogService,$scope,$state){
        const self = this;
        self.logDatas = [];
        self.key = '';
        self.currentPage = 0;
        self.indexSize = 25;
        self.totalPage = 100;
        self.pageToShow = 5;
        self.listPage = [];
        self.isSearching = false;
        self.goToPage = goToPage;
        self.submit = submit;
        self.viewDetail = function (id){
            console.log(id)
            $state.go('log2',{logId:id})
        };

        loadAllData(self.currentPage, self.indexSize);

        function loadAllData(page,size){
            dialogService.checkToken().then((isValid) => {
                if(isValid){
                    self.logDatas = [];
                    LogDataService.getAllData2(page,size).then(
                        function (response){
                            console.log("data controller thu duoc : ",response);
                            self.totalPage = response.totalPages;
                            self.logDatas = response.content;
                            updatePagination();}
                        ,function (err){
                            dialogService.showErrorDialog("Error", err.message);
                        }
                    )
                }else{
                    console.log("Token không hợp lệ");
                }
            }).catch((err)=>{
                console.error("Lỗi");
            })
        }

        function submit(){
            dialogService.checkToken().then((isValid)=>{
                if(isValid){
                    console.log('call submit')
                    LogDataService.searchData(self.key,self.currentPage,self.indexSize).then(
                        function (response){
                            console.log('data search: ', response);
                            self.totalPage = response.totalPages;
                            self.logDatas = [];
                            self.isSearching=true;
                            self.logDatas = response.content;
                            updatePagination();
                        }, function (err){
                            console.log("err: ", err);
                            dialogService.showErrorDialog("Error", err.message);
                        }
                    )
                }else{
                    console.log("Error");
                }
            }).catch((err)=>{
                console.log(err);
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