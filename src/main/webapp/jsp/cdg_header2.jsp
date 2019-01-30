<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ page import="javax.servlet.http.*"%>

<!DOCTYPE html>
<html lang="en">

<head>
  <!-- Required meta tags -->
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <title>Juniper Home</title>
  <!-- plugins:css -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/iconfonts/mdi/css/materialdesignicons.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/vendor.bundle.base.css">
  <!-- endinject -->
  <!-- inject:css -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style2.css">
  <!-- endinject -->
  <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/img/favicon.ico" />
  <!-- Include multi.js -->
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/multi.min.css">
  <script src="${pageContext.request.contextPath}/assets/js/multi.min.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script type="text/javascript">
  function tog(ids)
  {
	  if(ids=="max")
	{
	  document.getElementById("min").style.display="block";
	  document.getElementById("max").style.display="none";
	}
	  if(ids=="min")
	{
		document.getElementById("max").style.display="block";
		document.getElementById("min").style.display="none";
	}
  }
  function multisel(src_id,tgt_id)
  {
		var el = document.getElementById(src_id);
		var result = "";
		var options = el.options;
		var opt;
		for (var i = 0, iLen = options.length; i < iLen; i++) {
			opt = options[i];
			if (opt.selected) {
				if(opt.value==="*")
				{
					result="";
					for (var j = 0, jLen = options.length; j < jLen; j++) {
						opt1 = options[j];
						if(opt1.value!="*")
						{
							result=result+","+opt1.value;
						}
					}
					break;
				}
				else
				{
					result=result+","+opt.value;
				}
			}
		}
		result=result.substring(1);
		document.getElementById(tgt_id).value=result;
  }
  
  function multisel1(src_id,tgt_id)
  {
		var el = document.getElementById(src_id);
		var result = "";
		var options = el.options;
		var opt;
		for (var i = 0, iLen = options.length; i < iLen; i++) {
			opt = options[i];
			if (opt.selected) {
				if(opt.value==="*")
				{
					result="*";
					break;
				}
				else
				{
					result=result+","+opt.value;
				}
			}
		}
		if(result!="*") {
			result=result.substring(1);
		}
		document.getElementById(tgt_id).value=result;
  } 
  
  
  $(document).ready(function() {
	
		 $("#projects").change(function() {

				var project = $(this).val();
	
				 $.post('${pageContext.request.contextPath}/login/features', {
					project : project
				}, function(data) {
					//alert(data);
					
					window.location.href="${pageContext.request.contextPath}/login/dashboard";
				}); 
	 
		}); 
	  
  });
  
  </script>
   <style>
.cust {
  width:95%;
  margin:5px;
  padding:0.875rem 0.5rem;
}
  
.row1 {
  display: flex;
  flex-wrap: wrap;
  padding: 0 4px;
}

/* Create four equal columns that sits next to each other */
.column1,.column2 {
  flex: 16.66%;
  max-width: 16.66%;
  padding: 0 5px;
}

.column1 img {
  margin-top: 8px;
  vertical-align: middle;
  width: 150px;
  height: 175px;
  border: 1px solid black;
}
.column2 img {
  margin-top: 8px;
  vertical-align: middle;
  width: 150px;
  border: 1px solid black;
}
/* Responsive layout - makes a two column-layout instead of four columns */
@media screen and (max-width: 800px) {
  .column1,.column2 {
    flex: 50%;
    max-width: 50%;
  }
}
/* Responsive layout - makes the two columns stack on top of each other instead of next to each other */
@media screen and (max-width: 600px) {
  .column1,.column2{
    flex: 100%;
    max-width: 100%;
  }
}
#bgDiv {
  position:fixed;
  top:0px;
  bottom:0px;
  left:260px;
  right:0px;
  overflow:hidden;
  padding:0;
  margin:0;
  background-color:white;
  filter:alpha(opacity=25);
  opacity:0.25;
  z-index:1000;
}

.fs {
  border:1px groove #DCDCDC;
  padding:10px;
  margin:10px;
  border-radius:10px;
}
.leg {
  font-size:0.8em;
  font-weight:bold;
}
.grid-container {
	display: grid;
	height: 100%;
	grid-template-columns: 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr
		1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr
		1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr
		1fr 1fr 1fr 1fr;
	grid-template-rows: 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr
		1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr
		1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr
		1fr 1fr 1fr;
	grid-gap: 0.5px 0.5px;
}

