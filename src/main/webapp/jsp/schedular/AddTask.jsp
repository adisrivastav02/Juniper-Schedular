<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../cdg_header.jsp" />
<script>
var i = 1;
function dup_div1() {
	var dyn = document.getElementById('cud1');
	var dyndiv = dyn.cloneNode(true);
	var x = ++i;
	dyndiv.id = "cud" + i;
	dyndiv.getElementsByTagName('div')[0].id = "dynshort" + i;
	dyndiv.getElementsByTagName('div')[2].id = "dynlong" + i;
	dyndiv.getElementsByTagName('input')[0].id = "job_id" + i;
	dyndiv.getElementsByTagName('input')[0].name = "job_id" + i;
	dyndiv.getElementsByTagName('input')[1].id = "job_name" + i;
	dyndiv.getElementsByTagName('input')[1].name = "job_name" + i;
	dyndiv.getElementsByTagName('select')[0].id = "command_type" + i;
	dyndiv.getElementsByTagName('select')[0].name = "command_type" + i;
	dyndiv.getElementsByTagName('input')[2].id = "script" + i;
	dyndiv.getElementsByTagName('input')[2].name = "script" + i;
	dyndiv.getElementsByTagName('input')[3].id = "command" + i;
	dyndiv.getElementsByTagName('input')[3].name = "command" + i;
	dyndiv.getElementsByTagName('input')[4].id = "argument_1" + i;
	dyndiv.getElementsByTagName('input')[4].name = "argument_1" + i;
	dyndiv.getElementsByTagName('input')[5].id = "argument_2" + i;
	dyndiv.getElementsByTagName('input')[5].name = "argument_2" + i;
	dyndiv.getElementsByTagName('input')[6].id = "argument_3" + i;
	dyndiv.getElementsByTagName('input')[6].name = "argument_3" + i;
	
	
	dyn.parentNode.appendChild(dyndiv);
	document.getElementById('counter').value = i;
}

function togg(ids, idx) {
	var x5 = idx.slice(-1);
	if (ids == "min") {
		document.getElementById("dynlong" + x5).style.display = "none";
		document.getElementById("dynshort" + x5).style.display = "block";

		var x7 = document.getElementById("dynshort" + x5).innerHTML;
		x7 = x7.substr(x7.indexOf('<'), x7.length);

		var x6 = "Job Name : "
				+ document.getElementById("job_id" + x5).value;

		document.getElementById("dynshort" + x5).innerHTML = x6 + x7;
	}
	if (ids == "max") {
		document.getElementById("dynlong" + x5).style.display = "block";
		document.getElementById("dynshort" + x5).style.display = "none";
	}
}

