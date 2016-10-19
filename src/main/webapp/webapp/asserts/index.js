/**
 * Created by dingpengwei on 8/19/16.
 */
window.onload = function () {
    this.initFrameView();
    this.requestSessionStatus(onRequestSessionStatusCommonCallback);
    this.initIndexFrameView();
};

function initIndexFrameView() {
    let mainContainer = document.getElementById(MAIN);
    mainContainer.innerHTML = null;
    let posterContainer = document.createElement("div");
    posterContainer.className = "HFillContainer";
    let recommendContainer = document.createElement("div");
    recommendContainer.className = "HFillContainer";
    mainContainer.appendChild(posterContainer);
    mainContainer.appendChild(recommendContainer);

    requestSeries();

    requestPoster(posterContainer, function (reset, height) {
        if (reset) {

        } else {
            mainContainer.style.height = (posterContainer.clientHeight + recommendContainer.clientHeight) + "px";
        }
    });
    requestRecommend(recommendContainer, function (reset, height) {
        if (reset) {

        } else {
            mainContainer.style.height = (posterContainer.clientHeight + recommendContainer.clientHeight) + "px";
        }
    });
}

function requestSeries() {
    let url = BASE_PATH + "series/retrieves";
    asyncRequestByGet(url, function (data) {
        var result = checkResponseDataFormat(data);
        if (result) {
            var jsonData = JSON.parse(data);
            createSeriesView(jsonData.data);
        }
    }, onErrorCallback, onTimeoutCallback);
}

function requestPoster(posterContainer, onResizeCallback) {
    let url = BASE_PATH + "poster/retrieves";
    asyncRequestByGet(url, function (data) {
        var result = checkResponseDataFormat(data);
        if (result) {
            var jsonData = JSON.parse(data);
            createPosterView(posterContainer, jsonData.data, onResizeCallback);
        }
    }, onErrorCallback, onTimeoutCallback);
}


function requestRecommend(recommendContainer, onResizeCallback) {
    let url = BASE_PATH + "format/recommends";
    asyncRequestByGet(url, function (data) {
        var result = checkResponseDataFormat(data);
        if (result) {
            var jsonData = JSON.parse(data);
            fillRecommendContainer(recommendContainer, jsonData.data, onResizeCallback);
        }
    }, onErrorCallback, onTimeoutCallback);
}

function createSeriesView(seriesEntities) {
    let seriesEntitiesView = document.getElementById(ID_HEADER_MENU_DOWN);
    for (let i = 0; i < seriesEntities.length; i++) {
        let seriesEntityView = document.createElement("div")
        seriesEntityView.className = "tabItem_normal";
        seriesEntityView.innerHTML = seriesEntities[i].label;
        seriesEntityView.onclick = function () {
            let url = BASE_PATH + "ps?seriesId=" + seriesEntities[i].seriesId;
            window.open(url);
        };
        seriesEntitiesView.appendChild(seriesEntityView);
    }
}

function createPosterView(posterContainer, posterEntities, onResizeCallback) {
    let length = posterEntities == undefined ? 0 : posterEntities.length;

    if (length == 0) {
        posterContainer.style.height = "0px";
    } else {
        let index = 0;
        posterContainer.style.height = "320px";
        let posterView = document.createElement("img");
        posterView.style.width = "1000px";
        posterView.style.height = "320px";
        posterView.src = posterEntities[index].href;
        posterContainer.appendChild(posterView);
        onResizeCallback(false, 320);
        if (length > 1) {
            window.setInterval(function () {
                index++;
                if (index == length) {
                    index = 0;
                }
                posterView.src = posterEntities[index].href;
            }, 3000);
        }
    }
}

function fillRecommendContainer(recommendContainer, formatEntities, onResizeCallback) {
    let length = formatEntities == undefined ? 0 : formatEntities.length;
    for (let i = 0; i < length; i++) {
        if (i % 4 == 0) {
            let clearFloat = document.createElement("div")
            clearFloat.className = "clearFloat";
            recommendContainer.appendChild(clearFloat);
        }
        let formatEntityView = document.createElement("div")
        formatEntityView.className = "productItem";
        /**
         * 产品的标题
         */
        let formatEntityTitleView = document.createElement("div")
        formatEntityTitleView.className = "productItem_title";
        formatEntityTitleView.style.backgroundColor = COLORS[Math.floor(Math.random() * 10)];
        formatEntityTitleView.innerHTML = formatEntities[i].parent.parent.label;
        formatEntityTitleView.onclick = function () {
            let url = BASE_PATH + "ps?seriesId=" + formatEntities[i].parent.parent.seriesId;
            window.open(url);
        };
        formatEntityView.appendChild(formatEntityTitleView);
        /**
         * 产品的图片
         */
        let formatEntityImageView = document.createElement("img")
        formatEntityImageView.className = "productItem_img";
        formatEntityImageView.onclick = function () {
            let url = BASE_PATH + "pd?typeId=" + formatEntities[i].typeId + "&formatId=" + formatEntities[i].formatId;
            window.open(url);
        };
        formatEntityView.appendChild(formatEntityImageView);
        /**
         * 产品的类型规格价格
         */
        let formatEntityLinkView = document.createElement("div")
        formatEntityLinkView.className = "productItem_link";
        let formatEntityTypeLabel = document.createElement("div")
        formatEntityTypeLabel.className = "productItem_link_label";
        formatEntityTypeLabel.innerHTML = formatEntities[i].parent.label;
        formatEntityLinkView.appendChild(formatEntityTypeLabel);
        let formatEntityFormatLabel = document.createElement("div")
        formatEntityFormatLabel.className = "productItem_link_label";
        formatEntityFormatLabel.innerHTML = formatEntities[i].label + formatEntities[i].meta;
        formatEntityLinkView.appendChild(formatEntityFormatLabel);
        let formatEntityPriceLabel = document.createElement("div")
        formatEntityPriceLabel.className = "productItem_link_label";
        formatEntityPriceLabel.innerHTML = formatEntities[i].price + formatEntities[i].priceMeta;
        formatEntityLinkView.appendChild(formatEntityPriceLabel);
        formatEntityView.appendChild(formatEntityLinkView);
        formatEntityLinkView.onclick = function () {
            let url = BASE_PATH + "pd?typeId=" + formatEntities[i].typeId + "&formatId=" + formatEntities[i].formatId;
            window.open(url);
        };
        let formatEntityBuyView = document.createElement("div")
        formatEntityBuyView.className = "productItem_buy";
        formatEntityBuyView.innerHTML = "立即购买";
        formatEntityBuyView.onclick = function () {
            let object = new Object();
            object.productIds = formatEntities[i].formatId;
            let url = BASE_PATH + "pb?p=" + JSON.stringify(object);
            window.open(url);
        };
        formatEntityView.appendChild(formatEntityBuyView);
        recommendContainer.appendChild(formatEntityView);
    }

    let rowNum = Math.floor(length / 4) + (length % 4 == 0 ? 0 : 1);
    recommendContainer.style.height = rowNum * 400 + rowNum * 2 + "px";
    onResizeCallback(false, recommendContainer.clientHeight);
}
