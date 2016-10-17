/**
 * Created by dingpengwei on 8/23/16.
 */
window.onload = function () {
    initTitleView();
    requestLinker();
    let accountId = document.getElementById("accountId") == undefined ? null : document.getElementById("accountId").content;
    if (accountId == undefined || accountId == null || accountId == "") {
        requestBillingByAnonymous();
    } else {
        let mainView = document.getElementById(MAIN);
        mainView.innerHTML = null;
        mainView.style.height = "280px";
        let receiverContainer = document.createElement("div");
        receiverContainer.className = "containerStyle";
        receiverContainer.style.height = "120px";
        receiverContainer.style.minHeight = "120px";
        let payStyleContainer = document.createElement("div");
        payStyleContainer.className = "containerStyle";
        payStyleContainer.appendChild(createPayStyleContainer());
        let productContainer = document.createElement("div");
        productContainer.className = "containerStyle";
        mainView.appendChild(receiverContainer);
        mainView.appendChild(payStyleContainer);
        mainView.appendChild(productContainer);
        let userEntity = new Object();
        userEntity.sessionId = accountId;
        requestUserReceiver(userEntity,receiverContainer);
        requestCart(productContainer);
    }
};

function requestBillingByAnonymous() {
    let formatIds = document.getElementById("productIds") == undefined ? null : document.getElementById("productIds").content;
    let formatEntity = new Object();
    formatEntity.formatId = formatIds;
    let url = BASE_PATH + "format/retrieveTreeInversion?p=" + JSON.stringify(formatEntity);
    asyncRequestByGet(url, function (data) {
        var result = checkResponseDataFormat(data);
        if (result) {
            var jsonData = JSON.parse(data);
            createBillingByAnonymous(jsonData.data);
        }
    }, onErrorCallback, onTimeoutCallback);
}

function requestUserReceiver(userEntity,receiverContainer) {
    let url = BASE_PATH + "receiver/retrieves?p=" + JSON.stringify(userEntity);
    asyncRequestByGet(url, function (data) {
        var result = checkResponseDataFormat(data);
        if (result) {
            var jsonData = JSON.parse(data);
            receiverContainer.appendChild(createUserReceiverContainer(jsonData.data,function (height) {
                receiverContainer.parentNode.style.height = (receiverContainer.parentNode.clientHeight + height) + "px";
            }));
        }
    }, onErrorCallback, onTimeoutCallback);
}

function requestCart(productContainer) {
    let mappingIds = document.getElementById("productIds") == undefined ? null : document.getElementById("productIds").content;
    let cartEntity = new Object();
    cartEntity.sessionId = "test";
    cartEntity.mappingIds = mappingIds;
    let url = BASE_PATH + "cart/retrieves?p=" + JSON.stringify(cartEntity);
    asyncRequestByGet(url, function (data) {
        var result = checkResponseDataFormat(data);
        if (result) {
            var jsonData = JSON.parse(data);
            console.log(jsonData);
            fillingProductContainer(productContainer,jsonData.data,function (height) {
                productContainer.parentNode.style.height = (productContainer.parentNode.clientHeight + height) + "px";
            });
        }
    }, onErrorCallback, onTimeoutCallback);
}

function requestCreateOrder(orderEntity) {
    let url = BASE_PATH + "order/create?p="  + JSON.stringify(orderEntity);
    asyncRequestByGet(url, function (data) {
        var result = checkResponseDataFormat(data);
        if (result) {
            var jsonData = JSON.parse(data);
            if (orderEntity.sessionId == undefined) {
                onRequestAnonymousCreateOrderCallback(jsonData.data);
            } else {
                onRequestUserCreateOrderCallback(jsonData.data);
            }
        }
    }, onErrorCallback, onTimeoutCallback);
}

/**
 * 创建匿名订单
 * @param data
 */
function onRequestAnonymousCreateOrderCallback(data) {
    let url = BASE_PATH + "pq?orderId=" + data.orderId;
    window.open(url, "_self");
}

/**
 * 创建用户订单
 * @param data
 */
function onRequestUserCreateOrderCallback(data) {
    let url = BASE_PATH + "pm?accountId=test&dir=order";
    window.open(url, "_self");
}

/**
 * 创建匿名支付信息
 * @param data
 */
