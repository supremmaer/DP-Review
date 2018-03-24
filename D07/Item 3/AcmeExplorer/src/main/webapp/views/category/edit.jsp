<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<form:form action="category/edit.do" modelAttribute="category">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="categories" />

	<form:label path="father">
		<spring:message code="category.parent" />:
	</form:label>
	<form:select path="father">
		<form:option label="${category.father.name}" value="${category.father.id}" />
		<form:options items="${categories}" itemLabel="name" itemValue="id" />
	</form:select>
	<br />
	<form:label path="name">
		<spring:message code="category.name" />:
	</form:label>
	<form:input path="name" />
	<form:errors cssClass="error" path="name" />

	<input type="submit" name="save"
		value="<spring:message code="category.save" />" />
		
	<jstl:if test="${category.id != 0}">
	<input type="submit" name="delete"
		value="<spring:message code="category.delete" />" />
	</jstl:if>
		
	<spring:message code="category.cancel" var="cancel"/>
	<input type="button" name="cancel" value="${cancel}" onclick="javascript:relativeRedir('category/list.do');" />
</form:form>