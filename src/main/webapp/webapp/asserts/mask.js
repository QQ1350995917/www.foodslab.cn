/**
 * Created by dingpengwei on 9/6/16.
 */
const MASK_VIEW_ID = "maskView";
function showMaskView(id, color) {
    if (color == undefined || color == null || color == "") {
        color = "#CCCCCC";
    }
    if (id == undefined || id == null || id == "") {
        id = MASK_VIEW_ID;
    }
    document.documentElement.style.overflow = 'hidden';
    document.oncontextmenu = new Function("event.returnValue=false;");
    document.onselectstart = new Function("event.returnValue=false;");
    window.onhelp = new Function("event.returnValue=false;"); //屏蔽F1帮助
    document.onkeydown = function () {
        if (window.event && window.event.keyCode == 13) {
            window.event.returnValue = false;
        }
    }
    document.body.appendChild(createMaskView(id, color));
}

function dismissMaskView(id) {
    if (id == undefined || id == null || id == "") {
        id = MASK_VIEW_ID;
    }
    document.documentElement.style.overflow = 'scroll';
    document.oncontextmenu = new Function("event.returnValue=true;");
    document.onselectstart = new Function("event.returnValue=true;");
    window.onhelp = new Function("event.returnValue=true;");
    document.onkeydown = null;
    document.body.removeChild(document.getElementById(id));
}

function createMaskView(id, color) {
    let markView = document.createElement("div");
    markView.id = id;
    markView.style.position = "absolute";
    markView.style.top = getScrolledTop() + "px";
    markView.style.backgroundColor = color;
    markView.style.width = "1000px";
    markView.style.height = "100%";
    markView.style.left = "50%";
    markView.style.marginLeft = "-500px";
    markView.style.zIndex = "9";
    markView.style.opacity = "0.6";
    return markView;
}

function getScrolledTop() {
    var scrollPos;
    if (window.pageYOffset) {
        scrollPos = window.pageYOffset;
    } else if (document.compatMode && document.compatMode != 'BackCompat') {
        scrollPos = document.documentElement.scrollTop;
    } else if (document.body) {
        scrollPos = document.body.scrollTop;
    }
    return scrollPos;
}