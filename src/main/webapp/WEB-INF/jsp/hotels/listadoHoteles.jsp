<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security"
  uri="http://www.springframework.org/security/tags" %>

<petclinic:layout pageName="hotels">
    <h2>Hoteles</h2>


    <table id="hotelsTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Street</th>
            <th style="width: 200px;">City</th>
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
                	<spring:url value="/hotels/{hotelId}" var="hotelUrl">
                        <spring:param name="hotelId" value="${hotel.id}"/>
                    </spring:url>
                	<a href="${fn:escapeXml(hotelUrl)}">Show</a>
                	<br>
                	<security:authorize access="hasAuthority('admin')">
	                	<c:if test="${hotel.count == 0}">
		                	<spring:url value="/hotels/delete/{hotelId}" var="hotelUrl">
		                        <spring:param name="hotelId" value="${hotel.id}"/>
		                    </spring:url>
		                	<a href="${fn:escapeXml(hotelUrl)}">Delete</a>
		                </c:if>	
                	</security:authorize>  
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
     <security:authorize access="hasAuthority('admin')">
     <div class="form-group">
            <a class="btn btn-default" href='<spring:url value="/hotels/new" htmlEscape="true"/>'>New Hotel</a>
        </div>
 	</security:authorize>  
</petclinic:layout>
