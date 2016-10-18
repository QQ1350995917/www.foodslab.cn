/**
 * Created by dingpengwei on 8/19/16.
 */
const BASE_PATH = "http://localhost:8080/foodslab/";
const ID_HEADER_ICON = "headerIcon";
const ID_HEADER_MENU_TOP = "headerMenuTop";
const ID_HEADER_MENU_QUERY = "headerMenuTopQuery";
const ID_HEADER_MENU_LOGIN = "headerMenuTopLogin";
const ID_HEADER_MENU_LOGOUT = "headerMenuTopLogout";
const ID_HEADER_MENU_DOWN = "headerMenuDown";
const MAIN = "main";
const KEY_CS = "cs";

const RESPONSE_SUCCESS = 3050;

const COLORS = new Array("#715595", "#006AA8", "#3EAF5C", "#F0DB4F", "#715595", "#006AA8", "#3EAF5C", "#F0DB4F", "#715595", "#006AA8", "#3EAF5C", "#F0DB4F");

/**
 * 通用函数,检测服务器返回的数据格式是否正确
 * @param data 服务器返回的数据
 * @returns {boolean} 检测结果
 */
function checkResponseDataFormat(data) {
    return true;
}

function onErrorCallback() {

}

function onTimeoutCallback() {

}

function initFrameView() {
    setTitleViewLogoutStatus();
    requestLinker();
}

/**
 * 初始化登录状态面板
 */
function setTitleViewLogoutStatus() {
    document.getElementById(ID_HEADER_ICON).onclick = function () {
        window.open(BASE_PATH, "_blank");
    }

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

    loginAction.onclick = function () {
        showLoginView(function () {
            requestSessionStatus(onRequestSessionStatusCommonCallback);
        });
    };

    queryAction.onclick = function () {
        window.open(BASE_PATH + "pq", "_blank");
    };
}

/**
 * 设置登录状态面板为登录状态
 * @param userEntity
 */
function setTitleViewLoginStatus(userEntity) {
    let accountEntity = userEntity.children[0];
    let headerMenuTop = document.getElementById(ID_HEADER_MENU_TOP);
    headerMenuTop.innerHTML = null;
    let logoutAction = document.createElement("div");
    logoutAction.id = ID_HEADER_MENU_LOGOUT;
    logoutAction.className = "header_menu_top_item";
    logoutAction.innerHTML = "退出";
    logoutAction.onclick = function () {
        let requestUserEntity = new Object();
        requestUserEntity.cs = getCookie(KEY_CS);
        let url = BASE_PATH + "account/logout?p=" + JSON.stringify(requestUserEntity);
        asyncRequestByGet(url, function (data) {
            var result = checkResponseDataFormat(data);
            if (result) {
                var jsonData = JSON.parse(data);
                if (jsonData.code == RESPONSE_SUCCESS) {
                    setTitleViewLogoutStatus();
                } else {
                    new Toast().show("退出失败");
                }
            }
        }, onErrorCallback, onTimeoutCallback);
    }
    headerMenuTop.appendChild(logoutAction);
    let loginAction = document.createElement("div");
    loginAction.className = "header_menu_top_item";
    loginAction.innerHTML = accountEntity.nickName == undefined ? accountEntity.identity : accountEntity.nickName;
    headerMenuTop.appendChild(loginAction);
    let queryAction = document.createElement("div");
    queryAction.className = "header_menu_top_item";
    queryAction.style.widths = "100px";
    queryAction.innerHTML = "订单查询";
    headerMenuTop.appendChild(queryAction);
    loginAction.onclick = function () {
        let requestPageEntity = new Object();
        requestPageEntity.cs = getCookie("cs");
        requestPageEntity.dir = getCookie("order");
        window.open(BASE_PATH + "pm?p=" + JSON.stringify(requestPageEntity));
    }
    queryAction.onclick = function () {
        let requestPageEntity = new Object();
        requestPageEntity.cs = getCookie("cs");
        window.open(BASE_PATH + "pm?p=" + JSON.stringify(requestPageEntity));
    }
}

