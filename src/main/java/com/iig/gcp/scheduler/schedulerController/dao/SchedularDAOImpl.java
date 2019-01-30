package com.iig.gcp.scheduler.schedulerController.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import com.iig.gcp.scheduler.schedulerController.dto.*;
import com.iig.gcp.scheduler.schedulerController.utils.*;

/**
 * @author Nakuldinkarrao.V
 *
 */
@Component
public class SchedularDAOImpl implements SchedularDAO {

	@Autowired
	private ConnectionUtils ConnectionUtils;
	//DateFormat batchDate = new SimpleDateFormat("yyyy-MM-dd");
	//DateFormat lastUpdateTs = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	//DateFormat jobScheduleTime = new SimpleDateFormat("hh:mm:ss");
	DateFormat dateFormat2 = new SimpleDateFormat("hh:mm:ss");
	Date date = new Date();

	private static String SPACE = " ";
	private static String COMMA = ",";
	private static String SEMICOLON = ";";
	private static String QUOTE = "\'";
	private static String DATABASE_NAME = "iigs_scheduler_db";
	private static String FEED_MASTER_TABLE = "JUNIPER_SCH_MASTER_JOB_DETAIL";
	private static String FEED_CURRENT_TABLE = "JUNIPER_SCH_CURRENT_JOB_DETAIL";

	// Master Table
	@Override
	public ArrayList<String> getFeedFromMaster(String project) throws Exception {
		ArrayList<String> arrFeedId = new ArrayList<String>();
		Connection conn = null;
		ResultSet rs =null;
		PreparedStatement pstm =null;
		try {
		conn = ConnectionUtils.getConnection();
		String query = "Select distinct batch_id from juniper_sch_master_job_detail master left join juniper_project_master project on master.project_id=project.project_sequence where project.project_id='"+project+"' order by batch_id";
		 pstm = conn.prepareStatement(query);
		 rs = pstm.executeQuery();
		while (rs.next()) {
			arrFeedId.add(rs.getString(1));
		}
		} catch (ClassNotFoundException | SQLException e) {
			throw e;
		}finally {	
		conn.close();
		}
		
		return arrFeedId;
	}

	/**
	 * 
	 */
	@Override
	public List<MasterJobsDTO> allLoadJobs(String project) throws Exception {
		List<MasterJobsDTO> scheduledJobs = new ArrayList<MasterJobsDTO>();
		Connection conn = null;
		ResultSet rs =null;
		PreparedStatement pstm =null;
		try {
		conn = ConnectionUtils.getConnection();
		String query="SELECT DISTINCT master.batch_id, CASE WHEN master.schedule_type = 'O' then 'On-Demand' WHEN master.schedule_type = 'A' then 'API Based' WHEN master.schedule_type = 'K' then 'Kafka Based' WHEN master.schedule_type = 'F' then 'File Watcher' WHEN master.weekly_flag = 'Y' THEN concat('Weekly on ', master.week_run_day) WHEN master.daily_flag = 'Y' THEN concat('Daily at ', substr(master.job_schedule_time, 1, 5)) WHEN master.monthly_flag = 'Y' THEN concat('Monthly on ', master.month_run_day) WHEN master.yearly_flag = 'Y' THEN concat('Yearly on month ', master.month_run_val) END AS consolidated_schedule, CASE WHEN master.weekly_flag = 'Y' THEN 'Weekly' WHEN master.daily_flag = 'Y' THEN 'Daily' WHEN master.monthly_flag = 'Y' THEN 'Monthly' WHEN master.yearly_flag = 'Y' THEN 'Yearly' END AS schedule, CASE WHEN master.schedule_type not in ('O') then 'CURR-Y' ELSE 'CURR-N' END AS in_current, concat('SUS-', master.is_suspended) AS is_suspended FROM juniper_sch_master_job_detail master LEFT JOIN juniper_sch_current_job_detail curr ON master.job_id = curr.job_id AND master.batch_id = curr.batch_id AND TO_CHAR(curr.batch_date,'DD-MON-YY') = TO_CHAR(SYSDATE,'DD-MON-YY') LEFT JOIN juniper_project_master project on master.project_id=project.project_sequence where project.project_id='"+project+"' ORDER BY master.batch_id";
		pstm = conn.prepareStatement(query);
		rs = pstm.executeQuery();
		MasterJobsDTO dto = null;
		while (rs.next()) {
			dto = new MasterJobsDTO();
			//dto.setJob_id(rs.getString(1));
			//dto.setJob_name(rs.getString(2));
			dto.setBatch_id(rs.getString(1));
			dto.setConsolidatedSchedule(rs.getString(2));
			dto.setSchedule(rs.getString(3));
			dto.setIn_current(rs.getString(4));
			dto.setIs_suspended(rs.getString(5));
			//dto.setJob_sequence(rs.getInt(6));
			scheduledJobs.add(dto);
		}
		} catch (ClassNotFoundException | SQLException e) {
		throw e;
		}finally {	
			conn.close();
		}
		return scheduledJobs;
	}

