<!DOCTYPE html>
<html>

<head>
    <!-- Basic -->
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <!-- Mobile Metas -->
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <!-- Site Metas -->
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <meta name="author" content="" />

    <title>Fonicy</title>


    <!-- bootstrap core css -->
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.css" />

    <!-- fonts style -->
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700;900&display=swap" rel="stylesheet">

    <!--owl slider stylesheet -->
    <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.carousel.min.css" />

    <!-- font awesome style -->
    <link href="/css/font-awesome.min.css" rel="stylesheet" />

    <!-- Custom styles for this template -->
    <link href="/css/style.css" rel="stylesheet" />
    <!-- responsive style -->
    <link href="/css/responsive.css" rel="stylesheet" />

</head>

<body class="sub_page">

<div class="hero_area">
    <!-- header section strats -->
    <header class="header_section">
        <div class="header_top">
            <div class="container-fluid header_top_container">
                <div class="lang_box dropdown">
                    <a href="#" title="Language" class="nav-link" data-toggle="dropdown" aria-expanded="true">
                        <img src="/img/flag-uk.png" alt="flag" class=" " title="United Kingdom"> <i class="fa fa-angle-down " aria-hidden="true"></i>
                    </a>
                    <div class="dropdown-menu ">
                        <a href="#" class="dropdown-item">
                            <img src="/img/flag-france.png" class="" alt="flag">
                        </a>
                    </div>
                    <span>
              English
            </span>
                </div>
                <div class="contact_nav">
                    <a href="">
                        <i class="fa fa-phone" aria-hidden="true"></i>
                        <span>
                Call : +01 123455678990
              </span>
                    </a>
                    <a href="">
                        <i class="fa fa-envelope" aria-hidden="true"></i>
                        <span>
                Email : demo@gmail.com
              </span>
                    </a>
                    <a href="">
                        <i class="fa fa-map-marker" aria-hidden="true"></i>
                        <span>
                Location
              </span>
                    </a>
                </div>
                <div class="social_box">
                    <a href="">
                        <i class="fa fa-facebook" aria-hidden="true"></i>
                    </a>
                    <a href="">
                        <i class="fa fa-twitter" aria-hidden="true"></i>
                    </a>
                    <a href="">
                        <i class="fa fa-linkedin" aria-hidden="true"></i>
                    </a>
                    <a href="">
                        <i class="fa fa-instagram" aria-hidden="true"></i>
                    </a>
                </div>
            </div>
        </div>
        <div class="header_bottom">
            <div class="container-fluid">
                <nav class="navbar navbar-expand-lg custom_nav-container ">
                    <a class="navbar-brand" href="/home">
                        <img src="/img/logo.png" alt="">
                    </a>
                    </a>

                    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                        <span class=""> </span>
                    </button>

                    <div class="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul class="navbar-nav  ">
                            <li class="nav-item ">
                                <a class="nav-link" href="/home">Home <span class="sr-only">(current)</span></a>
                            </li>
                            <li class="nav-item active">
                                <a class="nav-link" href="/app-show-crawl">Crawl Service</a>
                            </li>
                            <li class="nav-item active">
                                <a class="nav-link" href="/show-all-product">Crawl Data</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="/home"> About</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="/contact">Contact Us</a>
                            </li>
                            <form class="form-inline">
                                <button class="btn  my-2 my-sm-0 nav_search-btn" type="submit">
                                    <i class="fa fa-search" aria-hidden="true"></i>
                                </button>
                            </form>
                        </ul>
                        <div class="quote_btn-container">
                            <a href="#" class="quote_btn" id="logoutBtn">
                                Logout
                            </a>
                        </div>
                    </div>
                </nav>
            </div>
        </div>
    </header>
    <!-- end header section -->
</div>


<!-- service section -->

