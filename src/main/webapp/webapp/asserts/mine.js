/**
 * Created by dingpengwei on 9/8/16.
 */
window.onload = function () {
    this.initFrameView();
    requestSessionStatus(onMineRequestSessionStatusCommonCallback);
    let dir = document.getElementById("dir") == undefined ? null : document.getElementById("dir").content == "" ? "cart":document.getElementById("dir").content;
    if (dir == "cart"){
        createMineTabView(0);
    }
    
    if (dir == "order"){
        createMineTabView(1);
    }

    if (dir == "account"){
        createMineTabView(2);
    }
    
    if (dir == "receiver"){
        createMineTabView(3);
    }
};

/**
 * 查询登录状态完成时候的mine处理方式
 * @param data
 */
function onMineRequestSessionStatusCommonCallback(data) {
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
            loginAction.style.cursor = "default";
            loginAction.innerHTML = accountEntity.nickName == undefined ? accountEntity.identity : accountEntity.nickName;
            headerMenuTop.appendChild(loginAction);

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

const MINE_TABS = new Array("购物车", "订单", "账户", "收货地址");
function createMineTabView(index) {
    if (index == undefined) {
        index = 0;
    }
    let mineTabContainer = document.getElementById(ID_HEADER_MENU_DOWN);
    mineTabContainer.innerHTML = null;
    for (let i = 0; i < MINE_TABS.length; i++) {
        let tabView = document.createElement("div");
        if (index == i) {
            tabView.className = "tabItem_selected tabItem";
        } else {
            tabView.className = "tabItem_normal tabItem";
        }
        tabView.innerHTML = MINE_TABS[i];
        mineTabContainer.appendChild(tabView);
        tabView.onclick = function () {
            createMineTabView(i);
        };
    }
    if (index == 0) {
        requestMyCart();
    } else if (index == 1) {
        requestMyOrder();
    } else if (index == 2) {
        requestMyAccount();
    } else if (index == 3) {
        requestMyReceiver();
    }
}

function requestMyCart() {
    requestMineCart(getCookie("cs"));
}

function requestMyOrder() {
    requestMineOrder(getCookie("cs"));
}

function requestMyAccount() {
    requestMineAccount(getCookie("cs"));
}

function requestMyReceiver() {
    requestMineReceiver(getCookie("cs"));
}