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

@WebServlet("/mypage_wishbook_list")
public class mypage_wishbook_listServlet extends HttpServlet {

	int start_page = -1;
	int end_page = -1;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession getSession = request.getSession();
		String login_m_pid = (String) getSession.getAttribute("m_pid");

//		String id = request.getParameter("id");
//		request.setAttribute("id2", id);
		String search = request.getParameter("search");
		if (search == null || "".equals(search)) {
			search = "";
		}

//		ArrayList<Map<String, String>> myInfo = getDBList("select * from member");
		ArrayList<Map<String, String>> library = getDBList("select lb_name from library");
		request.setAttribute("library", library);
//		request.setAttribute("myInfo", myInfo);

		if (login_m_pid == null || login_m_pid.equals("")) {
			request.getRequestDispatcher("/sign_in").forward(request, response);
			return;
		}

		ArrayList<Map<String, String>> myInfo = getDBList("select * from member where m_pid = " + login_m_pid);

		
		
		// 계산한 다음에
		String limitDate = "";
		if (myInfo.get(0).get("M_LIMITDATE") != null && !myInfo.get(0).get("M_LIMITDATE").equals("0")) {
			System.out.println("if성공");
			limitDate = myInfo.get(0).get("M_LIMITDATE");
		}

		// 현재 날짜를 가져오기
		java.util.Date currentDate = new java.util.Date();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd"); // 출력 형식 지정
		String formattedDate = sdf.format(currentDate); // 현재 날짜를 지정한 형식으로 변환

		// limitDate와 formattedDate의 차이 계산
		try {
			java.util.Date limitDateObj = sdf.parse(limitDate);
			java.util.Date formattedDateObj = sdf.parse(formattedDate);

			long diffInMillies = limitDateObj.getTime() - formattedDateObj.getTime(); // 두 날짜의 밀리초 단위 차이
			long diff = diffInMillies / (1000 * 60 * 60 * 24); // 밀리초를 일로 변환

			myInfo.get(0).put("diff", diff+"");
		} catch (Exception e) {
			e.printStackTrace();
		}

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

		ArrayList<Map<String, String>> list = getWishList(login_m_pid, search);
		System.out.println(list);
		ArrayList<Map<String, String>> pageList = new ArrayList<>();

		String query2 = "";
		query2 += " select count(*) from wishlist inner join library on library.lb_id = wishlist.lb_id";
		query2 += " where wishlist.m_pid = " + login_m_pid;

		ArrayList<Map<String, String>> totalList = getDBList(query2);

		int totalViewCount = Integer.parseInt(totalList.get(0).get("COUNT(*)"));
//		System.out.println("총 글 개수 : " + totalViewCount);

		request.setAttribute("totalViewCount", totalViewCount);
		request.setAttribute("list", list);
		request.getRequestDispatcher("/mypage/mypage_wishbook_list.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		System.out.println("접속성공");

		String r_id = request.getParameter("ids");
		r_id = r_id.replace("[", "");
		r_id = r_id.replace("]", "");
		r_id = r_id.replace("\"", "");

		System.out.println(r_id);
		HttpSession getSession = request.getSession();
		String login_m_pid = (String) getSession.getAttribute("m_pid");
		String queryDelete = "";

		queryDelete = "delete wishlist where m_pid = " + login_m_pid + " and w_id in (" + r_id + ")";
		System.out.println("queryDelete" + queryDelete);

		setDBList(queryDelete);

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

//	 위시리스트 불러오고 검색까지
	private ArrayList<Map<String, String>> getWishList(String m_pid, String search) {
		ArrayList<Map<String, String>> result_list = new ArrayList<Map<String, String>>();
		try {
			Connection conn = getConnection();
			// SQL준비
			String query = "";
			query += "SELECT * FROM (";
			query += " SELECT rownum AS rnum, w_title, w_id, lb_name, w_author, w_pubyear, w_isbn, w_content, w_publisher, w_tel, w_date, m_pid, w_state";
			query += " FROM wishlist";
			query += " inner join library";
			query += " on library.lb_id = wishlist.lb_id";
			query += " where wishlist.m_pid = " + m_pid + " and w_title like '%" + search + "%')";
			query += " WHERE rnum >= " + start_page + " and rnum <= " + end_page;

			System.out.println("query:" + query);
			// SQL 실행준비
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Map<String, String> map = new HashMap<String, String>();

				map.put("w_id", rs.getString("w_id"));// 이걸 쓸 줄 알아야 덜 지저분해질 것 같다...
				map.put("lb_name", rs.getString("lb_name"));// 이걸 쓸 줄 알아야 덜 지저분해질 것 같다...
				map.put("w_title", rs.getString("w_title"));// 이걸 쓸 줄 알아야 덜 지저분해질 것 같다...
				map.put("w_author", rs.getString("w_author"));
				map.put("w_pubyear", rs.getString("w_pubyear"));
				map.put("w_content", rs.getString("w_content"));
				map.put("w_publisher", rs.getString("w_publisher"));
				map.put("w_state", rs.getString("w_state"));

				map.put("w_date", rs.getString("w_date"));
//				map.put("lb_name", rs.getString("lb_name"));
//				map.put("lb_content", rs.getString("lb_content").replace("\n", "<br>").replace("\"", "\\\"").replace("\r", "\\r"));
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

	private ArrayList<Map<String, String>> getDBList(String query) {
		ArrayList<Map<String, String>> result_list = new ArrayList<Map<String, String>>();
		try {
			Connection conn = getConnection();
			// SQL준비

//			System.out.println("query:" + query);

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

	private int setDBList(String query) {
		int result = -1;
		try {
			Connection conn = getConnection();
			// SQL준비

			System.out.println("query:" + query);
			// SQL 실행준비
			PreparedStatement ps = conn.prepareStatement(query);
			result = ps.executeUpdate();

			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
