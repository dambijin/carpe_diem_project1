package carpedm.dto;

import java.sql.Date;

public class SearchinfoDTO {

	int si_id;
	String si_keyword;
	String si_opt;
	Date si_time;
	int b_id;
	String si_ip;

	@Override
	public String toString() {
		return "SearchinfoDTO [si_id=" + si_id + ", si_keyword=" + si_keyword + ", si_opt=" + si_opt + ", si_time="
				+ si_time + ", b_id=" + b_id + ", si_ip=" + si_ip + "]";
	}

	public int getSi_id() {
		return si_id;
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

	public int getB_id() {
		return b_id;
	}

	public void setB_id(int b_id) {
		this.b_id = b_id;
	}

	public String getSi_ip() {
		return si_ip;
	}

	public void setSi_ip(String si_ip) {
		this.si_ip = si_ip;
	}
}