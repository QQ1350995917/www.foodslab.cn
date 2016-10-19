/**
 * Created by dingpengwei on 8/30/16.
 */
window.onload = function () {
    this.initFrameView();
    createMenuDownView();
    requestSessionStatus(onQueryRequestSessionStatusCommonCallback);
    requestRecommend();
    diversifyMainView();
    let orderId = document.getElementById("orderId") == undefined ? null : document.getElementById("orderId").content;
    if (orderId != null) {
        requestOrderByLean(orderId);
    }
};

/**
 * 查询登录状态完成时候的query处理方式
 * @param data
 */
function onQueryRequestSessionStatusCommonCallback(data) {
    if (data == undefined){
        document.getElementById(ID_HEADER_MENU_LOGIN).onclick = function () {
            showLoginView(function () {
                requestSessionStatus(onRequestSessionStatusCommonCallback);
            });
        }
        document.getElementById(ID_HEADER_MENU_QUERY).onclick = function () {
            window.open(BASE_PATH + "pq");
        }
    }else{
        var jsonData = JSON.parse(data);
        if (jsonData.code == RESPONSE_SUCCESS) {
            let userEntity = jsonData.data;
            let accountEntity = userEntity.children[0];
            let headerMenuTop = document.getElementById(ID_HEADER_MENU_TOP);
            headerMenuTop.innerHTML = null;
            let logoutAction = document.createElement("div");
            logoutAction.className = "header_menu_top_item";
            logoutAction.innerHTML = "退出";
            logoutAction.onclick = function () {
                let requestUserEntity = new Object();
                requestUserEntity.cs = getCookie(KEY_CS);
                let url = BASE_PATH + "account/logout?p=" + JSON.stringify(requestUserEntity);
                asyncRequestByGet(url, function (data) {
                    window.open(BASE_PATH,"_self");
                }, onErrorCallback, onTimeoutCallback);
            }
            headerMenuTop.appendChild(logoutAction);
            let loginAction = document.createElement("div");
            loginAction.className = "header_menu_top_item";
            loginAction.innerHTML = accountEntity.nickName == undefined ? accountEntity.identity : accountEntity.nickName;
            headerMenuTop.appendChild(loginAction);
            loginAction.onclick = function () {
                let requestPageEntity = new Object();
                requestPageEntity.cs = getCookie("cs");
                window.open(BASE_PATH + "pm?p=" + JSON.stringify(requestPageEntity),"_self");
            }
        } else {
            let headerMenuTop = document.getElementById(ID_HEADER_MENU_TOP);
            headerMenuTop.innerHTML = null;
            let loginAction = document.createElement("div");
            loginAction.id = ID_HEADER_MENU_LOGIN;
            loginAction.className = "header_menu_top_item";
            loginAction.innerHTML = "登录/注册";
            headerMenuTop.appendChild(loginAction);

            let queryAction = document.createElement("div");
            queryAction.id = ID_HEADER_MENU_QUERY;
            queryAction.className = "header_menu_top_item";
            queryAction.style.widths = "100px";
            queryAction.innerHTML = "订单查询";
            headerMenuTop.appendChild(queryAction);
            document.getElementById(ID_HEADER_MENU_LOGIN).onclick = function () {
                showLoginView(function () {
                    requestSessionStatus(onRequestSessionStatusCommonCallback);
                });
            }
            document.getElementById(ID_HEADER_MENU_QUERY).onclick = function () {
                window.open(BASE_PATH + "pq");
            }
            delCookie(KEY_CS);
        }
    }
}


function requestOrderByLean(orderId) {
    let url = BASE_PATH + "order/query?orderId=" + orderId;
    asyncRequestByGet(url, function (data) {
        var result = checkResponseDataFormat(data);
        if (result) {
            var jsonData = JSON.parse(data);
            onRequestOrderByLeanCallback(orderId,jsonData.data);
        }
    }, onErrorCallback, onTimeoutCallback);
}

function requestOrderByKey(key) {
    onRequestOrderByKeyCallback(key,undefined);
}

function requestRecommend() {
    let url = BASE_PATH + "format/recommends";
    asyncRequestByGet(url, function (data) {
        var result = checkResponseDataFormat(data);
        if (result) {
            var jsonData = JSON.parse(data);
            createRecommendView(jsonData.data);
        }
    }, onErrorCallback, onTimeoutCallback);
}

