
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="auditRecord/auditor/edit.do"
	modelAttribute="auditRecord">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />
	<form:hidden path="auditor" />
	<form:hidden path="trip" />
	<form:hidden path="draftMode" />

	<form:label path="title">
		<spring:message code="auditRecord.title" />:
	</form:label>
	<form:input path="title" />
	<form:errors cssClass="error" path="title" />
	<br />

	<form:label path="description">
		<spring:message code="auditRecord.description" />:
	</form:label>
	<form:input path="description" />
	<form:errors cssClass="error" path="description" />
	<br />

	<form:label path="attachments">
		<spring:message code="auditRecord.attachments" />:
	</form:label>
	<form:textarea path="attachments" />
	<form:errors cssClass="error" path="attachments" />
	<br />
	
	<%-- <form:label path="draftMode">
		<spring:message code="auditRecord.draftMode" />
	</form:label>
	<form:checkbox path="draftMode" />
	<form:errors cssClass="error" path="draftMode" />
	<br /> --%>
	

	


	<input type="submit" name="save"
		value="<spring:message code="auditRecord.save" />" />
	<jstl:if test="${auditRecord.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="auditRecord.delete"/>" />
	</jstl:if>
	<spring:message code="auditRecord.cancel" var="cancel"/>
	<input type="button" name="cancel" value="${cancel}" onclick="javascript:relativeRedir('auditRecord/auditor/list.do');" />

	
</form:form>