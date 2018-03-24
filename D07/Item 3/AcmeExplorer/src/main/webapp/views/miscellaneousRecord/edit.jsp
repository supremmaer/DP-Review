<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="miscellaneousRecord/ranger/edit.do"
	modelAttribute="miscellaneousRecord">
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<form:label path="title">
		<spring:message code="miscellaneousRecord.title" />:
	</form:label>
	<form:input path="title" />
	<form:errors cssClass="error" path="title" />
	<br />
	
	<form:label path="linkAttachment">
		<spring:message code="miscellaneousRecord.linkAttachment" />:
	</form:label>
	<form:input path="linkAttachment" />
	<form:errors cssClass="error" path="linkAttachment" />
	<br />
	
	<form:label path="comment">
		<spring:message code="miscellaneousRecord.comment" />:
	</form:label>
	<form:input path="comment" />
	<form:errors cssClass="error" path="comment" />
	<br />
	
		<spring:message code="miscellaneousRecord.save" var="saveButton"/>
	<input type="submit" name="save" value="${saveButton }" />
	
	<jstl:if test="${miscellaneousRecord.id != 0}">
		<input type="submit" name="delete" value="<spring:message code="miscellaneousRecord.delete"/>"/>
	</jstl:if>

	<spring:message code="miscellaneousRecord.cancel" var="cancel"/>
	<input type="button" name="cancel" value="${cancel}" onclick="javascript:relativeRedir('miscellaneousRecord/list.do?rangerId=${rangerId}');" />
	
	</form:form>