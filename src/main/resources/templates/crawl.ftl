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
    <link rel="stylesheet" type="text/css" href="/css/style.css"/>

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
                            <a href="/auth/logout" class="quote_btn" id="logoutBtn">
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
                            <input type="text" id="cookieData" placeholder="Cookie" />
                        </div>
                        <div class="form_group">
                            <button type="submit">CRAWL</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="product-form" id="productDetails" style="display:none;">
            <div class="upper-layout">
                <div class="left-side">
                    <div class="main-image-container">
                        <button id="prevImageBtn">  </button>
<#--                        <img id="mainImage" src="" alt="Product Image" style="width: 300px; height: 300px;">-->
                        <div id="mainImage" style="width: 500px; height: 500px; background-size: cover; background-position: center;"></div>
                        <button id="nextImageBtn">  </button>
                    </div>
                    <div class="thumbnail-container">
                        <button class="prevButton" id="prevButton" style="display: none;">&lt;</button>
                        <div id="thumbnailList"></div>
                        <button class="nextButton" id="nextButton"  style="display: none;">&gt;</button>
                    </div>
                </div>

                <div class="right-side">
                    <div class="product-info">
                        <h2 id="pTitle" class="pTitle"><a id="pLink"></a></h2>
                        <div class="price-display">
                            <p id="priceName"></p>
                            <div class="price" id="priceDiv"></div>
                        </div>
                    </div>
                    <div class="product-color-info" id="product-color-info" style="width: 80%;">
                        <p id="color-name"></p>
                        <div id="color-buttons"></div>
                    </div>
                    <div class="size-info">
                        <p id="size-name"></p>
                        <table class="size-table">
                            <tbody id="sizeDetails"></tbody>
                        </table>
                    </div>
                </div>
            </div>

            <h3><span class="spanColor">|</span><span id="product-table-name"></span></h3>
            <table class="product-table" id="product-table">
                <tbody id="otherProducts"></tbody>
            </table>

            <div class="video-container" id="video-container">
                <h4 id="video-title"></h4>
                <div id="videoDemo">
                </div>
            </div>

            <div class="product-image-detail" id="product-image-detail">

            </div>
        </div>
    </div>

    <div id="dialog" class="dialog" style="display: none">
        <div class="dialog-content">
            <span class="close-button">&times;</span>
            <h2 id="dialog-title">Dialog Title</h2>
            <p id="dialog-message">This is a simple dialog box!</p>
            <button id="confirm-button">OK</button>
        </div>
    </div>
</section>
<!-- end contact section -->

