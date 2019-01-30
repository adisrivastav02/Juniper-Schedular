<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../cdg_header2.jsp" />
<script>
	function json_construct() {
		get_seq();
		var data = {};
		$(".form-control").serializeArray().map(function(x) {
			data[x.name] = x.value;
		});
		var x = '{"header":{"user":"info@clouddatagrid.com","service_account":"Extraction_CDG_UK","reservoir_id":"R0001","event_time":"today"},"body":{"data":'
				+ JSON.stringify(data) + '}}';
		document.getElementById('x').value = x;
		//console.log(x);
		//alert(x);
		document.getElementById('seqpip').submit();
	}
	
	
	$(document).ready(function() {
		$("#batch").change(function() {
			var batchid = $(this).val();
			var project_id =document.getElementById('project').value;
			$.post('/scheduler/CreateSequence1', {
				batchid : batchid,
				project_id :project_id
			}, function(data) {
				$('#daglist').html(data)
			});
		});
	});
</script>

<div class="main-panel">
	<div class="content-wrapper">
		<div class="row">
			<div class="col-12 grid-margin stretch-card">
				<div class="card">
					<div class="card-body">
						<h4 class="card-title">DAG Sequence</h4>
						<p class="card-description">Create Sequence</p>
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
						<form role="form" class="forms-sample" name="seqpip"
							action="${pageContext.request.contextPath}/scheduler/CreateSequenceSubmit"
							method="post">
							
							<div class="form-group row">
								<div class="col-sm-12">
									<label>Flow Name</label> <input type="text"
										class="form-control" id="sequence_name" name="sequence_name"
										placeholder="Sequence Name">
								</div>

							</div>
							
								<div class="form-group row" id="connfunc">
								<div class="col-sm-12">
								<label>Select Batch *</label> <select name="batch" id="batch"
									class="form-control">
									<option value="" selected disabled>Select Batch
										...</option>
									<c:forEach items="${batch_val}" var="batch_val">
										<option value="${batch_val.BATCH_UNIQUE_NAME}">${batch_val.BATCH_UNIQUE_NAME}</option>
									</c:forEach>
								</select>
							</div>
							</div>				
							
							<div id="composerList">
							</div>
							
							<div>
							<div style="float:left;width:20%;height:25px;font-weight:bold;text-align:center;">Artifacts</div>
							<div style="float:right;width:80%;height:25px;font-weight:bold;text-align:center;">Execution Sequence ---></div>
							</div>	
							<div>
							<div id="daglist" class="double-scroll" style="float:left;width:20%;height:600px;overflow:auto;scrollbar-x-position:top;border:1px solid grey;border-radius:10px;">				
							</div>
							<div class="grid-container" class="double-scroll" style="float:right;width:80%;height:600px;overflow:auto;overflow-y:hidden;scrollbar-x-position:top;border:1px solid grey;border-radius:10px;"></div>
							</div>
							
							<input type="hidden" name="sequence" id="sequence"
								class="form-control"> <input type="hidden" name="x"
								id="x">
							<input type="hidden" name="project" id="project" class="form-control"
								value="${project}">
							<center>
								<button class="btn btn-rounded btn-gradient-info mr-2" style="margin: 10px;" onclick="json_construct();">Create Flow</button>
							</center>		
						</form>
					</div>
				</div>
			</div>
		</div>
		<jsp:include page="../cdg_footer.jsp" />
		