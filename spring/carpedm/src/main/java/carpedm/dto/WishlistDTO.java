package carpedm.dto;

import java.sql.Date;

public class WishlistDTO {
	int w_id;
	int lb_id;
	String w_title;
	int w_pubyear;
	String w_author;
	long w_isbn;
	String w_content;
	String w_publisher;
	String w_name;
	String w_tel;
	int m_pid;
	Date w_date;
	char w_state;
	
	// join 해야하는 필드 생성
	String lb_name;
	String m_id;

	//페이징용
	private int startrow;
	private int endrow;
	

	@Override
	public String toString() {
		return "WishlistDTO [w_id=" + w_id + ", lb_id=" + lb_id + ", w_title=" + w_title + ", w_pubyear=" + w_pubyear
				+ ", w_author=" + w_author + ", w_isbn=" + w_isbn + ", w_content=" + w_content + ", w_publisher="
				+ w_publisher + ", w_name=" + w_name + ", w_tel=" + w_tel + ", m_pid=" + m_pid + ", w_date=" + w_date
				+ ", w_state=" + w_state + ", lb_name=" + lb_name + ", m_id=" + m_id + ", startrow=" + startrow
				+ ", endrow=" + endrow + "]";
	}

	public int getStartrow() {
		return startrow;
	}

	public void setStartrow(int startrow) {
		this.startrow = startrow;
	}

	public int getEndrow() {
		return endrow;
	}

	public void setEndrow(int endrow) {
		this.endrow = endrow;
	}

	public int getW_id() {
		return w_id;
	}
	public void setW_id(int w_id) {
		this.w_id = w_id;
	}
	public int getLb_id() {
		return lb_id;
	}
	public void setLb_id(int lb_id) {
		this.lb_id = lb_id;
	}
	public String getW_title() {
		return w_title;
	}
	public void setW_title(String w_title) {
		this.w_title = w_title;
	}
	public int getW_pubyear() {
		return w_pubyear;
	}
	public void setW_pubyear(int w_pubyear) {
		this.w_pubyear = w_pubyear;
	}
	public String getW_author() {
		return w_author;
	}
	public void setW_author(String w_author) {
		this.w_author = w_author;
	}
	public long getW_isbn() {
		return w_isbn;
	}
	public void setW_isbn(long w_isbn) {
		this.w_isbn = w_isbn;
	}
	public String getW_content() {
		return w_content;
	}
	public void setW_content(String w_content) {
		this.w_content = w_content;
	}
	public String getW_publisher() {
		return w_publisher;
	}
	public void setW_publisher(String w_publisher) {
		this.w_publisher = w_publisher;
	}
	public String getW_name() {
		return w_name;
	}
	public void setW_name(String w_name) {
		this.w_name = w_name;
	}
	public String getW_tel() {
		return w_tel;
	}
	public void setW_tel(String w_tel) {
		this.w_tel = w_tel;
	}
	public int getM_pid() {
		return m_pid;
	}
	public void setM_pid(int m_pid) {
		this.m_pid = m_pid;
	}
	public Date getW_date() {
		return w_date;
	}
	public void setW_date(Date w_date) {
		this.w_date = w_date;
	}
	public char getW_state() {
		return w_state;
	}
	public void setW_state(char w_state) {
		this.w_state = w_state;
	}
	public String getLb_name() {
		return lb_name;
	}
	public void setLb_name(String lb_name) {
		this.lb_name = lb_name;
	}
	public String getM_id() {
		return m_id;
	}
	public void setM_id(String m_id) {
		this.m_id = m_id;
	}

}