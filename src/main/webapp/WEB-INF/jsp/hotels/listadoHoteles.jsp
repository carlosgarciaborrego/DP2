<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="hotels">
    <h2>Hoteles</h2>

    <table id="hotelsTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Name</th>
            <th style="width: 200px;">Location</th>
            <th>Count</th>
            <th>Capacity</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${hotel}" var="hotel">
            <tr>
                <td>
                   <c:out value="${hotel.name}"/>
                </td>
                <td>
                    <c:out value="${hotel.location}"/>
                </td>
                <td>
                    <c:out value="${hotel.count}"/>
                </td>
                <td>
                    <c:out value="${hotel.capacity}"/>
                </td>
                <td>
                	<spring:url value="/hotels/delete/{hotelId}" var="hotelUrl">
                        <spring:param name="hotelId" value="${hotel.id}"/>
                    </spring:url>
                	<a href="${fn:escapeXml(hotelUrl)}">Delete</a>
                	<br>
                	<spring:url value="/hotels/{hotelId}" var="hotelUrl">
                        <spring:param name="hotelId" value="${hotel.id}"/>
                    </spring:url>
                	<a href="${fn:escapeXml(hotelUrl)}">Show</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
     <div class="form-group">
            <a class="btn btn-default" href='<spring:url value="/hotels/new" htmlEscape="true"/>'>New Hotel</a>
        </div>
</petclinic:layout>
