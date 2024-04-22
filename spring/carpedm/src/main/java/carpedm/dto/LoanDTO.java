package carpedm.dto;

import java.sql.Date;

public class LoanDTO {

	int l_id;
	int b_id;
	Date l_loandate;
	Date l_returndate;
	Date l_returnrealdate;
	int l_extendcount;
	int m_pid;
	String b_title;
	String b_author;
	String lb_name;
	char b_resstate;
	char b_loanstate;
	
	
	@Override
	public String toString() {
		return "LoanDTO [l_id=" + l_id + ", b_id=" + b_id + ", l_loandate=" + l_loandate + ", l_returndate="
				+ l_returndate + ", l_returnrealdate=" + l_returnrealdate + ", l_extendcount=" + l_extendcount
				+ ", m_pid=" + m_pid + ", b_title=" + b_title + ", b_author=" + b_author + ", lb_name=" + lb_name
				+ ", b_resstate=" + b_resstate + ", b_loanstate=" + b_loanstate + "]";
	}
	public int getL_id() {
		return l_id;
	}
	public void setL_id(int l_id) {
		this.l_id = l_id;
	}
	public int getB_id() {
		return b_id;
	}
	public void setB_id(int b_id) {
		this.b_id = b_id;
	}
	public Date getL_loandate() {
		return l_loandate;
	}
	public void setL_loandate(Date l_loandate) {
		this.l_loandate = l_loandate;
	}
	public Date getL_returndate() {
		return l_returndate;
	}
	public void setL_returndate(Date l_returndate) {
		this.l_returndate = l_returndate;
	}
	public Date getL_returnrealdate() {
		return l_returnrealdate;
	}
	public void setL_returnrealdate(Date l_returnrealdate) {
		this.l_returnrealdate = l_returnrealdate;
	}
	public int getL_extendcount() {
		return l_extendcount;
	}
	public void setL_extendcount(int l_extendcount) {
		this.l_extendcount = l_extendcount;
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
	public String getLb_name() {
		return lb_name;
	}
	public void setLb_name(String lb_name) {
		this.lb_name = lb_name;
	}
	public char getB_resstate() {
		return b_resstate;
	}
	public void setB_resstate(char b_resstate) {
		this.b_resstate = b_resstate;
	}
	public char getB_loanstate() {
		return b_loanstate;
	}
	public void setB_loanstate(char b_loanstate) {
		this.b_loanstate = b_loanstate;
	}

	
}