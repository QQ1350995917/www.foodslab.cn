/**
 * Created by dingpengwei on 9/8/16.
 */
function requestMineAccount(cs) {
    onRequestAccountCallback(undefined);
}

function onRequestAccountCallback(data) {
    let mainView = document.getElementById(MAIN);
    mainView.innerHTML = null;
    mainView.style.height = "560px";

    mainView.appendChild(createPhoneAccountContainer());
    mainView.appendChild(createAuthAccountContainer(0, "微信账号", "", "http://localhost:8080/foodslab/webapp/asserts/images/login_wx.png"));
    mainView.appendChild(createAuthAccountContainer(1, "QQ账号", undefined, "http://localhost:8080/foodslab/webapp/asserts/images/login_qq.png"));
}

function createPhoneAccountContainer(data) {
    let phoneAccountContainer = document.createElement("div");
    phoneAccountContainer.className = "accountItemContainer";
    let accountTitleView = document.createElement("div");
    accountTitleView.className = "accountItem";
    accountTitleView.innerHTML = "电话账号";
    accountTitleView.style.height = "40px";
    accountTitleView.style.lineHeight = "40px";
    accountTitleView.style.textAlign = "center";
    phoneAccountContainer.appendChild(accountTitleView);

    /**
     * 创建账号行
     * @type {Element}
     */
    let accountContainer = document.createElement("div");
    accountContainer.className = "accountItem";
    let accountLabelLeft = document.createElement("div");
    accountLabelLeft.className = "labelLeft";
    accountLabelLeft.innerHTML = "账号:";
    accountContainer.appendChild(accountLabelLeft);
    let accountLabelContainer = document.createElement("div");
    accountLabelContainer.className = "labelRight";
    accountLabelContainer.style.borderWidth = "0px";
    addPhoneAccountEditor(accountLabelContainer);

    accountContainer.appendChild(accountLabelContainer);
    phoneAccountContainer.appendChild(accountContainer);

    /**
     * 创建昵称行
     * @type {Element}
     */
    let nickNameContainer = document.createElement("div");
    nickNameContainer.className = "accountItem";
    let nickNameLabelLeft = document.createElement("div");
    nickNameLabelLeft.className = "labelLeft";
    nickNameLabelLeft.innerHTML = "昵称:";
    nickNameContainer.appendChild(nickNameLabelLeft);
    let nickNameLabelInput = document.createElement("input");
    nickNameLabelInput.className = "labelInput";
    nickNameContainer.appendChild(nickNameLabelInput);
    phoneAccountContainer.appendChild(nickNameContainer);
    /**
     * 创建性别行
     * @type {Element}
     */
    let genderContainer = document.createElement("div");
    genderContainer.className = "accountItem";
    let genderLabelLeft = document.createElement("div");
    genderLabelLeft.className = "labelLeft";
    genderLabelLeft.innerHTML = "性别:";
    genderContainer.appendChild(genderLabelLeft);
    let maleLabelInput = document.createElement("input");
    maleLabelInput.setAttribute("type", "radio");
    maleLabelInput.name = "gender";
    maleLabelInput.className = "genderRadio";
    genderContainer.appendChild(maleLabelInput);
    let manLabelLeft = document.createElement("div");
    manLabelLeft.className = "labelLeft";
    manLabelLeft.style.width = "20px";
    manLabelLeft.innerHTML = "男";
    genderContainer.appendChild(manLabelLeft);
    let femaleLabelInput = document.createElement("input");
    femaleLabelInput.setAttribute("type", "radio");
    femaleLabelInput.name = "gender";
    femaleLabelInput.className = "genderRadio";
    femaleLabelInput.style.marginLeft = "50px";
    genderContainer.appendChild(femaleLabelInput);
    let womanLabelLeft = document.createElement("div");
    womanLabelLeft.className = "labelLeft";
    womanLabelLeft.style.width = "20px";
    womanLabelLeft.innerHTML = "女";
    genderContainer.appendChild(womanLabelLeft);

    phoneAccountContainer.appendChild(genderContainer);
    /**
     * 创建头像行
     * @type {Element}
     */
    let imageContainer = document.createElement("div");
    imageContainer.className = "accountItem";
    imageContainer.style.height = "120px";
    let imageLabelLeft = document.createElement("div");
    imageLabelLeft.className = "labelLeft";
    imageLabelLeft.innerHTML = "头像:";
    imageContainer.appendChild(imageLabelLeft);
    let imageLabelInput = document.createElement("img");
    imageLabelInput.className = "headerImage";
    imageContainer.appendChild(imageLabelInput);
    phoneAccountContainer.appendChild(imageContainer);
    /**
     * 创建地址行
     * @type {Element}
     */
    let addressContainer = document.createElement("div");
    addressContainer.className = "accountItem";
    let addressLabelLeft = document.createElement("div");
    addressLabelLeft.className = "labelLeft";
    addressLabelLeft.innerHTML = "地址:";
    addressContainer.appendChild(addressLabelLeft);
    let addressLabelInput = document.createElement("input");
    addressLabelInput.className = "labelInput";
    addressContainer.appendChild(addressLabelInput);
    phoneAccountContainer.appendChild(addressContainer);

    let dismissPhoneAccount = document.createElement("div");
    dismissPhoneAccount.className = "accountItem";
    dismissPhoneAccount.innerHTML = "删除账号";
    dismissPhoneAccount.style.width = "326px";
    dismissPhoneAccount.style.textAlign = "center";
    dismissPhoneAccount.style.borderWidth = "1px";
    phoneAccountContainer.appendChild(dismissPhoneAccount);


    return phoneAccountContainer;
}

