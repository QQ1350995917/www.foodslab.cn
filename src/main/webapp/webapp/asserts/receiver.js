/**
 * Created by dingpengwei on 8/24/16.
 * 显示/隐藏背景蒙层
 * 显示/隐藏操作界面
 * 在编辑完成时候回调函数执行并返回数据
 * 控制在编辑完成时候是否提交到服务器
 */
/**
 * 收货人编辑界面调用接口
 * @param receiverEntity 收货人对象,undefined则表示为新增,非undefined则表示编辑
 * @param callback function 回调函数,并回传编辑完成的收货人对象
 * @param submit bool 是否在完成时候执行提交到服务器的操作
 */
function showReceiverEditorView(receiverEntity, callback, submit) {
    document.documentElement.style.overflow = 'hidden';
    document.oncontextmenu = new Function("event.returnValue=false;");
    document.onselectstart = new Function("event.returnValue=false;");
    window.onhelp = new Function("event.returnValue=false;"); //屏蔽F1帮助
    document.onkeydown = function () {
        if (window.event && window.event.keyCode == 13) {
            window.event.returnValue = false;
        }
    }
    // 创建背景
    document.body.appendChild(createReceiverBackground());
    let receiverEditor = createReceiverEditor();
    document.body.appendChild(receiverEditor);
    receiverEditor.appendChild(createReceiverEditorTitle());
    receiverEditor.appendChild(createReceiverEditorName(receiverEntity));
    receiverEditor.appendChild(createReceiverEditorPhone(receiverEntity));
    receiverEditor.appendChild(createReceiverEditorAddress(receiverEntity));
    receiverEditor.appendChild(createReceiverEditorAddressAppend(receiverEntity));
    receiverEditor.appendChild(createReceiverEditorSave(receiverEntity, callback, submit));
}

function dismissReceiverEditorView() {
    document.documentElement.style.overflow = 'scroll';
    document.oncontextmenu = new Function("event.returnValue=true;");
    document.onselectstart = new Function("event.returnValue=true;");
    window.onhelp = new Function("event.returnValue=true;");
    document.onkeydown = null;
    document.body.removeChild(document.getElementById("receiverEditorView"));
    document.body.removeChild(document.getElementById("receiverView"));
}

function createReceiverBackground() {
    let receiverView = document.createElement("div");
    receiverView.id = "receiverView";
    receiverView.style.position = "absolute";
    receiverView.style.top = getScrollTop() + "px";
    receiverView.style.backgroundColor = "#CCCCCC";
    receiverView.style.width = "1000px";
    receiverView.style.height = "100%";
    receiverView.style.left = "50%";
    receiverView.style.marginLeft = "-500px";
    receiverView.style.zIndex = "9";
    receiverView.style.opacity = "0.6";
    return receiverView;
}

function createReceiverEditor() {
    let receiverEditorContainer = document.createElement("div");
    receiverEditorContainer.id = "receiverEditorView";
    receiverEditorContainer.className = "receiverEditor";
    receiverEditorContainer.style.position = "absolute";
    receiverEditorContainer.style.width = "600px";
    receiverEditorContainer.style.height = "300px";
    receiverEditorContainer.style.top = getScrollTop() + 100 + "px";
    receiverEditorContainer.style.left = "50%";
    receiverEditorContainer.style.marginLeft = "-300px";
    receiverEditorContainer.style.zIndex = "10";
    receiverEditorContainer.style.opacity = "1";
    receiverEditorContainer.style.borderWidth = "1px";
    receiverEditorContainer.style.borderColor = "#CCCCCC";
    receiverEditorContainer.style.borderStyle = "solid";
    receiverEditorContainer.style.backgroundColor = "#FFFFFF";
    return receiverEditorContainer;
}

