<div class="cralw-data-container">
    <div class="search-container">
        <form name="search-data-form" class="search-data-form" ng-submit="ctrl.submit()">
            <input type="text" class="searchInput" ng-model="ctrl.key"  placeholder="Enter product key">
            <button type="submit" class="search-data-btn">Search</button>
        </form>
    </div>
    <div class="show-data">
        <div>
            <span>Product Title</span>
        </div>
        <div>
            Product Image
        </div>
        <div>
            Product Price
        </div>
        <div>
            Product Color
        </div>
        <div>
            Product Size
        </div>
    </div>
    <div class="show-data" ng-repeat="item in ctrl.crawlDatas">
        <div>
            <span class="pTitle">{{item.pTitle}}</span>
        </div>
        <div class="pImage">
            <img class="pImageValue" ng-src="{{ item.pImg+'_300x300.jpg '}}" alt="">
        </div>
        <div class="pPriceData">
            <span class="pPrice">{{item.pPrice}}</span>
        </div>
        <div class="color-crawl-data">
            <span class="pColor">{{item.pColor}}</span>
        </div>
        <div class="size-crawl-data">
            <span class="pSize">{{item.pSize}}</span>
        </div>
    </div>
</div>