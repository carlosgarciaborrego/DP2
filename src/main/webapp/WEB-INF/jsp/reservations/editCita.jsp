<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security"
  uri="http://www.springframework.org/security/tags" %>
  <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<petclinic:layout pageName="reservations">
<jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#reservationDate").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <h2><c:if test="${reservation['new']}">New </c:if>Reservation</h2>
     
     
        <form:form modelAttribute="reservation" class="form-horizontal" action="../../petclinic/reservations/save">
            
           <security:authorize access="hasAuthority('owner')">
           <c:if test="${reservation['new']}">   
            <div class="form-group has-feedback">
            	<form:hidden path="id"/>
            	<form:hidden path="owner.id"/>
            	<form:hidden path="status"/>
                <petclinic:inputField label="Telephone" name="telephone"/>
                <petclinic:inputField label="Reservation Date" name="reservationDate"/>
               <petclinic:inputField label="Response Client" name="responseClient"/>
				<petclinic:selectField name="clinic" label="Clinics " names="${clinics}" size="3"/>
            </div>
           </c:if>
           
               <c:if test="${!reservation['new']}">
            <div class="form-group has-feedback">
            	<form:hidden path="id"/>
            	<form:hidden path="telephone"/>
            	<form:hidden path="reservationDate"/>
            	<form:hidden path="status"/>
            	<form:hidden path="clinic"/>
            	<form:hidden path="owner.id"/>
                <petclinic:inputFieldDisabled label="Telephone" name="telephone"/>
                <petclinic:inputFieldDisabled label="Reservation Date" name="reservationDate"/>
                <petclinic:inputFieldDisabled label="Status" name="status"/>
               	<c:if test="${reservation.status == 'pending'}">
                   <petclinic:inputField label="Response Client" name="responseClient"/>    
                </c:if> 
               	<c:if test="${reservation.status != 'pending'}">
                   <petclinic:inputFieldDisabled label="Response Client" name="responseClient"/>    
                </c:if>
               <petclinic:inputFieldDisabled label="clinic" name="clinic"/>
            </div>
           </c:if>
		</security:authorize>	
		
		
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    
                     <c:choose>
                    <c:when test="${reservation['new']}">
                      <button class="btn btn-default" type="submit">Add Reservation</button>
                    </c:when>
                    
                    <c:otherwise>
                    <c:if test="${reservation.status == 'pending'}">
                        <button class="btn btn-default" type="submit">Update Reservation</button>
                     </c:if>    
                    </c:otherwise>
                   
                </c:choose>
                </div>
            </div>
            
        </form:form>
   
     
        
    </jsp:body>

</petclinic:layout>



