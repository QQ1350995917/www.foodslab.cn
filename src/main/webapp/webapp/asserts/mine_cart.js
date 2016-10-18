/**
 * Created by dingpengwei on 9/6/16.
 */
function requestMineCart(cs) {
    let cartEntity = new Object();
    cartEntity.cs = cs;
    let mainView = document.getElementById(MAIN);
    mainView.innerHTML = null;
    let url = BASE_PATH + "cart/retrieves?p=" + JSON.stringify(cartEntity);
    asyncRequestByGet(url, function (data) {
        var result = checkResponseDataFormat(data);
        if (result) {
            var jsonData = JSON.parse(data);
            if (jsonData.data == undefined || jsonData.data.length == 0) {
                createEmptyCartView();
            } else {
                let mainView = document.getElementById(MAIN);
                mainView.appendChild(createMainTitleView());
                createMainContentView(mainView, jsonData.data);
                createMainFloatView(mainView);
            }
        }
    }, onErrorCallback, onTimeoutCallback);
}

function requestUpdateNumber(cartEntity) {
    let url = BASE_PATH + "cart/update?p=" + JSON.stringify(cartEntity);
    asyncRequestByGet(url, function (data) {
        var result = checkResponseDataFormat(data);
        if (result) {

        } else {
            new Toast().show("修改失败");
        }
    }, onErrorCallback, onTimeoutCallback);
}

function requestDelete(cartEntity, parentView, currentView) {
    console.log(cartEntity);
    let url = BASE_PATH + "cart/delete?p=" + JSON.stringify(cartEntity);
    asyncRequestByGet(url, function (data) {
        var result = checkResponseDataFormat(data);
        if (result) {
            parentView.removeChild(currentView);
            console.log(parentView.clientHeight);
            parentView.style.height = (parentView.clientHeight - 100) + "px";
            console.log(parentView.clientHeight);
        } else {
            new Toast().show("删除失败");
        }
    }, onErrorCallback, onTimeoutCallback);
}

function createEmptyCartView() {

}

function createMainTitleView() {
    let titleView = document.createElement("div");
    titleView.className = "titleView";
    let selectAll = document.createElement("input");
    selectAll.id = "selectAllCartOnTop";
    selectAll.type = "checkbox";
    selectAll.className = "selector";
    selectAll.onclick = function () {
        onSelectAllCartItem(selectAll.checked);
        document.getElementById("selectAllCartOnBottom").checked = selectAll.checked;
    };
    titleView.appendChild(selectAll);
    let selectorText = document.createElement("div");
    selectorText.className = "label";
    selectorText.style.width = "84px";
    selectorText.style.textAlign = "left";
    selectorText.innerHTML = "全选";
    titleView.appendChild(selectorText);

    let productName = document.createElement("div");
    productName.className = "label";
    productName.style.width = "450px";
    productName.style.textAlign = "left";
    productName.innerHTML = "商品";
    titleView.appendChild(productName);

    let price = document.createElement("div");
    price.className = "label";
    price.innerHTML = "单价";
    titleView.appendChild(price);

    let totalNumber = document.createElement("div");
    totalNumber.className = "label";
    totalNumber.innerHTML = "数量";
    titleView.appendChild(totalNumber);

    let totalPrice = document.createElement("div");
    totalPrice.className = "label";
    totalPrice.innerHTML = "小计";
    titleView.appendChild(totalPrice);

    let action = document.createElement("div");
    action.className = "label";
    action.innerHTML = "操作";
    titleView.appendChild(action);

    return titleView;
}

