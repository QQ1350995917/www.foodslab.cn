/**
 * Created by dingpengwei on 10/30/16.
 */

function loadNamedBilling(receiverContainer, productContainer, payBarContainer) {
    let requestUserEntity = new Object();
    requestUserEntity.cs = getCookie(KEY_CS);
    asyncRequestByGet(BASE_PATH + "receiver/retrieves?p=" + JSON.stringify(requestUserEntity), function (data) {
        var result = checkResponseDataFormat(data);
        if (result) {
            var jsonData = JSON.parse(data);
            if (jsonData.code == RC_SUCCESS || jsonData.code == RC_SUCCESS_EMPTY) {
                attachUserReceiverContainer(receiverContainer, jsonData.data, function (height) {
                    receiverContainer.parentNode.style.height = (receiverContainer.parentNode.clientHeight + height) + "px";
                })
            } else {
                new Toast().show("获取收货人失败");
            }
        }
    }, onErrorCallback, onTimeoutCallback);

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
}


function attachUserReceiverContainer(receiverContainer, receiverEntities, onAttachCallback) {
    let receiverMessage = document.createElement("div");
    receiverMessage.className = "messageLabel";
    receiverMessage.innerHTML = "收货人信息";
    receiverContainer.appendChild(receiverMessage);
    let currentReceiverContainer = document.createElement("div");
    currentReceiverContainer.id = "currentReceiverId";
    currentReceiverContainer.className = "billingMoreReceiverItem";
    currentReceiverContainer.style.borderColor = "red";
    currentReceiverContainer.style.color = "red";
    let length = receiverEntities == undefined ? 0 : receiverEntities.length;
    let defaultReceiverEntity = undefined;
    for (let i = 0; i < length; i++) {
        if (receiverEntities[i].status == 3){
            defaultReceiverEntity = receiverEntities[i];
            break;
        }
    }
    if (defaultReceiverEntity == undefined){
        defaultReceiverEntity = receiverEntities[0];
    }
    attachCurrentReceiverToContainer(currentReceiverContainer, defaultReceiverEntity, true);
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
    let moreReceiverContainer = document.createElement("div");
    moreReceiverContainer.className = "messageLabel";
    receiverContainer.appendChild(moreReceiverContainer);
    moreReceiverTip.onclick = function () {
        if (this.status == "less") {
            moreReceiverTip.innerHTML = "更多收货地址 ︽ ";
            this.status = "more";
            attachMoreReceiverToContainer(moreReceiverContainer, receiverEntities, function (heightVariable) {
                receiverContainer.style.height = receiverContainer.clientHeight + heightVariable + "px";
                onAttachCallback(heightVariable);
            });
            receiverContainer.style.height = receiverContainer.clientHeight + moreReceiverContainer.customerHeight + "px";
            onAttachCallback(moreReceiverContainer.customerHeight);
        } else if (this.status == "more") {
            moreReceiverTip.innerHTML = "更多收货地址 ︾ ";
            this.status = "less";
            receiverContainer.style.height = "120px";
            moreReceiverContainer.innerHTML = null;
            onAttachCallback(-moreReceiverContainer.customerHeight);
        }
    };
}

