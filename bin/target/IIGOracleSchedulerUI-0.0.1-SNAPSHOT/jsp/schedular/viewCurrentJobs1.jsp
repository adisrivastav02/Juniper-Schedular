<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
$(document).ready(function() {
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
					alert("Job killed");		
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
							</a>						
						</td>
						<td style="max-width:150px">
						<a href="#" ><img name = "kill" id="kill" src="${pageContext.request.contextPath}/assets/img/KILL-${row.status}.png"  alt="Image" height="160" width="160"class="rounded">
						</a>
						</td>	
						</tr>
	                </c:forEach>
                      
                     </tbody>
                  </table>
                 </div>