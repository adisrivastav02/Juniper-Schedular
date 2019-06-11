/**
 * 
 */

package com.iig.gcp.scheduler.schedulerController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.json.JSONException;
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
import com.iig.gcp.CustomAuthenticationProvider;
//import com.iig.gcp.extraction.dto.ConnectionMaster;
//import com.iig.gcp.extraction.dto.DriveMaster;
import com.iig.gcp.scheduler.schedulerController.dto.*;
import com.iig.gcp.scheduler.schedulerController.utils.*;
import com.iig.gcp.scheduler.schedulerService.*;

@Controller
@SessionAttributes(value = { "user_name", "project_name", "jwt" })
public class SchedularController {

	@Value("${parent.front.micro.services}")
	private String parent_ms;

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
	public ModelAndView homePage(@Valid @ModelAttribute("jsonObject") String jsonObject, ModelMap modelMap,
			HttpServletRequest request) {
		// Validate the token at the first place

		if (jsonObject == null || jsonObject.equals("")) {
			// TODO: Redirect to Access Denied Page
			return new ModelAndView("/login");
		}

		JSONObject jObj = new JSONObject(jsonObject);
		String user_name = jObj.getString("user");
		String project_name = jObj.getString("project");
		String jwt = jObj.getString("jwt");

		try {
			JSONObject jsonModelObject = null;
			if (modelMap.get("jsonObject") == null || modelMap.get("jsonObject").equals("")) {
				// TODO: Redirect to Access Denied Page
				return new ModelAndView("/login");
			}
			jsonModelObject = new JSONObject(modelMap.get("jsonObject").toString());

			authenticationByJWT(user_name + ":" + project_name, jsonModelObject.get("jwt").toString());
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("/login");
			// redirect to Login Page
		}

		request.getSession().setAttribute("user_name", user_name);
		request.getSession().setAttribute("project_name", project_name);
		request.getSession().setAttribute("jwt", jwt);

		return new ModelAndView("/index");
	}

