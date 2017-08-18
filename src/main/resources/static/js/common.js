/*获取地址参数
 */
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var href = window.location.href;
    var b = href.lastIndexOf("?");
    var r = href.substr(b + 1).match(reg);
    if (r != null) {
        return decodeURI(r[2]);
    }
    else {
        return null;
    }
}
/*防伪跳转界面*/
function TZfw() {
    var fwm = getUrlParam("fwm");
    window.location.href = "/FW/Check/Index?fwm=" + fwm;
}

/*
 *显示消息
 *msg 显示信息
 *isAutoHide 是否自动隐藏消失 默认2秒后自动隐藏
 *htmlStr 提示框HTML代码
 */
function showMsg(msg, isAutoHide, isOnlyText, htmlStr) {
    $.mobile.loading("show", {
        text: msg,
        textVisible: true,
        theme: "b",//$.mobile.loader.prototype.options.theme
        textonly: isOnlyText,
        html: htmlStr
    });
    if (isAutoHide) {
        setTimeout(function () {
            hideMsg()
        }, 3000);
    }
}

//隐藏消息框
function hideMsg() {
    $.mobile.loading('hide');
}
//Cookie
function getCookieValue(name) {
    var name = escape(name);
    //读cookie属性，这将返回文档的所有cookie   
    var allcookies = document.cookie;
    //查找名为name的cookie的开始位置   
    name += "=";
    var pos = allcookies.indexOf(name);
    //如果找到了具有该名字的cookie，那么提取并使用它的值  
    if (pos != -1) {                                    //如果pos值为-1则说明搜索"version="失败   
        var start = pos + name.length;                  //cookie值开始的位置   
        var end = allcookies.indexOf(";", start);      //从cookie值开始的位置起搜索第一个";"的位置,即cookie值结尾的位置   
        if (end == -1) end = allcookies.length;        //如果end值为-1说明cookie列表里只有一个cookie   
        var value = allcookies.substring(start, end);  //提取cookie的值   
        var ssss = decodeURIComponent(value);
        return ssss;                       //对它解码         
    }
    else return "";                                   //搜索失败，返回空字符串   
}
/*'yyyy-MM-dd HH:mm:ss'格式的字符串转日期*/
function stringToDate(str) {
    var tempStrs = str.split(" ");
    var dateStrs = tempStrs[0];
    //var dateStrs = tempStrs[0].split("-");
    //var year = parseInt(dateStrs[0], 10);
    //var month = parseInt(dateStrs[1], 10) - 1;
    //var day = parseInt(dateStrs[2], 10);
    //var timeStrs = tempStrs[1].split(":");
    //var hour = parseInt(timeStrs[0], 10);
    //var minute = parseInt(timeStrs[1], 10);
    //var second = parseInt(timeStrs[2], 10);
    //var date = new Date(year, month, day);
    return dateStrs;
}
function open_win_shoplogin() {
    $("#divWin_ZZ").css("display", "block");
    $("#divWin_Shop").css("display", "block");
}
function close_win_shoplogin() {
    $("#divWin_ZZ").css("display", "none");
    $("#divWin_Shop").css("display", "none");
}