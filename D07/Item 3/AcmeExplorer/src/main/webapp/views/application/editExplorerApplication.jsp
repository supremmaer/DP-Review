
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

<security:authorize access="hasRole('EXPLORER')">
<form:form action="application/explorer/edit.do"
	modelAttribute="application">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />
	<form:hidden path="explorer" />
	<form:hidden path="trip" />
	<form:hidden path="creditCard" />
	<form:hidden path="status"/>
	
	<security:authorize access="hasRole('EXPLORER')">
	<form:label path="comment">
		<spring:message code="application.comment" />:
	</form:label>
	<form:textarea path="comment" />
	<form:errors cssClass="error" path="comment" />
	<br />
	<form:hidden path="rejectingReason"/>
	</security:authorize>
	
	<input type="submit" name="save"
		value="<spring:message code="application.save" />" /> 

	
	<spring:message code="application.cancel" var="cancel"/>
	<input type="button" name="cancel" value="${cancel}" onclick="javascript:relativeRedir('application/explorer/list.do');" />
	<br />

</form:form>
</security:authorize>