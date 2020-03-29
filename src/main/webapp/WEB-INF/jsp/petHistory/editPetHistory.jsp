<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security"
  uri="http://www.springframework.org/security/tags" %>

<petclinic:layout pageName="pethistory">
    <h2>
        <c:if test="${petHistory['new']}">New </c:if> Pet History
    </h2>
    <form:form modelAttribute="petHistory" class="form-horizontal" id="add-owner-form">
        <div class="form-group has-feedback">
        <form:hidden path="id" />
        <form:hidden path="date" />
            <petclinic:inputField label="Summary" name="summary" />
            <petclinic:inputField label="Details" name="details" />

        </div>
 
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
     
                <c:choose>
                    <c:when test="${petHistory['new']}">
                        <button class="btn btn-default" type="submit">Add History</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Update History</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</petclinic:layout>
