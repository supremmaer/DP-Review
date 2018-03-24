<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!--  Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="true" 
name="notes" requestURI="${requestURI}" id="row">

	<!-- Attributes -->
	
	<spring:message code="note.moment" var="noteMoment" />
	<display:column property="moment" title="${noteMoment}" sortable="true" format="${datePattern}"/>
	
	<spring:message code="note.remark" var="noteRemark" />
	<display:column property="remark" title="${noteRemark}" sortable="false" />
	
	<spring:message code="note.trip" var="noteTrip" />
	<display:column title="${tripHeader}">
		<a href="trip/display.do?tripId=${row.trip.id}">${row.trip.title}</a>
	</display:column>
	
	<spring:message code="note.replyMoment" var="replyMoment" />
	<display:column property="replyMoment" title="${replyMoment}" sortable="false" format="${datePattern}"/>
	
	<spring:message code="note.reply" var="noteReply" />
	<display:column property="reply" title="${noteReply}" sortable="false" />
	
	<security:authorize access="hasRole('MANAGER')">
		<display:column>
			<jstl:if test="${row.replyMoment==null}">
			<a href="note/manager/edit.do?noteId=${row.id}">
				<spring:message	code="note.respond" />
			</a>
			</jstl:if>
		</display:column>
	</security:authorize>	
</display:table>	

