<div class="body" >
    <div class="login-container" id="login-container" ng-class="{'right-panel-active': ctrl.isSignUp}">
        <div class="login-form-container sign-up-container" ng-show="ctrl.isSignUp">
            <form class="login-form" ng-submit="ctrl.registerUser()" name="registerForm">
                <h1 class="login-h1">Create Account</h1>
                <div class="social-container">
                    <a class="login-a" href="#" class="social"><i class="fab fa-facebook-f"></i></a>
                    <a class="login-a" href="#" class="social"><i class="fab fa-linkedin-in"></i></a>
                    <p class="social login-a">
                        <div id="g_id_onload"
                             data-client_id="{{ctrl.clientId}}"
                             data-callback="onGoogleLoginSuccess"
                             data-auto_prompt="false">
                        </div>
                        <div class="g_id_signin" data-type="standard"></div>
                    </p>

                </div>
                <span class="login-span">or use your email for registration</span>
                <input class="login-input" ng-model="ctrl.register.userName" type="text" placeholder="Name" />
                <input class="login-input" ng-model="ctrl.register.userEmail" type="email" placeholder="Email" />
                <input class="login-input" ng-model="ctrl.register.password" type="password" placeholder="Password" />
                <input class="login-input" ng-model="ctrl.confirmPass" type="password" placeholder="Confirm Password" />
                <div class="g-recaptcha" data-sitekey="6LfWglUqAAAAAAZ-U4V-SjJeXBYG5q9D5-e7b1J-"></div>
                <button class="login-button" type="submit">Sign Up</button>
            </form>
        </div>
        <div class="login-form-container sign-in-container" ng-show="!ctrl.isSignUp">
            <form class="login-form" name="registerForm" ng-submit="ctrl.loginUser()">
                <h1 class="login-h1">Sign in</h1>
                <div class="social-container">
                    <a class="login-a" href="#" class="social"><i class="fab fa-facebook-f"></i></a>
                    <a class="login-a" href="#" class="social"><i class="fab fa-linkedin-in"></i></a>
                    <p class="login-a social">
                        <div id="g_id_onload"
                             data-client_id="{{ctrl.clientId}}"
                             data-callback="onGoogleLoginSuccess"
                             data-auto_prompt="false">
                        </div>
                        <div class="g_id_signin" data-type="standard"></div>
                    </p>
                </div>
                <span class="login-span">or use your account</span>
                <input class="login-input" ng-model="ctrl.login.username" type="email" placeholder="Email" />
                <input class="login-input" ng-model="ctrl.login.password" type="password" placeholder="Password" />
                <div class="login-rememberMe">
                    <input class="login-checkbox" ng-model="ctrl.login.rememberMe" type="checkbox" name="rememberMe"/>
                    <label class="login-label" for="rememberMe">Remember Me</label>
                </div>
                <a class="login-a" href="#">Forgot your password?</a>
                <button class="login-button" type="submit">Sign In</button>
            </form>
        </div>
        <div class="overlay-container">
            <div class="overlay">
                <div class="overlay-panel overlay-left" >
                    <h1 class="login-h1">Welcome Back!</h1>
                    <p class="login-p">To keep connected with us please login with your personal info</p>
                    <button class="login-button ghost" id="signIn" ng-click="ctrl.toggleForm(false)">Sign In</button>
                </div>
                <div class="overlay-panel overlay-right">
                    <h1 class="login-h1">Hello, Friend!</h1>
                    <p class="login-p">Enter your personal details and start journey with us</p>
                    <button class="login-button ghost" id="signUp" ng-click="ctrl.toggleForm(true)">Sign Up</button>
                </div>
            </div>
        </div>
    </div>
</div>
