package carpedm.admin;

import java.sql.Date;

public class BookDTO {
	String lb_name;
	int lb_id;
	int b_id;
	String b_title;
	String b_author;
	int b_pubyear;
	long b_isbn;
	String b_publisher;
	String b_kywd;
//	String b_imgurl;
	char b_loanstate;
	char b_resstate;
	int bg_id;
	String b_like;
	String b_star;
	Date b_date;
	
	// 검색에 관한 변수
	private String type;
	private String keyword;
		
	// 정렬에 대한 변수
	private String orderColumn;
	private String orderType;
		
	//연체날짜계산
	private String m_limitdate_text;

	/**
	 * @return the b_id
	 */
	public int getB_id() {
		return b_id;
	}

	/**
	 * @param b_id the b_id to set
	 */
	public void setB_id(int b_id) {
		this.b_id = b_id;
	}

	/**
	 * @return the lb_id
	 */
	public int getLb_id() {
		return lb_id;
	}

	/**
	 * @param lb_id the lb_id to set
	 */
	public void setLb_id(int lb_id) {
		this.lb_id = lb_id;
	}

	/**
	 * @return the b_title
	 */
	public String getB_title() {
		return b_title;
	}

	/**
	 * @param b_title the b_title to set
	 */
	public void setB_title(String b_title) {
		this.b_title = b_title;
	}

	/**
	 * @return the b_author
	 */
	public String getB_author() {
		return b_author;
	}

	/**
	 * @param b_author the b_author to set
	 */
	public void setB_author(String b_author) {
		this.b_author = b_author;
	}

	/**
	 * @return the b_pubyear
	 */
	public int getB_pubyear() {
		return b_pubyear;
	}

	/**
	 * @param b_pubyear the b_pubyear to set
	 */
	public void setB_pubyear(int b_pubyear) {
		this.b_pubyear = b_pubyear;
	}

	/**
	 * @return the b_isbn
	 */
	public long getB_isbn() {
		return b_isbn;
	}

	/**
	 * @param b_isbn the b_isbn to set
	 */
	public void setB_isbn(long b_isbn) {
		this.b_isbn = b_isbn;
	}

	/**
	 * @return the b_pubisher
	 */
	public String getB_publisher() {
		return b_publisher;
	}

	/**
	 * @param b_pubisher the b_pubisher to set
	 */
	public void setB_publisher(String b_publisher) {
		this.b_publisher = b_publisher;
	}

	/**
	 * @return the b_kywd
	 */
	public String getB_kywd() {
		return b_kywd;
	}

	/**
	 * @param b_kywd the b_kywd to set
	 */
	public void setB_kywd(String b_kywd) {
		this.b_kywd = b_kywd;
	}

//	/**
//	 * @return the b_imgurl
//	 */
//	public String getB_imgurl() {
//		return b_imgurl;
//	}
//
//	/**
//	 * @param b_imgurl the b_imgurl to set
//	 */
//	public void setB_imgurl(String b_imgurl) {
//		this.b_imgurl = b_imgurl;
//	}

	/**
	 * @return the b_loanstate
	 */
	public int getB_loanstate() {
		return b_loanstate;
	}

	/**
	 * @param b_loanstate the b_loanstate to set
	 */
	public void setB_loanstate(char b_loanstate) {
		this.b_loanstate = b_loanstate;
	}

	/**
	 * @return the b_resstate
	 */
	public char getB_resstate() {
		return b_resstate;
	}

	/**
	 * @param b_resstate the b_resstate to set
	 */
	public void setB_resstate(char b_resstate) {
		this.b_resstate = b_resstate;
	}

	/**
	 * @return the bg_id
	 */
	public int getBg_id() {
		return bg_id;
	}

	/**
	 * @param bg_id the bg_id to set
	 */
	public void setBg_id(int bg_id) {
		this.bg_id = bg_id;
	}

	/**
	 * @return the b_like
	 */
	public String getB_like() {
		return b_like;
	}

	/**
	 * @param b_like the b_like to set
	 */
	public void setB_like(String b_like) {
		this.b_like = b_like;
	}

	/**
	 * @return the b_star
	 */
	public String getB_star() {
		return b_star;
	}

	/**
	 * @param b_star the b_star to set
	 */
	public void setB_star(String b_star) {
		this.b_star = b_star;
	}

	/**
	 * @return the b_date
	 */
	public Date getB_date() {
		return b_date;
	}

	/**
	 * @param b_date the b_date to set
	 */
	public void setB_date(Date b_date) {
		this.b_date = b_date;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the keyword
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * @param keyword the keyword to set
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/**
	 * @return the orderColumn
	 */
	public String getOrderColumn() {
		return orderColumn;
	}

	/**
	 * @param orderColumn the orderColumn to set
	 */
	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}

	/**
	 * @return the orderType
	 */
	public String getOrderType() {
		return orderType;
	}

	/**
	 * @param orderType the orderType to set
	 */
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	/**
	 * @return the m_limitdate_text
	 */
	public String getM_limitdate_text() {
		return m_limitdate_text;
	}

	/**
	 * @param m_limitdate_text the m_limitdate_text to set
	 */
	public void setM_limitdate_text(String m_limitdate_text) {
		this.m_limitdate_text = m_limitdate_text;
	}

	/**
	 * @return the lb_name
	 */
	public String getLb_name() {
		return lb_name;
	}

	/**
	 * @param lb_name the lb_name to set
	 */
	public void setLb_name(String lb_name) {
		this.lb_name = lb_name;
	}

	@Override
	public String toString() {
		return "BookDTO [lb_name=" + lb_name + ", lb_id=" + lb_id + ", b_id=" + b_id + ", b_title=" + b_title
				+ ", b_author=" + b_author + ", b_pubyear=" + b_pubyear + ", b_isbn=" + b_isbn + ", b_publisher="
				+ b_publisher + ", b_kywd=" + b_kywd + ", b_loanstate=" + b_loanstate + ", b_resstate=" + b_resstate
				+ ", bg_id=" + bg_id + ", b_like=" + b_like + ", b_star=" + b_star + ", b_date=" + b_date + ", type="
				+ type + ", keyword=" + keyword + ", orderColumn=" + orderColumn + ", orderType=" + orderType
				+ ", m_limitdate_text=" + m_limitdate_text + "]";
	}

	
		
	
}
