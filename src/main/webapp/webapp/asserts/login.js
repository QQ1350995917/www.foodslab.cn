/**
 * Created by dingpengwei on 9/7/16.
 */

function showLoginView(loginSuccessCallback) {
    showMaskView();
    document.body.appendChild(createLoginView(loginSuccessCallback));
}

function createLoginView(loginSuccessCallback) {
    let loginContainer = document.createElement("div")
    loginContainer.id = "loginContainer";
    loginContainer.className = "loginContainer";
    loginContainer.appendChild(crateTitleRowView());
    loginContainer.appendChild(createAuthRowView());
    loginContainer.appendChild(createFunctionContainer(loginSuccessCallback));
    return loginContainer;
}

function crateTitleRowView() {
    let titleRowView = document.createElement("div")
    titleRowView.className = "titleRowView";

    let titleMessage = document.createElement("div")
    titleMessage.className = "titleMessage";
    titleMessage.innerHTML = "微信/QQ扫码登录";
    titleRowView.appendChild(titleMessage);

    let closer = document.createElement("div")
    closer.className = "closer";
    closer.innerHTML = "X";
    closer.onclick = function () {
        dismissLoginView();
    };
    titleRowView.appendChild(closer);

    return titleRowView;
}

function createAuthRowView() {
    let authRowView = document.createElement("div")
    authRowView.className = "authRowView";
    let wx = document.createElement("img");
    wx.className = "login";
    wx.src = "http://localhost:8080/foodslab/webapp/asserts/images/login_wx.png";
    authRowView.appendChild(wx);
    let authLine = document.createElement("hr");
    authLine.className = "authLine";
    authRowView.appendChild(authLine);
    let qq = document.createElement("img");
    qq.className = "login";
    qq.src = "http://localhost:8080/foodslab/webapp/asserts/images/login_qq.png";
    authRowView.appendChild(qq);
    return authRowView;
}

function createFunctionContainer(loginSuccessCallback) {
    let functionContainer = document.createElement("div");
    functionContainer.className = "functionPanel";
    addLoginContainer(functionContainer,loginSuccessCallback);
    return functionContainer;
}

/**
 * 添加登录的容器
 * @param container
 */
function addLoginContainer(container,loginSuccessCallback) {
    container.innerHTML = null;
    let titleMessage = document.createElement("div");
    titleMessage.style.float = "none";
    titleMessage.style.marginTop = "10px";
    titleMessage.className = "titleMessage";
    titleMessage.innerHTML = "欢迎您登录";
    container.appendChild(titleMessage);

    let phoneNumberInput = document.createElement("input");
    phoneNumberInput.placeholder = "请输入电话号码";
    phoneNumberInput.className = "login";
    container.appendChild(phoneNumberInput);

    let passwordInput = document.createElement("input");
    passwordInput.placeholder = "请输入密码";
    passwordInput.className = "login";
    passwordInput.type = "password";
    container.appendChild(passwordInput);

    let loginAction = document.createElement("div")
    loginAction.className = "loginAction";
    loginAction.innerHTML = "登录";
    loginAction.style.backgroundColor = "red";
    loginAction.style.width = "390px";
    loginAction.style.height = "40px";
    loginAction.style.marginLeft = "5px";
    container.appendChild(loginAction);
    loginAction.onclick = function () {
        let identity = phoneNumberInput.value;
        let password = passwordInput.value;
        let requestAccount = new Object();
        requestAccount.identity = identity;
        requestAccount.password = password;
        requestLogin(requestAccount,loginSuccessCallback);
    }

    let functionAction = document.createElement("div")
    functionAction.className = "login";
    functionAction.style.width = "390px";
    functionAction.style.marginTop = "5px";
    functionAction.style.marginLeft = "5px";
    container.appendChild(functionAction);

    let registerAction = document.createElement("div")
    registerAction.className = "functionAction";
    registerAction.innerHTML = "注册账户";
    functionAction.appendChild(registerAction);
    registerAction.onclick = function () {
        addRegisterContainer(container,loginSuccessCallback);
    };

    let passwordAction = document.createElement("div")
    passwordAction.className = "functionAction";
    passwordAction.innerHTML = "忘记密码";
    functionAction.appendChild(passwordAction);
    passwordAction.onclick = function () {
        addResetPasswordContainer(container,loginSuccessCallback);
    };
}

/**
 * 添加注册的容器
 * @param container
 */
