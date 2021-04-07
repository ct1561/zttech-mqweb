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
		<canvas id="myChart" >
		</canvas>
	
	</div>
	<button onclick="testOneFunction()">123</button>
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/Chart.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/moment.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-3.5.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/utils.js"></script>
<script type="text/javascript">
	var timeXData = [];
	var oneData = [];
	var twoData = [];
	
	var ctx = document.getElementById('myChart')
	
	var config = {
		type: 'line',
		data: {
			labels: timeXData,
			
			datasets: [{
				label: 'GC次数',
				yAxisID: 'used-y',
				backgroundColor:  [
                'rgba(255, 99, 132, 0.2)',
                'rgba(54, 162, 235, 0.2)',
                'rgba(255, 206, 86, 0.2)',
                'rgba(75, 192, 192, 0.2)',
                'rgba(153, 102, 255, 0.2)',
                'rgba(255, 159, 64, 0.2)'
            ],
				borderColor: window.chartColors.blue,
				data: oneData,
				fill: false
			}, {
				label: 'GC时间',
				yAxisID: 'proportion-y',
				backgroundColor:  [
                'rgba(255, 99, 132, 0.2)',
                'rgba(54, 162, 235, 0.2)',
                'rgba(255, 206, 86, 0.2)',
                'rgba(75, 192, 192, 0.2)',
                'rgba(153, 102, 255, 0.2)',
                'rgba(255, 159, 64, 0.2)'
            ],
				borderColor: window.chartColors.red,
				data: twoData,
				fill: false
			}]
			
			
		},
		options: {
			responsive: true,
			title: {
				display: true,
				text: '老年区GC情况表'
			},
			tooltips: {
				mode: 'index',
				intersect: false
			},
			hover: {
				mode: 'nearest',
				intersect: true
			},
			scales: {
				xAxes: [{
					display: true,
					scaleLabel: {
						display: true,
						labelString: '时间'
					}
				}],
				yAxes: [{
					type: 'linear',
					display: true,
					position: 'left',
					id: 'used-y',
					scaleLabel: {
						display:true,
						labelString: 'GC次数'
					}
				},{
					type: 'linear',
					display: true,
					position: 'right',
					id: 'proportion-y',
					scaleLabel: {
						display:true,
						labelString: 'GC时间（ms）'
					}
				}]
			}
		}
	};
	
	var myChart = new Chart(ctx,config)
	
	var secondFormat = 'h:mm'
	var dayFormat = 'MM-DD HH:mm'
	var stepLength = 1;
	
	var stringT;

	var sleep = function(time) {
	    var startTime = new Date().getTime() + parseInt(time, 10);
	    while(new Date().getTime() < startTime) {}
	};
	
	var ischart = new Chart(ctx, config);

	function testOneFunction(){
		$.ajax({
			url: 'getFullGCCollectionInfo',
			type: 'POST',
			async: false,
			success: function(msg){
				ischart.data.datasets.forEach(dataset => {
					if(dataset.data.length >= 60){
						dataset.data.shift()
					}

					if(dataset.label == "GC次数"){
						dataset.data.push(msg.fullGCCollectionCount)
					} else if(dataset.label == "GC时间"){
						dataset.data.push(msg.fullGCCollectionTime)
					}
				})
				
				if(stepLength < 5){
					stringT = moment().format(secondFormat)
				} else {
					stringT = moment().format(dayFormat)
					stepLength = 0
				}
				stepLength++

				if(ischart.data.labels.length >= 60){
					ischart.data.labels.shift()
				}
				
				ischart.data.labels.push(stringT)
				window.ischart.update()
			}
		})
		
		sleep(1000)
	}

 	setInterval(function(){
		testOneFunction()
    }, 1000) 
</script>
</html>
