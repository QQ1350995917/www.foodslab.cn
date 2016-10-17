/**
 * Created by dingpengwei on 8/22/16.
 */
window.onload = function () {
    initTitleView();
    let typeId = document.getElementById("typeId") == undefined ? null : document.getElementById("typeId").content;
    let formatId = document.getElementById("formatId") == undefined ? null : document.getElementById("formatId").content;
    let typeEntity = new Object();
    typeEntity.typeId = typeId;
    requestType(typeEntity,formatId);
    requestLinker();
};

function requestType(typeEntity,selectedFormatId) {
    let url = BASE_PATH + "type/retrieve?p=" + JSON.stringify(typeEntity);
    asyncRequestByGet(url, function (data) {
        var result = checkResponseDataFormat(data);
        if (result) {
            var jsonData = JSON.parse(data);
            createTypeTitle(jsonData.data);
            createTypeMainView(jsonData.data,selectedFormatId);
        }
    }, onErrorCallback, onTimeoutCallback);
}

function requestPutInCart(formatEntity) {
    formatEntity.sessionId = "test";
    let url = BASE_PATH + "cart/create?p=" + JSON.stringify(formatEntity);
    asyncRequestByGet(url, function (data) {
        var result = checkResponseDataFormat(data);
        if (result) {
            var jsonData = JSON.parse(data);
            onRequestPutInCartCallback(jsonData.data);
        }
    }, onErrorCallback, onTimeoutCallback);
}

function onRequestPutInCartCallback(data) {
    showMaskView(undefined,"#CCCCCC");
    createPutInCartResultView(data);
}

function createTypeTitle(typeEntity) {
    let typeEntityView = document.getElementById(HEADER_MENU_DOWN);
    typeEntityView.innerHTML = null;

    let arrow1 = document.createElement("div");
    arrow1.className = "tabItem_normal";
    arrow1.style.cursor = "default";
    arrow1.innerHTML = ">>";
    typeEntityView.appendChild(arrow1);

    let seriesEntityView = document.createElement("div");
    seriesEntityView.className = "tabItem_normal";
    seriesEntityView.innerHTML = typeEntity.parent.label;
    seriesEntityView.onclick = function () {
        let url = BASE_PATH + "ps?seriesId=" + typeEntity.parent.seriesId;
        window.open(url, "_self");
    };
    typeEntityView.appendChild(seriesEntityView);

    let arrow2 = document.createElement("div");
    arrow2.className = "tabItem_normal";
    arrow2.style.cursor = "default";
    arrow2.innerHTML = ">>";
    typeEntityView.appendChild(arrow2);

    let typeLabelView = document.createElement("div");
    typeLabelView.className = "tabItem_normal";
    typeLabelView.style.cursor = "default";
    typeLabelView.innerHTML = typeEntity.label;
    typeEntityView.appendChild(typeLabelView);
}

function createTypeMainView(typeEntity,selectedFormatId) {
    let typeMainTopView = document.createElement("div");
    typeMainTopView.className = "typeMainTopView";

    let typeMainTopLeft = document.createElement("div");
    typeMainTopLeft.className = "typeMainTopLeft";
    typeMainTopView.appendChild(typeMainTopLeft);

    let typeMainTopRight = document.createElement("div");
    typeMainTopRight.className = "typeMainTopRight";
    let countdownView = document.createElement("div");
    countdownView.innerHTML = "优惠剩余时间09天09时09分09秒";
    countdownView.style.height = "25px";
    countdownView.style.backgroundColor = "red";
    typeMainTopRight.appendChild(countdownView);

    let descriptionView = document.createElement("div");
    descriptionView.innerHTML = typeEntity.summary;
    descriptionView.style.color = "black";
    descriptionView.style.height = "70px";
    typeMainTopRight.appendChild(descriptionView);

    let formatEntitiesView = document.createElement("div");
    formatEntitiesView.style.height = "231px";
    formatEntitiesView.style.marginTop = "20px";
    requestFormat(typeEntity,formatEntitiesView,selectedFormatId);
    typeMainTopRight.appendChild(formatEntitiesView);

    typeMainTopView.appendChild(typeMainTopRight);

    let typeMainLine = document.createElement("hr");
    typeMainLine.className = "typeMainLine";
    let typeMainDownView = document.createElement("div");
    typeMainDownView.className = "typeMainDownView";
    typeMainDownView.innerHTML = typeEntity.directions;

    let mainView = document.getElementById(MAIN);
    mainView.appendChild(typeMainTopView);
    mainView.appendChild(typeMainLine);
    mainView.appendChild(typeMainDownView);
    mainView.style.height = typeMainTopView.clientHeight + typeMainLine.clientHeight + typeMainDownView.clientHeight + "px";
}

