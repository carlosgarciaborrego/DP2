<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security"
  uri="http://www.springframework.org/security/tags" %>

<petclinic:layout pageName="vet">
    <h2>
        <c:if test="${vet['new']}">New </c:if> Vet
    </h2>
    <form:form modelAttribute="vet" class="form-horizontal" id="add-owner-form">
        <div class="form-group has-feedback">
        <form:hidden path="id" />
            <petclinic:inputField label="First Name" name="firstName" readonly="!hasAuthority('admin')"/>
            <petclinic:inputField label="Last Name" name="lastName" readonly="!hasAuthority('admin')"/>
            <petclinic:inputField label="Address" name="address" readonly="!hasAuthority('admin')"/>
            <petclinic:inputField label="City" name="city" readonly="!hasAuthority('admin')"/>
            <petclinic:inputField label="Telephone" name="telephone" readonly="!hasAuthority('admin')"/>
        </div>
        <security:authorize access="hasAuthority('admin')">
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
     
                <c:choose>
                    <c:when test="${vet['new']}">
                        <button class="btn btn-default" type="submit">Add Vet</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Update Vet</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        </security:authorize>
    </form:form>
</petclinic:layout>
