function saveRes(){
	var name = $("#name").val();
	var url = $("#url").val();
	var permission = $("#permission").val();

	var sendInfo = {
		"name" : name,
		"url" : url,
		"permission" : permission
	};

	$.ajax({
		type : "POST",
		url : "save",
		dataType : "json",
		contentType : 'application/json',
		data : JSON.stringify(sendInfo),
		success : function(data) {
			if (data.success == "success") {
				alert("保存成功！")
				window.location.href = "list";
			}  else {
				alert("保存失败！");
			}
		},
		error : function() {
			alert("网络异常，请稍后再试！");
		}
	});
}