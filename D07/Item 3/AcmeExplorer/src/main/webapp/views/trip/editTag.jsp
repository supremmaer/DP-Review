<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form action="trip/manager/editTag.do" method="post">
	<input type="hidden" name="tripId" value="${tripId}">
	<select name="key">
		<jstl:forEach var="tag" items="${systags}">
			<option value="${tag}">${tag}</option>
		</jstl:forEach>
	</select>
	<textarea name="value"></textarea>
	<input type="submit" name="save" value="<spring:message code="trip.save" />" />
	
	<spring:message code="trip.cancel" var="cancel"/>
	<input type="button" name="cancel" value="${cancel}" onclick="javascript:relativeRedir('trip/display.do?tripId=${tripId}');" />
</form>