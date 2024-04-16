package carpedm.dto;

import java.sql.Date;

public class BookgenreDTO {
	int bg_id;
	String bg_name;

	@Override
	public String toString() {
		return "BookgenreDTO [bg_id=" + bg_id + ", bg_name=" + bg_name + "]";
	}

	public int getBg_id() {
		return bg_id;
	}

	public void setBg_id(int bg_id) {
		this.bg_id = bg_id;
	}

	public String getBg_name() {
		return bg_name;
	}

	public void setBg_name(String bg_name) {
		this.bg_name = bg_name;
	}
}