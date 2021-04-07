<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div>
	<table border="1">
		<thead>
			<tr>
				<th>队列</th>
				<th>Dll名</th>
				<th>函数名</th>
				<th>日志地址</th>
				<th>操作</th>
			</tr>
		</thead>
		
		<tbody>
			<c:forEach items="${maps}" var="map">
				<tr>
					<td>${map.key}</td>
					<td>${map.value.dllName}</td>
					<td>${map.value.entryPoint}</td>
					<td>${map.value.logFolder}</td>
					<td>
						<a onclick="remove(this)">移除</a>
					</td>
				</tr>
			</c:forEach>
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td><a href="goAddCmd">添加</a></td>
			</tr>
		</tbody>
	</table>
	
</div>
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-3.5.1.js"></script>
<script type="text/javascript">
	function remove(frame){
		var tr = $(frame).parent().parent()
		var td = tr.children("td")
		var queueName = td.eq(0).text()
		var dllName = td.eq(1).text()
		var entryPoint = td.eq(2).text()
		var result = td.eq(3).text()
		var logFolder = td.eq(4).text()
		$.ajax({
			type: "POST",
			url: "/jvm/mq/removeCmd",
			dataType: "json",
			data: {
				queueName: queueName,
				dllName: dllName,
				entryPoint: entryPoint,
				result: result
			},
			success: function(msg){
				console.log(msg)
				if(msg == true){
					tr.remove()
				} else {
					alert("移除失败")
				}
			}
		})
	}
</script>
</html>