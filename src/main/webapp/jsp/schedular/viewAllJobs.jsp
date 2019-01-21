<jsp:include page="../cdg_header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
$(document).ready(function() {
			$("#frequency").change(function() {
				var frequency = $(this).val();
				//alert(frequency);
				var batchId = document.getElementById('arrfeedId').value
				$.post('${pageContext.request.contextPath}/scheduler/frequency', {
					frequency : frequency,
					batchId : batchId
				}, function(data) {
					$('#allvalues').html(data)
				});
		
	});
			
			
			$("#arrfeedId").change(function() {
				var frequency = document.getElementById('frequency').value
				var batchId = $(this).val();
				$.post('${pageContext.request.contextPath}/scheduler/batchid', {
					batchId : batchId,
					frequency : frequency
				}, function(data) {
					$('#allvalues').html(data)
				});
			});
			
			
			$("#run ").click(function(){
			var $row = $(this).closest("tr");
			var $feedId = $row.find('td:eq( 0 )').html();
			//var $jobId = $row.find('td:eq( 1 )').html();
			var $val = $row.find('td:eq( 2 )').html();
			if($val.includes("CURR-N")){
				   $.post('${pageContext.request.contextPath}/scheduler/runMasterJob', {
					   feedId : $feedId
					}, function(data) {
					window.location.reload();
					alert("Job ordered for today");			
					});
				}
			});
			
			$("#delete ").click(function(){
				var $row = $(this).closest("tr");
				var $feedId = $row.find('td:eq( 0 )').html();
				//var $jobId = $row.find('td:eq( 1 )').html();
					   $.post('${pageContext.request.contextPath}/scheduler/deleteMasterJob', {
						   feedId : $feedId
						}, function(data) {
							window.location.reload();
							alert("Job deleted");		
						});
					});		
			
			$("#suspend").click(function(){
				var $row = $(this).closest("tr");
				var $feedId = $row.find('td:eq( 0 )').html();
				//var $jobId = $row.find('td:eq( 1 )').html();
				var $val = $row.find('td:eq( 6 )').html();
				if($val.includes("SUS-Y")){
					   $.post('${pageContext.request.contextPath}/scheduler/unSuspendMasterJob', {
						   feedId : $feedId
						}, function(data) {
						window.location.reload();
						alert("Job Unsuspended");
						});
				} else {
					   $.post('${pageContext.request.contextPath}/scheduler/suspendMasterJob', {
						   feedId : $feedId
						}, function(data) {
						window.location.reload();
						alert("Job suspended");
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
					<h4 class="card-title">All Jobs</h4>
					<%
               if(request.getAttribute("successString") != null) {
               %>
            <div class="alert alert-success" id="success-alert">
               <button type="button" class="close" data-dismiss="alert">x</button>
               ${successString}
            </div>
            <%
               }
               %>
            <%
               if(request.getAttribute("errorString") != null) {
               %>
            <div class="alert alert-danger" id="error-alert">
               <button type="button" class="close" data-dismiss="alert">x</button>
               ${errorString}
            </div>
            <%
               }
               %>
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
				          <label>Frequency</label> 
											<select class="form-control" name="frequency" id="frequency">
												<option value="ALL" selected="selected">All</option>
											    <option value="DAILY">Daily</option>
											    <option value="WEEKLY">Weekly</option>
											    <option value="MONTHLY">Monthly</option>
											    <option value="YEARLY">Yearly</option>
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
				 <table class="table table-bordered" id="feedId1"  >
                    <thead>
                      <tr style="color: green;;font: bolder;">
                      <th style="">
                          Feed Id
                        </th>
                        <th>
                         Schedule Info
                        </th>
                    	<th>
                          Order
                        </th>
                        <th>
                         Delete
                        </th>
                      </tr>
                    </thead>
                    <tbody>
                   <c:forEach var="row" items="${allLoadJobs}">
	                    <tr>
	                    <td style="max-width:150px"><c:out value="${row.batch_id}" /></td>
						<td style="max-width:150px"><c:out value="${row.consolidatedSchedule}" /></td>
						<td style="max-width:50px">
							<input type="hidden" id="img_id" value="${row.in_current}"/>
							<a href="#">
								<img id="run" name="run" src="${pageContext.request.contextPath}/assets/img/${row.in_current}.png" 
					      				alt="Image" height="160" width="160"class="rounded"  >
							</a>					
						<!-- <button type="button" class="btn btn-success btn-fw">Run</button> -->
						</td>
						<td style="max-width:50px">
						<a href="#" ><img name="delete" id="delete" src="${pageContext.request.contextPath}/assets/img/delete.png"  alt="Image" height="160" width="160"class="rounded">
						</a>
						<!-- <button type="button" class="btn btn-danger btn-fw">Delete</button> -->
						</td>
						<!--  <td style="max-width:50px">
							<input type="hidden" id="img_id" value="${row.is_suspended}"/>
							<a href="#">
								<img id="suspend" name="suspend" src="${pageContext.request.contextPath}/assets/img/${row.is_suspended}.png" 
					      				alt="Image" height="160" width="160"class="rounded"  >
							</a>
						</td>	-->
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