package carpedm.dto;

import java.sql.Date;

public class ReservationDTO {
	int r_id;
	int b_id;
	Date r_resdate;
	char r_resstate;
	int m_pid;

	String b_title;
	String b_author;
	String b_publisher;
	String lb_name;
	
	
	@Override
	public String toString() {
		return "ReservationDTO [r_id=" + r_id + ", b_id=" + b_id + ", r_redate=" + r_resdate + ", r_resstate="
				+ r_resstate + ", m_pid=" + m_pid + ", b_title=" + b_title + ", b_author=" + b_author + ", b_publisher="
				+ b_publisher + ", lb_name=" + lb_name + "]";
	}
	public int getR_id() {
		return r_id;
	}
	public void setR_id(int r_id) {
		this.r_id = r_id;
	}
	public int getB_id() {
		return b_id;
	}
	public void setB_id(int b_id) {
		this.b_id = b_id;
	}
	public Date getR_resdate() {
		return r_resdate;
	}
	public void setR_resdate(Date r_resdate) {
		this.r_resdate = r_resdate;
	}
	public char getR_resstate() {
		return r_resstate;
	}
	public void setR_resstate(char r_resstate) {
		this.r_resstate = r_resstate;
	}
	public int getM_pid() {
		return m_pid;
	}
	public void setM_pid(int m_pid) {
		this.m_pid = m_pid;
	}
	public String getB_title() {
		return b_title;
	}
	public void setB_title(String b_title) {
		this.b_title = b_title;
	}
	public String getB_author() {
		return b_author;
	}
	public void setB_author(String b_author) {
		this.b_author = b_author;
	}
	public String getB_publisher() {
		return b_publisher;
	}
	public void setB_publisher(String b_publisher) {
		this.b_publisher = b_publisher;
	}
	public String getLb_name() {
		return lb_name;
	}
	public void setLb_name(String lb_name) {
		this.lb_name = lb_name;
	}
	
	

}