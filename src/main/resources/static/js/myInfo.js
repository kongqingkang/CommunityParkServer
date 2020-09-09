function init(){
    $.ajax({
        type: "get",
        sync : false,
        serializeForm: true,
        url: "/CommunityPark/getSelfInfo",
        contentType:"application/json;charset=UTF-8",
        dataType: "json",
        success: function (res) {
            console.log(res.data);
            var data = res.data;
            $("#avatarUrl1").attr("src",data.avatarUrl);
            $("#truthName2").text(data.truthName);
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            alert(XMLHttpRequest.status);
            alert(XMLHttpRequest.readyState);
            alert(textStatus);
        },
    });
}

function logoutA(){
    // event.preventDefault();// 使a自带的方法失效，即无法向addStudent.action发出请求
    $.ajax({
        type: "POST",  // 使用post方式
        url: "/CommunityPark/logout",
        // contentType:"application/json",
        // 参数列表，stringify()方法用于将JS对象序列化为json字符串
        // dataType:"json",
        success: function(res){
            if (res.code==0){
                alert(res.msg);
                window.location.href = "/CommunityPark/login";
            }else{
                alert(res.code);
                alert("失败！");
            }
            // 请求成功后的操作
        },
        error: function(result){
            // 请求失败后的操作
            alert("失败llll！");
        }
    });
};