function createMainContentView(mainView, cartEntities) {
    let length = cartEntities == undefined ? 0:cartEntities.length;
    for (let i = 0; i < length; i++) {
        let cartEntity = cartEntities[i];
        let itemView = document.createElement("div");
        itemView.className = "itemView";
        let selector = document.createElement("input");
        selector.name = "cartItemSelectorName";
        selector.type = "checkbox";
        selector.className = "selector";
        selector.value = cartEntity.mappingId;
        itemView.appendChild(selector);

        let itemIcon = document.createElement("img");
        itemIcon.className = "itemIcon";
        itemIcon.onclick = function () {
            let url = BASE_PATH + "pd?typeId=" + cartEntity.formatEntity.parent.typeId + "&formatId=" + cartEntity.formatEntity.formatId;
            window.open(url);
        }
        itemView.appendChild(itemIcon);

        let productName = document.createElement("div");
        productName.className = "label";
        productName.style.width = "450px";
        productName.style.textAlign = "left";
        productName.innerHTML = " " + cartEntity.formatEntity.parent.parent.label + " " + cartEntity.formatEntity.parent.label + " " + cartEntity.formatEntity.label+ cartEntity.formatEntity.meta;
        productName.style.cursor = "pointer";
        productName.onclick = function () {
            let url = BASE_PATH + "pd?typeId=" + cartEntity.formatEntity.parent.typeId + "&formatId=" + cartEntity.formatEntity.formatId;
            window.open(url);
        };
        itemView.appendChild(productName);

        let price = document.createElement("div");
        price.className = "label";
        price.innerHTML = cartEntity.formatEntity.pricing + cartEntity.formatEntity.priceMeta;
        itemView.appendChild(price);

        let totalNumberContainer = document.createElement("div");
        totalNumberContainer.className = "label";
        totalNumberContainer.style.width = "108px";
        totalNumberContainer.style.height = "28px";
        totalNumberContainer.style.borderWidth = "1px";
        totalNumberContainer.style.marginTop = "5px";

        let totalNumberMinus = document.createElement("div");
        totalNumberMinus.className = "label";
        totalNumberMinus.style.width = "30px";
        totalNumberMinus.style.height = "28px";
        totalNumberMinus.style.lineHeight = "28px";
        totalNumberMinus.style.borderRightWidth = "1px";
        totalNumberMinus.innerHTML = "-";
        totalNumberMinus.style.cursor = "pointer";
        totalNumberContainer.appendChild(totalNumberMinus);

        let totalNumber = document.createElement("input");
        totalNumber.readOnly = "true";
        totalNumber.style.float = "left";
        totalNumber.style.width = "44px";
        totalNumber.style.height = "26px";
        totalNumber.style.textAlign = "center";
        totalNumber.style.borderWidth = "0px";
        totalNumber.value = cartEntity.amount;
        totalNumberContainer.appendChild(totalNumber);

        let totalNumberAdd = document.createElement("div");
        totalNumberAdd.className = "label";
        totalNumberAdd.style.width = "30px";
        totalNumberAdd.style.height = "28px";
        totalNumberAdd.style.lineHeight = "28px";
        totalNumberAdd.style.borderLeftWidth = "1px";
        totalNumberAdd.innerHTML = "+";
        totalNumberAdd.style.cursor = "pointer";
        totalNumberContainer.appendChild(totalNumberAdd);
        itemView.appendChild(totalNumberContainer);

        let totalPrice = document.createElement("div");
        totalPrice.className = "label";
        totalPrice.innerHTML = (cartEntity.amount * cartEntity.formatEntity.pricing) + cartEntity.formatEntity.priceMeta;
        itemView.appendChild(totalPrice);

        let deleteAction = document.createElement("div");
        deleteAction.className = "label";
        deleteAction.style.color = "#666666";
        deleteAction.style.cursor = "pointer";
        deleteAction.innerHTML = "删除";
        itemView.appendChild(deleteAction);

        mainView.appendChild(itemView);

        totalNumberMinus.onclick = function () {
            if (totalNumber.value > 1) {
                totalNumber.value = parseInt(totalNumber.value) - 1;
                totalPrice.innerHTML = (parseInt(totalNumber.value) * cartEntity.formatEntity.pricing) + cartEntity.formatEntity.priceMeta;
                let requestCartEntity = new Object();
                requestCartEntity.cs = getCookie("cs");
                requestCartEntity.mappingId = cartEntity.mappingId;
                requestCartEntity.amount = totalNumber.value;
                requestUpdateNumber(requestCartEntity);
            }
        };
        totalNumberAdd.onclick = function () {
            if (totalNumber.value < 10000) {
                totalNumber.value = parseInt(totalNumber.value) + 1;
                totalPrice.innerHTML = (parseInt(totalNumber.value) * cartEntity.formatEntity.pricing) + cartEntity.formatEntity.priceMeta;
                let requestCartEntity = new Object();
                requestCartEntity.cs = getCookie("cs");
                requestCartEntity.mappingId = cartEntity.mappingId;
                requestCartEntity.amount = totalNumber.value;
                requestUpdateNumber(requestCartEntity);
            }
        }

        deleteAction.onclick = function () {
            let requestCartEntity = new Object();
            requestCartEntity.cs = getCookie("cs");
            requestCartEntity.mappingId = cartEntity.mappingId;
            requestDelete(requestCartEntity, mainView, itemView);
        };
    }

    mainView.style.height = 50 + length * 100 + "px";
}

