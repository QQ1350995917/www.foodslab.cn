/**
 * Created by dingpengwei on 8/23/16.
 */
window.onload = function () {
    this.initFrameView();
    requestSessionStatus(onBillingRequestSessionStatusCommonCallback);

    let mainView = document.getElementById(MAIN);
    let receiverContainer = document.createElement("div");
    receiverContainer.className = "HFillContainer BHContainer";
    mainView.appendChild(receiverContainer);

    let payStyleContainer = document.createElement("div");
    payStyleContainer.className = "HFillContainer BHContainer";
    payStyleContainer.style.height = "170px";
    mainView.appendChild(payStyleContainer);

    let productContainer = document.createElement("div");
    productContainer.className = "HFillContainer BHContainer";
    mainView.appendChild(productContainer);

    let payBarContainer = document.createElement("div");
    payBarContainer.className = "HFillContainer";
    payBarContainer.style.height = "38px";
    mainView.appendChild(payBarContainer);

    let cs = getCookie(KEY_CS);
    if (isNullValue(cs)) {
        attachAnonymousReceiverContainer(receiverContainer, function (data) {
        });

        attachPayStyleContainer(payStyleContainer, function () {
        });

        let formatIds = document.getElementById("productIds") == undefined ? null : document.getElementById("productIds").content;
        let formatEntity = new Object();
        formatEntity.formatId = formatIds;
        let url = BASE_PATH + "format/retrieveTreeInversion?p=" + JSON.stringify(formatEntity);
        asyncRequestByGet(url, function (data) {
            var result = checkResponseDataFormat(data);
            if (result) {
                var jsonData = JSON.parse(data);
                let formatEntities = new Array();
                formatEntities.push(jsonData.data);
                attachProductContainer(productContainer, formatEntities, function (height) {
                    productContainer.style.height = height + "px";
                    mainView.style.height = (mainView.clientHeight + height) + "px";
                });
                attachPayBarContainer(payBarContainer, formatEntities);
            }
        }, onErrorCallback, onTimeoutCallback);
    } else {
        requestSessionStatus(function (data) {
            var result = checkResponseDataFormat(data);
            if (result) {
                var jsonData = JSON.parse(data);
                if (jsonData.code == RESPONSE_SUCCESS) {
                    let requestUserEntity = new Object();
                    requestUserEntity.cs = getCookie(KEY_CS);
                    asyncRequestByGet(BASE_PATH + "receiver/retrieves?p=" + JSON.stringify(requestUserEntity), function (data) {
                        var result = checkResponseDataFormat(data);
                        if (result) {
                            var jsonData = JSON.parse(data);
                            attachUserReceiverContainer(receiverContainer, jsonData.data, function (height) {
                                receiverContainer.parentNode.style.height = (receiverContainer.parentNode.clientHeight + height) + "px";
                            })
                        }
                    }, onErrorCallback, onTimeoutCallback);

                    attachPayStyleContainer(payStyleContainer, function () {
                    });

                    let productIds = document.getElementById("productIds") == undefined ? null : document.getElementById("productIds").content;
                    let cartEntity = new Object();
                    cartEntity.cs = getCookie(KEY_CS);
                    cartEntity.productIds = productIds.split(",");
                    asyncRequestByGet(BASE_PATH + "cart/retrieves?p=" + JSON.stringify(cartEntity), function (data) {
                        var result = checkResponseDataFormat(data);
                        if (result) {
                            var jsonData = JSON.parse(data);
                            attachProductContainer(productContainer, jsonData.data, function (height) {
                                productContainer.style.height = height + "px";
                                productContainer.parentNode.style.height = (productContainer.parentNode.clientHeight + height) + "px";
                            });
                            attachPayBarContainer(payBarContainer, jsonData.data);
                        }
                    }, onErrorCallback, onTimeoutCallback);
                } else {
                    delCookie(KEY_CS);
                }
            }
        });

    }
};

/**
 * 查询登录状态完成时候的billing处理方式
 * @param data
 */
function onBillingRequestSessionStatusCommonCallback(data) {
    var jsonData = JSON.parse(data);
    if (jsonData.code == RESPONSE_SUCCESS) {
        let userEntity = jsonData.data;
        setTitleViewLoginStatus(userEntity);
        document.getElementById(ID_HEADER_MENU_LOGOUT).onclick = function () {
            let requestPageEntity = new Object();
            requestPageEntity.cs = getCookie("cs");
            requestPageEntity.dir = getCookie("order");
            window.open(BASE_PATH, "_self");
        }
    } else {
        showLoginView(function () {
            location.reload();
        })
        delCookie(KEY_CS);
    }
}

/**
 * @param container
 * @param onAttachCallback
 */
