<%--
 * action-1.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jstl:if test="${hasParent}">
	<h2>
		<spring:message code="category.navigating" />
		<jstl:out value="${category.name}" />
	</h2>
	<spring:url var="parentURI" value="category/list.do">
		<spring:param name="categoryId" value="${category.father.id}" />
	</spring:url>
	<a href="${parentURI}"><spring:message
			code="category.returnToParent" /></a>
</jstl:if>
<spring:url var="requestURI" value="category/list.do">
	<spring:param name="categoryId" value="${category.id}" />
</spring:url>
<display:table name="categories" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">
	<spring:url var="viewURL" value="category/list.do">
		<spring:param name="categoryId" value="${row.id}" />
	</spring:url>
	<display:column>
		<a href="${viewURL}"><spring:message code="category.viewCategory" /></a>
	</display:column>

	<spring:message code="category.name" var="categoryName" />
	<display:column property="name" title="${categoryName}" />

	<security:authorize access="hasRole('ADMINISTRATOR')">
		<spring:url var="editCategory" value="category/edit.do">
			<spring:param name="categoryId" value="${row.id}" />
		</spring:url>
		<display:column>
			<a href="${editCategory}"><spring:message code="category.edit" /></a>
		</display:column>
	</security:authorize>

</display:table>
<security:authorize access="hasRole('ADMINISTRATOR')">
	<spring:url var="createCategory" value="category/create.do">
		<spring:param name="categoryId" value="${row.id}" />
	</spring:url>
	<a href="${createCategory}"><spring:message code="category.create" /></a>
</security:authorize>
<spring:url var="tripsURI" value="trip/list.do">
	<spring:param name="categoryId" value="${category.id}" />
</spring:url>
<a href="${tripsURI}"><spring:message code="category.viewTrips" />
	(${tripNum})</a>