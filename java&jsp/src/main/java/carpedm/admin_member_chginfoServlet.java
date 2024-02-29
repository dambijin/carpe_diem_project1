package carpedm;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/admin_member_chginfo")
public class admin_member_chginfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String URL = "jdbc:oracle:thin:@112.148.46.134:51521:xe";
	private static final String USER = "carpedm";
	private static final String PASSWORD = "dm1113@";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	    String m_pid = request.getParameter("m_pid");
	        
		ArrayList<Map<String, String>> list = getmember(m_pid);

		
		System.out.println(list);
		request.setAttribute("list", list);
		request.getRequestDispatcher("/admin/admin_member_chginfo.jsp").forward(request, response);
	}


	// 기본적인 접속메소드
		private static Connection getConnection() {
			Connection conn = null;
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				conn = DriverManager.getConnection(URL, USER, PASSWORD);
//				    System.out.println("db접속성공");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return conn;
		}
		
	// 맴버가져오기
	private static ArrayList<Map<String,String>> getmember(String m_pid) {
		ArrayList<Map<String,String>> result_list = new ArrayList<Map<String,String>>();
		try {
			Connection conn = getConnection();
			// SQL준비
			String query = "";
			query += " select";
			query += " *";
			query += " from";
			query += " member";
			query += " where m_pid = " + m_pid;

			System.out.println("query:" + query);
			// SQL 실행준비
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Map<String,String> map = new HashMap<String, String>();
							
				map.put("m_pid", rs.getString("M_PID"));
				map.put("m_id", rs.getString("M_ID"));
				map.put("m_pw", rs.getString("M_PW"));
				map.put("m_name", rs.getString("M_NAME"));
				map.put("m_tel", rs.getString("M_TEL"));
				map.put("m_email", rs.getString("M_EMAIL"));
				map.put("m_birthday", rs.getString("M_BIRTHDAY"));
				map.put("m_address", rs.getString("M_ADDRESS"));
				map.put("m_email_agree", rs.getString("M_EMAIL_AGREE"));
				map.put("m_loanstate", rs.getString("M_LOANSTATE"));
				map.put("m_managerchk", rs.getString("M_MANAGERCHK"));
				map.put("lb_id", rs.getString("LB_ID"));

				result_list.add(map);
			}

			rs.close();
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result_list;
	}

}
