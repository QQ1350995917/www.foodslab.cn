/**
 * Created by dingpengwei on 9/8/16.
 */
window.onload = function () {
    initTitleView();
    requestLinker();
    let dir = document.getElementById("dir") == undefined ? null : document.getElementById("dir").content;
    if (dir == "cart"){
        createMineTabView(0);
    }
    
    if (dir == "order"){
        createMineTabView(1);
    }

    if (dir == "account"){
        createMineTabView(2);
    }
    
    if (dir == "receiver"){
        createMineTabView(3);
    }
    
    
};

const MINE_TABS = new Array("购物车", "订单", "账户", "收货地址");
function createMineTabView(index) {
    if (index == undefined) {
        index = 0;
    }
    let mineTabContainer = document.getElementById(HEADER_MENU_DOWN);
    mineTabContainer.innerHTML = null;
    for (let i = 0; i < MINE_TABS.length; i++) {
        let tabView = document.createElement("div");
        if (index == i) {
            tabView.className = "tabItem_selected tabItem";
        } else {
            tabView.className = "tabItem_normal tabItem";
        }
        tabView.innerHTML = MINE_TABS[i];
        mineTabContainer.appendChild(tabView);
        tabView.onclick = function () {
            createMineTabView(i);
        };
    }
    if (index == 0) {
        requestMyCart();
    } else if (index == 1) {
        requestMyOrder();
    } else if (index == 2) {
        requestMyAccount();
    } else if (index == 3) {
        requestMyReceiver();
    }
}

function requestMyCart() {
    requestCart("test");
}

function requestMyOrder() {
    requestOrder("test");
}

function requestMyAccount() {
    requestAccount("test");
}

function requestMyReceiver() {
    requestReceiver("test");
}