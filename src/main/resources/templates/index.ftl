<!DOCTYPE html>
<html lang="en" ng-app="app">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="referrer" content="no-referrer-when-downgrade">
    <meta name="Cross-Origin-Opener-Policy" content="same-origin-allow-popups">
    <title>Shop Linh Lung</title>

    <link href="/img/favicon.ico" rel="icon">
    <link href="/css/crawl-page-style.css" rel="stylesheet">
    <link href="/css/login-style.css" rel="stylesheet">
    <link href="/css/crawl-data-style.css" rel="stylesheet">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
    <link href="/lib/animate/animate.min.css" rel="stylesheet">
    <link href="/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
    <link href="/css/style.css" rel="stylesheet">

    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular.min.js"></script>
    <script src="/js/app/lib/angular-ui-router.js"></script>
    <script src="/js/app/app.js"></script>
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<#--    <script src="/js/login-js.js"></script>-->
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>
    <script src="/lib/easing/easing.min.js"></script>
    <script src="/lib/owlcarousel/owl.carousel.min.js"></script>
    <script src="mail/jqBootstrapValidation.min.js"></script>
    <script src="mail/contact.js"></script>
    <script src="/js/main.js"></script>
    <script src="/js/app/navbar-directive.js"></script>
    <script src="/js/app/footer-directive.js"></script>
    <script src="/js/app/loginController.js"></script>
    <script src="/js/app/HomeController.js"></script>
    <script src="/js/app/CrawlController.js"></script>
    <script src="/js/app/CrawlDataController.js"></script>
    <script src="/js/app/LogDataController.js"></script>
    <script src="/js/app/Log2Controller.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script src="https://accounts.google.com/gsi/client" async defer></script>
</head>
<body>
<navbar></navbar>
<div class="main-data" ui-view></div>
<#--<footer-template></footer-template>-->
<#--<footer>-->
<#--    <!-- Footer Start &ndash;&gt;-->
<#--    <div class="container-fluid bg-dark text-secondary mt-5 pt-5">-->
<#--        <div class="row px-5 pt-5">-->
<#--            <div class="col-md-3 mb-5">-->
<#--                <h5 class="text-secondary text-uppercase mb-4">Quick Shop</h5>-->
<#--                <div class="d-flex flex-column justify-content-start">-->
<#--                    <a class="text-secondary mb-2" ui-sref="home"><i class="fa fa-angle-right mr-2"></i>Home</a>-->
<#--                    <a class="text-secondary mb-2" ui-sref="crawl"><i class="fa fa-angle-right mr-2"></i>Crawl</a>-->
<#--                    <a class="text-secondary mb-2" ui-sref="crawl-data"><i class="fa fa-angle-right mr-2"></i>Crawl Data</a>-->
<#--                    <a class="text-secondary mb-2" ui-sref="home"><i class="fa fa-angle-right mr-2"></i>Shopping Cart</a>-->
<#--                    <a class="text-secondary mb-2" ui-sref="home"><i class="fa fa-angle-right mr-2"></i>Checkout</a>-->
<#--                    <a class="text-secondary" ui-sref="log-data"><i class="fa fa-angle-right mr-2"></i>Log</a>-->
<#--                </div>-->
<#--            </div>-->
<#--            <div class="col-lg-9 col-md-12">-->
<#--                <div class="row">-->
<#--                    <div class="col-lg-6 col-md-8 mb-5 pr-3 pr-xl-5">-->
<#--                        <h5 class="text-secondary text-uppercase mb-4">Get In Touch</h5>-->
<#--                        <p class="mb-4">No dolore ipsum accusam no lorem. Invidunt sed clita kasd clita et et dolor sed dolor. Rebum tempor no vero est magna amet no</p>-->
<#--                        <p class="mb-2"><i class="fa fa-map-marker-alt text-primary mr-3"></i>123 Street, New York, USA</p>-->
<#--                        <p class="mb-2"><i class="fa fa-envelope text-primary mr-3"></i>info@example.com</p>-->
<#--                        <p class="mb-0"><i class="fa fa-phone-alt text-primary mr-3"></i>+012 345 67890</p>-->
<#--                    </div>-->
<#--                    <div class="col-md-5 mb-5">-->
<#--                        <h5 class="text-secondary text-uppercase mb-4">Newsletter</h5>-->
<#--                        <p>Duo stet tempor ipsum sit amet magna ipsum tempor est</p>-->
<#--                        <form action="">-->
<#--                            <div class="input-group">-->
<#--                                <input type="text" class="form-control" placeholder="Your Email Address">-->
<#--                                <div class="input-group-append">-->
<#--                                    <button class="btn btn-primary">Sign Up</button>-->
<#--                                </div>-->
<#--                            </div>-->
<#--                        </form>-->
<#--                        <h6 class="text-secondary text-uppercase mt-4 mb-3">Follow Us</h6>-->
<#--                        <div class="d-flex">-->
<#--                            <a class="btn btn-primary btn-square mr-2" href="#"><i class="fab fa-twitter"></i></a>-->
<#--                            <a class="btn btn-primary btn-square mr-2" href="#"><i class="fab fa-facebook-f"></i></a>-->
<#--                            <a class="btn btn-primary btn-square mr-2" href="#"><i class="fab fa-linkedin-in"></i></a>-->
<#--                            <a class="btn btn-primary btn-square" href="#"><i class="fab fa-instagram"></i></a>-->
<#--                        </div>-->
<#--                    </div>-->
<#--                </div>-->
<#--            </div>-->
<#--        </div>-->
<#--        <div class="row border-top mx-xl-5 py-4" style="border-color: rgba(256, 256, 256, .1) !important;">-->
<#--            <div class="col-md-6 px-xl-0">-->
<#--                <p class="mb-md-0 text-center text-md-left text-secondary">-->
<#--                    &copy; <a class="text-primary" href="#">Domain</a>. All Rights Reserved. Designed-->
<#--                    by-->
<#--                    <a class="text-primary" href="https://htmlcodex.com">HTML Codex</a>-->
<#--                </p>-->
<#--            </div>-->
<#--            <div class="col-md-6 px-xl-0 text-center text-md-right">-->
<#--                <img class="img-fluid" src="img/payments.png" alt="">-->
<#--            </div>-->
<#--        </div>-->
<#--    </div>-->
<#--    <!-- Footer End &ndash;&gt;-->
<#--</footer>-->
</body>
</html>