<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../cdg_header.jsp" />
<script>
	function jsonconstruct(val) {
		var data = {};
		document.getElementById('button_type').value = val;
		$(".form-control").serializeArray().map(function(x) {
			data[x.name] = x.value;
		});
		var x = '{"header":{},"body":{"data":'
				+ JSON.stringify(data) + '}}';
		document.getElementById('x').value = x;
		//console.log(x);
		alert(x);
		document.getElementById('AddBatch').submit();
	}
	$(document).ready(function() {
		$("#conn").change(function() {
			var conn = $(this).val();
			var src_val = document.getElementById("src_val").value;
			$.post('${pageContext.request.contextPath}/extraction/ConnectionDetailsEdit', {
				conn : conn,
				src_val : src_val
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
							<input type="hidden" name="x" id="x" value=""> <input
								type="hidden" name="button_type" id="button_type" value="">
							<input type="hidden" name="src_val" id="src_val"
								value="${src_val}">
								<input type="hidden" name="project" id="project" class="form-control"
								value="${project}">
								<input type="hidden" name="user" id="user" class="form-control"
								value="${usernm}">

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
								<label>Select Connection</label> <select name="conn" id="conn"
									class="form-control">
									<option value="" selected disabled>Select Connection
										...</option>
									<c:forEach items="${conn_val}" var="conn_val">
										<option value="${conn_val.connection_id}">${conn_val.connection_name}</option>
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
											<label>Batch ID *</label> <input type="text"
												class="form-control" id="batch_id"
												name="batch_id" placeholder="Batch ID">
										</div>
										</div>
										<div class="form-group row">
										<div class="col-sm-12">
											<label>Batch Description *</label> <input type="text"
												class="form-control" id="batch_desc"
												name="batch_desc" placeholder="Batch Description">
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