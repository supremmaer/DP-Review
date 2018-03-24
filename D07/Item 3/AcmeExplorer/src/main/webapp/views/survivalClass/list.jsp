<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!--  Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="survivalClass" requestURI="${requestURI}" id="row">

	<!-- Attributes -->

	<spring:message code="survivalClass.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="true" />

	<spring:message code="survivalClass.datePattern" var="datePattern"/>
	
	<spring:message code="survivalClass.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}"
		sortable="false" format="${datePattern}" />

	<spring:message code="survivalClass.description"
		var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}"
		sortable="false" />

	<spring:message code="survivalClass.name" var="locationNameHeader" />
	<display:column property="location.name" title="${locationNameHeader}"
		sortable="false" />

	<spring:message code="survivalClass.latitude"
		var="locationLatitudeHeader" />
	<display:column property="location.latitude"
		title="${locationLatitudeHeader}" sortable="false" />

	<spring:message code="survivalClass.longitude"
		var="locationLongitudeHeader" />
	<display:column property="location.longitude"
		title="${locationLongitudeHeader}" sortable="false" />
	<!-- Action links -->

	<security:authorize access="hasRole('MANAGER')">
		<jstl:if test="${ (trip.manager.id==principalId)||requestURI =='survivalClass/manager/list.do'}">
			<display:column>
				<a href="survivalClass/manager/edit.do?survivalClassId=${row.id }">
					<spring:message code="survivalClass.manager.edit" />
				</a>
			</display:column>
		</jstl:if>


	</security:authorize>

	<security:authorize access="hasRole('EXPLORER')">


		<display:column>
			<spring:url var="enrollURL" value="survivalClass/explorer/enroll.do">
				<spring:param name="survivalClassId" value="${row.id }"></spring:param>
			</spring:url>
			<a href=${enrollURL }> <jstl:choose>
					<jstl:when test="${!registered.contains(row)}">
						<spring:message code="survivalClass.explorer.enroll" />
					</jstl:when>
					<jstl:otherwise>
						<spring:message code="survivalClass.explorer.cancel" />
					</jstl:otherwise>
				</jstl:choose>
			</a>
		</display:column>



	</security:authorize>

</display:table>

<jstl:out value="${message }"></jstl:out>
<security:authorize access="hasRole('MANAGER')">
	<jstl:if test="${ trip.manager.id==principalId}">
		<a href="survivalClass/manager/create.do?tripId=${trip.id }"> <spring:message
				code="survivalClass.manager.create" />
		</a>
	</jstl:if>



</security:authorize>