function createReceiverEditorTitle() {
    let receiverEditorTitleLine = document.createElement("div");
    receiverEditorTitleLine.className = "receiverItem";
    let receiverTipLabel = document.createElement("div");
    receiverTipLabel.className = "receiverItemLabel";
    receiverTipLabel.style.fontSize = "1.2rem";
    receiverTipLabel.innerHTML = "收货人信息";
    receiverEditorTitleLine.appendChild(receiverTipLabel);

    let receiverEditorClose = document.createElement("div");
    receiverEditorClose.innerHTML = "X";
    receiverEditorClose.className = "receiverItemLabel";
    receiverEditorClose.style.float = "right";
    receiverEditorClose.style.width = "30px";
    receiverEditorClose.style.height = "30px";
    receiverEditorClose.style.margin = "5px";
    receiverEditorClose.style.lineHeight = "30px";
    receiverEditorClose.style.borderRadius = "20px";
    receiverEditorClose.style.borderColor = "red";
    receiverEditorClose.style.color = "red";
    receiverEditorClose.style.borderWidth = "1px";
    receiverEditorClose.style.cursor = "pointer";
    receiverEditorClose.onclick = function () {
        dismissReceiverEditorView();
    };
    receiverEditorTitleLine.appendChild(receiverEditorClose);
    return receiverEditorTitleLine;
}

function createReceiverEditorName(receiverEntity) {
    let receiverEditorNameLine = document.createElement("div");
    receiverEditorNameLine.className = "receiverItem";
    let receiverNameLabel = document.createElement("div");
    receiverNameLabel.className = "receiverItemLabel";
    receiverNameLabel.style.float = "left";
    receiverNameLabel.style.textAlign = "right";
    receiverNameLabel.innerHTML = "<span style='color: #FF0000;'>*</span>姓名:";
    receiverEditorNameLine.appendChild(receiverNameLabel);

    let receiverEditorName = document.createElement("input");
    receiverEditorName.id = "receiverEditorName";
    receiverEditorName.className = "editor input";
    receiverEditorName.style.width = "150px";
    receiverEditorName.value = receiverEntity == undefined ? "" : receiverEntity.name;
    receiverEditorNameLine.appendChild(receiverEditorName);
    return receiverEditorNameLine;
}

function createReceiverEditorAddress(receiverEntity) {
    let receiverEditorAddressLine = document.createElement("div");
    receiverEditorAddressLine.className = "receiverItem";
    let receiverAddressLabel = document.createElement("div");
    receiverAddressLabel.className = "receiverItemLabel";
    receiverAddressLabel.style.float = "left";
    receiverAddressLabel.style.textAlign = "right";
    receiverAddressLabel.innerHTML = "<span style='color: #FF0000;'>*</span>地址:";
    receiverEditorAddressLine.appendChild(receiverAddressLabel);

    let provinceSelect = document.createElement("select");
    provinceSelect.id = "addressEditorProvince";
    provinceSelect.className = "addressSelector";
    provinceSelect.style.visibility = "visible";
    provinceSelect.options.add(new Option("北京市", "北京市"));
    // for (let index = 0; index < ADDRESS_LEVEL1.length; index++) {
    //     let option = new Option(ADDRESS_LEVEL1[index].label, ADDRESS_LEVEL1[index].code);
    //     province.options.add(option);
    // }
    receiverEditorAddressLine.appendChild(provinceSelect);

    let citySelect = document.createElement("select");
    citySelect.id = "addressEditorCity";
    citySelect.className = "addressSelector";
    citySelect.options.add(new Option("海淀区", "海淀区"));
    receiverEditorAddressLine.appendChild(citySelect);

    let countySelect = document.createElement("select");
    countySelect.id = "addressEditorCounty";
    countySelect.className = "addressSelector";
    countySelect.options.add(new Option("中关村", "中关村"));
    receiverEditorAddressLine.appendChild(countySelect);

    let townSelect = document.createElement("select");
    townSelect.id = "addressEditorTown";
    townSelect.className = "addressSelector";
    townSelect.options.add(new Option("三环到四环", "三环到四环"));
    receiverEditorAddressLine.appendChild(townSelect);

    let villageSelect = document.createElement("select");
    villageSelect.id = "addressEditorVillage";
    villageSelect.className = "addressSelector";
    villageSelect.options.add(new Option("鼎好大厦", "鼎好大厦"));
    receiverEditorAddressLine.appendChild(villageSelect);

    // province.onchange = function () {
    //     city.style.visibility = "hidden";
    //     county.style.visibility = "hidden";
    //     town.style.visibility = "hidden";
    //     village.style.visibility = "hidden";
    //     requestSubAddress(province.options[province.selectedIndex].value, city, 2);
    // };
    //
    // city.onchange = function () {
    //     county.style.visibility = "hidden";
    //     town.style.visibility = "hidden";
    //     village.style.visibility = "hidden";
    //     requestSubAddress(city.options[city.selectedIndex].value, county, 3);
    // };
    //
    // county.onchange = function () {
    //     town.style.visibility = "hidden";
    //     village.style.visibility = "hidden";
    //     requestSubAddress(county.options[county.selectedIndex].value, town, 4);
    // };
    //
    // town.onchange = function () {
    //     village.style.visibility = "hidden";
    //     requestSubAddress(town.options[town.selectedIndex].value, village, 5);
    // };

    return receiverEditorAddressLine;
}

