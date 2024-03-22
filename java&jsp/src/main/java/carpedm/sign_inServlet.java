package carpedm;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/sign_in")
public class sign_inServlet extends HttpServlet {
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
////		한글 깨짐 방지
//		try {
//			request.setCharacterEncoding("UTF-8");
//			response.setContentType("text/html; charset=utf-8;");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
//		Connection conn = null;
//		// 쿼리(sql코드) 구문을 사용하기위해
////      PreparedStatement : Statement를 상속하고 있는 Interface
//		PreparedStatement ps = null;
////      쿼리 결과를 가져오기 위해 사용
//		ResultSet rs = null;
//
////      실행하려고 하는 쿼리(sql코드)
//		String query = "SELECT * FROM member";
//		try {
//			// 데이터베이스 연결
//			conn = getConnection();
//			// PreparedStatement 생성
//			ps = conn.prepareStatement(query);
//			// 쿼리 실행 및 결과 가져오기
//			rs = ps.executeQuery();
//
//			// 결과 처리
//			// while 전달인자가 true인 동안 계속 반복
//			// re.next() 다음줄이 있으면 true 없으면 false
//			while (rs.next()) {
//				// HashMap() 중복된 key값과 1개의 value값을 입력 받을수 있다
//				Map map = new HashMap();
//				map.put("id", rs.getString("m_id")); // 아이디
//				map.put("pw", rs.getString("m_pw")); // 비밀번호
//
////            	map 객체를 list에 추가하는 작업을 수행
//				list.add(map);
//
////              list.get(0) : 리스트 첫번째에 있는 맵을 꺼내옴
////            	뒤에 붙는 .get("num") : 그 맵에서 num이라는 키값을 갖는 밸류값을 가져온다
////            	System.out.println(list.get(0).get("num"));
////            	System.out.println(list.get(0).get("title"));
//
////            	ArrayList al= new ArrayList();
////            	al.add(list.get(0).get("num"));
//			}
////            System.out.println(al);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		for (int i = 1; i < list.size(); i++) {
//			System.out.println(list.get(i).get("id"));
//		}
//
//		request.setAttribute("idpw_list", list);
//
		request.getRequestDispatcher("sign/sign_in.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userid = request.getParameter("userid");
		String userpw = request.getParameter("userpw");

		System.out.println(userid);
		System.out.println(userpw);
		// 데이터베이스에서 사용자 정보를 가져옵니다.
		// 이 부분은 실제 데이터베이스 환경에 맞게 수정해야 합니다.
		List<Map<String, String>> idpw_list = getUserInfoFromDatabase(userid,userpw);

		boolean found = false;
		if(idpw_list.size()>0)
		{
			found = true;
		}
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		if (found) {
			// 로그인 성공: 세션에 사용자 정보를 저장하고 클라이언트에게 로그인 성공 메시지를 보냅니다.
			HttpSession session = request.getSession();
			session.setAttribute("m_pid", idpw_list.get(0).get("M_PID"));
			session.setAttribute("m_managerchk", idpw_list.get(0).get("M_MANAGERCHK"));
			
		    // 세션 ID를 쿠키에 저장합니다.
		    Cookie sessionCookie = new Cookie("lc", idpw_list.get(0).get("M_MANAGERCHK"));
		    sessionCookie.setPath("/");  // 쿠키의 유효 경로를 설정합니다.
		    response.addCookie(sessionCookie);
			out.print("{\"success\": true}");
		} else {
			// 로그인 실패: 클라이언트에게 로그인 실패 메시지를 보냅니다.
			out.print("{\"success\": false}");
		}
	}

	// 데이터베이스에서 사용자 정보를 가져오는 메소드
	// 이 메소드는 실제 데이터베이스 환경에 맞게 수정해야 합니다.
	private List<Map<String, String>> getUserInfoFromDatabase(String id, String pw) {
		List<Map<String, String>> result_list = new ArrayList<Map<String, String>>();
		String query = "SELECT * FROM member where m_id='"+id+"' AND m_pw='"+pw+"'";
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