<section class="contact_section layout_padding">
    <div class="container-fluid">
        <div class="col-lg-4 col-md-5 offset-md-1">
            <div class="heading_container">
                <h2>
                    Crawl Service
                </h2>
            </div>
        </div>
        <div >
            <div >
                <div class="form_container">
                    <form id="crawlForm">
                        <div class="form_group">
                            <input type="text" id="websiteUrl" placeholder="Website Url" />
                        </div>
                        <div class="form_group">
                            <button type="submit">CRAWL</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="product-form" style="display:none;" id="productDetails">
            <h2>Product Details</h2>
            <div class="form-field">
                <label for="pTitle">Title:</label>
                <input type="text" id="pTitle" name="pTitle" placeholder="Enter product title" readonly>
            </div>
            <div class="form-field">
                <label for="pBrand">Brand:</label>
                <input type="text" id="pBrand" name="pBrand" placeholder="Enter brand" readonly>
            </div>
            <div class="form-field">
                <label for="pStype">Style:</label>
                <input type="text" id="pStype" name="pStype" placeholder="Enter style" readonly>
            </div>
            <div class="form-field">
                <label for="pColor">Color:</label>
                <input type="text" id="pColor" name="pColor" placeholder="Enter color" readonly>
            </div>
            <div class="form-field">
                <label for="pPrice">Price:</label>
                <input type="text" id="pPrice" name="pPrice" placeholder="Enter price" readonly>
            </div>
            <div class="form-field">
                <label for="pMaterial">Material:</label>
                <input type="text" id="pMaterial" name="pMaterial" readonly>
            </div>
            <div class="form-field">
                <label for="pProductType">Product Type:</label>
                <input type="text" id="pProductType" name="pProductType"  readonly>
            </div>
            <div class="form-field">
                <label for="pRelease">Product Release:</label>
                <input type="text" id="pRelease" name="pRelease" readonly>
            </div>
            <div class="form-field">
                <label for="pSaleArea">Product Sale Area:</label>
                <input type="text" id="pSaleArea" name="pSaleArea" readonly>
            </div>
            <div class="form-field">
                <label for="pSize">Product Size:</label>
                <input type="text" id="pSize" name="pSize" readonly>
            </div>

            <h3>Other Products</h3>
            <table class="product-table">
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Image</th>
                    <th>Price</th>
                    <th>Available Count</th>
                    <th>Sale Count</th>
                </tr>
                </thead>
                <tbody id="otherProducts">
                </tbody>
            </table>
        </div>
    </div>
</section>
<!-- end contact section -->

<!-- info section -->
<section class="info_section ">

    <div class="container">
        <div class="contact_nav">
            <a href="">
                <i class="fa fa-phone" aria-hidden="true"></i>
                <span>
            Call : +01 123455678990
          </span>
            </a>
            <a href="">
                <i class="fa fa-envelope" aria-hidden="true"></i>
                <span>
            Email : demo@gmail.com
          </span>
            </a>
            <a href="">
                <i class="fa fa-map-marker" aria-hidden="true"></i>
                <span>
            Location
          </span>
            </a>
        </div>

        <div class="info_top ">
            <div class="row info_main_row">
                <div class="col-sm-6 col-md-4 col-lg-3">
                    <div class="info_links">
                        <h4>
                            QUICK LINKS
                        </h4>
                        <div class="info_links_menu">
                            <a class="" href="/home">Home <span class="sr-only">(current)</span></a>
                            <a class="" href="/app-show-crawl">Crawl Service</a>
                            <a class="" href="/show-all-product">Crawl Data</a>
                            <a class="" href="/home"> About</a>
                            <a class="" href="/home">Contact Us</a>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-md-4 col-lg-3 mx-auto">
                    <div class="info_post">
                        <h5>
                            INSTAGRAM FEEDS
                        </h5>
                        <div class="post_box">
                            <div class="img-box">
                                <img src="/img/instagram.jpg" alt="">
                            </div>
                            <div class="img-box">
                                <img src="/img/instagram.jpg" alt="">
                            </div>
                            <div class="img-box">
                                <img src="/img/instagram.jpg" alt="">
                            </div>
                            <div class="img-box">
                                <img src="/img/instagram.jpg" alt="">
                            </div>
                            <div class="img-box">
                                <img src="/img/instagram.jpg" alt="">
                            </div>
                            <div class="img-box">
                                <img src="/img/instagram.jpg" alt="">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="info_form">
                        <h4>
                            SIGN UP TO OUR NEWSLETTER
                        </h4>
                        <form action="">
                            <input type="text" placeholder="Enter Your Email" />
                            <button type="submit">
                                Subscribe
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <div class="info_bottom">
            <div class="row">
                <div class="col-md-2">
                    <div class="info_logo">
                        <a href="">
                            <img src="/img/logo2.png" alt="">
                        </a>
                    </div>
                </div>
                <div class="col-md-4 ml-auto">
                    <div class="social_box">
                        <a href="">
                            <i class="fa fa-facebook" aria-hidden="true"></i>
                        </a>
                        <a href="">
                            <i class="fa fa-twitter" aria-hidden="true"></i>
                        </a>
                        <a href="">
                            <i class="fa fa-linkedin" aria-hidden="true"></i>
                        </a>
                        <a href="">
                            <i class="fa fa-instagram" aria-hidden="true"></i>
                        </a>
                    </div>
                </div>
            </div>

        </div>
    </div>
