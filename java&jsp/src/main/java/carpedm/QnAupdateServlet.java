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

@WebServlet("/QnA_update")
public class QnAupdateServlet extends HttpServlet {
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

		String url = request.getRequestURL().toString(); // 현재 URL 가져오기
		String queryString = request.getQueryString(); // 쿼리 문자열 가져오기

		int nid = 0;
		if (queryString != null) {
			String[] params = queryString.split("&"); // 쿼리 파라미터 분리

			for (String param : params) {
				String[] keyValue = param.split("="); // 파라미터 이름과 값 분리
				String paramName = keyValue[0]; // 파라미터 이름
				String paramValue = keyValue.length > 1 ? keyValue[1] : ""; // 파라미터 값

				if (paramName.equals("N_ID")) {
					nid = Integer.parseInt(paramValue);
				}
			}
		}

//		실행할 쿼리문
		String nid_query = "";
		nid_query += "SELECT * FROM notice where";
		nid_query += " n_id=";
		nid_query += nid;

//		System.out.println("N_ID 값: " + nid_query);
		ArrayList<Map<String, String>> notice = getDBList(nid_query);

		request.setAttribute("notice", notice);

		String mPid = "";
//		M_PID 컬럼 데이터 가져오기
		if (notice != null && !notice.isEmpty()) {
			for (int i = 0; i < notice.size(); i++) {
//				notice의 ArrayList를 실행해서 
//				row는 변수를 사용해 map한테 넣어줌 
				Map<String, String> row = notice.get(i);
				mPid = row.get("M_PID");
//				System.out.println("M_PID 값: " + mPid);
			}
		}

//		실행할 쿼리문
		String member_query = "";
		member_query += "SELECT * FROM MEMBER where";
		member_query += " M_PID=";
		member_query += mPid;
		System.out.println(member_query);
		ArrayList<Map<String, String>> member = getDBList(member_query);

		request.setAttribute("member", member);

		request.getRequestDispatcher("board/QnA_update.jsp").forward(request, response);

	}

	public static ArrayList<Map<String, String>> getDBList(String notice) {
		ArrayList<Map<String, String>> result_list = new ArrayList<Map<String, String>>();
		try {
			Connection conn = getConnection();
			// SQL준비

//			System.out.println("nidquery:" + notice);
			// SQL 실행준비
			PreparedStatement ps = conn.prepareStatement(notice);
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
//			    System.out.println(map.get("N_ID"));
			}

			rs.close();
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result_list;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