function onRequestOrderByLeanCallback(orderId,data) {
    let searchResultView = document.getElementById("searchResultView");
    searchResultView.innerHTML = null;
    if (searchResultView.clientHeight == 0){
        let mainView = document.getElementById(MAIN);
        mainView.style.height = mainView.clientHeight + 120 + "px";
    }
    if (data == undefined) {
        searchResultView.style.height = "120px";
        searchResultView.innerHTML = "没有查询到订单号为<span style='color: #FF0000'>" + orderId + "</span>的订单";
    } else {
        searchResultView.style.height = "120px";

        let orderInfo = document.createElement("div");
        orderInfo.className = "orderItem";
        orderInfo.innerHTML = "订单号: " + data.orderId + "  下单时间: " + data.orderTime;
        let receiverInfo = document.createElement("div");
        receiverInfo.className = "orderItem";
        receiverInfo.innerHTML = "收货地址: " + data.address + "  收货人: " + data.name + " " + data.phone;
        let expressInfo = document.createElement("div");
        expressInfo.className = "orderItem";
        expressInfo.innerHTML = "快递公司: " + data.expressName + "    " + "快递单号: " + data.expressNumber;
        let expressCurrentInfo = document.createElement("div");
        expressCurrentInfo.className = "orderItem";
        expressCurrentInfo.innerHTML = "快递状态: " + data.expressStatus;


        searchResultView.appendChild(orderInfo);
        searchResultView.appendChild(receiverInfo);
        searchResultView.appendChild(expressInfo);
        searchResultView.appendChild(expressCurrentInfo);
    }
}

function onRequestOrderByKeyCallback(key,data) {
    let searchResultView = document.getElementById("searchResultView");
    searchResultView.innerHTML = null;
    if (searchResultView.clientHeight == 0){
        let mainView = document.getElementById(MAIN);
        mainView.style.height = mainView.clientHeight + 100 + "px";
    }
    if (data == undefined) {
        searchResultView.style.height = "100px";
        searchResultView.innerHTML = "没有查询到关于<span style='color: #FF0000'>" + key + "</span>的订单";
    } else {
        
    }


}

function createMenuDownView() {
    let searchContainer = document.createElement("div");
    searchContainer.className = "searchContainer";
    let searchInput = document.createElement("input");
    searchInput.className = "searchInput";
    searchInput.type = "text";
    let searchButton = document.createElement("div");
    searchButton.className = "searchButton";
    searchButton.innerHTML = "查询订单";
    searchButton.onclick = function () {
        if (searchInput.value != null && searchInput.value != ""){
            requestOrderByKey(searchInput.value);
        }else{
            new Toast().show("请输入要查询的信息");
        }
    };
    searchContainer.appendChild(searchInput);
    searchContainer.appendChild(searchButton);
    document.getElementById(ID_HEADER_MENU_DOWN).appendChild(searchContainer);
    searchInput.focus();
}

function diversifyMainView() {
    let mainView = document.getElementById(MAIN);
    let searchResultView = document.createElement("div")
    searchResultView.id = "searchResultView";
    searchResultView.className = "searchResultView";
    mainView.appendChild(searchResultView);

    let recommendView = document.createElement("div")
    recommendView.className = "recommendView";
    recommendView.style.height = "430px";
    let recommendTitleView = document.createElement("div")
    recommendTitleView.innerHTML = "为您推荐";
    recommendTitleView.style.color = "black";
    recommendTitleView.style.height = "26px";
    let recommendContentView = document.createElement("div")
    recommendContentView.id = "recommendContentView";
    recommendView.appendChild(recommendTitleView);
    recommendView.appendChild(recommendContentView);
    mainView.appendChild(recommendView);
    mainView.style.height = searchResultView.clientHeight + recommendView.clientHeight + "px";
}

function createRecommendView(formatEntities) {
    let columns = 6;
    let height = 200;
    let formatEntitiesView = document.createElement("div")
    for (let i = 0; i < formatEntities.length; i++) {
        if (i % columns == 0) {
            let clearFloat = document.createElement("div")
            clearFloat.className = "clearFloat";
            formatEntitiesView.appendChild(clearFloat);
        }
        /**
         * 产品的图片
         */
        let formatEntityImageView = document.createElement("img")
        formatEntityImageView.className = "productItem_img";
        formatEntityImageView.style.width = "164.6px";
        formatEntityImageView.style.height = "200px";
        // formatEntityImageView.src = "http://www.foodslab.cn";
        formatEntityImageView.onclick = function () {
            let url = BASE_PATH + "pd?typeId=" + formatEntities[i].typeId + "&formatId=" + formatEntities[i].formatId;
            window.open(url,"_self");
        };
        formatEntitiesView.appendChild(formatEntityImageView);
    }
    let recommendContentView = document.getElementById("recommendContentView");
    let rowNum = Math.floor(formatEntities.length / columns) + (formatEntities.length % columns == 0 ? 0 : 1);
    recommendContentView.style.height = rowNum * height + rowNum * 2 + "px";
    recommendContentView.appendChild(formatEntitiesView);
}