function createBillingByAnonymous(formatEntity) {
    let mainView = document.getElementById(MAIN);
    mainView.innerHTML = null;

    let receiverView = createAnonymousReceiverContainer();
    mainView.appendChild(receiverView);

    let payStyleView = createPayStyleContainer();
    mainView.appendChild(payStyleView);
    let formatEntities = new Array();
    formatEntities.push(formatEntity);
    let productView = createProductContainer(formatEntities);
    mainView.appendChild(productView);

    let payBarView = createPayBarContainer(formatEntity);
    mainView.appendChild(payBarView);

    mainView.style.height = receiverView.clientHeight + payStyleView.clientHeight + productView.clientHeight + payBarView.clientHeight + "px";
}

/**
 * 创建用户支付信息
 * @param data
 */
function fillingProductContainer(productContainer, cartEntities,resetHeightCallback) {
    let length = cartEntities == undefined ? 0:cartEntities.length;
    let formatEntities = new Array();
    for (let i=0;i<length;i++){
        let cartEntity = cartEntities[i];
        let formatEntity = cartEntity.formatEntity;
        formatEntity.amount = cartEntity.amount;
        formatEntities.push(formatEntity);
    }
    let productView = createProductContainer(formatEntities);
    productContainer.appendChild(productView);

    let payBarView = createPayBarContainer(cartEntities);
    productContainer.appendChild(payBarView);
    productContainer.style.height =  productView.clientHeight + payBarView.clientHeight + "px";
    resetHeightCallback(productContainer.clientHeight);
}

function createAnonymousReceiverContainer() {
    let receiverContainer = document.createElement("div");
    receiverContainer.className = "receiverContainer containerStyle";
    let receiverMessage = document.createElement("div");
    receiverMessage.className = "messageLabel";
    receiverMessage.innerHTML = "购买人信息<span style='color: #FF0000;'>（为了能和您取得联系请填写真实信息，如不填写则认为购买和收货是同一人）</span>";
    receiverContainer.appendChild(receiverMessage);

    let buyerInfoMessageLine = document.createElement("div");
    buyerInfoMessageLine.className = "messageLabel";
    buyerInfoMessageLine.style.height = "35px";
    buyerInfoMessageLine.style.marginTop = "5px";
    let buyerNameLabel = document.createElement("div");
    buyerNameLabel.className = "messageLabelInItem";
    buyerNameLabel.style.marginTop = "0px";
    buyerNameLabel.style.marginLeft = "0px";
    buyerNameLabel.innerHTML = "姓名:";
    buyerInfoMessageLine.appendChild(buyerNameLabel);
    let buyerNameEditor = document.createElement("input");
    buyerNameEditor.id = "senderName";
    buyerNameEditor.className = "editor";
    buyerNameEditor.style.float = "left";
    buyerNameEditor.style.marginRight = "20px";
    buyerInfoMessageLine.appendChild(buyerNameEditor);
    let buyerPhoneLabel = document.createElement("div");
    buyerPhoneLabel.className = "messageLabelInItem";
    buyerPhoneLabel.style.marginTop = "0px";
    buyerPhoneLabel.innerHTML = "电话:";
    buyerInfoMessageLine.appendChild(buyerPhoneLabel);
    let buyerPhoneEditor = document.createElement("input");
    buyerPhoneEditor.id = "senderPhone";
    buyerPhoneEditor.className = "editor";
    buyerPhoneEditor.style.float = "left";
    buyerInfoMessageLine.appendChild(buyerPhoneEditor);
    receiverContainer.appendChild(buyerInfoMessageLine);
    let receiverInfoMessageLine = createReceiverAddressEditorContainer(undefined);
    receiverContainer.appendChild(receiverInfoMessageLine);
    return receiverContainer;
}

