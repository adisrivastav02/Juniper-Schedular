package com.iig.gcp.scheduler.schedulerController.dto;

public class MasterJobsDTO {
	private int job_sequence;
	private String job_id;
	private String job_name;
	private String batch_id;
	private String pre_processing;
	private String post_processing;
	private String command;
	private String argument_1;
	private String argument_2;
	private String argument_3;
	private String argument_4;
	private String argument_5;
	private char daily_flag;
	private char weekly_flag;
	private char monthly_flag;
	private char yearly_flag;
	private String job_schedule_time;
	private String predessor_job_id_1;
	private String predessor_job_id_2;
	private String predessor_job_id_3;
	private String predessor_job_id_4;
	private String predessor_job_id_5;
	private String predessor_job_id_6;
	private String predessor_job_id_7;
	private String predessor_job_id_8;
	private String predessor_job_id_9;
	private String predessor_job_id_10;
	private String is_dependent_job;
	private String week_run_day;
	private String month_run_val;
	private String month_run_day;
	private String command_type;
	private String target_emid;
	private String source_emid;
	private String feed_id;
	private String project_id;
	private int week_num_month;
	private String schedule;
	private String consolidatedSchedule;
	private String is_suspended;
	private String in_current;
	
	
	
	
	public String getIs_suspended() {
		return is_suspended;
	}
	public void setIs_suspended(String is_suspended) {
		this.is_suspended = is_suspended;
	}
	public String getIn_current() {
		return in_current;
	}
	public void setIn_current(String in_current) {
		this.in_current = in_current;
	}
	public String getSchedule() {
		return schedule;
	}
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}
	public String getConsolidatedSchedule() {
		return consolidatedSchedule;
	}
	public void setConsolidatedSchedule(String consolidatedSchedule) {
		this.consolidatedSchedule = consolidatedSchedule;
	}
	public int getJob_sequence() {
		return job_sequence;
	}
	public void setJob_sequence(int job_sequence) {
		this.job_sequence = job_sequence;
	}
	public String getJob_id() {
		return job_id;
	}
	public void setJob_id(String job_id) {
		this.job_id = job_id;
	}
	public String getJob_name() {
		return job_name;
	}
	public void setJob_name(String job_name) {
		this.job_name = job_name;
	}
	public String getBatch_id() {
		return batch_id;
	}
	public void setBatch_id(String batch_id) {
		this.batch_id = batch_id;
	}
	public String getPre_processing() {
		return pre_processing;
	}
	public void setPre_processing(String pre_processing) {
		this.pre_processing = pre_processing;
	}
	public String getPost_processing() {
		return post_processing;
	}
	public void setPost_processing(String post_processing) {
		this.post_processing = post_processing;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getArgument_1() {
		return argument_1;
	}
	public void setArgument_1(String argument_1) {
		this.argument_1 = argument_1;
	}
	public String getArgument_2() {
		return argument_2;
	}
	public void setArgument_2(String argument_2) {
		this.argument_2 = argument_2;
	}
	public String getArgument_3() {
		return argument_3;
	}
	public void setArgument_3(String argument_3) {
		this.argument_3 = argument_3;
	}
	public String getArgument_4() {
		return argument_4;
	}
	public void setArgument_4(String argument_4) {
		this.argument_4 = argument_4;
	}
	public String getArgument_5() {
		return argument_5;
	}
	public void setArgument_5(String argument_5) {
		this.argument_5 = argument_5;
	}
	public char getDaily_flag() {
		return daily_flag;
	}
	public void setDaily_flag(char daily_flag) {
		this.daily_flag = daily_flag;
	}
	public char getWeekly_flag() {
		return weekly_flag;
	}
	public void setWeekly_flag(char weekly_flag) {
		this.weekly_flag = weekly_flag;
	}
	public char getMonthly_flag() {
		return monthly_flag;
	}
	public void setMonthly_flag(char monthly_flag) {
		this.monthly_flag = monthly_flag;
	}
	public char getYearly_flag() {
		return yearly_flag;
	}
	public void setYearly_flag(char yearly_flag) {
		this.yearly_flag = yearly_flag;
	}
	public String getPredessor_job_id_1() {
		return predessor_job_id_1;
	}
	public void setPredessor_job_id_1(String predessor_job_id_1) {
		this.predessor_job_id_1 = predessor_job_id_1;
	}
	public String getPredessor_job_id_2() {
		return predessor_job_id_2;
	}
	public void setPredessor_job_id_2(String predessor_job_id_2) {
		this.predessor_job_id_2 = predessor_job_id_2;
	}
	public String getPredessor_job_id_3() {
		return predessor_job_id_3;
	}
	public void setPredessor_job_id_3(String predessor_job_id_3) {
		this.predessor_job_id_3 = predessor_job_id_3;
	}
	public String getPredessor_job_id_4() {
		return predessor_job_id_4;
	}
	public void setPredessor_job_id_4(String predessor_job_id_4) {
		this.predessor_job_id_4 = predessor_job_id_4;
	}
	public String getPredessor_job_id_5() {
		return predessor_job_id_5;
	}
	public void setPredessor_job_id_5(String predessor_job_id_5) {
		this.predessor_job_id_5 = predessor_job_id_5;
	}
	public String getPredessor_job_id_6() {
		return predessor_job_id_6;
	}
	public void setPredessor_job_id_6(String predessor_job_id_6) {
		this.predessor_job_id_6 = predessor_job_id_6;
	}
	public String getPredessor_job_id_7() {
		return predessor_job_id_7;
	}
	public void setPredessor_job_id_7(String predessor_job_id_7) {
		this.predessor_job_id_7 = predessor_job_id_7;
	}
	public String getPredessor_job_id_8() {
		return predessor_job_id_8;
	}
	public void setPredessor_job_id_8(String predessor_job_id_8) {
		this.predessor_job_id_8 = predessor_job_id_8;
	}
	public String getPredessor_job_id_9() {
		return predessor_job_id_9;
	}
	public void setPredessor_job_id_9(String predessor_job_id_9) {
		this.predessor_job_id_9 = predessor_job_id_9;
	}
	public String getPredessor_job_id_10() {
		return predessor_job_id_10;
	}
	public void setPredessor_job_id_10(String predessor_job_id_10) {
		this.predessor_job_id_10 = predessor_job_id_10;
	}
	public String getIs_dependent_job() {
		return is_dependent_job;
	}
	public void setIs_dependent_job(String is_dependent_job) {
		this.is_dependent_job = is_dependent_job;
	}
	public String getWeek_run_day() {
		return week_run_day;
	}
	public void setWeek_run_day(String week_run_day) {
		this.week_run_day = week_run_day;
	}
	public String getMonth_run_val() {
		return month_run_val;
	}
	public void setMonth_run_val(String month_run_val) {
		this.month_run_val = month_run_val;
	}
	public String getMonth_run_day() {
		return month_run_day;
	}
	public void setMonth_run_day(String month_run_day) {
		this.month_run_day = month_run_day;
	}
	public String getCommand_type() {
		return command_type;
	}
	public void setCommand_type(String command_type) {
		this.command_type = command_type;
	}
	public String getJob_schedule_time() {
		return job_schedule_time;
	}
	public void setJob_schedule_time(String job_schedule_time) {
		this.job_schedule_time = job_schedule_time;
	}
	public String getTarget_emid() {
		return target_emid;
	}
	public void setTarget_emid(String target_emid) {
		this.target_emid = target_emid;
	}
	public String getSource_emid() {
		return source_emid;
	}
	public void setSource_emid(String source_emid) {
		this.source_emid = source_emid;
	}
	public String getFeed_id() {
		return feed_id;
	}
	public void setFeed_id(String feed_id) {
		this.feed_id = feed_id;
	}
	public String getProject_id() {
		return project_id;
	}
	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}
	public int getWeek_num_month() {
		return week_num_month;
	}
	public void setWeek_num_month(int week_num_month) {
		this.week_num_month = week_num_month;
	}    

}
