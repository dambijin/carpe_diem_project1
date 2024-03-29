package carpedm.admin;

import java.io.IOException;
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


@WebServlet("/admin_book_list")
public class admin_book_listServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String URL = "jdbc:oracle:thin:@112.148.46.134:51521:xe";
	private static final String USER = "carpedm";
	private static final String PASSWORD = "dm1113@";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8;");
		
		// 데이터베이스 연동을 위한 DAO 객체 생성
		admin_book_listDAO DAO = new admin_book_listDAO();
				
		
		ArrayList<Map<String, String>> list = getbook();

		String page = request.getParameter("page");
		if (page == null || "".equals(page)) {
			page = "1";
		}
		int currentPage = Integer.parseInt(page);

		// perPage(표시 개수) 처리 부분
		String perPage = request.getParameter("perPage");
		if (perPage == null || "".equals(perPage)) {
			perPage = "10";
		}
		int itemsPerPage = Integer.parseInt(perPage);
		// 페이지 처리를 위한 계산
		int startRow = (currentPage - 1) * itemsPerPage + 1;
		int endRow = currentPage * itemsPerPage;
		request.setAttribute("page", page);
		request.setAttribute("perPage", perPage);
		ArrayList<Map<String, String>> pageList = new ArrayList<>();

		// 인덱스를 1부터 시작하기 위해 startRow와 endRow를 1씩 감소
		startRow--;
		endRow--;

		for (int i = startRow; i <= endRow; i++) {
			if (i < list.size()) {
				pageList.add(list.get(i));
			} else {
				break;
			}
		}

		request.setAttribute("book_list", pageList);
		request.setAttribute("allcount", list.size());
		
		System.out.println(list);
//		request.setAttribute("book_list", list);
		request.getRequestDispatcher("/admin/admin_book_list.jsp").forward(request, response);
	}

	// 기본적인 접속메소드
	private static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
//			    System.out.println("db접속성공");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	
	// 맴버가져오기
	private static ArrayList<Map<String,String>> getbook() {
		ArrayList<Map<String,String>> result_list = new ArrayList<Map<String,String>>();
		try {
			Connection conn = getConnection();
			// SQL준비
			String query = "";
			query += " select";
			query += " b.b_id,b.lb_id, b.b_title, b.b_author, b.b_publisher, b.b_isbn, l.lb_name, b.b_date, b.b_resstate, b.b_loanstate";
			query += " from book b";
			query += " join library l on b.lb_id = l.lb_id";
			
			

			System.out.println("query:" + query);
			// SQL 실행준비
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Map<String,String> map = new HashMap<String, String>();
				/* 폐기처분행(자바스크립트에서 사용할때 필요할 수 있음)
				map.put("lb_name", StringEscapeUtils.escapeJson(rs.getString("lb_name")));//이걸 쓸 줄 알아야 덜 지저분해질 것 같다...
				map.put("lb_address", StringEscapeUtils.escapeJson(rs.getString("lb_address")));
				map.put("lb_tel", StringEscapeUtils.escapeJson(rs.getString("lb_tel")));
				map.put("lb_openTime", StringEscapeUtils.escapeJson(rs.getString("lb_openTime")));
				map.put("lb_content", StringEscapeUtils.escapeJson(rs.getString("lb_content")));
				map.put("lb_imgUrl", StringEscapeUtils.escapeJson(rs.getString("lb_imgUrl")));
				map.put("lb_content", rs.getString("lb_content").replace("\n", "<br>").replace("\"", "\\\"").replace("\r", "\\r"));
				*/				
				map.put("b_id", rs.getString("b_id"));
				map.put("lb_id", rs.getString("lb_id"));
				map.put("b_title", rs.getString("b_title"));
				map.put("b_author", rs.getString("b_author"));
				map.put("b_publisher", rs.getString("b_publisher"));
				map.put("b_isbn", rs.getString("b_isbn"));
				map.put("lb_name", rs.getString("lb_name"));
				map.put("b_date", rs.getString("b_date"));
				map.put("b_resstate", rs.getString("b_resstate"));
				map.put("b_loanstate", rs.getString("b_loanstate"));

				result_list.add(map);
//		    	System.out.println(rs.getString("lb_name"));
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
