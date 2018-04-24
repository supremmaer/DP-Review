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

<!--  Listing grid -->
<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="professionalRecord" requestURI="${requestURI}" id="row">

	<!-- Attributes -->
	<spring:message code="professionalRecord.nameCompany" var="nameCompanyHeader" />
	<display:column property="nameCompany" title="${nameCompanyHeader}"
		sortable="true" />
		
		<spring:message code="professionalRecord.datePattern" var="datePattern"/>
		<spring:message code="professionalRecord.startDate" var="startDateHeader" />
	<display:column property="startDate" title="${startDateHeader}"
		sortable="true" format="${datePattern}" />
	
	<spring:message code="professionalRecord.endDate" var="endDateHeader" />
	<display:column property="endDate" title="${endDateHeader}"
		sortable="true" format="${datePattern}" />
		
		<spring:message code="professionalRecord.role" var="roleHeader" />
	<display:column property="role" title="${roleHeader}"
		sortable="true" />
		
		<spring:message code="professionalRecord.attachment" var="attachmentHeader" />
	<display:column  title="${attachmentHeader}"
		sortable="true" >
		<a href="<jstl:out value="${row.attachment}" />">${row.attachment}</a>
		</display:column>
		
		<spring:message code="professionalRecord.comment" var="commentHeader" />
	<display:column property="comment" title="${commentHeader}"
		sortable="true" />
	<!-- Action links -->
	
	<security:authorize access="hasRole('RANGER')">
		<jstl:if test="${ rangerId==principalId}">
			<display:column>
				<a
					href="professionalRecord/ranger/edit.do?professionalRecordId=${row.id }">
					<spring:message code="professionalRecord.edit" />
				</a>
			</display:column>
		</jstl:if>


	</security:authorize>

	</display:table>
	
	<security:authorize access="hasRole('RANGER')">
	<jstl:if test="${ rangerId==principalId}">

		<a href="professionalRecord/ranger/create.do"> <spring:message code="professionalRecord.create" /> </a>

	</jstl:if>


</security:authorize>