	/**
	 * 
	 */
	@Override
	public List<MasterJobsDTO> typAndBatchLoadJobs(String frequency, String batchId) throws Exception {
		List<MasterJobsDTO> scheduledJobs = new ArrayList<MasterJobsDTO>();
		Connection conn = null;
		ResultSet rs =null;
		PreparedStatement pstm =null;
		try {
		conn = ConnectionUtils.getConnection();
		if (batchId.equals("ALL") && frequency.equals("ALL")) {
			batchId = "%";
			frequency = "%";
		} else if (batchId.equals("ALL") && !frequency.equals("ALL")) {
			batchId = "%";
		} else if (!batchId.equals("ALL") && frequency.equals("ALL")) {
			frequency = "%";
		}

		String query = "SELECT DISTINCT master.batch_id, CASE WHEN master.schedule_type = 'O' then 'On-Demand'WHEN master.schedule_type = 'A' then 'API Based' WHEN master.schedule_type = 'K' then 'Kafka Based' WHEN master.schedule_type = 'F' then 'File Watcher' WHEN master.weekly_flag = 'Y' THEN concat('Weekly on ', master.week_run_day) WHEN master.daily_flag = 'Y' THEN concat('Daily at ', substr(master.job_schedule_time, 1, 5)) WHEN master.monthly_flag = 'Y' THEN concat('Monthly on ', master.month_run_day) WHEN master.yearly_flag = 'Y' THEN concat('Yearly on month', master.month_run_val) END AS consolidated_schedule, CASE WHEN master.weekly_flag = 'Y' THEN 'Weekly' WHEN master.daily_flag = 'Y' THEN 'Daily' WHEN master.monthly_flag = 'Y' THEN 'Monthly' WHEN master.yearly_flag = 'Y' THEN 'Yearly' END AS schedule, CASE WHEN master.schedule_type not in ('O') then 'CURR-Y' ELSE 'CURR-N' END AS in_current, concat('SUS-', master.is_suspended) AS is_suspended FROM JUNIPER_SCH_MASTER_JOB_DETAIL master LEFT JOIN JUNIPER_SCH_CURRENT_JOB_DETAIL curr ON master.job_id = curr.job_id AND master.batch_id = curr.batch_id AND TO_CHAR(curr.batch_date,'DD-MON-YY') = TO_CHAR(SYSDATE,'DD-MON-YY') WHERE CASE WHEN master.weekly_flag = 'Y' THEN 'WEEKLY' WHEN master.daily_flag = 'Y' THEN 'DAILY' WHEN master.monthly_flag = 'Y' THEN 'MONTHLY' WHEN master.yearly_flag = 'Y' THEN 'YEARLY' END LIKE ? AND master.batch_id LIKE ? ORDER BY batch_id";
		pstm = conn.prepareStatement(query);
		pstm.setString(1, frequency);
		pstm.setString(2, batchId);
		rs = pstm.executeQuery();
		MasterJobsDTO dto = null;
		while (rs.next()) {
			dto = new MasterJobsDTO();
			//dto.setJob_id(rs.getString(1));
			//dto.setJob_name(rs.getString(2));
			dto.setBatch_id(rs.getString(1));
			dto.setConsolidatedSchedule(rs.getString(2));
			dto.setSchedule(rs.getString(3));
			dto.setIn_current(rs.getString(4));
			dto.setIs_suspended(rs.getString(5));
			//dto.setJob_sequence(rs.getInt(6));
			scheduledJobs.add(dto);
		}
		} catch (ClassNotFoundException | SQLException e) {
			throw e;
			}finally {	
				conn.close();
			}
		return scheduledJobs;
	}
	
	/**
	 * 
	 */
	@Override
	public String deleteJobFromMaster(String feedId) throws Exception{
		Connection conn = null;
		int rs =0;
		PreparedStatement pstm1 =null;
		try {
		conn = ConnectionUtils.getConnection();
		/*PreparedStatement pstm;
		MasterJobsDTO masterJobDTO =orderJobFromMaster(feedId);
		if(masterJobDTO!=null) {
			jobId = masterJobDTO.getJob_id();
			int masterJobSeq=masterJobDTO.getJob_sequence();
			
			for(int i=1;i<=10;i++) {
				String predessor="predessor_job_id_"+i;
				String updatePredecessorsQuery= "update JUNIPER_SCH_MASTER_JOB_DETAIL  set "+predessor+"=' ' where "+predessor+"="+QUOTE+jobId+QUOTE+" and job_sequence="+QUOTE+masterJobSeq+QUOTE;
				pstm = conn.prepareStatement(updatePredecessorsQuery);
				pstm.executeUpdate();
			}
		}
		*/
		
		String query = "delete from JUNIPER_SCH_MASTER_JOB_DETAIL where batch_id=?";
		pstm1 = conn.prepareStatement(query);
		pstm1.setString(1, feedId);	
		rs = pstm1.executeUpdate();
		return (rs + " Jobs deleted with FeedID: " + feedId);
	
		} catch (ClassNotFoundException | SQLException e) {
		throw e;
		
		}finally {	
			conn.close();
		}
	
	}
	

	// Archive Table
	@Override
	public ArrayList<String> getFeedIdList() throws Exception {
		Connection conn = null;
		ResultSet rs =null;
		PreparedStatement pstm =null;
		ArrayList<String> arrFeedId = new ArrayList<String>();
		try {
		 conn = ConnectionUtils.getConnection();
		String query = "select distinct batch_id from iigs_archive_job_detail order by batch_id";
		 pstm = conn.prepareStatement(query);
		 rs = pstm.executeQuery();

		while (rs.next()) {
			arrFeedId.add(rs.getString(1));
		}
		conn.close();
		} catch (ClassNotFoundException | SQLException e) {
			throw e;
			
		}finally {	
			conn.close();
		}
		return arrFeedId;
	}

	@Override
	public ArrayList<ArchiveJobsDTO> getListOfArchievJobs(@Valid String feed_id) throws Exception {

		ArrayList<ArchiveJobsDTO> arrArchiveJobsDTO =new ArrayList<ArchiveJobsDTO>();
		Connection conn = null;
		ResultSet rs =null;
		PreparedStatement pstm =null;
		try {
		conn=ConnectionUtils.getConnection();	
		String query="select distinct job_id from iigs_archive_job_detail where batch_id=? order by job_id";

		pstm = conn.prepareStatement(query);
		pstm.setString(1, feed_id);
		rs = pstm.executeQuery();
		ArchiveJobsDTO archiveJobsDTO = null;
		while (rs.next()) {
			archiveJobsDTO = new ArchiveJobsDTO();
			archiveJobsDTO.setJob_id(rs.getString(1));
			arrArchiveJobsDTO.add(archiveJobsDTO);
		}
		} catch (ClassNotFoundException | SQLException e) {
		throw e;
		
		}finally {	
			conn.close();
		}
		return arrArchiveJobsDTO;
	}