@media all and (-ms-high-contrast: none) {
	.grid-container {
		display: -ms-grid;
		-ms-grid-columns: 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr
			1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr
			1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr
			1fr 1fr 1fr;
		-ms-grid-rows: 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr
			1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr
			1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr
			1fr 1fr;
	}
}
</style>
<script>
	function allowDrop(ev) {
		ev.preventDefault();
	}
	function drag(ev) {
		ev.dataTransfer.setData("text", ev.target.id);
	}
	function drop(ev,el) {
		ev.preventDefault();
		var data = ev.dataTransfer.getData("text");
		el.appendChild(document.getElementById(data));
	}
	function load() {
		var x = document.getElementsByClassName("grid-container")[0];
		var y = "";
		var z1 = "";
		var z2 = "";
		for (var i = 0; i < 10; i++) {
			for (var j = 0; j < 500; j = j + 10) {
				y = y
						+ "<div id='"
						+ (i + j)
						+ "' name='"
						+ (i + j)
						+ "' class='form-control customgrid' ondrop='drop(event,this)' ondragover='allowDrop(event)' style='min-width:100px;height:50px;padding:0px;'></div>";
			}
		}
		for (var k = 1; k <= 50; k++) {
			z1 = z1
					+ "<div style='text-align:center;min-width:100px;height:25px;font-weight:bold;margin-top:20px;'>"
					+ k + "</div>";
			z2 = z2
					+ "<div style='text-align:center;min-width:100px;height:25px;font-weight:bold;'>"
					+ k + "</div>";
		}
		x.innerHTML = z1 + y + z2;
	}
	function get_seq() {
		var csv = "", divid = "", curr = "";
		var arr = [ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
				"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
				"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
				"", "", "" ]
		for (var i = 0; i < 500; i++) {
			divid = document.getElementsByClassName("customgrid")[i];
			if (divid.innerHTML != "") {
				eleid = divid.innerHTML.split('id="')[1].split('"')[0];
				arr[Math.trunc(divid.id / 10)] += eleid + ",";
			}
		}
		for (var j = arr.length - 1; j >= 0; j--) {
			if (arr[j] === "") {
				arr.splice(j, 1);
			} else {
				arr[j] = arr[j].slice(0, -1);
			}
		}
		for (var k = 0; k < arr.length; k++) {
			if (k == 0) {
				csv += arr[k];
			} else {
				var temp1 = arr[k].split(",");
				var temp2 = curr.split(",");
				for (var x = 0; x < temp1.length; x++) {
					for (var y = 0; y < temp2.length; y++) {
						csv += "|" + temp1[x] + "," + temp2[y];
					}
				}
			}
			curr = arr[k];
		}
		document.getElementById("sequence").value = csv;
	}
