 <jsp:include page="cdg_header.jsp" />
 
 <div class="main-panel">
        <div class="content-wrapper" >
          <div class="row">
            <div class="col-md-4 stretch-card grid-margin">
              <div class="card bg-gradient-warning card-img-holder text-white">
                <div class="card-body">
                  <img src="${pageContext.request.contextPath}/assets/img/circle.svg" class="card-img-absolute" alt="circle-image"/>
                  <h3 class="font-weight-normal mb-3" ><a class="nav-link text-white" href="/scheduler/viewAllJobs"> Master Feed Details </a></h3>
                 </div>
              </div>
            </div>
			
			
            <div class="col-md-4 stretch-card grid-margin">
              <div class="card bg-gradient-success card-img-holder text-white">
                <div class="card-body">
                  <img src="${pageContext.request.contextPath}/assets/img/circle.svg" class="card-img-absolute" alt="circle-image"/>
                  <h3 class="font-weight-normal mb-3"><a class="nav-link text-white" href="/scheduler/scheduledjobs"> Current Feed Details </a></h3>
                 
                </div>
              </div>
            </div>

			
			
                      </div>

          </div>

<jsp:include page="cdg_footer.jsp" />