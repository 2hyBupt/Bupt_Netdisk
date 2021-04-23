SUCCESS = 200;
ERROR = 403;
$(function(){
    getvCode();
    $("#verifyimg").click(getvCode);
    $("#loginbutton").click(login);
    $("#exampleInputEmail").blur(checkName);
    $("#exampleInputPassword").blur(checkPassword)
});


//检查名字和密码的正确性--writen by fjl
function checkPassword() {
    var password = $("#exampleInputPassword").val();
    var rule = /^\w{6,20}$/;
    if (!rule.test(password)) {
        $("#exampleInputPassword").next().html("6~20个字符");
        return false
    }
    $("#exampleInputPassword").next().empty();
    return true
}
function checkName() {
    var username = $("#exampleInputEmail").val();
    var rule = /^\w{6,20}$/;
    if (!rule.test(username)) {
        $("#exampleInputEmail").next().html("6~20个字符");
        return false
    }
    $("#exampleInputEmail").next().empty();
    return true
}


//登录函数  -- wirten by fjl & ysx
function login() {
    var url = "/netdisk/logIn";//改为我的服务器
    var userName = $("#exampleInputEmail").val();
    var password = $("#exampleInputPassword").val();
    var code = $("#code").val();
    var n = checkPassword() + checkName();
    if (n != 2) {
        return
    }
    $.ajax({
        url : url,
        type : "post",//表示发送
        dataType : "json",
        data:{
            "userName" : userName,
            "password" : password,
        },
       //接收函数
        success : function(result) {//指发送成功后显示（这里默认已经收到了请求了）
            //window.alert("enter");
           // window.alert(typeof result.statuscode);
            if (result.statuscode === SUCCESS) {
               window.alert("登录成功,欢迎"+userName);
               //添加cookie
                addCookie("userName",userName);
                addCookie("password",password);
                addCookie("id",result.id);
                addCookie("phone",result.phone);
                addCookie("capacity",result.capacity);
                //  layer.msg("登录成功",{icon:1});
                setInterval(function (){
                    location.href="MyCloudDisk.html";
                },1000)
            } else {
                window.alert(result.errorInfo);
            }
        },
        error : function(e) {
            window.alert("error");
            layer.msg("LogIn功能函数网络连接异常",{icon:2})
        }
    })
};

/**
 * 获取验证码
 * 将验证码写到login.html页面的id = verifyimg 的地方
 */
function getvCode() {
    /* document.getElementById("verifyimg").src = timestamp("http://192.168.0.105:8080/api-system/role/verifyCode");*/
}


//为url添加时间戳-- writen by fjl
function timestamp(url) {
    var getTimestamp = new Date().getTime();
    if (url.indexOf("?") > -1) {
        url = url + "&timestamp=" + getTimestamp
    } else {
        url = url + "?timestamp=" + getTimestamp
    }
    return url;
};