	@Override
	public ArrayList<ArchiveJobsDTO> getChartDetails(@Valid String job_id) throws Exception {
		ArrayList<ArchiveJobsDTO> arrArchiveJobsDTO = new ArrayList<ArchiveJobsDTO>();
		Connection conn = null;
		ResultSet rs =null;
		PreparedStatement pstm =null;
		try {
		conn = ConnectionUtils.getConnection();
		String query = "select job_id, batch_id, status, start_time, end_time, batch_date, timediff(end_time,start_time) as duration from iigs_archive_job_detail where job_id=? order by batch_date, batch_id, job_id";
		pstm = conn.prepareStatement(query);
		pstm.setString(1, job_id);
		rs = pstm.executeQuery();
		ArchiveJobsDTO archiveJobsDTO = null;
		while (rs.next()) {
			archiveJobsDTO = new ArchiveJobsDTO();
			archiveJobsDTO.setJob_id(rs.getString(1));
			archiveJobsDTO.setBatch_id(rs.getString(2));
			archiveJobsDTO.setStatus(rs.getString(3));
			archiveJobsDTO.setStart_time(rs.getString(4));
			archiveJobsDTO.setEnd_time(rs.getString(5));
			archiveJobsDTO.setBatch_date(rs.getString(6));
			archiveJobsDTO.setDuration(rs.getString(7));
			arrArchiveJobsDTO.add(archiveJobsDTO);
		}
		} catch (ClassNotFoundException | SQLException e) {
		throw e;
		
		}finally {	
			conn.close();
		}
		return arrArchiveJobsDTO;
	}

	public List<ArchiveJobsDTO> getRunStats(@Valid String job_id, @Valid String feed_id) throws Exception {
		List<ArchiveJobsDTO> archiveJobs = new ArrayList<ArchiveJobsDTO>();
		Connection conn = null;
		ResultSet rs =null;
		PreparedStatement pstm =null;
		try {
		conn = ConnectionUtils.getConnection();
		String query = "select job_id, batch_id, job_name, start_time, end_time, batch_date, timediff(end_time,start_time) as duration from iigs_archive_job_detail where batch_id=? and job_id=? order by batch_date, batch_id, job_id";
		pstm = conn.prepareStatement(query);
		pstm.setString(1, feed_id);
		pstm.setString(2, job_id);
		rs = pstm.executeQuery();
		ArchiveJobsDTO dto = null;
		while (rs.next()) {
			dto = new ArchiveJobsDTO();
			dto.setJob_id(rs.getString(1));
			dto.setBatch_id(rs.getString(2));
			dto.setJob_name(rs.getString(3));
			dto.setStart_time(rs.getString(4));
			dto.setEnd_time(rs.getString(5));
			dto.setBatch_date(rs.getString(6));
			dto.setDuration(rs.getString(7));
			archiveJobs.add(dto);
		}
		} catch (ClassNotFoundException | SQLException e) {
			throw e;
			
			}finally {	
				conn.close();
			}
		return archiveJobs;
	}

	// Current Table
	@Override
	public HashMap<String, ArrayList<String>> allCurrentJobsGroupByFeedId() throws Exception {
		HashMap<String, ArrayList<String>> hsMap = new HashMap<String, ArrayList<String>>();
		ArrayList<String> arrKey = new ArrayList<String>();
		ArrayList<String> arrValue = new ArrayList<String>();
		Connection conn = null;
		ResultSet rs =null;
		PreparedStatement pstm =null;
		try {
		conn = ConnectionUtils.getConnection();

		String query = "select count(job_id), batch_id from iigs_current_job_detail group by batch_id";
		// int all = 0, completed=0, running=0, failed=0, waiting =0, scheduled=0;
		pstm = conn.prepareStatement(query);
		rs = pstm.executeQuery();
		while (rs.next()) {
			//System.out.println("in DB K" + String.valueOf(rs.getInt(1)));
			arrKey.add(String.valueOf(rs.getInt(1)));
			arrValue.add(rs.getString(2));
		}
		hsMap.put("arrkey", arrKey);
		hsMap.put("arrValue", arrValue);
		} catch (ClassNotFoundException | SQLException e) {
		throw e;
		
		}finally {	
			conn.close();
		}
		return hsMap;
	}

	@Override
	public List<DailyJobsDTO> allCurrentJobs(String project) throws Exception {
		List<DailyJobsDTO> scheduledJobs = new ArrayList<DailyJobsDTO>();
		Connection conn = null;
		ResultSet rs =null;
		PreparedStatement pstm =null;
		try {
		conn = ConnectionUtils.getConnection();
		String query = "Select job_id,job_name,batch_id, job_schedule_time, case when status='C' then 'Completed' when status='F' then 'Failed' when status='R' then 'Running' when status='W' then 'Waiting' else 'To Run' end as status, TO_CHAR(batch_date,'DD-MON-YY') as batch_date from "+FEED_CURRENT_TABLE+" master left join juniper_project_master project on master.project_id=project.project_sequence where project.project_id= '"+project+"'order by batch_id, job_id, batch_date";
		pstm = conn.prepareStatement(query);
		rs = pstm.executeQuery();
		DailyJobsDTO dto = null;
		while (rs.next()) {
			dto = new DailyJobsDTO();
			dto.setJob_id(rs.getString(1));
			dto.setJob_name(rs.getString(2));
			dto.setBatch_id(rs.getString(3));
			dto.setJob_schedule_time(rs.getString(4));
			dto.setStatus(rs.getString(5));
			dto.setBatch_date(rs.getString(6));
			scheduledJobs.add(dto);
		}
		} catch (ClassNotFoundException | SQLException e) {
			throw e;
			
			}finally {	
				conn.close();
			}
		return scheduledJobs;
	}

