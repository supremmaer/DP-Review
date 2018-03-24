<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>  
<!-- TODO: Limit request rate -->
    <script>
    function requestList(keyword){
    
        if(keyword=="")
        {
            document.getElementById("result").innerHTML="";
            return;
        }

        if(window.XMLHttpRequest)
        {
            request=new XMLHttpRequest();
        }   else
        {
            request=new ActiveXObject("Microsoft.XMLHTTP");
        }

        request.onreadystatechange=function ()
        {
            if((request.readyState==4) && (request.status==200))
            {
                document.getElementById("result").innerHTML=request.responseText;
            }
        };

        request.open("get","trip/listKeyword.do?keyword="+keyword,"true");
        request.send();
     }  
</script>
<label for="" ><spring:message code="search.keyword" /> </label>
<input type="text" id="searchBox" onkeyup="requestList(this.value)"> 
<div id="result"></div>
