<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Listing grid -->


<display:table pagesize="5" class="displaytag" keepStatus="true" name="stories" requestURI="${requestURI}" id="row">

	<!-- Attributes -->
	
	<spring:message code="story.tripHeader" var="tripHeader" />
	<display:column title="${tripHeader}">
		<a href="trip/display.do?tripId=${row.trip.id}">${row.trip.title}</a>
	</display:column>
	
	<spring:message code="story.actorHeader" var="actorHeader" />
	<display:column>
		<a href="actor/display.do?actorId=${row.explorer.id}">${row.explorer.name}</a>
	</display:column>
 
	<spring:message code="story.titleHeader" var="titleHeader" />
	<display:column title="${titleHeader}">
		<a href="story/display.do?storyId=${row.id}">${row.title}</a>
	</display:column>
	
	<spring:message code="story.textHeader" var="textHeader" />
	<display:column property="text" title="${textHeader}"/>
	
	<%-- <spring:message code="story.attachmentHeader" var="attachmentHeader" />
	<display:column property="attachment" title="${attachmentHeader}"/> --%>
	
</display:table>

<security:authorize access="hasRole('EXPLORER')">
	<jstl:if test="${doable}">
		<a href="story/explorer/create.do?tripId=${tripId}"><spring:message code="story.create"/></a>
	</jstl:if>
</security:authorize>

	