/**
 * Created by dingpengwei on 8/30/16.
 */
function showPaymentView(callback) {
    document.documentElement.style.overflow = 'hidden';
    document.oncontextmenu = new Function("event.returnValue=false;");
    document.onselectstart = new Function("event.returnValue=false;");
    window.onhelp = new Function("event.returnValue=false;"); //屏蔽F1帮助
    document.onkeydown = function () {
        if (window.event && window.event.keyCode == 13) {
            window.event.returnValue = false;
        }
    }
    document.body.appendChild(createPaymentBackground());
    let paymentContainer = createPaymentContainer();
    document.body.appendChild(paymentContainer);
    paymentContainer.appendChild(createTitleView());
    paymentContainer.appendChild(createImageView(callback));
}

function dismissPaymentView() {
    document.documentElement.style.overflow = 'scroll';
    document.oncontextmenu = new Function("event.returnValue=true;");
    document.onselectstart = new Function("event.returnValue=true;");
    window.onhelp = new Function("event.returnValue=true;");
    document.onkeydown = null;
    document.body.removeChild(document.getElementById("paymentBackgroundView"));
    document.body.removeChild(document.getElementById("paymentView"));
}

function createPaymentBackground() {
    let receiverView = document.createElement("div");
    receiverView.id = "paymentBackgroundView";
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

function createPaymentContainer() {
    let paymentContainer = document.createElement("div");
    paymentContainer.id = "paymentView";
    paymentContainer.className = "receiverEditor";
    paymentContainer.style.position = "absolute";
    paymentContainer.style.width = "400px";
    paymentContainer.style.height = "400px";
    paymentContainer.style.top = getScrollTop() + 100 + "px";
    paymentContainer.style.left = "50%";
    paymentContainer.style.marginLeft = "-200px";
    paymentContainer.style.zIndex = "10";
    paymentContainer.style.opacity = "1";
    paymentContainer.style.borderWidth = "1px";
    paymentContainer.style.borderColor = "#CCCCCC";
    paymentContainer.style.borderStyle = "solid";
    paymentContainer.style.backgroundColor = "#FFFFFF";
    return paymentContainer;
}

function createTitleView() {
    let paymentTitleView = document.createElement("div");
    paymentTitleView.className = "receiverItem";
    paymentTitleView.style.width = "400px";
    paymentTitleView.style.backgroundColor = "#6AB344";
    let paymentTipLabel = document.createElement("div");
    paymentTipLabel.className = "receiverItemLabel";
    paymentTipLabel.style.width = "300px";
    paymentTipLabel.style.textAlign = "left";
    paymentTipLabel.style.paddingLeft = "10px";
    paymentTipLabel.style.fontSize = "1.2rem";
    paymentTipLabel.style.color = "#FFFFFF";
    paymentTipLabel.innerHTML = "微信支付";
    paymentTitleView.appendChild(paymentTipLabel);

    let paymentClose = document.createElement("div");
    paymentClose.innerHTML = "X";
    paymentClose.className = "receiverItemLabel";
    paymentClose.style.float = "right";
    paymentClose.style.width = "30px";
    paymentClose.style.height = "30px";
    paymentClose.style.margin = "5px";
    paymentClose.style.lineHeight = "30px";
    paymentClose.style.borderRadius = "20px";
    paymentClose.style.borderColor = "red";
    paymentClose.style.color = "red";
    paymentClose.style.borderWidth = "1px";
    paymentClose.style.cursor = "pointer";
    paymentClose.onclick = function () {
        dismissPaymentView();
    };
    paymentTitleView.appendChild(paymentClose);
    return paymentTitleView;
}

function createImageView(callback) {
    let paymentImageView = document.createElement("img");
    paymentImageView.className = "payStyleNormal";
    paymentImageView.style.width = "400px";
    paymentImageView.style.height = "400px";
    paymentImageView.src = "http://localhost:8080/foodslab/webapp/asserts/images/paymentholder.png";
    paymentImageView.onclick = function () {
        callback();
    };
    return paymentImageView;
}