	@Override
	public ArrayList<String> getFeedFromCurrent(String project) throws Exception {
		ArrayList<String> arrFeedId = new ArrayList<String>();
		Connection conn = null;
		ResultSet rs =null;
		PreparedStatement pstm =null;
		try {
		conn = ConnectionUtils.getConnection();
		String query = "Select distinct batch_id from "+FEED_CURRENT_TABLE+" master left join juniper_project_master project on master.project_id=project.project_sequence where project.project_id ='"+project+"' order by batch_id";
		pstm = conn.prepareStatement(query);
		rs = pstm.executeQuery();
		while (rs.next()) {
			arrFeedId.add(rs.getString(1));
		}
		} catch (ClassNotFoundException | SQLException e) {
		throw e;
		
		}finally {	
			conn.close();
		}
		return arrFeedId;
	}

	@Override
	public List<DailyJobsDTO> filterCurrentJobs(String status, String feedId) throws Exception {
		List<DailyJobsDTO> scheduledJobs = new ArrayList<DailyJobsDTO>();
		Connection conn = null;
		ResultSet rs =null;
		PreparedStatement pstm =null;
		try {
		conn = ConnectionUtils.getConnection();
		if (status.equals("ALL") && feedId.equals("ALL")) {
			status = "%";
			feedId = "%";
		} else if (status.equals("ALL") && !feedId.equals("ALL")) {
			status = "%";
		} else if (!status.equals("ALL") && feedId.equals("ALL")) {
			feedId = "%";
		}
		String query = "SELECT job_id, job_name, batch_id, job_schedule_time, CASE WHEN status = 'C' THEN 'Completed' WHEN status = 'F' THEN 'Failed' WHEN status = 'R' THEN 'Running' WHEN status = 'W' THEN 'Waiting' ELSE 'To Run' END AS status, TO_CHAR(batch_date,'DD-MON-YY') as batch_date FROM JUNIPER_SCH_CURRENT_JOB_DETAIL WHERE CASE WHEN status IS NULL THEN 'T' ELSE status END LIKE ? AND batch_id LIKE ? ORDER BY batch_id, job_id, batch_date";
		pstm = conn.prepareStatement(query);
		pstm.setString(1, status);
		pstm.setString(2, feedId);
		rs = pstm.executeQuery();
		DailyJobsDTO dto = null;
		while (rs.next()) {
			dto = new DailyJobsDTO();
			dto.setJob_id(rs.getString(1));
			dto.setJob_name(rs.getString(2));
			dto.setBatch_id(rs.getString(3));
			dto.setStatus(rs.getString(5));
			dto.setJob_schedule_time(rs.getString(4));
			dto.setBatch_date(rs.getString(6));
			scheduledJobs.add(dto);
		}
		} catch (ClassNotFoundException | SQLException e) {
			throw e;
			
			}finally {	
				conn.close();
			}
		return scheduledJobs;
	}


//	@Override
//	public MasterJobsDTO orderJobFromMaster(String feedId, String jobId)
//			throws ClassNotFoundException, SQLException, ParseException {
//		MasterJobsDTO masterJobDTO = null;
//		Connection conn = ConnectionUtils.getConnection();
//		String query = "select project_id,feed_id,'A','A',job_id,job_name,batch_id,pre_processing,post_processing,"
//				+ "command,argument_1,argument_2,argument_3,argument_4,argument_5,"
//				+ "daily_flag,monthly_flag,job_schedule_time,"
//				+ "predessor_job_id_1,predessor_job_id_2,predessor_job_id_3,predessor_job_id_4,"
//				+ "predessor_job_id_5,predessor_job_id_6,predessor_job_id_7,predessor_job_id_8,predessor_job_id_9,predessor_job_id_10,"
//				+ "weekly_flag,week_run_day,month_run_day,month_run_val,is_dependent_job,command_type,yearly_flag,"
//				+ "week_num_month,job_sequence,is_suspended from " + FEED_MASTER_TABLE + " where batch_id=? and job_id=?";
//		PreparedStatement pstm = conn.prepareStatement(query);
//		pstm.setString(1, feedId);
//		pstm.setString(2, jobId);
//		ResultSet rs = pstm.executeQuery();
//		while (rs.next()) {
//			masterJobDTO = new MasterJobsDTO();
//
//			masterJobDTO.setProject_id(rs.getString(1));
//			masterJobDTO.setFeed_id(rs.getString(2));
//			masterJobDTO.setSource_emid(rs.getString(3));
//			masterJobDTO.setTarget_emid(rs.getString(4));
//			// current_date
//			masterJobDTO.setJob_id(rs.getString(5));
//			masterJobDTO.setJob_name(rs.getString(6));
//			masterJobDTO.setBatch_id(rs.getString(7));
//
//			masterJobDTO.setPre_processing(rs.getString(8));
//			masterJobDTO.setPost_processing(rs.getString(9));
//			masterJobDTO.setCommand(rs.getString(10));
//			masterJobDTO.setArgument_1(rs.getString(11));
//			masterJobDTO.setArgument_2(rs.getString(12));
//			masterJobDTO.setArgument_3(rs.getString(13));
//			masterJobDTO.setArgument_4(rs.getString(14));
//			masterJobDTO.setArgument_5(rs.getString(15));
//
//			if (rs.getString(16) != null && !rs.getString(16).equals("")) {
//				char dailyFlag = rs.getString(16).charAt(0);
//				masterJobDTO.setDaily_flag(dailyFlag);
//			} else {
//				masterJobDTO.setDaily_flag(' ');
//			}
//
//			if (rs.getString(17) != null && !rs.getString(17).equals("")) {
//				char monthlyFlag = rs.getString(17).charAt(0);
//				masterJobDTO.setMonthly_flag(monthlyFlag);
//			} else {
//				masterJobDTO.setMonthly_flag(' ');
//			}
//
//			masterJobDTO.setJob_schedule_time(rs.getString(18));
//
//			// status
//
//			String predessor_job_id_1 = rs.getString(19);
//			masterJobDTO.setPredessor_job_id_1(predessor_job_id_1);
//
//			String predessor_job_id_2 = rs.getString(20);
//			masterJobDTO.setPredessor_job_id_2(predessor_job_id_2);
//
//			String predessor_job_id_3 = rs.getString(21);
//			masterJobDTO.setPredessor_job_id_3(predessor_job_id_3);
//
//			String predessor_job_id_4 = rs.getString(22);
//			masterJobDTO.setPredessor_job_id_4(predessor_job_id_4);
//
//			String predessor_job_id_5 = rs.getString(23);
//			masterJobDTO.setPredessor_job_id_5(predessor_job_id_5);
//
//			String predessor_job_id_6 = rs.getString(24);
//			masterJobDTO.setPredessor_job_id_6(predessor_job_id_6);
//
//			String predessor_job_id_7 = rs.getString(25);
//			masterJobDTO.setPredessor_job_id_7(predessor_job_id_7);
//
//			String predessor_job_id_8 = rs.getString(26);
//			masterJobDTO.setPredessor_job_id_8(predessor_job_id_8);
//
//			String predessor_job_id_9 = rs.getString(27);
//			masterJobDTO.setPredessor_job_id_9(predessor_job_id_9);
//
//			String predessor_job_id_10 = rs.getString(28);
//			masterJobDTO.setPredessor_job_id_10(predessor_job_id_10);
//
//			if (rs.getString(29) != null && !rs.getString(29).equals("")) {
//				char weeklyFlag = rs.getString(29).charAt(0);
//				masterJobDTO.setWeekly_flag(weeklyFlag);
//			} else {
//				masterJobDTO.setWeekly_flag(' ');
//			}
//
//			String weekRunDay = rs.getString(30);
//			masterJobDTO.setWeek_run_day(weekRunDay);
//
//			String monthlyRunDay = rs.getString(31);
//			masterJobDTO.setMonth_run_day(monthlyRunDay);
//
//			String monthlyRunVal = rs.getString(32);
//			masterJobDTO.setMonth_run_val(monthlyRunVal);
// 
//			if(rs.getString(33)!=null) {
//				masterJobDTO.setIs_dependent_job(rs.getString(33));
//			}
//			else {
//				masterJobDTO.setIs_dependent_job("");
//			}
//			
//
//			String commandType = rs.getString(34);
//			masterJobDTO.setCommand_type(commandType);
//
//			// timestamp
//
//			if (rs.getString(35) != null && !rs.getString(35).equals("")) {
//				char yearlyFlag = rs.getString(35).charAt(0);
//				masterJobDTO.setYearly_flag(yearlyFlag);
//			} else {
//				masterJobDTO.setYearly_flag(' ');
//			}
//
//			if (rs.getString(36) != null && !rs.getString(36).equals("")) {
//				String weekNumMonth = rs.getString(36);
//				int i = Integer.parseInt(weekNumMonth);
//				masterJobDTO.setWeek_num_month(i);
//			}
//			
//			masterJobDTO.setJob_sequence(rs.getInt(37));
//			
//			masterJobDTO.setIs_suspended(rs.getString(38));
//
//		}
//		ConnectionUtils.closeQuietly(conn);
//		return masterJobDTO;
//	}