/**
 * 查询登录状态
 * @param onRequestCallback
 */
function requestSessionStatus(onRequestCallback) {
    let cs = getCookie(KEY_CS);
    if (!isNullValue(cs)) {
        let requestUserEntity = new Object();
        requestUserEntity.cs = cs;
        let url = BASE_PATH + "account/retrieve?p=" + JSON.stringify(requestUserEntity);
        asyncRequestByGet(url, function (data) {
            onRequestCallback(data);
        }, onErrorCallback, onTimeoutCallback);
    }
}

/**
 * 查询登录状态完成时候的一般处理方式
 * @param data
 */
function onRequestSessionStatusCommonCallback(data) {
    var jsonData = JSON.parse(data);
    if (jsonData.code == RESPONSE_SUCCESS) {
        let userEntity = jsonData.data;
        setTitleViewLoginStatus(userEntity);
    } else {
        setTitleViewLogoutStatus();
        delCookie(KEY_CS);
    }
}

function requestLinker() {
    let url = BASE_PATH + "link/retrieves";
    asyncRequestByGet(url, function (data) {
        var result = checkResponseDataFormat(data);
        if (result) {
            var jsonData = JSON.parse(data);
            createLinkView(jsonData.data);
        }
    }, onErrorCallback, onTimeoutCallback);
}

function createLinkView(linkEntities) {
    let footer = document.getElementById("footer");
    let maxChildrenLength = 0;
    for (let i = 0; i < linkEntities.length - 1; i++) {
        let linkEntity = linkEntities[i];
        let currentChildrenLength = linkEntity.children == undefined ? 0 : linkEntity.children.length;
        if (currentChildrenLength > maxChildrenLength) {
            maxChildrenLength = currentChildrenLength;
        }
        let linkerItemContainer = createLinkerItemContainer(linkEntity);
        footer.appendChild(linkerItemContainer);
    }
    let clearFloat = document.createElement("div");
    clearFloat.className = "clearFloat";
    footer.appendChild(clearFloat);
    let siteInfoContainer = createSiteInfoContainer(linkEntities[linkEntities.length - 1]);
    footer.appendChild(siteInfoContainer);
    footer.style.height = maxChildrenLength * 30 + 70 + "px";
}

function createLinkerItemContainer(data) {
    let linkerItemContainer = document.createElement("div");
    linkerItemContainer.className = "footer_link_item";
    let linkerTitleView = document.createElement("div");
    linkerTitleView.className = "footer_link_linker";
    linkerTitleView.innerHTML = data.label;
    linkerItemContainer.appendChild(linkerTitleView);
    for (let i = 0; i < data.children.length; i++) {
        let linkerView = document.createElement("div");
        linkerView.className = "footer_link_linker";
        let linker = document.createElement("a");
        linker.innerText = data.children[i].label;
        linker.setAttribute("href", data.children[i].href);
        linker.target = "_blank";
        linkerView.appendChild(linker);
        linkerItemContainer.appendChild(linkerView);
    }
    return linkerItemContainer;
}

function createSiteInfoContainer(data) {
    let linkerSiteContainer = document.createElement("div");
    let hr = document.createElement("hr");
    hr.className = "footer_link_line";
    linkerSiteContainer.appendChild(hr);
    let linkRegister = document.createElement("div");
    linkRegister.className = "footer_link";
    linkRegister.style.width = data.children.length * 70 + "px";
    linkRegister.style.margin = "auto";
    linkerSiteContainer.appendChild(linkRegister);
    for (let j = 0; j < data.children.length; j++) {
        let subLinkEntity = data.children[j];
        let linkerView = document.createElement("div");
        linkerView.className = "footer_link_linker";
        let linker = document.createElement("a");
        linker.innerText = subLinkEntity.label;
        linker.setAttribute("href", subLinkEntity.href);
        linker.target = "_blank";
        linkerView.appendChild(linker);
        linkRegister.appendChild(linkerView);
    }
    return linkerSiteContainer;
}

