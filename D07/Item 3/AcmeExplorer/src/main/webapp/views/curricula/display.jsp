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

<b><spring:message code="curricula.name" /></b>
<jstl:out value="${curriculum.personalRecord.fullName}" />
<br />

<jstl:url var="img" value="${curriculum.personalRecord.photo }"></jstl:url>
<img src="${img }" >
<br />

<b><spring:message code="curricula.email" /></b>
<jstl:out value="${curriculum.personalRecord.email}"  />
<br />

<b><spring:message code="curricula.phone" /></b>
<jstl:out value="${curriculum.personalRecord.phone}" />
<br />

<b><spring:message code="curricula.url" /></b>
<a href="<jstl:out value="${curriculum.personalRecord.url}" />">attachment</a>
<br />

<a href="educationRecord/list.do?rangerId=${rangerId }"><spring:message code="curricula.educationRecord" /></a>
<a href="professionalRecord/list.do?rangerId=${rangerId }"><spring:message code="curricula.professionalRecord" /></a>
<a href="endorserRecord/list.do?rangerId=${rangerId }"><spring:message code="curricula.endorserRecord" /></a>
<a href="miscellaneousRecord/list.do?rangerId=${rangerId }"><spring:message code="curricula.miscellaneousRecord" /></a>