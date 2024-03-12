package carpedm;

import java.sql.Date;

public class Admin_member_chginfoDTO {
//	EMPNO	NUMBER(4,0)
//	ENAME	VARCHAR2(10 BYTE)
//	JOB	VARCHAR2(9 BYTE)
//	MGR	NUMBER(4,0)
//	HIREDATE	DATE
//	SAL	NUMBER(7,2)
//	COMM	NUMBER(7,2)
//	DEPTNO	NUMBER(2,0)
	
	String m_pid;
	String m_id;
	String m_pw;
	String m_name;
	String m_tel;
	String m_email;
	String m_birthday;
	String m_address;
	String m_email_agree;
	String m_loanstate;
	String m_managerchk;
	String lb_id;
	
	public String getM_pid() {
		return m_pid;
	}
	public void setM_pid(String m_pid) {
		this.m_pid = m_pid;
	}
	public String getM_id() {
		return m_id;
	}
	public void setM_id(String m_id) {
		this.m_id = m_id;
	}
	public String getM_pw() {
		return m_pw;
	}
	public void setM_pw(String m_pw) {
		this.m_pw = m_pw;
	}
	public String getM_name() {
		return m_name;
	}
	public void setM_name(String m_name) {
		this.m_name = m_name;
	}
	public String getM_tel() {
		return m_tel;
	}
	public void setM_tel(String m_tel) {
		this.m_tel = m_tel;
	}
	public String getM_email() {
		return m_email;
	}
	public void setM_email(String m_email) {
		this.m_email = m_email;
	}
	public String getM_birthday() {
		return m_birthday;
	}
	public void setM_birthday(String m_birthday) {
		this.m_birthday = m_birthday;
	}
	public String getM_address() {
		return m_address;
	}
	public void setM_address(String m_address) {
		this.m_address = m_address;
	}
	public String getM_email_agree() {
		return m_email_agree;
	}
	public void setM_email_agree(String m_email_agree) {
		this.m_email_agree = m_email_agree;
	}
	public String getM_loanstate() {
		return m_loanstate;
	}
	public void setM_loanstate(String m_loanstate) {
		this.m_loanstate = m_loanstate;
	}
	public String getM_managerchk() {
		return m_managerchk;
	}
	public void setM_managerchk(String m_managerchk) {
		this.m_managerchk = m_managerchk;
	}
	public String getLb_id() {
		return lb_id;
	}
	public void setLb_id(String lb_id) {
		this.lb_id = lb_id;
	}
	
	
//	@Override
//	public String toString() {
//		return "EmpDTO [empno=" + empno + ", ename=" + ename + ", job=" + job + ", mgr=" + mgr + ", hiredate="
//				+ hiredate + ", sal=" + sal + ", comm=" + comm + ", deptno=" + deptno + "]";
//	}
	
	
}
