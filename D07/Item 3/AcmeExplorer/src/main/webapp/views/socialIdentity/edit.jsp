<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="socialIdentity/actor/edit.do" modelAttribute="socialIdentity">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	
	<form:label path="nick">
		<spring:message code="socialIdentity.nick" />
	</form:label>
	<form:input path="nick"/>
	<form:errors cssClass="error" path="nick" />
	<br />
	
	<form:label path="socialNetwork">
		<spring:message code="socialIdentity.socialNetwork" />
	</form:label>
	<form:input path="socialNetwork"/>
	<form:errors cssClass="error" path="socialNetwork" />
	<br />
	
	<form:label path="profile">
		<spring:message code="socialIdentity.profile" />
	</form:label>
	<form:input path="profile"/>
	<form:errors cssClass="error" path="profile" />
	<br />
	
	<form:label path="photo">
		<spring:message code="socialIdentity.photo" />
	</form:label>
	<form:input path="photo"/>
	<form:errors cssClass="error" path="photo" />
	<br />
	
	
	<input type="submit" name="save" value="<spring:message code="socialIdentity.save" />" />
	
	<jstl:if test="${socialIdentity.id != 0}">
		<input type="submit" name="delete" value="<spring:message code="socialIdentity.delete" />" />
	</jstl:if>
	
	<spring:message code="socialIdentity.cancel" var="cancel"/>
	<input type="button" name="cancel" value="${cancel}" onclick="javascript:relativeRedir('socialIdentity/actor/list.do');" />
</form:form>
	
