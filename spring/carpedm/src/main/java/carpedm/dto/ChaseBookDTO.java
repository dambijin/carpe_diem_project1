package carpedm.dto;

import java.util.Date;

public class ChaseBookDTO {

	String bc_id;
	String m_pid;
	String b_id;
	Date b_time;
	String si_id;

	
	@Override
	public String toString() {
		return "ChaseBookDTO [bc_id=" + bc_id + ", m_pid=" + m_pid + ", b_id=" + b_id + ", b_time=" + b_time
				+ ", si_id=" + si_id + "]";
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
	public Date getB_time() {
		return b_time;
	}
	public void setB_time(Date b_time) {
		this.b_time = b_time;
	}
	public String getSi_id() {
		return si_id;
	}
	public void setSi_id(String si_id) {
		this.si_id = si_id;
	}


}