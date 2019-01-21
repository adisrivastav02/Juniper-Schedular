/**
 * 
 */

package com.iig.gcp.scheduler.schedulerController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
//import com.iig.gcp.extraction.dto.ConnectionMaster;
//import com.iig.gcp.extraction.dto.DriveMaster;
import com.iig.gcp.scheduler.schedulerController.dto.*;
import com.iig.gcp.scheduler.schedulerController.utils.*;
import com.iig.gcp.scheduler.schedulerService.*;

@Controller
@SessionAttributes(value= {"user_name","project_name","jwt"})
public class SchedularController {
	
	@Value("${parent.front.micro.services}")
	private static String parent_ms;

	@Autowired
	SchedularService schedularService;
	
	@Autowired
    private AuthenticationManager authenticationManager;
	
	// Master Table

		/**
		 * 
		 * @param modelMap
		 * @return
		 */
		@RequestMapping(value = { "/" }, method = RequestMethod.GET)
		public ModelAndView homePage(@Valid @ModelAttribute("jsonObject") String jsonObject,ModelMap modelMap,HttpServletRequest request) {
			//Validate the token at the first place
			
			
			if(jsonObject== null || jsonObject.equals("")) {
				//TODO: Redirect to Access Denied Page
				return new ModelAndView("/login");
			}
			
			JSONObject jObj = new JSONObject(jsonObject);
			String user_name=jObj.getString("user");
			String project_name=jObj.getString("project");
			String jwt=jObj.getString("jwt");
			
			try {
			JSONObject jsonModelObject = null;
			if(modelMap.get("jsonObject")== null || modelMap.get("jsonObject").equals("")) {
				//TODO: Redirect to Access Denied Page
				return new ModelAndView("/login");
			}
			jsonModelObject = new JSONObject( modelMap.get("jsonObject").toString());
			
			authenticationByJWT(user_name+":"+project_name, jsonModelObject.get("jwt").toString());
			}
			catch(Exception e) {
				e.printStackTrace();
				return new ModelAndView("/login");
				//redirect to Login Page
			}
			
			
			request.getSession().setAttribute("user_name", user_name);
			request.getSession().setAttribute("project_name", project_name);
			request.getSession().setAttribute("jwt", jwt);
			
			return new ModelAndView("/index");
		}
		
		private void authenticationByJWT(String name, String token) {
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(name, token);
	        Authentication authenticate = authenticationManager.authenticate(authToken);
	        SecurityContextHolder.getContext().setAuthentication(authenticate);
		}
		
