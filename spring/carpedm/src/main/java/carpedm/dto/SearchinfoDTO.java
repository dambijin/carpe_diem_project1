package carpedm.dto;

import java.util.Date;

public class SearchinfoDTO {

	int si_id;
	String si_keyword;
	String si_opt;
	Date si_time;
	String si_ip;
	String m_pid;

	@Override
	public String toString() {
		return "SearchinfoDTO [si_id=" + si_id + ", si_keyword=" + si_keyword + ", si_opt=" + si_opt + ", si_time="
				+ si_time + ", si_ip=" + si_ip + ", m_pid=" + m_pid + "]";
	}

	public int getSi_id() {
		return si_id;
	}

	public String getM_pid() {
		return m_pid;
	}

	public void setM_pid(String m_pid) {
		this.m_pid = m_pid;
	}

	public void setSi_id(int si_id) {
		this.si_id = si_id;
	}

	public String getSi_keyword() {
		return si_keyword;
	}

	public void setSi_keyword(String si_keyword) {
		this.si_keyword = si_keyword;
	}

	public String getSi_opt() {
		return si_opt;
	}

	public void setSi_opt(String si_opt) {
		this.si_opt = si_opt;
	}

	public Date getSi_time() {
		return si_time;
	}

	public void setSi_time(Date si_time) {
		this.si_time = si_time;
	}


	public String getSi_ip() {
		return si_ip;
	}

	public void setSi_ip(String si_ip) {
		this.si_ip = si_ip;
	}
}