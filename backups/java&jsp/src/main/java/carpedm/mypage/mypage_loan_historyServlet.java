package carpedm.mypage;

import java.io.IOException;
import java.sql.Connection;
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
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

@WebServlet("/mypage_loan_history")
public class mypage_loan_historyServlet extends HttpServlet {

	int start_page = -1;
	int end_page = -1;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//	String id = request.getParameter("id");
//	request.setAttribute("id2", id);
		String search = request.getParameter("search");
		if (search == null || "".equals(search)) {
			search = "";
		}
		ArrayList<Map<String, String>> library = getDBList("select lb_name from library");
		request.setAttribute("library", library);
		
		HttpSession getSession = request.getSession();
		String login_m_pid = (String) getSession.getAttribute("m_pid");
		if (login_m_pid == null || login_m_pid.equals("")) {
			request.getRequestDispatcher("/sign_in").forward(request, response);
			return;
		}

		ArrayList<Map<String, String>> myInfo = getDBList("select * from member where m_pid = " + login_m_pid);
		request.setAttribute("myInfo", myInfo);
		
		// page를 가져옴
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
				
				start_page = ((currentPage - 1) * itemsPerPage) + 1;
				end_page = currentPage * itemsPerPage;
				
				request.setAttribute("page", page);
				request.setAttribute("perPage", perPage);
		
		ArrayList<Map<String, String>> list = getLoanHistory(login_m_pid,search);
		ArrayList<Map<String, String>> pageList = new ArrayList<>();
		
		String query2 = "";
		query2 += " select";
		query2 += " count(*)";
		query2 += " from";
		query2 += " reservation";
		query2 += " inner join book";
		query2 += " on reservation.b_id = book.b_id";
		query2 += " inner join library";
		query2 += " on book.lb_id = library.lb_id";
		query2 += " LEFT JOIN ";
		query2 += " loan ON reservation.b_id = loan.b_id";
		query2 += " where reservation.m_pid = " + login_m_pid ;
		
		
		ArrayList<Map<String, String>> totalList = getDBList(query2);
		
		int totalViewCount = Integer.parseInt(totalList.get(0).get("COUNT(*)"));


				request.setAttribute("list", list);
				request.setAttribute("totalViewCount", totalViewCount);
		
	
//	Gson gson = new Gson();
//	String json = gson.toJson(list);
//	request.setAttribute("list", json);
		request.setAttribute("list", list);
		request.getRequestDispatcher("/mypage/mypage_loan_history.jsp").forward(request, response);
	}
	

// 기본적인 접속메소드
	 private Connection getConnection() {
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

	private ArrayList<Map<String, String>> getLoanHistory(String m_pid, String search) {
		ArrayList<Map<String, String>> result_list = new ArrayList<Map<String, String>>();
		try {
			Connection conn = getConnection();
			// SQL준비
			String query = "";
			query += "select";
			query += " l_id, b_title, b_author, b_publisher, l_loandate, l_returnrealdate, library.lb_name";
			query += " from";
			query += " loan";
			query += " INNER join book";
			query += " on loan.b_id = book.b_id ";
			query += " inner join library ";
			query += " on book.lb_id = library.lb_id ";
			query += " where loan.l_returnrealdate IS NOT NULL AND m_pid = " + m_pid + " and b_title like '%" + search + "%'";
			
			 
			System.out.println("query:" + query);
			// SQL 실행준비
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Map<String, String> map = new HashMap<String, String>();

				map.put("l_id", rs.getString("l_id"));// 이걸 쓸 줄 알아야 덜 지저분해질 것 같다...
				map.put("b_title", rs.getString("b_title"));// 이걸 쓸 줄 알아야 덜 지저분해질 것 같다...
				map.put("b_author", rs.getString("b_author"));
				map.put("b_publisher", rs.getString("b_publisher"));
				map.put("l_loandate", rs.getString("l_loandate"));
				map.put("l_returnrealdate", rs.getString("l_returnrealdate"));
				map.put("lb_name", rs.getString("lb_name"));
				

//			map.put("lb_content", rs.getString("lb_content").replace("\n", "<br>").replace("\"", "\\\"").replace("\r", "\\r"));
				result_list.add(map);
//	    	System.out.println(rs.getString("lb_name"));
			}

			rs.close();
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result_list;
	}
	private ArrayList<Map<String, String>> getDBList(String query) {
		ArrayList<Map<String, String>> result_list = new ArrayList<Map<String, String>>();
		try {
			Connection conn = getConnection();
			// SQL준비

			System.out.println("query:" + query);
			
			
			// SQL 실행준비
			PreparedStatement ps = conn.prepareStatement(query);
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
