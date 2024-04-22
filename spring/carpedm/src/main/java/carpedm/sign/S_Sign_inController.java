package carpedm.sign;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import carpedm.dto.MemberDTO;

@Controller
public class S_Sign_inController {

	private static final Logger logger = LoggerFactory.getLogger(S_Sign_inController.class);

	@Autowired
	private SqlSession sqlSession;

	@RequestMapping(value = "/sign_in", method = RequestMethod.GET)
	protected String sign_in(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false); // 현재 세션 가져오기. 없으면 null 반환
		if (session != null) {
			session.invalidate(); // 세션 초기화
		}
		Cookie lcCookie = new Cookie("lc", "");
		lcCookie.setMaxAge(0);
		lcCookie.setPath("/");
		response.addCookie(lcCookie); // 응답에 쿠키를 추가합니다.

		return "sign/sign_in.jsp";
	}

	@RequestMapping(value = "/sign_in", method = RequestMethod.POST)
	@ResponseBody
	public String login(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "userid", defaultValue = "") String userid,
			@RequestParam(value = "userpw", defaultValue = "") String userpw) throws IOException {

		Map map = new HashMap();
		map.put("m_id", userid);
		map.put("m_pw", userpw);

		List<MemberDTO> idpw_list = sqlSession.selectList("mapper.carpedm.sign.sign_in", map);
		if (idpw_list.size() > 0) { // 위에 sql에서 id와 pw값을 이미 검사했기때문에 0개보다 크면 무조건 로그인성공했다고 가정함
			HttpSession session = request.getSession();
			session.setAttribute("m_pid", idpw_list.get(0).getM_pid());
			session.setAttribute("m_managerchk", idpw_list.get(0).getM_managerchk());
			session.setAttribute("m_name", idpw_list.get(0).getM_name());

			// 세션 ID를 쿠키에 저장합니다.
			String rnd_sessionKey = System.currentTimeMillis() + "";
			if (idpw_list.get(0).getM_managerchk().equals("Y")) {
				System.out.println("매니저임");
				rnd_sessionKey = "9" + rnd_sessionKey;
			} else {
				rnd_sessionKey = "1" + rnd_sessionKey;
			}
			System.out.println(rnd_sessionKey);
			Cookie sessionCookie = new Cookie("lc", rnd_sessionKey);
			sessionCookie.setPath("/"); // 쿠키의 유효 경로를 설정합니다.
			response.addCookie(sessionCookie);
			return "{\"success\": true}";
		} else {
			return "{\"success\": false}";
		}
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		Cookie lcCookie = new Cookie("lc", "");
		lcCookie.setMaxAge(0);
		lcCookie.setPath("/");
		response.addCookie(lcCookie);

		// 로그아웃 후 리다이렉트할 페이지로 이동
		response.sendRedirect("/carpedm/main");
	}

}