function requestFormat(typeEntity,containerView,selectedFormatId) {
    let url = BASE_PATH + "format/retrieves?p=" + JSON.stringify(typeEntity);
    asyncRequestByGet(url, function (data) {
        var result = checkResponseDataFormat(data);
        if (result) {
            var jsonData = JSON.parse(data);
            createFormatView(containerView,jsonData.data,selectedFormatId);
        }
    }, onErrorCallback, onTimeoutCallback);
}

function createFormatView(containerView, formatEntities, selectedFormatId) {
    containerView.innerHTML = null;
    let formatLabelView = document.createElement("div");
    formatLabelView.className = "formatItem";
    let currentFormat = undefined;
    for (let i = 0; i < formatEntities.length; i++) {
        let formatLabel = document.createElement("div");
        formatLabel.className = "formatLabel";
        formatLabel.innerHTML = formatEntities[i].label + formatEntities[i].meta;
        formatLabel.onclick = function () {
            createFormatView(containerView, formatEntities, formatEntities[i].formatId);
        };
        if (selectedFormatId == formatEntities[i].formatId) {
            currentFormat = formatEntities[i];
            formatLabel.className = "formatLabelSelected";
        }
        formatLabelView.appendChild(formatLabel);
    }

    if (currentFormat == undefined) {
        currentFormat = formatEntities[0];
        formatLabelView.childNodes[0].className = "formatLabelSelected";
    }
    containerView.appendChild(formatLabelView);
    containerView.appendChild(createFormatGiftItemView(currentFormat));
    containerView.appendChild(createFormatExpressItemView(currentFormat));
    containerView.appendChild(createFormatDiscountItemView(currentFormat));
}

function createFormatGiftItemView(formatEntity) {
    let formatGiftView = document.createElement("div");
    formatGiftView.className = "formatItem";
    let formatGiftLabel = document.createElement("div");
    formatGiftLabel.className = "formatLabel formatExpendLabel";
    formatGiftLabel.innerHTML = "满 " + formatEntity.giftCount + " 赠送 " + formatEntity.giftLabel;
    formatGiftView.appendChild(formatGiftLabel);
    return formatGiftView;
}

function createFormatExpressItemView(formatEntity) {
    let formatExpressView = document.createElement("div");
    formatExpressView.className = "formatItem";
    let formatExpressLabel = document.createElement("div");
    formatExpressLabel.className = "formatLabel formatExpendLabel";
    formatExpressLabel.innerHTML = "满 " + formatEntity.expressCount + " 包邮 " + formatEntity.expressName;
    formatExpressView.appendChild(formatExpressLabel);
    return formatExpressView;
}

