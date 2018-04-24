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

<display:table pagesize="5" class="displaytag" keepStatus="false"
name="sponsorships" requestURI="${requestURI}" id="row">

<!-- Attributes -->

<spring:message code="sponsorship.banner" var="bannerHeader"/>
<display:column property="banner" title="${bannerHeader}" sortable="false"/>

<spring:message code="sponsorship.infoPage" var="infoPageHeader"/>
<display:column property="infoPage" title="${infoPageHeader}" sortable="false"/>

<spring:message code="sponsorship.creditCard" var="creditCardHeader"/>
<display:column property="cc" title="${row.cc.holderName}" sortable="false"/>

<spring:message code="sponsorship.trip" var="tripHeader"/>
<display:column title="${tripHeader}">
		<a href="trip/display.do?tripId=${row.trip.id}">${row.trip.title}</a>
	</display:column>

<display:column>
<jstl:if test="${row.cc==null}">
	<a href="creditcard/sponsor/create.do?sponsorshipId=${row.id}">
			<spring:message	code="sponsorship.addCreditCard" />
			</a>
	</jstl:if>
</display:column>
</display:table>