function createReceiverEditorAddressAppend(receiverEntity) {
    let receiverEditorAddressAppendLine = document.createElement("div");
    receiverEditorAddressAppendLine.id = "appendAddressContainer";
    receiverEditorAddressAppendLine.className = "receiverItem";
    // receiverEditorAddressAppendLine.style.visibility = "hidden";
    let receiverAddressAppendLabel = document.createElement("div");
    receiverAddressAppendLabel.className = "receiverItemLabel";
    receiverAddressAppendLabel.style.float = "left";
    receiverAddressAppendLabel.style.textAlign = "right";
    receiverAddressAppendLabel.innerHTML = "补充地址:";
    receiverEditorAddressAppendLine.appendChild(receiverAddressAppendLabel);

    let receiverEditorAddressAppend = document.createElement("input");
    receiverEditorAddressAppend.id = "receiverEditorAddressAppend";
    receiverEditorAddressAppend.className = "editor input";
    receiverEditorAddressAppend.value = (receiverEntity == undefined ? "" : (receiverEntity.append == undefined ? "" : receiverEntity.append));
    receiverEditorAddressAppendLine.appendChild(receiverEditorAddressAppend);
    return receiverEditorAddressAppendLine;

}


function createReceiverEditorPhone(receiverEntity) {
    let receiverEditorPhoneContainer = document.createElement("div");
    receiverEditorPhoneContainer.className = "receiverItem";
    let receiverPhoneLabel = document.createElement("div");
    receiverPhoneLabel.className = "receiverItemLabel";
    receiverPhoneLabel.style.float = "left";
    receiverPhoneLabel.style.textAlign = "right";
    receiverPhoneLabel.innerHTML = "<span style='color: #FF0000;'>*</span>联系电话:";
    receiverEditorPhoneContainer.appendChild(receiverPhoneLabel);

    let receiverEditorPhone = document.createElement("input");
    receiverEditorPhone.id = "receiverEditorPhone";
    receiverEditorPhone.className = "editor input";
    receiverEditorPhone.style.width = "150px";
    receiverEditorPhone.style.float = "left";
    receiverEditorPhone.value = receiverEntity == undefined ? "" : receiverEntity.phone0;
    receiverEditorPhoneContainer.appendChild(receiverEditorPhone);

    let receiverPhoneBackupLabel = document.createElement("div");
    receiverPhoneBackupLabel.className = "receiverItemLabel";
    receiverPhoneBackupLabel.style.float = "left";
    receiverPhoneBackupLabel.style.textAlign = "right";
    receiverPhoneBackupLabel.innerHTML = "备用电话:";
    receiverEditorPhoneContainer.appendChild(receiverPhoneBackupLabel);

    let receiverEditorPhoneBackup = document.createElement("input");
    receiverEditorPhoneBackup.id = "receiverEditorPhoneBackup";
    receiverEditorPhoneBackup.className = "editor input";
    receiverEditorPhoneBackup.style.width = "150px";
    receiverEditorPhoneBackup.style.float = "left";
    receiverEditorPhoneBackup.value = receiverEntity == undefined ? "" : receiverEntity.phone1;
    receiverEditorPhoneContainer.appendChild(receiverEditorPhoneBackup);

    return receiverEditorPhoneContainer;
}

