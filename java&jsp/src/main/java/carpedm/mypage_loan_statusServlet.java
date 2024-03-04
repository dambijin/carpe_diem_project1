package carpedm;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/mypage_loan_status")
public class mypage_loan_statusServlet extends HttpServlet {
	
	private static final String URL = "jdbc:oracle:thin:@112.148.46.134:51521:xe";
	private static final String USER = "carpedm";
	private static final String PASSWORD = "dm1113@";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		String id = request.getParameter("id");
//		request.setAttribute("id2", id);

	
		
		ArrayList<Map<String, String>> library = getDBList("select lb_name from library");
		request.setAttribute("library", library);
		
		HttpSession getSession = request.getSession();
		String login_m_pid = (String) getSession.getAttribute("m_pid");
		if (login_m_pid == null || login_m_pid.equals("")) {
			login_m_pid = "4";
		}

		ArrayList<Map<String, String>> myInfo = getDBList("select * from member where m_pid = " + login_m_pid);
		request.setAttribute("myInfo", myInfo);
		
		ArrayList<Map<String,String>> list = getLoan(login_m_pid);
		request.setAttribute("list", list);
		request.getRequestDispatcher("/mypage/mypage_loan_status.jsp").forward(request, response);
	}

	// 기본적인 접속메소드
	private Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
//		            System.out.println("db접속성공");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	private ArrayList<Map<String,String>> getLoan(String m_pid) {
		ArrayList<Map<String,String>> result_list = new ArrayList<Map<String,String>>();
		try {
			Connection conn = getConnection();
			// SQL준비
			String query = "";
			query += "select";
			query += " l_id, b_title, b_author, b_publisher, l_loandate, l_returnrealdate, l_extendcount, library.lb_name";
			query += " from";
			query += " loan";
			query += " INNER JOIN book ON loan.b_id = book.b_id INNER JOIN library ON book.lb_id = library.lb_id";	
			query += " WHERE loan.l_returnrealdate IS NULL AND m_pid = " + m_pid;
			
			

			System.out.println("query:" + query);
			// SQL 실행준비
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Map<String,String> map = new HashMap<String, String>();

				map.put("l_id", rs.getString("l_id"));//이걸 쓸 줄 알아야 덜 지저분해질 것 같다...
				map.put("b_title", rs.getString("b_title"));//이걸 쓸 줄 알아야 덜 지저분해질 것 같다...
				map.put("b_author", rs.getString("b_author"));
				map.put("b_publisher", rs.getString("b_publisher"));
				map.put("l_loandate", rs.getString("l_loandate"));
				map.put("l_returnrealdate", rs.getString("l_returnrealdate"));
				map.put("l_extendcount", rs.getString("l_extendcount"));
				map.put("lb_name", rs.getString("lb_name"));
				
//				map.put("lb_content", rs.getString("lb_content").replace("\n", "<br>").replace("\"", "\\\"").replace("\r", "\\r"));
				result_list.add(map);
//		    	System.out.println(rs.getString("lb_name"));
			}

			rs.close();
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result_list;
	}
	private ArrayList<Map<String, String>> getDBList(String query) {
		ArrayList<Map<String, String>> result_list = new ArrayList<Map<String, String>>();
		try {
			Connection conn = getConnection();
			// SQL준비

			System.out.println("query:" + query);
			
			
			// SQL 실행준비
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();

			while (rs.next()) {
			    Map<String, String> map = new HashMap<String, String>();

			    for (int i = 1; i <= columnCount; i++) {
			        String columnName = rsmd.getColumnName(i);
			        map.put(columnName, rs.getString(columnName));
			    }

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
