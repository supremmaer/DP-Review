<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="sponsorship/sponsor/edit.do" modelAttribute="sponsorship">
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="trip"/>
	<input type="hidden" name="tripId" value="${tripId}">
	
	<!-- TODO add the trip somehow -->
	
	<spring:message code="sponsorship.banner" var="banner"/>
	<spring:url value="${banner}" var="bannerURL"/>
	<form:label path="banner">${bannerURL}</form:label>
	<form:input path="banner"/>
	<form:errors cssClass="error" path="banner"/>

	<spring:message code="sponsorship.infoPage" var="infoPage"/>
	<spring:url value="${infoPage}" var="infoPageURL"/>
	<form:label path="infoPage">${infoPageURL}</form:label>
	<form:input path="infoPage"/>
	<form:errors cssClass="error" path="infoPage"/>

	<input type="submit" name="save" value="<spring:message code="sponsorship.save" />" />
	
	<spring:message code="sponsorship.cancel" var="cancel"/>
	<input type="button" name="cancel" value="${cancel}" onclick="javascript:relativeRedir('sponsorship/list.do');" />
</form:form>