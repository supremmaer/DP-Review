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

<display:table pagesize="5" class="displaytag" keepStatus="true" name="trips" requestURI="${requestURI}" id="row"> <!-- TODO: Apply Spring JPA pagination -->

<!-- Attributes -->

<display:column property="ticker" title="Ticker" sortable="false"/>


<!-- TODO check this column-->
<spring:message code="trip.titleHeader" var="title"/>
<display:column title="${title}">
	<a href="trip/display.do?tripId=${row.id}">${row.title}</a>
</display:column>
 
<spring:message code="trip.datePattern" var="datePattern"/>

<spring:message code="trip.publicationDateHeader" var="publicationDateHeader"/>
<display:column property="publicationDate" title="${publicationDateHeader}" sortable="false" format="${datePattern}"/>

<spring:message code="trip.startDateHeader" var="startDate"/>
<display:column property="startDate" title="${startDate}" sortable="false" format="${datePattern}"/>

<spring:message code="trip.endDateHeader" var="endDate"/>
<display:column property="endDate" title="${endDate}" sortable="false" format="${datePattern}"/>

<spring:message code="trip.priceHeader" var="price"/>
<spring:message code="trip.language" var="language"/>

<display:column title="${price}">
	<jstl:if test="${language=='English'}">&euro;</jstl:if>
	<fmt:formatNumber value="${row.price *(VATTax+1)}" pattern="0000.00"/>
	<jstl:if test="${language=='Spanish'}">&euro;</jstl:if>
</display:column>

<!-- TODO correct the if -->
<security:authorize access="hasRole('MANAGER')">
	<jsp:useBean id="date" class="java.util.Date"/>
	<jstl:if test="${managerId!=null }">
	<display:column>
		<jstl:if test="${date < row.publicationDate && row.manager.id == managerId}">
			<a href="trip/manager/edit.do?tripId=${row.id}">
				<spring:message	code="trip.edit" />
			</a>
		</jstl:if>
	</display:column>
	</jstl:if>
	
</security:authorize>

</display:table>

<security:authorize access="hasRole('MANAGER')">
	<a href="trip/manager/create.do"> 
		<spring:message code="trip.create" />
	</a>
	<br/>
	<a href="trip/manager/list.do"> 
		<spring:message code="trip.mytrips" />
	</a>
</security:authorize>
