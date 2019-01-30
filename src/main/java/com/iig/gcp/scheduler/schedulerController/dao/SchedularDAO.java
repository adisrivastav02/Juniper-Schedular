package com.iig.gcp.scheduler.schedulerController.dao;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;
import com.iig.gcp.scheduler.schedulerController.dto.*;

public interface SchedularDAO {
	
	//Master Table
	 ArrayList<String> getFeedFromMaster(String project) throws Exception;
	 List<MasterJobsDTO> allLoadJobs(String project) throws Exception;
	 List<MasterJobsDTO> typAndBatchLoadJobs(String strFrequencyType, String strBatchId) throws Exception;
	 //MasterJobsDTO orderJobFromMaster(String feedId, String jobId) throws ClassNotFoundException, SQLException, ParseException;
	 String deleteJobFromMaster(String feedId) throws Exception;
	 String suspendJobFromMaster(String feedId) throws ClassNotFoundException, SQLException, Exception;
	 String unSuspendJobFromMaster(@Valid String feedId) throws Exception;

	
	//Archive Table
	ArrayList<String> getFeedIdList() throws Exception;
	ArrayList<ArchiveJobsDTO> getListOfArchievJobs(@Valid String feed_id)throws Exception;
	ArrayList<ArchiveJobsDTO> getChartDetails(@Valid String job_id) throws Exception;
	List<ArchiveJobsDTO> getRunStats(@Valid String job_id, @Valid String feed_id) throws Exception;
	
	//Current Table
	List<DailyJobsDTO> allCurrentJobs(String project) throws Exception;
	ArrayList<String> getFeedFromCurrent(String project) throws Exception;
	List<DailyJobsDTO> filterCurrentJobs(String status, String feedId) throws Exception;
	HashMap<String, ArrayList<String>> allCurrentJobsGroupByFeedId() throws Exception;
	String moveJobFromMasterToCurrentJob(String feedId) throws ClassNotFoundException, SQLException, Exception;
	String runScheduleJob(@Valid String feedId, String jobId, String batchDate) throws Exception;
	String killCurrentJob(@Valid String feedId, String jobId, String batchDate) throws Exception;
	
	//Adhoc Task
	AdhocJobDTO extractBatchJobDetails(String batch_id, String project_id,String job_id);
	ArrayList<BatchDetailsDTO> getBatchDetails() throws Exception;
	ArrayList<TaskSequenceDTO> getJobDetails(String batch_id,String project_id) throws Exception;
	ArrayList<String> getKafkaTopic();
	ArrayList<String> getBatchJobs(String batch_id,String project_id);
	BatchTableDetailsDTO extractBatchDetails(String batch_id, String project_id) throws SQLException;
}
