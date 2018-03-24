<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<form:form action="finder/explorer/edit.do" modelAttribute="finder">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="trips" />

	<form:label path="keyWord">
		<spring:message code="finder.keyword" />:
	</form:label>
	<form:input path="keyWord" />
	<form:errors cssClass="error" path="keyWord" />
	<br />

	<form:label path="lowerPrice">
		<spring:message code="finder.lowerPrice" />:
	</form:label>
	<form:input path="lowerPrice" />
	<form:errors cssClass="error" path="lowerPrice" />
	<br />
	<form:label path="maxPrice">
		<spring:message code="finder.maxPrice" />:
	</form:label>
	<form:input path="maxPrice" />
	<form:errors cssClass="error" path="maxPrice" />
	<br />

	<form:label path="startDate">
		<spring:message code="finder.startDate" />
	</form:label>
	<form:input path="startDate" placeholder="dd/mm/yyyy HH:MM" />
	<form:errors cssClass="error" path="startDate" />
	<br />

	<form:label path="endDate">
		<spring:message code="finder.endDate" />
	</form:label>
	<form:input path="endDate" placeholder="dd/mm/yyyy HH:MM" />
	<form:errors cssClass="error" path="endDate" />
	<br />



	<input type="submit" name="save"
		value="<spring:message code="finder.save" />" />
	
	<spring:message code="finder.cancel" var="cancel"/>
	<input type="button" name="cancel" value="${cancel}" onclick="javascript:relativeRedir('welcome/index.do');" />
</form:form>