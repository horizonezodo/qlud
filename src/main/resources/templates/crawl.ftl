<div class="container-fluid custom-div">
    <div class="mb-3">
        <form class="m-2" name="crawlForm" ng-submit="ctrl.crawlData()">
            <div class="mb-2">
                <input type="text" class="form-control" id="exampleFormControlInput1" ng-model="ctrl.crawl.crawlUrl" placeholder="Enter Product Url">
            </div>
            <div class="mb-2">
                <input type="text" class="form-control" id="exampleFormControlInput1" ng-model="ctrl.cookieValue" placeholder="Enter 1688 Cookie">
            </div>
            <div class="d-grid gap-2 col-6 mx-auto">
                <button type="submit" class="btn btn-primary">Crawl</button>
            </div>
        </form>
    </div>
    <div class="body-container" ng-show="ctrl.isShow">
        <div class="top-content">
            <div class="left-top-content">
                <div class="main-img">
                    <img ng-src="{{ ctrl.currentImage }}" alt="">
                </div>
                <div class="list-img">
                    <button class="navigate-btn" ng-click="ctrl.previousPage()" ng-disabled="ctrl.currentPage === 0"><</button>
                    <img ng-repeat="image in ctrl.currentImages" ng-src="{{ image }}" ng-click="ctrl.changeImage(image)" alt="">
                    <button class="navigate-btn" ng-click="ctrl.nextPage()" ng-disabled="ctrl.currentPage >= Math.floor(ctrl.ImageList.length / ctrl.imagePerPage)">></button>
                </div>
            </div>
            <div class="right-top-content">
                <p class="product-title">{{ctrl.title}}</p>
                <div class="price">
                    <p class="price-label">{{ctrl.priceLabel}}</p>
                    <div class="price-data" ng-repeat="item in ctrl.currentPriceList">
                        <p class="price-value">¥{{item.price}}</p>
                    </div>
                </div>
                <div class="color"  ng-if="ctrl.colorNull && ctrl.sizeNull">
                    <div ng-if = "!ctrl.showImgBtn" class="color-data">
                        <p class="color-label">{{ctrl.colorLabel}}</p>
                        <button ng-repeat="color in ctrl.colorList" ng-click="ctrl.selectColor(color.name)" class="color-btn"><img ng-if="ctrl.showImgData" ng-src="{{color.imageUrl + '_50x50.jpg'}}"><span class="color-name">{{color.name}}</span></button>
                    </div>
                    <div ng-if = "ctrl.showImgBtn" class="color-data">
                        <p class="color-label">{{ctrl.sizeLable}}</p>
                        <button ng-repeat="item in ctrl.sizeList" ng-click="ctrl.selectColor(item)" class="color-btn"><span class="color-name">{{item}}</span></button>
                    </div>
                </div>
                <div class="size">
                    <span ng-if="ctrl.colorNull && ctrl.sizeNull && !ctrl.showImgBtn" class="size-label">{{ctrl.sizeLable}}</span>
                    <span ng-if="ctrl.colorNull && ctrl.sizeNull && ctrl.showImgBtn" class="size-label">{{ctrl.colorLabel}}</span>
                    <span ng-if="ctrl.colorNull && !ctrl.sizeNull" class="size-label">{{ctrl.colorLabel}}</span>
                    <span ng-if="!ctrl.colorNull && ctrl.sizeNull" class="size-label">{{ctrl.sizeLable}}</span>
                    <span ng-if="!ctrl.colorNull && !ctrl.sizeNull" class="size-label">采购量</span>
                    <div class="size-table">
                        <div class="size-data" ng-repeat="data in ctrl.showDataList track by $index">
                            <div class="size-value">
                                <img class="size-img" ng-if="ctrl.colorNull && !ctrl.sizeNull" ng-src="{{data.imgUrl + '_36x36.jpg'}}"/>
                                <span class="product-size">{{data.name}}</span>
                            </div>
                            <div class="size-price-value">
                                <span class="product-price">{{data.price}}元</span>
                            </div>
                            <div class="canbook-value">
                                <span class="canbook-label">{{data.canBookCount}}</span>
                            </div>
                            <div class="sale-value">
                                <button class="decreament-btn">-</button>
                                <span class="sale-label">{{data.saleCount}}</span>
                                <button class="increase-btn">+</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="bottom-content">
            <div class="product-detail">
                <div class="detail-label">
                    <span class="red-icon">|</span>
                    <span>
            {{ctrl.detailLabel}}
          </span>
                </div>
                <div class="detail-table">
                    <div class="detail-data">
                        <div class="detail-value" ng-repeat="item in ctrl.detailList">
                            <div class="detail-item">
                                {{item.name}}
                            </div>
                            <div class="detail-item-value">
                                {{item.value}}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="video-detail"  ng-if="ctrl.showVideo">
                <div class="detail-label">
                    <span class="red-icon">|</span>
                    <span>
            {{ctrl.videoLabel}}
          </span>
                </div>
                <div class="video">
                    <video ng-src="{{ctrl.videoUrl}}" controls></video>
                </div>
            </div>
            <div class="image-detail">
                <div class="detail-label">
                    <span class="red-icon">|</span>
                    <span>{{ctrl.imageDetailLabel}}</span>
                </div>
                <div class="list-img-detail">
                    <img class="img-deltail" ng-repeat="img in ctrl.imageDetailList" class="img-detail" ng-src="{{img + '_300x300.jpg'}}">
                </div>
<#--                <div ng-repeat="img in ctrl.imageDetailList" class="list-img-detail" ng-style="{'background-image': 'url(' + img + '_300x300.jpg)'}">-->
<#--                </div>-->
            </div>
        </div>
    </div>
</div>

