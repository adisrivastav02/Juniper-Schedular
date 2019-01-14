<jsp:include page="../cdg_header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
$(document).ready(function() {
	//fetch the job filer values using feedid-Start
	$("#feedIdFilter").change(function() {
		var feed_id = $(this).val();
		//alert(feed_id);
		$.post('${pageContext.request.contextPath}/schedule/feedIdFilter', {		
			feed_id : feed_id	
		}, function(data) {
			//document.getElementById('chartId').style.display= "none";
			$('#jobIdFilter').html(data);
				
			//start job filter
				$("#lstJobId").change(function() {
					var job_id = $(this).val();
				//	alert("value of job id"+job_id);
					$.post('${pageContext.request.contextPath}/schedule/jobIdFilter', {
						job_id : job_id	,
						feed_id :feed_id
					}, function(result) {
						
						$('#testValue').html(result);
						
						document.getElementById('testValue').style.display= "block";
					//	var a = eval('('+'${x}'+')'); 

						//var x = '${x}';
					//	alert(a);
						
						
						
						
					})
					
					
					
					
				});
			
				//end job filter
			
			
			});
		
		});
	
		//fetch the job filer values using feedid-End
	
	
		
	});
	

</script>
<div class="main-panel">
	<div class="content-wrapper">
		<div class="row">
			<div class="col-12 grid-margin stretch-card">
				<div class="card">
					<div class="card-body">
						<h4 class="card-title">Run Statics</h4>	
						<form class="forms-sample" id="runform" name="runform"
							method="post" action="${pageContext.request.contextPath}/schedule/selectFeedId">
							<fieldset class="fs">
								<div class="form-group">
										<label>Select Feed</label> <select class="form-control"
											id="feedIdFilter" name="feedIdFilter">
											<option value="" selected disabled>Feed Data...</option>
											<c:forEach items="${feed_id}" var="feed_id">
												<option value="${feed_id}">${feed_id}</option>
											</c:forEach>
										</select>
								</div>
								<div class="form-group" id="jobIdFilter">
								</div>
								
							</fieldset>							
						</form>		
					</div>
				</div>
			</div>
		</div>
		<div class="row" style="display: none;" id="testValue" >
			
		</div>
		<div class="row" id="chartId" style="display: none;">
			
		</div>
<jsp:include page="../cdg_footer.jsp" />