	/* 
	 * This method move job from master table to current table
	 * (non-Javadoc)
	 * @see com.iig.gcp.scheduler.dao.SchedularDAO#moveJobFromMasterToCurrentJob(java.lang.String, java.lang.String)
	 */
	@Override
	public String moveJobFromMasterToCurrentJob(String feedId)throws Exception {
		Connection conn = null;
		PreparedStatement pstm,pstm1 =null;
		try {
			conn = ConnectionUtils.getConnection();
			String deleteDependentQuery = "DELETE FROM juniper_sch_dependent where job_id like '" + feedId + "%' and to_char(batch_date,'YYYYMMDD')=TO_CHAR(SYSDATE,'YYYYMMDD')";
			PreparedStatement pstmd1 = conn.prepareStatement(deleteDependentQuery);
			pstmd1.executeUpdate();			
			
			String deleteCurrentFeedLoggerQuery = "DELETE FROM juniper_sch_current_job_detail WHERE batch_id = ? and to_char(batch_date,'YYYYMMDD')=TO_CHAR(SYSDATE,'YYYYMMDD')";
			
			PreparedStatement pstmd = conn.prepareStatement(deleteCurrentFeedLoggerQuery);
			pstmd.setString(1, feedId);
			pstmd.executeUpdate();
			
			String insertCurrentFeedLoggerQuery = "INSERT INTO juniper_sch_current_job_detail ( master_job_sequence, project_id, feed_id, batch_date, job_id, job_name, batch_id, pre_processing, post_processing, command, argument_1, argument_2, argument_3, argument_4, argument_5, daily_flag, monthly_flag, job_schedule_time, status, predessor_job_id_1, predessor_job_id_2, predessor_job_id_3, predessor_job_id_4, predessor_job_id_5, predessor_job_id_6, predessor_job_id_7, predessor_job_id_8, predessor_job_id_9, predessor_job_id_10, weekly_flag, week_run_day, month_run_day, month_run_val, is_dependent_job, command_type, last_update_ts, yearly_flag, week_num_month, SCHEDULE_TYPE ) SELECT job_sequence, project_id, feed_id, SYSDATE, job_id, job_name, batch_id, pre_processing, post_processing, command, argument_1, argument_2, concat(CAST((SYSDATE - TO_DATE('1-1-1970 00:00:00', 'MM-DD-YYYY HH24:Mi:SS')) * 24 * 3600 AS INTEGER), lpad(DENSE_RANK () OVER( ORDER BY batch_id), 5, '0')) AS argument_3, argument_4, argument_5, daily_flag, monthly_flag, TO_CHAR(SYSTIMESTAMP + interval '2' minute,'hh24:mi:ss'), CASE WHEN TRIM(predessor_job_id_1) IS NOT NULL THEN 'W' ELSE '' END AS status, predessor_job_id_1, predessor_job_id_2, predessor_job_id_3, predessor_job_id_4, predessor_job_id_5, predessor_job_id_6, predessor_job_id_7, predessor_job_id_8, predessor_job_id_9, predessor_job_id_10, weekly_flag, week_run_day, month_run_day, month_run_val, is_dependent_job, command_type, SYSDATE, yearly_flag, week_num_month, SCHEDULE_TYPE FROM juniper_sch_master_job_detail WHERE batch_id = ?";
			
			pstm = conn.prepareStatement(insertCurrentFeedLoggerQuery);
			pstm.setString(1, feedId);
			pstm.executeUpdate();
			
			String insertDependentQuery = "INSERT INTO juniper_sch_dependent ( batch_date, job_sequence, job_id, predecessor_job_id_1, predecessor_job_id_2, predecessor_job_id_3, predecessor_job_id_4, predecessor_job_id_5, predecessor_job_id_6, predecessor_job_id_7, predecessor_job_id_8, predecessor_job_id_9, predecessor_job_id_10, dependent_job_count, current_job_count, completed_flag ) SELECT batch_date, current_job_sequence, job_id, CASE WHEN predessor_job_id_1 IS NOT NULL THEN TRIM(predessor_job_id_1) ELSE NULL END AS predecessor_job_id_1, CASE WHEN predessor_job_id_2 IS NOT NULL THEN TRIM(predessor_job_id_2) ELSE NULL END AS predecessor_job_id_2, CASE WHEN predessor_job_id_3 IS NOT NULL THEN TRIM(predessor_job_id_3) ELSE NULL END AS predecessor_job_id_3, CASE WHEN predessor_job_id_4 IS NOT NULL THEN TRIM(predessor_job_id_4) ELSE NULL END AS predecessor_job_id_4, CASE WHEN predessor_job_id_5 IS NOT NULL THEN TRIM(predessor_job_id_5) ELSE NULL END AS predecessor_job_id_5, CASE WHEN predessor_job_id_6 IS NOT NULL THEN TRIM(predessor_job_id_6) ELSE NULL END AS predecessor_job_id_6, CASE WHEN predessor_job_id_7 IS NOT NULL THEN TRIM(predessor_job_id_7) ELSE NULL END AS predecessor_job_id_7, CASE WHEN predessor_job_id_8 IS NOT NULL THEN TRIM(predessor_job_id_8) ELSE NULL END AS predecessor_job_id_8, CASE WHEN predessor_job_id_9 IS NOT NULL THEN TRIM(predessor_job_id_9) ELSE NULL END AS predecessor_job_id_9, CASE WHEN TRIM(predessor_job_id_1) IS NOT NULL THEN TRIM(predessor_job_id_1) ELSE NULL END AS predecessor_job_id_10, CASE WHEN predessor_job_id_1 IS NOT NULL AND predessor_job_id_2 IS NOT NULL AND predessor_job_id_3 IS NOT NULL AND predessor_job_id_4 IS NOT NULL AND predessor_job_id_5 IS NOT NULL AND predessor_job_id_6 IS NOT NULL AND predessor_job_id_7 IS NOT NULL AND predessor_job_id_8 IS NOT NULL AND predessor_job_id_9 IS NOT NULL AND predessor_job_id_10 IS NOT NULL THEN 10 WHEN predessor_job_id_1 IS NOT NULL AND predessor_job_id_2 IS NOT NULL AND predessor_job_id_3 IS NOT NULL AND predessor_job_id_4 IS NOT NULL AND predessor_job_id_5 IS NOT NULL AND predessor_job_id_6 IS NOT NULL AND predessor_job_id_7 IS NOT NULL AND predessor_job_id_8 IS NOT NULL AND predessor_job_id_9 IS NOT NULL THEN 9 WHEN predessor_job_id_1 IS NOT NULL AND predessor_job_id_2 IS NOT NULL AND predessor_job_id_3 IS NOT NULL AND predessor_job_id_4 IS NOT NULL AND predessor_job_id_5 IS NOT NULL AND predessor_job_id_6 IS NOT NULL AND predessor_job_id_7 IS NOT NULL AND predessor_job_id_8 IS NOT NULL THEN 8 WHEN predessor_job_id_1 IS NOT NULL AND predessor_job_id_2 IS NOT NULL AND predessor_job_id_3 IS NOT NULL AND predessor_job_id_4 IS NOT NULL AND predessor_job_id_5 IS NOT NULL AND predessor_job_id_6 IS NOT NULL AND predessor_job_id_7 IS NOT NULL THEN 7 WHEN predessor_job_id_1 IS NOT NULL AND predessor_job_id_2 IS NOT NULL AND predessor_job_id_3 IS NOT NULL AND predessor_job_id_4 IS NOT NULL AND predessor_job_id_5 IS NOT NULL AND predessor_job_id_6 IS NOT NULL THEN 6 WHEN predessor_job_id_1 IS NOT NULL AND predessor_job_id_2 IS NOT NULL AND predessor_job_id_3 IS NOT NULL AND predessor_job_id_4 IS NOT NULL AND predessor_job_id_5 IS NOT NULL THEN 5 WHEN predessor_job_id_1 IS NOT NULL AND predessor_job_id_2 IS NOT NULL AND predessor_job_id_3 IS NOT NULL AND predessor_job_id_4 IS NOT NULL THEN 4 WHEN predessor_job_id_1 IS NOT NULL AND predessor_job_id_2 IS NOT NULL AND predessor_job_id_3 IS NOT NULL THEN 3 WHEN predessor_job_id_1 IS NOT NULL AND predessor_job_id_2 IS NOT NULL THEN 2 WHEN TRIM(predessor_job_id_1) IS NOT NULL THEN 1 ELSE 0 END AS dependent_job_count, 0 AS current_job_count, 'W' AS completed_flag FROM juniper_sch_current_job_detail WHERE TO_CHAR(batch_date, 'DD-MON-YY') = TO_CHAR(current_date, 'DD-MON-YY') AND predessor_job_id_1 IS NOT NULL AND batch_id=? ORDER BY job_schedule_time";
			pstm1 = conn.prepareStatement(insertDependentQuery);
			pstm1.setString(1, feedId);
			pstm1.executeUpdate();
			
			
			return "Success";
			} catch (ClassNotFoundException | SQLException e) {
				return "Failure";
			
			}finally {	
				conn.close();
			}
	}