<!-- info section -->
<section class="info_section ">

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
    let currentImageIndex = 0;
    const MAX_IMAGES = 4;
    const MIN_IMAGES = 1;
    let imageList = [];
    document.getElementById('crawlForm').addEventListener('submit', function(event) {
        event.preventDefault();

        const websiteUrl = document.getElementById('websiteUrl').value;
        const cookieData = document.getElementById('cookieData').value;
        const cookieEncodeData = encodeURIComponent(cookieData);
        const CrawlData = {
            crawlUrl: websiteUrl,
            cookieData: cookieEncodeData,
        };
        fetch('/app/crawl', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer `+accessToken
            },
            body: JSON.stringify({ crawlUrl: websiteUrl,cookieData:cookieEncodeData })
        })
            .then(response => {
                if (!response.ok) {
                    return response.json().then(errorData => {
                        showDialog("Error", errorData.message);
                        throw new Error(errorData.message);
                    });
                }
                return response.json()
            })
            .then(data => {
                console.log(data)
                document.getElementById("pLink").innerText = data.productTitle || '';
                document.getElementById("pLink").setAttribute("href",data.link || '');

                document.getElementById("priceName").innerText = data.productPrices.name || '';

                const priceDisplay = (Array.isArray(data.productPrices.originalPrice) && data.productPrices.originalPrice.length > 0)
                    ? data.productPrices.originalPrice
                    : data.productPrices.currentPrice;

                const maxPrice = Math.max(...priceDisplay.map(item => parseFloat(item.price)));

                const priceDiv = document.getElementById("priceDiv");
                priceDiv.innerHTML= ''
                priceDiv.classList.add("priceDiv");
                priceDisplay.forEach(dataPrice => {
                    const priceContent = document.createElement("div")
                    priceContent.classList.add("priceContent")

                    const h2 = document.createElement("p");
                    h2.classList.add('priceInt');
                    h2.innerHTML = "¥ " +  dataPrice.price;

                    const priceConnect = document.createElement("p");
                    priceConnect.classList.add('priceConnect');
                    priceConnect.innerHTML = "&nbsp&nbsp&nbspfor&nbsp&nbsp&nbsp ";

                    const p = document.createElement("p");
                    p.classList.add('priceDetail');
                    p.innerHTML = dataPrice.priceAmount;
                    priceContent.appendChild(h2);
                    priceContent.appendChild(priceConnect)
                    priceContent.appendChild(p);
                    priceDiv.appendChild(priceContent)
                })
                console.log(data.productColorAndSize)
                if(data.productColorAndSize.productColor === null && data.productColorAndSize.productSize === null){
                    console.log("abc")
                        document.getElementById("product-color-info").innerHTML = '';
                        const sizeTable = document.getElementById("sizeDetails");
                        sizeTable.innerHTML = '';
                        const showData = data.productColorAndSize.productInfoMap;
                        showData.forEach(showData => {
                            document.getElementById("size-name").innerText = '采购量';

                            const row = document.createElement("tr");
                            const priceCell = document.createElement("td");
                            const priceData = showData.discountPrice || showData.currentPrice || maxPrice;
                            const colorPrice = priceData + " 元";
                            priceCell.innerText = priceData ? colorPrice : "N/A";
                            priceCell.classList.add("bold-text")
                            row.appendChild(priceCell);

                            const canBookCountCell = document.createElement("td")
                            canBookCountCell.innerText = showData ? showData.canBookCount : "N/A";
                            row.appendChild(canBookCountCell);

                            const saleCountCell = document.createElement("td")
                            if(showData.canBookCount !== 0){
                                saleCountCell.classList.add("saleCountClass")
                                const saleCountDiv = document.createElement("div")
                                saleCountDiv.classList.add("sale-count-div")
                                const decreamentBtn = document.createElement("button")
                                decreamentBtn.classList.add("decreamentBtn")
                                decreamentBtn.id = "decrementBtn"
                                decreamentBtn.innerHTML = "-"

                                const canBookCellText = document.createElement("p");
                                canBookCellText.id = "counterLabel"
                                canBookCellText.classList.add("canBookText")
                                canBookCellText.innerText = showData ? showData.saleCount : "N/A";

                                const incrementBtn = document.createElement("button")
                                incrementBtn.classList.add("incrementBtn")
                                incrementBtn.id = "incrementBtn"
                                incrementBtn.innerHTML = "+"

                                saleCountDiv.appendChild(decreamentBtn)
                                saleCountDiv.appendChild(canBookCellText)
                                saleCountDiv.appendChild(incrementBtn)
                                saleCountCell.appendChild(saleCountDiv)
                            }else{
                                saleCountCell.innerText="缺货";
                            }
                            row.appendChild(saleCountCell);

                            sizeTable.appendChild(row)
                        })
                }
                else {
                    console.log("asa")
                    const sizeTable = document.getElementById("sizeDetails");
                    // sizeTable.innerHTML = '';

                    if(data.productColorAndSize.productColor.colors.length > 0 && data.productColorAndSize.productSize.size.length > 0){
                        console.log("abs")
                        document.getElementById("size-name").innerText = data.productColorAndSize.productSize.name;
                        const otherImageProductDiv = document.getElementById("color-buttons");
                        otherImageProductDiv.innerHTML=''
                        console.log("qua thẻ div")
                        const buttonLabel = document.getElementById("color-name");
                            buttonLabel.innerHTML = '';
                            console.log("qua label")
                            buttonLabel.innerText = data.productColorAndSize.productColor.name;

                        data.productColorAndSize.productColor.colors.forEach(imageOtherProduct => {
                            const button = document.createElement("button");
                            button.classList.add('color-button');
                            if(imageOtherProduct.imageUrl === "" || imageOtherProduct.imageUrl === undefined || imageOtherProduct.imageUrl === null){
                                button.innerHTML = imageOtherProduct.name;
                            }
                            else{
                                button.innerHTML = `<img src="` + imageOtherProduct.imageUrl + `" alt="` + imageOtherProduct.name + `" style="width: 36px; height: 36px; margin-right: 10px"/>` + imageOtherProduct.name;
                            }
                            button.addEventListener("click", () => updateTable(imageOtherProduct.name));
                            otherImageProductDiv.appendChild(button);

                        });

                        function updateTable(selectorColorName) {
                            sizeTable.innerHTML = '';
                            data.productColorAndSize.productSize.size.forEach(size => {
                                const detail = data.productColorAndSize.productInfoMap.find(detail => {
                                    if(detail.name.includes("&gt;")){
                                        const [colorName, sizeName] = detail.name.split('&gt;');
                                        return colorName.trim() === selectorColorName.trim() && sizeName.trim() === size.trim()
                                    }else{
                                        return detail.name.trim() === selectorColorName.trim() || detail.name.trim() === size.trim()
                                    }

                                });

                                const row = document.createElement("tr");
                                const sizeCell = document.createElement("td");
                                sizeCell.classList.add("bold-text")
                                sizeCell.innerText = size;
                                row.appendChild(sizeCell);

                                const priceCell = document.createElement("td")
                                priceCell.classList.add("bold-text")
                                const priceData = detail.discountPrice ? detail.discountPrice : detail.currentPrice;
                                const colorPrice = ((parseFloat(priceData) === 0) ? maxPrice : priceData) + " 元"
                                priceCell.innerText = detail ? colorPrice : "N/A";
                                row.appendChild(priceCell);
                                sizeTable.appendChild(row);

                                const canBookCountCell = document.createElement("td")
                                canBookCountCell.innerText = detail ? detail.canBookCount : "N/A";
                                row.appendChild(canBookCountCell);

                                const saleCountCell = document.createElement("td")
                                if(detail.canBookCount === 0){
                                    saleCountCell.innerText = "缺货";
                                }else{
                                    saleCountCell.classList.add("saleCountClass")
                                    const saleCountDiv = document.createElement("div");
                                    saleCountDiv.classList.add("sale-count-div");
                                    const decreamentBtn = document.createElement("button")
                                    decreamentBtn.classList.add("decreamentBtn")
                                    decreamentBtn.id = "decrementBtn"
                                    decreamentBtn.innerHTML = "-"

                                    const canBookCellText = document.createElement("p");
                                    canBookCellText.id = "counterLabel"
                                    canBookCellText.classList.add("canBookText")
                                    canBookCellText.innerText = detail ? detail.saleCount : "N/A";

                                    const incrementBtn = document.createElement("button")
                                    incrementBtn.classList.add("incrementBtn")
                                    incrementBtn.id = "incrementBtn"
                                    incrementBtn.innerHTML = "+"

                                    saleCountDiv.appendChild(decreamentBtn)
                                    saleCountDiv.appendChild(canBookCellText)
                                    saleCountDiv.appendChild(incrementBtn)
                                    saleCountCell.appendChild(saleCountDiv)
                                }
                                row.appendChild(saleCountCell);

                            });
                        }

                        updateTable(data.productColorAndSize.productColor.colors[0].name);
                    }
                    else if (data.productColorAndSize.productColor.colors.length > 0){
                        document.getElementById("size-name").innerText = data.productColorAndSize.productColor.name;
                            sizeTable.innerHTML = '';
                        document.getElementById("product-color-info").innerHTML = '';
                            const showData = data.productColorAndSize.productColor.colors;
                            showData.forEach(size => {
                                const detail = data.productColorAndSize.productInfoMap.find(detail => {
                                    return detail.name.trim() === size.name.trim();
                                });
                                // console.log("success")
                                const row = document.createElement("tr");
                                const sizeCell = document.createElement("td");
                                sizeCell.classList.add("table-size-name");
                                const sizeName = document.createElement("p")
                                sizeName.classList.add("bold-text")
                                sizeName.innerText = detail.name;
                                const sizeImg = document.createElement("img");
                                sizeImg.classList.add("size-img");
                                sizeImg.src = size.imageUrl;
                                sizeCell.appendChild(sizeImg);
                                sizeCell.appendChild(sizeName);
                                row.appendChild(sizeCell);

                                const priceCell = document.createElement("td")
                                priceCell.classList.add("bold-text")
                                const priceData = detail.discountPrice ? detail.discountPrice : detail.currentPrice;
                                const colorPrice = ((parseFloat(priceData) === 0) ? maxPrice : priceData) + " 元"
                                priceCell.innerText = detail ? colorPrice : "N/A";
                                row.appendChild(priceCell);
                                sizeTable.appendChild(row);

                                const canBookCountCell = document.createElement("td")
                                canBookCountCell.innerText = detail ? detail.canBookCount : "N/A";
                                row.appendChild(canBookCountCell);

                                const saleCountCell = document.createElement("td")
                                if(detail.canBookCount !== 0){
                                    saleCountCell.classList.add("saleCountClass")
                                    const saleCountDiv = document.createElement("div");
                                    saleCountDiv.classList.add("sale-count-div");
                                    const decreamentBtn = document.createElement("button")
                                    decreamentBtn.classList.add("decreamentBtn")
                                    decreamentBtn.id = "decrementBtn"
                                    decreamentBtn.innerHTML = "-"

                                    const canBookCellText = document.createElement("p");
                                    canBookCellText.id = "counterLabel"
                                    canBookCellText.classList.add("canBookText")
                                    canBookCellText.innerText = detail ? detail.saleCount : "N/A";

                                    const incrementBtn = document.createElement("button")
                                    incrementBtn.classList.add("incrementBtn")
                                    incrementBtn.id = "incrementBtn"
                                    incrementBtn.innerHTML = "+"

                                    saleCountDiv.appendChild(decreamentBtn)
                                    saleCountDiv.appendChild(canBookCellText)
                                    saleCountDiv.appendChild(incrementBtn)
                                    saleCountCell.appendChild(saleCountDiv)
                                }else{
                                    saleCountCell.innerText="缺货";
                                }
                                row.appendChild(saleCountCell);

                        })
                    }
                    else{
                        document.getElementById("product-color-info").innerHTML = '';
                        sizeTable.innerHTML = '';
                        data.productColorAndSize.productSize.size.forEach(size => {
                            const detail = data.productColorAndSize.productInfoMap.find(detail => {
                                    return detail.name.trim() === size.trim()
                            });
                            const row = document.createElement("tr");
                            const sizeCell = document.createElement("td");
                            sizeCell.classList.add("bold-text")
                            sizeCell.innerText = size;
                            row.appendChild(sizeCell);

                            const priceCell = document.createElement("td")
                            priceCell.classList.add("bold-text")
                            const priceData = detail.discountPrice ? detail.discountPrice : detail.currentPrice;
                            const colorPrice = ((parseFloat(priceData) === 0) ? maxPrice : priceData) + " 元"
                            priceCell.innerText = detail ? colorPrice : "N/A";
                            row.appendChild(priceCell);
                            sizeTable.appendChild(row);

                            const canBookCountCell = document.createElement("td")
                            canBookCountCell.innerText = detail ? detail.canBookCount : "N/A";
                            row.appendChild(canBookCountCell);

                            const saleCountCell = document.createElement("td");
                            if(detail.canBookCount !== 0){
                                saleCountCell.classList.add("saleCountClass");
                                const saleCountDiv = document.createElement("div");
                                saleCountDiv.classList.add("sale-count-div");
                                const decreamentBtn = document.createElement("button");
                                decreamentBtn.classList.add("decreamentBtn");
                                decreamentBtn.id = "decrementBtn";
                                decreamentBtn.innerHTML = "-";

                                const canBookCellText = document.createElement("p");
                                canBookCellText.id = "counterLabel";
                                canBookCellText.classList.add("canBookText");
                                canBookCellText.innerText = detail ? detail.saleCount : "N/A";

                                const incrementBtn = document.createElement("button");
                                incrementBtn.classList.add("incrementBtn");
                                incrementBtn.id = "incrementBtn";
                                incrementBtn.innerHTML = "+";

                                saleCountDiv.appendChild(decreamentBtn);
                                saleCountDiv.appendChild(canBookCellText);
                                saleCountDiv.appendChild(incrementBtn);
                                saleCountCell.appendChild(saleCountDiv);
                            }else{
                                saleCountCell.innerText='缺货';
                            }
                            row.appendChild(saleCountCell);
                    });
                    }
                }

                imageList = data.imageProductList;
                displayMainImage(currentImageIndex);

                function displayThumbnails(){
                    const thumbnailList = document.getElementById('thumbnailList');
                    thumbnailList.innerHTML = '';
                    const displayedImages = imageList.slice(currentImageIndex, currentImageIndex + MAX_IMAGES);
                    displayedImages.forEach((image, index) => {
                        const thumbnailIndex = currentImageIndex + index
                        const thumbnail = `<img src="`+image+`" alt="Product Thumbnail" style="width: 50px; height: 50px;" onclick="selectImage(` +thumbnailIndex+`)">`;
                        thumbnailList.insertAdjacentHTML('beforeend', thumbnail);
                    });
                    upadteNavigationButtons();
                }

                function upadteNavigationButtons(){
                    const prevButton = document.getElementById('prevButton');
                    const nextButton = document.getElementById('nextButton');
                    prevButton.style.display = currentImageIndex === 0 ? 'none' : 'inline-block';
                    nextButton.style.display = currentImageIndex + MIN_IMAGES >= imageList.length ? 'none' : 'inline-block';
                }
                function nextImages() {
                    if (currentImageIndex + MIN_IMAGES < imageList.length) {
                        currentImageIndex += MIN_IMAGES;
                        displayThumbnails();
                    }
                }

                function prevImages() {
                    if (currentImageIndex > 0) {
                        currentImageIndex -= MIN_IMAGES;
                        displayThumbnails();
                    }
                }

                document.getElementById('prevButton').addEventListener('click', prevImages);
                document.getElementById('nextButton').addEventListener('click', nextImages);
                displayThumbnails();

                document.getElementById('product-table-name').innerText = data.productDetail.name || ''
                const tbody = document.querySelector('#product-table tbody');
                tbody.innerHTML='';
                const productDetail = data.productDetail;
                for(let i = 0; i<productDetail.productInfos.length;i+=4 ){
                    const row = document.createElement("tr");

                    for(let j = 0;j<4;j++){
                        if(i+j < productDetail.productInfos.length){
                            const info = productDetail.productInfos[i+j];

                            const cellName = document.createElement("td");
                            cellName.textContent = info.name;
                            row.appendChild(cellName);

                            const cellValue = document.createElement("td");
                            cellValue.textContent = info.value;
                            row.appendChild(cellValue);
                        }else{
                            const cellName = document.createElement("td");
                            row.appendChild(cellName);

                            const cellValue = document.createElement("td");
                            row.appendChild(cellValue);
                        }
                    }
                    tbody.appendChild(row);
                }


                const videoDiv = document.getElementById("videoDemo");
                videoDiv.innerHTML=''
                const titleVideo = document.getElementById("video-title");
                titleVideo.innerText=''
                if(typeof data.videoUrl !== 'undefined' && data.videoUrl !== null && data.videoUrl !== '') {
                    titleVideo.innerHTML = "Product Demo";
                    const videoProduct = document.createElement("video");
                    videoProduct.src= data.videoUrl;
                    videoProduct.autoplay=true;
                    videoProduct.muted = true;
                    videoProduct.loop = true;
                    videoProduct.classList.add("videoProduct");
                    videoDiv.appendChild(videoProduct);
                }

                const imageDetail = document.getElementById("product-image-detail");
                imageDetail.innerHTML = '';
                const detailHeader = document.createElement("h4");
                const colorSpace = document.createElement("p")
                colorSpace.classList.add("spanColor");
                colorSpace.innerText="|";
                detailHeader.appendChild(colorSpace);
                detailHeader.classList.add("detail-header");
                detailHeader.innerText = '商品描述';
                const detailImage = document.createElement("div");
                detailImage.classList.add("product-detail-list-image");
                detailImage.innerHTML ='';
                data.productImageDetail.forEach(img => {
                    const image = document.createElement("img");
                    image.classList.add("product-detail-img");
                    image.src = img;
                    detailImage.appendChild(image);

                })
                imageDetail.appendChild(detailHeader);
                imageDetail.appendChild(detailImage);

                document.getElementById('productDetails').style.display = 'block';
            })
            .catch(error =>{
                if(error.toLowerCase().indexOf("can not read".toLowerCase()) !== -1){
                    showDialog("Eror", "Sorry we got some error");
                }else{
                    showDialog("Error", error.message);
                }
            });
    });
    function displayMainImage(index) {
        // const mainImage = document.getElementById('mainImage');
        // mainImage.src = imageList[index];
        const mainImageDiv = document.getElementById('mainImage');
        mainImageDiv.style.backgroundImage = `url(`+imageList[index]+`)`;
        mainImageDiv.style.backgroundSize = 'cover';
        mainImageDiv.style.backgroundPosition = 'center';
    }

    document.getElementById('prevImageBtn').addEventListener('click', function() {
        if (currentImageIndex > 0) {
            currentImageIndex--;
        } else {
            currentImageIndex = imageList.length - 1;
        }
        displayMainImage(currentImageIndex);
    });

    document.getElementById('nextImageBtn').addEventListener('click', function() {
        if (currentImageIndex < imageList.length - 1) {
            currentImageIndex++;
        } else {
            currentImageIndex = 0;
        }
        displayMainImage(currentImageIndex);
    });

    function selectImage(index) {
        currentImageIndex = index;
        displayMainImage(index);
    }

</script>
<#--Controll button increament Buy product and decreament Buy product-->
<script>
    let counter = 0;
    const counterLabel = document.getElementById('counterLabel');
    document.getElementById('incrementBtn').addEventListener('click', function() {
        counter++;
        counterLabel.textContent = counter;
        // console.log("increment attachment");
    });
    document.getElementById('decrementBtn').addEventListener('click', function() {
        counter--;
        counterLabel.textContent = counter;
        // console.log("decrement attachment");
    });
</script>
<#--Script for logout btn-->
<script>
    document.getElementById('logoutBtn').addEventListener('click', function(event) {
        event.preventDefault();

        const accessToken = localStorage.getItem("accessToken");
        if (!accessToken) {
            // console.error("accessToken is missing");
            return;
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
                    localStorage.removeItem("accessToken");
                    localStorage.removeItem("refreshToken");
                    // console.log('Logout successful');
                    window.location.href = '/';
                } else {
                    // console.error('Logout failed');
                }
            })
            .catch(error => {
                // console.error('Error:', error);
            });
    });
</script>
<script src='/js/dialog.js'></script>
</body>

</html>