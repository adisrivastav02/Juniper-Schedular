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
			if(document.getElementById('button_type').value=="create"){
				document.getElementById("composerList").style.display="";
				var project_id =document.getElementById('project').value;
				$.post('/scheduler/CreateSequence1', {
					batchid : batchid,
					project_id :project_id
				}, function(data) {
					$('#daglist').html(data)
				});
			}else{
			document.getElementById("composerList").style.display="";
			var project_id =document.getElementById('project').value;
			$.post('/scheduler/EditSequence', {
				batchid : batchid,
				project_id :project_id
			}, function(data) {
				$('#daglist').html(data)
			});
			}
		});
	});
	
	function allowDrop(ev) {
		ev.preventDefault();
	}
	
	function drop(ev,el) {
		ev.preventDefault();
		var data = ev.dataTransfer.getData("text");
		el.appendChild(document.getElementById(data));
	}
	function funccheck(val) {
		if (val == 'create') {
			document.getElementById('button_type').value="create";
			document.getElementById('batch2').value="";
			document.getElementById('l1').style.display = "block";
			document.getElementById('l2').style.display = "none";
			window.location.reload();
		} else {
			document.getElementById('l2').style.display = "block";
			document.getElementById('l1').style.display = "none";
			document.getElementById('button_type').value="edit";
			document.getElementById('batch1').value="";
			document.getElementById("composerList").style.display="none";
		}	
	}
	
	function testfunc1(val){
		var batchid = val;
		if(document.getElementById('button_type').value=="create"){
			document.getElementById("composerList").style.display="";
			var project_id =document.getElementById('project').value;
			$.post('/scheduler/CreateSequence1', {
				batchid : batchid,
				project_id :project_id
			}, function(data) {
				$('#daglist').html(data)
			});
	}
	}
			
	function testfunc2(val){
		document.getElementById("composerList").style.display="";
		var batchid=val;
		var project_id =document.getElementById('project').value;
		$.post('/scheduler/EditSequence', {
			batchid : batchid,
			project_id :project_id
		}, function(data) {
			$('#daglist').html(data)
		});
	}
</script>

<div class="main-panel">
	<div class="content-wrapper">
		<div class="row">
			<div class="col-12 grid-margin stretch-card">
				<div class="card">
					<div class="card-body">
						<h4 class="card-title">Task Sequence</h4>
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
           
						
							
							
             
               <input type="hidden" name="xtest" id="xtest" value="${xtest}">
                <input type="hidden" name="button_type" id="button_type" value="create">
						<form role="form" class="forms-sample" name="seqpip"
							action="${pageContext.request.contextPath}/scheduler/CreateSequenceSubmit"
							method="post" enctype="application/json">
							  <input type="hidden" name="x" id="x" value="">
							<div class="form-group row">
								<label class="col-sm-3 col-form-label">Sequence</label>
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
							
								<div class="form-group row" id="connfunc">
								<div class="col-sm-12">
								<label>Select Batch *</label> 
								<input list="batch1" id="l1" name="l1" class="form-control" onchange="testfunc1(this.value)">
									<datalist id="batch1">
									  <c:forEach items="${batch_val1}" var="batch_val1">
										<option value="${batch_val1.BATCH_UNIQUE_NAME}">
									</c:forEach>
									</datalist>
								<input list="batch2" id="l2" name="l2" class="form-control" onchange="testfunc2(this.value)" style="display: none;"  >
									<datalist id="batch2" style="display: none;">
									  <c:forEach items="${batch_val2}" var="batch_val2">
										<option value="${batch_val2.BATCH_UNIQUE_NAME}">
									</c:forEach>
									</datalist>
							</div>
							</div>				
							
							<div id="composerList" style="display:none;">
							
							
							<div>
							<div style="float:left;width:20%;height:25px;font-weight:bold;text-align:center;">Tasks</div>
							<div style="float:right;width:80%;height:25px;font-weight:bold;text-align:center;">Task Sequence ---></div>
							</div>	
							<div>
							<div id="daglist" class="double-scroll"  ondrop='drop(event,this)' ondragover='allowDrop(event)' style="float:left;width:20%;height:600px;overflow:auto;scrollbar-x-position:top;border:1px solid grey;border-radius:10px;">				
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
							</div>	
						</form>
					</div>
				</div>
			</div>
		</div>
		<jsp:include page="../cdg_footer.jsp" />
		