function attachMoreReceiverToContainer(moreReceiverContainer, receiverEntities, onAttachCallback) {
    moreReceiverContainer.innerHTML = null;
    let length = receiverEntities == undefined ? 0 : receiverEntities.length;
    let defaultReceiverEntity = undefined;
    for (let i = 0; i < length; i++) {
        let receiverEntity = receiverEntities[i];
        let receiverEntityContainer = document.createElement("div");
        receiverEntityContainer.className = "billingMoreReceiverItem";
        attachCurrentReceiverInfoToView(receiverEntityContainer, receiverEntity);
        moreReceiverContainer.appendChild(receiverEntityContainer);
        if (receiverEntity.status == 3 && defaultReceiverEntity == undefined) {//非空判定防止账户合并出现多个默认地址
            defaultReceiverEntity = receiverEntity;
            receiverEntityContainer.style.borderColor = "red";
        } else {
            let receiverEntityDelete = document.createElement("div");
            receiverEntityDelete.style.float = "right";
            receiverEntityDelete.style.width = "40px";
            receiverEntityDelete.style.height = "40px";
            receiverEntityDelete.style.color = "red";
            receiverEntityDelete.innerHTML = "删除";
            receiverEntityDelete.style.visibility = "hidden";
            receiverEntityContainer.appendChild(receiverEntityDelete);

            let receiverEntityDefault = document.createElement("div");
            receiverEntityDefault.style.float = "right";
            receiverEntityDefault.style.width = "100px";
            receiverEntityDefault.style.height = "40px";
            receiverEntityDefault.style.color = "red";
            receiverEntityDefault.innerHTML = "设置为默认";
            receiverEntityDefault.style.visibility = "hidden";
            receiverEntityContainer.appendChild(receiverEntityDefault);

            receiverEntityContainer.onmouseover = function () {
                receiverEntityDelete.style.visibility = "visible";
                receiverEntityDefault.style.visibility = "visible";
                receiverEntityDelete.onclick = function () {
                    window.event.cancelBubble = true;
                    let requestReceiverEntity = new Object();
                    requestReceiverEntity.cs = getCookie(KEY_CS);
                    requestReceiverEntity.receiverId = receiverEntity.receiverId;
                    requestReceiverEntity.status = -1;
                    let url = BASE_PATH + "receiver/delete?p=" + JSON.stringify(requestReceiverEntity);
                    asyncRequestByGet(url, function (data) {
                        var result = checkResponseDataFormat(data);
                        if (result) {
                            var jsonData = JSON.parse(data);
                            if (jsonData.code == RC_SUCCESS) {
                                let part1 = receiverEntities.slice(0, i);
                                let part2 = receiverEntities.slice(i + 1, length);
                                receiverEntities = part1.concat(part2);
                                onAttachCallback(-40);
                                attachMoreReceiverToContainer(moreReceiverContainer, receiverEntities, onAttachCallback)
                            } else {
                                new Toast().show("删除失败");
                            }
                        }
                    }, onErrorCallback, onTimeoutCallback);
                };
                receiverEntityDefault.onclick = function () {
                    window.event.cancelBubble = true;
                    let requestReceiverEntity = new Object();
                    requestReceiverEntity.cs = getCookie(KEY_CS);
                    requestReceiverEntity.receiverId = receiverEntity.receiverId;
                    requestReceiverEntity.status = 3;
                    let url = BASE_PATH + "receiver/king?p=" + JSON.stringify(requestReceiverEntity);
                    asyncRequestByGet(url, function (data) {
                        var result = checkResponseDataFormat(data);
                        if (result) {
                            var jsonData = JSON.parse(data);
                            if (jsonData.code == RC_SUCCESS) {
                                if (defaultReceiverEntity != undefined) {
                                    defaultReceiverEntity.status = 2;
                                }
                                receiverEntity.status = 3;
                                attachMoreReceiverToContainer(moreReceiverContainer, receiverEntities, onAttachCallback)
                                let currentReceiverContainer = document.getElementById("currentReceiverId");
                                attachCurrentReceiverToContainer(currentReceiverContainer, receiverEntity, true);
                            } else {
                                new Toast().show("设置失败");
                            }
                        }
                    }, onErrorCallback, onTimeoutCallback);
                }
            }
            receiverEntityContainer.onmouseout = function () {
                receiverEntityDelete.style.visibility = "hidden";
                receiverEntityDefault.style.visibility = "hidden";
            }
        }
        receiverEntityContainer.onclick = function () {
            showReceiverEditorView(receiverEntity, function (newReceiverEntity) {
                receiverEntityContainer.bindReceiverId = newReceiverEntity.receiverId;
                attachCurrentReceiverInfoToView(receiverEntityContainer, newReceiverEntity);
                let currentReceiverContainer = document.getElementById("currentReceiverId");
                attachCurrentReceiverToContainer(currentReceiverContainer, receiverEntity, true);
            }, true);
        };
    }

    if (receiverEntities == undefined || length < 10) {
        let addNewReceiver = document.createElement("div");
        addNewReceiver.className = "billingMoreReceiverItem";
        addNewReceiver.style.textAlign = "center";
        addNewReceiver.style.borderBottomWidth = "1px";
        addNewReceiver.innerHTML = "+";
        moreReceiverContainer.appendChild(addNewReceiver);
        addNewReceiver.onclick = function () {
            showReceiverEditorView(undefined, function (newReceiverEntity) {
                receiverEntities.push(newReceiverEntity);
                attachMoreReceiverToContainer(moreReceiverContainer, receiverEntities, onAttachCallback)
                onAttachCallback(40);
            }, true);
        }
    }
    moreReceiverContainer.style.height = (length * 43 + (receiverEntities.length < 10 ? 1 : 0) * 40) + "px";
    moreReceiverContainer.customerHeight = length * 43 + ((receiverEntities.length < 10 ? 1 : 0) * 40);
}