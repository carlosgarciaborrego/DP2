<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security"
  uri="http://www.springframework.org/security/tags" %>

<petclinic:layout pageName="reservations">
    <h2>Reservations</h2>


    <table id="reservationsTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Owner</th>
            <th style="width: 200px;">Telephone</th>
            <th>Date</th>
            <th>Status</th>
            <th>ResponseClinic</th>
            <th>ResponseClient</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${reservation}" var="reservation">
            <tr>
                <td>
                   <c:out value="${reservation.owner.firstName} ${reservation.owner.lastName} "/>
                </td>
                <td>
                    <c:out value="${reservation.telephone}"/>
                </td>
                <td>
                    <c:out value="${reservation.reservationDate}"/>
                </td>
                <td>
                    <c:out value="${reservation.status}"/>
                </td>
                <td>
                    <c:out value="${reservation.responseClinic}"/>
                </td>
                 <td>
                    <c:out value="${reservation.responseClient}"/>
                </td>
                <td>
                  <security:authorize access="hasAuthority('owner')">
                	<spring:url value="/reservations/{reservationId}" var="reservationUrl">
                        <spring:param name="reservationId" value="${reservation.id}"/>
                    </spring:url>
                	<a href="${fn:escapeXml(reservationUrl)}">Show</a>
                	<br>
                	<c:if test="${reservation.status == 'pending'}">
	                	<spring:url value="/reservations/delete/{reservationId}" var="reservationUrl">
	                        <spring:param name="reservationId" value="${reservation.id}"/>
	                    </spring:url>
	                	<a href="${fn:escapeXml(reservationUrl)}">Delete</a>
	                </c:if> 	
	               </security:authorize>
		                <security:authorize access="hasAuthority('veterinarian')">
		               <spring:url value="/reservations/accepted/{reservationId}" var="reservationUrl">
	                        <spring:param name="reservationId" value="${reservation.id}"/>
	                    </spring:url>
	                	<a href="${fn:escapeXml(reservationUrl)}">Aceptar</a>
	                	
		                <spring:url value="/reservations/rejected/{reservationId}" var="reservationUrl">
	                        <spring:param name="reservationId" value="${reservation.id}"/>
	                    </spring:url>
	                	<a href="${fn:escapeXml(reservationUrl)}">Rechazar</a>
	                 </security:authorize>
	                	
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <security:authorize access="hasAuthority('owner')">
    
     	<div class="form-group">
            <a class="btn btn-default" href='<spring:url value="/reservations/new" htmlEscape="true"/>'>New Reservation</a>
        </div>
       
    </security:authorize>    
</petclinic:layout>
