<div class="cralw-data-container">
    <div class="search-container">
        <form name="search-log-form" class="search-data-form" ng-submit="ctrl.submit()">
            <input type="text" class="searchInput" ng-model="ctrl.key"  placeholder="Enter search key">
            <button type="submit" class="search-data-btn">Search</button>
        </form>
    </div>
    <div class="show-data-log">
        <div style="display: none">
            <span>Product Id</span>
        </div>
        <div>
            Url
        </div>
<#--        <div>-->
<#--           Request-->
<#--        </div>-->
<#--        <div>-->
<#--            Response-->
<#--        </div>-->
        <div>
            Start Time
        </div>
        <div>
            Message
        </div>
        <div>
            Action
        </div>
    </div>
    <div class="show-data-log" ng-repeat="item in ctrl.logDatas">
        <div style="display: none">
            <span class="id">{{item.id}}</span>
        </div>
        <div class="color-crawl-data">
            <span class="pColor">{{item.url}}</span>
        </div>
<#--        <div class="color-crawl-data">-->
<#--            <span class="pColor">{{item.request}}</span>-->
<#--        </div>-->
<#--        <div class="color-crawl-data">-->
<#--            <span class="pColor">{{item.response}}</span>-->
<#--        </div>-->
        <div class="size-crawl-data">
            <span class="pSize">{{item.startTime}}</span>
        </div>
        <div class="size-crawl-data">
            <span class="pSize">{{item.message}}</span>
        </div>
        <div>
            <button type="button" ng-click="ctrl.viewDetail(item.id)" class="btn btn-primary custom-width">View Detail</button>
        </div>
    </div>
    <div class="pagination">
        <a ng-if="ctrl.currentPage !== 1" ng-click="ctrl.goToPage(ctrl.currentPage - 1)">&laquo;</a>
        <a ng-repeat="page in ctrl.listPage" ng-click="ctrl.goToPage(page)" ng-class="{'active': page === ctrl.currentPage}">{{page}}</a>
        <a ng-if="ctrl.currentPage <= ctrl.totalPage" ng-click="ctrl.goToPage(ctrl.currentPage + 1)">&raquo;</a>
    </div>
</div>