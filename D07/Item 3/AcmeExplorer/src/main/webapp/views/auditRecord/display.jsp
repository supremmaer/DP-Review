<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<b><spring:message code="auditRecord.title"/></b>
<jstl:out value="${auditRecord.title}"/>
<br/>

<b><spring:message code="auditRecord.auditor"/></b>
<a href="actor/display.do?actorId=${auditRecord.auditor.id}">
	<jstl:out value="${auditRecord.auditor.name}"/>
</a>
<br/>

<jstl:if test="${auditRecord.trip != null}">
	<b><spring:message code="auditRecord.trip"/></b>
	<a href="trip/display.do?tripId=${auditRecord.trip.id}"><jstl:out value="${auditRecord.trip.title}"/></a>
	<br/>
</jstl:if>

<spring:message code="auditRecord.display.datePattern" var="datePattern"/>

<b><spring:message code="auditRecord.moment"/></b>
<fmt:formatDate value="${auditRecord.moment}" pattern="${datePattern}"/>
<br/>

<b><spring:message code="auditRecord.description"/></b>
<jstl:out value="${auditRecord.description}"/>
<br/>

<b><spring:message code="auditRecord.attachments"/></b>
<br/>
<jstl:forEach var="attachment" items="${auditRecord.attachments}">
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<a href="${attachment}"><jstl:out value="${attachment}"/></a>
	<br/>
</jstl:forEach>
<br/>

<b><spring:message code="auditRecord.draftMode"/></b>
<jstl:out value="${auditRecord.draftMode}"/>
<br/>

<security:authorize access="hasRole('AUDITOR')">
	<jstl:if test="${auditRecord.draftMode && auditRecord.auditor.id == principalID}"> 
	
		<a href="auditRecord/auditor/edit.do?auditRecordId=${auditRecord.id}"><spring:message code="auditRecord.edit"/></a> 
		<br/>
	</jstl:if>
	
	<jstl:if test="${auditRecord.draftMode && auditRecord.auditor.id == principalID}"> 
	
		<a href="auditRecord/auditor/setFinalMode.do?auditRecordId=${auditRecord.id}"><spring:message code="auditRecord.setFinalMode"/></a> 
		<br/>
	</jstl:if>
</security:authorize>