		/**
		 * 
		 * @param modelMap
		 * @return
		 */
		@RequestMapping(value = { "/scheduler/viewAllJobs" }, method = RequestMethod.GET)
		public ModelAndView allJobs(ModelMap modelMap,HttpServletRequest request) {
			try {
				
			
				HashMap<String, List<MasterJobsDTO>> hsMap = new HashMap<String, List<MasterJobsDTO>>();
				hsMap.put("ALL", schedularService.allLoadJobs((String)request.getSession().getAttribute("project_name")));
				ArrayList<String> arrfeedId = schedularService.getFeedFromMaster((String)request.getSession().getAttribute("project_name"));
				modelMap.addAttribute("arrfeedId", arrfeedId);
				modelMap.addAttribute("allLoadJobs", hsMap.get("ALL"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return new ModelAndView("/schedular/viewAllJobs");
		}
		


	/**
	 * This method populates the View Run Statics Screen with FeedID
	 * 
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = { "/scheduler/runstats" }, method = RequestMethod.GET)
	public ModelAndView viewRunStatics(ModelMap modelMap) {
		try {
			ArrayList<String> arrFeedId = schedularService.getFeedIdList();
			modelMap.addAttribute("feed_id", arrFeedId);
		} catch (Exception e) {
			modelMap.addAttribute("errorStatus", e.getMessage());
		}
		return new ModelAndView("schedular/viewRunStatics");
	}

	/**
	 * This method first fetch MasterFeed data and insert into the CurrentFeed
	 * 
	 * @param feed_id
	 * @param job_id
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = { "/scheduler/runMasterJob" }, method = RequestMethod.POST)
	public ModelAndView moveJobFromMasterToCurrent(@Valid @RequestParam("feedId") String feedId, ModelMap modelMap,HttpServletRequest request) {
		try {
				String message = schedularService.moveJobFromMasterToCurrentJob(feedId);
				if (message.equals("Success")) {
					modelMap.addAttribute("successString", "Job Ordered for today");
				} else {
					modelMap.addAttribute("errorStatus", "Job ordering failed");
				}
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorStatus", e.getMessage());
		}
		return allJobs(modelMap,request);
	}

	/**
	 * This method suspends the job in master table.
	 * 
	 * @param feed_id
	 * @param job_id
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = { "/scheduler/suspendMasterJob" }, method = RequestMethod.POST)
	public  ModelAndView suspendJobFromMaster(@Valid @RequestParam("feedId") String feedId,
			ModelMap modelMap,HttpServletRequest request) {
		try {
			String suspendStatus = schedularService.suspendJobFromMaster(feedId);
			if (suspendStatus.equals("Success")) {
				modelMap.addAttribute("successString", "Job Suspended");
			} else {
				modelMap.addAttribute("errorStatus", "Job Suspense failure");
			}
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorStatus", e.getMessage());
		}
		return allJobs(modelMap,request);
	}
	
	
	/**
	 * This method unsuspends the job in master table.
	 * 
	 * @param feed_id
	 * @param job_id
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = { "/scheduler/unSuspendMasterJob" }, method = RequestMethod.POST)
	public  ModelAndView unSuspendJobFromMaster(@Valid @RequestParam("feedId") String feedId,
			ModelMap modelMap,HttpServletRequest request) {
		try {
			String suspendStatus = schedularService.unSuspendJobFromMaster(feedId);
			if (suspendStatus.equals("Success")) {
				modelMap.addAttribute("successString", "Job Suspended");
			} else {
				modelMap.addAttribute("errorStatus", "Job Suspense failure");
			}
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorStatus", e.getMessage());
		}
		return allJobs(modelMap,request);
	}

	/**
	 * This method deletes the record from MasterFeed data
	 * 
	 * @param feed_id
	 * @param job_id
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = { "/scheduler/deleteMasterJob" }, method = RequestMethod.POST)
	public ModelAndView deleteJobFromMaster(@Valid @RequestParam("feedId") String feedId,
			ModelMap modelMap) {
		try {
			String message = schedularService.deleteJobFromMaster(feedId);
			modelMap.addAttribute("successString", "Job deleted");

		} catch (Exception e) {

			modelMap.addAttribute("errorStatus", e.getMessage());

		}
		return new ModelAndView("schedular/viewAllJobs1");
	}

	/**
	 * This method populated the View Run Statics Screen with list of Job Id w.r.t.
	 * feedid
	 * 
	 * @param feed_id
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = { "/schedule/feedIdFilter" }, method = RequestMethod.POST)
	public ModelAndView getJobIdFilter(@Valid @RequestParam("feed_id") String feed_id, ModelMap modelMap) {
		try {
			ArrayList<ArchiveJobsDTO> arrArchiveJobs = schedularService.getListOfArchievJobs(feed_id);
			ArrayList<String> arrJobId = new ArrayList<String>();
			for (ArchiveJobsDTO archiveJob : arrArchiveJobs) {
				arrJobId.add(archiveJob.getJob_id());
			}
			modelMap.addAttribute("arrJobId", arrJobId);
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorStatus", e.getMessage());
		}
		return new ModelAndView("schedular/feedIdFilter");
	}

	@RequestMapping(value = { "/schedule/jobIdFilter" }, method = RequestMethod.POST)
	public ModelAndView getAreachart(@Valid @RequestParam("job_id") String job_id,
			@Valid @RequestParam("feed_id") String feed_id, ModelMap modelMap) {
		ModelAndView model = new ModelAndView("schedular/populateChart");

		try {
			ArrayList<ArchiveJobsDTO> arrChartDetails = schedularService.getChartDetails(job_id);
			ArrayList<String> arrBatchDate = new ArrayList<String>();
			ArrayList<String> arrDuration = new ArrayList<String>();

			ObjectMapper mapper = new ObjectMapper();

			for (ArchiveJobsDTO archiveJob : arrChartDetails) {
				// System.out.println("Job Id:"+archiveJob.getDuration());
				arrBatchDate.add(archiveJob.getBatch_date().toString());
				arrDuration.add(archiveJob.getDuration());
			}

			String json = mapper.writeValueAsString(arrBatchDate);
			//System.out.println(" arrDuration" + arrDuration);
			modelMap.addAttribute("x", json.toString());
			modelMap.addAttribute("y", arrDuration.toString());
			List<ArchiveJobsDTO> arrArchiveTable = schedularService.getRunStats(job_id, feed_id);
			modelMap.addAttribute("allLoadJobs", arrArchiveTable);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;

	}

	
	/**
	 * 
	 * @param frequency
	 * @param batchId
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = { "/scheduler/frequency" }, method = RequestMethod.POST)
	public ModelAndView frequencySelect(@Valid @RequestParam("frequency") String frequency,
			@RequestParam("batchId") String batchId, ModelMap modelMap) {
		try {

			List<MasterJobsDTO> dtos = schedularService.typeLoadJobs(frequency, batchId);
			modelMap.addAttribute("allLoadJobs", dtos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("schedular/viewAllJobs1");
	}

	/**
	 * 
	 * @param strFrequency
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = { "/scheduler/batchid" }, method = RequestMethod.POST)

	public ModelAndView batchIdSelect(@Valid @RequestParam("frequency") String frequency,
			@RequestParam("batchId") String batchId, ModelMap modelMap) {
		try {
			List<MasterJobsDTO> dtos = schedularService.typeLoadJobs(frequency, batchId);
			modelMap.addAttribute("allLoadJobs", dtos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("schedular/viewAllJobs1");
	}

	// Current Table
	@RequestMapping(value = { "/scheduler/scheduledjobs" }, method = RequestMethod.GET)
	public ModelAndView allCurrentJobs(ModelMap modelMap,HttpServletRequest request) {
		try {
			HashMap<String, List<DailyJobsDTO>> hsMap = new HashMap<String, List<DailyJobsDTO>>();
			hsMap.put("ALL", schedularService.allCurrentJobs((String)request.getSession().getAttribute("project_name")));
			ArrayList<String> arrfeedId = schedularService.getFeedFromCurrent((String)request.getSession().getAttribute("project_name"));
			modelMap.addAttribute("arrfeedId", arrfeedId);
			modelMap.addAttribute("user_id", (String)request.getSession().getAttribute("user_name"));
			modelMap.addAttribute("allLoadJobs", hsMap.get("ALL"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("schedular/viewCurrentJobs");
	}

	@RequestMapping(value = { "/scheduler/statusfilter" }, method = RequestMethod.POST)
	public ModelAndView statusFilter(@Valid @RequestParam("status") String status,
			@RequestParam("feedid") String feedid, ModelMap modelMap) {
		try {

			List<DailyJobsDTO> dtos = schedularService.filterCurrentJobs(status, feedid);
			modelMap.addAttribute("allLoadJobs", dtos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("schedular/viewCurrentJobs1");
	}

	@RequestMapping(value = { "/scheduler/feedfilter" }, method = RequestMethod.POST)
	public ModelAndView feedFilter(@Valid @RequestParam("status") String status, @RequestParam("feedid") String feedid,
			ModelMap modelMap) {
		try {

			List<DailyJobsDTO> dtos = schedularService.filterCurrentJobs(status, feedid);
			modelMap.addAttribute("allLoadJobs", dtos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("schedular/viewCurrentJobs1");
	}

	@RequestMapping(value = { "/scheduler/runScheduleJob" }, method = RequestMethod.POST)
	public ModelAndView runScheduleJob(@Valid @RequestParam("feedId") String feedId,
			@RequestParam("jobId") String jobId, @RequestParam("batchDate") String batchDate, ModelMap modelMap) {
		try {
			String message = schedularService.runScheduleJob(feedId, jobId, batchDate);
			modelMap.addAttribute("successString", message);
		} catch (Exception e) {

			modelMap.addAttribute("errorStatus", e.getMessage());

		}
		return new ModelAndView("schedular/viewCurrentJobs1");
	}

	@RequestMapping(value = { "/scheduler/stopScheduleJob" }, method = RequestMethod.POST)
	public ModelAndView killCurrentJob(@Valid @RequestParam("feedId") String feedId,
			@RequestParam("jobId") String jobId, @RequestParam("batchDate") String batchDate, ModelMap modelMap) {
		try {
			String message = schedularService.killCurrentJob(feedId, jobId, batchDate);
			modelMap.addAttribute("successString", message);
		} catch (Exception e) {
			modelMap.addAttribute("errorStatus", e.getMessage());

		}
		return new ModelAndView("schedular/viewCurrentJobs1");
	}
	/*
	 * @RequestMapping(value = { "/scheduler/scheduledjobs"}, method =
	 * RequestMethod.GET) public ModelAndView scheduledJobs(ModelMap modelMap) { try
	 * { HashMap<String,List<DailyJobsDTO>> hsMap =
	 * schedularService.allCurrentJobs(); //HashMap<String, ArrayList<String>>
	 * hsMapCount = schedularService.allCurrentJobsGroupByFeedId();
	 * 
	 * System.out.println("Key"+hsMapCount.get("arrkey"));
	 * 
	 * System.out.println("value"+hsMapCount.get("arrValue")); int
	 * totalCount=hsMap.get("completed").size()+hsMap.get("failed").size()+
	 * hsMap.get("notstarted").size();
	 * modelMap.addAttribute("arrCompletedJobs",hsMap.get("completed"));
	 * modelMap.addAttribute("arrFailedJobs", hsMap.get("failed"));
	 * modelMap.addAttribute("arrNotStartedJobs", hsMap.get("notstarted"));
	 * modelMap.addAttribute("totalCount", totalCount);
	 * modelMap.addAttribute("hskeys", hsMapCount.get("arrkey"));
	 * modelMap.addAttribute("takeVals", hsMapCount.get("arrValue"));
	 * 
	 * }catch(Exception e) { e.printStackTrace(); } return new
	 * ModelAndView("schedular/viewScheduledjobs"); }
	 */
	
	@RequestMapping(value = { "/scheduler/AddTask" }, method = RequestMethod.GET)
	public ModelAndView AddTask(
			 ModelMap modelMap) {
		try {
			String message="Reached the add task controller block";
		} catch (Exception e) {
			//modelMap.addAttribute("errorStatus", e.getMessage());

		}
		return new ModelAndView("schedular/AddTask");
	}
	
	
	@RequestMapping(value = { "/scheduler/AddBatch" }, method = RequestMethod.GET)
	public ModelAndView AddBatch1(@Valid ModelMap modelMap) {
		try {
			String message="Reached the add batch controller block Post";
		} catch (Exception e) {

		}
		return new ModelAndView("schedular/AddBatch");
	}
	
	@RequestMapping(value = { "/scheduler/ListTask" }, method = RequestMethod.GET)
	public ModelAndView ListTask(
			 ModelMap modelMap) {
		try {
			String message="Reached the l task controller block";
			//System.out.println(message);
			//String message = schedularService.killCurrentJob(feedId, jobId, batchDate);
			//modelMap.addAttribute("successString", message);
		} catch (Exception e) {
			//modelMap.addAttribute("errorStatus", e.getMessage());

		}
		return new ModelAndView("schedular/ListTask");
	}

/*	@RequestMapping(value = { "/"}, method = RequestMethod.GET)
	public ModelAndView onBoardProject(@Valid @ModelAttribute("jsonObject") String jsonObject,ModelMap modelMap,HttpServletRequest request) {
		List<String> projects=null;
		JSONObject jObj = new JSONObject(jsonObject);
		usr=jObj.getString("user");
		proj=jObj.getString("project");
		modelMap.addAttribute("proj_val", projects);
		return new ModelAndView("/index");
	}*/
	
	@RequestMapping(value = { "/schedular/error"}, method = RequestMethod.GET)
	public ModelAndView error(ModelMap modelMap,HttpServletRequest request) {
		return new ModelAndView("/index");
	}
	
	@RequestMapping(value = { "/schedular/logout"}, method = RequestMethod.GET)
	public ModelAndView logout(ModelMap modelMap,HttpServletRequest request) {
		request.getSession().invalidate();
		return new ModelAndView("redirect:" + parent_ms);
	}
}
