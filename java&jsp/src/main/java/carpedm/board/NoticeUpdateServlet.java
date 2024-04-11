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

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet("/notice_update")
public class NoticeUpdateServlet extends HttpServlet {

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
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=utf-8;");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}


		String nid = request.getParameter("N_ID");
//		실행할 쿼리문
		String notice = "";
		notice += " SELECT n.*, m.M_NAME, l.LB_NAME";
		notice += " FROM notice n";
		notice += " JOIN member m ON n.M_PID = m.M_PID";
		notice += " JOIN library l ON n.LB_ID = l.LB_ID";
		notice += " WHERE n.N_ID = ";
		notice += nid;
		ArrayList<Map<String, String>> qna_notice = getDBList(notice);
		request.setAttribute("qna_notice", qna_notice);
		
		String lb_id = qna_notice.get(0).get("LB_ID");
		String library_query = "";
		library_query += "SELECT *";
		library_query += " FROM LIBRARY";
		library_query += " WHERE LB_ID <> ";
		library_query += lb_id;
		System.out.println("library_query :" + library_query);
		ArrayList<Map<String, String>> library = getDBList(library_query);
		request.setAttribute("library", library);

		request.getRequestDispatcher("board/notice_update.jsp").forward(request, response);

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

		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=utf-8;");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		System.out.println(getDBUpdate(request, response));
		response.sendRedirect("notice_board");
	}

//	DB업데이트 or 인서트 등등을 할 수 있는 메소드
	private int getDBUpdate(HttpServletRequest request, HttpServletResponse response) {
		int result = -1;
		try {
			Connection conn = getConnection();

			// SQL준비
			String n_subject = request.getParameter("notice_subject"); // 제목
			String n_subject_update = n_subject;
			String n_lb = request.getParameter("library"); // 소속 도서관
			String n_fi = request.getParameter("n_file"); // 파일
			String n_write = request.getParameter("n_textarea"); // 글 내용(textarea)
			String nid = request.getParameter("n_id");
//			trim() : 앞뒤 공백 제거, 스페이스바 적을 수 있으니까 필요

			if (n_subject == null || n_subject.trim().equals("") || n_write == null || n_write.trim().equals("")) {
				response.sendRedirect("notice_update");
			} else {
//				UPDATE [테이블] SET [열] = '변경할값' WHERE [조건]
				String notice_in = "";
				notice_in += " update notice set";
				notice_in += " N_TITLE = ?";
				notice_in += ", LB_ID = ?";
//				notice_in += ", N_FILE = ?";
				notice_in += ", N_CONTENT = ?";
				notice_in += " where N_ID = ";
				notice_in += nid;

				// SQL 실행준비
				PreparedStatement ps = conn.prepareStatement(notice_in);
				ps.setString(1, n_subject_update);
				ps.setString(2, n_lb);
				ps.setString(3, n_write);
//				ps.setString(4, n_fi);
				System.out.println("n_lb : " + n_lb);
				System.out.println("업데이트 쿼리문 : " + notice_in);

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
