<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

  <script src="${pageContext.request.contextPath}/assets/js/misc.js"></script>
  <script>
  $(document).ready(function() {
		$("#dsradio1").on("change", function() {
			$("#ds1").show();
			$("#ds4").show();
			$("#ds2").hide();
			$("#ds3").hide();
		})
		$("#dsradio2").on("change", function() {
			$("#ds2").show();
			$("#ds1").hide();
			$("#ds3").hide();
			$("#ds4").hide();
		})
		   $("#dsradio3").on("change", function() {
			 $("#ds3").show();
			$("#ds1").hide();
			$("#ds2").hide();
			$("#ds4").hide();
			})   
		$("#success-alert").hide();
      $("#success-alert").fadeTo(10000,10).slideUp(2000, function(){
      });   
$("#error-alert").hide();
      $("#error-alert").fadeTo(10000,10).slideUp(2000, function(){
       });
	});
  </script>
  
<input type="hidden" name="adhoc_flag" id="adhoc_flag" class="form-control" value="">


						<div id="cud1">
								<fieldset class="fs">
								 <div class="form-group row">
									<div class="col-sm-12">
											<label>Batch Description </label> 
											<input type="text" class="form-control" id="batch_desc"
												name="batch_desc" value="${batchArr.BATCH_DESC}" placeholder="Batch Description">
										</div>
										<div class="col-sm-12">
												<label class="col-form-label"><i>${adhoc_flag}</i></label>
											</div>
									</div>	
									<div class="form-group row">
									<div class="col-sm-3">
												<label class="col-form-label">Edit Batch Scheduling</label>
											</div>
										<div class="col-sm-3">
									<div class="form-check form-check-info">
										<label class="form-check-label"> <input type="radio"
											class="form-check-input" name="selection_type" id="selection_type1" value="adhoc" onclick="schedulecheck(this.value)"> Adhoc
										</label>
									</div></div>
								
											<div class="col-sm-3">
												<div class="form-check form-check-info">
													<label class="form-check-label"> <input type="radio"
														class="form-check-input" name="selection_type" id="selection_type2" 
														value="regular" onclick="schedulecheck(this.value)">Regular
													</label>
												</div>
											</div>	
											<div class="col-sm-3">
												<div class="form-check form-check-info">
													<label class="form-check-label"> 
													<input type="radio" class="form-check-input" name="selection_type" id="selection_type3"  
														value="event_based" onclick="schedulecheck(this.value)"> Event Based
													</label>
												</div>
											</div>	
											</div>
											<div class="col-sm-12" id="adhoc_display" style="display: none;" >The job will be scheduled at 00:00</div>
											<div class="col-sm-12" id="frequency_test" style="display: none;" >
											<label>Select Frequency</label> <select class="form-control"
												name="frequency" id="frequency" onchange="schload(this.value)" style="display: none;">	
												<option value="" selected disabled>Select Frequency</option>
												 <option value="h">Hourly</option> 
												<option value="d">Daily</option>
												<option value="w">Weekly</option>
												<option value="m">Monthly</option>
												<!--  <option value="y">Yearly</option>
												<option value="c">Custom</option>-->
											</select>
								</div>	
								<div id="sch_load_test" style="display: none;"></div>
									<div id="custom" style="display: none;">
										<div class="form-group row">
										<div class="col-sm-1">
										</div>
											<div class="col-sm-2">
												<label>Select Minute</label>
												<div id="mindiv"></div>
											</div>
											<div class="col-sm-2">
												<label>Select Hour</label>
												<div id="hrdiv"></div>
											</div>
											<div class="col-sm-2">
												<label>Select Day of Week</label>
												<div id="dowdiv"></div>
											</div>
											<div class="col-sm-2">
												<label>Select Day of Month</label>
												<div id="domdiv"></div>
											</div>
											<div class="col-sm-2">
												<label>Select Month</label>
												<div id="mondiv"></div>
											</div>
										</div>
									</div>						
									<div id="Schedule_type" style="display: none;">
								<div class="form-group row">
									<label class="col-sm-3 col-form-label">Schedule Type<span
										style="color: red">*</span></label>
									<div class="col-sm-3">
										<div class="form-check form-check-info">
											<label class="form-check-label"> <input type="radio"
												class="form-check-input" name="dsoptradioo" id="dsradio1"
												value="file watcher" class="form-control"  onclick="fun(this.value)"> File Watcher
											</label>
										</div>
						
									
									<div class="row" id="ds1" style="display: none;">
									<div class="col-md-9"></div>
									<div class="col-md-12">
										<input type="text" class="form-control"
											placeholder="File watcher path" id="Filepath" name="Filepath">
									</div>
									</div>
									<br>
								<div class="row" id="ds4" style="display: none;">
									<div class="col-md-9"></div>
									<div class="col-md-12">
										<input type="text" class="form-control"
											placeholder="File name pattern" id="Filepattern" name="Filepattern">
									</div>
									</div>
									</div>
								
									<div class="col-sm-3">
										<div class="form-check form-check-info">
											<label class="form-check-label"> <input type="radio"
												class="form-check-input" name="dsoptradioo" id="dsradio2"
												value="kafka" class="form-control"  onclick="fun(this.value)"> Kafka Topic
											</label>
										</div>
										<div class="row" id="ds2" style="display: none;">
									<div class="col-md-9"></div>
									<div class="col-md-12">
									<select class="form-control"
									id="kafka_topic" name="kafka_topic">
									<option value="" selected disabled>Kafka name...</option>
									<c:forEach items="${kafka_topic}" var="kafka_topic">
										<option value="${kafka_topic}">${kafka_topic}</option>
									</c:forEach>
								</select>
									</div>
									</div>
									</div>
									<div class="col-sm-3">
										<div class="form-check form-check-info">
											<label class="form-check-label"> <input type="radio"
												class="form-check-input" name="dsoptradioo" id="dsradio3"
												value="API" class="form-control" 
										 onclick="fun(this.value)"> API </label>
											
										</div>
										<div class="row" id="ds3" style="display: none;">
									<div class="col-md-9"></div>
									<div class="col-md-12">
										<input type="text" class="form-control" 
											placeholder="API Feed name" id="api_unique_key" name="api_unique_key" readonly>
									</div>
								</div>
									</div>					
						</div>
						</div>
								</fieldset>
								<button onclick="jsonconstruct('edit');"class="btn btn-rounded btn-gradient-info mr-2">Save</button>
							</div>
							
