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

<!--  Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="legalTexts" requestURI="${requestURI}" id="row">

	<!-- Attributes -->
	<spring:message code="legalText.title" var="title" />
	<display:column property="title" title="${title}" sortable="false" />

	<spring:message code="legalText.draftMode" var="draftMode" />
	<spring:url var="setFinalLegalText"
		value="legalText/administrator/setFinal.do">
		<spring:param name="legalTextId" value="${row.id}" />
	</spring:url>
	<spring:url var="editURI" value="legalText/administrator/edit.do">
		<spring:param name="legalTextId" value="${row.id}" />
	</spring:url>
	<display:column title="${draftMode}">
		<jstl:choose>
			<jstl:when test="${row.draftMode}">
				<spring:message code="legalText.draft" />
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="${setFinalLegalText}"> <spring:message
						code="legalText.setFinal" />
				</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	<a href="${editURI}"> <spring:message code="legalText.edit" />
				</a>
			</jstl:when>
			<jstl:otherwise>
				<spring:message code="legalText.noDraft" />
			</jstl:otherwise>
		</jstl:choose>
	</display:column>

	<spring:message code="trip.datePattern" var="bar" />
	<spring:message code="legalText.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}"
		format="${bar}" />

	<spring:url var="displayLegalText"
		value="legalText/administrator/display.do">
		<spring:param name="legalTextId" value="${row.id}" />
	</spring:url>
	<display:column>
		<a href="${displayLegalText}"> <spring:message
				code="legalText.display" />
		</a>
	</display:column>


</display:table>
<a href="legalText/administrator/create.do"> <spring:message
		code="legalText.create" />
</a>
