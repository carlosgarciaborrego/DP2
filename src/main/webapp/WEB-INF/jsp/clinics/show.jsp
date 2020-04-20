<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security"
  uri="http://www.springframework.org/security/tags" %>

<petclinic:layout pageName="clinic">
    <h2>
        <c:if test="${clinic['new']}">New </c:if> Clinic
    </h2>
    <form:form modelAttribute="clinic" class="form-horizontal" id="add-owner-form">
         <security:authorize access="hasAuthority('admin')">
        <div class="form-group has-feedback">
        <form:hidden path="id" />
            <petclinic:inputField label="Name" name="name" />
            <petclinic:inputField label="Location" name="location" />
            <petclinic:inputField label="Telephone" name="telephone" />
            <petclinic:inputField label="Emergency" name="emergency" />
            <petclinic:inputField label="Capacity" name="capacity"/>
            <petclinic:inputField label="Email" name="email"/>
        </div>
        </security:authorize>
        <security:authorize access="hasAuthority('owner') or hasAuthority('veterinarian') ">
        <div class="form-group has-feedback">
        <form:hidden path="id" />
            <petclinic:inputFieldDisabled label="Name" name="name" />
            <petclinic:inputFieldDisabled label="Location" name="location" />
            <petclinic:inputFieldDisabled label="Telephone" name="telephone" />
            <petclinic:inputFieldDisabled label="Emergency" name="emergency" />
            <petclinic:inputFieldDisabled label="Capacity" name="capacity"/>
            <petclinic:inputFieldDisabled label="Email" name="email"/>
        </div>
        </security:authorize>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
             <security:authorize access="hasAuthority('admin')">
     
                <c:choose>
                    <c:when test="${clinic['new']}">
                        <button class="btn btn-default" type="submit">Add Clinic</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Update Clinic</button>
                    </c:otherwise>
                </c:choose>
              </security:authorize>
                 </div>
                 <div class="col-sm-offset-2 col-sm-10" style="text-align: right;">         
	   				 <spring:url value="/clinic/{clinicId}/vets" var="showUrl">
					 	<spring:param name="clinicId" value="${clinic.id}"/>
					 </spring:url>
					 <a href="${fn:escapeXml(showUrl)}" class="btn btn-default">Show vets</a>
                 </div>
           
        </div>

    </form:form>
</petclinic:layout>