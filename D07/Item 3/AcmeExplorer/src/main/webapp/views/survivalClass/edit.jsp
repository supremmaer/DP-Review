<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<form:form action="survivalClass/manager/edit.do"
	modelAttribute="survivalClassForm">


	<form:hidden path="survivalClassId" />
	<form:hidden path="locationId" />

	<form:label path="trip">
		<spring:message code="survivalClass.trip" />
	</form:label>
	<jstl:out value="${survivalClassForm.trip.title }:"></jstl:out>
	<form:hidden path="trip" />
	<form:errors cssClass="error" path="trip" />

	<br />
	<form:label path="title">
		<spring:message code="survivalClass.title" />
	</form:label>
	<form:input path="title" />
	<form:errors cssClass="error" path="title" />
	<br />

	<form:label path="description">
		<spring:message code="survivalClass.description" />
	</form:label>
	<form:textarea path="description" />
	<form:errors cssClass="error" path="description" />
	<br />

	<form:label path="moment">
		<spring:message code="survivalClass.moment" />
	</form:label>
	<form:input path="moment" placeholder="dd/mm/yyyy HH:MM" />
	<form:errors cssClass="error" path="moment" />
	<br />

	<form:label path="name">
		<spring:message code="survivalClass.name" />
	</form:label>
	<form:input path="name" />
	<form:errors cssClass="error" path="name" />
	<br />

	<form:label path="latitude">
		<spring:message code="survivalClass.latitude" />
	</form:label>
	<form:input path="latitude" />
	<form:errors cssClass="error" path="latitude" />
	<br />

	<form:label path="longitude">
		<spring:message code="survivalClass.longitude" />
	</form:label>
	<form:input path="longitude" />
	<form:errors cssClass="error" path="longitude" />
	<br />

	<input type="submit" name="save"
		value="<spring:message code="survivalClass.manager.save" />" />
	<jstl:if test="${survivalClassForm.survivalClassId != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="survivalClass.manager.delete"/>" />
	</jstl:if>
	
	<spring:message code="survivalClass.explorer.cancel" var="cancel"/>
	<input type="button" name="cancel" value="${cancel}" onclick="javascript:relativeRedir('welcome/index.do');" />

</form:form>
