package com.iig.gcp.scheduler.schedulerController.dto;

import java.util.Date;

public class ArchiveJobsDTO {
	private int job_sequence;
	private String job_id;
	private String	 job_name;
	private String batch_id;
	private String batch_date;
	private String pre_processing;
	private String post_processing;
	private String command;
	private String  argument_1;
	private String argument_2;
	private String  argument_3;
	private String argument_4;
	private String argument_5;
	private char daily_flag;
	private char weekly_flag;
	private char monthly_flag;
	private char yearly_flag;
	private String job_schedule_time;
	private String predessor_job_id_1;
	private String predessor_job_id_2;
	private String  predessor_job_id_3;
	private String  predessor_job_id_4;
	private String  predessor_job_id_5;
	private String  predessor_job_id_6;
	private String  predessor_job_id_7;
	private String  predessor_job_id_8;
	private String  predessor_job_id_9;
	private String  predessor_job_id_10;
	private String  is_dependent_job;
	private String  week_run_day;
	private String month_run_val;
	private String  month_run_day;
	private String command_type;
	private String status;
	private String  start_time;
	private String  end_time;
	private String last_update_ts;
	private int script_pointer;
	private String worker_id;
	private String duration;
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
	public String getBatch_date() {
		return batch_date;
	}
	public void setBatch_date(String batch_date) {
		this.batch_date = batch_date;
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
	public String getJob_schedule_time() {
		return job_schedule_time;
	}
	public void setJob_schedule_time(String job_schedule_time) {
		this.job_schedule_time = job_schedule_time;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getLast_update_ts() {
		return last_update_ts;
	}
	public void setLast_update_ts(String last_update_ts) {
		this.last_update_ts = last_update_ts;
	}
	public int getScript_pointer() {
		return script_pointer;
	}
	public void setScript_pointer(int script_pointer) {
		this.script_pointer = script_pointer;
	}
	public String getWorker_id() {
		return worker_id;
	}
	public void setWorker_id(String worker_id) {
		this.worker_id = worker_id;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
}
