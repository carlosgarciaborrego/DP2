<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security"
  uri="http://www.springframework.org/security/tags" %>
<petclinic:layout pageName="dashboard">

<div>
	<div>
		<canvas id="canvas1" style="height: 370px; width: 100%;"></canvas>
	</div>
	<div class="row">
	<div class="col-6">
		<canvas id="canvas2" style="height: 370px; width: 100%;"></canvas>
	</div>
	<div class="col-6">
		<canvas id="canvas3" style="height: 370px; width: 100%;"></canvas>
	</div>
	<div class="col-6">
		<canvas id="canvas4" style="height: 370px; width: 100%;"></canvas>
	</div>
	</div>
</div>
</petclinic:layout>
<script src="/petclinic/resources/libraries/chart.js/2.7.2/js/chart.bundle.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		
	
		var data = {
				labels : [
					<c:forEach var="t" items="${dashboard.vetsByClinics}">
						"${t}",
					</c:forEach>
				],
				datasets : [
					{
						data: [
							<c:forEach var="t" items="${dashboard.numVetsByClinics}">
							"${t}",
							</c:forEach>
						],
						backgroundColor: [
			                'rgba(255, 99, 132, 0.2)',
			                'rgba(54, 162, 235, 0.2)',
			                'rgba(255, 206, 86, 0.2)',
			                'rgba(75, 192, 192, 0.2)',
			                'rgba(153, 102, 255, 0.2)',
			                'rgba(255, 159, 64, 0.2)'
					]
					}
				]
		};
		var options = {
				scales : {
					yAxes : [
						{
							ticks : {
								suggestedMin : 0,
								suggestedMax : 20
							}
						}
					]
				},
				responsive: true,
				legend: {
					position: 'top',
				},
				title: {
					display: true,
					text: 'Number of vets by clinics'
				},
				animation: {
					animateScale: true,
					animateRotate: true
				}
				
		};
		var canvas, context;
		
		canvas = document.getElementById("canvas1");
		context = canvas.getContext("2d");
		new Chart(context, {
			type:"bar",
			data: data,
			options: options
		});
		
		
		});


	$(document).ready(function(){
		
		
		var data = {
				labels : [
					<c:forEach var="t" items="${dashboard.plazasByHotel}">
						"${t}",
					</c:forEach>
				],
				datasets : [
					{
						data: [
							<c:forEach var="t" items="${dashboard.numPlazasByHotel}">
							"${t}",
							</c:forEach>
						],
						backgroundColor: [
			                'rgba(255, 99, 132, 0.2)',
			                'rgba(54, 162, 235, 0.2)',
			                'rgba(255, 206, 86, 0.2)',
			                'rgba(75, 192, 192, 0.2)',
			                'rgba(153, 102, 255, 0.2)',
			                'rgba(255, 159, 64, 0.2)'
					]
					}
				]
		};
		var options = {
				responsive: true,
				legend: {
					position: 'top',
				},
				title: {
					display: true,
					text: 'Number of places by hotel'
				},
				animation: {
					animateScale: true,
					animateRotate: true
				}
			};
		var canvas, context;
		
		canvas = document.getElementById("canvas2");
		context = canvas.getContext("2d");
		new Chart(context, {
			type:"doughnut",
			data: data,
			
			options: options
		});
		
		
		});

	$(document).ready(function(){
		
		
		var data = {
				labels : [
					<c:forEach var="t" items="${dashboard.petsByVets}">
						"${t}",
					</c:forEach>
				],
				datasets : [
					{
						data: [
							<c:forEach var="t" items="${dashboard.numPetsByVet}">
							"${t}",
							</c:forEach>
						],
						backgroundColor: [
			                'rgba(255, 99, 132, 0.2)',
			                'rgba(54, 162, 235, 0.2)',
			                'rgba(255, 206, 86, 0.2)',
			                'rgba(75, 192, 192, 0.2)',
			                'rgba(153, 102, 255, 0.2)',
			                'rgba(255, 159, 64, 0.2)'
					]
					}
				]
		};
		var options = {
	
				responsive: true,
				legend: {
					position: 'top',
				},
				title: {
					display: true,
					text: 'Number of pets by vets'
				},
				animation: {
					animateScale: true,
					animateRotate: true
				}
			};
		var canvas, context;
		
		canvas = document.getElementById("canvas3");
		context = canvas.getContext("2d");
		new Chart(context, {
			type:"doughnut",
			data: data,
			
			options: options
		});
		
		
		});

	$(document).ready(function(){
		
		
		var data = {
				labels : [
					<c:forEach var="t" items="${dashboard.donationAmoundByCause}">
						"${t}",
					</c:forEach>
				],
				datasets : [
					{
						data: [
							<c:forEach var="t" items="${dashboard.numDonationAmoundByCause}">
							"${t}",
							</c:forEach>
						],
						backgroundColor: [
			                'rgba(255, 99, 132, 0.2)',
			                'rgba(54, 162, 235, 0.2)',
			                'rgba(255, 206, 86, 0.2)',
			                'rgba(75, 192, 192, 0.2)',
			                'rgba(153, 102, 255, 0.2)',
			                'rgba(255, 159, 64, 0.2)'
					]
					}
				]
		};
		var options = {
				responsive: true,
				title: {
					display: true,
					text: 'Donations by causes'
				},
				tooltips: {
					mode: 'index',
					intersect: false,
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
							labelString: 'Causes'
						}
					}],
					yAxes: [{
						display: true,
						scaleLabel: {
							display: true,
							labelString: 'Amount'
						},
						ticks : {
							suggestedMin : 0,
							suggestedMax : 2000
						}
					
					}]
				}
			};
		var canvas, context;
		
		canvas = document.getElementById("canvas4");
		context = canvas.getContext("2d");
		new Chart(context, {
			type:"line",
			data: data,
			
			options: options
		});
		
		
		});
	

</script>