/**
 * 网络请求工具方法
 * 以GET的方式进行网络请求
 * @param url 请求的地址
 * @param onDataCallback 请求完成的回调
 * @param onErrorCallback 请求错误的回调
 * @param onTimeoutCallback 请求超时的回调
 */
function asyncRequestByGet(url, onDataCallback, onErrorCallback, onTimeoutCallback) {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.timeout = 5000;
    xmlHttp.ontimeout = onTimeoutCallback;
    xmlHttp.open("GET", url, true);
    xmlHttp.send(null);
    xmlHttp.onreadystatechange = function () {
        if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
            onDataCallback(xmlHttp.responseText);
        } else {
            onErrorCallback;
        }
    }
}

/**
 * 网络请求工具方法
 * 以POST的方式进行网络请求
 * @param url 请求的地址
 * @param params 请求的参数
 * @param onDataCallback 请求完成的回调
 * @param onErrorCallback 请求错误的回调
 * @param onTimeoutCallback 请求超时的回调
 */
function asyncRequestByPost(url, params, onDataCallback, onErrorCallback, onTimeoutCallback) {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.timeout = 5000;
    xmlHttp.ontimeout = onTimeoutCallback;
    xmlHttp.open("POST", url, true);
    xmlHttp.setRequestHeader("cache-control", "no-cache");
    xmlHttp.setRequestHeader("contentType", "text/html;charset=uft-8");
    xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xmlHttp.send(encodeURI(params));
    xmlHttp.onreadystatechange = function () {
        if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
            onDataCallback(xmlHttp.responseText);
        } else {
            onErrorCallback();
        }
    }
}

/**
 * view层工具方法
 * 获取滚动的长度
 * @returns {*}
 */
function getScrollTop() {
    var scrollPos;
    if (window.pageYOffset) {
        scrollPos = window.pageYOffset;
    }
    else if (document.compatMode && document.compatMode != 'BackCompat') {
        scrollPos = document.documentElement.scrollTop;
    }
    else if (document.body) {
        scrollPos = document.body.scrollTop;
    }
    return scrollPos;
}

/**
 * view层工具方法
 * 格式化显示的时间字符串
 * @param format
 * @returns {*}
 */
Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S": this.getMilliseconds()
    }
    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
}

/**
 * 数据层工具方法
 * 判定字符串是否是泛义上的空
 * @param value
 * @returns {boolean}
 */
function isNullValue(value) {
    if (value == undefined || value == null || value == "") {
        return true;
    }
    return false;
}

/**
 * 数据层工具方法
 * 设置本地cookie存储
 * @param key
 * @param value
 * @param expireDays
 */
function setCookie(key, value, expireDays) {
    if (isNullValue(expireDays)) {
        expireDays = 30;
    }
    var expireDate = new Date()
    expireDate.setDate(expireDate.getDate() + expireDays)
    document.cookie = key + "=" + encodeURI(value) + ((expireDays == null) ? "" : ";expires=" + expireDate.toGMTString())
}

/**
 * 数据层工具方法
 * 获取本地cookie存储
 * @param key
 * @returns {*}
 */
function getCookie(key) {
    if (document.cookie.length > 0) {
        let indexStart = document.cookie.indexOf(key + "=")
        if (indexStart != -1) {
            indexStart = indexStart + key.length + 1
            let indexEnd = document.cookie.indexOf(";", indexStart)
            if (indexEnd == -1) indexEnd = document.cookie.length
            return decodeURI(document.cookie.substring(indexStart, indexEnd))
        }
    }
    return ""
}

/**
 * 数据层工具方法
 * 删除本地cookie存储
 * @param key
 */
function delCookie(key) {
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var del = getCookie(key);
    if (del != null) {
        document.cookie = key + "=" + del + ";expires=" + exp.toGMTString();
    }
}