<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<form:form action="systemConfiguration/administrator/edit.do"
	modelAttribute="systemConfigurationForm">
	
	<form:label path="VATTax">
		<spring:message code="systemConfiguration.VATTax" />:
	</form:label>
	<form:input path="VATTax" />
	<form:errors cssClass="error" path="VATTax" />
	<br />
	<form:label path="banner">
		<spring:message code="systemConfiguration.banner" />:
	</form:label>
	<form:input path="banner" />
	<form:errors cssClass="error" path="banner" />
	<br />
	<form:label path="countryCode">
		<spring:message code="systemConfiguration.countryCode" />:
	</form:label>
	<form:input path="countryCode" />
	<form:errors cssClass="error" path="countryCode" />
	<br />
	<form:label path="finderCacheTime">
		<spring:message code="systemConfiguration.finderCacheTime" />:
	</form:label>
	<form:input path="finderCacheTime" />
	<form:errors cssClass="error" path="finderCacheTime" />
	<br />
	<form:label path="finderResultsNumber">
		<spring:message code="systemConfiguration.finderResultsNumber" />:
	</form:label>
	<form:input path="finderResultsNumber" />
	<form:errors cssClass="error" path="finderResultsNumber" />
	<br />
	<form:label path="welcomeMessageEnglish">
		<spring:message code="systemConfiguration.welcomeMessageEnglish" />:
	</form:label>
	<form:textarea path="welcomeMessageEnglish" />
	<form:errors cssClass="error" path="welcomeMessageEnglish" />
	<br />
	
	<form:label path="welcomeMessageSpanish">
		<spring:message code="systemConfiguration.welcomeMessageSpanish" />:
	</form:label>
	<form:textarea path="welcomeMessageSpanish" />
	<form:errors cssClass="error" path="welcomeMessageSpanish" />
	<br />
	
	<form:label path="spamWords">
		<spring:message code="systemConfiguration.spamWords" />:
	</form:label>
	<form:textarea path="spamWords" />
	<form:errors cssClass="error" path="spamWords" />
	<br />
	<form:label path="tags">
		<spring:message code="systemConfiguration.tags" />:
	</form:label>
	<form:textarea path="tags" />
	<form:errors cssClass="error" path="tags" />
	<br />
	<jstl:if test="${!view}">
		<input type="submit" name="save"
			value="<spring:message code="systemConfiguration.save" />" />
	</jstl:if>
</form:form>
<jstl:if test="${!view}">
	<a href="systemConfiguration/administrator/display.do"><button
			type="button">
			<spring:message code="systemConfiguration.cancel" />
		</button></a>
</jstl:if>
<jstl:if test="${view}">
	<a href="systemConfiguration/administrator/edit.do"><button
			type="button">
			<spring:message code="systemConfiguration.edit" />
		</button></a>
</jstl:if>
<script>
	var disable = ${view};
	if (disable) {
		$("#systemConfigurationForm :input").attr('disabled', true);
	}
</script>