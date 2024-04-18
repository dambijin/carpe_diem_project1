package carpedm.dto;

import java.sql.Date;

public class ReservationDTO2 {
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
		return "ReservationDTO [r_id=" + r_id + ", b_id=" + b_id + ", r_resdate=" + r_resdate + ", r_resstate="
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

	public Date getR_resdate() {
		return r_resdate;
	}

	public void setR_redate(Date r_redate) {
		this.r_resdate = r_redate;
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

	/**
	 * @return the b_title
	 */
	public String getB_title() {
		return b_title;
	}

	/**
	 * @param b_title the b_title to set
	 */
	public void setB_title(String b_title) {
		this.b_title = b_title;
	}

	/**
	 * @return the b_author
	 */
	public String getB_author() {
		return b_author;
	}

	/**
	 * @param b_author the b_author to set
	 */
	public void setB_author(String b_author) {
		this.b_author = b_author;
	}

	/**
	 * @return the b_publisher
	 */
	public String getB_publisher() {
		return b_publisher;
	}

	/**
	 * @param b_publisher the b_publisher to set
	 */
	public void setB_publisher(String b_publisher) {
		this.b_publisher = b_publisher;
	}

	/**
	 * @return the lb_name
	 */
	public String getLb_name() {
		return lb_name;
	}

	/**
	 * @param lb_name the lb_name to set
	 */
	public void setLb_name(String lb_name) {
		this.lb_name = lb_name;
	}

}