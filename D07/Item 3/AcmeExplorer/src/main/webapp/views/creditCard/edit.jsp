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

<form:form action="creditcard/explorer/edit.do"
	modelAttribute="creditcard">

<form:hidden path="id"/>
<form:hidden path="version"/>
<input type="hidden" name="applicationId" value="${applicationId}">
	

<form:label path="holderName">
		<spring:message code="creditcard.holderName" />:
	</form:label>
	<form:input path="holderName" />
	<form:errors cssClass="error" path="holderName" />
	<br />
	
<form:label path="brandName">
		<spring:message code="creditcard.brandName" />:
	</form:label>
	<form:input path="brandName" />
	<form:errors cssClass="error" path="brandName" />
	<br />
	
<form:label path="number">
		<spring:message code="creditcard.number" />:
	</form:label>
	<form:input path="number" />
	<form:errors cssClass="error" path="number" />
	<br />
	
<form:label path="expirationMonth">
	<spring:message code="creditcard.expirationMonth"/>	
	</form:label>
	<form:input path="expirationMonth" />
	<form:errors cssClass="error" path="expirationMonth" />
	<br />
	
<form:label path="expirationYear">
	<spring:message code="creditcard.expirationYear"/>	
	</form:label>
	<form:input path="expirationYear" />
	<form:errors cssClass="error" path="expirationYear" />
	<br />
	
<form:label path="cvvCode">
	<spring:message code="creditcard.cvvCode"/>	
	</form:label>
	<form:input path="cvvCode" />
	<form:errors cssClass="error" path="cvvCode" />
	<br />

<input type="submit" name="save"
		value="<spring:message code="creditcard.save" />" />
		 
	<spring:message code="creditcard.cancel" var="cancel"/>
	<input type="button" name="cancel" value="${cancel}" onclick="javascript:relativeRedir('application/explorer/list.do');" />
	<br />	
	
</form:form>

