<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
$(document).ready(function() {
			
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

			<div id="allvalues" style="display: block;">
				 <table class="table table-bordered"   >
                    <thead>
                      <tr style="color: green;;font: bolder;">
                      <th>
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
						<td style="max-width:150px">
							<input type="hidden" id="img_id" value="${row.in_current}"/>
							<a href="#">
								<img id="run" name="run" src="${pageContext.request.contextPath}/assets/img/${row.in_current}.png" 
					      				alt="Image" height="160" width="160"class="rounded"  >
							</a>						
						<!-- <button type="button" class="btn btn-success btn-fw">Run</button> -->
						</td>
						<td style="max-width:150px">
						<a href="#" ><img name="delete" id="delete" src="${pageContext.request.contextPath}/assets/img/delete.png"  alt="Image" height="160" width="160"class="rounded">
						</a>
						<!-- <button type="button" class="btn btn-danger btn-fw">Delete</button> -->
						</td>
						<!-- <td style="max-width:150px">
							<a href="#">
								<img id="suspend" name="suspend" src="${pageContext.request.contextPath}/assets/img/${row.is_suspended}.png" 
					      				alt="Image" height="160" width="160"class="rounded"  >
							</a>
						</td> -->
						</tr>
	                </c:forEach>
                      
                     </tbody>
                  </table>
                 </div>