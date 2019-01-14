<jsp:include page="../cdg_header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
<!--

//-->

$(document).ready(function() {
	$("#frequency").change(function() {
		var frequency = $(this).val();
		//var src_val = document.getElementById("src_val").value;
		if(frequency=='ALL'){
			document.getElementById("allvalues").style.display= "block";
			document.getElementById("dailyvalues").style.display= "none";
			document.getElementById("weeklyvalues").style.display= "none";
			document.getElementById("monthlyvalues").style.display= "none";
			document.getElementById("yearlyvalues").style.display= "none";
		}
		else if(frequency=='DAILY'){
			document.getElementById("allvalues").style.display= "none";
			document.getElementById("dailyvalues").style.display= "block";
			document.getElementById("weeklyvalues").style.display= "none";
			document.getElementById("monthlyvalues").style.display= "none";
			document.getElementById("yearlyvalues").style.display= "none";
		}
		else if(frequency=='WEEKLY'){
			document.getElementById("allvalues").style.display= "none";
			document.getElementById("dailyvalues").style.display= "none";
			document.getElementById("weeklyvalues").style.display= "block";
			document.getElementById("monthlyvalues").style.display= "none";
			document.getElementById("yearlyvalues").style.display= "none";
		}
		else if(frequency=='MONTHLY'){
			document.getElementById("allvalues").style.display= "none";
			document.getElementById("dailyvalues").style.display= "none";
			document.getElementById("weeklyvalues").style.display= "none";
			document.getElementById("monthlyvalues").style.display= "block";
			document.getElementById("yearlyvalues").style.display= "none";
		}
		else if(frequency=='YEARLY'){
			document.getElementById("allvalues").style.display= "none";
			document.getElementById("dailyvalues").style.display= "none";
			document.getElementById("weeklyvalues").style.display= "none";
			document.getElementById("monthlyvalues").style.display= "none";
			document.getElementById("yearlyvalues").style.display= "block";
		}
		
	});
	
});


$(function() {
	  /* ChartJS
	   * -------
	   * Data and config for chartjs
	   */
	   
	   var totalJobs=${totalCount};
	  var completedJobs=${arrCompletedJobs.size()};
	  var failedjobs=${arrFailedJobs.size()};
	  var notstartedjobs=${arrNotStartedJobs.size()};
	  var pComp=((completedJobs/totalJobs) * 100).toFixed(3);
	  var pfail=((failedjobs/totalJobs) * 100).toFixed(3);
	  var pNs=((notstartedjobs/totalJobs) * 100).toFixed(3);
	  
	 // var hkeys=${hskeys};
	//  var help=${takeVals};
	// alert('hvalues'+help);
	// alert(hkeys);
	  'use strict';
	  	  var doughnutPieData = {
	    datasets: [{
	      data: [pComp, pfail, pNs],
	      backgroundColor: [
	        'rgba(255, 99, 132, 0.5)',
	        'rgba(54, 162, 235, 0.5)',
	        'rgba(255, 206, 86, 0.5)',
	        'rgba(75, 192, 192, 0.5)',
	        'rgba(153, 102, 255, 0.5)',
	        'rgba(255, 159, 64, 0.5)'
	      ],
	      borderColor: [
	        'rgba(255,99,132,1)',
	        'rgba(54, 162, 235, 1)',
	        'rgba(255, 206, 86, 1)',
	        'rgba(75, 192, 192, 1)',
	        'rgba(153, 102, 255, 1)',
	        'rgba(255, 159, 64, 1)'
	      ],
	    }],

	    // These labels appear in the legend and in the tooltips when hovering different arcs
	    labels: [
	      '% Jobs Completed ',
	      '% Jobs Failed',
	      '% Jobs Not Started',
	    ]
	  };	  // Get context with jQuery - using jQuery's .get() method.
	  var doughnutPieOptions = {
			    responsive: true,
			    animation: {
			      animateScale: true,
			      animateRotate: true
			    }
			  };
			 
	  if ($("#doughnutChart").length) {
	    var doughnutChartCanvas = $("#doughnutChart").get(0).getContext("2d");
	    var doughnutChart = new Chart(doughnutChartCanvas, {
	      type: 'doughnut',
	      data: doughnutPieData,
	      options: doughnutPieOptions
	    });
	  }

	  
	  var data = {
			    labels: ["cv"],
			    datasets: [{
			      label: '# of Jobs',
			      data: [1],
			      backgroundColor: [
			        'rgba(255, 99, 132, 0.2)',
			        'rgba(54, 162, 235, 0.2)',
			        'rgba(255, 206, 86, 0.2)',
			        'rgba(75, 192, 192, 0.2)',
			        'rgba(153, 102, 255, 0.2)',
			        'rgba(255, 159, 64, 0.2)'
			      ],
			      borderColor: [
			        'rgba(255,99,132,1)',
			        'rgba(54, 162, 235, 1)',
			        'rgba(255, 206, 86, 1)',
			        'rgba(75, 192, 192, 1)',
			        'rgba(153, 102, 255, 1)',
			        'rgba(255, 159, 64, 1)'
			      ],
			      borderWidth: 1,
			      fill: false
			    }]
			  };
	  
	  
	  var options = {
			    scales: {
			      yAxes: [{
			        ticks: {
			          beginAtZero: true
			        }
			      }]
			    },
			    legend: {
			      display: false
			    },
			    elements: {
			      point: {
			        radius: 0
			      }
			    }

			  };
	  
	// Get context with jQuery - using jQuery's .get() method.
	  if ($("#barChart").length) {
	    var barChartCanvas = $("#barChart").get(0).getContext("2d");
	    // This will get the first returned node in the jQuery collection.
	    var barChart = new Chart(barChartCanvas, {
	      type: 'bar',
	      data: data,
	      options: options
	    });
	  }
	  
	  	});
