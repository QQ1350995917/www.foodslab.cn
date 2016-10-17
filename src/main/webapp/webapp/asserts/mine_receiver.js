/**
 * Created by dingpengwei on 9/8/16.
 */
function requestReceiver(accountId) {
    let userEntity = new Object()
    userEntity.sessionId = accountId;
    let url = BASE_PATH + "receiver/retrieves?p=" + JSON.stringify(userEntity);
    asyncRequestByGet(url, function (data) {
        var result = checkResponseDataFormat(data);
        if (result) {
            var jsonData = JSON.parse(data);
            onRequestRetrieveCallback(accountId, jsonData.data);
        }
    }, onErrorCallback, onTimeoutCallback);
}

/**
 * 请求创建新收货人
 * @param data
 */
function requestCreateReceiver(receiverEntity) {
    let url = BASE_PATH + "receiver/create?p="  + JSON.stringify(receiverEntity);
    asyncRequestByGet(url, function (data) {
        var result = checkResponseDataFormat(data);
        if (result) {
            var jsonData = JSON.parse(data);
            onRequestCreateReceiverCallback(jsonData.data);
        }
    }, onErrorCallback, onTimeoutCallback);
}

/**
 * 更新收货人地址
 * @param data
 */
function requestUpdateReceiver(receiverEntity) {
    let url = BASE_PATH + "receiver/update?p="  + JSON.stringify(receiverEntity);
    asyncRequestByGet(url, function (data) {
        var result = checkResponseDataFormat(data);
        if (result) {
            var jsonData = JSON.parse(data);
            requestReceiver(jsonData.data.sessionId);
        }
    }, onErrorCallback, onTimeoutCallback);
}

function requestDeleteReceiver(receiverEntity) {
    let url = BASE_PATH + "receiver/delete?p=" + JSON.stringify(receiverEntity);
    asyncRequestByGet(url, function (data) {
        var result = checkResponseDataFormat(data);
        if (result) {
            var jsonData = JSON.parse(data);
            requestReceiver(jsonData.data.sessionId);
        }
    }, onErrorCallback, onTimeoutCallback);
}

function requestMarkReceiver(receiverEntity) {
    let url = BASE_PATH + "receiver/king?p=" + JSON.stringify(receiverEntity);
    asyncRequestByGet(url, function (data) {
        var result = checkResponseDataFormat(data);
        if (result) {
            var jsonData = JSON.parse(data);
            requestReceiver(jsonData.data.sessionId);
        }
    }, onErrorCallback, onTimeoutCallback);
}

/**
 * 请求所有的收货地址的回调
 * @param data
 */
function onRequestRetrieveCallback(accountId, data) {
    let mainView = document.getElementById(MAIN);
    mainView.innerHTML = null;
    let mainViewHeight = 0;
    let length = data == undefined ? 0 : data.length;
    for (let i = 0; i < length; i++) {
        let receiverItemContainer = createReceiverItemContainer(data[i]);
        receiverItemContainer.data = data[i];
        mainView.appendChild(receiverItemContainer);
        mainViewHeight = mainViewHeight + receiverItemContainer.clientHeight;
    }

    if (length < 12) {
        let receiverAddNewContainer = createAddNewReceiverContainer(accountId);
        mainView.appendChild(receiverAddNewContainer);
        mainViewHeight = mainViewHeight + receiverAddNewContainer.clientHeight;
    }

    mainView.style.height = mainViewHeight + "px";
}

/**
 * 请求创建新收货人的回调
 * @param data
 */
function onRequestCreateReceiverCallback(data) {
    let mainView = document.getElementById(MAIN);
    mainView.removeChild(mainView.childNodes[mainView.childNodes.length - 1]);
    let receiverItemContainer = createReceiverItemContainer(data);
    mainView.appendChild(receiverItemContainer);

    if (mainView.childNodes.length < 12) {
        let receiverAddNewContainer = createAddNewReceiverContainer(data.accountId);
        mainView.appendChild(receiverAddNewContainer);
        mainView.style.height = mainView.clientHeight + receiverItemContainer.clientHeight + "px";
    }
}

/**
 * 请求更新收货人的回调
 * @param data
 */
function onRequestUpdateReceiverCallback(data, nameView, addressView) {
    nameView.innerHTML = data.name;
    addressView.innerHTML = data.province + " " + data.city + " " + data.county + " " + data.town + " " + data.village
        + " " + (data.append == undefined ? "" : data.append ) + " " + data.phone0;
    if (data.status == 2) {
        let mainView = document.getElementById(MAIN);
        for (let i = 0; i < mainView.childNodes.length; i++) {
            let originData = mainView.childNodes[i].data;
            if (originData.status == 2) {
                originData.status = 1;
                mainView.childNodes[i].data = originData;
                createReceiverItemContainer(originData, mainView.childNodes[i]);
                break;
            }
        }
        nameView.parentNode.data = data;
        createReceiverItemContainer(data, nameView.parentNode);
    }
}

