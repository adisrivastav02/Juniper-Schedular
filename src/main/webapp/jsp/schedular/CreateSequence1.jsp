<%@page import="java.util.ArrayList"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:forEach items="${daglistdto}" var="daglistdto">
<button id="${daglistdto.JOB_NAME}" name="${daglistdto.JOB_NAME}" class="btn btn-rounded btn-gradient-info mr-2" draggable="true" ondragstart="drag(event)" style="width:90%;margin:5px;padding:10px 0px;">${daglistdto.JOB_NAME}</button>
</c:forEach>