</script>
<div class="main-panel">
	<div class="content-wrapper">
		<!-- <div class="row">
			<div class="col-12 grid-margin stretch-card">
				<div class="card">
					<div class="card-body">
					<h4 class="card-title">Scheduled Jobs</h4>
							<div class="row">
						   <div class="form-group col-md-6 ">
						   		<label>Batch Id</label> 
											<select class="form-control" name="batchId" id="batchId">
												<option value="ALL" selected="selected">All</option>
											    <option value="ingestion002">ingestion002</option>
											    <option value="ingestion003">ingestion003</option>
											    <option value="ingestion004">ingestion004</option>
											    <option value="ingestion005">ingestion005</option>
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
		</div> -->
							
		
		  <div class="row">
			<div class="col-md-3 stretch-card grid-margin">
              <div class="card bg-gradient-info card-img-holder text-white">
                <div class="card-body">
                  <img src="${pageContext.request.contextPath}/assets/img/circle.svg" class="card-img-absolute" alt="circle-image"/>
                  <h4 class="font-weight-normal mb-3">Total Jobs
                  </h4>
                  <h2 class="mb-5">${totalCount}</h2>
                </div>
              </div>
            </div>
            <div class="col-md-3 stretch-card grid-margin">
              <div class="card bg-gradient-success card-img-holder text-white">
                <div class="card-body">
                  <img src="${pageContext.request.contextPath}/assets/img/circle.svg" class="card-img-absolute" alt="circle-image"/>
                  <h4 class="font-weight-normal mb-3">Jobs Completed
                  </h4>
                  <h2 class="mb-5">${arrCompletedJobs.size()}</h2>
                </div>
              </div>
            </div>
            <div class="col-md-3 stretch-card grid-margin">
              <div class="card bg-gradient-danger card-img-holder text-white">
                <div class="card-body">
                  <img src="${pageContext.request.contextPath}/assets/img/circle.svg" class="card-img-absolute" alt="circle-image"/>
                  <h4 class="font-weight-normal mb-3">Jobs Failed
                  </h4>
                  <h2 class="mb-5">${arrFailedJobs.size()}</h2>
                </div>
              </div>
            </div>
            <div class="col-md-3 stretch-card grid-margin">
              <div class="card bg-gradient-dark card-img-holder text-white">
                <div class="card-body">
                  <img src="${pageContext.request.contextPath}/assets/img/circle.svg" class="card-img-absolute" alt="circle-image"/>
                  <h4 class="font-weight-normal mb-3">Jobs Not Started
                  </h4>
                  <h2 class="mb-5">${arrNotStartedJobs.size()}</h2>
                </div>
              </div>
            </div>
          </div>
          <div class="row">
          	<div class="col-lg-6 grid-margin stretch-card">
              <div class="card">
                <div class="card-body">
                  <h4 class="card-title">Jobs Percentage</h4>
                  <canvas id="doughnutChart" style="height:250px"></canvas>
                </div>
              </div>
            </div>
            <div class="col-lg-6 grid-margin stretch-card">
              <div class="card">
                <div class="card-body">
                  <h4 class="card-title">Job Count</h4>
                  <canvas id="barChart" style="height:230px"></canvas>
                </div>
              </div>
            </div>
          </div>
