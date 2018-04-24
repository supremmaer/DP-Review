<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jsp:useBean id="today" class="java.util.Date"/>

<jstl:if test="${banner != null}">
	<div>
		<a href="${bannerLink}"><img style="max-width: 20vw;" src="${banner}" alt="Sponsorship Banner" /></a>
	</div>
</jstl:if>

<b><spring:message code="trip.title"/></b>
<jstl:out value="${trip.title}"/>
<br/>

<b><spring:message code="trip.description"/></b>
<jstl:out value="${trip.description}"/>
<br/>

<spring:message code="trip.display.datePattern" var="datePattern"/>

<b><spring:message code="trip.publicationDate"/></b>
<fmt:formatDate value="${trip.publicationDate}" pattern="${datePattern}"/>
<br/>

<b><spring:message code="trip.startDate"/></b>
<fmt:formatDate value="${trip.startDate}" pattern="${datePattern}"/>
<br/>

<b><spring:message code="trip.endDate"/></b>
<fmt:formatDate value="${trip.endDate}" pattern="${datePattern}"/>
<br/>

<b><spring:message code="trip.explorerRequirements"/></b>
<jstl:out value="${trip.explorerRequirements}"/>
<br/>

<b><spring:message code="trip.category"/></b>
<jstl:out value="${trip.category.name}"/>
<br/>

<spring:message code="trip.pricePattern" var="pricePattern"/>
<b><spring:message code="trip.price"/></b>
<spring:message code="trip.language" var="language"/>
<jstl:if test="${language=='English'}">&euro;</jstl:if>
<fmt:formatNumber value="${trip.price}" pattern="0000.00"/>
<jstl:if test="${language=='Spanish'}">&euro;</jstl:if>
<br/>

	<spring:message code="trip.modifytags" var="modTags"/>
	<b>Tags:</b>
	<br/>
	<jstl:forEach var="tag" items="${trip.tag}">
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<jstl:out value="${tag.key}"/>: <jstl:out value="${tag.value}"/>
		<br/>
	</jstl:forEach>

<jstl:if test="${managerId != null && trip.publicationDate > today}">
	<a href="trip/manager/editTag.do?tripId=${trip.id}"> <spring:message code="trip.modifytags"/></a>
	<br/>
</jstl:if> 


<b><spring:message code="trip.ranger"/></b>
<a href="actor/display.do?actorId=${trip.ranger.id }">
	<jstl:out value="${trip.ranger.name}"/> <jstl:out value="${trip.ranger.surname}"/>
</a> 
<br/>

<b>Manager:</b>
<a href="actor/display.do?actorId=${trip.manager.id }">
	<jstl:out value="${trip.manager.name}"/> <jstl:out value="${trip.manager.surname}"/>
</a>
<br/>

<jstl:if test="${trip.auditRecord != null}">
	<b><spring:message code="trip.auditRecord"/></b>
	<a href="auditRecord/display.do?auditRecordId=${trip.auditRecord.id}"><jstl:out value="${trip.auditRecord.title}"/></a>
	<br/>
</jstl:if>


<jstl:if test="${trip.cancellationReason != null}">
	<b><spring:message code="trip.cancellationReason"/></b>
	<jstl:out value="${trip.cancellationReason}"/>
	<br/>
</jstl:if>

<security:authorize access="hasRole('MANAGER')">
	<jstl:if test="${managerId != null && trip.publicationDate < today && today < trip.startDate && trip.cancellationReason == null}">
		<spring:message code="trip.cancelTrip" var="cancelTrip"/>
		<a href="trip/manager/cancel.do?tripId=${trip.id}"><button><jstl:out value="${cancelTrip}"/></button></a>
		<br/>
	</jstl:if>
</security:authorize>

<security:authorize access="hasRole('EXPLORER')">
	<jstl:if test="${applyable}">
		<spring:message code="trip.apply" var="apply"/>
		<a href="application/explorer/create.do?tripId=${trip.id}"><button><jstl:out value="${apply}"/></button></a>
		<br/>
	</jstl:if>
</security:authorize>

<security:authorize access="hasRole('SPONSOR')">
		<spring:message code="trip.sponsor" var="sponsor"/>
		<a href="sponsorship/sponsor/create.do?tripId=${trip.id}"><button><jstl:out value="${sponsor}"/></button></a>
		<br/>
</security:authorize>


<security:authorize access="hasRole('AUDITOR')">

	<jstl:if test="${auditorId != null && trip.auditRecord == null}">
	
		<a href="auditRecord/auditor/create.do?tripId=${trip.id}"><spring:message code="trip.audit"/></a>
		<br/>
	</jstl:if>
		
		<a href="note/auditor/create.do?tripId=${trip.id}"><spring:message code="trip.note"/></a>
		<br/>
</security:authorize>

<!-- Legal Text -->
<br/>
<h3><spring:message code="trip.legalText"/></h3>

<b><spring:message code="trip.legalText.title"/></b>
<jstl:out value="${trip.legalText.title}"/>
<br/>

<b><spring:message code="trip.legalText.body"/></b>
<jstl:out value="${trip.legalText.body}"/>
<br/>

<b><spring:message code="trip.legalText.laws"/></b>
<jstl:out value="${trip.legalText.laws}"/>
<br/>

<spring:message code="trip.legalText.momentPattern" var="momentPattern"/>
<b><spring:message code="legalText.moment"/></b>
<fmt:formatDate value="${trip.legalText.moment}" pattern="${momentPattern}"/>
<br/>
<br/>


<!-- TODO: Revisar la seguridad -->

<a href="story/list.do?tripId=${trip.id}"><spring:message code="stories"/></a> 

<a href="stage/list.do?tripId=${trip.id}"><spring:message code="stages"/></a> 

<a href="survivalClass/list.do?tripId=${trip.id}"><spring:message code="survivalClasses"/></a> 

