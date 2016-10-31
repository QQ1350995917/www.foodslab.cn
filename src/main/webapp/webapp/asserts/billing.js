/**
 * Created by dingpengwei on 8/23/16.
 * 页面内容显示区域共分为四部分
 * 1:收货人信息区域
 * 2:支付方式区域
 * 3:支付产品列表区域
 * 4:支付工具栏区域
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

    if (isNullValue(getCookie(KEY_CS))) {
        loadAnonymousBilling(receiverContainer, productContainer, payBarContainer);
    } else {
        loadNamedBilling(receiverContainer, productContainer, payBarContainer)
    }

    attachPayStyleContainer(payStyleContainer, function () {

    });


};

/**
 * 查询登录状态完成时候的billing处理方式
 * @param data
 */
function onBillingRequestSessionStatusCommonCallback(data) {
    var jsonData = JSON.parse(data);
    if (jsonData.code == RC_SUCCESS) {
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

function attachCurrentReceiverToContainer(receiverContainer,receiverEntity, submit) {
    receiverContainer.innerHTML = null;
    if (receiverEntity == undefined) {
        receiverContainer.innerHTML = "点击编辑收货人信息";
    } else {
        receiverContainer.bindReceiverId = receiverEntity.receiverId;
        attachCurrentReceiverInfoToView(receiverContainer, receiverEntity)
    }
    receiverContainer.onclick = function () {
        showReceiverEditorView(receiverEntity, function (newReceiverEntity) {
            receiverContainer.bindReceiverId = newReceiverEntity.receiverId;
            attachCurrentReceiverInfoToView(receiverContainer, newReceiverEntity);
        }, submit);
    };
}

/**
 * 收货人信息展示
 * @param container
 * @param receiverEntity
 */
function attachCurrentReceiverInfoToView(container, receiverEntity) {
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

    let productAmount = 0;
    let pricingAmount = 0;
    let length = formatEntities == undefined ? 0 : formatEntities.length;
    for (let i = 0; i < length; i++) {
        productAmount = productAmount + formatEntities[i].amount;
        pricingAmount = pricingAmount + (formatEntities[i].formatEntity.pricing * formatEntities[i].amount);
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
    price.innerHTML = "商品总价:" + pricingAmount + "";
    buyInfoView.appendChild(price);

    let amount = document.createElement("div");
    amount.className = "payBarItem";
    amount.innerHTML = "共选购" + productAmount + "件商品";
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
            if (jsonData.code = RC_SUCCESS) {
                let requestObject = new Object();
                requestObject.orderId = jsonData.data.orderId;
                let url = BASE_PATH + "pq?p=" + JSON.stringify(requestObject);
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
            if (jsonData.code == RC_SUCCESS) {
                let pageEntity = new Object();
                pageEntity.cs = getCookie(KEY_CS);
                pageEntity.dir = "order";
                let url = BASE_PATH + "pm?p=" + JSON.stringify(pageEntity);
                window.open(url, "_self");
            }
        }
    }, onErrorCallback, onTimeoutCallback);
}

