package carpedm.sign;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/logout")
public class logoutServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);  // 현재 세션 가져오기. 없으면 null 반환
		if (session != null) {
		    session.invalidate();  // 세션 초기화
		}
		Cookie lcCookie = new Cookie("lc", "");
		lcCookie.setMaxAge(0);
		lcCookie.setPath("/");
		response.addCookie(lcCookie);  // 응답에 쿠키를 추가합니다.
		//"Referer" 값가져오기(이전 주소)
		String referer = request.getHeader("Referer");

		// referer 값이 null이 아니면, 그 주소로 리디렉션
		if (referer != null) {
		    response.sendRedirect(referer);
		} else {
		    // referer 값이 없는 경우
			response.sendRedirect("/carpedm_old/main");
		}
	
	}

}
