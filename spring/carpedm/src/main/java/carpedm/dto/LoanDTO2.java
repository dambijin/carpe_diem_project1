package carpedm.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class LoanDTO2 {
	
	int l_id;
	int b_id;
	Date l_loandate;
	Date l_returndate;
	Date l_returnrealdate;
	int l_extendcount;
	int m_pid;
	String b_title;
	String b_author;
	String lb_name;
	char b_resstate;
	char b_loanstate;
	  
}