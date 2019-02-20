<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
$(document).ready(function() {
	$("#job_id1").change(function() {	
			var job_id=document.getElementById('job_id1').value;
			var batch=document.getElementById('l2').value;
			var project=document.getElementById('project').value;
			$.post('${pageContext.request.contextPath}/scheduler/EditJob', {
				batch : batch,
				project : project,
				job_id :  job_id
			}, function(data) {
				$('#loadjob').html(data)
			});
	});
});
		
</script>
<label>Select Jobs *</label> 
<select name="job_id1" id="job_id1" class="form-control">
<option value="" selected disabled>Select Job ...</option>
<c:forEach items="${job_id1}" var="job_id1">
	<option value="${job_id1}">${job_id1}</option>
</c:forEach>
</select>
<div id="loadjob"></div>
