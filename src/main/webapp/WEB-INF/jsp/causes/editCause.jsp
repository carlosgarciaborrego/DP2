<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="causes">
    <jsp:body>
        <h2><c:if test="${cause['new']}">New </c:if>Cause</h2>

        <form:form modelAttribute="cause" class="form-horizontal" action="../../petclinic/causes/save">
            <div class="form-group has-feedback">
            	<form:hidden path="id"/>
                <petclinic:inputField label="Name" name="name"/>
                <petclinic:inputField label="Description" name="description"/>
                <petclinic:inputField label="Organisation" name="organisation"/>
                <petclinic:inputField label="BudgetTarget" name="budgetTarget"/>
                
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    
                     <c:choose>
                    <c:when test="${cause['new']}">
                    	<input type="hidden" name="causeId" value="${cause.id}"/>
                    	<input type="hidden" name=budgetArchivied value="0.0"/>
                      <button class="btn btn-default" type="submit">Add Cause</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Update Cause</button>
                    </c:otherwise>
                </c:choose>
                </div>
            </div>
        </form:form>
    </jsp:body>

</petclinic:layout>



