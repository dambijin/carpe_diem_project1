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
		
		ArrayList<Map<String, String>> notice = getDBList(nid_query);

		request.setAttribute("notice", notice);

		
		String lb_id = "";
		if (notice != null && !notice.isEmpty()) {
			for (int i = 0; i < notice.size(); i++) {
				Map<String, String> row = notice.get(i);
				lb_id = row.get("LB_ID");
				System.out.println("lb_id 값: " + lb_id);
			}
		}

		
		
//		실행할 쿼리문
		String l_id = "";
		l_id += "SELECT *";
		l_id += " FROM LIBRARY";
		l_id += " WHERE LB_ID <> ";
		l_id += lb_id;
		System.out.println("l_id :"+l_id);
		ArrayList<Map<String, String>> library_id = getDBList(l_id);

		
		request.setAttribute("library_id", library_id);
		

//		도서관 쿼리문
		String library = "";
		library += "SELECT *";
		library += " FROM LIBRARY ";
		library += " WHERE LB_ID = ";
		library += lb_id;
		System.out.println(library);
		ArrayList<Map<String, String>> library_list = getDBList(library);

		
		request.setAttribute("library_list", library_list);

		
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

//		멤버 쿼리문
		String member_query = "";
		member_query += "SELECT *";
		member_query += " FROM MEMBER where";
		member_query += " M_PID=";
		member_query += mPid;
		ArrayList<Map<String, String>> member = getDBList(member_query);

		
		request.setAttribute("member", member);
		
		
//		멤버 안에 tel을 실행할 쿼리문
		String mtel_query = "";
		mtel_query += "SELECT REPLACE(M_TEL, '-', '') AS M_TEL";
		mtel_query += " FROM MEMBER where";
		mtel_query += " M_PID=";
		mtel_query += mPid;
		ArrayList<Map<String, String>> membertel = getDBList(mtel_query);

		request.setAttribute("membertel", membertel);

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

		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=utf-8;");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	
		System.out.println(getDBUpdate(request, response));
		response.sendRedirect("QnA_board");
	}
	
	private int getDBUpdate(HttpServletRequest request , HttpServletResponse response) {
		int result = -1;
		try {
			Connection conn = getConnection();
			
			// SQL준비
			String n_subject = request.getParameter("notice_subject"); // 제목
			String n_lb= request.getParameter("library"); // 소속 도서관
			String n_fi= request.getParameter("n_file"); // 파일
			String n_write= request.getParameter("n_textarea"); // 글 내용(textarea)
			String nid= request.getParameter("n_id");
//			trim() : 앞뒤 공백 제거, 스페이스바 적을 수 있으니까 필요
			
			if(n_subject==null || n_subject.trim().equals("")
					|| n_write == null || n_write.trim().equals("")) {
				response.sendRedirect("notice_update");
			}else {
//				UPDATE [테이블] SET [열] = '변경할값' WHERE [조건]
				String notice_in = "";
				notice_in += " update notice set";
				notice_in += " N_TITLE = ?";
				notice_in += ", LB_ID = ?";
				notice_in += ", N_CONTENT = ?";
				notice_in += " where N_ID = ";
				notice_in += nid;
				
				// SQL 실행준비
				PreparedStatement ps = conn.prepareStatement(notice_in);
				ps.setString(1, n_subject);
				ps.setString(2, n_lb);
				ps.setString(3, n_write);
				System.out.println("n_lb : "+n_lb);
				System.out.println("update문 : "+notice_in);
				
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