</section>
<!-- end info_section -->

<!-- footer section -->
<footer class="footer_section">
    <div class="container">
        <p>
            &copy; <span id="displayYear"></span> All Rights Reserved By
            <a href="https://html.design/">Free Html Templates</a>
        </p>
    </div>
</footer>
<!-- footer section -->

<!-- jQery -->
<script src="/js/jquery-3.4.1.min.js"></script>
<!-- popper js -->
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous">
</script>
<!-- bootstrap js -->
<script src="/js/bootstrap.js"></script>
<!-- owl slider -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/owl.carousel.min.js">
</script>
<!--  OwlCarousel 2 - Filter -->
<script src="https://huynhhuynh.github.io/owlcarousel2-filter/dist/owlcarousel2-filter.min.js"></script>
<!-- custom js -->
<script src="/js/custom.js"></script>
<!-- Google Map -->
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCh39n5U-4IoWpsVGUHWdqB6puEkhRLdmI&callback=myMap">
</script>
<!-- End Google Map -->
<#--script for crawl btn-->
<script>
    const accessToken = localStorage.getItem("accessToken");
    document.getElementById('crawlForm').addEventListener('submit', function(event) {
        event.preventDefault();

        // Lấy URL từ input
        const websiteUrl = document.getElementById('websiteUrl').value;
        console.log('URL nhập vào:', websiteUrl);
        const CrawlData = {
            crawlUrl: websiteUrl,
        };

        // Gửi request tới backend
        fetch('/app/crawl', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer `+accessToken
            },
            body: JSON.stringify({ CrawlData })
        })
            .then(response => response.json())
            .then(data => {
                const priceData= data.price.map(p => p.price + ' for ' + p.priceAmount).join(' - ');
                document.getElementById('pTitle').value = data.productId || '';
                document.getElementById('pBrand').value = data.productBrand || '';
                document.getElementById('pStype').value = data.productModel || '';
                document.getElementById('pColor').value = data.productColor || '';
                document.getElementById('pPrice').value = priceData || '';
                document.getElementById('pMaterial').value = data.material || '';
                document.getElementById('pProductType').value = data.productType || '';
                document.getElementById('pSaleArea').value = data.productSaleArea || '';
                document.getElementById('pRelease').value = data.productRelease || '';
                document.getElementById('pSize').value = data.productSize || '';

                const otherProductsTable = document.getElementById('otherProducts');
                otherProductsTable.innerHTML = '';
                data.colors.forEach(product => {
                    const priceToDisplay = (product.price === '0.00' || product.price === 0) ? priceData : product.price;
                    const row = `<tr>
                            <td>` + product.name + `</td>
                            <td><img src="`+product.imageUrl +`" alt=" ` + product.name+`" style="width: 50px; height: 50px;" /></td>
                            <td>` + priceToDisplay+ `</td>
                            <td>`+product.remainingProducts+`</td>
                            <td>`+product.saleProductCount+`</td>
                        </tr>`;
                    otherProductsTable.insertAdjacentHTML('beforeend', row);
                });

                // Hiển thị phần product-form
                document.getElementById('productDetails').style.display = 'block';
            })
            .catch(error => console.error('Error:', error));
    });

</script>
<#--Script for logout btn-->
<script>
    document.getElementById('logoutBtn').addEventListener('click', function(event) {
        event.preventDefault(); // Ngăn chặn hành động mặc định của liên kết

        const accessToken = localStorage.getItem("accessToken");

        // Kiểm tra xem accessToken có tồn tại không
        if (!accessToken) {
            console.error("accessToken is missing");
            return; // Ngăn không cho tiếp tục nếu accessToken không tồn tại
        }

        // Gọi API logout
        fetch('/auth/logout', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer `+accessToken
            },
            body: JSON.stringify({})
        })
            .then(response => {
                if (response.ok) {
                    // Xóa token khỏi localStorage
                    localStorage.removeItem("accessToken");
                    localStorage.removeItem("refreshToken");
                    console.log('Logout successful');
                    window.location.href = '/'; // Chuyển hướng về trang chính
                } else {
                    console.error('Logout failed');
                }
            })
            .catch(error => {
                console.error('Error:', error);
            });
    });
</script>
</body>

</html>