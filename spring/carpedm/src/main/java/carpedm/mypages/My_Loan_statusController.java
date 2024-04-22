package carpedm.mypages;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.map.HashedMap;
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
public class My_Loan_statusController {

	My_Loan_statusController() {
		System.out.println("loan_statusController 입장");
	}

	private static final Logger logger = LoggerFactory.getLogger(My_Loan_statusController.class);

	@Autowired
	private SqlSession sqlSession;

	// 대출 현황 페이지
	@RequestMapping(value = "/mypage_loan_status", method = RequestMethod.GET)
	protected String loan_status(Locale locale, Model model, HttpServletRequest request)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String m_pid = (String) session.getAttribute("m_pid");
		logger.info("로그인 id : " + m_pid);

		if (m_pid == null || "".equals(m_pid) || "null".equals(m_pid)) {

			return "sign/sign_in.jsp";
		}

		Map<String, String> map = new HashMap();

		// m_pid 자리 넘보지 마셈
		map.put("m_pid", m_pid);

		List<Map<String, String>> list = sqlSession.selectList("mapper.carpedm.mypage.loanstatus", map);
		MemberDTO myInfo = sqlSession.selectOne("mapper.carpedm.mypage.myInfo", m_pid);
		String limitDate = myInfo.getM_limitdate() + "";

		System.out.println(limitDate);
		// 현재 날짜를 가져오기
		java.util.Date currentDate = new java.util.Date();
		java.text.SimpleDateFormat sdfInput = new java.text.SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",
				Locale.ENGLISH);
		java.text.SimpleDateFormat sdfOutput = new java.text.SimpleDateFormat("yyyy-MM-dd"); // 출력 형식 지정
		long diff = 0;
		try {
			// limitDate 문자열을 파싱하여 Date 객체로 변환
			java.util.Date limitDateObj = sdfInput.parse(limitDate);

			// Date 객체를 "yyyy-MM-dd" 형식의 문자열로 변환
			String formattedDate = sdfOutput.format(limitDateObj);

			// 변환된 문자열 출력
			System.out.println(formattedDate);

			// 현재 날짜를 "yyyy-MM-dd" 형식으로 변환
			String currentDateStr = sdfOutput.format(currentDate);

			// limitDate와 formattedDate의 차이 계산
			long diffInMillies = limitDateObj.getTime() - currentDate.getTime(); // 두 날짜의 밀리초 단위 차이
			diff = diffInMillies / (1000 * 60 * 60 * 24); // 밀리초를 일로 변환

			// 결과 출력
			System.out.println("날짜 차이: " + diff + "일");
		} catch (Exception e) {
			e.printStackTrace();
		}

		model.addAttribute("diff", diff);

		model.addAttribute("list", list);
		model.addAttribute("myInfo", myInfo);

		return "mypages/mypage_loan_status.jsp";
	}

	@ResponseBody
	@RequestMapping(value = "/mypage_loan_status", method = RequestMethod.POST)
	public Map<String, String> handleFormSubmit(@RequestParam("l_id") String l_id, HttpServletRequest request

	) {

		HttpSession session = request.getSession();
		String m_pid = (String) session.getAttribute("m_pid") + "";
		logger.info("로그인 id : " + m_pid);
		// 폼 데이터 처리 로직 작성
		Map<String, String> weapon = new HashMap();

		weapon.put("l_id", l_id);
		weapon.put("m_pid", m_pid);

		Map response = new HashMap();

		System.out.println(weapon);
		int succhk = sqlSession.insert("mapper.carpedm.mypage.weapon", weapon);
		if (succhk > 0) {
			response.put("message", "success");
		} else {
			response.put("message", "fail");
		}

		return response;

//		return "mypages/mypage_loan_status.jsp";
	}

}
