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

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="applications" requestURI="${requestURI}" id="row">
	
	<!-- Attributes -->
	
	<spring:message code="application.trip" var="tripHeader" />
	<display:column title="${tripHeader}">
		<a href="trip/display.do?tripId=${row.trip.id}">${row.trip.title}</a>
	</display:column>
 
	
	<spring:message code="application.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}" sortable="false" format="${application.moment.format}"/>
	
	<jstl:choose>
		<jstl:when test="${row.trip.startDate < date && row.status == PENDING}">
			<jstl:set value="RED" var="color"/>
		</jstl:when>
		<jstl:otherwise>
			<jstl:set value="${row.status}" var="color"/>
		</jstl:otherwise>
	</jstl:choose>
	<spring:message code="application.status" var="statusHeader" />
	<display:column title="${statusHeader}" sortable="true">
		<div class="status${color}">${row.status}</div>
	</display:column>
	
	<spring:message code="application.comment" var="commentHeader" />
	<display:column property="comment" title="${commentHeader}" sortable="false" />
	
	<!-- Action links -->
	
	<security:authorize access="hasRole('MANAGER')">
		<display:column>
		<jstl:if test="${row.status=='PENDING'}">
			<a href="application/manager/edit.do?applicationId=${row.id}">
				<spring:message	code="application.edit" />
			</a>
		</jstl:if>
		</display:column>
		
	<spring:message code="application.explorer" var="explorerHeader" />
		<display:column title="${explorerHeader}">
		<a href="actor/display.do?actorId=${row.explorer.id}">${row.explorer.name}</a>
	</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('EXPLORER')">
		<display:column>
			<jstl:if test="${row.status=='DUE'}">
			<a href="creditcard/explorer/create.do?applicationId=${row.id }">
				<spring:message	code="application.enterCreditCard" />
			</a>
			</jstl:if>	
		</display:column>
		
		<display:column>
			<jsp:useBean id="today" class="java.util.Date"/>
		<jstl:if test="${today < row.trip.startDate && row.status=='ACCEPTED'}">
			<spring:message code="application.cancelApplication" var="cancelApplication"/>
			<a href="application/explorer/cancel.do?applicationId=${row.id}"><button><jstl:out value="${cancelApplication}"/></button></a>
			<br/>
		</jstl:if>
		</display:column>
	
	</security:authorize>
	
	
</display:table>
	