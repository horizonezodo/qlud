<!DOCTYPE html>
<html lang="en" class="body-full-height">
<head>
    <!-- META SECTION -->
    <title>Joli Admin - Responsive Bootstrap Admin Template</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />

    <link rel="icon" href="/favicon.ico" type="image/x-icon" />
    <!-- END META SECTION -->

    <!-- CSS INCLUDE -->
    <link rel="stylesheet" type="text/css" id="theme" href="/css/theme-default.css"/>
    <link rel="stylesheet" type="text/css" href="/css/style.css"/>
    <!-- EOF CSS INCLUDE -->
</head>
<body>

<div class="login-container lightmode">

    <div id="dialog" class="dialog" style="display: none">
        <div class="dialog-content">
            <span class="close-button">&times;</span>
            <h2 id="dialog-title">Dialog Title</h2>
            <p id="dialog-message">This is a simple dialog box!</p>
            <button id="confirm-button">OK</button>
        </div>
    </div>

    <div class="login-box animated fadeInDown">
        <div class="login-logo"></div>
        <div class="login-body">
            <div class="login-title"><strong>Create</strong> your account</div>
            <form id="registrationForm" class="form-horizontal" method="post">
                <div class="form-group">
                    <div class="col-md-12">
                        <input type="text" class="form-control" name="username" placeholder="Username"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-12">
                        <input type="text" class="form-control" name="email" placeholder="E-mail"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-12">
                        <input type="text" class="form-control" name="phone" placeholder="Phone Number"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-12">
                        <input type="password" class="form-control" name="password" placeholder="Password"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-12">
                        <input type="password" class="form-control" name="cofirm-password" placeholder="Cofirm Password"/>
                    </div>
                </div>
                <div class="form-group">
                    <div style="margin-left: 15px" class="g-recaptcha" data-sitekey="6LfWglUqAAAAAAZ-U4V-SjJeXBYG5q9D5-e7b1J-"></div>
                </div>
                <div class="form-group">
                    <div class="col-md-6">
                        <a href="/" class="btn btn-link btn-block">You have account</a>
                    </div>
                    <div class="col-md-6">
                        <button type="submit" class="btn btn-info btn-block">Register</button>
                    </div>
                </div>
                <div class="login-or">OR</div>
                <div class="form-group">
                    <div class="col-md-4">
                        <button class="btn btn-info btn-block btn-twitter"><span class="fa fa-twitter"></span> Twitter</button>
                    </div>
                    <div class="col-md-4">
                        <button class="btn btn-info btn-block btn-facebook"><span class="fa fa-facebook"></span> Facebook</button>
                    </div>
                    <div class="col-md-4">
                        <button class="btn btn-info btn-block btn-google"><span class="fa fa-google-plus"></span> Google</button>
                    </div>
                </div>
            </form>
        </div>
        <div class="login-footer">
            <div class="pull-left">
                &copy; 2014 AppName
            </div>
            <div class="pull-right">
                <a href="#">About</a> |
                <a href="#">Privacy</a> |
                <a href="#">Contact Us</a>
            </div>
        </div>
    </div>

</div>

<#--Xử lý sau khi người dùng đăng nhập-->
<script>
    document.addEventListener('DOMContentLoaded', function() {
        document.getElementById('registrationForm').addEventListener('submit', async function(event) {
            event.preventDefault();
            const recaptchaResponse = grecaptcha.getResponse();
            if (!recaptchaResponse) {
                showDialog("Đăng ký thất bại", "Please complete reCaptcha");
                return;
            }
            const username = document.querySelector('input[name="username"]').value;
            const email = document.querySelector('input[name="email"]').value;
            const phone = document.querySelector('input[name="phone"]').value;
            const password = document.querySelector('input[name="password"]').value;
            const confirmPassword = document.querySelector('input[name="cofirm-password"]').value;

            if (password !== confirmPassword) {
                showDialog("Đăng ký thất bại", "Mật khẩu không khớp")
                return;
            }
            const registerData = {
                userName: username,
                userEmail: email,
                password: password,
                phoneNumber: phone,
                userRole:'user',
                recaptchaToken: recaptchaResponse
            };
            try{
            const response = await fetch('/auth/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(registerData)
            });

                if (response.ok) {
                    window.location.href = `/?username=` + email;
                } else {
                    const errorData = await response.json();
                    showDialog("Đăng ký thất bại", errorData.message)
                }
            } catch (error) {
                console.error('Error:', error);
                showDialog("Đã xảy ra lỗi ", error)
            }
        });
    });
</script>

<script src='https://www.google.com/recaptcha/api.js'></script>
<script src='/js/dialog.js'></script>
</body>
</html>






