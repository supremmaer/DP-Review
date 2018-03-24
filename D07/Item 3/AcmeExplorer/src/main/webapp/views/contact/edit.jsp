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
<form:form action="contact/create.do" modelAttribute="contact">
	<form:hidden path="id" />
	<form:hidden path="version" />


	<form:label path="name">
		<spring:message code="contact.name" />
	</form:label>
	<form:input path="name" />
	<form:errors cssClass="error" path="name" />
	<br />
	<form:label path="email">
		<spring:message code="contact.email" />
	</form:label>
	<form:input type="email" path="email" />
	<form:errors cssClass="error" path="email" />
	<br />
	<form:label path="phone">
		<spring:message code="contact.phone" />
	</form:label>
	<form:input path="phone" />
	<form:errors cssClass="error" path="phone" />
	<br />
	<input type="submit" name="save"
		value="<spring:message code="contact.save" />" />
		
	<a href="${returnURL}"><button type="button">
			<spring:message code="contact.cancel" />
		</button></a>
</form:form>
