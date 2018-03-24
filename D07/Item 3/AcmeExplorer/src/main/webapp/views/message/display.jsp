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

<h2>
	<spring:message code="message.subject" />
	<jstl:out value=": ${messageDisplay.subject}" />
</h2>
<h4>
	<spring:message code="message.from" />
	<jstl:out value=": ${messageDisplay.actor.name}" />
	<jstl:out value=" ${messageDisplay.actor.surname}" />
</h4>
<jstl:out value="${messageDisplay.body}" />

<a href="actor/message/list.do"><spring:message code="message.return" /></a>

<spring:url var="deleteMessage" value="actor/message/delete.do">
	<spring:param name="messageId" value="${messageDisplay.id}" />
</spring:url>
<a href="${deleteMessage}"><spring:message code="message.delete" /></a>