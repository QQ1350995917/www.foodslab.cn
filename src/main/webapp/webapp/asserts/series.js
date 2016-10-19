/**
 * Created by dingpengwei on 8/21/16.
 */
window.onload = function () {
    this.initFrameView();
    requestSessionStatus(onRequestSessionStatusCommonCallback);
    let seriesId = document.getElementById("seriesId") == undefined ? null : document.getElementById("seriesId").content;
    requestSeries(seriesId);
};
// + JSON.stringify(seriesEntity)
function requestSeries(seriesId) {
    let url = BASE_PATH + "series/retrieves";
    asyncRequestByGet(url, function (data) {
        var result = checkResponseDataFormat(data);
        if (result) {
            var jsonData = JSON.parse(data);
            createSeriesView(seriesId,jsonData.data,onSeriesTabClick);
        }
    }, onErrorCallback, onTimeoutCallback);
}

function createSeriesView(seriesId,seriesEntities,callback) {
    let seriesEntitiesView = document.getElementById(ID_HEADER_MENU_DOWN);
    seriesEntitiesView.innerHTML = null;
    let length = seriesEntities == undefined ? 0:seriesEntities.length;
    for (let i = 0; i < length; i++) {
        let seriesEntityView = document.createElement("div")
        let seriesEntity = seriesEntities[i];
        if (seriesId == seriesEntity.seriesId) {
            seriesEntityView.className = "tabItem_selected";
            callback(seriesEntity);
        } else {
            seriesEntityView.className = "tabItem_normal";
        }
        seriesEntityView.onclick = function () {
            createSeriesView(seriesEntity.seriesId,seriesEntities,callback);
        };
        seriesEntityView.innerHTML = seriesEntities[i].label;
        seriesEntitiesView.appendChild(seriesEntityView);
    }
}

function onSeriesTabClick(seriesEntity) {
    let url = BASE_PATH + "series/treeInversion?p=" + JSON.stringify(seriesEntity);
    asyncRequestByGet(url, function (data) {
        var result = checkResponseDataFormat(data);
        if (result) {
            var jsonData = JSON.parse(data);
            createProductView(jsonData.data);
        }
    }, onErrorCallback, onTimeoutCallback);
}

function createProductView(formatEntities) {
    let formatEntitiesView = document.createElement("div")
    let length = formatEntities == null ? 0:formatEntities.length;
    for (let i = 0; i < length; i++) {
        if (i % 4 == 0) {
            let clearFloat = document.createElement("div")
            clearFloat.className = "clearFloat";
            formatEntitiesView.appendChild(clearFloat);
        }

        let formatEntity = formatEntities[i];
        let formatEntityView = document.createElement("div")
        formatEntityView.className = "productItem";
        let formatEntityTitleView = document.createElement("div")
        formatEntityTitleView.className = "productItem_title";
        formatEntityTitleView.style.backgroundColor = COLORS[Math.floor(Math.random() * 10)];
        formatEntityTitleView.innerHTML = formatEntity.parent.label;
        formatEntityTitleView.onclick = function () {
            let url = BASE_PATH + "pd?typeId=" + formatEntity.typeId + "&formatId=" + formatEntity.formatId;
            window.open(url);
        };
        formatEntityView.appendChild(formatEntityTitleView);
        let formatEntityImageView = document.createElement("img")
        formatEntityImageView.className = "productItem_img";
        formatEntityImageView.onclick = function () {
            let url = BASE_PATH + "pd?typeId=" + formatEntity.typeId + "&formatId=" + formatEntity.formatId;
            window.open(url);
        };
        formatEntityView.appendChild(formatEntityImageView);
        let formatEntityLinkView = document.createElement("div")
        formatEntityLinkView.className = "productItem_link";
        // let formatEntityTypeLabel = document.createElement("div")
        // formatEntityTypeLabel.className = "productItem_link_label";
        // formatEntityTypeLabel.innerHTML = formatEntity.label;
        //formatEntityLinkView.appendChild(formatEntityTypeLabel);
        let formatEntityFormatLabel = document.createElement("div")
        formatEntityFormatLabel.className = "productItem_link_label";
        formatEntityFormatLabel.innerHTML = formatEntity.label + formatEntity.meta;
        formatEntityLinkView.appendChild(formatEntityFormatLabel);
        let formatEntityPriceLabel = document.createElement("div")
        formatEntityPriceLabel.className = "productItem_link_label";
        formatEntityPriceLabel.innerHTML = formatEntity.price + formatEntity.priceMeta;
        formatEntityLinkView.appendChild(formatEntityPriceLabel);
        formatEntityView.appendChild(formatEntityLinkView);
        formatEntityLinkView.onclick = function () {
            let url = BASE_PATH + "pd?typeId=" + formatEntity.typeId + "&formatId=" + formatEntity.formatId;
            window.open(url);
        };
        let formatEntityBuyView = document.createElement("div")
        formatEntityBuyView.className = "productItem_buy";
        formatEntityBuyView.innerHTML = "立即购买";
        formatEntityBuyView.onclick = function () {
            let object = new Object();
            object.productIds = formatEntity.formatId;
            let url = BASE_PATH + "pb?p=" + JSON.stringify(object);
            window.open(url);
        };
        formatEntityView.appendChild(formatEntityBuyView);
        formatEntitiesView.appendChild(formatEntityView);
    }
    let mainView = document.getElementById(MAIN);
    mainView.innerHTML = null;
    let rowNum = Math.floor(length / 4) + (length % 4 == 0 ? 0 : 1);
    mainView.style.height = rowNum * 400 + rowNum * 2 + "px";
    mainView.appendChild(formatEntitiesView);
}