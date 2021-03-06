<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security"
  uri="http://www.springframework.org/security/tags" %>
  <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<petclinic:layout pageName="hotels">
    <jsp:body>
        <h2><c:if test="${hotel['new']}">New </c:if>Hotel</h2>
     
	<security:authorize access="hasAuthority('admin')">
        <form:form modelAttribute="hotel" class="form-horizontal" action="../../petclinic/hotels/save">
            <div class="form-group has-feedback">
            	<form:hidden path="id"/>
                <petclinic:inputField label="Street" name="name"/>
                <petclinic:inputField label="City" name="location"/>
               	<petclinic:inputField label="Count" name="count"/>
				<petclinic:inputField label="Capacity" name="capacity"/>  
            </div>
			
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    
                     <c:choose>
                    <c:when test="${hotel['new']}">
                    	<input type="hidden" name="petId" value="${hotel.id}"/>
                      <button class="btn btn-default" type="submit">Add Hotel</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Update Hotel</button>
                    </c:otherwise>
                </c:choose>
                </div>
            </div>
        </form:form>
     </security:authorize>
     
  	<security:authorize access="hasAuthority('veterinarian')">
         <form:form modelAttribute="hotel" class="form-horizontal" action="../../petclinic/hotels/save">
            <div class="form-group has-feedback">
            	<form:hidden path="id"/>
                <petclinic:inputFieldDisabled label="Street" name="name"/>
                <petclinic:inputFieldDisabled label="City" name="location"/>
                <petclinic:inputFieldDisabled label="Count" name="count"/>
                <petclinic:inputFieldDisabled label="Capacity" name="capacity"/>
            </div>
        </form:form>
     </security:authorize>
        
    </jsp:body>

</petclinic:layout>



