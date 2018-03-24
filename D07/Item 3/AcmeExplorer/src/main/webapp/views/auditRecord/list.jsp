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
	name="auditRecords" requestURI="${requestURI}" id="row">
	
	<!-- Attributes -->
	
	<spring:message code="auditRecord.title" var="titleHeader" />
	<display:column title="${titleHeader}" sortable="false">
		<a href="auditRecord/display.do?auditRecordId=${row.id}">${row.title}</a>
	</display:column>
	
	<spring:message code="auditRecord.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}" sortable="false" format="{0,date,dd/MM/yyyy HH:mm}"/>
	
	<spring:message code="auditRecord.trip" var="tripHeader" />
	<display:column title="${tripHeader}" sortable="false" >
		<a href="trip/display.do?tripId=${row.trip.id}">${row.trip.title}</a>
	</display:column>
	
</display:table>
