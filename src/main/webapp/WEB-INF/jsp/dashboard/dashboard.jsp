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
<h3>
	 <c:out value="Number of vets by clinics"></c:out>
</h3>
<div>
	<canvas id="canvas1" style="height: 370px; width: 100%;"></canvas>
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
				legend : {
					display : false
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
	

</script>