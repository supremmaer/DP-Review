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

<display:table pagesize="5" class="displaytag" keepStatus="true" name="stages" requestURI="${requestURI}" id="row">

<!-- Attributes -->

<spring:message code="stage.titleHeader" var="title"/>
<display:column property="title" title="${title}" sortable="false"/>

<spring:message code="stage.descriptionHeader" var="description"/>
<display:column property="description" title="${description}" sortable="false"/>

<spring:message code="stage.pricePattern" var="pricePattern"/>
<spring:message code="stage.priceHeader" var="price"/>
<display:column property="price" title="${price}" sortable="false" format="${pricePattern}"/>

<security:authorize access="hasRole('MANAGER')">
	
	<jstl:if test="${isEditable}">
	<display:column>
		<a href="stage/manager/edit.do?stageId=${row.id}&tripId=${tripId}">
			<spring:message code="stage.editStage"/>
		</a>
	</display:column>
	</jstl:if>
</security:authorize>

</display:table>

<security:authorize access="hasRole('MANAGER')">
	<jstl:if test="${isEditable}">
		<a href="stage/manager/create.do?tripId=${tripId}">
			<spring:message code="stage.addStage"/>
		</a>
		<br/>
	</jstl:if>
</security:authorize>