function addRegisterContainer(container,loginSuccessCallback) {
    container.innerHTML = null;
    let titleMessage = document.createElement("div");
    titleMessage.style.float = "none";
    titleMessage.style.marginTop = "10px";
    titleMessage.className = "titleMessage";
    titleMessage.innerHTML = "欢迎您注册";
    container.appendChild(titleMessage);

    let phoneNumberInput = document.createElement("input");
    phoneNumberInput.placeholder = "请输入电话号码";
    phoneNumberInput.className = "login";
    container.appendChild(phoneNumberInput);

    let codeDiv = document.createElement("div");
    codeDiv.className = "login";
    codeDiv.style.height = "40px";
    let vCodeInput = document.createElement("input");
    vCodeInput.className = "login";
    vCodeInput.style.float = "left";
    vCodeInput.style.width = "100px";
    vCodeInput.style.marginRight = "0px";
    vCodeInput.placeholder = "请输入验证码";
    let vCodeImage = document.createElement("img");
    vCodeImage.className = "login";
    vCodeImage.style.width = "85px";
    vCodeImage.style.height = "36px";
    vCodeImage.style.float = "left";
    vCodeImage.style.marginTop = "5px";
    let dCodeInput = document.createElement("input");
    dCodeInput.className = "login";
    dCodeInput.style.float = "left";
    dCodeInput.style.width = "100px";
    dCodeInput.style.marginRight = "0px";
    dCodeInput.placeholder = "请输入短信码";
    let dCodeDiv = document.createElement("div");
    dCodeDiv.style.width = "85px";
    dCodeDiv.style.height = "34px";
    dCodeDiv.style.marginTop = "5px";
    dCodeDiv.style.borderWidth = "1px";
    dCodeDiv.style.borderStyle = "solid";
    dCodeDiv.style.borderColor = "#CCCCCC";
    dCodeDiv.style.float = "left";
    codeDiv.appendChild(vCodeInput);
    codeDiv.appendChild(vCodeImage);
    codeDiv.appendChild(dCodeInput);
    codeDiv.appendChild(dCodeDiv);
    container.appendChild(codeDiv);

    let passwordDiv = document.createElement("div");
    passwordDiv.className = "login";
    passwordDiv.style.height = "40px";
    let password0Input = document.createElement("input");
    password0Input.className = "login";
    password0Input.style.float = "left";
    password0Input.style.width = "185px";
    password0Input.style.marginRight = "0px";
    password0Input.placeholder = "请输入密码";

    let password1Input = document.createElement("input");
    password1Input.className = "login";
    password1Input.style.float = "left";
    password1Input.style.width = "187px";
    password1Input.style.marginRight = "0px";
    password1Input.placeholder = "请再次输入密码";
    passwordDiv.appendChild(password0Input);
    passwordDiv.appendChild(password1Input);
    container.appendChild(passwordDiv);

    let submitAction = document.createElement("div")
    submitAction.className = "loginAction";
    submitAction.innerHTML = "提交";
    submitAction.style.backgroundColor = "red";
    submitAction.style.width = "390px";
    submitAction.style.height = "40px";
    submitAction.style.marginLeft = "5px";
    container.appendChild(submitAction);
    submitAction.onclick = function () {
        let identity = phoneNumberInput.value;
        let password = password0Input.value;
        let requestAccount = new Object();
        requestAccount.identity = identity;
        requestAccount.password = password;
        requestAccount.source = 1;
        requestCreateAccount(requestAccount);
    }

    let functionAction = document.createElement("div")
    functionAction.className = "login";
    functionAction.style.width = "390px";
    functionAction.style.marginTop = "5px";
    functionAction.style.marginLeft = "5px";
    container.appendChild(functionAction);
    let loginAction = document.createElement("div")
    loginAction.className = "functionAction";
    loginAction.innerHTML = "返回登录";
    functionAction.appendChild(loginAction);
    loginAction.onclick = function () {
        addLoginContainer(container);
    };
}

/**
 * 添加重置密码的容器
 * @param container
 */
