<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<label>Select Job Id</label>
<select class="form-control" id="lstJobId" name="lstJobId">
	<option value="" selected disabled>Job Id...</option>
	<c:forEach items="${arrJobId}" var="arrJobId">
		<option value="${arrJobId}">${arrJobId}</option>
	</c:forEach>
</select>
