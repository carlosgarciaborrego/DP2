<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="vets">

    <h2>Vet Information</h2>


    <table class="table table-striped">
        <tr>
            <th>Name</th>
            <td><b><c:out value="${vet.firstName} ${vet.lastName}"/></b></td>
        </tr>
        <tr>
            <th>Address</th>
            <td><c:out value="${vet.address}"/></td>
        </tr>
        <tr>
            <th>City</th>
            <td><c:out value="${vet.city}"/></td>
        </tr>
        <tr>
            <th>Telephone</th>
            <td><c:out value="${vet.telephone}"/></td>
        </tr>
    </table>

    <br/>
    <br/>
    <br/>
    <h2>Pets</h2>

    <table class="table table-striped">
        <c:forEach var="pet" items="${pets}">

            <tr>
                <td valign="top">
                    <dl class="dl-horizontal">
                        <dt>Name</dt>
                        <dd><c:out value="${pet.name}"/></dd>
                        <dt>Birth Date</dt>
                        <dd><petclinic:localDate date="${pet.birthDate}" pattern="yyyy-MM-dd"/></dd>
                        <dt>Type</dt>
                        <dd><c:out value="${pet.type.name}"/></dd>
                    </dl>
                </td>
                   <td valign="top">
                    <table class="table-condensed">
                        <thead>
                        <tr>
                            <th>Pet History</th>
                        </tr>
                        </thead>
                        <tr>
                            <td>
                                <spring:url value="/vet/{vetId}/pets/{petId}/pethistory" var="petUrl">
                                    <spring:param name="vetId" value="${vet.id}"/>
                                    <spring:param name="petId" value="${pet.id}"/>
                                </spring:url>
                                <a href="${fn:escapeXml(petUrl)}">Pet History</a>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>

        </c:forEach>
    </table>

</petclinic:layout>
