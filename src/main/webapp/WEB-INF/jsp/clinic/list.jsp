<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security"
  uri="http://www.springframework.org/security/tags" %>

<petclinic:layout pageName="clinics">
    <h2>Clinics</h2>

    <table id="clinicTable" class="table table-striped">
        <thead>
        <tr>
            <th>Name</th>
            <th>Capacity</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${clinics}" var="clinic">
            <tr>
                <td>
                    <c:out value="${clinic.name}"/>
                </td>
                <td>
                    <c:out value="${clinic.capacity}"/>
                </td>
               <td>
                 <security:authorize access="hasAuthority('admin')">
                   <spring:url value="/clinic/{clinicId}/delete" var="deleteUrl">
				        <spring:param name="clinicId" value="${clinic.id}"/>
				    </spring:url>
				    <a href="${fn:escapeXml(deleteUrl)}" class="btn btn-default">Delete</a>
				</security:authorize>
				    <spring:url value="/clinic/{clinicId}" var="showUrl">
				        <spring:param name="clinicId" value="${clinic.id}"/>
				    </spring:url>
				    <a href="${fn:escapeXml(showUrl)}" class="btn btn-default">Show</a>
                 </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

		<security:authorize access="hasAuthority('admin')">
				        <div class="form-group">
				            <div class="col-sm-offset-2 col-sm-10" style="text-align: right;">
				
				               <spring:url value="clinic/new" var="newUrl">
								    </spring:url>
								    <a href="${fn:escapeXml(newUrl)}" class="btn btn-default">New Clinic</a>
				            </div>
				        </div>
				        </security:authorize>

    
    
</petclinic:layout>
