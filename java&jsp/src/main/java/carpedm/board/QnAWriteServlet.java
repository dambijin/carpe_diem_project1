package carpedm.board;

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
import javax.servlet.http.HttpSession;

@WebServlet("/QnA_write")
public class QnAWriteServlet extends HttpServlet {

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
		String library = "";
		library += "SELECT *";
		library += " FROM LIBRARY ";
//		library += " where LB_ID=";
//		library += lb_id;
//		System.out.println(library);
		ArrayList<Map<String, String>> library_list = getDBList(library);

		request.setAttribute("library_list", library_list);

		request.getRequestDispatcher("board/QnA_write.jsp").forward(request, response);
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
		response.sendRedirect("QnA_board");
	}

//	DB업데이트 or 인서트 등등을 할 수 있는 메소드
	private int getDBUpdate(HttpServletRequest request, HttpServletResponse response) {
		int result = -1;
		try {
			Connection conn = getConnection();
			// SQL준비
			String n_subject = request.getParameter("title"); // 제목
			String n_fi = request.getParameter("n_file"); // 파일
			String n_write = request.getParameter("n_textarea"); // 글 내용(textarea)
			String n_pub = request.getParameter("pub"); // 공개글 or 비공개글
			String n_lb = request.getParameter("library"); // 소속 도서관

			HttpSession getSession = request.getSession();
			String login_m_pid = (String) getSession.getAttribute("m_pid");
//			trim() : 앞뒤 공백 제거, 스페이스바 적을 수 있으니까 필요
			if (n_subject == null || n_subject.trim().equals("") || n_write == null || n_write.trim().equals("")) {
				response.sendRedirect("notice_write");
			} else {
				String notice_in = "";
				notice_in += " insert into notice";
				notice_in += " (";
				notice_in += "N_ID";
				notice_in += " , N_OPT";
				notice_in += " , LB_ID";
				notice_in += " , N_TITLE";
				notice_in += " , N_CONTENT";
				notice_in += " , N_VIEWCOUNT";
				notice_in += " , N_DATE";
				notice_in += " , M_PID)";
				notice_in += " values(";
				notice_in += " notice_seq.NEXTVAL";
				// 도서관 로그인하면 불러오기 사용 필요
				notice_in += " , ?"; // QnA는 N_OPT = 1(공개) 또는 N_OPT = 2(비공개)
				notice_in += " , ?"; // 도서관
				notice_in += " , ?"; // 제목
				notice_in += " , ?"; // 작성내용
				notice_in += " , 0";
				notice_in += " , sysdate";
				notice_in += " , ?)"; // 임의값 추후 로그인했을때 불러와야함

				System.out.println(notice_in);
				// SQL 실행준비
				PreparedStatement ps = conn.prepareStatement(notice_in);
				ps.setString(1, n_pub);
				ps.setString(2, n_lb);
				ps.setString(3, n_subject);
				ps.setString(4, n_write);
				ps.setString(5, login_m_pid);

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
