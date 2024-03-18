package carpedm;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class Admin_member_chginfoDAO {
	private static final int EMPTY_EMPNO = -9999;
	
	private Connection con;

	
//	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	
//	private String driver = "oracle.jdbc.driver.OracleDriver";
//	private String url = "jdbc:oracle:thin:@112.148.46.134:51521:xe";
//	private String user = "carpedm";
//	private String password = "dm1113@";

	private void connDB() {
		try {
			Context ctx = new InitialContext();
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
			// DataSource로부터 Connection을 얻어옴
			this.con =  dataFactory.getConnection();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 맴버가져오기
//		private static ArrayList<Map<String,String>> getmember(String m_pid) {
//			ArrayList<Map<String,String>> result_list = new ArrayList<Map<String,String>>();
//			try {
//				Connection conn = getConnection();
//				// SQL준비
//				String query = "";
//				query += " select";
//				query += " *";
//				query += " from";
//				query += " member";
//				query += " where m_pid = " + m_pid;
	//
//				System.out.println("query:" + query);
//				// SQL 실행준비
//				PreparedStatement ps = conn.prepareStatement(query);
//				ResultSet rs = ps.executeQuery();
//				while (rs.next()) {
//					Map<String,String> map = new HashMap<String, String>();
//								
//					map.put("m_pid", rs.getString("M_PID"));
//					map.put("m_id", rs.getString("M_ID"));
//					map.put("m_pw", rs.getString("M_PW"));
//					map.put("m_name", rs.getString("M_NAME"));
//					map.put("m_tel", rs.getString("M_TEL"));
//					map.put("m_email", rs.getString("M_EMAIL"));
//					map.put("m_birthday", rs.getString("M_BIRTHDAY"));
//					map.put("m_address", rs.getString("M_ADDRESS"));
//					map.put("m_email_agree", rs.getString("M_EMAIL_AGREE"));
//					map.put("m_loanstate", rs.getString("M_LOANSTATE"));
//					map.put("m_managerchk", rs.getString("M_MANAGERCHK"));
//					map.put("lb_id", rs.getString("LB_ID"));
	//
//					result_list.add(map);
//				}
//				rs.close();
//				ps.close();
//				conn.close();
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			return result_list;
//		}
	List listBook() {
		return listBook(EMPTY_EMPNO);
	}
	
	public List listBook(int m_pid) {
		/* 꼭 써야함!! */
		connDB();
//		Connection con2 = connDB2();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		List list = new ArrayList();
		
		try {
			// SQL 준비
			String query = "select * from member";
			if(m_pid != EMPTY_EMPNO) {
				query += " where m_pid = ?";
			}
//			query += " where m_pid = " + m_pid;
			
			ps = con.prepareStatement(query);
			if(m_pid != EMPTY_EMPNO) {
				ps.setInt(1, m_pid);
			}
//			PreparedStatement ps = con2.prepareStatement(query);

			// SQL 실행 및 결과 확보
			rs = ps.executeQuery();
			
			// 결과 활용
			while(rs.next()) {
				int m_pid1 = rs.getInt("m_pid");
				String m_id = rs.getString("m_id");
				String m_pw = rs.getString("m_pw");
				String m_name = rs.getString("m_name");
				String m_tel = rs.getString("m_tel");
				String m_email = rs.getString("m_email");
				Date m_birthday = (Date)rs.getDate("m_birthday");
				String m_address = rs.getString("m_address");
				String m_email_agree = rs.getString("m_email_agree");
				int m_loanstate = rs.getInt("m_loanstate");
				String m_managerchk = rs.getString("m_managerchk");
				String lb_id = rs.getString("lb_id");
				
				System.out.println("m_pid : "+ m_pid1);
				
				admin_chginfo_BookDTO bookDTO = new admin_chginfo_BookDTO();
				bookDTO.setM_pid(m_pid1);
				bookDTO.setM_pid(m_pid1);
				bookDTO.setM_pw(m_pw);
				bookDTO.setM_name(m_name);
				bookDTO.setM_tel(m_tel);
				bookDTO.setM_email(m_email);
				bookDTO.setM_birthday(m_birthday);
				bookDTO.setM_address(m_address);
				bookDTO.setM_email_agree(m_email_agree);
				bookDTO.setM_loanstate(m_loanstate);
				bookDTO.setM_managerchk(m_managerchk);
				
				list.add(bookDTO);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(this.con != null) {
				try {
					this.con.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return list;

	}
	int insertBook(admin_chginfo_BookDTO bookDTO) {
		int result = -9999;
		
		/* 꼭 써야함!! */
		connDB();
		
		PreparedStatement ps = null;
		
		try {
			// SQL 준비
			String query = " insert into member (m_pid, m_id, m_pw, m_name)";
			query += 	   " values(?, ?, ?, ?) ";

			ps = con.prepareStatement(query);
			
			ps.setInt(1, bookDTO.getM_pid());
			ps.setString(2, bookDTO.getM_id());
			ps.setString(3, bookDTO.getM_pw());
			ps.setString(4, bookDTO.getM_name());

			// SQL 실행 및 결과 확보
			result = ps.executeUpdate();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(this.con != null) {
				try {
					this.con.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return result;
	}
		

	// 수정메소드
	int updateBook(admin_chginfo_BookDTO bookDTO) {
		int result = -9999;
		/* 꼭 써야함!! */
		connDB();
//		Connection con2 = connDB2();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			String query = "UPDATE member";
			query += " SET ";
			query += " M_pw=?, ";
			query += " M_name=?, ";
			query += " M_tel=?, ";
			query += " M_email=?, ";
			query += " M_birthday=?, ";
			query += " M_address=?, ";
			query += " M_email_agree=? ";
			query += " where m_id = ?";
			
			ps = con.prepareStatement(query);
			
			ps.setString(1, bookDTO.getM_pw());
			ps.setString(2, bookDTO.getM_name());
			ps.setString(3, bookDTO.getM_tel());
			ps.setString(4, bookDTO.getM_email());
			ps.setDate(5, (Date)bookDTO.getM_birthday());
			ps.setString(6, bookDTO.getM_address());
			ps.setString(7, bookDTO.getM_email_agree());
			ps.setString(8, bookDTO.getM_id());
			
			System.out.println("query:" + query);
			
			// SQL 실행 및 결과 확보
			result = ps.executeUpdate();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(this.con != null) {
				try {
					this.con.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return result;
	}
}
