<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security"
  uri="http://www.springframework.org/security/tags" %>

<petclinic:layout pageName="vets">
    <h2>Veterinarios</h2>

    <table id="vetsTable" class="table table-striped">
        <thead>
        <tr>
            <th>Name</th>
            <th>Specialties</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${vets.vetList}" var="vet">
            <tr>
                <td>
                    <c:out value="${vet.firstName} ${vet.lastName}"/>
                </td>
                <td>
                    <c:forEach var="specialty" items="${vet.specialties}">
                        <c:out value="${specialty.name} "/>
                    </c:forEach>
                    <c:if test="${vet.nrOfSpecialties == 0}">none</c:if>
                </td>
               <td>
               <security:authorize access="hasAuthority('admin')">
                   <spring:url value="vet/delete/{vetId}" var="deleteUrl">
				        <spring:param name="vetId" value="${vet.id}"/>
				    </spring:url>
				    <a href="${fn:escapeXml(deleteUrl)}" class="btn btn-default">Delete</a>
				</security:authorize>
              
            	<security:authorize access="hasAuthority('admin')">
                 <spring:url value="/vet/{vetId}/specialty/new" htmlEscape="true" var="addSpecialtyUrl">
                <spring:param name="vetId" value="${vet.id}"/>
                </spring:url>
                 <a class="btn btn-default" href='${addSpecialtyUrl}'>Agregar especialidad</a>
                </security:authorize>
                
                <security:authorize access="!hasAuthority('owner')">
                 	<spring:url value="vet/show/{vetId}" var="showUrl">
				        <spring:param name="vetId" value="${vet.id}"/>
				    </spring:url>
				    <a href="${fn:escapeXml(showUrl)}" class="btn btn-default">Show</a>
				</security:authorize>    
                 </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

				<security:authorize access="hasAuthority('admin')">
				        <div class="form-group">
				            <div class="col-sm-offset-2 col-sm-10" style="text-align: right;">
				
				               <spring:url value="vet/new" var="newUrl">
								    </spring:url>
								    <a href="${fn:escapeXml(newUrl)}" class="btn btn-default">New vet</a>
				 
				            </div>
				        </div>
				        </security:authorize>

    <table class="table-buttons">
        <tr>
            <td>
                <a href="<spring:url value="/vets.xml" htmlEscape="true" />">Ver como XML</a>
            </td>
            <td>
                <a href="<spring:url value="/vets.json" htmlEscape="true" />">Ver como JSON</a>
            </td>
        </tr>
    </table>
    
    
</petclinic:layout>
