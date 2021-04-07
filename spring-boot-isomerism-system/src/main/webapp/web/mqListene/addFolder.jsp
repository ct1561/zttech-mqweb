<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div>
	<form action="addFolder" method="post">
		<div>
			<label>队列名：</label>
			<input type="text" name="queueName">
		</div>
		<div>
			<label>保存路径：</label>
			<input type="text" name="fileSaveRoute">
		</div>
		<div>
			<label>日志地址：</label>
			<input type="text" name="logFolder">
		</div>
		<div>
			<input type="submit" value="保存">
		</div>
	</form>
</div>

</body>
</html>