<jsp:include page="../cdg_header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
$(document).ready(function() {
			$("#status").change(function() {
				var status = $(this).val();
				var feedid = document.getElementById('arrfeedId').value
				$.post('${pageContext.request.contextPath}/scheduler/statusfilter', {
					status : status,
					feedid : feedid
				}, function(data) {
					$('#allvalues').html(data)
				});
		
	});
			
			
			$("#arrfeedId").change(function() {
				var status = document.getElementById('status').value
				var feedid = $(this).val();
				$.post('${pageContext.request.contextPath}/scheduler/feedfilter', {
					feedid : feedid,
					status : status
				}, function(data) {
					$('#allvalues').html(data)
				});
			});
			
			$("#run ").click(function(){
				
				var $row = $(this).closest("tr");
				var $feedId = $row.find('td:eq( 0 )').html();
				var $jobId = $row.find('td:eq( 1 )').html();
				var $batchDate = $row.find('td:eq(2)').html();
				var $val = $row.find('td:eq( 5 )').html();
				if($val.includes("RUN-Failed")){
					   $.post('${pageContext.request.contextPath}/scheduler/runScheduleJob', {
						   feedId : $feedId,
						   jobId : $jobId,
						   batchDate : $batchDate
						}, function(data) {
							$('#allvalues').html(data)
							window.location.reload();
							alert("Job Started");		
							});
						}
				});	
			
			$("#kill ").click(function(){
				var $row = $(this).closest("tr");
				var $feedId = $row.find('td:eq( 0 )').html();
				var $jobId = $row.find('td:eq( 1 )').html();
				var $batchDate = $row.find('td:eq(2)').html();
				var $val = $row.find('td:eq( 6 )').html();
				if($val.includes("KILL-Running")){
					   $.post('${pageContext.request.contextPath}/scheduler/stopScheduleJob', {
						   feedId : $feedId,
						   jobId : $jobId,
						   batchDate : $batchDate
						}, function(data) {
							$('#allvalues').html(data)
							window.location.reload();
							alert("Job Killed");
						});
						
					}
			});
});




	

</script>
<div class="main-panel">
	<div class="content-wrapper">
		<div class="row">
			<div class="col-12 grid-margin stretch-card">
				<div class="card">
					<div class="card-body">
					<h4 class="card-title">Current Jobs</h4>
							<div class="row">
						   <div class="form-group col-md-6 ">
						   		<label>Feed Id</label> 
											<select class="form-control" name="arrfeedId" id="arrfeedId">
												<option value="ALL" selected="selected">All</option>
											    <c:forEach items="${arrfeedId}" var="arrfeedId">
													<option value="${arrfeedId}">${arrfeedId}</option>
												</c:forEach>									  	
											</select>
				          </div>

				          <div class="form-group col-md-6">
				          <label>Status</label> 
											<select class="form-control" name="status" id="status">
												<option value="ALL" selected="selected">All</option>
											    <option value="C">Completed</option>
											    <option value="T">To Run</option>
											    <option value="W">Waiting</option>
											    <option value="F">Failed</option>
											    <option value="R">Running</option>
											    
									  		</select>
				          </div>
				          </div>	
        
					</div>
				</div>
			</div>
		</div>
	
         
		<div class="row">
			<div class="col-12 grid-margin stretch-card">
				<div class="card">
					<div class="card-body">
					
			
					
						<h4 class="card-title">Job Details</h4>
						<%
							if (request.getAttribute("successString") != null) {
						%>
						<p class="text-success h4">${successString}</p>
						<%
							}
						%>
						<%
							if (request.getAttribute("errorString") != null) {
						%>
						<p class="text-danger h4">${errorString}</p>
						<%
							}
						%>
					<form class="forms-sample" id="extractionExtractData"
							name="extractionExtractData" method="POST"
							action="${pageContext.request.contextPath}/extract/extractionExtractData1"
							enctype="application/json"> 

			 <div id="allvalues" style="display: block;"> 
				 <table   class="table table-bordered"   >
                    <thead>
                      <tr style="color: green;;font: bolder;">
                      <th>
                          Feed Id
                        </th>
                        <th>
                          Job Id
                        </th>
                        <th>
                          Batch Date
                        </th>
                        <th>
                         Schedule Info
                        </th>
                    	<th>
                         Status
                        </th>
                        <th >
                         Re-Run
                        </th>
                       <th >
                         Kill
                        </th>
                      </tr>
                    </thead>
                    <tbody>
                   <c:forEach var="row" items="${allLoadJobs}">
	                    <tr>
	                    <td style="max-width:150px"><c:out value="${row.batch_id}" /></td>
	                    <td style="max-width:150px"><c:out value="${row.job_id}" /></td>
						<td style="max-width:150px"><c:out value="${row.batch_date}" /></td>
						<td style="max-width:150px"><c:out value="${row.job_schedule_time}" /></td>
						<td style="max-width:150px">
                        <c:out value="${row.status}" />
                        </td>
						<td style="max-width:150px">
							<a href="#">
								<img id="run" name="run" src="${pageContext.request.contextPath}/assets/img/RUN-${row.status}.png" 
					      				alt="Image" height="160" width="160"class="rounded"  >
							</a>						</td>
						<td style="max-width:150px">
						<a href="#" ><img name = "kill" id="kill" src="${pageContext.request.contextPath}/assets/img/KILL-${row.status}.png"  alt="Image" height="160" width="160"class="rounded">
						</a>
						</td>		
						</tr>
	                </c:forEach>
                      
                     </tbody>
                  </table>
                </div> 
                 
					</form>
					</div>
				</div>
			</div>
		</div>
		<jsp:include page="../cdg_footer.jsp" />