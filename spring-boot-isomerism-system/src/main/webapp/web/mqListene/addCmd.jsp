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
	<form action="addCmd" method="post">
		<div>
			<label>队列名：</label>
			<input type="text" name="queueName">
		</div>
		<div>
			<label>dll名：</label>
			<input type="text" name="dllName">
		</div>
		<div>
			<label>函数名：</label>
			<input type="text" name="entryPoint">
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