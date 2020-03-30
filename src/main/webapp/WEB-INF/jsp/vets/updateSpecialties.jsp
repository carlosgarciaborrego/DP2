<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security"
  uri="http://www.springframework.org/security/tags" %>

<petclinic:layout pageName="vets">

  <h2>Agregar especialidad </h2>
  
  	    <table id="specialitiesTable" class="table table-striped">
  	     <thead>
  	    <tr>
  	    	 <th>Nombre</th>
  	    	 <th>Action</th>
  	   	</tr>
  	   			</thead>
  	   	     <tbody>
  	   	    <c:forEach items="${specialties}" var="spe">
  	   	     <tr> 
  	   	    	 <td><c:out value="${spe} "/></td>
  	   	     <td>

  	   	      
  	   	     	 <spring:url value="/vet/{vetId}/specialty/delete/{specialtyId}" var="deleteUrl">
				        <spring:param name="specialtyId" value="${spe.id}"/>
				        <spring:param name="vetId" value="${vet.id}"/>
				    </spring:url>
				    <a href="${fn:escapeXml(deleteUrl)}" class="btn btn-default">Delete</a>
  	   	     </td>
  	   	     </tr>
  	   	    </c:forEach>
  	   	    </tbody>
  	    </table>
  	    
  	    
  	     <form:form modelAttribute="specialty" class="form-horizontal" id="add-specialty-form">
  	            <petclinic:inputField label="Specialty" name="name"/>
  	            
  	            <button class="btn btn-default" type="submit">Agregar especialidad</button>
  	     </form:form>

</petclinic:layout>