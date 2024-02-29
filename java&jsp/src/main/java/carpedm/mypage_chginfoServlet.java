package carpedm;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

@WebServlet("/mypage_chginfo")
public class mypage_chginfoServlet extends HttpServlet {
	private static final String URL = "jdbc:oracle:thin:@112.148.46.134:51521:xe";
	private static final String USER = "carpedm";
	private static final String PASSWORD = "dm1113@";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//	String id = request.getParameter("id");
//	request.setAttribute("id2", id);

//		ArrayList<Map<String, String>> myInfo = getDBList("select * from member");
//		request.setAttribute("myInfo", myInfo);
		
		ArrayList<Map<String, String>> myInfo = getDBList("select * from member where m_pid = " + m_pid);
		request.setAttribute("myInfo", myInfo);
//	Gson gson = new Gson();
//	String json = gson.toJson(list);
//	request.setAttribute("list", json);

		request.getRequestDispatcher("/mypage/mypage_chginfo.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("포스트접근성공");
	
//		request.getRequestDispatcher("/mypage/mypage_chginfo.jsp").forward(request, response);
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=utf-8;");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String m_pid = "4";
		String m_pw = request.getParameter("password");
		String m_tel = request.getParameter("phonenumber");
		String email = request.getParameter("email_id" + "email_domain");
		
	}
	

// 기본적인 접속메소드
	private Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
//	            System.out.println("db접속성공");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	// 가져오기
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

	// 업에트
	
	private ArrayList<Map<String, String>> getDBUpdate(String query) {
		ArrayList<Map<String, String>> result_list = new ArrayList<Map<String, String>>();
		try {
			Connection conn = getConnection();
			// SQL준비
			String m_pid = "4";
			
			ArrayList<Map<String, String>> myInfo = getDBList("select * from member where m_pid = " + m_pid);
			String sql = "";
			sql += " UPDATE member";
			sql += " SET m_pw = ?";
			sql += " SET m_tel = ?";
			sql += " SET m_email = ?";
			sql += " WHERE m_pid = ?";
			
			
			// SQL 실행준비
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, m_pw);
			ps.setString(2, query);
			ps.setString(2, query + query);
			ps.setString(2, m_pid);
			
			int rs = ps.executeUpdate();
			
			System.out.println("바뀐 행 수:" + rs);

			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result_list;
	}
}