function createUserReceiverContainer(receiverEntities,onResetHeightCallback) {
    let receiverContainer = document.createElement("div");
    receiverContainer.className = "receiverContainer containerStyle";
    let receiverMessage = document.createElement("div");
    receiverMessage.className = "messageLabel";
    receiverMessage.innerHTML = "收货人信息";
    receiverContainer.appendChild(receiverMessage);
    let currentReceiverContainer = createReceiverAddressEditorContainer(receiverEntities[0]);
    receiverContainer.appendChild(currentReceiverContainer);

    let moreReceiverTip = document.createElement("div");
    moreReceiverTip.className = "messageLabel";
    moreReceiverTip.style.width = "120px";
    moreReceiverTip.style.height = "40px";
    moreReceiverTip.style.lineHeight = "40px";
    moreReceiverTip.innerHTML = "更多收货地址 ︾ ";
    moreReceiverTip.style.cursor = "pointer";
    moreReceiverTip.status = "less";
    receiverContainer.appendChild(moreReceiverTip);
    let moreReceiverContainer = createMoreReceiverAddressContainer(receiverEntities);
    // moreReceiverTip.onclick = function () {
    //     if (this.status == "less") {
    //         moreReceiverTip.innerHTML = "更多收货地址 ︾ ";
    //         this.status = "more";
    //         onResetHeightCallback(moreReceiverContainer.customerHeight);
    //         receiverContainer.appendChild(moreReceiverContainer);
    //     } else if (this.status == "more") {
    //         moreReceiverTip.innerHTML = "更多收货地址 ︽ ";
    //         this.status = "less";
    //         receiverContainer.parentNode.style.height = "120px";
    //         receiverContainer.removeChild(moreReceiverContainer);
    //     }
    // };
    receiverContainer.style.height = "120px";
    return receiverContainer;
}

function createMoreReceiverAddressContainer(receiverEntities) {
    let moreAddressContainer = document.createElement("div");
    moreAddressContainer.style.color = "black";
    let length = receiverEntities == undefined ? 0:receiverEntities.length;
    for (let i = 0; i < length; i++) {
        let receiverEntity = receiverEntities[i];
        moreAddressContainer.appendChild(createReceiverAddressEditorContainer(receiverEntity));
    }
    if (receiverEntities == undefined || receiverEntities.length < 10) {
        let addNewReceiver = document.createElement("div");
        addNewReceiver.className = "billingReceiverItem";
        addNewReceiver.style.textAlign = "center";
        addNewReceiver.style.borderColor = "#000000";
        addNewReceiver.innerHTML = "+";
        moreAddressContainer.appendChild(addNewReceiver);
    }
    moreAddressContainer.style.height = (length * 30 + (receiverEntities.length < 10 ? 1 : 0 * 30)) + "px";
    moreAddressContainer.customerHeight = length * 30 + ((receiverEntities.length < 10 ? 1 : 0)* 30) ;
    return moreAddressContainer;

}

function createReceiverAddressEditorContainer(data) {
    let receiverInfoMessageLine = document.createElement("div");
    receiverInfoMessageLine.className = "billingReceiverItem";
    receiverInfoMessageLine.style.height = "40px";
    receiverInfoMessageLine.style.lineHeight = "40px";
    receiverInfoMessageLine.bindData = data;
    if (data == undefined) {
        receiverInfoMessageLine.innerHTML = "双击编辑收货人信息";
    } else {
        addCurrentReceiverViewToContainer(receiverInfoMessageLine, data)
    }

    receiverInfoMessageLine.ondblclick = function () {
        showReceiverEditorView(receiverInfoMessageLine.bindData, function (data) {
            receiverInfoMessageLine.bindData = data;
            addCurrentReceiverViewToContainer(receiverInfoMessageLine, data)
        });
    };

    return receiverInfoMessageLine;
}

/**
 * 当前收货人信息
 * @param container
 * @param data
 */
