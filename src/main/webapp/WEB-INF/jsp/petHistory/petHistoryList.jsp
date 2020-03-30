<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security"
  uri="http://www.springframework.org/security/tags" %>

<petclinic:layout pageName="pethistories">
    <h2>Pet Histories</h2>

    <table id="pethistoriesTable" class="table table-striped">
        <thead>
        <tr>
            <th>Date</th>
            <th>Summary</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${petHistories}" var="petHistory">
            <tr>
                <td>
                    <c:out value="${petHistory.date}"/>
                </td>
                <td>
                    <c:out value="${petHistory.summary}"/>
                </td>
                
  			    <td>
   			    <security:authorize access="hasAuthority('veterinarian')">
                   <spring:url value="/vet/{vetId}/pets/{petId}/pethistory/{petHistoryId}/delete" var="deleteUrl">
				        <spring:param name="petHistoryId" value="${petHistory.id}"/>
				        <spring:param name="vetId" value="${vet.id}"/>
                    	<spring:param name="petId" value="${pet.id}"/>
				    </spring:url>
				    <a href="${fn:escapeXml(deleteUrl)}" class="btn btn-default">Delete</a>
				</security:authorize>
				    <spring:url value="/vet/{vetId}/pets/{petId}/pethistory/{petHistoryId}" var="showUrl">
				        <spring:param name="petHistoryId" value="${petHistory.id}"/>
				        <spring:param name="vetId" value="${vet.id}"/>
                   		<spring:param name="petId" value="${pet.id}"/>
				    </spring:url>
				    <a href="${fn:escapeXml(showUrl)}" class="btn btn-default">Show</a>
                </td>
		
                
            </tr>
        </c:forEach>
        </tbody>
    </table>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10" style="text-align: right;">

               <spring:url value="/vet/{vetId}/pets/{petId}/pethistory/new" var="newUrl">
                     <spring:param name="vetId" value="${vet.id}"/>
                     <spring:param name="petId" value="${pet.id}"/>
			   </spring:url>
				    <a href="${fn:escapeXml(newUrl)}" class="btn btn-default">New Pet History</a>
 
            </div>
        </div>
    

</petclinic:layout>