function createFormatDiscountItemView(formatEntity) {
    let formatDiscountView = document.createElement("div");
    formatDiscountView.className = "formatItem";

    if (formatEntity.price != formatEntity.pricing){
        /**
         * 定价
         */
        let formatPriceLabel = document.createElement("div");
        formatPriceLabel.className = "formatLabel";
        formatPriceLabel.style.cursor = "default";
        let formatPriceContent = document.createElement("div");
        formatPriceContent.className = "formatLabel";
        formatPriceContent.style.cursor = "default";
        formatPriceContent.innerHTML = formatEntity.price + formatEntity.priceMeta;
        formatPriceLabel.appendChild(formatPriceContent);
        formatPriceContent.style.borderWidth = "0px";
        let formatPriceLine = document.createElement("hr");
        formatPriceLine.className = "diagonal";
        formatPriceLabel.appendChild(formatPriceLine);
        formatDiscountView.appendChild(formatPriceLabel);
    }
    /**
     * 现价
     */
    let formatPricingLabel = document.createElement("div");
    formatPricingLabel.className = "formatLabel";
    formatPricingLabel.style.cursor = "default";
    formatPricingLabel.innerHTML = formatEntity.pricing + formatEntity.priceMeta;
    formatDiscountView.appendChild(formatPricingLabel);

    /**
     * 计数器
     */
    let formatCounterView = document.createElement("div");
    formatCounterView.className = "formatLabel";
    /**
     * 数值区
     */
    let formatCounterEdit = document.createElement("input");
    formatCounterEdit.style.width = "48px";
    formatCounterEdit.style.height = "26px";
    formatCounterEdit.style.borderWidth = "0px";
    formatCounterEdit.style.marginRight = "0px";
    formatCounterEdit.style.textAlign = "center";
    formatCounterEdit.style.fontSize = "1rem";
    formatCounterEdit.readOnly = "true";
    formatCounterEdit.value = 1;
    formatCounterView.appendChild(formatCounterEdit);
    /**
     * 操作区
     */
    let formatCounterOperator = document.createElement("div");
    formatCounterOperator.className = "formatLabel";
    formatCounterOperator.style.width = "30px";
    formatCounterOperator.style.borderWidth = "0px";
    formatCounterOperator.style.marginRight = "0px";
    formatCounterOperator.style.float = "right";
    let formatCounterAdd = document.createElement("div");
    formatCounterAdd.className = "counter";
    formatCounterAdd.style.borderTopWidth = "0px";
    formatCounterAdd.style.borderRightWidth = "0px";
    formatCounterAdd.innerHTML = "+";
    formatCounterAdd.onclick = function () {
        formatCounterEdit.value = parseInt(formatCounterEdit.value) + 1;
    };
    formatCounterOperator.appendChild(formatCounterAdd);
    let formatCounterMinus = document.createElement("div");
    formatCounterMinus.className = "counter";
    formatCounterMinus.style.borderRightWidth = "0px";
    formatCounterMinus.style.borderBottomWidth = "0px";
    formatCounterMinus.innerHTML = "-";
    formatCounterMinus.onclick = function () {
        if (parseInt(formatCounterEdit.value) > 1){
            formatCounterEdit.value = parseInt(formatCounterEdit.value) - 1;
        }
    };
    formatCounterOperator.appendChild(formatCounterMinus);
    formatCounterView.appendChild(formatCounterOperator);
    formatDiscountView.appendChild(formatCounterView);

    let buyNow = document.createElement("div");
    buyNow.className = "formatLabel button";
    buyNow.innerHTML = "立即购买";
    buyNow.onclick = function () {
        let url = BASE_PATH + "pb?formatId=" + formatEntity.formatId;
        window.open(url);
    };
    formatDiscountView.appendChild(buyNow);

    let putInCart = document.createElement("div");
    putInCart.className = "formatLabel button";
    putInCart.innerHTML = "加入购物车";
    putInCart.onclick = function () {
        let requestFormatEntity = new Object();
        requestFormatEntity.formatId = formatEntity.formatId;
        requestFormatEntity.amount = formatCounterEdit.value;
        requestPutInCart(requestFormatEntity);
    };
    formatDiscountView.appendChild(putInCart);
    return formatDiscountView;
}


function createPutInCartResultView(data) {
    let resultView = document.createElement("div");
    resultView.id = "receiverEditorView";
    resultView.className = "receiverEditor";
    resultView.style.position = "absolute";
    resultView.style.width = "600px";
    resultView.style.height = "100px";
    resultView.style.top = getScrollTop() + 100 + "px";
    resultView.style.left = "50%";
    resultView.style.marginLeft = "-300px";
    resultView.style.zIndex = "10";
    resultView.style.opacity = "1";
    resultView.style.borderWidth = "1px";
    resultView.style.borderColor = "#CCCCCC";
    resultView.style.borderStyle = "solid";
    resultView.style.backgroundColor = "#FFFFFF";

    let keepGoon = document.createElement("div");
    keepGoon.style.float = "left";
    keepGoon.style.width = "299px";
    keepGoon.style.height = "40px";
    keepGoon.style.lineHeight = "40px";
    keepGoon.style.textAlign = "center";
    keepGoon.style.marginTop = "30px";
    keepGoon.style.backgroundColor = "red";
    keepGoon.style.cursor = "pointer";
    keepGoon.innerHTML = "继续购物";
    keepGoon.onclick = function () {
        dismissMaskView();
        document.body.removeChild(keepGoon.parentNode);
    };
    let goToCart = document.createElement("div");
    goToCart.style.float = "right";
    goToCart.style.width = "299px";
    goToCart.style.height = "40px";
    goToCart.style.lineHeight = "40px";
    goToCart.style.textAlign = "center";
    goToCart.style.marginTop = "30px";
    goToCart.style.backgroundColor = "red";
    goToCart.style.cursor = "pointer";
    goToCart.innerHTML = "去购物车结算";
    goToCart.onclick = function () {
        dismissMaskView();
        document.body.removeChild(keepGoon.parentNode);
        let url = BASE_PATH + "pm?accountId=test&dir=cart";
        window.open(url,"_self");
    };
    resultView.appendChild(keepGoon);
    resultView.appendChild(goToCart);

    document.body.appendChild(resultView);
}