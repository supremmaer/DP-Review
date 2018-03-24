<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('ADMINISTRATOR')">
<!-- Listing grid -->

<b><spring:message code="administrator.avgApT"/></b>
<fmt:formatNumber value="${avgApplicationsperTrip}" pattern="####0.00"/>
<br/>
<b><spring:message code="administrator.minApT"/></b>
<fmt:formatNumber value="${minApplicationsperTrip}" pattern="####0.00"/>
<br/>
<b><spring:message code="administrator.maxApT"/></b>
<fmt:formatNumber value="${maxApplicationsperTrip}" pattern="####0.00"/>
<br/>
<b><spring:message code="administrator.sdApT"/></b>
<fmt:formatNumber value="${sdApplicationsperTrip}" pattern="####0.00"/>
<br/><br/><br/>

<b><spring:message code="administrator.avgTpM"/></b>
<fmt:formatNumber value="${avgTripperManager}" pattern="####0.00"/>
<br/>
<b><spring:message code="administrator.minTpM"/></b>
<fmt:formatNumber value="${minTripperManager}" pattern="####0.00"/>
<br/>
<b><spring:message code="administrator.maxTpM"/></b>
<fmt:formatNumber value="${maxTripperManager}" pattern="####0.00"/>
<br/>
<b><spring:message code="administrator.sdTpM"/></b>
<fmt:formatNumber value="${sdTripperManager}" pattern="####0.00"/>
<br/><br/><br/>

<b><spring:message code="administrator.avgPT"/></b>
<fmt:formatNumber value="${avgPriceofTrip}" pattern="####0.00"/>
<br/>
<b><spring:message code="administrator.minPT"/></b>
<fmt:formatNumber value="${minPriceofTrip}" pattern="####0.00"/>
<br/>
<b><spring:message code="administrator.maxPT"/></b>
<fmt:formatNumber value="${maxPriceofTrip}" pattern="####0.00"/>
<br/>
<b><spring:message code="administrator.sdPT"/></b>
<fmt:formatNumber value="${sdPriceofTrip}" pattern="####0.00"/>
<br/><br/><br/>

<b><spring:message code="administrator.avgTpR"/></b>
<fmt:formatNumber value="${avgTripperRanger}" pattern="####0.00"/>
<br/>
<b><spring:message code="administrator.minTpR"/></b>
<fmt:formatNumber value="${minTripperRanger}" pattern="####0.00"/>
<br/>
<b><spring:message code="administrator.maxTpR"/></b>
<fmt:formatNumber value="${maxTripperRanger}" pattern="####0.00"/>
<br/>
<b><spring:message code="administrator.sdTpR"/></b>
<fmt:formatNumber value="${sdTripperRanger}" pattern="####0.00"/>
<br/><br/><br/>

<b><spring:message code="administrator.RAP"/></b>
<fmt:formatNumber value="${ratioPendingApplication}" pattern="####0.00"/>
<br/>
<b><spring:message code="administrator.RAD"/></b>
<fmt:formatNumber value="${ratioDueApplication}" pattern="####0.00"/>
<br/>
<b><spring:message code="administrator.RAA"/></b>
<fmt:formatNumber value="${ratioAcceptedApplication}" pattern="####0.00"/>
<br/>
<b><spring:message code="administrator.RAC"/></b>
<fmt:formatNumber value="${ratioCancelledApplication}" pattern="####0.00"/>
<br/><br/><br/>

<b><spring:message code="administrator.RCvTo"/></b>
<fmt:formatNumber value="${ratioCancelledvstotalTrip}" pattern="####0.00"/>
<br/><br/><br/>

<b><spring:message code="administrator.LaA"/></b>
<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="aboveAverageTrip" requestURI="${requestURI}" id="row">
	
	<b><spring:message code="administrator.LaA" var="title"/></b>
	<display:column title="${title}">
		<a href="trip/display.do?tripId=${row.id}">${row.title}</a>
	</display:column>
	
	<display:column>
		<jstl:out value="${row.applications.size()}"/>
	</display:column>
</display:table>
<br/><br/><br/>

<b><spring:message code="administrator.NtLR"/></b>
<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="numberReferenceLegalText" requestURI="${requestURI}" id="row">
	
	<spring:message code="legalText.title" var="title" />
	<display:column title="${title}" sortable="false">
		<a href="legalText/administrator/display.do?legalTextId=${row.id}">${row.title}</a>
	</display:column>
	
	<display:column>
		<jstl:out value="${row.trips.size()}"/>
	</display:column>
</display:table>
<br/><br/><br/>

<b><spring:message code="administrator.avgNpT"/></b>
<fmt:formatNumber value="${avgNotesperTrip}" pattern="####0.00"/>
<br/>
<b><spring:message code="administrator.minNpT"/></b>
<fmt:formatNumber value="${minNotesperTrip}" pattern="####0.00"/>
<br/>
<b><spring:message code="administrator.maxNpT"/></b>
<fmt:formatNumber value="${maxNotesperTrip}" pattern="####0.00"/>
<br/>
<b><spring:message code="administrator.sdNpT"/></b>
<fmt:formatNumber value="${sdNotesperTrip}" pattern="####0.00"/>
<br/><br/><br/>

<b><spring:message code="administrator.avgARpT"/></b>
<fmt:formatNumber value="${avgAuditRecordsperTrip}" pattern="####0.00"/>
<br/>
<b><spring:message code="administrator.minARpT"/></b>
<fmt:formatNumber value="${minAuditRecordsperTrip}" pattern="####0.00"/>
<br/>
<b><spring:message code="administrator.maxARpT"/></b>
<fmt:formatNumber value="${maxAuditRecordsperTrip}" pattern="####0.00"/>
<br/>
<b><spring:message code="administrator.sdARpT"/></b>
<fmt:formatNumber value="${sdAuditRecordsperTrip}" pattern="####0.00"/>
<br/><br/><br/>

<b><spring:message code="administrator.RTwAR"/></b>
<fmt:formatNumber value="${ratioTripOneAuditRecord}" pattern="####0.00"/>
<br/><br/>
<b><spring:message code="administrator.RRwRC"/></b>
<fmt:formatNumber value="${ratioRangerCurriculaRegistered}" pattern="####0.00"/>
<br/><br/>
<b><spring:message code="administrator.RRwEC"/></b>
<fmt:formatNumber value="${atioRangerCurriculumEndorsed}" pattern="####0.00"/>
<br/><br/>
<b><spring:message code="administrator.RSM"/></b>
<fmt:formatNumber value="${ratioSuspiciousManagers}" pattern="####0.00"/>
<br/><br/>
<b><spring:message code="administrator.RSR"/></b>
<fmt:formatNumber value="${ratioSuspiciousRangers}" pattern="####0.00"/>
<br/><br/><br/>

</security:authorize>
	