app.directive('navbar', function() {
    return {
        restrict: 'E',
        templateUrl: 'partials/navbar',
        controller: function ($scope,$http,$location,$state,$rootScope){
            $scope.username= null;
            $scope.checkUserLogin = function (){
                const username = localStorage.getItem('username');
                if(username){
                    $scope.username = username;
                }else{
                    $scope.username = null;
                }
                $scope.label = $scope.username ? $scope.username : 'My Account';
            };

            $rootScope.$on('userLoggedIn', function() {
                $scope.checkUserLogin();  // Gọi lại hàm checkUserLogin sau khi đăng nhập thành công
            });

            $scope.login = function (){
                $state.go('login');
            }

            $scope.checkUserLogin();

            $scope.logout = function (){
                const headers = {
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + localStorage.getItem('accessToken')
                }

                $http.post('http://192.168.113.231:8080/auth/logout',{},{headers:headers})
                    .then(
                        function (res){
                            console.log("logout response: " + res);
                            localStorage.removeItem("accessToken");
                            localStorage.removeItem("refreshToken");
                            localStorage.removeItem("username");
                            $scope.checkUserLogin();
                            $state.go('login');
                        },
                        function (err){
                            console.log("error: " + err);
                        }
                    )
            }

            $scope.goHome = function (){
                $state.go('home');
            }

            $scope.goCrawl = function (){
                if(localStorage.getItem("accessToken")) {
                    $state.go('crawl');
                }else{
                    localStorage.setItem("path", "/#!/crawl")
                    $state.go('login')
                }
            }

            $scope.goCrawlData = function (){
                if(localStorage.getItem("accessToken")) {
                    $state.go('crawl-data');
                }else{
                    localStorage.setItem("path", "/#!/crawl-data")
                    $state.go('login');
                }
            }
            $scope.goLog = function (){
                if(localStorage.getItem("accessToken")) {
                    $state.go('log-data');
                }else{
                    localStorage.setItem("path", "/#!/log-data")
                    $state.go('login');
                }
            }

        },
    };
});