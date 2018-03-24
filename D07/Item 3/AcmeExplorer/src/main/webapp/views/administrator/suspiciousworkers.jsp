<%--
 * suspiciousworkers.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>



<display:table pagesize="5" class="displaytag" keepStatus="true" name="actors" requestURI="${requestURI}" id="row">
	<spring:message code="administrator.suspiciousActor" var="suspiciousActor" />
	<display:column title="${suspiciousActor}">
		<a href="actor/display.do?actorId=${row.id}">${row.name}</a>
	</display:column>
	
		<spring:message code="administrator.rol" var="rol" />
	<display:column title="${rol}">
		<jstl:out value="${row.userAccount.authorities}"/>
	</display:column>

	<spring:message code="administrator.ban" var="ban" />
	<display:column>
		<jstl:if test="${row.userAccount.accountNonLocked ==true}">
			<a href="actor/administrator/ban.do?actorId=${row.id}">${ban}</a>
		</jstl:if>
	</display:column>
	
	<spring:message code="administrator.unban" var="unban" />
	<display:column>
		<jstl:if test="${row.userAccount.accountNonLocked ==false}">
			<a href="actor/administrator/unban.do?actorId=${row.id}">${unban}</a>
		</jstl:if>
	</display:column>
</display:table>