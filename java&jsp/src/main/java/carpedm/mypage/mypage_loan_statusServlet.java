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

import graphql.language.Document;
import graphql.org.antlr.v4.runtime.ParserInterpreter;


@WebServlet("/mypage_loan_status")
public class mypage_loan_statusServlet extends HttpServlet {
	
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		String id = request.getParameter("id");
//		request.setAttribute("id2", id);
		

	
		
		ArrayList<Map<String, String>> library = getDBList("select lb_name from library");
		
		request.setAttribute("library", library);
		
		HttpSession getSession = request.getSession();
		String login_m_pid = (String) getSession.getAttribute("m_pid");
		
		
		
		//로그인 되어 있지 않으면 로그인 창으로 이동
		if (login_m_pid == null || login_m_pid.equals("")) {
			request.getRequestDispatcher("/sign_in").forward(request, response);
			return;
		}

		ArrayList<Map<String, String>> myInfo = getDBList("select * from member where m_pid = " + login_m_pid);
		request.setAttribute("myInfo", myInfo);
		
		ArrayList<Map<String,String>> list = getLoan(login_m_pid);
		request.setAttribute("list", list);
		request.getRequestDispatcher("/mypage/mypage_loan_status.jsp").forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		String l_id = request.getParameter("l_id");
		System.out.println(l_id);
		String query = "UPDATE loan set l_returndate = l_returndate + 7, l_extendcount = l_extendcount + 1 where l_id = " + l_id +  "and l_extendcount < 2";
		System.out.println(query);
		String m_pid = request.getParameter("m_pid");
		System.out.println(m_pid);
		
		ArrayList<Map<String, String>> queryStr = getDBList("select l_extendcount from loan where l_id = " + l_id);
		int queryInt = Integer.parseInt(queryStr.get(0).get("L_EXTENDCOUNT"));
		if(m_pid != null && !m_pid.equals("null") && !m_pid.equals(""))
		{
			int successRow = setDBList(query);
			System.out.println("변경된 행 수:" + successRow);
			if (successRow > 0) {
				// 완료하고 결과값을 보내기 위해
				response.getWriter().write("{\"message\": \"success\"}");
			} else if(queryInt >= 2) {
				//select문을 던져서 ext..값을 가져와보고 2가 넘었는지 안넘엇는지 확인해서 넘었으면 횟수초과라고 메세지 던지기
				 
				response.getWriter().write("{\"message\": \"앙실패띠\"}");
				
				}
		}
		else
		{
			System.out.println("현재 로그인되어있지 않습니다.");
			// 완료하고 결과값을 보내기 위해
			response.getWriter().write("{\"message\": \"fail\"}");
		}
	


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
	private ArrayList<Map<String,String>> getLoan(String m_pid) {
		ArrayList<Map<String,String>> result_list = new ArrayList<Map<String,String>>();
		try {
			Connection conn = getConnection();
			// SQL준비
			String query = "";
			query += "select";
			query += " l_id, b_title, b_author, b_publisher, l_loandate, l_returndate, l_returnrealdate, l_extendcount, library.lb_name";
			query += " from";
			query += " loan";
			query += " INNER JOIN book ON loan.b_id = book.b_id INNER JOIN library ON book.lb_id = library.lb_id";	
			query += " WHERE loan.l_returnrealdate IS NULL AND m_pid = " + m_pid;
			
			

			System.out.println("query:" + query);
			// SQL 실행준비
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Map<String,String> map = new HashMap<String, String>();

				map.put("l_id", rs.getString("l_id"));//이걸 쓸 줄 알아야 덜 지저분해질 것 같다...
				map.put("b_title", rs.getString("b_title"));//이걸 쓸 줄 알아야 덜 지저분해질 것 같다...
				map.put("b_author", rs.getString("b_author"));
				map.put("b_publisher", rs.getString("b_publisher"));
				map.put("l_loandate", rs.getString("l_loandate"));
				map.put("l_returndate", rs.getString("l_returndate"));
				map.put("l_extendcount", rs.getString("l_extendcount"));
				map.put("lb_name", rs.getString("lb_name"));
				
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
