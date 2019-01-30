package com.iig.gcp.scheduler.schedulerController.dto;

public class AdhocJobDTO {
	private int job_sequence;
	private String job_id;
	private String	 job_name;
	private String batch_id;
	private String command;
	private String command_type;
	private String  argument_1;
	private String argument_2;
	private String  argument_3;
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
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getCommand_type() {
		return command_type;
	}
	public void setCommand_type(String command_type) {
		this.command_type = command_type;
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
	
	

}
