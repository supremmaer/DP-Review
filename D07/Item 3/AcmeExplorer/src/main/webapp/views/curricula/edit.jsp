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

<form:form action="curriculum/ranger/edit.do"
	modelAttribute="personalRecord">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<form:label path="fullName">
		<spring:message code="curricula.name" />:
	</form:label>
	<form:input path="fullName" />
	<form:errors cssClass="error" path="fullName" />
	<br />

	<form:label path="photo">
		<spring:message code="curricula.photo" />:
	</form:label>
	<form:input path="photo" />
	<form:errors cssClass="error" path="photo" />
	<br />

	<form:label path="email">
		<spring:message code="curricula.email" />:
	</form:label>
	<form:input path="email" />
	<form:errors cssClass="error" path="email" />
	<br />

	<form:label path="phone">
		<spring:message code="curricula.phone" />:
	</form:label>
	<form:input path="phone" />
	<form:errors cssClass="error" path="phone" />
	<br />
	
		<form:label path="url">
		<spring:message code="curricula.url" />:
	</form:label>
	<form:input path="url" />
	<form:errors cssClass="error" path="url" />
	<br />
	<spring:message code="educationRecord.save" var="saveButton"/>
	<input type="submit" name="save" value="${saveButton }" />
	
	<jstl:if test="${personalRecord.id != 0}">
		<input type="submit" name="delete" value="<spring:message code="educationRecord.delete"/>"/>
	</jstl:if>
	
	<spring:message code="educationRecord.cancel" var="cancel"/>
	<input type="button" name="cancel" value="${cancel}" onclick="javascript:relativeRedir('actor/display.do?actorId=0');" />

</form:form>
<spring:message code="curricula.phoneCheck" var="phoneCheck" />
<script>
	$('#personalRecord').submit(function() {
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