package carpedm;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Admin_member_chginfoDAO {
	
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@112.148.46.134:51521:xe";
//	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String user = "carpedm";
	private String password = "dm1113@";
	
	private Connection con;

	private void connDB() {
		try {
			// 오라클 드라이버 로딩
			Class.forName(this.driver);

			// DB 접속
			this.con = DriverManager.getConnection(this.url, this.user, this.password);

		} catch (Exception e) {
			// TODO Auto-generated catch block
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
	
	public void listEmp() {
		/* 꼭 써야함!! */
		connDB();
//		Connection con2 = connDB2();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			// SQL 준비
			String query = "select * from member";
//			query += " where m_pid = " + m_pid;
			
			ps = con.prepareStatement(query);
//			PreparedStatement ps = con2.prepareStatement(query);

			// SQL 실행 및 결과 확보
			rs = ps.executeQuery();
			
			// 결과 활용
			while(rs.next()) {
				String m_pid = rs.getString("m_pid");
				String m_id = rs.getString("m_id");
				String m_pw = rs.getString("m_pw");
				String m_name = rs.getString("m_name");
				String m_tel = rs.getString("m_tel");
				String m_email = rs.getString("m_email");
				String m_birthday = rs.getString("m_birthday");
				String m_address = rs.getString("m_address");
				String m_email_agree = rs.getString("m_email_agree");
				String m_loanstate = rs.getString("m_loanstate");
				String m_managerchk = rs.getString("m_managerchk");
				String lb_id = rs.getString("lb_id");
				
				System.out.println("m_pid : "+ m_pid);
				
				
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

	}
	
	// 수정메소드
	List change(String name, String date, String id, String pw, String number, String email1, String email2, String address) {
		/* 꼭 써야함!! */
		connDB();
//		Connection con2 = connDB2();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			String query = "update member set ename=?";
			query += " where is not null";
			
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			
			System.out.println("query:" + query);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return change(name, date, id, pw, number, email1, email2, address);
	}
}
