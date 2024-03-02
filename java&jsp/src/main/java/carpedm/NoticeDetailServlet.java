package carpedm;

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

		// 현재 URL 가져오기
		String url = request.getRequestURL().toString();
		// 쿼리 문자열 가져오기
		String queryString = request.getQueryString();

		int nid = 0;
		if (queryString != null) {
//			쿼리 파라미터 분리
//			queryString이 "N_ID=14&name=John&age=25"와 같은 문자열을 가지고 있다면
//			이 문자열을 & 기준으로 분리하고 싶어서 split("&")을 사용
//			params : N_ID=14/name=John/age=25 
//			3개가 배열로 나옴
//			향상된 for문을 위해 사용
			String[] params = queryString.split("&");
			for (String param : params) {
				// 예시 : N_ID=14 을 = 기준으로 분리
				String[] keyValue = param.split("=");
				// 예시 : N_ID
				String paramName = keyValue[0];
				// 예시 : 14를 넣기 위해 만든 변수
				String paramValue = "";
//				만약 14가 1보다 크다면
				if (keyValue.length > 1) {
					paramValue = keyValue[1];
				}

				if (paramName.equals("N_ID")) {
					try {
		                nid = Integer.parseInt(paramValue);
		            } catch (NumberFormatException e) {
		                e.printStackTrace();
		            }
				}
			}
		}

//		실행할 쿼리문
		String nid_query = "";
		nid_query += "SELECT * FROM notice where n_opt=0";
		nid_query += "and n_id=";
		nid_query += nid;
		nid_query += " order by n_id desc";

		System.out.println("N_ID 값: " + nid_query);
		ArrayList<Map<String, String>> notice = getDBList(nid_query);

		request.setAttribute("notice", notice);
		
		
//		멤버 가져오기
		String mPid = "";
		if (notice != null && !notice.isEmpty()) {
			for (int i = 0; i < notice.size(); i++) {
				Map<String, String> row = notice.get(i);
				mPid = row.get("M_PID");
				System.out.println("mPid 값: " + mPid);
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
		
		
//		도서관 가져오기
		String lb_id = "";
		if (notice != null && !notice.isEmpty()) {
			for (int i = 0; i < notice.size(); i++) {
				Map<String, String> row = notice.get(i);
				lb_id = row.get("LB_ID");
				System.out.println("lb_id 값: " + lb_id);
			}
		}

//		도서관 쿼리문
		String library = "";
		library += "SELECT *";
		library += " FROM LIBRARY ";
		library += " where LB_ID=";
		library += lb_id;
		ArrayList<Map<String, String>> library_list = getDBList(library);
		request.setAttribute("library_list", library_list);
		
		
		
		String notice_id= "";
		if (notice != null && !notice.isEmpty()) {
			for (int i = 0; i < notice.size(); i++) {
				Map<String, String> row = notice.get(i);
				notice_id = row.get("N_ID");
			}
		}
		
		try {
	        // 데이터 베이스 연결
	        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
	        
	        String sql = "UPDATE notice SET N_VIEWCOUNT = N_VIEWCOUNT + 1 WHERE N_ID = ?";
	        
	        PreparedStatement pst = conn.prepareStatement(sql);
	        pst.setString(1, notice_id);
	        System.out.println("--------------------------");
	        System.out.println("공지사항번호: "+notice_id);
	        System.out.println("카운트업데이트 값: "+sql);
//	        executeUpdate : 업데이트 하는 sql문 작성됨
	        int rowCount = pst.executeUpdate();
	        System.out.println("--------------------------");
	        System.out.println("pst:"+pst);
	        System.out.println("rowCount :"+rowCount);
	        if (rowCount > 0) {
//	        	가져올 쿼리문
	        	
	        } else {
	        	System.out.println("조회수 증가 실패");
	        }
	        
	        String UPDATE = "select * from notice where N_ID=";
        	UPDATE += notice_id;
        	ArrayList<Map<String, String>> update = getDBList(UPDATE);
    		request.setAttribute("update", update);
	        
    		
	        // 리소스 닫기
	        pst.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace(); 
	    }
		

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

}
