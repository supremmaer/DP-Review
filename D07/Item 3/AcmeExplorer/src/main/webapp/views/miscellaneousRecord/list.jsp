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
	name="miscellaneousRecord" requestURI="${requestURI}" id="row">

	<!-- Attributes -->
	<spring:message code="miscellaneousRecord.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}"
		sortable="true" />
		
		<spring:message code="miscellaneousRecord.linkAttachment" var="linkAttachmentHeader" />
	<display:column title="${linkAttachmentHeader}"
		sortable="true" >
		<a href="<jstl:out value="${row.linkAttachment}" />">${row.linkAttachment}</a>
		</display:column>
		
		<spring:message code="miscellaneousRecord.comment" var="commentHeader" />
	<display:column property="comment" title="${commentHeader}"
		sortable="true" />
		
		<!-- Action links -->

	<security:authorize access="hasRole('RANGER')">
		<jstl:if test="${ rangerId==principalId}">
			<display:column>
				<a
					href="miscellaneousRecord/ranger/edit.do?miscellaneousRecordId=${row.id }">
					<spring:message code="miscellaneousRecord.edit" />
				</a>
			</display:column>
		</jstl:if>


	</security:authorize>

</display:table>

<security:authorize access="hasRole('RANGER')">
	<jstl:if test="${ rangerId==principalId}">

		<a href="miscellaneousRecord/ranger/create.do"> <spring:message code="miscellaneousRecord.create" /> </a>

	</jstl:if>


</security:authorize>


