<div class="log" style="padding: 0 10%">
    <div class="log-info">
        <div>
            <span>Log Id: </span>
            <span>{{ctrl.logData.id}}</span>
        </div>
        <div>
            <span>Url: </span>
            <span>{{ctrl.logData.url}}</span>
        </div>
        <div>
            <span>Method: </span>
            <span>{{ctrl.logData.method}}</span>
        </div>
        <div>
            <span>Request: </span>
<#--            <span>{{ctrl.log.request}}</span>-->
            <pre ng-bind="ctrl.logData.request | json"></pre>
        </div>
        <div>
            <span>Response: </span>
<#--            <span>{{ctrl.log.response}}</span>-->
            <pre ng-bind="ctrl.logData.response | json"></pre>
        </div>
        <div>
            <span>Response Status: </span>
            <span>{{ctrl.logData.statusCode}}</span>
        </div>
        <div>
            <span>Start Time: </span>
            <span>{{ctrl.logData.startTime}}</span>
        </div>
        <div>
            <span>Message:</span>
            <span>{{ctrl.logData.message}}</span>
        </div>
    </div>
    <div>
        <button ng-click="ctrl.backToLog()">Back</button>
    </div>
</div>