</script>
</head>
<body onload="load();">
  <div class="container-scroller">
    <!-- partial:partials/_navbar.html -->
    <nav class="navbar default-layout-navbar col-lg-12 col-12 p-0 fixed-top d-flex flex-row">
      <div class="text-center navbar-brand-wrapper d-flex align-items-center justify-content-center">
       <a class="navbar-brand brand-logo" href="${pageContext.request.contextPath}/parent"><img src="${pageContext.request.contextPath}/assets/img/juniper.jpg" alt="logo"/></a>
        <a class="navbar-brand brand-logo-mini" href="${pageContext.request.contextPath}/parent"><img src="${pageContext.request.contextPath}/assets/img/junipersmall.jpg" alt="logo"/></a>
      </div>
      <div class="navbar-menu-wrapper d-flex align-items-stretch">
      	<div class="navbar-nav navbar-nav-left">
			
        </div>
        <ul class="navbar-nav navbar-nav-right">
          <li class="nav-item nav-profile dropdown">
            <a class="nav-link dropdown-toggle" id="profileDropdown" href="#" data-toggle="dropdown" aria-expanded="false">
              <div class="nav-profile-img">
              
           <c:choose>
			    <c:when test="${user_name=='admin'}"><img src="${pageContext.request.contextPath}/assets/img/faces/jiten.jpg" alt="image"></c:when>
			    <c:when test="${user_name=='vaibhav'}"><img src="${pageContext.request.contextPath}/assets/img/faces/face.png" alt="image"></c:when>
			    <c:when test="${user_name=='abhishek'}"><img src="${pageContext.request.contextPath}/assets/img/faces/face.png" alt="image"></c:when>
			    <c:when test="${user_name=='test'}"><img src="${pageContext.request.contextPath}/assets/img/faces/face.png" alt="image"></c:when>
			    <c:otherwise><img src="${pageContext.request.contextPath}/assets/img/faces/face.png" alt="image"></c:otherwise>
			</c:choose>
			 
                <span class="availability-status online"></span>             
              </div>
              <div class="nav-profile-text">
                <p class="mb-1 text-black">${user_name}</p>
              </div>
            </a>
            <div class="dropdown-menu navbar-dropdown" aria-labelledby="profileDropdown">
              <a class="dropdown-item" href="#">
                <i class="mdi mdi-account-circle mr-2 text-success"></i>
                Profile
              </a>
              <div class="dropdown-divider"></div>
             <a class="dropdown-item" href="${pageContext.request.contextPath}/logout">
                <i class="mdi mdi-logout mr-2 text-primary"></i>
                Logout
              </a>
            </div>
          </li>
          <li class="nav-item d-none d-lg-block full-screen-link">
            <a class="nav-link">
              <i class="mdi mdi-fullscreen" id="fullscreen-button"></i>
            </a>
          </li>
          <li class="nav-item nav-logout d-none d-lg-block">
           <a class="nav-link" href="${pageContext.request.contextPath}/logout">
              <i class="mdi mdi-power"></i>
            </a> 
          </li>
        </ul>
        <button class="navbar-toggler navbar-toggler-right d-lg-none align-self-center" type="button" data-toggle="offcanvas">
          <span class="mdi mdi-menu"></span>
        </button>
      </div>
    </nav>
    <!-- partial -->
    <div class="container-fluid page-body-wrapper">
      <!-- partial:partials/_sidebar.html -->
      <nav class="sidebar sidebar-offcanvas" id="sidebar">
        <ul class="nav">
          <li class="nav-item nav-profile">
            <a href="#" class="nav-link">
              <div class="nav-profile-image">
                <c:choose>
			   <c:when test="${user_name=='admin'}"><img src="${pageContext.request.contextPath}/assets/img/faces/jiten.jpg" alt="image"></c:when>
			    <c:when test="${user_name=='vaibhav'}"><img src="${pageContext.request.contextPath}/assets/img/faces/face.png" alt="image"></c:when>
			    <c:when test="${user_name=='abhishek'}"><img src="${pageContext.request.contextPath}/assets/img/faces/face.png" alt="image"></c:when>
			    <c:when test="${user_name=='test'}"><img src="${pageContext.request.contextPath}/assets/img/faces/face.png" alt="image"></c:when>
			    <c:otherwise><img src="${pageContext.request.contextPath}/assets/img/faces/face.png" alt="image"></c:otherwise>
			</c:choose>
                <span class="login-status online"></span> <!--change to offline or busy as needed-->              
              </div>
              <div class="nav-profile-text d-flex flex-column">
                <span class="font-weight-bold mb-2">${user_name}</span>
                <span class="text-secondary text-small">Application User</span>
              </div>
              <i class="mdi mdi-bookmark-check text-success nav-profile-badge"></i>
            </a>
          </li>
          
          <li class="nav-item">
   <a class="nav-link" data-toggle="collapse" href="#scheduler" aria-expanded="false" aria-controls="scheduler"> <span class="menu-title">Scheduler</span> <i class="menu-arrow"></i> <i class="mdi mdi-timer menu-icon"></i> </a> 
   <div class="collapse" id="scheduler">
      <ul class="nav flex-column sub-menu">
      		<li class="nav-item"> <a class="nav-link" href="/scheduler/AddBatch"> Add Batch </a></li>
      		<li class="nav-item"> <a class="nav-link" href="/scheduler/AddTask"> Add Task </a></li>
      		<li class="nav-item"> <a class="nav-link" href="/scheduler/CreateSequence"> Create Sequence </a></li>
			<li class="nav-item"> <a class="nav-link" href="/scheduler/viewAllJobs"> Master Feed Details </a></li>
			<li class="nav-item"> <a class="nav-link" href="/scheduler/scheduledjobs"> Current Feed Details </a></li>
      </ul>
   </div>
</li>
  
        </ul>
      </nav>
