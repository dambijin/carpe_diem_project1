package carpedm.dto;

import java.sql.Date;

public class LoanDTO {
	  int l_id;
	    int b_id;
	    Date l_loandate;
	    Date l_returndate;
	    Date l_returnrealdate;
	    int l_extendcount;
	    int m_pid;
		public int getL_id() {
			return l_id;
		}
		public void setL_id(int l_id) {
			this.l_id = l_id;
		}
		public int getB_id() {
			return b_id;
		}
		public void setB_id(int b_id) {
			this.b_id = b_id;
		}
		public Date getL_loandate() {
			return l_loandate;
		}
		public void setL_loandate(Date l_loandate) {
			this.l_loandate = l_loandate;
		}
		public Date getL_returndate() {
			return l_returndate;
		}
		public void setL_returndate(Date l_returndate) {
			this.l_returndate = l_returndate;
		}
		public Date getL_returnrealdate() {
			return l_returnrealdate;
		}
		public void setL_returnrealdate(Date l_returnrealdate) {
			this.l_returnrealdate = l_returnrealdate;
		}
		public int getL_extendcount() {
			return l_extendcount;
		}
		public void setL_extendcount(int l_extendcount) {
			this.l_extendcount = l_extendcount;
		}
		public int getM_pid() {
			return m_pid;
		}
		public void setM_pid(int m_pid) {
			this.m_pid = m_pid;
		}
		@Override
		public String toString() {
			return "LoanDTO [l_id=" + l_id + ", b_id=" + b_id + ", l_loandate=" + l_loandate + ", l_returndate="
					+ l_returndate + ", l_returnrealdate=" + l_returnrealdate + ", l_extendcount=" + l_extendcount
					+ ", m_pid=" + m_pid + "]";
		}
}