package carpedm.dto;

import java.sql.Date;

public class LibraryDTO {
	String lb_id;
	String lb_name;
	String lb_address;
	String lb_tel;
	String lb_opentime;
	String lb_content;
	String lb_imgurl;

	@Override
	public String toString() {
		return "LibraryDTO [lb_id=" + lb_id + ", lb_name=" + lb_name + ", lb_address=" + lb_address + ", lb_tel="
				+ lb_tel + ", lb_opentime=" + lb_opentime + ", lb_content=" + lb_content + ", lb_imgurl=" + lb_imgurl
				+ "]";
	}

	public String getLb_id() {
		return lb_id;
	}

	public void setLb_id(String lb_id) {
		this.lb_id = lb_id;
	}

	public String getLb_name() {
		return lb_name;
	}

	public void setLb_name(String lb_name) {
		this.lb_name = lb_name;
	}

	public String getLb_address() {
		return lb_address;
	}

	public void setLb_address(String lb_address) {
		this.lb_address = lb_address;
	}

	public String getLb_tel() {
		return lb_tel;
	}

	public void setLb_tel(String lb_tel) {
		this.lb_tel = lb_tel;
	}

	public String getLb_opentime() {
		return lb_opentime;
	}

	public void setLb_opentime(String lb_opentime) {
		this.lb_opentime = lb_opentime;
	}

	public String getLb_content() {
		return lb_content;
	}

	public void setLb_content(String lb_content) {
		this.lb_content = lb_content;
	}

	public String getLb_imgurl() {
		return lb_imgurl;
	}

	public void setLb_imgurl(String lb_imgurl) {
		this.lb_imgurl = lb_imgurl;
	}
}