'use strict'
angular.module('app')
    .factory('Log2Service', ['$http', '$q',function ($http,$q){
        const factory={
            getLog : getLog
        };

        function getLog(logId){
            const deferred = $q.defer();
            const headers = {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('accessToken')
            }
            $http.get('http://192.168.113.231:8080/app/log/'+logId,{headers:headers})
                .then(
                    function (res){
                        console.log('Get Data');
                        console.log(res.data)
                        deferred.resolve(res.data)
                    },
                    function (err){
                        console.log("error: " + err.message())
                        deferred.reject(err);
                    }
                );
            return deferred.promise;
        }

        return factory;
    }])
    .controller('Log2Controller',['$state','$scope','$stateParams','Log2Service','dialogService', function ($state, $scope, $stateParams,Log2Service,dialogService){
    const self = this;
    let logId = $stateParams.logId;
    self.backToLog= function (){
        $state.go('log-data')
    }
    self.logData = {
        id:'',
        url:'',
        method:'',
        request:{},
        response:{},
        statusCode:0,
        startTime:'',
        message:'',
    };
    console.log(logId);
    getLog(logId);
    function getLog(logId){
        console.log('log id: ' + logId);
        Log2Service.getLog(logId).then(
            function (response){
                self.logData = {};
                self.logData.id = response.id;
                self.logData.url = response.url;
                self.logData.method = response.method;
                if(isValidJson(response.request)){
                    self.logData.request = JSON.parse(response.request);
                }else{
                    self.logData.request = response.request;
                }
                if(isValidJson(response.response)){
                    self.logData.response = JSON.parse(response.response);
                }else{
                    self.logData.response = response.response;
                }
                self.logData.statusCode = response.status;
                self.logData.startTime = response.startTime;
                self.logData.message = response.message;
            }
            ,function (err){
                dialogService.showErrorDialog("Error", err.message);
            }
        )
    }

    function isValidJson(str){
        try{
            JSON.parse(str);
        }catch (e){
            return false;
        }
        return true;
    }


}])