function addCurrentReceiverViewToContainer(container, data) {
    container.innerHTML = null;
    container.style.fontSize = "1rem";
    let nameLabel = document.createElement("div");
    nameLabel.id = "RName";
    nameLabel.className = "messageLabelInItem";
    nameLabel.style.borderWidth = "1px";
    nameLabel.innerHTML = data.name;
    let phoneLabel = document.createElement("div");
    phoneLabel.id = "RPhone";
    phoneLabel.className = "messageLabelInItem";
    phoneLabel.innerHTML = data.phone0;
    let phoneBakLabel = document.createElement("div");
    phoneBakLabel.id = "RPhoneBak";
    phoneBakLabel.className = "messageLabelInItem";
    phoneBakLabel.innerHTML = data.phone1;
    let provinceLabel = document.createElement("div");
    provinceLabel.id = "RProvince";
    provinceLabel.className = "messageLabelInItem";
    provinceLabel.innerHTML = data.province;
    let cityLabel = document.createElement("div");
    cityLabel.id = "RCity";
    cityLabel.className = "messageLabelInItem";
    cityLabel.innerHTML = data.city;
    let countyLabel = document.createElement("div");
    countyLabel.id = "RCounty";
    countyLabel.className = "messageLabelInItem";
    countyLabel.innerHTML = data.county;
    let townLabel = document.createElement("div");
    townLabel.id = "RTown";
    townLabel.className = "messageLabelInItem";
    townLabel.innerHTML = data.town;
    let villageLabel = document.createElement("div");
    villageLabel.id = "RVillage";
    villageLabel.className = "messageLabelInItem";
    villageLabel.innerHTML = data.village;
    let appendLabel = document.createElement("div");
    appendLabel.id = "RAppend";
    appendLabel.className = "messageLabelInItem";
    appendLabel.innerHTML = data.append;

    container.appendChild(nameLabel);
    container.appendChild(phoneLabel);
    container.appendChild(phoneBakLabel);
    container.appendChild(provinceLabel);
    container.appendChild(cityLabel);
    container.appendChild(countyLabel);
    container.appendChild(townLabel);
    container.appendChild(villageLabel);
    container.appendChild(appendLabel);
}

/**
 * 创建支付类型
 * @returns {Element}
 */
function createPayStyleContainer() {
    let payStyleContainer = document.createElement("div");
    payStyleContainer.className = "payStyleContainer containerStyle";

    let payStyleMessage = document.createElement("div");
    payStyleMessage.className = "messageLabel";
    payStyleMessage.innerHTML = "请选择支付方式";
    payStyleContainer.appendChild(payStyleMessage);

    let payStyleView = document.createElement("div");
    payStyleView.className = "messageLabel";
    payStyleView.style.height = "120px";

    let payWei = document.createElement("img");
    payWei.className = "payStyleSelect";
    payWei.src = "http://localhost:8080/foodslab/webapp/asserts/images/paywei.png";
    payWei.style.marginRight = "20px";

    let payZhi = document.createElement("img");
    payZhi.className = "payStyleNormal";
    payZhi.src = "http://localhost:8080/foodslab/webapp/asserts/images/payzhi.png";
    payStyleView.appendChild(payWei);
    payStyleView.appendChild(payZhi);
    payStyleContainer.appendChild(payStyleView);

    payWei.onclick = function () {
        payWei.className = "payStyleSelect";
        payZhi.className = "payStyleNormal";
    };
    payZhi.onclick = function () {
        payZhi.className = "payStyleSelect";
        payWei.className = "payStyleNormal";
    };

    return payStyleContainer;
}

function createProductContainer(formatEntities) {
    let productContainer = document.createElement("div");
    productContainer.className = "productContainer containerStyle";
    let listMessage = document.createElement("div");
    listMessage.className = "messageLabel";
    listMessage.innerHTML = "发货清单";
    productContainer.appendChild(listMessage);
    let length = formatEntities == undefined ? 0 : formatEntities.length;
    for (let i = 0; i < length; i++) {
        let formatEntity = formatEntities[i];
        let productItemContainer = document.createElement("div");
        productItemContainer.className = "productItemView";

        let productImg = document.createElement("div");
        productImg.className = "productImg";
        productItemContainer.appendChild(productImg);

        let productName = document.createElement("div");
        productName.className = "messageLabelInItem";
        productName.style.width = "470px";
        productName.innerHTML = formatEntity.parent.parent.label + " " + formatEntity.parent.label + " " + formatEntity.label + formatEntity.meta;
        productItemContainer.appendChild(productName);

        let productPricing = document.createElement("div");
        productPricing.className = "messageLabelInItem";
        productPricing.style.width = "190px";
        productPricing.style.textAlign = "center";
        productPricing.innerHTML = formatEntity.pricing + formatEntity.priceMeta;
        productItemContainer.appendChild(productPricing);

        let productAmount = document.createElement("div");
        productAmount.className = "messageLabelInItem";
        productAmount.style.width = "190px";
        productAmount.style.textAlign = "center";
        productAmount.innerHTML = formatEntity.amount;
        productItemContainer.appendChild(productAmount);

        productContainer.appendChild(productItemContainer);
    }

    productContainer.style.height = 120 * length + 40 + "px";
    return productContainer;
}

