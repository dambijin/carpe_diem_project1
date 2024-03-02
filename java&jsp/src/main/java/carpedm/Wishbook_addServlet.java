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

@WebServlet("/wishbook_add")
public class Wishbook_addServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String URL = "jdbc:oracle:thin:@112.148.46.134:51521:xe";
	private static final String USER = "carpedm";
	private static final String PASSWORD = "dm1113@";
	

//	DB접속 메소드
	public static Connection getConnection() {
		Connection conn= null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


//		실행할 쿼리문
		String library = "";
		library += "SELECT *";
		library += " FROM LIBRARY ";
//		library += " where LB_ID=";
//		library += lb_id;
		System.out.println(library);
		ArrayList<Map<String, String>> library_list = getDBList(library);

		request.setAttribute("library_list", library_list);
		
		request.getRequestDispatcher("board/wishbook_add.jsp").forward(request, response);	
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


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("포스트접근성공");
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=utf-8;");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	
		System.out.println(getDBUpdate(request, response));
		response.sendRedirect("notice_board");
	
	}
	
	private int getDBUpdate(HttpServletRequest request , HttpServletResponse response) {
		int result = -1;
		try {
			Connection conn = getConnection();
			// SQL준비
			String n_lb= request.getParameter("library"); // 소속 도서관
			String w_title= request.getParameter("w_title"); // 자료명
			String author= request.getParameter("author"); // 저자
			String w_date= request.getParameter("w_date"); // 발행년도
			String isbn= request.getParameter("isbn"); // isbn
			String w_content= request.getParameter("w_content"); // 신청사유
			String pbs= request.getParameter("pbs"); // 출판사
			String w_name= request.getParameter("w_name"); // 이름
			String w_tel= request.getParameter("w_tel"); // 휴대폰번호
			
//			trim() : 앞뒤 공백 제거, 스페이스바 적을 수 있으니까 필요
			if(w_title==null || w_title.trim().equals("")
					|| author == null || author.trim().equals("")
					|| w_date == null || w_date.trim().equals("")
					|| w_content == null || w_content.trim().equals("")
					|| pbs == null || pbs.trim().equals("")
					|| w_tel == null || w_tel.trim().equals("")
					) {
				response.sendRedirect("wishbook_add");
			}else {
				String notice_in = "";
				notice_in += " insert into wishlist";
				notice_in += " (";
				notice_in += "W_ID";
				notice_in += " , LB_ID";
				notice_in += " , W_TITLE"; // 자료명
				notice_in += " , W_AUTHOR"; // 저자
				notice_in += " , W_PUBYEAR"; // 발행년도
				notice_in += " , W_ISBN";
				notice_in += " , W_CONTENT"; // 신청사유
				notice_in += " , W_PUBLISHER"; // 출판사
				notice_in += " , W_NAME"; // 이름
				notice_in += " , W_TEL"; //전화번호
//				로그인했을때 가정해서 회원번호도 넣어야함
				notice_in += " , W_DATE)"; // 희망도서 신청날짜
				notice_in += " values(";
				notice_in += " wishlist_seq.NEXTVAL";
				notice_in += " , ?"; // 도서관
				notice_in += " , ?"; // 자료명
				notice_in += " , ?"; // 저자
				notice_in += " , ?"; // 발행년도
				notice_in += " , ?"; // isbn
				notice_in += " , ?"; // 신청사유
				notice_in += " , ?"; // 출판사
				notice_in += " , ?"; // 이름
				notice_in += " , ?"; // 전화번호
				notice_in += " , SYSDATE)"; // 희망도서 신청날짜
				
				System.out.println(notice_in);
				// SQL 실행준비
				PreparedStatement ps = conn.prepareStatement(notice_in);
				ps.setString(1, n_lb);
				ps.setString(2, w_title);
				ps.setString(3, author);
				ps.setString(4, w_date);
				ps.setString(5, w_date);
				ps.setString(6, w_content);
				ps.setString(7, pbs);
				ps.setString(8, w_name);
				ps.setString(9, w_tel);
				
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
