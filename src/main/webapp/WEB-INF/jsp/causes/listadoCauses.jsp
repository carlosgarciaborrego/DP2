<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security"
  uri="http://www.springframework.org/security/tags" %>

<petclinic:layout pageName="causes">
    <h2>Causes</h2>


    <table id="causesTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Name</th>
            <th style="width: 200px;">Description</th>
            <th>Organisation</th>
            <th>Target</th>
            <th>Archivied</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${cause}" var="cause">
            <tr>
                <td>
                   <c:out value="${cause.name}"/>
                </td>
                <td>
                    <c:out value="${cause.description}"/>
                </td>
                <td>
                    <c:out value="${cause.organisation}"/>
                </td>
                <td>
                    <c:out value="${cause.budgetTarget}"/>
                </td>
                <td>
                    <c:out value="${cause.budgetArchivied}"/>
                </td>
                <td>
                	
               		<spring:url value="/causes/donate/{causeId}" var="causeUrl">
                       	<spring:param name="causeId" value="${cause.id}"/>
                    </spring:url>
                		<a href="${fn:escapeXml(causeUrl)}" class="btn btn-default">Donate</a>
                	<br>

                	<spring:url value="/causes/{causeId}" var="causeUrl">
                        <spring:param name="causeId" value="${cause.id}"/>
                    </spring:url>
                	<a href="${fn:escapeXml(causeUrl)}" class="btn btn-default">Show</a>
                	<br>
                	<security:authorize access="hasAuthority('admin')">
	                	<spring:url value="/causes/delete/{causeId}" var="causeUrl">
	                        <spring:param name="causeId" value="${cause.id}"/>
	                    </spring:url>
	                	<a href="${fn:escapeXml(causeUrl)}" class="btn btn-default">Delete</a>
                	</security:authorize>
                	
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
     <security:authorize access="hasAuthority('admin')">
     	<div class="form-group">
            <a class="btn btn-default" href='<spring:url value="/causes/new" htmlEscape="true"/>'>New Cause</a>
        </div>
      </security:authorize>  
</petclinic:layout>
