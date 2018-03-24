<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<div>
	<a href="/AcmeExplorer"><img class="banner" src="${pageBanner}" alt="Sample Co., Inc." /></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv"> <spring:message
						code="master.page.profile" /> (<security:authentication
						property="principal.username" />)
			</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/display.do?actorId=0"><spring:message
								code="master.page.profile" /></a></li>
					<li><a href="actor/edit.do"><spring:message
								code="master.page.profile.edit" /></a></li>
					<li><a href="actor/message/list.do"><spring:message
								code="master.page.profile.messages" /></a></li>
					<li><a href="j_spring_security_logout"><spring:message
								code="master.page.logout" /> </a></li>
				</ul></li>
		</security:authorize>
		<security:authorize access="hasRole('ADMINISTRATOR')">
			<li><a class="fNiv"><spring:message
						code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="administrator/suspiciousworkers.do"><spring:message
								code="master.page.administrator.suspiciousworkers" /></a></li>
					<li><a href="administrator/dashboard.do"><spring:message
								code="master.page.administrator.dashboard" /></a></li>
					<li><a href="administrator/notification.do"><spring:message
								code="master.page.administrator.notification" /></a></li>
					<li><a href="legalText/administrator/list.do"><spring:message
								code="master.page.administrator.legalText" /></a></li>
					<li><a href="systemConfiguration/administrator/display.do"><spring:message
								code="master.page.administrator.systemConfiguration" /></a></li>
				</ul></li>
			<li><a class="fNiv"><spring:message
						code="master.page.administrator.createUser" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/create.do?actorType=RANGER"><spring:message
								code="master.page.administrator.createRanger" /></a></li>
					<li><a href="actor/create.do?actorType=MANAGER"><spring:message
								code="master.page.administrator.createManager" /></a></li>
					<li><a href="actor/create.do?actorType=ADMINISTRATOR"><spring:message
								code="master.page.administrator.createAdministrator" /></a></li>
					<li><a href="actor/create.do?actorType=AUDITOR"><spring:message
								code="master.page.administrator.createAuditor" /></a></li>
					<li><a href="actor/create.do?actorType=SPONSOR"><spring:message
								code="master.page.administrator.createSponsor" /></a></li>
					<li><a href="actor/create.do?actorType=EXPLORER"><spring:message
								code="master.page.administrator.createExplorer" /></a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="hasRole('MANAGER')">
			<li><a class="fNiv"><spring:message
						code="master.page.manager" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="application/manager/list.do"><spring:message
								code="master.page.manager.application" /></a></li>
					<li><a href="note/manager/list.do"><spring:message
								code="master.page.manager.note" /></a></li>
					<li><a href="survivalClass/manager/list.do"><spring:message
								code="master.page.manager.survivalClass" /></a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="hasRole('SPONSOR')">
			<li><a class="fNiv"><spring:message
						code="master.page.sponsor" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="sponsorship/sponsor/list.do"><spring:message
								code="master.page.sponsor.sponsorships" /></a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="hasRole('EXPLORER')">
			<li><a class="fNiv"><spring:message
						code="master.page.explorer" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="finder/explorer/edit.do"><spring:message
								code="master.page.explorer.finder" /></a></li>
					<li><a href="application/explorer/list.do"><spring:message
								code="master.page.explorer.applications" /></a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="hasRole('AUDITOR')">
			<li><a class="fNiv"><spring:message
						code="master.page.auditor" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="auditRecord/auditor/list.do"><spring:message
								code="master.page.auditor.auditRecords" /></a></li>
					<li><a href="note/auditor/list.do"><spring:message
								code="master.page.auditor.notes" /></a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="hasRole('CUSTOMER')">
			<li><a class="fNiv"><spring:message
						code="master.page.customer" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="customer/action-1.do"><spring:message
								code="master.page.customer.action.1" /></a></li>
					<li><a href="customer/action-2.do"><spring:message
								code="master.page.customer.action.2" /></a></li>
				</ul></li>
		</security:authorize>


		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message
						code="master.page.login" /></a></li>
			<li><a class="fNiv"><spring:message
						code="master.page.anonymous.register" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/create.do?actorType=RANGER"><spring:message
								code="master.page.anonymous.action.1" /></a></li>
					<li><a href="actor/create.do?actorType=EXPLORER"><spring:message
								code="master.page.anonymous.action.2" /></a></li>
				</ul></li>



		</security:authorize>



		<li><a class="fNiv"><spring:message code="master.page.trips" /></a>
			<ul>
				<li class="arrow"></li>
				<li><a href="trip/list.do"><spring:message
							code="master.page.trips.list" /></a></li>
				<li><a href="trip/search.do"><spring:message
							code="master.page.trips.search" /></a></li>
				<li><a href="category/list.do"><spring:message
							code="master.page.trips.categories" /></a></li>
			</ul></li>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