function createPayBarContainer(data) {
    let payBarContainer = document.createElement("div");
    payBarContainer.className = "payBarContainer";
    payBarContainer.style.borderWidth = "0px";

    let payAction = document.createElement("div");
    payAction.className = "payBarItem";
    payAction.style.backgroundColor = "red";
    payAction.style.color = "#FFFFFF";
    payAction.style.textAlign = "center";
    payAction.style.cursor = "pointer";
    payAction.innerHTML = "结算";
    payAction.onclick = function () {
        onPayActionClick();
    };
    payBarContainer.appendChild(payAction);

    let buyInfoView = document.createElement("div");
    buyInfoView.className = "payBarContainer";
    buyInfoView.style.width = "798px";
    let postage = document.createElement("div");
    postage.className = "payBarItem";
    postage.innerHTML = "运费:";
    buyInfoView.appendChild(postage);

    let price = document.createElement("div");
    price.className = "payBarItem";
    price.innerHTML = "商品总价:";
    buyInfoView.appendChild(price);

    let amount = document.createElement("div");
    amount.className = "payBarItem";
    amount.innerHTML = "共选购" + "N" + "件商品";
    buyInfoView.appendChild(amount);

    payBarContainer.appendChild(buyInfoView);

    return payBarContainer;
}

function onPayActionClick() {
    let name = document.getElementById("RName") == undefined ? undefined : document.getElementById("RName").innerHTML;
    let province = document.getElementById("RProvince") == undefined ? undefined : document.getElementById("RProvince").innerHTML;
    let city = document.getElementById("RCity") == undefined ? undefined : document.getElementById("RCity").innerHTML;
    let county = document.getElementById("RCounty") == undefined ? undefined : document.getElementById("RCounty").innerHTML;
    let town = document.getElementById("RTown") == undefined ? undefined : document.getElementById("RTown").innerHTML;
    let village = document.getElementById("RVillage") == undefined ? undefined : document.getElementById("RVillage").innerHTML;
    let append = document.getElementById("RAppend") == undefined ? undefined : document.getElementById("RAppend").innerHTML;
    let phone0 = document.getElementById("RPhone") == undefined ? undefined : document.getElementById("RPhone").innerHTML;
    let phone1 = document.getElementById("RPhoneBak") == undefined ? undefined : document.getElementById("RPhoneBak").innerHTML;

    if (isNullValue(name)) {
        new Toast().show("请输入收货人姓名");
        return;
    }

    if (isNullValue(phone0)) {
        new Toast().show("请输入收货人电话");
        return;
    }

    if (isNullValue(province)) {
        new Toast().show("请完善收货人地址");
        return;
    }

    if (isNullValue(city)) {
        new Toast().show("请完善收货人地址");
        return;
    }

    if (isNullValue(county)) {
        new Toast().show("请完善收货人地址");
        return;
    }

    if (isNullValue(town)) {
        new Toast().show("请完善收货人地址");
        return;
    }

    if (isNullValue(village)) {
        new Toast().show("请完善收货人地址");
        return;
    }
    let accountId = document.getElementById("accountId") == undefined ? null : document.getElementById("accountId").content;
    let productIds = document.getElementById("productIds") == undefined ? null : document.getElementById("productIds").content;
    if (accountId == undefined) {
        let senderName = document.getElementById("senderName").value;
        let senderPhone = document.getElementById("senderPhone").value;
        showPaymentView(function () {
            let orderEntity = new Object();
            orderEntity.productIds = productIds.split(",");
            let receiver = new Object();
            orderEntity.senderName = senderName;
            orderEntity.senderPhone = senderPhone;
            receiver.name = name;
            receiver.phone0 = phone0;
            receiver.phone1 = phone1;
            receiver.province = province;
            receiver.city = city;
            receiver.county = county;
            receiver.town = town;
            receiver.village = village;
            receiver.append = append;
            orderEntity.receiver = receiver;
            requestCreateOrder(orderEntity);
        });
    } else {
        showPaymentView(function () {
            let orderEntity = new Object();
            orderEntity.sessionId = accountId;
            orderEntity.productIds = productIds.split(",");
            orderEntity.receiverId = "50cbf344-c92c-43ce-88f7-7fbe21e8e478";
            requestCreateOrder(orderEntity);
        });
    }


}