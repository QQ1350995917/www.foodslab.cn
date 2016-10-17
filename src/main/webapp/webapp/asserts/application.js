/**
 * Created by dingpengwei on 8/19/16.
 */
const BASE_PATH = "http://localhost:8080/foodslab/";
const HEADER_MENU_TOP = "header_menu_top";
const HEADER_MENU_DOWN = "header_menu_down";
const MAIN = "main";

const RESPONSE_SUCCESS = 3050;

const COLORS = new Array("#715595", "#006AA8", "#3EAF5C", "#F0DB4F", "#715595", "#006AA8", "#3EAF5C", "#F0DB4F", "#715595", "#006AA8", "#3EAF5C", "#F0DB4F");

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

function initTitleView() {
    let header = document.getElementById("header");
    header.innerHTML = "<div id='header_icon' class='header_icon' onclick='onIndexClick()'>foodslab.cn</div> <div id='header_menu' class='header_menu'> <div id='header_menu_top' class='header_menu_top'></div> <div id='header_menu_down' class='header_menu_down'></div> </div>";
    requestAccount();
}

function requestAccount() {
    if (sessionStorage.cs) {

    } else {
        let topMenuPanel = document.getElementById(HEADER_MENU_TOP);
        let loginAction = document.createElement("div");
        loginAction.className = "header_menu_top_item";
        loginAction.innerHTML = "登录/注册";
        let queryAction = document.createElement("div");
        queryAction.className = "header_menu_top_item";
        queryAction.style.widths = "100px";
        queryAction.innerHTML = "订单查询";
        topMenuPanel.appendChild(loginAction);
        topMenuPanel.appendChild(queryAction);
        loginAction.onclick = function () {
            showLoginView();
        }
        
        queryAction.onclick = function () {
            window.open(BASE_PATH + "pq");
        }
    }
}

function onIndexClick() {
    window.open(BASE_PATH, "_blank");
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

function isNullValue(value) {
    if (value == undefined || value == null || value == "") {
        return true;
    }
    return false;
}

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