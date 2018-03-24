
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

<form:form action="story/explorer/edit.do" modelAttribute="story">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="explorer" />
	<form:hidden path="trip" />

	<form:label path="title">
		<spring:message code="story.title" />
	</form:label>
	<form:input path="title"/>
	<form:errors cssClass="error" path="title" />
	<br />
	
	<form:label path="text">
		<spring:message code="story.text" />
	</form:label>
	<form:textarea path="text" />
	<form:errors cssClass="error" path="text" />
	<br />
	
	<spring:message code="story.attachment"  var="attachment"/>
	<form:label path="attachments">
		<jstl:out value="${attachment}"/>
	</form:label>
	<form:textarea path="attachments" placeholder="http://example1.com, http://expample2.com"/>
	<form:errors cssClass="error" path="attachments"/>
	<br />
	
	<input type="submit" name="save"
		value="<spring:message code="story.save" />" /> 
	
	<spring:message code="story.cancel" var="cancel"/>
	<input type="button" name="cancel" value="${cancel}" onclick="javascript:relativeRedir('story/list.do?tripId=${story.trip.id}');" />
	<br />

</form:form>