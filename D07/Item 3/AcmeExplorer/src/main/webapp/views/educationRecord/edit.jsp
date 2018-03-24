<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="educationRecord/ranger/edit.do"
	modelAttribute="educationRecord">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<form:label path="diploma">
		<spring:message code="educationRecord.diploma" />:
	</form:label>
	<form:input path="diploma" />
	<form:errors cssClass="error" path="diploma" />
	<br />
	
		<form:label path="startDate">
		<spring:message code="educationRecord.startDate" />:
	</form:label>
	<form:input path="startDate" placeholder="dd/mm/yyyy HH:MM" />
	<form:errors cssClass="error" path="startDate" />
	<br />
	
		<form:label path="endDate">
		<spring:message code="educationRecord.endDate" />:
	</form:label>
	<form:input path="endDate" placeholder="dd/mm/yyyy HH:MM" />
	<form:errors cssClass="error" path="endDate" />
	<br />
	
		<form:label path="institution">
		<spring:message code="educationRecord.institution" />:
	</form:label>
	<form:input path="institution" />
	<form:errors cssClass="error" path="institution" />
	<br />
	
		<form:label path="attachment">
		<spring:message code="educationRecord.attachment" />:
	</form:label>
	<form:input path="attachment" />
	<form:errors cssClass="error" path="attachment" />
	<br />
		<form:label path="comment">
		<spring:message code="educationRecord.comment" />:
	</form:label>
	<form:textarea path="comment" />
	<form:errors cssClass="error" path="comment" />
	<br />
	
	<spring:message code="educationRecord.save" var="saveButton"/>
	<input type="submit" name="save" value="${saveButton }" />
	
	<jstl:if test="${educationRecord.id != 0}">
		<input type="submit" name="delete" value="<spring:message code="educationRecord.delete"/>"/>
	</jstl:if>
	
	<spring:message code="educationRecord.cancel" var="cancel"/>
	<input type="button" name="cancel" value="${cancel}" onclick="javascript:relativeRedir('educationRecord/list.do?rangerId=${rangerId}');" />

</form:form>