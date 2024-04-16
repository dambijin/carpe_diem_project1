package carpedm.admin;

import java.sql.Date;

public class WishListDTO {
	
	int w_id;
	int lb_id;
	String w_title;
	String w_pubyear;
	String w_author;
	long w_isbn;
	String w_content;
	String w_publisher;
	String w_name;
	String w_tel;
	int w_pid;
	Date w_date;
	String w_state;
	
	
	/**
	 * @return the w_id
	 */
	public int getW_id() {
		return w_id;
	}
	/**
	 * @param w_id the w_id to set
	 */
	public void setW_id(int w_id) {
		this.w_id = w_id;
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
	 * @return the w_title
	 */
	public String getW_title() {
		return w_title;
	}
	/**
	 * @param w_title the w_title to set
	 */
	public void setW_title(String w_title) {
		this.w_title = w_title;
	}
	/**
	 * @return the w_pubyear
	 */
	public String getW_pubyear() {
		return w_pubyear;
	}
	/**
	 * @param w_pubyear the w_pubyear to set
	 */
	public void setW_pubyear(String w_pubyear) {
		this.w_pubyear = w_pubyear;
	}
	/**
	 * @return the w_author
	 */
	public String getW_author() {
		return w_author;
	}
	/**
	 * @param w_author the w_author to set
	 */
	public void setW_author(String w_author) {
		this.w_author = w_author;
	}
	/**
	 * @return the w_isbn
	 */
	public long getW_isbn() {
		return w_isbn;
	}
	/**
	 * @param w_isbn the w_isbn to set
	 */
	public void setW_isbn(long w_isbn) {
		this.w_isbn = w_isbn;
	}
	/**
	 * @return the w_content
	 */
	public String getW_content() {
		return w_content;
	}
	/**
	 * @param w_content the w_content to set
	 */
	public void setW_content(String w_content) {
		this.w_content = w_content;
	}
	/**
	 * @return the w_publisher
	 */
	public String getW_publisher() {
		return w_publisher;
	}
	/**
	 * @param w_publisher the w_publisher to set
	 */
	public void setW_publisher(String w_publisher) {
		this.w_publisher = w_publisher;
	}
	/**
	 * @return the w_name
	 */
	public String getW_name() {
		return w_name;
	}
	/**
	 * @param w_name the w_name to set
	 */
	public void setW_name(String w_name) {
		this.w_name = w_name;
	}
	/**
	 * @return the w_tel
	 */
	public String getW_tel() {
		return w_tel;
	}
	/**
	 * @param w_tel the w_tel to set
	 */
	public void setW_tel(String w_tel) {
		this.w_tel = w_tel;
	}
	/**
	 * @return the w_pid
	 */
	public int getW_pid() {
		return w_pid;
	}
	/**
	 * @param w_pid the w_pid to set
	 */
	public void setW_pid(int w_pid) {
		this.w_pid = w_pid;
	}
	/**
	 * @return the w_date
	 */
	public Date getW_date() {
		return w_date;
	}
	/**
	 * @param w_date the w_date to set
	 */
	public void setW_date(Date w_date) {
		this.w_date = w_date;
	}
	/**
	 * @return the w_state
	 */
	public String getW_state() {
		return w_state;
	}
	/**
	 * @param w_state the w_state to set
	 */
	public void setW_state(String w_state) {
		this.w_state = w_state;
	}
	@Override
	public String toString() {
		return "WishListDTO [w_id=" + w_id + ", lb_id=" + lb_id + ", w_title=" + w_title + ", w_pubyear=" + w_pubyear
				+ ", w_author=" + w_author + ", w_isbn=" + w_isbn + ", w_content=" + w_content + ", w_publisher="
				+ w_publisher + ", w_name=" + w_name + ", w_tel=" + w_tel + ", w_pid=" + w_pid + ", w_date=" + w_date
				+ ", w_state=" + w_state + "]";
	}
	
	
}
