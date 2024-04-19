package carpedm.dto;

import java.sql.Date;

public class NoticeBoardDTO {
	int n_id;
	int lb_id;
	int n_opt;
	String n_title;
	String n_content;
	Date n_date;
	int n_viewcount;
	String n_file;
	Date n_chgdate;
	int m_pid;
	int n_parent_id;
	String m_name;
	String lb_name;
	int lv;
	String m_tel; // 전화번호
	String search;
	String type;


	
	@Override
	public String toString() {
		return "NoticeBoardDTO [n_id=" + n_id + ", lb_id=" + lb_id + ", n_opt=" + n_opt + ", n_title=" + n_title
				+ ", n_content=" + n_content + ", n_date=" + n_date + ", n_viewcount=" + n_viewcount + ", n_file="
				+ n_file + ", n_chgdate=" + n_chgdate + ", m_pid=" + m_pid + ", n_parent_id=" + n_parent_id
				+ ", m_name=" + m_name + ", lb_name=" + lb_name + ", lv=" + lv + ", m_tel=" + m_tel + ", search="
				+ search + ", type=" + type + "]";
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getM_tel() {
		return m_tel;
	}

	public void setM_tel(String m_tel) {
		this.m_tel = m_tel;
	}

	public int getLv() {
		return lv;
	}

	public void setLv(int lv) {
		this.lv = lv;
	}

	public String getM_name() {
		return m_name;
	}

	public void setM_name(String m_name) {
		this.m_name = m_name;
	}

	public String getLb_name() {
		return lb_name;
	}

	public void setLb_name(String lb_name) {
		this.lb_name = lb_name;
	}

	public int getN_id() {
		return n_id;
	}

	public void setN_id(int n_id) {
		this.n_id = n_id;
	}

	public int getLb_id() {
		return lb_id;
	}

	public void setLb_id(int lb_id) {
		this.lb_id = lb_id;
	}

	public int getN_opt() {
		return n_opt;
	}

	public void setN_opt(int n_opt) {
		this.n_opt = n_opt;
	}

	public String getN_title() {
		return n_title;
	}

	public void setN_title(String n_title) {
		this.n_title = n_title;
	}

	public String getN_content() {
		return n_content;
	}

	public void setN_content(String n_content) {
		this.n_content = n_content;
	}

	public Date getN_date() {
		return n_date;
	}

	public void setN_date(Date n_date) {
		this.n_date = n_date;
	}

	public int getN_viewcount() {
		return n_viewcount;
	}

	public void setN_viewcount(int n_viewcount) {
		this.n_viewcount = n_viewcount;
	}

	public String getN_file() {
		return n_file;
	}

	public void setN_file(String n_file) {
		this.n_file = n_file;
	}

	public Date getN_chgdate() {
		return n_chgdate;
	}

	public void setN_chgdate(Date n_chgdate) {
		this.n_chgdate = n_chgdate;
	}

	public int getM_pid() {
		return m_pid;
	}

	public void setM_pid(int m_pid) {
		this.m_pid = m_pid;
	}

	public int getN_parent_id() {
		return n_parent_id;
	}

	public void setN_parent_id(int n_parent_id) {
		this.n_parent_id = n_parent_id;
	}
}