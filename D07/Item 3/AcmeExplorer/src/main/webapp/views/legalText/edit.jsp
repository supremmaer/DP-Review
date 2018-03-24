<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<form:form action="legalText/administrator/edit.do"
	modelAttribute="legalText">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />
	<form:hidden path="trips" />
	<form:hidden path="draftMode" />

	<form:label path="title">
		<spring:message code="legalText.title" />:
	</form:label>
	<form:input path="title" />
	<form:errors cssClass="error" path="title" />
	<br />
	<form:label path="body">
		<spring:message code="legalText.body" />:
	</form:label>
	<form:textarea path="body" />
	<form:errors cssClass="error" path="body" />
	<br />
	<form:label path="laws">
		<spring:message code="legalText.laws" />:
	</form:label>
	<form:textarea path="laws" />
	<form:errors cssClass="error" path="laws" />
	<br />
	<jstl:if test="${!view}">
		<input type="submit" name="save"
			value="<spring:message code="legalText.save" />" />

		<jstl:if test="${legalText.id != 0}">
			<input type="submit" name="delete"
				value="<spring:message code="legalText.delete" />" />
		</jstl:if>
	</jstl:if>
</form:form>
<a href="legalText/administrator/list.do"><button type="button">
		<spring:message code="legalText.cancel" />
	</button></a>
<script>
	var disable = ${view};
	if (disable) {
		$("#legalText :input").attr('disabled', true);
	}
</script>