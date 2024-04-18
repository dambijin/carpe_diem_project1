package carpedm.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class OverdueDTO {

	int m_pid;
	String m_name;
	String b_title;
	String b_author;
	String lb_name;
	Date l_loanDate;
	Date l_returnDate;
	char b_resstate;
	char b_loanstate;
	
	
}
