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

@WebServlet("/sign_up")
public class sign_up_Servlet extends HttpServlet {
	private static final String URL = "jdbc:oracle:thin:@112.148.46.134:51521:xe";
	private static final String USER = "carpedm";
	private static final String PASSWORD = "dm1113@";

//	DB접속 메소드
	private static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		실행할 쿼리문
		String member = "";
		member += "SELECT *";
		member += " FROM member ";
//		member += " where LB_ID=";
//		member += lb_id;
//		System.out.println(member);
		ArrayList<Map<String, String>> member_list = getDBList(member);

		request.setAttribute("member_list", member_list);

		request.getRequestDispatcher("sign/sign_up.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("포스트접근성공");
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=utf-8;");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		System.out.println(getDBUpdate(request, response));

	}

	public static ArrayList<Map<String, String>> getDBList(String member) {
		ArrayList<Map<String, String>> result_list = new ArrayList<Map<String, String>>();
		try {
			Connection conn = getConnection();
			// SQL준비
//			System.out.println("notice:" + notice);
			// SQL 실행준비
			PreparedStatement ps = conn.prepareStatement(member);
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
//			    값 잘 나오는지 확인
//			    System.out.println(result_list.get(0).get("N_TITLE"));
			}

			rs.close();
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result_list;
	}

	private int getDBUpdate(HttpServletRequest request, HttpServletResponse response) {
		int result = -1;
		try {
			Connection conn = getConnection();
			// SQL준비
			String m_id = request.getParameter("id");
			String m_pw = request.getParameter("pw");
			String m_pw2 = request.getParameter("pw_check");
			String m_name = request.getParameter("name");
			String m_birthday = request.getParameter("birthday");
			String m_tel = request.getParameter("tel");
			String m_email = request.getParameter("email");
			String m_address = request.getParameter("address");

			// 유효성 검사 및 비밀번호 확인
			if (m_id == null || m_id.trim().isEmpty() || m_pw == null || m_pw.trim().isEmpty() || m_pw2 == null
					|| m_pw2.trim().isEmpty() || m_name == null || m_name.trim().isEmpty() || m_birthday == null
					|| m_birthday.trim().isEmpty() || m_tel == null || m_tel.trim().isEmpty() || m_email == null
					|| m_email.trim().isEmpty() || m_address == null || m_address.trim().isEmpty()) {
			} else {
				String member_in = "";
				member_in += " insert into member";
				member_in += " (";
				member_in += "M_PID";
				member_in += " , M_ID";
				member_in += " , M_PW";
				member_in += " , M_NAME";
				member_in += " , M_TEL";
				member_in += " , M_EMAIL";
				member_in += " , M_BIRTHDAY";
				member_in += " , M_ADDRESS";
				member_in += " , M_EMAIL_AGREE";
				member_in += " , M_LOANSTATE";
				member_in += " , M_MANAGERCHK";
				member_in += " , LB_ID)";

				System.out.println(member_in);
				// SQL 실행준비
				PreparedStatement ps = conn.prepareStatement(member_in);
				ps.setString(1, m_id);
				ps.setString(2, m_pw);
				ps.setString(3, m_pw2);
				ps.setString(4, m_name);
				ps.setString(5, m_birthday);
				ps.setString(6, m_tel);
				ps.setString(7, m_email);
				ps.setString(8, m_address);

				result = ps.executeUpdate();
				System.out.println("바뀐 행 수:" + result);

				ps.close();
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
