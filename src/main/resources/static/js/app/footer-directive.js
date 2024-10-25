angular.module('app').directive('footerTemplate', function() {
    return{
        restrict: 'E',
        templateUrl: 'partials/footer',
        controller: function ($scope,$location) {
            $scope.goHome = function (){
                $location.path('/');
            }

            $scope.goCrawl = function (){
                $location.path('/crawl');
            }

            $scope.goCrawlData = function (){
                $location.path('/crawl-data');
            }

            $scope.goLog = function (){
                $location.path('/log-data');
            }
        }
    }
});