function jsonconstruct(id) {
	alert("Reached the jsonconstruct");
	var data = {};
	var errors = [];
	var i;
	var counter=document.getElementById("counter").value;
	for (i=1;i<=counter;i++){
		document.getElementById("command"+i).value=document.getElementById("script"+i).value+document.getElementById("command"+i).value;
	}

	for (i=1;i<=counter;i++){
	var job_id1 = document.getElementById("job_id"+i).value;
	var job_name1 = document.getElementById("job_name"+i).value;
	var command_type1 = document.getElementById("command_type"+i).value;
	var script1 = document.getElementById("script"+i).value;
	var command1 = document.getElementById("command"+i).value;
	var argument_11 = document.getElementById("argument_1"+i).value;
	var argument_21 = document.getElementById("argument_2"+i).value;
	var argument31 = document.getElementById("argument_3"+i).value;
	if (!checkLength(job_id1)) {
		errors[errors.length] = "job_id"+i;
	}
	if (!checkLength(job_name1)) {
		errors[errors.length] = "job_name"+i;
	}
	if (!checkLength(command_type1)) {
		errors[errors.length] = "command_type"+i;
	}
	if (!checkLength(script1)) {
		errors[errors.length] = "script"+i;
	}
	if (!checkLength(command1)) {
		errors[errors.length] = "command"+i;
	}
	
	}
	
	
	for (i=1;i<=counter;i++){
		if(document.getElementById("command_type"+i).value=="shell"){	
			if(document.getElementById("command"+i).value.substr(-3)!=".sh"){
				errors[errors.length]="Command  path with command type selected as shell must end with .sh ";
			}
		}
		if(document.getElementById("command_type"+i).value=="python"){	
			if(document.getElementById("command"+i).value.substr(-3)!=".py"){
				errors[errors.length]="Command  path with command type selected as python must end with .py ";
			}
		}
		if(document.getElementById("command_type"+i).value=="java"){	
			if(document.getElementById("command"+i).value.substr(-4)!=".jar"){
				errors[errors.length]="Command  path with command type selected as java must end with .jar ";
			}
		}
		if(document.getElementById("command_type"+i).value=="bigquery"){	
			if(document.getElementById("command"+i).value.substr(-4)!=".bql"){
				errors[errors.length]="Command  path with command type selected as bigquery must end with .bql ";
			}
		}
	}
	
	
	
	if (errors.length > 0) {
		reportErrors(errors);
		return false;
	}
	
		$(".form-control").serializeArray().map(function(x) {
			data[x.name] = x.value;
		});
	var x = '{"header":{"user":"info@clouddatagrid.com","service_account":"Extraction_CDG_UK","reservoir_id":"R0001","event_time":"today"},"body":{"data":'
			+ JSON.stringify(data) + '}}';
	document.getElementById('x').value = x;
	//console.log(x);
	//alert(x);
	document.getElementById('AddTaskSave').submit();
	
}
	$(document).ready(function() {
		$("#batch1").change(function() {
			if(document.getElementById('button_type').value=="create"){
				document.getElementById('cud1').style.display = "block";
				document.getElementById('addandsavebuttons').style.display = "block";
			}	
		});
		
		$("#batch2").change(function() {
				document.getElementById('addandsavebuttons').style.display = "none";
				var batch=document.getElementById('batch2').value;
				var project=document.getElementById('project').value;
				document.getElementById('cud1').style.display = "block";
				$.post('${pageContext.request.contextPath}/scheduler/LoadBatchJobs', {
					batch : batch,
					project : project
				}, function(data) {
					$('#cud1').html(data)
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
			document.getElementById('button_type').value="create";
			document.getElementById('batch1').style.display = "block";
			document.getElementById('batch2').style.display = "none";
			document.getElementById('l2').style.display = "none";
			document.getElementById('batch2').innerHTml="";
			
			
		} else {
			//document.getElementById('batch1').value = "";
			document.getElementById('addandsavebuttons').style.display = "none";
			document.getElementById('button_type').value="edit";
			document.getElementById('connfunc').style.display = "block";
			document.getElementById('cud1').innerHTML="";
			document.getElementById('batch1').style.display = "none";
			document.getElementById('l2').style.display = "block";
			document.getElementById('l1').style.display = "none";
			//document.getElementById('batch2').style.display = "block";
		}
		
		
	}
	
	function testfunc1(val){
		document.getElementById('cud1').style.display = "block";
		document.getElementById('addandsavebuttons').style.display = "block";
	}
	function testfunc2(val){
		document.getElementById('addandsavebuttons').style.display = "none";
		var batch=val;
		var project=document.getElementById('project').value;
		document.getElementById('cud1').style.display = "block";
		$.post('${pageContext.request.contextPath}/scheduler/LoadBatchJobs', {
			batch : batch,
			project : project
		}, function(data) {
			$('#cud1').html(data)
		});
	}
	
	function bqcheck(id,val){
		id=id.substr(-1);
		if(val=="bigquery"){
			//document.getElementById("script"+id).value="x";
				document.getElementById("script"+id).value="/home/juniper/bql/";
		}else{
			//document.getElementById("script"+id).value="y";
				document.getElementById("script"+id).value="/home/juniper/scripts/";
		}
		
	}
</script>
<div class="main-panel">
	<div class="content-wrapper">
		<div class="row">
			<div class="col-12 grid-margin stretch-card">
				<div class="card">
					<div class="card-body">
						<h4 class="card-title">Add Task</h4>
						<p class="card-description">Task Details</p>
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
						<form class="forms-sample" id="AddTaskSave" name="AddTaskSave"
							method="POST"
							action="${pageContext.request.contextPath}/scheduler/AddTaskSave"
							enctype="application/json">
							<input type="hidden" name="x" id="x" value=""> <input
								type="hidden" name="button_type" id="button_type" value="create">
							<input type="hidden" name="src_val" id="src_val"
								value="${src_val}"> <input type="hidden" name="project"
								id="project" class="form-control" value="${project}"> <input
								type="hidden" name="user" id="user" class="form-control"
								value="${usernm}"> <input class="form-control"
								type="hidden" id="counter" name="counter" value="1">

							<div class="form-group row">
								<label class="col-sm-3 col-form-label">Connection</label>
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
											value="edit" onclick="funccheck(this.value)">
											Edit/View
										</label>
									</div>
								</div>
							</div>

							<div class="form-group" id="connfunc">
								<label>Select Batch *</label> 
								<input list="batch1" id="l1" name="l1" class="form-control" onchange="testfunc1(this.value)">
									<datalist id="batch1">
									  <c:forEach items="${batch_val1}" var="batch_val1">
										<option value="${batch_val1.BATCH_UNIQUE_NAME}">
									</c:forEach>
									</datalist>
								<input list="batch2" id="l2"  name="l2" class="form-control" onchange="testfunc2(this.value)" style="display: none;"  >
									<datalist id="batch2" style="display: none;">
									  <c:forEach items="${batch_val2}" var="batch_val2">
										<option value="${batch_val2.BATCH_UNIQUE_NAME}">
									</c:forEach>
									</datalist>
								<div id="cud1" class="fs" style="display: none;">
									<div id="dynshort1" style="display: none;">
										<div style="float: right; z-index: 999; cursor: pointer;"
											onclick="togg('max',this.parentNode.id)">
											<b>+</b>
										</div>
									</div>
									<div id="dynlong1" style="display: block;">
										<div style="float: right; z-index: 999; cursor: pointer;"
											onclick="togg('min',this.parentNode.id)">
											<b><font size="5">-</font></b>
										</div>

										<div class="form-group row">
											<div class="col-sm-6">
												<label>Job Name *</label> <input type="text"
													class="form-control" id="job_id1" name="job_id1"
													placeholder="Job Name">
											</div>
											<div class="col-sm-6">
												<label>Job Description *</label> <input type="text"
													class="form-control" id="job_name1" name="job_name1"
													placeholder="Job Description">
											</div>
										</div>
										<div class="form-group row">
											<div class="col-sm-12">
												<label>Command Type *</label> <select class="form-control"
													id="command_type1" name="command_type1"
													onchange="bqcheck(this.id,this.value)">
													<option value="" selected disabled>Select Command
														Type</option>
													<option value="shell">shell</option>
													<option value="python">python</option>
													<option value="java">java</option>
													<option value="bigquery">bigquery</option>
												</select>
											</div>
										</div>

										<div class="form-group row">
										<div class="col-sm-12">
												<label>Script Location</label> <input type="text"
													class="form-control" id="script1" name="script1" readonly>
											</div>
											<div class="col-sm-12">
												<label>Command/BQL name*</label> <input type="text"
													class="form-control" id="command1" name="command1"
													placeholder="Command">
											</div>
										</div>
										<div class="form-group row">
											<div class="col-sm-4">
												<label>Argument_1 </label> <input type="text"
													class="form-control" id="argument_11" name="argument_11"
													placeholder="argument_1">
											</div>
											<div class="col-sm-4">
												<label>Argument_2 </label> <input type="text"
													class="form-control" id="argument_21" name="argument_21"
													placeholder="argument_2">
											</div>
											<div class="col-sm-4">
												<label>Argument_3 </label> <input type="text"
													class="form-control" id="argument_31" name="argument31"
													placeholder="argument_3">
											</div>
										</div>

									</div>
								</div>
							</div>
							<div id="addandsavebuttons" style="display: none;">
								<div style="float: right;">
									<button id="add" type="button"
										class="btn btn-rounded btn-gradient-info mt-2"
										onclick="return dup_div1();">+</button>
								</div>
								<button class="btn btn-rounded btn-gradient-info mr-2" id="save"
									onclick=" return jsonconstruct(this.id)">Save Task</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<jsp:include page="../cdg_footer.jsp" />