	@Override
	public String runScheduleJob(@Valid String feedId, String jobId, String batchDate) throws Exception {
		
		Connection conn = null;
		int rs =0;
		PreparedStatement pstm =null;
		try {
		conn = ConnectionUtils.getConnection();
		String query = "update JUNIPER_SCH_CURRENT_JOB_DETAIL set last_update_ts=SYSTIMESTAMP, job_schedule_time=TO_CHAR(systimestamp, 'hh24:mi:ss'), status=NULL where job_id = ? and batch_id=? and TO_CHAR(batch_date,'DD-MON-YY')=?";
		pstm = conn.prepareStatement(query);
		pstm.setString(1, jobId);
		pstm.setString(2, feedId);
		pstm.setString(3, batchDate);
		rs = pstm.executeUpdate();
		return (rs + "Job run with FeedID: " + feedId + " and JobID: " + jobId + " on Batch Date: " + batchDate);
		}
		catch (Exception e) {
			//System.out.println(e.toString());
			return (e.toString());

		}finally {	
			conn.close();
		}
	}
	
	/**
	 * 
	 */
	@Override
	public String killCurrentJob(@Valid String feedId, String jobId, String batchDate) throws Exception {
		Connection conn = null;
		int rs =0;
		PreparedStatement pstm =null;
		try {
		conn = ConnectionUtils.getConnection();
		String query = "update "+ FEED_CURRENT_TABLE +" set last_update_ts=SYSTIMESTAMP, status='F' where job_id = ? and batch_id=? and TO_CHAR(batch_date,'DD-MON-YY')=?";
		pstm = conn.prepareStatement(query);
		pstm.setString(1, jobId);
		pstm.setString(2, feedId);
		pstm.setString(3, batchDate);
		 rs = pstm.executeUpdate();
		return (rs + "Killed job with FeedID: " + feedId + " and JobID: " + jobId + " on Batch Date: " + batchDate);
		}
		catch (Exception e) {
			//System.out.println(e.toString());
			return (e.toString());

		}finally {	
			conn.close();
		}
	}


/***
 * This method suspends job present in Master so it wont move to Current table ever.
 * @param feedId
 * @param jobId
 * @throws Exception 
 */
	@Override
	public String suspendJobFromMaster(String feedId) throws Exception {
		Connection conn = null;
		int rs =0;
		PreparedStatement pstm =null;
		try {
			conn = ConnectionUtils.getConnection();
			String suspendFromMasterQuery = "update JUNIPER_SCH_MASTER_JOB_DETAIL  set is_suspended='Y' where batch_id=?";
			pstm = conn.prepareStatement(suspendFromMasterQuery);
			pstm.setString(1, feedId);
			rs=pstm.executeUpdate();
			return (rs + " Jobs Suspended with FeedID: " + feedId);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return "Failure";
		}finally {	
			conn.close();
		}
		
	}

@Override
public String unSuspendJobFromMaster(@Valid String feedId) throws Exception {
	Connection conn = null;
	int rs =0;
	PreparedStatement pstm =null;
	try {
		conn = ConnectionUtils.getConnection();
		String suspendFromMasterQuery = "update JUNIPER_SCH_MASTER_JOB_DETAIL  set is_suspended='N' where batch_id=? and is_suspended='Y'";
		pstm = conn.prepareStatement(suspendFromMasterQuery);
		pstm.setString(1, feedId);
		rs=pstm.executeUpdate();
		return (rs + " Jobs Unsuspended with FeedID: " + feedId );
	} catch (ClassNotFoundException | SQLException e) {
		e.printStackTrace();
		return "Failure";
	}finally {	
		conn.close();
	}
	
}

@Override
public ArrayList<BatchDetailsDTO> getBatchDetails() {
	Connection connection=null;
	BatchDetailsDTO conn = null;
	PreparedStatement pstm =null;
	ArrayList<BatchDetailsDTO> arrBatchDetails = new ArrayList<BatchDetailsDTO>();
	try {
		connection = ConnectionUtils.getConnection();
		pstm = connection.prepareStatement(" select BATCH_UNIQUE_NAME from JUNIPER_SCH_BATCH_DETAILS");
		
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			conn = new BatchDetailsDTO();
			conn.setBATCH_UNIQUE_NAME(rs.getString(1));
			arrBatchDetails.add(conn);
		}
	} catch (Exception e) {
		e.printStackTrace();
	}finally {
		try {
			pstm.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	return arrBatchDetails;
	
}

@Override
public ArrayList<TaskSequenceDTO> getJobDetails(String batch_id,String project_id) {
	Connection connection=null;
	TaskSequenceDTO conn = null;
	PreparedStatement pstm = null;
	ArrayList<TaskSequenceDTO> arrBatchDetails = new ArrayList<TaskSequenceDTO>();
	try {
		connection = ConnectionUtils.getConnection();
		String query="select distinct job_name,BATCH_ID from JUNIPER_SCH_ADHOC_JOB_DETAIL where BATCH_ID='"+batch_id+"'"
				+" and project_id=(select PROJECT_SEQUENCE from JUNIPER_PROJECT_MASTER where PROJECT_ID='"+project_id+"')";
		pstm = connection.prepareStatement(query);
		
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			conn = new TaskSequenceDTO();
			conn.setJOB_NAME(rs.getString(1));
			conn.setBATCH_ID(rs.getString(2));		
			arrBatchDetails.add(conn);
		}
	} catch (Exception e) {
		e.printStackTrace();
	}finally {
		try {
			pstm.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	return arrBatchDetails;
	
}


@Override
public ArrayList<String> getKafkaTopic()
{
	ArrayList<String> arr = new ArrayList<String>();
	Connection connection=null;
	PreparedStatement pstm=null;
	try {
		connection = ConnectionUtils.getConnection();
		pstm = connection.prepareStatement("SELECT distinct kafka_topic FROM  juniper_ext_kafka_topic_master");
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			arr.add(rs.getString(1));
		}
	} catch (Exception e) {
		e.printStackTrace();
	}finally {
		try {
			pstm.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	return arr;
}

@Override
public ArrayList<String> getBatchJobs(String batch_id,String project_id)
{
	ArrayList<String> arr = new ArrayList<String>();
	String selectQuery="";
	Connection connection=null;
	try {
		connection = ConnectionUtils.getConnection();
		selectQuery="select JOB_NAME from JUNIPER_SCH_ADHOC_JOB_DETAIL where BATCH_ID='"+batch_id+"' and PROJECT_ID=(select project_sequence from JUNIPER_PROJECT_MASTER where PROJECT_ID='"+project_id+"')";
		System.out.println(selectQuery);
		PreparedStatement pstm = connection.prepareStatement(selectQuery);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			arr.add(rs.getString(1));
		}
		pstm.close();
	} catch (Exception e) {
		e.printStackTrace();
	}finally{
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	return arr;
}

@Override
public BatchTableDetailsDTO extractBatchDetails(String batch_id, String project_id) throws SQLException{
	BatchTableDetailsDTO arr = new BatchTableDetailsDTO();
	Connection connection=null;
	PreparedStatement pstm=null;
	try {
		connection = ConnectionUtils.getConnection();
		String selectQuery="select BATCH_ID,BATCH_UNIQUE_NAME,BATCH_DESCRIPTION,DAILY_FLAG,WEEKLY_FLAG,MONTHLY_FLAG,YEARLY_FLAG,JOB_SCHEDULE_TIME,"
				+"ARGUMENT_4,WEEK_RUN_DAY,MONTH_RUN_VAL,MONTH_RUN_DAY,PROJECT_ID,WEEK_NUM_MONTH,SCHEDULE_TYPE"
				+" from JUNIPER_SCH_BATCH_DETAILS where BATCH_UNIQUE_NAME='"+batch_id+"' and PROJECT_ID="
				+"(select project_sequence from JUNIPER_PROJECT_MASTER where PROJECT_ID='"+project_id+"')";
		pstm = connection.prepareStatement(selectQuery);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			arr.setBATCH_NAME(rs.getString("BATCH_UNIQUE_NAME"));
			arr.setBATCH_DESC(rs.getString("BATCH_DESCRIPTION"));
			arr.setDAILY_FLAG(rs.getString("DAILY_FLAG"));
			arr.setWEEKLY_FLAG(rs.getString("WEEKLY_FLAG"));
			arr.setMONTH_RUN_DAY(rs.getString("MONTHLY_FLAG"));
			arr.setYEARLY_FLAG(rs.getString("YEARLY_FLAG"));
			arr.setJOB_SCHEDULE_TIME(rs.getString("JOB_SCHEDULE_TIME"));
			arr.setArgument_4(rs.getString("ARGUMENT_4"));
			arr.setWEEK_NUM_MONTH(rs.getString("WEEK_NUM_MONTH"));
			arr.setMONTH_RUN_DAY(rs.getString("MONTH_RUN_DAY"));
			arr.setMONTH_RUN_VAL(rs.getString("MONTH_RUN_VAL"));
			arr.setWEEK_RUN_DAY(rs.getString("WEEK_RUN_DAY"));
			arr.setSCHEDULE_TYPE(rs.getString("SCHEDULE_TYPE"));
			arr.setProject_sequence(rs.getInt("PROJECT_ID"));
		}
	}catch (Exception e) {
		e.printStackTrace();
	}finally {
		try {
			pstm.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	connection.close();
	return arr;
}

@Override
public AdhocJobDTO extractBatchJobDetails(String batch_id, String project_id,String job_id) {
	AdhocJobDTO arr = new AdhocJobDTO();
	Connection connection=null;
	PreparedStatement pstm =null;
	try {
		connection = ConnectionUtils.getConnection();
		String selectQuery="select COMMAND,COMMAND_TYPE,ARGUMENT_1,ARGUMENT_2,ARGUMENT_3"
				+" from JUNIPER_SCH_ADHOC_JOB_DETAIL where BATCH_ID='"+batch_id+"' "
				+" and JOB_NAME='"+job_id+"'"	+ "and PROJECT_ID="
				+"(select project_sequence from JUNIPER_PROJECT_MASTER where PROJECT_ID='"+project_id+"')";
		pstm = connection.prepareStatement(selectQuery);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			arr.setJob_name(job_id);
			arr.setCommand(rs.getString("COMMAND"));
			arr.setCommand_type(rs.getString("COMMAND_TYPE"));
			arr.setArgument_1(rs.getString("ARGUMENT_1"));
			arr.setArgument_2(rs.getString("ARGUMENT_2"));
			arr.setArgument_3(rs.getString("ARGUMENT_3"));
		}
	} catch (Exception e) {
		e.printStackTrace();
	}finally {
		try {
			pstm.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	return arr;
}



}