function onRequestDeleteReceiverCallback(data, view) {
    let mainView = document.getElementById(MAIN);
    let viewHeigth = view.clientHeight;
    mainView.removeChild(view);
    mainView.style.height = mainView.clientHeight - viewHeigth + "px";
    if (mainView.childNodes.length < 12) {
        if (mainView.childNodes[mainView.childNodes.length - 1].id != "addReceiverView") {
            let receiverAddNewContainer = createAddNewReceiverContainer(data.accountId);
            mainView.appendChild(receiverAddNewContainer);
            mainView.style.height = mainView.clientHeight + receiverAddNewContainer.clientHeight + "px";
        }
    }
}

function createReceiverItemContainer(receiverEntity, receiverItemContainer) {
    if (receiverItemContainer == undefined) {
        receiverItemContainer = document.createElement("div");
    } else {
        receiverItemContainer.innerHTML = null;
    }
    receiverItemContainer.className = "receiverItemContainer";
    let connectLine1 = document.createElement("hr");
    connectLine1.className = "connectLineH";
    receiverItemContainer.appendChild(connectLine1);
    let nameView = document.createElement("div");
    nameView.className = "receiverLabel";
    nameView.style.width = "151px";
    nameView.innerHTML = receiverEntity.name;
    receiverItemContainer.appendChild(nameView);
    let connectLine2 = document.createElement("hr");
    connectLine2.className = "connectLineH";
    receiverItemContainer.appendChild(connectLine2);

    let addressView = document.createElement("div");
    addressView.className = "receiverLabel";
    addressView.style.width = "560px";
    addressView.style.textAlign = "left";
    addressView.style.paddingLeft = "10px";
    addressView.style.paddingRight = "10px";
    addressView.innerHTML = receiverEntity.province + " " + receiverEntity.city + " " + receiverEntity.county + " " + receiverEntity.town + " " + receiverEntity.village
        + " " + (receiverEntity.append == undefined ? "" : receiverEntity.append ) + " " + receiverEntity.phone0;
    receiverItemContainer.appendChild(addressView);
    if (receiverEntity.status == 3) {
        nameView.style.borderColor = "red";
        addressView.style.borderColor = "red";
        addressView.style.width = "784px";
    } else {
        let connectLine3 = document.createElement("hr");
        connectLine3.className = "connectLineH";
        receiverItemContainer.appendChild(connectLine3);

        let defaultView = document.createElement("div");
        defaultView.className = "receiverLabel";
        defaultView.style.width = "100px";
        defaultView.innerHTML = "设置为默认";
        receiverItemContainer.appendChild(defaultView);

        let connectLine4 = document.createElement("hr");
        connectLine4.className = "connectLineH";
        receiverItemContainer.appendChild(connectLine4);

        let deleteView = document.createElement("div");
        deleteView.className = "receiverLabel";
        deleteView.style.width = "80px";
        deleteView.innerHTML = "删除";
        receiverItemContainer.appendChild(deleteView);

        defaultView.onclick = function () {
            window.event.cancelBubble = true;
            let requestReceiver = new Object();
            requestReceiver.receiverId = receiverEntity.receiverId;
            requestReceiver.sessionId = "test";
            requestReceiver.status = 3;
            requestMarkReceiver(requestReceiver);
        };

        deleteView.onclick = function () {
            let requestReceiver = new Object();
            requestReceiver.sessionId = "test";
            requestReceiver.accountId = "test";
            requestReceiver.receiverId = receiverEntity.receiverId;
            requestDeleteReceiver(requestReceiver);
        };
    }

    nameView.ondblclick = function () {
        showReceiverEditorView(receiverEntity, function (newReceiverEntity) {
            requestUpdateReceiver(newReceiverEntity);
        });
    };

    addressView.ondblclick = function () {
        showReceiverEditorView(receiverEntity, function (newReceiverEntity) {
            requestUpdateReceiver(newReceiverEntity);
        });
    };
    return receiverItemContainer;
}

function createAddNewReceiverContainer(accountId) {
    let addReceiverView = document.createElement("div");
    addReceiverView.id = "addReceiverView";
    addReceiverView.className = "receiverItemContainer";
    addReceiverView.style.width = "1000px";
    addReceiverView.style.borderWidth = "0px";
    let addView = document.createElement("div");
    addView.className = "receiverLabel";
    addView.style.borderWidth = "1px";
    addView.style.width = "998px";
    addView.innerHTML = "+";
    addReceiverView.appendChild(addView);
    addView.onclick = function () {
        showReceiverEditorView(undefined, function (receiverEntity) {
            receiverEntity.accountId = accountId;
            requestCreateReceiver(receiverEntity);
        });
    };
    return addReceiverView;
}
