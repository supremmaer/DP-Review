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

<%-- <display:table pagesize="10" class="displaytag" keepStatus="true" name="story.attachments" id="row">
	<display:column>
		<
	</display:column>
</display:table>
 --%>
<b><spring:message code="story.title"/></b>
<jstl:out value="${story.title}"/>
<br/>

<b><spring:message code="story.text"/></b>
<jstl:out value="${story.text}"/>
<br/>

<b><spring:message code="story.trip"/></b>
<a href="trip/display.do?tripId=${story.trip.id}">${story.trip.title}</a>
<br/>

<b><spring:message code="story.explorer"/></b>
<a href="actor/display.do?actorId=${story.explorer.id}">${story.explorer.name}</a>
<br/>


<b><spring:message code="story.attachment" /></b>
<br/>
<jstl:forEach var="attachment" items="${story.attachments}">
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<a href="${attachment}"><jstl:out value="${attachment}"/></a>
	<br/>
</jstl:forEach>

