<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="">
    <div class="pc-sign">
        <input type="text" id="username" placeholder="用户名/邮箱/手机号">
    </div>
    <div class="pc-sign">
        <input type="password" id="password" placeholder="请输入您的密码">
    </div>
    <div class="pc-submit-ss">
        <input type="button" id="btn_submit" value="登录" placeholder="">
    </div>
</form>

<script src="jquery-1.7.2.min.js" type="text/javascript"></script>
<script>
    $(function(){
        $("#btn_submit").click(function(){
            var username=$.trim($("#username").val());
            var password=$.trim($("#password").val());
            if (username== ""||password== "") {
                alert("登录信息不能为空!");
                return;
            }
            $.ajax({
                url:"/login",
                type:"POST",
                data:{username:username,password:$("#password").val()},
                success:function(data){
                    if(data.code=="000000"){
                        window.location.href=data.data;
                    }else{
                        alert(data.msg);
                    }
                }
            });
        });
    })
</script>
</body>
</html>