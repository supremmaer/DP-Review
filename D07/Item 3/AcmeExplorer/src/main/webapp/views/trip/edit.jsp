<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="trip/manager/edit.do" modelAttribute="trip">
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="ticker"/>
	<form:hidden path="sponsorships"/>
	<form:hidden path="notes"/>
	<form:hidden path="stages"/>
	<form:hidden path="applications"/>
	<form:hidden path="survivalClasses"/>
	<form:hidden path="manager"/>
	
	<spring:message code="trip.title" var="title"/>
	<form:label path="title">${title}</form:label>
	<form:input path="title"/>
	<form:errors cssClass="error" path="title"/>
	<br />

	<spring:message code="trip.description" var="description"/>
	<form:label path="description">${description}</form:label>
	<form:input path="description"/>
	<form:errors cssClass="error" path="description"/>
	<br />
	
	<spring:message code="trip.publicationDate" var="publicationDate"/>
	<form:label path="publicationDate">${publicationDate}</form:label>
	<form:input path="publicationDate" placeholder="dd/mm/yyyy HH:MM"/>
	<form:errors cssClass="error" path="publicationDate"/>
	<br />
	
	<spring:message code="trip.startDate" var="startDate"/>
	<form:label path="startDate">${startDate}</form:label>
	<form:input path="startDate" placeholder="dd/mm/yyyy HH:MM"/>
	<form:errors cssClass="error" path="startDate"/>
	<br />
	
	<spring:message code="trip.endDate" var="endDate"/>
	<form:label path="endDate">${endDate}</form:label>
	<form:input path="endDate" placeholder="dd/mm/yyyy HH:MM"/>
	<form:errors cssClass="error" path="endDate"/>
	<br />
	
	<!-- TODO category and ranger -->
	<spring:message code="trip.legalText" var="legalText"/>
	<form:label path="legalText">${legalText}</form:label>
	<form:select path="legalText">
		<form:option value="0" label="----" />		
		<form:options items="${legalTexts}" itemValue="id" itemLabel="title" />
	</form:select>
	<form:errors cssClass="error" path="legalText" />
	<br />
	
	<spring:message code="trip.category" var="category"/>
	<form:label path="category">${category}</form:label>
	<form:select path="category">
		<form:option value="0" label="----" />		
		<form:options items="${categories}" itemValue="id" itemLabel="name" />
	</form:select>
	<form:errors cssClass="error" path="category" />
	<br />
	
	<spring:message code="trip.ranger" var="ranger"/>
	<form:label path="ranger">${ranger}</form:label>
	<form:select path="ranger">
		<form:option value="0" label="----" />		
		<form:options items="${rangers}" itemValue="id" itemLabel="name" />
	</form:select>
	<form:errors cssClass="error" path="ranger" />
	<br />
	
	<spring:message code="trip.explorerRequirements" var="explorerRequirements"/>
	<form:label path="explorerRequirements">${explorerRequirements}</form:label>
	<form:textarea path="explorerRequirements"/>
	<form:errors cssClass="error" path="explorerRequirements"/>
	<br />
	
	<input type="submit" name="save" value="<spring:message code="trip.save" />" />
	<jstl:if test="${trip.id != 0}">
		<input type="submit" name="delete" value="<spring:message code="trip.delete"/>"/>
	</jstl:if>

	<spring:message code="trip.cancel" var="cancel" />
	<input type="button" name="cancel" value="${cancel}" onclick="javascript:relativeRedir('trip/list.do');" />
</form:form>