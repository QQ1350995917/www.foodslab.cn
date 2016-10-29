/**
 * Created by dingpengwei on 9/8/16.
 */
function requestMineOrder() {
    let orderEntity = new Object();
    orderEntity.cs = getCookie(KEY_CS);
    let url = BASE_PATH + "order/retrieves?p=" + JSON.stringify(orderEntity);
    asyncRequestByGet(url, function (data) {
        var result = checkResponseDataFormat(data);
        if (result) {
            var jsonData = JSON.parse(data);
            onRequestOrderCallback(jsonData.data);
        }
    }, onErrorCallback, onTimeoutCallback);
}


function onRequestOrderCallback(orderEntities) {
    let mainView = document.getElementById(MAIN);
    mainView.innerText = null;
    mainView.style.height = 0;
    let length = orderEntities == undefined ? 0 : orderEntities.length;
    for (let i = 0; i < length; i++) {
        let orderItemView = createOrderItemView(orderEntities[i]);
        mainView.appendChild(orderItemView);
        mainView.style.height = mainView.clientHeight + orderItemView.clientHeight + "px";
    }
}

function createOrderItemView(orderEntity) {
    let itemContainer = document.createElement("div");
    itemContainer.className = "itemContainer";
    itemContainer.customerHeight = 0;
    let orderItemTitleView = createOrderItemTitleView(orderEntity)
    itemContainer.appendChild(orderItemTitleView);
    itemContainer.customerHeight = orderItemTitleView.customerHeight;

    let orderProductsContainer = createOrderProductItem(orderEntity.formatEntities);
    itemContainer.customerHeight = itemContainer.customerHeight + orderProductsContainer.customerHeight;
    itemContainer.appendChild(orderProductsContainer);

    let orderReceiverView = createOrderReceiverView();
    orderReceiverView.style.height = itemContainer.customerHeight - 41 + "px";
    itemContainer.appendChild(orderReceiverView);

    let orderMoneyView = createOrderMoneyView();
    orderMoneyView.style.height = itemContainer.customerHeight - 41 + "px";
    itemContainer.appendChild(orderMoneyView);

    let orderStatusView = createOrderStatusView(orderEntity);
    orderStatusView.style.height = itemContainer.customerHeight - 41 + "px";
    itemContainer.appendChild(orderStatusView);

    itemContainer.style.height = itemContainer.customerHeight + "px";

    return itemContainer;
}

function createOrderItemTitleView(orderEntity) {
    let itemTitleContainer = document.createElement("div");
    itemTitleContainer.className = "itemTitleContainer";
    itemTitleContainer.customerHeight = 40;
    itemTitleContainer.innerHTML = new Date(orderEntity.createTime).format("yyyy-MM-dd hh:mm") + " " + " 订单号: " + orderEntity.orderId;
    return itemTitleContainer;
}

function createOrderProductItem(formatEntities) {
    let productContainer = document.createElement("div");
    productContainer.className = "productContainer";
    productContainer.customerHeight = 0;
    let length = formatEntities == undefined ? 0 : formatEntities.length;
    for (let i = 0; i < length; i++) {
        let formatEntity = formatEntities[i];
        let productItemView = document.createElement("div");
        productItemView.className = "productItemView";
        productItemView.customerHeight = 101;

        let icon = document.createElement("div");
        icon.className = "icon";
        productItemView.appendChild(icon);

        let name = document.createElement("div");
        name.className = "label";
        name.style.width = "350px";
        name.style.textAlign = "left";
        name.style.marginLeft = "10px";
        name.innerHTML = formatEntity.parent.parent.label + " " + formatEntity.parent.label + " " + formatEntity.label + formatEntity.meta;
        productItemView.appendChild(name);

        let num = document.createElement("div");
        num.className = "label";
        num.style.width = "60px";
        num.innerHTML = "X3";
        productItemView.appendChild(num);

        productContainer.appendChild(productItemView);
        productContainer.customerHeight = productContainer.customerHeight + productItemView.customerHeight;
    }
    productContainer.style.height = productContainer.customerHeight + "px";
    return productContainer;
}

function createOrderReceiverView() {
    let receiverContainer = document.createElement("div");
    receiverContainer.className = "orderReceiverView";
    receiverContainer.style.width = "149px";
    let receiverView = document.createElement("div");
    receiverView.style.height = "40px";
    receiverView.style.lineHeight = "40px";
    receiverView.innerHTML = "收货人";
    receiverContainer.appendChild(receiverView);
    return receiverContainer;
}

function createOrderMoneyView() {
    let orderMoneyContainer = document.createElement("div");
    orderMoneyContainer.className = "orderReceiverView";
    let moneyView = document.createElement("div");
    moneyView.style.height = "40px";
    moneyView.style.lineHeight = "40px";
    moneyView.innerHTML = "$1000";
    orderMoneyContainer.appendChild(moneyView);
    return orderMoneyContainer;
}

function createOrderStatusView(orderEntity) {
    let orderStatusContainer = document.createElement("div");
    orderStatusContainer.className = "orderReceiverView";
    orderStatusContainer.style.width = "227px";
    let statusView = document.createElement("div");
    statusView.style.height = "40px";
    statusView.style.lineHeight = "40px";
    if (orderEntity.status == 1) {
        statusView.innerHTML = "已付款,待发货";
    } else if (orderEntity.status == 2) {
        let orderExpressing = document.createElement("div");
        orderExpressing.innerHTML = "已发货,待收货";
        orderStatusContainer.appendChild(orderExpressing);
        let orderExpressed = document.createElement("div");
        orderExpressed.innerHTML = "确认收货";
        orderExpressed.style.cursor = "pointer";
        orderExpressed.onclick = function () {
            let requestOrderEntity = new Object();
            requestOrderEntity.cs = getCookie(KEY_CS);
            requestOrderEntity.orderId = orderEntity.orderId;
            let url = BASE_PATH + "order/expressed?p=" + JSON.stringify(requestOrderEntity);
            asyncRequestByGet(url, function (data) {
                var result = checkResponseDataFormat(data);
                if (result) {
                    var jsonData = JSON.parse(data);
                    if (jsonData.code == RC_SUCCESS) {
                        requestMineOrder();
                    } else {
                        new Toast().show("操作失败");
                    }
                }
            }, onErrorCallback, onTimeoutCallback);
        };
        orderStatusContainer.appendChild(orderExpressed);
    } else if (orderEntity.status == 3) {
        statusView.innerHTML = "已收货,已完成";
    } else {
        statusView.innerHTML = "未知状态";
    }
    orderStatusContainer.appendChild(statusView);
    return orderStatusContainer;
}