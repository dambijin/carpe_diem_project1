package carpedm.board;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/notice_detail")
public class NoticeDetailServlet extends HttpServlet {
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
//		한글 깨짐 방지
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=utf-8;");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		String nid = request.getParameter("N_ID");
		
//		실행할 쿼리문
		String select = "";
		select += "SELECT notice.*, library.LB_NAME, member.M_NAME";
		select += " FROM notice";
		select += " JOIN library ON notice.LB_ID = library.LB_ID";
		select += " JOIN member ON notice.M_PID = member.M_PID";
		select += " WHERE notice.N_ID = ";
		select += nid;

		ArrayList<Map<String, String>> notice = getDBList(select);
		request.setAttribute("notice", notice);


//		업데이트문
		String update = "UPDATE notice SET N_VIEWCOUNT = N_VIEWCOUNT + 1 WHERE N_ID = ?";
		viewUpdate(update, nid);
		
		request.getRequestDispatcher("board/notice_detail.jsp").forward(request, response);
	}

	public static ArrayList<Map<String, String>> getDBList(String notice) {
		ArrayList<Map<String, String>> result_list = new ArrayList<Map<String, String>>();
		try {
			Connection conn = getConnection();
			// SQL준비
//			System.out.println("notice:" + notice);
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


//	viewcount 업데이트 메소드
	public static void viewUpdate(String notice, String nid) {
		try {
			// 데이터 베이스 연결
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement pst = conn.prepareStatement(notice);
			pst.setString(1, nid);
//			executeUpdate : 업데이트 하는 sql문 작성됨
			int rowCount = pst.executeUpdate();
			if (rowCount > 0) {
//			가져올 쿼리문
			} else {
				System.out.println("조회수 증가 실패");
			}
			// 리소스 닫기
			pst.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
