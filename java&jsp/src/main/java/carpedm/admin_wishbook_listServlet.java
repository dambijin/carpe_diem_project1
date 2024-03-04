package carpedm;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/admin_wishbook_list")
public class admin_wishbook_listServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	private static final String URL = "jdbc:oracle:thin:@112.148.46.134:51521:xe";
	private static final String USER = "carpedm";
	private static final String PASSWORD = "dm1113@";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<Map<String, String>> list = getwishbook();
		
		request.setAttribute("wishbook_list", list);
		request.getRequestDispatcher("/admin/admin_wishbook_list.jsp").forward(request, response);	
	}

	// 기본적인 접속메소드
	private static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
		    System.out.println("db접속성공");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

		
	// 희망도서 가져오기
	private static ArrayList<Map<String,String>> getwishbook() {
		ArrayList<Map<String,String>> result_list = new ArrayList<Map<String,String>>();
		try {
			Connection conn = getConnection();
			// SQL준비
			String query = "";
			query += " select";
			query += " l.lb_name, w.w_id, l.lb_id, w.w_title, w.w_author, w.w_publisher, w.m_pid, w.w_pubyear, w.w_content, w.w_state,m.m_id";
			query += " from";
			query += " wishlist w";
			query += " join library l on w.lb_id = l.lb_id";
			query += " join member m on w.m_pid = m.m_pid";

			System.out.println("query:" + query);
			
			// SQL 실행준비
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Map<String,String> map = new HashMap<String, String>();
//				/*
//				 * 폐기처분행(자바스크립트에서 사용할때 필요할 수 있음) map.put("lb_name",
//				 * StringEscapeUtils.escapeJson(rs.getString("lb_name")));//이걸 쓸 줄 알아야 덜 지저분해질 것
//				 * 같다... map.put("lb_address",
//				 * StringEscapeUtils.escapeJson(rs.getString("lb_address"))); map.put("lb_tel",
//				 * StringEscapeUtils.escapeJson(rs.getString("lb_tel"))); map.put("lb_openTime",
//				 * StringEscapeUtils.escapeJson(rs.getString("lb_openTime")));
//				 * map.put("lb_content",
//				 * StringEscapeUtils.escapeJson(rs.getString("lb_content")));
//				 * map.put("lb_imgUrl",
//				 * StringEscapeUtils.escapeJson(rs.getString("lb_imgUrl")));
//				 * map.put("lb_content", rs.getString("lb_content").replace("\n",
//				 * "<br>").replace("\"", "\\\"").replace("\r", "\\r"));
//				 */
				map.put("w_id", rs.getString("w_id"));
				map.put("w_title", rs.getString("w_title"));
				map.put("w_author", rs.getString("w_author"));
				map.put("w_publisher", rs.getString("w_publisher"));
				map.put("m_pid", rs.getString("m_pid"));
				map.put("lb_name", rs.getString("lb_name"));
				map.put("w_pubyear", rs.getString("w_pubyear"));
				map.put("w_content", rs.getString("w_content"));
			    //jsp에 조건을 쓰면 보기 힘드니까 그냥 여기다가 넣자
			    String w_state_text = "진행중";
			    if(rs.getString("w_state") == null || rs.getString("w_state").equals("0"))
			    {
			    	w_state_text = "진행중";
			    }
			    else if(rs.getString("w_state").equals("1"))
			    {
			    	w_state_text = "완료";			    	
			    }
			    else if(rs.getString("w_state").equals("2"))
			    {
			    	w_state_text = "취소";
			    }
			    else if(rs.getString("w_state").equals("3"))
			    {
			    	w_state_text = "반려";
			    }
			    map.put("w_state", w_state_text);
				
//				map.put("w_state", rs.getString("w_state"));
				map.put("m_id", rs.getString("m_id"));

				result_list.add(map);
//				System.out.println(rs.getString("w_id"));
			}

			rs.close();
			ps.close();
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result_list;
	}
	
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//	    request.setCharacterEncoding("UTF-8");
//	    response.setContentType("text/html; charset=UTF-8;");
//	    // 클라이언트에서 전달된 input_todo 매개변수를 가져오기
//	    String w_id = request.getParameter("input_todo");
//	    // JSON 형식의 결과를 응답으로 전송
//	    response.getWriter().write(getJSON(w_id));
//	}
	
//	public String getJSON(String input_todo) {
//		if(input_todo == null) input_todo = "";
//		StringBuffer result = new StringBuffer("");
//		result.append("{\"result\" :[");
//		AdminWishbookDAO bookDAO = new AdminWishbookDAO();
//		ArrayList<Wishlist> list = bookDAO.search(input_todo);
//		for(int i = 0; i< list.size(); i++) {
//			result.append("[{\"value\": \"" + list.get(i).getW_id() + "\"},");
//			result.append("{\"value\": \"" + list.get(i).getW_title() + "\"},");
//			result.append("{\"value\": \"" + list.get(i).getW_author() + "\"},");
//			result.append("{\"value\": \"" + list.get(i).getW_isbn() + "\"},");
//			result.append("{\"value\": \"" + list.get(i).getW_pubyear() + "\"},");
//			result.append("{\"value\": \"" + list.get(i).getM_pid() + "\"},");
//			result.append("{\"value\": \"" + list.get(i).getW_name() + "\"},");
//			result.append("{\"value\": \"" + list.get(i).getW_content() + "\"}],");
//		}
//		result.append("]}");
//		return result.toString();
//	}
	
	// controller 메서드: 요청에 대한 처리를 담당하는 메서드
//	protected void controller(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
//		try {
//			// 한글 깨짐 방지
//			request.setCharacterEncoding("UTF-8");
//			response.setContentType("text/html; charset=utf-8;");
//			
//		} catch(UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//	}
}
