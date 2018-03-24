<%--
 * action-1.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jstl:if test="${hasParent}">
	<h2>
		<spring:message code="folder.navigating" />
		<jstl:out value="${folder.name}" />
	</h2>
	<spring:url var="parentURI" value="actor/message/list.do">
		<spring:param name="folderId" value="${folder.father.id}" />
	</spring:url>
	<a href="${parentURI}"><spring:message code="folder.returnToParent" /></a>
</jstl:if>
<h2>
	<spring:message code="folder.list" />
</h2>
<spring:url var="requestURI" value="actor/message/list.do">
	<spring:param name="folderId" value="${folder.id}" />
</spring:url>
<display:table name="folders" id="folderRow" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<spring:message code="folder.name" var="folderName" />
	<display:column property="name" title="${folderName}" />

	<spring:url var="viewFolder" value="actor/message/list.do">
		<spring:param name="folderId" value="${folderRow.id}" />
	</spring:url>
	<display:column>
		<a href="${viewFolder}"><spring:message code="folder.view" /></a>
	</display:column>

	<spring:message code="folder.messageNo" var="messageNo" />
	<display:column title="${messageNo}">
	${fn:length(folderRow.messages)}
	</display:column>

	<spring:url var="editFolder" value="actor/folder/edit.do">
		<spring:param name="folderId" value="${folderRow.id}" />
	</spring:url>
	<display:column>
		<jstl:if test="${!folderRow.systemFolders}">
			<a href="${editFolder}"><spring:message code="folder.edit" /></a>
		</jstl:if>
	</display:column>
</display:table>
<h2>
	<spring:message code="message.list" />
</h2>
<spring:url var="createFolder" value="actor/folder/create.do">
	<spring:param name="folderId" value="${folder.id}" />
</spring:url>
<a href="${createFolder}"><spring:message code="folder.create" /></a>
<jstl:set var="selectNumber" value="0" />
<display:table name="messages" id="messageRow"
	requestURI="${requestURI}" pagesize="5" class="displaytag">

	<spring:message code="message.subject" var="messageSubject" />
	<display:column property="subject" title="${messageSubject}" />

	<spring:message code="message.from" var="messageFrom" />
	<display:column property="actor.name" title="${messageFrom}" />

	<spring:url var="viewMessage" value="actor/message/display.do">
		<spring:param name="messageId" value="${messageRow.id}" />
	</spring:url>
	<display:column>
		<a href="${viewMessage}"><spring:message code="message.view" /></a>
	</display:column>

	<spring:url var="deleteMessage" value="actor/message/delete.do">
		<spring:param name="messageId" value="${messageRow.id}" />
		<spring:param name="currentFolderId" value="${folder.id}" />
	</spring:url>
	<display:column>
		<a href="${deleteMessage}"><spring:message code="message.delete" /></a>
	</display:column>

	<spring:url var="moveMessage" value="actor/message/move.do">
		<spring:param name="messageId" value="${messageRow.id}" />
	</spring:url>
	<spring:message code="message.move" var="messageMove" />
	<display:column>
		<form action="${moveMessage}" method="post">
			<input type="hidden" name="messageId" value="${messageRow.id}">
			<input type="hidden" name="currentFolderId" value="${folder.id}">
			<select name="targetFolderId">
				<jstl:forEach var="selectedFolder" items="${allFolders}">
					<option value="${selectedFolder.id}">${selectedFolder.name}</option>
				</jstl:forEach>
			</select> <input type="submit" value="${messageMove}">
		</form>
	</display:column>
</display:table>
<a href="actor/message/create.do"><spring:message
		code="message.create" /></a>