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
    <!-- EOF CSS INCLUDE -->
</head>
<body>

<div class="login-container">

    <div class="login-box animated fadeInDown">
        <div class="login-logo"></div>
        <div class="login-body">
            <div class="login-title"><strong>Log In</strong> to your account</div>
            <form id="loginForm" class="form-horizontal">
                <div class="form-group">
                    <div class="col-md-12">
                        <input type="text" class="form-control" name="username" placeholder="User Name" required />
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-12">
                        <input type="password" class="form-control" name="password" placeholder="Password" required />
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-12">
                        <label class="check" style="color: #fff">
                            <input type="checkbox" name="rememberMe" class="icheckbox" checked="checked"/> Remember Me
                        </label>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-6">
                        <a href="#" class="btn btn-link btn-block">Forgot your password?</a>
                    </div>
                    <div class="col-md-6">
                        <button type="submit" class="btn btn-info btn-block">Log In</button>
                    </div>
                </div>
                <div class="login-or">OR</div>
                <div class="form-group">
                    <div class="col-md-4">
                        <button type="button" class="btn btn-info btn-block btn-twitter">
                            <span class="fa fa-twitter"></span> Twitter
                        </button>
                    </div>
                    <div class="col-md-4">
                        <button type="button" class="btn btn-info btn-block btn-facebook">
                            <span class="fa fa-facebook"></span> Facebook
                        </button>
                    </div>
                    <div class="col-md-4">
                        <button type="button" class="btn btn-info btn-block btn-google">
                            <span class="fa fa-google-plus"></span> Google
                        </button>
                    </div>
                </div>
                <div class="login-subtitle">
                    Don't have an account yet? <a href="/register">Create an account</a>
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

<!-- Form Đăng Nhập -->
<form id="loginForm">
    <input type="text" name="username" placeholder="Username" required />
    <input type="password" name="password" placeholder="Password" required />
    <input type="checkbox" name="rememberMe" /> Remember Me
    <button type="submit">Login</button>
</form>

<!-- Nút Đăng Xuất -->
<button id="logoutBtn" style="display: none;">Logout</button>

<script>
    // Xử lý Đăng Nhập
    document.getElementById('loginForm').addEventListener('submit', async function (event) {
        event.preventDefault(); // Ngăn chặn việc gửi form theo cách mặc định

        const username = document.querySelector('input[name="username"]').value;
        const password = document.querySelector('input[name="password"]').value;
        const rememberMe = document.querySelector('input[name="rememberMe"]').checked;

        const loginData = {
            username: username,
            password: password,
            rememberMe: rememberMe
        };

        try {
            const response = await fetch('/auth/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(loginData)
            });

            if (response.ok) {
                const data = await response.json();
                const accessToken = data.accessToken;
                const refreshToken = data.refreshToken;

                // Lưu token vào localStorage
                localStorage.setItem('accessToken', accessToken);
                localStorage.setItem('refreshToken', refreshToken);

                // Hiện nút Logout
                document.getElementById('logoutBtn').style.display = 'block';

                // Điều hướng đến trang chính
                window.location.href = '/home';
            } else {
                const errorData = await response.json();
                alert(errorData.message || 'Login Failure!');
            }
        } catch (error) {
            console.error('Error:', error);
            alert('An error occurred while logging in. Please try again.');
        }
    });

    const params = new URLSearchParams(window.location.search);
    const username = params.get('username');
    const password = params.get('password');
    if (username) {
        document.querySelector('input[name="username"]').value = username;
    }
    if (password) {
        document.querySelector('input[name="password"]').value = password;
    }

</script>


</body>
</html>
