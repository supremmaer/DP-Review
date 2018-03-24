<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table pagesize="5" class="displaytag" keepStatus="true" name="socialIdentities" requestURI="socialIdentity/actor/list.do" id="row">
	

	
	<!-- Attributes -->
	<spring:message code="socialIdentity.nick" var="nickHeader" />
	<display:column property="nick" title="${nickHeader}" sortable="true" />

	<spring:message code="socialIdentity.socialNetwork" var="socialNetworkHeader" />
	<display:column property="socialNetwork" title="${socialNetworkHeader}" sortable="false" />

	<spring:message code="socialIdentity.profile" var="profileHeader" />
	<display:column title="${profileHeader}" sortable="false" >
		<a href="${row.profile}"><spring:message code="socialIdentity.profile"/></a>
	</display:column>
	
	<spring:message code="socialIdentity.photo" var="photoHeader" />
	<display:column title="${photoHeader}" sortable="false" >
		<jstl:choose>
		<jstl:when test="${row.photo.length()>0}">
			<a href="${row.photo}"><spring:message code="socialIdentity.photo"/></a>
		</jstl:when>
		<jstl:otherwise>
			<spring:message code="socialIdentity.nophoto"/>
		</jstl:otherwise>
		</jstl:choose>
	
	</display:column>
	
	<display:column>
		<a href="socialIdentity/actor/edit.do?socialIdentityId=${row.id}">
			<spring:message	code="socialIdentity.edit" />
		</a>
	</display:column>

</display:table>

<div>
	<a href="socialIdentity/actor/create.do"> 
	<spring:message code="socialIdentity.create" />
	</a>
</div>