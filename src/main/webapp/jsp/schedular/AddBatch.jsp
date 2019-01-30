<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../cdg_header.jsp" />
<script>
	function jsonconstruct(val) {
		var data = {};
		document.getElementById('button_type').value = val;
		document.getElementById("cron").value = cron_construct();
		$(".form-control").serializeArray().map(function(x) {
			data[x.name] = x.value;
		});
		var x = '{"header":{},"body":{"data":'
				+ JSON.stringify(data) + '}}';
		document.getElementById('x').value = x;
		//console.log(x);
		//alert(x);
		document.getElementById('AddBatch').submit();
	}
	function looper(id, start, end) {
		var x = '<select name="'+id+'" id="'+id+'" class="form-control" multiple="multiple"><option value="*">Select Value...</option>';
		for (var i = start; i <= end; i++) {
			x += '<option value="'+i+'">' + i + '</option>';
		}
		x += '</select>';
		return x;
	}
	function dow(id) {
		var x = '<select name="'+id+'" id="'+id+'" class="form-control" multiple="multiple">';
		x += '<option value="*">Select Days of Week</option><option value="*">All Days</option><option value="SUN">Sunday</option><option value="MON">Monday</option><option value="TUE">Tuesday</option><option value="WED">Wednesday</option><option value="THU">Thursday</option><option value="FRI">Friday</option><option value="SAT">Saturday</option>';
		x += '</select>';
		return x;
	}
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
		$("#batch_val").change(function() {
			var batch_id = $(this).val();
			var project_id =document.getElementById('project').value;
			$.post('/scheduler/BatchEdit', {
				batch_id : batch_id,
				project_id :project_id,
				}, function(data) {
				$('#cud').html(data)
			});
		});
		$("#success-alert").hide();
        $("#success-alert").fadeTo(10000,10).slideUp(2000, function(){
        });   
 $("#error-alert").hide();
        $("#error-alert").fadeTo(10000,10).slideUp(2000, function(){
         });
	});

	function funccheck(val) {
		if (val == 'create') {
					window.location.reload();
		} else {
			document.getElementById('connfunc').style.display = "block";
			document.getElementById('cud').innerHTML="";
		}
	}
	function schedulecheck(val) {
		if (val == 'adhoc') {			
			document.getElementById('adhoc_display').style.display = "block";
			document.getElementById('Schedule_type').style.display = "none";
			document.getElementById('frequency_test').style.display = "none";
			document.getElementById('frequency').style.display = "none";
			document.getElementById('sch_load_test').style.display = "none";
			document.getElementById('custom').style.display = "none";
			document.getElementById('sch_type').value = 'adhoc';
		} else if(val == 'regular'){
			document.getElementById('adhoc_display').style.display = "none";
			document.getElementById('sch_type').value = 'regular';
			document.getElementById('frequency_test').style.display = "block";
			document.getElementById('frequency').style.display = "block";
			document.getElementById('sch_load_test').style.display = "block";
			document.getElementById('Schedule_type').style.display = "none";
		}else{
			document.getElementById('adhoc_display').style.display = "none";
			document.getElementById('sch_type').value = 'event_based';
			document.getElementById('Schedule_type').style.display = "block";
			document.getElementById('frequency_test').style.display = "none";
			document.getElementById('frequency').style.display = "none";
			document.getElementById('sch_load_test').style.display = "none";
			document.getElementById('custom').style.display = "none";
			
		}
	}
		
		function schload(val) {
			var x, num, dow = '<select name="dy" id="dy" class="form-control"><option value="">Select Day of Week</option><option value="SUN">Sunday</option><option value="MON">Monday</option><option value="TUE">Tuesday</option><option value="WED">Wednesday</option><option value="THU">Thursday</option><option value="FRI">Friday</option><option value="SAT">Saturday</option></select>';
			document.getElementById("custom").style.display = "none";
		
			for (var i = 1; i <= 31; i++) {
				num += '<option value="'+i+'">' + i + '</option>';
			}
			if (val == 'h') {
				var x = '<div class="col-sm-12"><label>The job will be executed every hour at 00 minutes.</label></div>';
			} else if (val == 'd') {
				var x = '<div class="col-sm-12"><label>The job will be executed daily at the selected time.</label></div><div class="col-sm-12"><label>Select Time</label><input type="time" name="tm" id="tm" class="form-control"></div>';
			} else if (val == 'w') {
				var x = '<div class="col-sm-12"><label>The job will be executed weekly on the selected day at the selected time.</label></div><div class="col-sm-12"><div class="col-sm-6"><label>Select Day of Week</label>'
						+ dow
						+ '</div><div class="col-md-6"><label>Select Time</label><input type="time" name="tm" id="tm" class="form-control"></div></div>';
			} else if (val == 'm') {
				var x = '<div class="col-sm-12"><label>The job will be executed monthly on the selected day at the selected time.</label></div><div class="col-sm-12"><div class="col-sm-6"><label>Select Day of Month</label><select name="dyt" id="dyt" class="form-control"><option value="">Select Day of Month</option>'
						+ num
						+ '</select></div><div class="col-sm-6"><label>Select Time</label><input type="time" name="tm" id="tm" class="form-control"></div></div>';
			} else if (val == 'y') {
				var x = '<div class="col-sm-12"><label>The job will be executed yearly on the selected date at the selected time.</label></div><div class="col-sm-12"><div class="col-sm-6"><label>Select Date</label><input type="date" name="dt" id="dt" class="form-control"></div><div class="col-sm-6"><label>Select Time</label><input type="time" name="tm" id="tm" class="form-control"></div></div>';
			} else if (val == 'c') {
				var x = '<div class="col-sm-12"><label>Create custom execution frequencies for your job from the below list.</label></div>';
				document.getElementById("custom").style.display = "block";
			}
			document.getElementById('sch_load_test').innerHTML = x;
		}
		
		function cron_construct() {
			var cron;
			var x = document.getElementById("frequency").value;
			if (x == "h") {
				cron = "0 * * * *";
			} else if (x == "d") {
				var t = document.getElementById("tm").value;
				var y = t.split(":");
				cron = y[1] + " " + y[0] + " * * *";
			} else if (x == "w") {
				var d = document.getElementById("dy").value;
				var t = document.getElementById("tm").value;
				var y = t.split(":");
				cron = y[1] + " " + y[0] + " * * " + d;
			} else if (x == "m") {
				var d = document.getElementById("dyt").value;
				var t = document.getElementById("tm").value;
				var y = t.split(":");
				cron = y[1] + " " + y[0] + " " + d + " * *";
			} else if (x == "y") {
				var d = document.getElementById("dt").value;
				var t = document.getElementById("tm").value;
				var y = t.split(":");
				var z = d.split("-");
				cron = y[1] + " " + y[0] + " " + z[2] + " " + z[1] + " *";
			} else if (x == "c") {
				var min = $('#mins').val();
				var hr = $('#hr').val();
				var dyt = $('#dyt').val();
				var mon = $('#mon').val();
				var dy = $('#dy').val();
				if (min == "")
					min = "*";
				if (hr == "")
					hr = "*";
				if (dyt == "")
					dyt = "*";
				if (mon == "")
					mon = "*";
				if (dy == "")
					dy = "*";
				cron = min + " " + hr + " " + dyt + " " + mon + " " + dy;
			}else{
				cron="";
			}
			return cron;
		}	
		
		function fun(val) {
			if (val == 'API') {
				document.getElementById('api_unique_key').value = Math.floor(Math.random()*1000000000);
				document.getElementById('sch_flag').value = 'A';
			
			} 
			else if (val == 'file watcher') {
				document.getElementById('sch_flag').value = 'F';
				
			} 
			else if (val == 'kafka') {
				document.getElementById('sch_flag').value = 'K';
				
			} 
		}
