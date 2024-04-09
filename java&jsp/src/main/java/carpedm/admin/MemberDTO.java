package carpedm.admin;

import java.sql.Date;

public class MemberDTO {

	int m_pid;
	String m_id;
	String m_pw;
	String m_name;
	String m_tel;
	String m_email;
	Date m_birthday;
	String m_address;
	String m_email_agree;
	Date m_limitdate;
	String m_managerchk;
	
	// 검색에 관한 변수
	private String type;
	private String keyword;
	
	// 정렬에 대한 변수
	String orderColumn;
	String orderType;
	
	//연체날짜계산
	String m_limitdate_text;
	
	/**
	 * @return the m_limitdate_text
	 */
	public String getM_limitdate_text() {
		return m_limitdate_text;
	}
	/**
	 * @param m_limitdate_text the m_limitdate_text to set
	 */
	public void setM_limitdate_text(String m_limitdate_text) {
		this.m_limitdate_text = m_limitdate_text;
	}
	/**
	 * @return the m_pid
	 */
	public int getM_pid() {
		return m_pid;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the keyword
	 */
	public String getKeyword() {
		return keyword;
	}
	/**
	 * @param keyword the keyword to set
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	/**
	 * @return the orderColumn
	 */
	public String getOrderColumn() {
		return orderColumn;
	}
	/**
	 * @param orderColumn the orderColumn to set
	 */
	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}
	/**
	 * @return the orderType
	 */
	public String getOrderType() {
		return orderType;
	}
	/**
	 * @param orderType the orderType to set
	 */
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	/**
	 * @param m_pid the m_pid to set
	 */
	public void setM_pid(int m_pid) {
		this.m_pid = m_pid;
	}
	/**
	 * @return the m_id
	 */
	public String getM_id() {
		return m_id;
	}
	/**
	 * @param m_id the m_id to set
	 */
	public void setM_id(String m_id) {
		this.m_id = m_id;
	}
	/**
	 * @return the m_pw
	 */
	public String getM_pw() {
		return m_pw;
	}
	/**
	 * @param m_pw the m_pw to set
	 */
	public void setM_pw(String m_pw) {
		this.m_pw = m_pw;
	}
	/**
	 * @return the m_name
	 */
	public String getM_name() {
		return m_name;
	}
	/**
	 * @param m_name the m_name to set
	 */
	public void setM_name(String m_name) {
		this.m_name = m_name;
	}
	/**
	 * @return the m_tel
	 */
	public String getM_tel() {
		return m_tel;
	}
	/**
	 * @param m_tel the m_tel to set
	 */
	public void setM_tel(String m_tel) {
		this.m_tel = m_tel;
	}
	/**
	 * @return the m_email
	 */
	public String getM_email() {
		return m_email;
	}
	/**
	 * @param m_email the m_email to set
	 */
	public void setM_email(String m_email) {
		this.m_email = m_email;
	}
	/**
	 * @return the m_birthday
	 */
	public Date getM_birthday() {
		return m_birthday;
	}
	/**
	 * @param m_birthday the m_birthday to set
	 */
	public void setM_birthday(Date m_birthday) {
		this.m_birthday = m_birthday;
	}
	/**
	 * @return the m_address
	 */
	public String getM_address() {
		return m_address;
	}
	/**
	 * @param m_address the m_address to set
	 */
	public void setM_address(String m_address) {
		this.m_address = m_address;
	}
	/**
	 * @return the m_email_agree
	 */
	public String getM_email_agree() {
		return m_email_agree;
	}
	/**
	 * @param m_email_agree2 the m_email_agree to set
	 */
	public void setM_email_agree(String m_email_agree2) {
		this.m_email_agree = m_email_agree2;
	}
	/**
	 * @return the m_loanstate
	 */
	public Date getM_limitdate() {
		return m_limitdate;
	}
	/**
	 * @param m_loanstate the m_loanstate to set
	 */
	public void setM_limitdate(Date m_limitdate) {
		this.m_limitdate = m_limitdate;
	}
	/**
	 * @return the m_managerchk
	 */
	public String getM_managerchk() {
		return m_managerchk;
	}
	/**
	 * @param m_managerchk2 the m_managerchk to set
	 */
	public void setM_managerchk(String m_managerchk2) {
		this.m_managerchk = m_managerchk2;
	}
	
	@Override
	public String toString() {
		return "MemberDTO [m_pid=" + m_pid + ", m_id=" + m_id + ", m_pw=" + m_pw + ", m_name=" + m_name + ", m_tel="
				+ m_tel + ", m_email=" + m_email + ", m_birthday=" + m_birthday + ", m_address=" + m_address
				+ ", m_email_agree=" + m_email_agree + ", m_limitdate=" + m_limitdate + ", m_managerchk=" + m_managerchk
				+ ", type=" + type + ", keyword=" + keyword + ", orderColumn=" + orderColumn + ", orderType="
				+ orderType + "]";
	}
}