function createReceiverEditorSave(receiverEntity, callback, submit) {
    let receiverEditorSave = document.createElement("div");
    receiverEditorSave.className = "receiverItem";
    receiverEditorSave.style.marginTop = "20px";
    let receiverSave = document.createElement("div");
    receiverSave.style.backgroundColor = "red";
    receiverSave.style.float = "left";
    receiverSave.style.width = "400px";
    receiverSave.style.height = "40px";
    receiverSave.style.lineHeight = "40px";
    receiverSave.style.textAlign = "center";
    receiverSave.style.marginLeft = "100px";
    receiverSave.style.backgroundColor = "red";
    receiverSave.style.color = "#FFFFFF";
    receiverSave.style.cursor = "pointer";
    receiverSave.innerHTML = "确定";
    receiverSave.onclick = function () {
        let receiverName = document.getElementById("receiverEditorName").value;
        let receiverPhone = document.getElementById("receiverEditorPhone").value;
        let receiverPhoneBackup = document.getElementById("receiverEditorPhoneBackup").value;
        let provinceSelector = document.getElementById("addressEditorProvince");
        let provinceName = provinceSelector.options[provinceSelector.selectedIndex].value;
        let citySelector = document.getElementById("addressEditorCity");
        let cityName = citySelector.options[citySelector.selectedIndex].value;
        let countySelector = document.getElementById("addressEditorCounty");
        let countyName = countySelector.options[countySelector.selectedIndex].value;
        let townSelector = document.getElementById("addressEditorTown");
        let townName = townSelector.options[townSelector.selectedIndex].value;
        let villageSelector = document.getElementById("addressEditorVillage");
        let villageName = villageSelector.options[townSelector.selectedIndex].value;
        let receiverAddressAppend = document.getElementById("receiverEditorAddressAppend").value;

        if (receiverEntity == undefined) {
            receiverEntity = new Object();
            receiverEntity.name = receiverName;
            receiverEntity.phone0 = receiverPhone;
            receiverEntity.phone1 = receiverPhoneBackup;
            receiverEntity.province = provinceName;
            receiverEntity.city = cityName;
            receiverEntity.county = countyName;
            receiverEntity.town = townName;
            receiverEntity.village = villageName;
            receiverEntity.append = receiverAddressAppend;
            if (submit) {
                receiverEntity.cs = getCookie(KEY_CS);
                let url = BASE_PATH + "/receiver/create?p=" + JSON.stringify(receiverEntity);
                asyncRequestByGet(url, function (data) {
                    var result = checkResponseDataFormat(data);
                    if (result) {
                        var jsonData = JSON.parse(data);
                        if (jsonData.code == RC_SUCCESS) {
                            receiverEntity.receiverId = jsonData.data.receiverId;
                            callback(receiverEntity);
                            dismissReceiverEditorView();
                            new Toast().show("保存成功");
                        } else {
                            new Toast().show("保存失败");
                        }
                    }
                }, onErrorCallback, onTimeoutCallback);
            } else {
                callback(receiverEntity);
                dismissReceiverEditorView();
            }
        } else {
            receiverEntity.name = receiverName;
            receiverEntity.phone0 = receiverPhone;
            receiverEntity.phone1 = receiverPhoneBackup;
            receiverEntity.province = provinceName;
            receiverEntity.city = cityName;
            receiverEntity.county = countyName;
            receiverEntity.town = townName;
            receiverEntity.village = villageName;
            receiverEntity.append = receiverAddressAppend;
            if (submit) {
                receiverEntity.cs = getCookie(KEY_CS);
                let url = BASE_PATH + "/receiver/update?p=" + JSON.stringify(receiverEntity);
                asyncRequestByGet(url, function (data) {
                    var result = checkResponseDataFormat(data);
                    if (result) {
                        var jsonData = JSON.parse(data);
                        if (jsonData.code == RC_SUCCESS) {
                            callback(receiverEntity);
                            dismissReceiverEditorView();
                            new Toast().show("保存成功");
                        } else {
                            new Toast().show("保存失败");
                        }
                    }
                }, onErrorCallback, onTimeoutCallback);
            } else {
                callback(receiverEntity);
                dismissReceiverEditorView();
            }
        }
    };
    receiverEditorSave.appendChild(receiverSave);
    return receiverEditorSave;
}
