package carpedm.mypages;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class My_Wishbook_detailController {

	

	My_Wishbook_detailController() {
		System.out.println("Wishbook_detailController 입장");
	}

	private static final Logger logger = LoggerFactory.getLogger(My_Wishbook_detailController.class);

	@Autowired
	private SqlSession sqlSession;

	// 희망 도서 신청 목록 페이지
	@RequestMapping(value = "/wishbook_detail", method = RequestMethod.GET)
	protected String wishbook_detail(Locale locale, @RequestParam("w_id") int w_id, Model model,HttpServletRequest request) {
		HttpSession session = request.getSession();
		String m_pid = (String) session.getAttribute("m_pid") + "";
		logger.info("로그인 id : " + m_pid);
		
		if (m_pid == null || "".equals(m_pid) || "null".equals(m_pid)) {
			
			return "sign/sign_in.jsp";
		}
		
		Map<String, Integer> map = new HashedMap();
		
		//m_pid 자리 넘보지 마셈
		map.put("m_pid", Integer.parseInt(m_pid));
		map.put("w_id", w_id);
		
		
		List list = sqlSession.selectList("mapper.carpedm.mypage.wishboodetail", map);

		model.addAttribute("list", list);
		model.addAttribute("w_id", Integer.valueOf(w_id));
		return "mypages/wishbook_detail.jsp.noTiles";
	}

	@RequestMapping(value = "/wishbook_detail", method = RequestMethod.POST)
	@ResponseBody
	public String deleteDetail(@RequestParam("w_id") String w_id,HttpServletRequest request) {
		HttpSession session = request.getSession();
		String m_pid = (String) session.getAttribute("m_pid") + "";
		logger.info("로그인 id : " + m_pid);
		
		System.out.println("post 접근완료");
		// 폼 데이터 처리 로직 작성
		Map<String, String> delete = new HashedMap();

		delete.put("w_id", w_id);
		delete.put("m_pid", m_pid);
		System.out.println(delete);
		int succhk = sqlSession.insert("mapper.carpedm.mypage.deleteDetail", delete);
		logger.info("딜리트 결과 : " + succhk);
		return "0";
	}

}