function createMainFloatView(mainView) {
    let titleView = document.createElement("div");
    titleView.className = "billingBarFloat";
    let selectAll = document.createElement("input");
    selectAll.id = "selectAllCartOnBottom";
    selectAll.type = "checkbox";
    selectAll.className = "selector";
    selectAll.onclick = function () {
        onSelectAllCartItem(selectAll.checked);
        document.getElementById("selectAllCartOnTop").checked = selectAll.checked;
    };
    titleView.appendChild(selectAll);

    let selectorText = document.createElement("div");
    selectorText.className = "label";
    selectorText.style.width = "40px";
    selectorText.style.textAlign = "left";
    selectorText.innerHTML = "全选";
    titleView.appendChild(selectorText);

    let deleteSelect = document.createElement("div");
    deleteSelect.className = "label";
    deleteSelect.style.width = "100px";
    deleteSelect.style.textAlign = "left";
    deleteSelect.innerHTML = "删除选中商品";
    titleView.appendChild(deleteSelect);

    let productName = document.createElement("div");
    productName.className = "label";
    productName.style.width = "414px";
    productName.innerHTML = "已经选择N件商品";
    titleView.appendChild(productName);

    let price = document.createElement("div");
    price.className = "label";
    price.style.width = "200px";
    price.innerHTML = "总价(不含运费):8888元";
    titleView.appendChild(price);

    let totalNumber = document.createElement("div");
    totalNumber.className = "label";
    totalNumber.innerHTML = "运费:123元";
    titleView.appendChild(totalNumber);

    let action = document.createElement("div");
    action.className = "label";
    action.style.backgroundColor = "red";
    action.style.cursor = "pointer";
    action.style.color = "#FFFFFF";
    action.innerHTML = "结算";
    titleView.appendChild(action);

    mainView.appendChild(titleView);
    mainView.style.height = mainView.clientHeight + 40 + "px";

    if (mainView.clientHeight - window.innerHeight - document.body.scrollTop > 0) {
        titleView.className = "billingBarFloat";
    } else {
        titleView.className = "billingBarBand";
    }

    window.onscroll = function () {
        if (mainView.clientHeight - window.innerHeight - document.body.scrollTop > -70) {
            titleView.className = "billingBarFloat";
        } else {
            titleView.className = "billingBarBand";
        }
    };

    window.onresize = function () {
        if (mainView.clientHeight - window.innerHeight - document.body.scrollTop > -70) {
            titleView.className = "billingBarFloat";
        } else {
            titleView.className = "billingBarBand";
        }
    };

    action.onclick = function () {
        onBillingAction();
    }
}

function onSelectAllCartItem(checked) {
    let cartItemSelectorViews = document.getElementsByName("cartItemSelectorName");
    for (let i = 0; i < cartItemSelectorViews.length; i++) {
        cartItemSelectorViews[i].checked = checked;
    }
}

function onBillingAction() {
    let cartItemSelectorViews = document.getElementsByName("cartItemSelectorName");
    let mappingIds = "";
    for (let j = 0; j < cartItemSelectorViews.length; j++) {
        if (cartItemSelectorViews[j].checked) {
            mappingIds = mappingIds + "," + cartItemSelectorViews[j].value;
        }
    }

    if (mappingIds == "") {

    } else {
        mappingIds = mappingIds.replace(/,/, "");
        let requestObject = new Object();
        requestObject.cs = getCookie("cs");
        requestObject.mappingIds = mappingIds;
        let url = BASE_PATH + "pb?p=" + JSON.stringify(requestObject);
        window.open(url, "_self");
    }

}

