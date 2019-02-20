package com.iig.gcp.scheduler.schedulerService;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;
import com.iig.gcp.scheduler.schedulerController.dto.*;

public interface SchedularService {

	//Current Table
	List<DailyJobsDTO> allCurrentJobs(String project) throws Exception;
	ArrayList<String> getFeedFromCurrent(String project) throws Exception;
	List<DailyJobsDTO> filterCurrentJobs(String status, String feedId) throws Exception;
	HashMap<String, ArrayList<String>> allCurrentJobsGroupByFeedId() throws Exception;
	String runScheduleJob(@Valid String feedId, String jobId, String batchDate) throws Exception;
	String killCurrentJob(@Valid String feedId, String jobId, String batchDate) throws Exception;	

	
	String invokeRest(String json,String url) throws UnsupportedOperationException, Exception ;
	
	//Adhoc Scheduling
	ArrayList<BatchDetailsDTO> getBatchDetails() throws Exception;
	ArrayList<BatchDetailsDTO> getCreateBatchDetails() throws Exception;
	ArrayList<BatchDetailsDTO> getEditBatchDetails() throws Exception;
	BatchTableDetailsDTO extractBatchDetails(String batch_id,String project_id) throws Exception;
	ArrayList<TaskSequenceDTO> getJobDetails(String batch_id,String project_id) throws Exception;
	ArrayList<DailyJobsDTO> getAdhocJobDetails(String batch_id,String project_id,String job_name) throws Exception;	
	String getBatchSequence (String batch_id,String project_id,String job_name,int i) throws Exception;
	ArrayList<String> getKafkaTopic() throws Exception;
	ArrayList<String> getBatchJobs(String batch_id,String project_id) throws Exception;;
	AdhocJobDTO extractBatchJobDetails(String batch_id, String project_id,String job_id) throws Exception;
	ArrayList<BatchDetailsDTO> getAdhocBatchCreateDetails() throws Exception;
	ArrayList<BatchDetailsDTO> getAdhocBatchEditDetails() throws Exception;

	
	//Archive table
	ArrayList<String> getFeedIdList() throws Exception;
	ArrayList<ArchiveJobsDTO> getListOfArchievJobs(@Valid String feed_id) throws Exception;
	ArrayList<ArchiveJobsDTO> getChartDetails(@Valid String job_id) throws Exception;
	List<ArchiveJobsDTO> getRunStats(@Valid String job_id, @Valid String feed_id) throws Exception;
	
	//Master Table
	ArrayList<String> getFeedFromMaster(String project)throws Exception;
	List<MasterJobsDTO> allLoadJobs(String project) throws Exception;
	List<MasterJobsDTO> typeLoadJobs(String frequency, String batchId) throws Exception;
	//MasterJobsDTO orderJobFromMaster(String feedId,String jobId) throws ClassNotFoundException, SQLException, ParseException;
	String moveJobFromMasterToCurrentJob(String feedId) throws ClassNotFoundException, SQLException, Exception;
	String deleteJobFromMaster(String feedId) throws Exception;
	String suspendJobFromMaster(String feedId) throws ClassNotFoundException, SQLException, Exception;
	String unSuspendJobFromMaster(@Valid String feedId) throws Exception;



	
	
		
		

}
