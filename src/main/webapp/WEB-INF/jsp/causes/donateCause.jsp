<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security"
  uri="http://www.springframework.org/security/tags" %>

<petclinic:layout pageName="donations">
    <jsp:body>
        <h2><c:if test="${donation['new']}">New </c:if>Donation</h2>

        <form:form modelAttribute="donation" class="form-horizontal" action="../../causes/donate/">
        
            <div class="form-group has-feedback">
            
            	<form:hidden path="cause.id" name="causeId"/>
            	
                <petclinic:inputField label="Name" name="name" />
                <petclinic:inputField label="Amount" name="amount" />

            </div>
            
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
					
                	<button class="btn btn-default" type="submit">Donate to Cause</button>
               
                </div>
            </div>
          
        </form:form>
    </jsp:body>

</petclinic:layout>



