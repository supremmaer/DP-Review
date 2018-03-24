<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="endorserRecord/ranger/edit.do"
	modelAttribute="endorserRecord">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<form:label path="fullNameEndorser">
		<spring:message code="endorserRecord.fullNameEndorser" />:
	</form:label>
	<form:input path="fullNameEndorser" />
	<form:errors cssClass="error" path="fullNameEndorser" />
	<br />
	
	<form:label path="email">
		<spring:message code="endorserRecord.email" />:
	</form:label>
	<form:input path="email" />
	<form:errors cssClass="error" path="email" />
	<br />
	
	<form:label path="phone">
		<spring:message code="endorserRecord.phone" />:
	</form:label>
	<form:input path="phone" />
	<form:errors cssClass="error" path="phone" />
	<br />
	
	<form:label path="linkedInProfile">
		<spring:message code="endorserRecord.linkedInProfile" />:
	</form:label>
	<form:input path="linkedInProfile" />
	<form:errors cssClass="error" path="linkedInProfile" />
	<br />
	
	<form:label path="comment">
		<spring:message code="endorserRecord.comment" />:
	</form:label>
	<form:input path="comment" />
	<form:errors cssClass="error" path="comment" />
	<br />
	
	<spring:message code="endorserRecord.save" var="saveButton"/>
	<input type="submit" name="save" value="${saveButton }" />
	
	<jstl:if test="${endorserRecord.id != 0}">
		<input type="submit" name="delete" value="<spring:message code="endorserRecord.delete"/>"/>
	</jstl:if>

	
	<spring:message code="endorserRecord.cancel" var="cancel"/>
	<input type="button" name="cancel" value="${cancel}" onclick="javascript:relativeRedir('endorserRecord/list.do?rangerId=${rangerId}');" />

</form:form>

<spring:message code="endorserRecord.phoneCheck" var="phoneCheck" />
<script>
	$('#endorserRecord').submit(function() {
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