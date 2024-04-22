package carpedm.dto;

import java.util.Date;

public class BookDTO {

	int lb_id;
	int b_id;
	String b_title;
	String b_author;
	int b_pubyear;
	String b_isbn;
	String b_publisher;
	String b_kywd;
	String b_imgurl;
	char b_loanstate;

	char b_resstate;
	int bg_id;
	String b_like;
	String b_star;
	Date b_date;

	String lb_name;
	// 검색에 관한 변수
	private String type;
	private String keyword;

	// 정렬에 대한 변수
	private String orderColumn;
	private String orderType;

	// 연체날짜계산
	private String m_limitdate_text;

	//페이징용
	private int startrow;
	private int endrow;
	


	@Override
	public String toString() {
		return "BookDTO [lb_id=" + lb_id + ", b_id=" + b_id + ", b_title=" + b_title + ", b_author=" + b_author
				+ ", b_pubyear=" + b_pubyear + ", b_isbn=" + b_isbn + ", b_publisher=" + b_publisher + ", b_kywd="
				+ b_kywd + ", b_imgurl=" + b_imgurl + ", b_loanstate=" + b_loanstate + ", b_resstate=" + b_resstate
				+ ", bg_id=" + bg_id + ", b_like=" + b_like + ", b_star=" + b_star + ", b_date=" + b_date + ", lb_name="
				+ lb_name + ", type=" + type + ", keyword=" + keyword + ", orderColumn=" + orderColumn + ", orderType="
				+ orderType + ", m_limitdate_text=" + m_limitdate_text + ", startRow=" + startrow + ", endRow=" + endrow
				+ "]";
	}

	public int getStartRow() {
		return startrow;
	}

	public void setStartRow(int startRow) {
		this.startrow = startRow;
	}

	public int getEndRow() {
		return endrow;
	}

	public void setEndRow(int endRow) {
		this.endrow = endRow;
	}

	public int getLb_id() {
		return lb_id;
	}

	public void setLb_id(int lb_id) {
		this.lb_id = lb_id;
	}

	public int getB_id() {
		return b_id;
	}

	public void setB_id(int b_id) {
		this.b_id = b_id;
	}

	public String getB_title() {
		return b_title;
	}

	public void setB_title(String b_title) {
		this.b_title = b_title;
	}

	public String getB_author() {
		return b_author;
	}

	public void setB_author(String b_author) {
		this.b_author = b_author;
	}

	public int getB_pubyear() {
		return b_pubyear;
	}

	public void setB_pubyear(int b_pubyear) {
		this.b_pubyear = b_pubyear;
	}

	public String getB_isbn() {
		return b_isbn;
	}

	public void setB_isbn(String b_isbn) {
		this.b_isbn = b_isbn;
	}

	public String getB_publisher() {
		return b_publisher;
	}

	public void setB_publisher(String b_publisher) {
		this.b_publisher = b_publisher;
	}

	public String getB_kywd() {
		return b_kywd;
	}

	public void setB_kywd(String b_kywd) {
		this.b_kywd = b_kywd;
	}

	public String getB_imgurl() {
		return b_imgurl;
	}

	public void setB_imgurl(String b_imgurl) {
		this.b_imgurl = b_imgurl;
	}

	public char getB_loanstate() {
		return b_loanstate;
	}

	public void setB_loanstate(char b_loanstate) {
		this.b_loanstate = b_loanstate;
	}

	public char getB_resstate() {
		return b_resstate;
	}

	public void setB_resstate(char b_resstate) {
		this.b_resstate = b_resstate;
	}

	public int getBg_id() {
		return bg_id;
	}

	public void setBg_id(int bg_id) {
		this.bg_id = bg_id;
	}

	public String getB_like() {
		return b_like;
	}

	public void setB_like(String b_like) {
		this.b_like = b_like;
	}

	public String getB_star() {
		return b_star;
	}

	public void setB_star(String b_star) {
		this.b_star = b_star;
	}

	public Date getB_date() {
		return b_date;
	}

	public void setB_date(Date b_date) {
		this.b_date = b_date;
	}

	public String getLb_name() {
		return lb_name;
	}

	public void setLb_name(String lb_name) {
		this.lb_name = lb_name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getOrderColumn() {
		return orderColumn;
	}

	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getM_limitdate_text() {
		return m_limitdate_text;
	}

	public void setM_limitdate_text(String m_limitdate_text) {
		this.m_limitdate_text = m_limitdate_text;
	}




}
