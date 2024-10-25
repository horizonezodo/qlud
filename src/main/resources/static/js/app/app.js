const app = angular.module('app', ['ui.router']);
app.factory('dialogService', function($http,$rootScope,$state) {
    return {
        showErrorDialog: function(title, message) {
            Swal.fire({
                icon: "error",
                title: title,
                text: message,
                footer: '<a href="#">Why do I have this issue?</a>'
            });
        },

        showSuccessDialog: function (title, message){
            Swal.fire({
                position: "top-end",
                icon: "success",
                title: title,
                text: message,
                showConfirmButton: false,
                timer: 1500
            });
        },

        checkToken: function (){
            return new Promise((resolve, reject)=>{
                const headers = {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + localStorage.getItem('accessToken')
                }
                const accessToken = localStorage.getItem('accessToken');
                console.log("access Token: ", accessToken);
                $http.post('http://192.168.113.231:8080/auth/check-token',{ token: accessToken}).then(
                    function (res){
                        console.log("Đã chạy vào hàm check token và token hợp lệ đây");
                        if(res.status === 200 || res.status === 201){
                             return resolve(true);
                        }
                    }
                ).catch((err) =>{
                    console.log("Token ko hợp lệ call refreshToken");
                    if([400, 401, 403].includes(err.status)){
                        const refreshToken = localStorage.getItem('refreshToken');
                        console.log("refresh Token: ", refreshToken);
                        $http.post('http://192.168.113.231:8080/auth/refresh-token',{refreshToken: refreshToken}).then(
                            function (res){
                                console.log("Refresh Token thành công");
                                if(res.status === 200 || res.status === 201){
                                    console.log("logout response: " + res);
                                    localStorage.setItem("accessToken", res.data.accessToken);
                                    localStorage.setItem("refreshToken", res.data.refreshToken);
                                    return resolve(true);
                                }
                            }
                        ).catch((err) => {
                            console.log("RefreshToken hết hạn tiến hành login lại")
                            if([400, 401, 403].includes(err.status)){
                                $http.post('http://192.168.113.231:8080/auth/logout',{},{headers:headers})
                                    .then(
                                    function (res){
                                        console.log("Logout thành công ")
                                        console.log("logout response: " + res);
                                        localStorage.removeItem("accessToken");
                                        localStorage.removeItem("refreshToken");
                                        localStorage.removeItem("username");
                                        $rootScope.$broadcast('userLoggedIn');
                                        $state.go('login');
                                    }
                                ).catch((err)=>{
                                    console.log("Lỗi xảy ra ")
                                    console.error("Lỗi khác:", err);
                                    return reject(err);
                                })
                            }
                        })
                    }else{
                        console.log("Lỗi ngay từ đầu")
                        console.error("Lỗi khác:", err);
                        return reject(err);
                    }
                })
            })
        }
    };
});

app.config(function ($stateProvider, $urlRouterProvider){
    $urlRouterProvider.otherwise('/')
    $stateProvider
        .state('home',{
            url: '/',
            templateUrl: 'partials/home',
            controller: 'HomeController',
            controllerAs:'ctrl'
        })
        .state('login',{
            url: '/login',
            templateUrl: 'partials/login',
            controller: 'loginController',
            controllerAs:'ctrl'
        })
        .state('crawl',{
            url:'/crawl',
            templateUrl:'partials/crawl',
            controller: 'CrawlController',
            controllerAs:'ctrl'
        })
        .state('crawl-data',{
            url:'/crawl-data',
            templateUrl:'partials/crawl-data',
            controller:'CrawlDataController',
            controllerAs:'ctrl'
        })
        .state('log-data',{
            url:'/log-data',
            templateUrl:'partials/log-data',
            controller:'LogDataController',
            controllerAs:'ctrl'
        })
        .state('log2',{
            url:'/log2/:logId',
            templateUrl:'partials/log2',
            controller:'Log2Controller',
            controllerAs:'ctrl'
        })
});