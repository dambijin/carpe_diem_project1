package carpedm.dto;

import java.util.Date;

public class BookCartDTO {

	String bc_id;
	String m_pid;
	String b_id;
	Date bc_date;

	@Override
	public String toString() {
		return "BookCartDTO [bc_id=" + bc_id + ", m_pid=" + m_pid + ", b_id=" + b_id + ", bc_date=" + bc_date + "]";
	}
	public String getBc_id() {
		return bc_id;
	}
	public void setBc_id(String bc_id) {
		this.bc_id = bc_id;
	}
	public String getM_pid() {
		return m_pid;
	}
	public void setM_pid(String m_pid) {
		this.m_pid = m_pid;
	}
	public String getB_id() {
		return b_id;
	}
	public void setB_id(String b_id) {
		this.b_id = b_id;
	}
	public Date getBc_date() {
		return bc_date;
	}
	public void setBc_date(Date bc_date) {
		this.bc_date = bc_date;
	}
	
	
}