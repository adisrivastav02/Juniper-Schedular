package com.iig.gcp.scheduler.schedulerService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.iig.gcp.scheduler.schedulerController.dao.SchedularDAO;
import com.iig.gcp.scheduler.schedulerController.dto.AdhocJobDTO;
import com.iig.gcp.scheduler.schedulerController.dto.ArchiveJobsDTO;
import com.iig.gcp.scheduler.schedulerController.dto.BatchDetailsDTO;
import com.iig.gcp.scheduler.schedulerController.dto.BatchTableDetailsDTO;
import com.iig.gcp.scheduler.schedulerController.dto.DailyJobsDTO;
import com.iig.gcp.scheduler.schedulerController.dto.MasterJobsDTO;
import com.iig.gcp.scheduler.schedulerController.dto.TaskSequenceDTO;

@Service
public class SchedularServiceImpl implements SchedularService{
	
	
	
	@Autowired
	SchedularDAO schedularDAO;
	
	//Master Table
	@Override
	public ArrayList<String> getFeedFromMaster(String project) throws Exception {
		// TODO Auto-generated method stub
		return schedularDAO.getFeedFromMaster(project);
	}
	
	@Value("${adhoc.task.compute.url}")
	private String ADHOC_TASK_COMPUTE_URL;
	
	/**
	 * 
	 */
	@Override
	public List<MasterJobsDTO> allLoadJobs(String project) throws Exception {
		return schedularDAO.allLoadJobs(project);
	}

	
	@Override
	public List<MasterJobsDTO> typeLoadJobs(String frequency, String batchId) throws Exception {
		// TODO Auto-generated method stub
		return schedularDAO.typAndBatchLoadJobs(frequency, batchId);
	}
	
	
	//Archive Table;
	@Override
	public ArrayList<String> getFeedIdList() throws Exception {
		// TODO Auto-generated method stub
		return schedularDAO.getFeedIdList();
	}

	@Override
	public ArrayList<ArchiveJobsDTO> getListOfArchievJobs(@Valid String feed_id) throws Exception {
		// TODO Auto-generated method stub
		return schedularDAO.getListOfArchievJobs(feed_id);
	}

	@Override
	public ArrayList<ArchiveJobsDTO> getChartDetails(@Valid String job_id) throws Exception {
		// TODO Auto-generated method stub
		return schedularDAO.getChartDetails(job_id);
	}

	@Override
	public List<ArchiveJobsDTO> getRunStats(@Valid String job_id,@Valid String feed_id) throws Exception {
		// TODO Auto-generated method stub
		return schedularDAO.getRunStats(job_id,feed_id);
	}
	
	//Current Table;
	@Override
	public List<DailyJobsDTO> allCurrentJobs(String project) throws Exception {
		// TODO Auto-generated method stub
		return schedularDAO.allCurrentJobs(project);
	}

	@Override
	public ArrayList<String> getFeedFromCurrent(String project) throws Exception {
		// TODO Auto-generated method stub
		return schedularDAO.getFeedFromCurrent(project);
	}

	@Override
	public List<DailyJobsDTO> filterCurrentJobs(String status, String feedId) throws Exception {
		// TODO Auto-generated method stub
		return schedularDAO.filterCurrentJobs(status, feedId);
	}

	@Override
	public HashMap<String, ArrayList<String>> allCurrentJobsGroupByFeedId() throws Exception {
		// TODO Auto-generated method stub
		return schedularDAO.allCurrentJobsGroupByFeedId();
	}

//	@Override
//	public MasterJobsDTO orderJobFromMaster(String feedId, String jobId) throws ClassNotFoundException, SQLException, ParseException {
//		return schedularDAO.orderJobFromMaster(feedId, jobId);
//		
//	}

	@Override
	public String moveJobFromMasterToCurrentJob(String feedId) throws Exception {
		return schedularDAO.moveJobFromMasterToCurrentJob(feedId);
		
	}
	
	@Override
	public String deleteJobFromMaster(String feedId) throws Exception {
		return schedularDAO.deleteJobFromMaster(feedId);
		
	}

	@Override
	public String runScheduleJob(@Valid String feedId, String jobId, String batchDate) throws Exception {
		return schedularDAO.runScheduleJob(feedId, jobId, batchDate);
	}

	@Override
	public String killCurrentJob(@Valid String feedId, String jobId, String batchDate) throws Exception{
		return schedularDAO.killCurrentJob(feedId, jobId, batchDate);
	}

	@Override
	public String suspendJobFromMaster(String feedId) throws Exception {
		return schedularDAO.suspendJobFromMaster(feedId);
	}

	@Override
	public String unSuspendJobFromMaster(@Valid String feedId) throws Exception {
		return schedularDAO.unSuspendJobFromMaster(feedId);
	}
	
	@Override
	public ArrayList<BatchDetailsDTO> getBatchDetails() throws Exception {
		return schedularDAO.getBatchDetails();
	}
	
	@Override
	public ArrayList<TaskSequenceDTO> getJobDetails(String batch_id,String project_id) throws Exception {
		return schedularDAO.getJobDetails(batch_id,project_id);
	}
	
	@Override
	public ArrayList<String> getKafkaTopic()  throws Exception{
		try {
			return schedularDAO.getKafkaTopic();
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ArrayList<String> getBatchJobs(String batch_id,String project_id) throws Exception  {
		try {
			return schedularDAO.getBatchJobs(batch_id,project_id);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public BatchTableDetailsDTO extractBatchDetails(String batch_id, String project_id) throws Exception{
		return schedularDAO.extractBatchDetails(batch_id,project_id);
	}
	

	@Override
	public AdhocJobDTO extractBatchJobDetails(String batch_id, String project_id,String job_id) throws Exception{
		try {
			return schedularDAO.extractBatchJobDetails(batch_id,project_id,job_id);
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	@Override
	public String invokeRest(String json, String url) throws UnsupportedOperationException, Exception {
		String resp = null;
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost postRequest = new HttpPost(ADHOC_TASK_COMPUTE_URL + url);
		System.out.println(ADHOC_TASK_COMPUTE_URL + url);
		postRequest.setHeader("Content-Type", "application/json");
		StringEntity input = new StringEntity(json);
		postRequest.setEntity(input);
		HttpResponse response = httpClient.execute(postRequest);
		String response_string = EntityUtils.toString(response.getEntity(), "UTF-8");
		if (response.getStatusLine().getStatusCode() != 200) {
			resp = "Error" + response_string;
			throw new Exception("Error" + response_string);
		} else {
			resp = response_string;
		}
		return resp;
	}
	
}