function addPhoneAccountEditor(container) {
    container.innerHTML = null;
    let accountLabelInput = document.createElement("div");
    accountLabelInput.className = "labelRight";
    accountLabelInput.style.cursor = "pointer";
    accountLabelInput.ondblclick = function () {
        container.innerHTML = null;
        container.parentNode.style.height = "180px";

        let phoneInput = document.createElement("input");
        phoneInput.className = "labelInput";
        phoneInput.style.marginBottom = "5px";
        container.appendChild(phoneInput);
        let verificationDiv = document.createElement("div");
        verificationDiv.className = "labelRight";
        verificationDiv.style.marginBottom = "5px";
        verificationDiv.innerHTML = "验证码";
        container.appendChild(verificationDiv);
        let verificationInput = document.createElement("input");
        verificationInput.className = "labelInput";
        verificationInput.style.marginBottom = "5px";
        verificationInput.value = "输入验证码获取动态码";
        container.appendChild(verificationInput);

        let dynamicCodeInput = document.createElement("input");
        dynamicCodeInput.className = "labelInput";
        dynamicCodeInput.style.marginBottom = "5px";
        dynamicCodeInput.value = "输入获取动的态码";
        container.appendChild(dynamicCodeInput);

        let actionDiv = document.createElement("div");
        actionDiv.className = "labelRight";
        actionDiv.style.borderWidth = "0px";
        let backDiv = document.createElement("div");
        backDiv.className = "labelRight";
        backDiv.style.width = "125px";
        backDiv.style.textAlign = "center";
        backDiv.style.cursor = "pointer";
        backDiv.innerHTML = "返回";
        backDiv.onclick = function () {
            container.parentNode.style.height = "30px";
            addPhoneAccountEditor(container);
        };
        actionDiv.appendChild(backDiv);
        let saveDiv = document.createElement("div");
        saveDiv.className = "labelRight";
        saveDiv.style.width = "126px";
        saveDiv.style.marginLeft = "10px";
        saveDiv.style.textAlign = "center";
        saveDiv.style.cursor = "pointer";
        saveDiv.innerHTML = "保存";
        saveDiv.onclick = function () {
            container.parentNode.style.height = "30px";
            addPhoneAccountEditor(container);
        };
        actionDiv.appendChild(saveDiv);
        container.appendChild(actionDiv);
    };
    container.appendChild(accountLabelInput);
}

