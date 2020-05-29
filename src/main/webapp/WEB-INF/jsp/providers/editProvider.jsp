<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security"
  uri="http://www.springframework.org/security/tags" %>
  <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<petclinic:layout pageName="providers">
    <jsp:body>
        <h2><c:if test="${provider['new']}">New </c:if>Provider</h2>
     
	<security:authorize access="hasAuthority('admin')">
        <form:form modelAttribute="provider" class="form-horizontal" action="../../petclinic/providers/save">
            <div class="form-group has-feedback">
            	<form:hidden path="id"/>
                <petclinic:inputField label="Name" name="name"/>
                <petclinic:inputField label="City" name="city"/>
                <petclinic:inputField label="Telephone" name="telephone"/>
                <petclinic:inputField label="Description" name="description"/>
               	<petclinic:selectField name="clinic" label="Clinics " names="${clinics}" size="3"/>
            </div>
			
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    
                     <c:choose>
                    <c:when test="${hotel['new']}">
                    	<input type="hidden" name="petId" value="${provider.id}"/>
                      <button class="btn btn-default" type="submit">Add Provider</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Update Provider</button>
                    </c:otherwise>
                </c:choose>
                </div>
            </div>
        </form:form>
     </security:authorize>

    </jsp:body>

</petclinic:layout>



