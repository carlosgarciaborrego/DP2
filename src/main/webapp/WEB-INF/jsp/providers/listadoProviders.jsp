<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security"
  uri="http://www.springframework.org/security/tags" %>

<petclinic:layout pageName="providers">
    <h1>Providers List</h1>
	<br>
   <c:forEach items="${clinic}" var="clinic">
		<strong><font size=4>Clinic: ${clinic.name}</font></strong>
		<c:if test="${not empty clinic.providers}">
		    <table id="providersTable" class="table table-striped">
		        <thead>
		        <tr>
		            <th style="width: 200px;">Name</th>
		            <th style="width: 200px;">City</th>
		            <th style="width: 200px;">Telephone</th>
		            <th style="width: 300px;">Description</th>
		            <th>Actions</th>
		        </tr>
		        </thead>
		        <tbody>
		    	  		
		             <c:forEach items="${clinic.providers}" var="provider">
		            	<tr>
			                <td>
			                   <c:out value="${provider.name}"/>
			                </td>
			                <td>
			                    <c:out value="${provider.city}"/>
			                </td>
			                <td>
			                    <c:out value="${provider.telephone}"/>
			                </td>
			                <td>
			                    <c:out value="${provider.description}"/>
			                </td>
			              
			                
			                <td>
			                	<spring:url value="/providers/{providerId}" var="providerUrl">
			                        <spring:param name="providerId" value="${provider.id}"/>
			                    </spring:url>
			                	<a href="${fn:escapeXml(providerUrl)}">Show</a>
			                	<br>
			                	<spring:url value="/providers/delete/{providerId}" var="providerUrl">
			                        <spring:param name="providerId" value="${provider.id}"/>
			                    </spring:url>
			                	<a href="${fn:escapeXml(providerUrl)}">Delete</a>
			                </td>
		           		 </tr>
		       		 </c:forEach>
		       		 
		       	</tbody>
		    </table>
	    </c:if>
	    <c:if test="${ empty clinic.providers}">
	    	<h5> The clinic ${clinic.name} has neither a provider</h5>
	    	<br>
	    </c:if>
   	</c:forEach>
    
     <div class="form-group">
         <a class="btn btn-default" href='<spring:url value="/providers/new" htmlEscape="true"/>'>New Provider</a>
     </div>
</petclinic:layout>
