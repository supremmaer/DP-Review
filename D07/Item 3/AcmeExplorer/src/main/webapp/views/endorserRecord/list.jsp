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
<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="endorserRecord" requestURI="${requestURI}" id="row">

	<!-- Attributes -->
	<spring:message code="endorserRecord.fullNameEndorser" var="fullNameEndorserHeader" />
	<display:column property="fullNameEndorser" title="${fullNameEndorserHeader}"
		sortable="true" />
		
		<spring:message code="endorserRecord.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}"
		sortable="true" />
		
		<spring:message code="endorserRecord.phone" var="phoneHeader" />
	<display:column property="phone" title="${phoneHeader}"
		sortable="true" />
		
		<spring:message code="endorserRecord.linkedInProfile" var="linkedInProfileHeader" />
	<display:column  title="${linkedInProfileHeader}"
		sortable="true" >
		<a href="<jstl:out value="${row.linkedInProfile}" />">${row.linkedInProfile}</a>
		</display:column>
	
	<spring:message code="endorserRecord.comment" var="commentHeader" />
	<display:column property="comment" title="${commentHeader}"
		sortable="true" />
	<!-- Action links -->
	
	<security:authorize access="hasRole('RANGER')">
		<jstl:if test="${ rangerId==principalId}">
			<display:column>
				<a
					href="endorserRecord/ranger/edit.do?endorserRecordId=${row.id }">
					<spring:message code="endorserRecord.edit" />
				</a>
			</display:column>
		</jstl:if>


	</security:authorize>

	</display:table>
	
	<security:authorize access="hasRole('RANGER')">
	<jstl:if test="${ rangerId==principalId}">

		<a href="endorserRecord/ranger/create.do"> <spring:message code="endorserRecord.create" /> </a>

	</jstl:if>


</security:authorize>