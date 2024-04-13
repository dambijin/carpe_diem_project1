package carpedm.mypage;

import java.io.BufferedReader;
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

@WebServlet("/mypage_reservation_list")
public class mypage_reservation_listServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String search = request.getParameter("search");
		if (search == null || "".equals(search)) {
			search = "";
		}

		HttpSession getSession = request.getSession();
		String login_m_pid = (String) getSession.getAttribute("m_pid");

		if (login_m_pid == null || login_m_pid.equals("")) {
			request.getRequestDispatcher("/sign_in").forward(request, response);
			return;
		}
		ArrayList<Map<String, String>> myInfo = getDBList("select * from member where m_pid = " + login_m_pid);
		request.setAttribute("myInfo", myInfo);

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

		ArrayList<Map<String, String>> list = getLoan(login_m_pid, search);
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

		request.setAttribute("list", pageList);
		request.setAttribute("allcount", list.size());
//		request.setAttribute("list", list);

		ArrayList<Map<String, String>> library = getDBList("select lb_name from library");
		request.setAttribute("library", library);

		request.getRequestDispatcher("/mypage/mypage_reservation_list.jsp").forward(request, response);
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
		String queryUpdate = "";
		queryDelete = "delete reservation where m_pid = " + login_m_pid + " and r_id in (" + r_id + ")";
		queryUpdate = "UPDATE book SET b_resstate = 'Y' WHERE book.b_id IN (SELECT book.b_id FROM reservation INNER JOIN book ON reservation.b_id = book.b_id WHERE m_pid = " + login_m_pid + "AND r_id = "+ r_id + " )";
		System.out.println("queryDelete" + queryDelete);
		System.out.println( "queryUpdate" + queryUpdate);
		setDBList(queryUpdate);
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

	private ArrayList<Map<String, String>> getLoan(String m_pid, String search) {
		ArrayList<Map<String, String>> result_list = new ArrayList<Map<String, String>>();
		try {
			Connection conn = getConnection();
			// SQL준비
			String query = "";
			query += " select";
			query += " book.b_id, r_id, b_title, b_author, b_publisher, r_resdate, reservation.r_resstate, loan.L_returndate, library.lb_name ";
			query += " from";
			query += " reservation";
			query += " inner join book";
			query += " on reservation.b_id = book.b_id";
			query += " inner join library";
			query += " on book.lb_id = library.lb_id";
			query += " LEFT JOIN ";
			query += " loan ON reservation.b_id = loan.b_id";
			query += " where reservation.m_pid = " + m_pid + " and b_title like '%" + search + "%'";

			System.out.println("query:" + query);

			// SQL 실행준비
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Map<String, String> map = new HashMap<String, String>();

				
				map.put("r_id", rs.getString("r_id"));
				map.put("b_title", rs.getString("b_title"));
				map.put("b_author", rs.getString("b_author"));
				map.put("b_publisher", rs.getString("b_publisher"));
				map.put("r_resdate", rs.getString("r_resdate"));
				map.put("lb_name", rs.getString("lb_name"));
				map.put("r_resstate", rs.getString("r_resstate"));
				map.put("l_returndate", rs.getString("l_returndate"));

//				
				result_list.add(map);
//		    	System.out.println(rs.getString("l_returndate"));
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