<%-- 		<div class="row">
			<div class="col-12 grid-margin stretch-card">
				<div class="card">
					<div class="card-body">
					
			
					
						<h4 class="card-title">Job Status Description</h4>
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
							action="/extract/extractionExtractData1"
							enctype="application/json">
							<input type="hidden" name="x" id="x" value="">

			<div id="allvalues" style="display: block;">
				 <table class="table table-bordered"   >
                    <thead>
                      <tr>
                        <th>
                          Job Id
                        </th>
                        <th>
                          Job Name
                        </th>
                        <th>
                          Batch Id
                        </th>
                        <th>
                         Status
                        </th>
                        <th>
                          Action
                        </th>
                      </tr>
                    </thead>
                    <tbody>
                   <c:forEach var="row" items="${conn_val}">
	                    <tr>
	                    <td><c:out value="${row.job_id}" /></td>
						<td><c:out value="${row.job_name}" /></td>
						<td><c:out value="${row.batch_id}" /></td>
						<td><c:out value="${row.status}" /></td>
						<td><c:out value="${row.command}" /></td>
						
						</tr>
	                </c:forEach>
                      
                     </tbody>
                  </table>
                 </div>
                 <div id="dailyvalues" style="display: none;">
						<table class="table table-bordered"   >
						<thead>
                      <tr>
                        <th>
                          Job Id
                        </th>
                        <th>
                          Job Name
                        </th>
                        <th>
                          Batch Id
                        </th>
                         <th>
                          Time
                        </th>
                        <th>
                         Status
                        </th>
                        <th>
                          Action
                        </th>
                      </tr>
                    </thead>
                     <tbody>
                     </tbody>
						</table>
						
					</div>	
					<div id="weeklyvalues" style="display: none;">
						<table class="table table-bordered"   >
						<thead>
						<tr>
                        <th>
                          Job Id
                        </th>
                        <th>
                          Job Name
                        </th>
                        <th>
                          Batch Id
                        </th>
                         <th>
                          Weekday
                        </th>
                         <th>
                          Time
                        </th>
                        <th>
                         Status
                        </th>
                        <th>
                          Action
                        </th>
                      </tr>
                    </thead>
                     <tbody>
                     </tbody>
						</table>
					</div>
					<div id="monthlyvalues" style="display: none;"  >
						<table class="table table-bordered" >
						<thead>
						<tr>
                        <th>
                          Job Id
                        </th>
                        <th>
                          Job Name
                        </th>
                        <th>
                          Batch Id
                        </th>
                         <th>
                          Day Of Month
                        </th>
                         <th>
                          Time
                        </th>
                        <th>
                         Status
                        </th>
                        <th>
                          Action
                        </th>
                      </tr>
                    </thead>
                     <tbody>
                     </tbody>
						</table>
						
					</div>
					<div id="yearlyvalues" style="display: none;"  >
						<table class="table table-bordered"   >
						<thead>
						<tr>
                        <th>
                          Job Id
                        </th>
                        <th>
                          Job Name
                        </th>
                        <th>
                          Batch Id
                        </th>
                         <th>
                         Date
                        </th>
                         <th>
                          Time
                        </th>
                        <th>
                         Status
                        </th>
                        <th>
                          Action
                        </th>
                      </tr>
                    </thead>
                     <tbody>
                     </tbody>
						</table>
					</div>		

						</form>
					</div>
				</div>
			</div>
		</div> --%>
		<jsp:include page="../cdg_footer.jsp" />