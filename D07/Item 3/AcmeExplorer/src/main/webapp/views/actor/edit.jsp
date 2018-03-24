<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<form:form action="actor/edit.do" modelAttribute="actor">
	<form:hidden path="userAccount" />
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="socialIdentities" />
	<form:hidden path="messagesReceived" />
	<form:hidden path="messagesSent" />
	<form:hidden path="folders" />

	
	<form:label path="name">
		<spring:message code="actor.name" />
	</form:label>
	<form:input path="name" />
	<form:errors cssClass="error" path="name" />
	<br />
	<form:label path="surname">
		<spring:message code="actor.surname" />
	</form:label>
	<form:input path="surname" />
	<form:errors cssClass="error" path="surname" />
	<br />
	<form:label path="email">
		<spring:message code="actor.email" />
	</form:label>
	<form:input type="email" path="email" />
	<form:errors cssClass="error" path="email" />
	<br />
	<form:label path="phone">
		<spring:message code="actor.phone" />
	</form:label>
	<form:input path="phone" />
	<form:errors cssClass="error" path="phone" />
	<br />
	<form:label path="address">
		<spring:message code="actor.address" />
	</form:label>
	<form:input path="address" />
	<form:errors cssClass="error" path="address" />
	<br />
	<input type="submit" name="save"
		value="<spring:message code="actor.save" />" />
		
	<spring:message code="actor.cancel" var="cancel"/>
	<input type="button" name="cancel" value="${cancel}" onclick="javascript:relativeRedir('welcome/index.do');" />
</form:form>
<spring:message code="actor.phoneCheck" var="phoneCheck" />
<script>
	$('#actor').submit(function() {
		var regex = /^((\+[1-9][0-9]{0,2}\s[0-9]{4,})|(\+[1-9][0-9]{0,2}\s\([1-9][0-9]{0,2}\)\s[0-9]{4,})|([0-9]{4,})|(^$))$/;
		var phone = document.getElementById("phone").value;
		var submit = false;
		if (!regex.exec(phone)) {
			submit = confirm('<jstl:out value="${phoneCheck}" />');
		} else {
			submit = true;
		}
		return submit;
	});
</script>