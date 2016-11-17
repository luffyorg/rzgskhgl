$("#loginbtn").click(function(){
		var loginname = $("#name").val();
		var pwd = $("#password").val();

		var sendInfo = {
			name : loginname,
			password : pwd
		};

		$.ajax({
			type : "POST",
			url : "login",
			dataType : "json",
			contentType : 'application/json',
			data : JSON.stringify(sendInfo),
			success : function(data) {
				if(data.result == "success"){
					//alert("登录成功");
					window.location.href="admin/product/list";
				}
				else if(data.result == "usererror"){
					alert("用户名或密码有误");
				}else {
					alert("身份验证失败");
				}
			},
			error : function() {
				alert("没有返回值");
			}
		});
	});