function attachAnonymousReceiverContainer(container, onAttachCallback) {
    let receiverMessage = document.createElement("div");
    receiverMessage.className = "messageLabel";
    receiverMessage.innerHTML = "购买人信息<span style='color: #FF0000;'>（为了能和您取得联系请填写真实信息，如不填写则认为购买和收货是同一人）</span>";
    container.appendChild(receiverMessage);

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
    container.appendChild(buyerInfoMessageLine);
    let receiverInfoMessageLine = createReceiverAddressEditorContainer(undefined);
    container.appendChild(receiverInfoMessageLine);
    onAttachCallback();
}


function createReceiverAddressEditorContainer(receiverEntity) {
    let receiverInfoMessageLine = document.createElement("div");
    receiverInfoMessageLine.id = "currentReceiverId";
    receiverInfoMessageLine.className = "billingReceiverItem";
    receiverInfoMessageLine.style.height = "40px";
    receiverInfoMessageLine.style.lineHeight = "40px";
    if (receiverEntity == undefined) {
        receiverInfoMessageLine.innerHTML = "点击编辑收货人信息";
    } else {
        receiverInfoMessageLine.bindReceiverId = receiverEntity.receiverId;
        addCurrentReceiverViewToContainer(receiverInfoMessageLine, receiverEntity)
    }

    receiverInfoMessageLine.onclick = function () {
        showReceiverEditorView(receiverEntity, function (newReceiverEntity) {
            receiverInfoMessageLine.bindReceiverId = newReceiverEntity.receiverId;
            addCurrentReceiverViewToContainer(receiverInfoMessageLine, newReceiverEntity);
        });
    };

    return receiverInfoMessageLine;
}

/**
 * 当前收货人信息
 * @param container
 * @param receiverEntity
 */
function addCurrentReceiverViewToContainer(container, receiverEntity) {
    container.innerHTML = null;
    container.style.fontSize = "1rem";
    let nameLabel = document.createElement("div");
    nameLabel.id = "RName";
    nameLabel.className = "messageLabelInItem";
    nameLabel.style.borderWidth = "1px";
    nameLabel.innerHTML = receiverEntity.name;
    let phoneLabel = document.createElement("div");
    phoneLabel.id = "RPhone";
    phoneLabel.className = "messageLabelInItem";
    phoneLabel.innerHTML = receiverEntity.phone0;
    let phoneBakLabel = document.createElement("div");
    phoneBakLabel.id = "RPhoneBak";
    phoneBakLabel.className = "messageLabelInItem";
    phoneBakLabel.innerHTML = receiverEntity.phone1;
    let provinceLabel = document.createElement("div");
    provinceLabel.id = "RProvince";
    provinceLabel.className = "messageLabelInItem";
    provinceLabel.innerHTML = receiverEntity.province;
    let cityLabel = document.createElement("div");
    cityLabel.id = "RCity";
    cityLabel.className = "messageLabelInItem";
    cityLabel.innerHTML = receiverEntity.city;
    let countyLabel = document.createElement("div");
    countyLabel.id = "RCounty";
    countyLabel.className = "messageLabelInItem";
    countyLabel.innerHTML = receiverEntity.county;
    let townLabel = document.createElement("div");
    townLabel.id = "RTown";
    townLabel.className = "messageLabelInItem";
    townLabel.innerHTML = receiverEntity.town;
    let villageLabel = document.createElement("div");
    villageLabel.id = "RVillage";
    villageLabel.className = "messageLabelInItem";
    villageLabel.innerHTML = receiverEntity.village;
    let appendLabel = document.createElement("div");
    appendLabel.id = "RAppend";
    appendLabel.className = "messageLabelInItem";
    appendLabel.innerHTML = receiverEntity.append;

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
function attachPayStyleContainer(container, onAttachCallback) {
    let payStyleMessage = document.createElement("div");
    payStyleMessage.className = "messageLabel";
    payStyleMessage.innerHTML = "请选择支付方式";
    container.appendChild(payStyleMessage);

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
    container.appendChild(payStyleView);

    payWei.onclick = function () {
        payWei.className = "payStyleSelect";
        payZhi.className = "payStyleNormal";
    };
    payZhi.onclick = function () {
        payZhi.className = "payStyleSelect";
        payWei.className = "payStyleNormal";
    };
    onAttachCallback();
}


/**
 * 匿名用户和登录用户公用的产品处理函数
 * @param container
 * @param formatEntities
 * @param onAttachCallback
 */
function attachProductContainer(container, formatEntities, onAttachCallback) {
    let listMessage = document.createElement("div");
    listMessage.className = "messageLabel";
    listMessage.innerHTML = "发货清单";
    container.appendChild(listMessage);
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
        productItemContainer.appendChild(productName);

        let productPricing = document.createElement("div");
        productPricing.className = "messageLabelInItem";
        productPricing.style.width = "190px";
        productPricing.style.textAlign = "center";

        productItemContainer.appendChild(productPricing);

        let productAmount = document.createElement("div");
        productAmount.className = "messageLabelInItem";
        productAmount.style.width = "190px";
        productAmount.style.textAlign = "center";
        productAmount.innerHTML = formatEntity.amount;
        productItemContainer.appendChild(productAmount);

        if (isNullValue(formatEntity.formatEntity)) {
            productName.innerHTML = formatEntity.parent.parent.label + " " + formatEntity.parent.label + " " + formatEntity.label + formatEntity.meta;
            productPricing.innerHTML = formatEntity.pricing + formatEntity.priceMeta;
        } else {
            productName.innerHTML = formatEntity.formatEntity.parent.parent.label + " " + formatEntity.formatEntity.parent.label + " " + formatEntity.formatEntity.label + formatEntity.formatEntity.meta;
            productPricing.innerHTML = formatEntity.formatEntity.pricing + formatEntity.formatEntity.priceMeta;
        }

        container.appendChild(productItemContainer);
    }
    onAttachCallback(length * 119 + 40);
}