</script>
<div class="main-panel">
	<div class="content-wrapper">
		<div class="row">
			<div class="col-12 grid-margin stretch-card">
				<div class="card">
					<div class="card-body">
						<h4 class="card-title">Add Batch</h4>
						<p class="card-description">Batch Details</p>
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
						<script type="text/javascript">
							window.onload = function() {
								
							}
						</script>
						<form class="forms-sample" id="BatchDetails"
							name="ConnectionDetails" method="POST"
							action="${pageContext.request.contextPath}/scheduler/AddBatchClick"
							enctype="application/json">
							<input type="hidden" name="x" id="x" value=""> 
							<input type="hidden" name="button_type" id="button_type" class="form-control" value="">
							<input type="hidden" name="src_val" id="src_val"
								value="${src_val}">
								<input type="hidden" name="project" id="project" class="form-control"
								value="${project}">
								<input type="hidden" name="user" id="user" class="form-control"
								value="${usernm}">
								<input type="hidden" name="sch_flag" id="sch_flag" class="form-control"
								value="">
								<input type="hidden" name="sch_type" id="sch_type" class="form-control"
								value="">
								<input type="hidden" name="cron" id="cron" class="form-control"
								value="">

							<div class="form-group row">
								<label class="col-sm-3 col-form-label">Batch</label>
								<div class="col-sm-4">
									<div class="form-check form-check-info">
										<label class="form-check-label"> <input type="radio"
											class="form-check-input" name="radio" id="radio1"
											checked="checked" value="create"
											onclick="funccheck(this.value)"> Create
										</label>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-check form-check-info">
										<label class="form-check-label"> <input type="radio"
											class="form-check-input" name="radio" id="radio2"
											value="edit" onclick="funccheck(this.value)"> Edit/View
										</label>
									</div>
								</div>
							</div>

							<div class="form-group" id="connfunc" style="display: none;">
								<label>Select Batch</label> 
								<select name="batch_val" id="batch_val"
									class="form-control">
									<option value="" selected disabled>Select Batch
										...</option>
										<c:forEach items="${batch_val}" var="batch_val">
										<option value="${batch_val.BATCH_UNIQUE_NAME}">${batch_val.BATCH_UNIQUE_NAME}</option>
									</c:forEach>
								</select>
							</div>
							<div id="cud">
								<fieldset class="fs">
									<div class="form-group row">
										<div class="col-sm-12">
											<label>Batch Name *</label> <input type="text"
												class="form-control" id="batch_name"
												name="batch_name" placeholder="Batch Name">
										</div>
									</div>
									<div class="form-group row">
									<div class="col-sm-12">
											<label>Batch Description </label> <input type="text"
												class="form-control" id="batch_desc"
												name="batch_desc" placeholder="Batch Description">
										</div>
										</div>	
										<div class="form-group row">	
										<div class="col-sm-3">
										<label class="col-form-label">Batch Scheduling</label>
									</div>
									<div class="col-sm-3">
									<div class="form-check form-check-info">
									
										<label class="form-check-label"> <input type="radio"
											class="form-check-input" name="selection_type" id="selection_type1" value="adhoc"
											onclick="schedulecheck(this.value)"> Adhoc
										</label>
									</div></div>
								
								<div class="col-sm-3">
									<div class="form-check form-check-info">
										<label class="form-check-label"> <input type="radio"
											class="form-check-input" name="selection_type" id="selection_type2"
											value="regular" onclick="schedulecheck(this.value)"> Regular
										</label>
									</div>
								</div>	
								<div class="col-sm-3">
									<div class="form-check form-check-info">
										<label class="form-check-label"> <input type="radio"
											class="form-check-input" name="selection_type" id="selection_type3"
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
									<!-- <option value="h">Hourly</option> -->
									<option value="d">Daily</option>
									<option value="w">Weekly</option>
									<option value="m">Monthly</option>
									<!-- <option value="y">Yearly</option> -->
									<!-- <option value="c">Custom</option> -->
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
								<button onclick="jsonconstruct('add');"class="btn btn-rounded btn-gradient-info mr-2">Save</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
<jsp:include page="../cdg_footer.jsp" />
<script>
document.getElementById("mindiv").innerHTML = looper("mins", 0, 59);
document.getElementById("hrdiv").innerHTML = looper("hr", 0, 23);
document.getElementById("domdiv").innerHTML = looper("dyt", 1, 31);
document.getElementById("mondiv").innerHTML = looper("mon", 1, 12);
document.getElementById("dowdiv").innerHTML = dow("dy");
</script>