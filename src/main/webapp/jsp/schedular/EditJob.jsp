<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="form-group row">

	<div class="col-sm-12">
		<label>Job Description *</label> 
			<input type="text"	class="form-control" id="job_name1" name="job_name1" value="${jobArr.job_name}">
	</div>
		<div class="col-sm-12">
											<label>Command Type *</label> <select class="form-control" id="command_type1" name="command_type1" onchange="bqcheck(this.id,this.value)">
											<option value="${jobArr.command_type}" selected>${jobArr.command_type}</option>
												<option value="shell">shell</option>
												<option value="python">python</option>
												<option value="java">java</option>
												<option value="bigquery">bigquery</option>
											</select>
										</div>
										<div class="col-sm-12">
												<label>Script Location</label> <input type="text"
													class="form-control" id="script1" name="script1" readonly>
											</div>
											<div class="col-sm-12">
											<label>Command *</label> <input type="text"
												class="form-control" id="command1" name="command1"
												value="${jobArr.command}">
										</div>
										</div>
										<div class="form-group row">
										<div class="col-sm-4">
											<label>Argument_1 </label> <input type="text"
												class="form-control" id="argument_11" name="argument_11"
												value="${jobArr.argument_1}">
										</div>
										<div class="col-sm-4">
											<label>Argument_2 </label> <input type="text"
												class="form-control" id="argument_21" name="argument_21"
												value="${jobArr.argument_2}">
										</div>
										<div class="col-sm-4">
											<label>Argument_3 </label> <input type="text"
												class="form-control" id="argument_31" name="argument_31"
												value="${jobArr.argument_3}">
										</div>		
										</div>	
<button class="btn btn-rounded btn-gradient-info mr-2" id="update" onclick="return jsonconstruct(this.id)">Update Task</button>