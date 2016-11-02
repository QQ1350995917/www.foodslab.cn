/**
 * Created by dingpengwei on 10/30/16.
 */

function loadAnonymousBilling(receiverContainer,productContainer,payBarContainer) {
    attachAnonymousReceiverContainer(receiverContainer);
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
                productContainer.parentNode.style.height = (productContainer.parentNode.clientHeight + height) + "px";
            });
            attachPayBarContainer(payBarContainer, formatEntities);
        }
    }, onErrorCallback, onTimeoutCallback);
}

function attachAnonymousReceiverContainer(container) {
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
    let currentReceiverContainer = document.createElement("div");
    currentReceiverContainer.id = "currentReceiverId";
    currentReceiverContainer.className = "billingMoreReceiverItem";
    attachCurrentReceiverToContainer(currentReceiverContainer,undefined,false);
    container.appendChild(currentReceiverContainer);
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