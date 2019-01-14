<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="col-12 grid-margin stretch-card">
				<div class="card">
					<div class="card-body">
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
                          Job Name
                        </th>
                        <th>
                         Batch Date
                        </th>
                    	<th>
                         Start Time
                        </th>
                       <th >
                       End Time
                        </th>
                         <th >
                      Duration
                        </th>
                      </tr>
                    </thead>
                    <tbody>
                   <c:forEach var="row" items="${allLoadJobs}">
	                    <tr>
	                    <td><c:out value="${row.batch_id}" /></td>
	                    <td><c:out value="${row.job_id}" /></td>
						<td><c:out value="${row.job_name}" /></td>
						<td><c:out value="${row.batch_date}" /></td>
						<td>
                        <c:out value="${row.start_time}" />
                        </td>
						<td>
						 <c:out value="${row.end_time}" />
						</td>
						<td>
						 <c:out value="${row.duration}" />
						</td>	
						</tr>
	                </c:forEach>
                      
                     </tbody>
                  </table>
                  		
               		 </div>
				</div>
			</div>
					
					
<!-- <input type="hidden" name="x" id="x">
<input type="hidden" name="y" id="y">
 -->