function createAuthAccountContainer(index, title, data, link) {
    let authAccountContainer = document.createElement("div");
    authAccountContainer.className = "accountItemContainer";
    if (index == 0) {
        authAccountContainer.style.marginLeft = "5px";
        authAccountContainer.style.marginRight = "5px";
    }
    let accountTitleView = document.createElement("div");
    accountTitleView.className = "accountItem";
    accountTitleView.style.height = "40px";
    accountTitleView.style.lineHeight = "40px";
    accountTitleView.style.textAlign = "center";
    authAccountContainer.appendChild(accountTitleView);
    if (data == undefined) {
        accountTitleView.innerHTML = "扫码绑定" + title;
        let scanner = document.createElement("img");
        scanner.className = "scanner";
        scanner.src = link;
        authAccountContainer.appendChild(scanner);
    } else {
        accountTitleView.innerHTML = title;
        /**
         * 创建昵称行
         * @type {Element}
         */
        let nickNameContainer = document.createElement("div");
        nickNameContainer.className = "accountItem";
        let nickNameLabelLeft = document.createElement("div");
        nickNameLabelLeft.className = "labelLeft";
        nickNameLabelLeft.innerHTML = "昵称:";
        nickNameContainer.appendChild(nickNameLabelLeft);
        let nickNameLabelInput = document.createElement("div");
        nickNameLabelInput.className = "labelRight";
        nickNameLabelInput.innerHTML = "微信绑定账号";
        nickNameLabelInput.style.borderWidth = "0px";
        nickNameContainer.appendChild(nickNameLabelInput);
        authAccountContainer.appendChild(nickNameContainer);
        /**
         * 创建性别行
         * @type {Element}
         */
        let genderContainer = document.createElement("div");
        genderContainer.className = "accountItem";
        let genderLabelLeft = document.createElement("div");
        genderLabelLeft.className = "labelLeft";
        genderLabelLeft.innerHTML = "性别:";
        genderContainer.appendChild(genderLabelLeft);
        let genderLabelInput = document.createElement("div");
        genderLabelInput.className = "labelRight";
        genderLabelInput.innerHTML = "男";
        genderLabelInput.style.borderWidth = "0px";
        genderContainer.appendChild(genderLabelInput);
        authAccountContainer.appendChild(genderContainer);
        /**
         * 创建头像行
         * @type {Element}
         */
        let imageContainer = document.createElement("div");
        imageContainer.className = "accountItem";
        imageContainer.style.height = "120px";
        let imageLabelLeft = document.createElement("div");
        imageLabelLeft.className = "labelLeft";
        imageLabelLeft.innerHTML = "头像:";
        imageContainer.appendChild(imageLabelLeft);
        let imageLabelInput = document.createElement("img");
        imageLabelInput.className = "headerImage";
        imageLabelInput.src = "http://localhost:8080/foodslab/webapp/asserts/images/paywei.png";
        imageContainer.appendChild(imageLabelInput);
        authAccountContainer.appendChild(imageContainer);
        /**
         * 创建地址行
         * @type {Element}
         */
        let addressContainer = document.createElement("div");
        addressContainer.className = "accountItem";
        let addressLabelLeft = document.createElement("div");
        addressLabelLeft.className = "labelLeft";
        addressLabelLeft.innerHTML = "地址:";
        addressContainer.appendChild(addressLabelLeft);
        let addressLabelInput = document.createElement("div");
        addressLabelInput.className = "labelRight";
        addressLabelInput.style.borderWidth = "0px";
        addressLabelInput.innerHTML = "北京市 昌平区";
        addressContainer.appendChild(addressLabelInput);
        authAccountContainer.appendChild(addressContainer);
    }
    return authAccountContainer;
}
