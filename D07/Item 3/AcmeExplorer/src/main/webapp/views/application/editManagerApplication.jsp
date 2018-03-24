
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

<security:authorize access="hasRole('MANAGER')">
<form:form action="application/manager/edit.do"
	modelAttribute="application">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />
	<form:hidden path="explorer" />
	<form:hidden path="trip" />
	<form:hidden path="creditCard" />
	
	<security:authorize access="hasRole('MANAGER')">
	<form:hidden path="comment"/>
	
	<form:label path="rejectingReason">
		<spring:message code="application.rejectingReason"/>
	</form:label>
	<form:textarea path="rejectingReason"/>
	<form:errors cssClass="error" path="rejectingReason"/>
	<br/>
	
	<jstl:if test="${application.status=='PENDING'}">
	<form:label path="status">
		<spring:message code="application.status"/>:
	</form:label>
	<select name="status">
		<option value="DUE">
			<spring:message code="application.status.due"/>
		</option>
		<option value="REJECTED">
			<spring:message code="application.status.rejected"/>
		</option>
	</select>
	<br />
	</jstl:if>
	</security:authorize>
	
	<input type="submit" name="save"
		value="<spring:message code="application.save" />" /> 
			
	<spring:message code="application.cancel" var="cancel"/>
	<input type="button" name="cancel" value="${cancel}" onclick="javascript:relativeRedir('application/manager/list.do');" />
	<br />

</form:form>
</security:authorize>