function addResetPasswordContainer(container,loginSuccessCallback) {
    container.innerHTML = null;
    let titleMessage = document.createElement("div");
    titleMessage.style.float = "none";
    titleMessage.style.marginTop = "10px";
    titleMessage.className = "titleMessage";
    titleMessage.innerHTML = "找回密码";
    container.appendChild(titleMessage);

    let phoneNumberInput = document.createElement("input");
    phoneNumberInput.placeholder = "请输入电话号码";
    phoneNumberInput.className = "login";
    container.appendChild(phoneNumberInput);

    let codeDiv = document.createElement("div");
    codeDiv.className = "login";
    codeDiv.style.height = "40px";
    let vCodeInput = document.createElement("input");
    vCodeInput.className = "login";
    vCodeInput.style.float = "left";
    vCodeInput.style.width = "100px";
    vCodeInput.style.marginRight = "0px";
    vCodeInput.placeholder = "请输入验证码";
    let vCodeImage = document.createElement("img");
    vCodeImage.className = "login";
    vCodeImage.style.width = "85px";
    vCodeImage.style.height = "36px";
    vCodeImage.style.float = "left";
    vCodeImage.style.marginTop = "5px";
    let dCodeInput = document.createElement("input");
    dCodeInput.className = "login";
    dCodeInput.style.float = "left";
    dCodeInput.style.width = "100px";
    dCodeInput.style.marginRight = "0px";
    dCodeInput.placeholder = "请输入短信码";
    let dCodeDiv = document.createElement("div");
    dCodeDiv.style.width = "85px";
    dCodeDiv.style.height = "34px";
    dCodeDiv.style.marginTop = "5px";
    dCodeDiv.style.borderWidth = "1px";
    dCodeDiv.style.borderStyle = "solid";
    dCodeDiv.style.borderColor = "#CCCCCC";
    dCodeDiv.style.float = "left";
    codeDiv.appendChild(vCodeInput);
    codeDiv.appendChild(vCodeImage);
    codeDiv.appendChild(dCodeInput);
    codeDiv.appendChild(dCodeDiv);
    container.appendChild(codeDiv);

    let passwordDiv = document.createElement("div");
    passwordDiv.className = "login";
    passwordDiv.style.height = "40px";
    let password0Input = document.createElement("input");
    password0Input.className = "login";
    password0Input.style.float = "left";
    password0Input.style.width = "185px";
    password0Input.style.marginRight = "0px";
    password0Input.placeholder = "请输入密码";

    let password1Input = document.createElement("input");
    password1Input.className = "login";
    password1Input.style.float = "left";
    password1Input.style.width = "187px";
    password1Input.style.marginRight = "0px";
    password1Input.placeholder = "请再次输入密码";
    passwordDiv.appendChild(password0Input);
    passwordDiv.appendChild(password1Input);
    container.appendChild(passwordDiv);

    let submitAction = document.createElement("div")
    submitAction.className = "loginAction";
    submitAction.innerHTML = "提交";
    submitAction.style.backgroundColor = "red";
    submitAction.style.width = "390px";
    submitAction.style.height = "40px";
    submitAction.style.marginLeft = "5px";
    container.appendChild(submitAction);

    let functionAction = document.createElement("div")
    functionAction.className = "login";
    functionAction.style.width = "390px";
    functionAction.style.marginTop = "5px";
    functionAction.style.marginLeft = "5px";
    container.appendChild(functionAction);
    let loginAction = document.createElement("div")
    loginAction.className = "functionAction";
    loginAction.innerHTML = "返回登录";
    functionAction.appendChild(loginAction);
    loginAction.onclick = function () {
        addLoginContainer(container,loginSuccessCallback);
    };

    let registerAction = document.createElement("div")
    registerAction.className = "functionAction";
    registerAction.innerHTML = "注册账户";
    functionAction.appendChild(registerAction);
    registerAction.onclick = function () {
        addRegisterContainer(container,loginSuccessCallback);
    };
}

function dismissLoginView() {
    document.body.removeChild(document.getElementById("loginContainer"));
    dismissMaskView();
}

function requestLogin(accountEntity,loginSuccessCallback) {
    let url = BASE_PATH + "account/login?p=" + JSON.stringify(accountEntity);
    asyncRequestByGet(url, function (data) {
        var result = checkResponseDataFormat(data);
        if (result) {
            var jsonData = JSON.parse(data);
            if (jsonData.code == RC_SUCCESS) {
                setCookie(KEY_CS,jsonData.data.cs);
                new Toast().show(jsonData.message);
                dismissLoginView();
                loginSuccessCallback();
            } else {
                new Toast().show(jsonData.message);
            }
        }
    }, onErrorCallback, onTimeoutCallback);
}

function requestCreateAccount(accountEntity) {
    let url = BASE_PATH + "account/create?p=" + JSON.stringify(accountEntity);
    asyncRequestByGet(url, function (data) {
        var result = checkResponseDataFormat(data);
        if (result) {
            var jsonData = JSON.parse(data);
            console.log(jsonData);
        }
    }, onErrorCallback, onTimeoutCallback);
}