	@RequestMapping(value = { "/parent" }, method = RequestMethod.GET)
	public ModelAndView parentHome(ModelMap modelMap, HttpServletRequest request, Authentication auth)
			throws JSONException {
		CustomAuthenticationProvider.MyUser m = (CustomAuthenticationProvider.MyUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("userId", m.getName());
		jsonObject.put("project", m.getProject());
		jsonObject.put("jwt", m.getJwt());
		modelMap.addAttribute("jsonObject", jsonObject.toString());
		return new ModelAndView("redirect:" + "//" + parent_ms + "/fromChild", modelMap);

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
	public ModelAndView allJobs(ModelMap modelMap, HttpServletRequest request) {
		try {

			HashMap<String, List<MasterJobsDTO>> hsMap = new HashMap<String, List<MasterJobsDTO>>();
			hsMap.put("ALL", schedularService.allLoadJobs((String) request.getSession().getAttribute("project_name")));
			ArrayList<String> arrfeedId = schedularService
					.getFeedFromMaster((String) request.getSession().getAttribute("project_name"));
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
	public ModelAndView moveJobFromMasterToCurrent(@Valid @RequestParam("feedId") String feedId, ModelMap modelMap,
			HttpServletRequest request) {
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
		return allJobs(modelMap, request);
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
	public ModelAndView suspendJobFromMaster(@Valid @RequestParam("feedId") String feedId, ModelMap modelMap,
			HttpServletRequest request) {
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
		return allJobs(modelMap, request);
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
	public ModelAndView unSuspendJobFromMaster(@Valid @RequestParam("feedId") String feedId, ModelMap modelMap,
			HttpServletRequest request) {
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
		return allJobs(modelMap, request);
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
	public ModelAndView deleteJobFromMaster(@Valid @RequestParam("feedId") String feedId, ModelMap modelMap) {
		try {
			schedularService.deleteJobFromMaster(feedId);
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
				arrBatchDate.add(archiveJob.getBatch_date().toString());
				arrDuration.add(archiveJob.getDuration());
			}

			String json = mapper.writeValueAsString(arrBatchDate);
			modelMap.addAttribute("x", json.toString());
			modelMap.addAttribute("y", arrDuration.toString());
			List<ArchiveJobsDTO> arrArchiveTable = schedularService.getRunStats(job_id, feed_id);
			modelMap.addAttribute("allLoadJobs", arrArchiveTable);
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorStatus", e.getMessage());
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
	public ModelAndView allCurrentJobs(ModelMap modelMap, HttpServletRequest request) {
		try {
			HashMap<String, List<DailyJobsDTO>> hsMap = new HashMap<String, List<DailyJobsDTO>>();
			hsMap.put("ALL",
					schedularService.allCurrentJobs((String) request.getSession().getAttribute("project_name")));
			ArrayList<String> arrfeedId = schedularService
					.getFeedFromCurrent((String) request.getSession().getAttribute("project_name"));
			modelMap.addAttribute("arrfeedId", arrfeedId);
			modelMap.addAttribute("user_id", (String) request.getSession().getAttribute("user_name"));
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

	@RequestMapping(value = { "/schedular/error" }, method = RequestMethod.GET)
	public ModelAndView error(ModelMap modelMap, HttpServletRequest request) {
		return new ModelAndView("/index");
	}

	@RequestMapping(value = { "/scheduler/AddTask" }, method = RequestMethod.GET)
	public ModelAndView AddTask(ModelMap modelMap, HttpServletRequest request) {
		try {
			String project_id = (String) request.getSession().getAttribute("project_name");
			ArrayList<BatchDetailsDTO> batch_val1 = schedularService.getCreateBatchDetails(project_id);
			ArrayList<BatchDetailsDTO> batch_val2 = schedularService.getEditBatchDetails(project_id);
			modelMap.addAttribute("batch_val1", batch_val1);
			modelMap.addAttribute("batch_val2", batch_val2);
			ArrayList<String> tproj = schedularService
					.getGoogleProject((String) request.getSession().getAttribute("project_name"));
			modelMap.addAttribute("tproj", tproj);
			ArrayList<String> service_acc = schedularService
					.getServiceAccount((String) request.getSession().getAttribute("project_name"));
			modelMap.addAttribute("service_acc", service_acc);
			ArrayList<String> kafka_topic = schedularService.getKafkaTopic();
			modelMap.addAttribute("kafka_topic", kafka_topic);
		} catch (Exception e) {
			modelMap.addAttribute("errorStatus", e.getMessage());

		}
		modelMap.addAttribute("usernm", request.getSession().getAttribute("user_name"));
		modelMap.addAttribute("project", (String) request.getSession().getAttribute("project_name"));
		return new ModelAndView("schedular/AddTask");
	}

	@RequestMapping(value = "/scheduler/AddTaskSave", method = RequestMethod.POST)
	public ModelAndView AddTaskSave(@Valid @ModelAttribute("x") String x, @ModelAttribute("src_val") String src_val,
			@ModelAttribute("button_type") String button_type, ModelMap model, HttpServletRequest request,
			ModelMap modelMap) throws UnsupportedOperationException, Exception {
		String resp = null;
		System.out.println("x is " + x);
		try {
			if (button_type.equalsIgnoreCase("create")) {
				resp = schedularService.invokeRest(x, "addScheduleData");
			} else {
				resp = schedularService.invokeRest(x, "editScheduleData");
			}

			String status0[] = resp.toString().split(":");
			String status1[] = status0[1].split(",");
			String status = status1[0].replaceAll("\'", "").trim();
			String message0 = status0[2];
			String message = message0.replaceAll("[\'}]", "").trim();
			String final_message = status + ": " + message;
			if (resp.toLowerCase().contains("success") && button_type.equalsIgnoreCase("create")) {
				modelMap.addAttribute("successString", "Success :Task added successfully");
			} else if (resp.toLowerCase().contains("success") && button_type.equalsIgnoreCase("edit")) {
				modelMap.addAttribute("successString", "Success :Task updated successfully");
			} else {
				if (final_message.contains("ORA-00001")) {
					modelMap.addAttribute("errorString", "Failed :All the job names passed must be unique");
				} else {
					modelMap.addAttribute("errorString", final_message);
				}
			}

			modelMap.addAttribute("usernm", request.getSession().getAttribute("user_name"));
			model.addAttribute("project", (String) request.getSession().getAttribute("project_name"));
			String project_id = (String) request.getSession().getAttribute("project_name");
			ArrayList<BatchDetailsDTO> batch_val1 = schedularService.getCreateBatchDetails(project_id);
			modelMap.addAttribute("batch_val1", batch_val1);
			ArrayList<BatchDetailsDTO> batch_val2 = schedularService.getEditBatchDetails(project_id);
			modelMap.addAttribute("batch_val2", batch_val2);
			ArrayList<String> kafka_topic = schedularService.getKafkaTopic();
			modelMap.addAttribute("kafka_topic", kafka_topic);
			ArrayList<String> tproj = schedularService
					.getGoogleProject((String) request.getSession().getAttribute("project_name"));
			modelMap.addAttribute("tproj", tproj);
			ArrayList<String> service_acc = schedularService
					.getServiceAccount((String) request.getSession().getAttribute("project_name"));
			modelMap.addAttribute("service_acc", service_acc);
		} catch (Exception e) {
			modelMap.addAttribute("errorString", e.getMessage());

		}
		return new ModelAndView("schedular/AddTask");
	}

	@RequestMapping(value = { "/scheduler/AddBatch" }, method = RequestMethod.GET)
	public ModelAndView AddBatch1(@Valid ModelMap model, HttpServletRequest request) {
		try {
			model.addAttribute("usernm", request.getSession().getAttribute("user_name"));
			String project_id = (String) request.getSession().getAttribute("project_name");
			ArrayList<BatchDetailsDTO> batch_val = schedularService.getBatchDetails(project_id);
			model.addAttribute("batch_val", batch_val);
			model.addAttribute("project", (String) request.getSession().getAttribute("project_name"));
			ArrayList<String> kafka_topic = schedularService.getKafkaTopic();
			model.addAttribute("kafka_topic", kafka_topic);
			// String message = schedularService.killCurrentJob(feedId, jobId, batchDate);
			// modelMap.addAttribute("successString", message);
		} catch (Exception e) {
			model.addAttribute("errorStatus", e.getMessage());

		}
		return new ModelAndView("schedular/AddBatch");
	}

	@RequestMapping(value = "/scheduler/AddBatchClick", method = RequestMethod.POST)
	public ModelAndView AddBatchClick(@Valid @ModelAttribute("x") String x, @ModelAttribute("src_val") String src_val,
			@ModelAttribute("button_type") String button_type, ModelMap model, HttpServletRequest request,
			ModelMap modelMap) throws UnsupportedOperationException, Exception {
		String message = "";
		System.out.println("x is "+x);
		try {
			if (button_type.equalsIgnoreCase("add")) {
				message = schedularService.invokeRest(x, "saveBatchDetails");
				if (message.toLowerCase().contains("success")) {
					modelMap.addAttribute("successString", "Batch added successfully");
				} else {
					modelMap.addAttribute("errorString", "Batch name already exits,pass a unique name");
				}
			} else {
				message = schedularService.invokeRest(x, "editBatchDetails");
				if (message.toLowerCase().contains("success")) {
					modelMap.addAttribute("successString", "Batch updated successfully");
				} else {
					modelMap.addAttribute("errorString", message);
				}
			}

			modelMap.addAttribute("usernm", request.getSession().getAttribute("user_name"));
			model.addAttribute("project", (String) request.getSession().getAttribute("project_name"));
			String project_id = (String) request.getSession().getAttribute("project_name");
			ArrayList<BatchDetailsDTO> batch_val = schedularService.getBatchDetails(project_id);
			model.addAttribute("batch_val", batch_val);
			ArrayList<String> kafka_topic = schedularService.getKafkaTopic();
			model.addAttribute("kafka_topic", kafka_topic);
		} catch (Exception e) {
			modelMap.addAttribute("errorString", e.getMessage());

		}
		return new ModelAndView("schedular/AddBatch");
	}

	@RequestMapping(value = { "/scheduler/CreateSequence" }, method = RequestMethod.GET)
	public ModelAndView CreateSequence(@Valid ModelMap modelMap, HttpServletRequest request) {
		try {
			modelMap.addAttribute("usernm", request.getSession().getAttribute("user_name"));
			modelMap.addAttribute("project", (String) request.getSession().getAttribute("project_name"));
			String project_id = (String) request.getSession().getAttribute("project_name");
			ArrayList<BatchDetailsDTO> batch_val1 = schedularService.getAdhocBatchCreateDetails(project_id);
			ArrayList<BatchDetailsDTO> batch_val2 = schedularService.getAdhocBatchEditDetails(project_id);
			modelMap.addAttribute("batch_val1", batch_val1);
			modelMap.addAttribute("batch_val2", batch_val2);

		} catch (Exception e) {
			modelMap.addAttribute("errorStatus", e.getMessage());
		}
		return new ModelAndView("schedular/CreateSequence");
	}

	@RequestMapping(value = "/scheduler/CreateSequence1", method = RequestMethod.POST)
	public ModelAndView CreateSequence1(@Valid @ModelAttribute("batchid") String batchid,
			@Valid @ModelAttribute("project_id") String project_id, ModelMap model)
			throws ClassNotFoundException, SQLException {
		ArrayList<TaskSequenceDTO> arr;
		try {
			arr = schedularService.getJobDetails(batchid, project_id);
			model.addAttribute("daglistdto", arr);
			model.addAttribute("5", "TaskTest");
		} catch (Exception e) {
			model.addAttribute("errorStatus", e.getMessage());
		}
		return new ModelAndView("schedular/CreateSequence1");
	}

	@RequestMapping(value = "/scheduler/EditSequence", method = RequestMethod.POST)
	public ModelAndView EditSequence(@Valid @ModelAttribute("batchid") String batchid,
			@Valid @ModelAttribute("project_id") String project_id, ModelMap model)
			throws ClassNotFoundException, SQLException {

		try {
			String xtest = "";
			xtest = schedularService.getBatchSequence(batchid, project_id, null, 0);
			System.out.println("xtest is " + xtest);
			model.addAttribute("xtest", xtest);

		} catch (Exception e) {
			model.addAttribute("errorStatus", e.getMessage());
		}
		return new ModelAndView("schedular/EditSequence");
	}

	@RequestMapping(value = "/scheduler/CreateSequenceSubmit", method = RequestMethod.POST)
	public ModelAndView CreateSequenceSubmit(@Valid @ModelAttribute("x") String x, ModelMap modelMap,
			HttpServletRequest request) throws UnsupportedOperationException, Exception {
		try {

			String resp = schedularService.invokeRest(x, "sequenceSubmit");
			String status0[] = resp.toString().split(":");
			String status1[] = status0[1].split(",");
			String status = status1[0].replaceAll("\'", "").trim().replaceAll("\"", "");
			String message0 = status0[2];
			String message = message0.replaceAll("[\'}]", "").trim().replaceAll("\"", "");
			String final_message = status + ": " + message;
			if (resp.toLowerCase().contains("success")) {
				modelMap.addAttribute("successString", final_message);
			} else {
				modelMap.addAttribute("errorString", final_message);
			}
			modelMap.addAttribute("usernm", request.getSession().getAttribute("user_name"));
			modelMap.addAttribute("project", (String) request.getSession().getAttribute("project_name"));
			String project_id = (String) request.getSession().getAttribute("project_name");
			ArrayList<BatchDetailsDTO> batch_val1 = schedularService.getAdhocBatchCreateDetails(project_id);
			ArrayList<BatchDetailsDTO> batch_val2 = schedularService.getAdhocBatchEditDetails(project_id);
			modelMap.addAttribute("batch_val1", batch_val1);
			modelMap.addAttribute("batch_val2", batch_val2);
		} catch (Exception e) {
			modelMap.addAttribute("errorString", e.getMessage());
		}
		return new ModelAndView("schedular/CreateSequence");
	}

	@RequestMapping(value = "scheduler/BatchEdit", method = RequestMethod.POST)
	public ModelAndView BatchEdit(@Valid @ModelAttribute("batch_id") String batch_id,
			@Valid @ModelAttribute("project_id") String project_id, ModelMap model, HttpServletRequest request)
			throws UnsupportedOperationException, Exception {
		try {
			String adhoc_flag = "";
			String regular_flag = "";
			String event_flag = "";

			BatchTableDetailsDTO batchArr = schedularService.extractBatchDetails(batch_id, project_id);
			if (batchArr.getSCHEDULE_TYPE().contains("R") && batchArr.getDAILY_FLAG() != null) {
				adhoc_flag = "The batch is regular type and scheduled everyday at " + batchArr.getJOB_SCHEDULE_TIME();
			} else if (batchArr.getSCHEDULE_TYPE().contains("R") && batchArr.getHOURLY_FLAG() != null) {
				adhoc_flag = "The batch is regular type and scheduled every hour at " + batchArr.getJOB_SCHEDULE_TIME();
			}
			else if (batchArr.getSCHEDULE_TYPE().contains("R") && batchArr.getWEEKLY_FLAG() != null) {
				adhoc_flag = "The batch is regular type and scheduled everyday at " + batchArr.getJOB_SCHEDULE_TIME()
						+ " on every " + batchArr.getWEEK_RUN_DAY();
			} else if (batchArr.getSCHEDULE_TYPE().contains("R") && batchArr.getMONTHLY_FLAG() != null) {
				adhoc_flag = "The batch is regular type and scheduled everyday at " + batchArr.getJOB_SCHEDULE_TIME()
						+ " on every " + batchArr.getMONTH_RUN_DAY() + " of " + batchArr.getMONTH_RUN_VAL();
			} else if (batchArr.getSCHEDULE_TYPE().contains("O") && batchArr.getArgument_4() == null) {
				adhoc_flag = "The batch is adhoc type and scheduled everyday at 00:00";
			} else if (batchArr.getSCHEDULE_TYPE().contains("F") && batchArr.getArgument_4() != null) {
				adhoc_flag = "The batch is event based type and scheduled everyday at 00:00 with filewatcher as "
						+ batchArr.getArgument_4();
			} else if (batchArr.getSCHEDULE_TYPE().contains("K") && batchArr.getArgument_4() != null) {
				adhoc_flag = "The batch is event based type and scheduled everyday at 00:00 with Kafka topic as "
						+ batchArr.getArgument_4();
			} else if (batchArr.getSCHEDULE_TYPE().contains("A") && batchArr.getArgument_4() != null) {
				adhoc_flag = "The batch is event based type and scheduled everyday at 00:00 with API value as "
						+ batchArr.getArgument_4();
			}
			model.addAttribute("adhoc_flag", adhoc_flag);
			model.addAttribute("regular_flag", regular_flag);
			model.addAttribute("event_flag", event_flag);
			model.addAttribute("batchArr", batchArr);
			ArrayList<String> kafka_topic = schedularService.getKafkaTopic();
			model.addAttribute("kafka_topic", kafka_topic);
		} catch (Exception e) {
			model.addAttribute("errorString", e.getMessage());
		}
		return new ModelAndView("schedular/BatchEdit");
	}

	@RequestMapping(value = "scheduler/LoadBatchJobs", method = RequestMethod.POST)
	public ModelAndView LoadBatchJobs(@Valid @ModelAttribute("batch") String batch_id,
			@Valid @ModelAttribute("project") String project_id, ModelMap model, HttpServletRequest request)
			throws UnsupportedOperationException, Exception {
		try {
			ArrayList<String> job_id1 = schedularService.getBatchJobs(batch_id, project_id);
			model.addAttribute("job_id1", job_id1);
		} catch (Exception e) {
			model.addAttribute("errorString", e.getMessage());
		}
		return new ModelAndView("schedular/LoadBatchJobs");
	}

	@RequestMapping(value = "scheduler/EditJob", method = RequestMethod.POST)
	public ModelAndView EditJob(@Valid @ModelAttribute("batch") String batch_id,
			@Valid @ModelAttribute("project") String project_id, @Valid @ModelAttribute("job_id") String job_id,
			ModelMap model, HttpServletRequest request) throws UnsupportedOperationException, Exception {
		try {
			AdhocJobDTO jobArr = schedularService.extractBatchJobDetails(batch_id, project_id, job_id);
			String script1 = jobArr.getCommand().substring(1, jobArr.getCommand().lastIndexOf('/') + 1);
			System.out.println("script1 is " + script1);
			model.addAttribute("script1", script1);
			model.addAttribute("jobArr", jobArr);
		} catch (Exception e) {
			model.addAttribute("errorString", e.getMessage());
		}
		return new ModelAndView("schedular/EditJob");
	}

}
