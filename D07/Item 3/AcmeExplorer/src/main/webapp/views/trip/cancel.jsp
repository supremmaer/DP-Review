<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form action="">
</form>
<form:form action="trip/manager/cancel.do" modelAttribute="trip">
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="ticker"/>
	<form:hidden path="sponsorships"/>
	<form:hidden path="notes"/>
	<form:hidden path="stages"/>
	<form:hidden path="applications"/>
	<form:hidden path="survivalClasses"/>
	<form:hidden path="manager"/>
	<form:hidden path="title"/>
	<form:hidden path="description"/>
	<form:hidden path="publicationDate"/>
	<form:hidden path="startDate"/>
	<form:hidden path="endDate" placeholder="dd/mm/yyyy HH:MM"/>
	<form:hidden path="legalText"/>
	<form:hidden path="category"/>
	<form:hidden path="ranger"/>
	<form:hidden path="explorerRequirements"/>
	
	<form:label path="cancellationReason">label</form:label>
	<form:textarea path="cancellationReason"/>

	<input type="submit" name="save" value="<spring:message code="trip.save" />" />
	
	<a href="trip/list.do">
		<button type="button">
			<spring:message code="trip.cancel" />
		</button>
	</a>
</form:form>