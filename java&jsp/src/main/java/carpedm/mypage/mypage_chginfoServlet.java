package carpedm.mypage;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

@WebServlet("/mypage_chginfo")
public class mypage_chginfoServlet extends HttpServlet {
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//	String id = request.getParameter("id");
//	request.setAttribute("id2", id);

//		ArrayList<Map<String, String>> myInfo = getDBList("select * from member");
//		request.setAttribute("myInfo", myInfo);
		
		  HttpSession getSession = request.getSession();
	      String login_m_pid = (String) getSession.getAttribute("m_pid");
	      
	      if (login_m_pid == null || login_m_pid.equals("")) {
				request.getRequestDispatcher("/sign_in").forward(request, response);
				return;
			}
	      
		ArrayList<Map<String, String>> myInfo = getDBList("select * from member where m_pid = " + login_m_pid);
		request.setAttribute("myInfo", myInfo);
		//	Gson gson = new Gson();
//	String json = gson.toJson(list);
//	request.setAttribute("list", json);

		request.getRequestDispatcher("/mypage/mypage_chginfo.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("포스트접근성공");
	

//		request.getRequestDispatcher("/mypage/mypage_chginfo.jsp").forward(request, response);
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=utf-8;");
			getDBUpdate(request);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	
		//성공
		response.sendRedirect("mypage_loan_status");
		
		//실패
//		response.sendRedirect("mypage_chgInfoServlet");
		
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

	// 가져오기
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

	// 업에트
	
	    
	    private int getDBUpdate(HttpServletRequest request) {
			int result = -1;
			try {
				HttpSession getSession = request.getSession();
			    String login_m_pid = (String) getSession.getAttribute("m_pid");
				Connection conn = getConnection();
				// SQL준비
				String m_pid = login_m_pid;
				String m_pw = request.getParameter("password");
				String m_tel = request.getParameter("phonenumber");
				String email = request.getParameter("email_id")+"@"+request.getParameter("email_domain");
				String address = request.getParameter("sample6_postcode") + "\n" + request.getParameter("sample6_address") + "\n" + request.getParameter("sample6_address2");
				String emailChk = request.getParameter("email");

				String sql = "";
				sql += " UPDATE member";
				sql += " SET m_pw = '"+ m_pw +"',";
				sql += " m_tel = '"+ m_tel +"',";
				sql += " m_email = '"+ email +"',";
				sql += " m_email_agree = '" + emailChk + "',";
				sql += " m_address = '" + address + "'";
				sql += " WHERE m_pid = "+ m_pid;
				
				System.out.println(sql);
				// SQL 실행준비
				PreparedStatement ps = conn.prepareStatement(sql);
//				ps.setString(1, m_pw);
//				ps.setString(2, m_tel);
//				ps.setString(3, email);
//				ps.setString(4, emailChk);
//				ps.setString(5, adress);
//				ps.setString(6, m_pid);
				

				result = ps.executeUpdate();
				
				System.out.println("바뀐 행 수:" + result);
				

				ps.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}
	   
	}
	
	
	

