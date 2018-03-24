<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<form:form action="actor/message/send.do" modelAttribute="messageForm">

	<form:label path="destination">
		<spring:message code="message.destination" />:
	</form:label>
	<form:input path="destination" />
	<form:errors cssClass="error" path="destination" />
	<br />
	<form:label path="subject">
		<spring:message code="message.subject" />:
	</form:label>
	<form:input path="subject" />
	<form:errors cssClass="error" path="subject" />
	<br />
	<form:label path="body">
		<spring:message code="message.body" />:
	</form:label>
	<form:textarea path="body" />
	<form:errors cssClass="error" path="body" />
	<br />
	<form:label path="priority">
		<spring:message code="message.priority" />:
	</form:label>
	<select name="priority">
		<option value="HIGH">
			<spring:message code="message.priority.high" />
		</option>
		<option value="NEUTRAL">
			<spring:message code="message.priority.neutral" />
		</option>
		<option value="LOW">
			<spring:message code="message.priority.low" />
		</option>
	</select>
	<br />
	<input type="submit" name="send"
		value="<spring:message code="message.send" />" />
	
	<spring:message code="message.cancel" var="cancel"/>
	<input type="button" name="cancel" value="${cancel}" onclick="javascript:relativeRedir('actor/message/list.do');" />
</form:form>