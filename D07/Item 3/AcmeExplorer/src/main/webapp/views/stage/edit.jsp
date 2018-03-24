<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="stage/manager/edit.do" modelAttribute="stage">
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<input type="hidden" name="tripId" value="${tripId}">
	
	<spring:message code="stage.title" var="title"/>
	<form:label path="title">${title}</form:label>
	<form:input path="title"/>
	<form:errors cssClass="error" path="title"/>
	<br />
	
	<spring:message code="stage.description" var="description"/>
	<form:label path="description">${description}</form:label>
	<form:input path="description"/>
	<form:errors cssClass="error" path="description"/>
	<br />
	
	<spring:message code="stage.price" var="price"/>
	<form:label path="price">${price}</form:label>
	<form:input path="price"/>
	<form:errors cssClass="error" path="price"/>
	<br />

	<input type="submit" name="save" value="<spring:message code="stage.save" />" />
	<jstl:if test="${stage.id != 0}">
		<input type="submit" name="delete" value="<spring:message code="stage.delete"/>"/>
	</jstl:if>

	<spring:message code="stage.cancel" var="cancel"/>
	<input type="button" name="cancel" value="${cancel}" onclick="javascript:relativeRedir('stage/list.do?tripId=${tripId}');" />
</form:form>