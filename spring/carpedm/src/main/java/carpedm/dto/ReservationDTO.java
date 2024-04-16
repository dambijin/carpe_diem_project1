package carpedm.dto;

import java.sql.Date;

public class ReservationDTO {
	int r_id;
	int b_id;
	Date r_redate;
	char r_resstate;
	int m_pid;

	@Override
	public String toString() {
		return "ReservationDTO [r_id=" + r_id + ", b_id=" + b_id + ", r_redate=" + r_redate + ", r_resstate="
				+ r_resstate + ", m_pid=" + m_pid + "]";
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

	public Date getR_redate() {
		return r_redate;
	}

	public void setR_redate(Date r_redate) {
		this.r_redate = r_redate;
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

}