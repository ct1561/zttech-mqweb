<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/bootstrap.js"></script> --%>
<link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
<script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
<title>Insert title here</title>
</head>
<body>
	<p>resources</p>
	<div>
		<table border="1">
			<thead>
				<tr>
					<th colspan="6">JVM基本信息</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<th>JDK版本</th>
					<td>${parameter.jdkVersion}</td>
					<th>JVM启动时间</th>
					<td>${parameter.jvmStartTimeMs}</td>
					<th>JVM运行时间</th>
					<td>${parameter.jvmUpTimeMs}</td>
				</tr>
				<tr>
					<th onclick="goJavaClass()">当前加载类总量</th>
					<td colspan="2">${parameter.jvmLoadClassCount}</td>
					<th>当前卸载类总量</th>
					<td colspan="2">${parameter.jvmUnLoadClassCount}</td>
				</tr>
				<tr>
					<th onclick="goThread()">守护线程数</th>
					<td>${parameter.daemonThreadCount}</td>
					<th>当前线程总数</th>
					<td>${parameter.threadCount}</td>
					<th>死锁线程数</th>
					<td>${parameter.deadLockedThreadCount}</td>
				</tr>
				<tr>
					<th onclick="goYoungGCCollection()">新生带GC名</th>
					<td>${parameter.youngGCName}</td>
					<th>新生带GC次数</th>
					<td>${parameter.youngGCCollectionCount}</td>
					<th>新生带GC时间</th>
					<td>${parameter.youngGCCollectionTime}</td>
				</tr>
				<tr>
					<th onclick="goFullGCCollection()">老年带GC名</th>
					<td>${parameter.fullGCName}</td>
					<th>老年带GC次数</th>
					<td>${parameter.fullGCCollectionCount}</td>
					<th>老年带GC时间</th>
					<td>${parameter.fullGCCollectionTime}</td>
				</tr>
				<tr>
					<th colspan="3">堆内存已使用</th>
					<td colspan="3">
						<div class="progress">
							<div onclick="goHeapMemory()" class="progress-bar" role="progressbar" style="width: 
							${parameter.heapMemoryInfo.heapMemoryUsed / parameter.heapMemoryInfo.heapMemoryMax * 100}%;" 
							aria-valuenow="60" aria-valuemin="0" aria-valuemax="100">
								<c:choose>
									<c:when test="${parameter.heapMemoryInfo.heapMemoryMax <= 0}">
										未设置最大值或最大值为零
									</c:when>
									<c:otherwise>
										${parameter.heapMemoryInfo.heapMemoryUsed / parameter.heapMemoryInfo.heapMemoryMax * 100}%
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<th colspan="3">堆外内存已使用</th>
					<td colspan="3">
						<div class="progress">
							<div onclick="goNonHeapMemory()" class="progress-bar" role="progressbar" style="width: 
							${parameter.nonHeapMemoryInfo.nonHeapMemoryUsed / parameter.nonHeapMemoryInfo.nonHeapMemoryMax * 100}%;" 
							aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">
								<c:choose>
									<c:when test="${parameter.nonHeapMemoryInfo.nonHeapMemoryMax <= 0}">
										未设置最大值或最大值为零
									</c:when>
									<c:otherwise>
										${parameter.nonHeapMemoryInfo.nonHeapMemoryUsed / parameter.nonHeapMemoryInfo.nonHeapMemoryMax * 100}%
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<th colspan="3">Eden内存已使用</th>
					<td colspan="3">
						<div class="progress">
							<div onclick="goEdenGen()" class="progress-bar" role="progressbar" style="width: 
							${parameter.edenGenInfo.edenGenUsed / parameter.edenGenInfo.edenGenMax * 100}%;" 
							aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">
								<c:choose>
									<c:when test="${parameter.edenGenInfo.edenGenMax <= 0}">
										未设置最大值或最大值为零
									</c:when>
									<c:otherwise>
										${parameter.edenGenInfo.edenGenUsed / parameter.edenGenInfo.edenGenMax * 100}%
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<th colspan="3">Survivor内存已使用</th>
					<td colspan="3">
						<div class="progress">
							<div onclick="goSurvivor()" class="progress-bar" role="progressbar" style="width: 
							${parameter.survivorInfo.survivorUsed / parameter.survivorInfo.survivorMax * 100}%;" 
							aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">
								<c:choose>
									<c:when test="${parameter.survivorInfo.survivorMax <= 0}">
										未设置最大值或最大值为零
									</c:when>
									<c:otherwise>
										${parameter.survivorInfo.survivorUsed / parameter.survivorInfo.survivorMax * 100}%
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<th colspan="3">老年带内存已使用</th>
					<td colspan="3">
						<div class="progress">
							<div onclick="goOldGen()" class="progress-bar" role="progressbar" style="width: 
							${parameter.oldGenInfo.oldGenUsed / parameter.oldGenInfo.oldGenMax * 100}%;" 
							aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">
								<c:choose>
									<c:when test="${parameter.oldGenInfo.oldGenMax <= 0}">
										未设置最大值或最大值为零
									</c:when>
									<c:otherwise>
										${parameter.oldGenInfo.oldGenUsed / parameter.oldGenInfo.oldGenMax * 100}%
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<th colspan="3">持久带内存已使用</th>
					<td colspan="3">
						<div class="progress">
							<div onclick="goPermGen()" class="progress-bar" role="progressbar" style="width: 
							${parameter.permGenInfo.permGenUsed / parameter.permGenInfo.permGenMax * 100}%;" 
							aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">
								<c:choose>
									<c:when test="${parameter.permGenInfo.permGenMax <= 0}">
										未设置最大值或最大值为零
									</c:when>
									<c:otherwise>
										${parameter.permGenInfo.permGenUsed / parameter.permGenInfo.permGenMax * 100}%
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<th colspan="3">CodeCache内存已使用</th>
					<td colspan="3">
						<div class="progress">
							<div onclick="goCodeCache()" class="progress-bar" role="progressbar" style="width: 
							${parameter.codeCacheInfo.codeCacheUsed / parameter.codeCacheInfo.codeCacheMax * 100}%;" 
							aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">
								<c:choose>
									<c:when test="${parameter.codeCacheInfo.codeCacheMax <= 0}">
										未设置最大值或最大值为零
									</c:when>
									<c:otherwise>
										${parameter.codeCacheInfo.codeCacheUsed / parameter.codeCacheInfo.codeCacheMax * 100}%
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</td>
				</tr>
				
			</tbody>
		</table>
	</div>
</body>
<script type="text/javascript">
	function goHeapMemory(){
		window.open('goHeapMemory')
	}
	function goNonHeapMemory(){
		window.open('goNonHeapMemory')
	}
	function goCodeCache(){
		window.open('goCodeCache')
	}
	function goEdenGen(){
		window.open('goEdenGen')
	}
	function goFullGCCollection(){
		window.open('goFullGCCollection')
	}
	function goJavaClass(){
		window.open('goJavaClass')
	}
	function goOldGen(){
		window.open('goOldGen')
	}
	function goPermGen(){
		window.open('goPermGen')
	}
	function goSurvivor(){
		window.open('goSurvivor')
	}
	function goThread(){
		window.open('goThread')
	}
	function goYoungGCCollection(){
		window.open('goYoungGCCollection')
	}
</script>
</html>