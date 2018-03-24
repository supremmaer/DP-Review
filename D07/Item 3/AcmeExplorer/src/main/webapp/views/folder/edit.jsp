<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<form:form action="actor/folder/edit.do" modelAttribute="folder">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="folders" />
	<form:hidden path="messages" />
	<form:hidden path="systemFolders" />

	<form:label path="father">
		<spring:message code="folder.parent" />:
	</form:label>
	<form:select path="father">
		<form:option label="${folder.father.name}" value="${folder.father.id}" />
		<form:options items="${folders}" itemLabel="name" itemValue="id" />
	</form:select>
	<br />
	<form:label path="name">
		<spring:message code="folder.name" />:
	</form:label>
	<form:input path="name" />
	<form:errors cssClass="error" path="name" />

	<input type="submit" name="save"
		value="<spring:message code="folder.save" />" />
		
	<jstl:if test="${folder.name.length()>0}">
	<input type="submit" name="delete"
		value="<spring:message code="folder.delete" />" />
	</jstl:if>	
	<spring:message code="message.cancel" var="cancel"/>
	<input type="button" name="cancel" value="${cancel}" onclick="javascript:relativeRedir('actor/message/list.do');" />
</form:form>