/**
 * 匿名用户和登录用户公用的支付工具条
 * @param container
 * @param formatEntities
 * @returns {Element}
 */
function attachPayBarContainer(container, formatEntities) {
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
    container.appendChild(payAction);

    let length = formatEntities == undefined ? 0 : formatEntities.length;
    for (let i = 0; i < length; i++) {
    }

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

    container.appendChild(buyInfoView);
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

    if (isNullValue(name) || isNullValue(phone0) || isNullValue(province) || isNullValue(city) || isNullValue(county) || isNullValue(town) || isNullValue(village)) {
        new Toast().show("请完善收货人信息");
        return;
    }

    let productIds = document.getElementById("productIds") == undefined ? null : document.getElementById("productIds").content;
    if (isNullValue(getCookie(KEY_CS))) {
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
            requestCreateAnonymousOrder(orderEntity);
        });
    } else {
        showPaymentView(function () {
            let orderEntity = new Object();
            orderEntity.cs = getCookie(KEY_CS);
            orderEntity.productIds = productIds.split(",");
            let currentReceiverId = document.getElementById("currentReceiverId").bindReceiverId;
            if (!isNullValue(currentReceiverId)) {
                orderEntity.receiverId = currentReceiverId;
            }
            requestCreateOrder(orderEntity);
        });
    }
}

function requestCreateAnonymousOrder(orderEntity) {
    let url = BASE_PATH + "order/createAnonymous?p=" + JSON.stringify(orderEntity);
    asyncRequestByGet(url, function (data) {
        var result = checkResponseDataFormat(data);
        if (result) {
            var jsonData = JSON.parse(data);
            if (jsonData.code = RESPONSE_SUCCESS){
                let url = BASE_PATH + "pq?orderId=" + data.orderId;
                window.open(url, "_self");
            }
        }
    }, onErrorCallback, onTimeoutCallback);
}

function requestCreateOrder(orderEntity) {
    let url = BASE_PATH + "order/create?p=" + JSON.stringify(orderEntity);
    asyncRequestByGet(url, function (data) {
        var result = checkResponseDataFormat(data);
        if (result) {
            var jsonData = JSON.parse(data);
            if (jsonData.code == RESPONSE_SUCCESS){
                let pageEntity = new Object();
                pageEntity.cs = getCookie(KEY_CS);
                pageEntity.dir = "order";
                let url = BASE_PATH + "pm?p=" + JSON.stringify(pageEntity);
                window.open(url, "_self");    
            }
        }
    }, onErrorCallback, onTimeoutCallback);
}

function attachUserReceiverContainer(container, receiverEntities, onAttachCallback) {
    let receiverMessage = document.createElement("div");
    receiverMessage.className = "messageLabel";
    receiverMessage.innerHTML = "收货人信息";
    container.appendChild(receiverMessage);
    let currentReceiverContainer = createReceiverAddressEditorContainer(receiverEntities[0]);
    container.appendChild(currentReceiverContainer);

    let moreReceiverTip = document.createElement("div");
    moreReceiverTip.className = "messageLabel";
    moreReceiverTip.style.width = "120px";
    moreReceiverTip.style.height = "40px";
    moreReceiverTip.style.lineHeight = "40px";
    moreReceiverTip.innerHTML = "更多收货地址 ︾ ";
    moreReceiverTip.style.cursor = "pointer";
    moreReceiverTip.status = "less";
    container.appendChild(moreReceiverTip);
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
    container.style.height = "120px";
    onAttachCallback();
}

function createMoreReceiverAddressContainer(receiverEntities) {
    let moreAddressContainer = document.createElement("div");
    moreAddressContainer.style.color = "black";
    let length = receiverEntities == undefined ? 0 : receiverEntities.length;
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
    moreAddressContainer.customerHeight = length * 30 + ((receiverEntities.length < 10 ? 1 : 0) * 30);
    return moreAddressContainer;
}