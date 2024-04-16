package carpedm.dto;

import java.sql.Date;

public class BannerDTO {
	int ban_id;
	String ban_imgurl;
	Date ban_startdate;
	Date ban_enddate;


	@Override
	public String toString() {
		return "BannerDTO [ban_id=" + ban_id + ", ban_imgurl=" + ban_imgurl + ", ban_startdate=" + ban_startdate
				+ ", ban_enddate=" + ban_enddate + "]";
	}
	public int getBan_id() {
		return ban_id;
	}

	public void setBan_id(int ban_id) {
		this.ban_id = ban_id;
	}

	public String getBan_imgurl() {
		return ban_imgurl;
	}

	public void setBan_imgurl(String ban_imgurl) {
		this.ban_imgurl = ban_imgurl;
	}

	public Date getBan_startdate() {
		return ban_startdate;
	}

	public void setBan_startdate(Date ban_startdate) {
		this.ban_startdate = ban_startdate;
	}

	public Date getBan_enddate() {
		return ban_enddate;
	}

	public void setBan_enddate(Date ban_enddate) {
		this.ban_enddate = ban_enddate;
	}

}