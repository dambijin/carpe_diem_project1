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

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

@WebServlet("/QnA_detail")
public class QnADetailServlet extends HttpServlet {

//	DB접속 메소드
	private static Connection getConnection() {
		Connection conn = null;
		try {
			Context ctx = new InitialContext();
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/carpedm");
			conn = dataFactory.getConnection();
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
		System.out.println("가져온 nid : " + nid);

//		실행할 쿼리문
		String nid_query = "SELECT n.*, m.M_NAME, l.LB_NAME";
		nid_query += " FROM notice n";
		nid_query += " INNER JOIN member m ON n.M_PID = m.M_PID";
		nid_query += " INNER JOIN library l ON n.LB_ID = l.LB_ID";
		nid_query += " WHERE n.n_id = ";
		nid_query += nid;

		System.out.println("N_ID 들어간 쿼리: " + nid_query);
		ArrayList<Map<String, String>> notice = getDBList(nid_query);

		request.setAttribute("notice", notice);

		HttpSession getSession = request.getSession();
		String login_m_pid = (String) getSession.getAttribute("m_pid"); // 로그인한 아이디
		// M_MANAGERCHK 매니저인지 아닌지 구분
		String login_manager = (String) getSession.getAttribute("m_managerchk");
		// 게시물 비공개 공개 여부
		String opt = notice.get(0).get("N_OPT");
		String mpid = notice.get(0).get("M_PID");
//		QnA게시물 아이디
		boolean upchk = true; // 조회수를 올릴지 말지 결정하는 체크 값
		if ((login_m_pid == null || !login_m_pid.equals(mpid)) && opt.equals("2")) {
			// 비공개글을 들어갔을 때 로그인이 안되어있거나, 해당 작성자가 아니면 체크값을 false로 변경
			upchk = false;
		}
		
		System.out.println(login_manager);
		if (login_manager != null && login_manager.equals("Y")) {
			upchk = true;
		}

		if (upchk) {// 체크값이 true로 온전한 상태면 조회수 증가
			String update = "UPDATE notice SET N_VIEWCOUNT = N_VIEWCOUNT + 1 WHERE N_ID = ?";
			viewUpdate(update, nid);
		}
		
		String newline = "\n";
		request.setAttribute("newline", newline);

		request.getRequestDispatcher("board/QnA_detail.jsp").forward(request, response);

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

	public static void viewUpdate(String notice, String nid) {
		try {
			// 데이터 베이스 연결
			Connection conn = null;
			try {
				Context ctx = new InitialContext();
				DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/carpedm");
				conn = dataFactory.getConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
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
