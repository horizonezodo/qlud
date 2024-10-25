'use strict';
angular.module('app').
factory('loginService', ['$http', '$q',function ($http,$q){
    const factory = {
        loginUser : loginUser,
        registerUser : registerUser,
    };

    function loginUser(login){
        console.log('Login User')
        console.log(login)
        const deferred = $q.defer();
        $http.post('http://192.168.113.231:8080/auth/login', login)
            .then(
                function (res){
                    console.log('Login success');
                    console.log(res);
                    deferred.resolve(res.data);
                },
                function (err){
                    console.error('Login Failure: ' + err.data.message);
                    deferred.reject(err.data);
                }
            );
        return deferred.promise;
    };

    function registerUser(register){
        console.log('register user')
        console.log(register)
        const deferrer = $q.defer();
        $http.post('http://192.168.113.231:8080/auth/register', register)
            .then(
                function (res){
                    console.log('register success')
                    console.log(res)
                },
                function (err){
                    console.error('Register Failure: ' + err.data.message);
                    deferrer.reject(err)
                }
            );
        return deferrer.promise;
    };

    return factory;
}]).
controller('loginController', ['loginService','dialogService','$scope','$window','$http','$state','$rootScope',function(loginService,dialogService,$scope,$window,$http,$state,$rootScope) {
    const self = this;

    const reCAPTCHA = document.createElement('script');
    reCAPTCHA.src='https://www.google.com/recaptcha/api.js';
    reCAPTCHA.async=true;
    reCAPTCHA.defer=true;
    document.body.appendChild(reCAPTCHA);
    const script = document.createElement('script');
    script.src="https://accounts.google.com/gsi/client"
    script.defer=true;
    script.async=true;
    document.body.appendChild(script)
    const style = document.createElement('link');
    style.href='/css/login-style.css';
    style.rel='stylesheet'
    document.head.appendChild(style)


    self.isSignUp = false;
    self.toggleForm = function(isSignUp) {
        console.log('click')
        self.isSignUp = isSignUp;
    };

    self.register = {
        userName: '',
        userEmail: '',
        password: '',
        phoneNumber:'',
        userRole: 'user',
        recaptchaToken:''
    }

    self.confirmPass = '';

    self.login ={
        username : '',
        password: '',
        rememberMe: true
    }

    self.loginUser = loginUser;
    self.registerUser = registerUser;

    function loginUser(login){
        console.log(self.login)
        loginService.loginUser(self.login).then(
            function (response){
                console.log('login success data response');
                console.log(response);
                localStorage.setItem("accessToken", response.accessToken);
                localStorage.setItem("refreshToken", response.refreshToken);
                localStorage.setItem("username", response.userName);
                $rootScope.$broadcast('userLoggedIn');
                if(localStorage.getItem("path")){
                    window.location.href=localStorage.getItem("path");
                    localStorage.removeItem("path");
                }else{
                    window.location.href="/";
                }
            },
            function (err){
                console.log('login failure');
                dialogService.showErrorDialog("Login Failure","Username or Password is not correct");
            }
        )
    }

    function registerUser(register){
        console.log(self.register);
        if(self.register.password === self.confirmPass){
            const recaptchaResponse = grecaptcha.getResponse();
            if (!recaptchaResponse) {
                dialogService.showErrorDialog("Register Failure", 'Please complete reCaptcha');
            }
            self.register.recaptchaToken = recaptchaResponse;
            loginService.registerUser(self.register).then(
                function (res){
                    console.log('register success');
                    console.log(res)
                    $scope.registerForm.$setPristine();
                    self.isSignUp = false;
                    dialogService.showSuccessDialog("Success","Welcome to Us!")
                },
                function (err){
                    console.log('register failure');
                    console.log(err)
                    dialogService.showErrorDialog("Register Failure", err.data.message);
                }
            )
        }else{
            dialogService.showErrorDialog("Register Failure", 'Password does not match');
        }
    }


    self.clientId = '224739075686-l7flm7piipgsb6vb67b42kq23hhqp07o.apps.googleusercontent.com';
    window.onGoogleLoginSuccess = function (response){
        const token = response.credential

        angular.element(document.body).injector().get('$http').post('http://http://192.168.113.231:8080/auth/oauth-2-success', {token: token})
            .then(function (response){
                console.log("Success: ", response.data);
                localStorage.setItem('accessToken', response.data.accessToken);
                localStorage.setItem('refreshToken', response.data.refreshToken);
                localStorage.setItem('username', response.data.userName)
                window.location.href = '/';
            }).catch(function(error) {
            console.error('Error during login:', error);
            dialogService.showErrorDialog("Login with google failure", error);
        });
    }
}]);