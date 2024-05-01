package carpedm.mypages;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
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

import carpedm.dto.MemberDTO;

@Controller
public class My_BookCartController {

    private final Logger logger = LoggerFactory.getLogger(My_BookCartController.class);

    @Autowired
    private My_BookCartService myBookCartService;
    @Autowired
    private SqlSession sqlSession;

    // 예약페이지
    @RequestMapping(value = "/mypage_bookcart", method = RequestMethod.GET)
    protected String reservation_list(Locale locale, Model model,
            @RequestParam(value = "search", defaultValue = "") String keyword,
            @RequestParam(value = "page", defaultValue = "1") String page,
            @RequestParam(value = "perPage", defaultValue = "10") String perPage,
            HttpServletRequest request) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String m_pid = (String) session.getAttribute("m_pid") + "";
        logger.info("로그인 id : " + m_pid);

        if (m_pid == null || "".equals(m_pid) || "null".equals(m_pid)) {
            return "sign/sign_in.jsp";
        }

        Map<String, String> map = new HashMap();

        // 페이징관련
        int currentPage = Integer.parseInt(page);
        int itemsPerPage = Integer.parseInt(perPage);
        int startRow = (currentPage - 1) * itemsPerPage + 1;
        int endRow = currentPage * itemsPerPage;

      
        map.put("m_pid", m_pid + "");
        map.put("keyword", keyword);
        map.put("startRow", startRow + "");
        map.put("endRow", endRow + "");

        List list = myBookCartService.getBookCartList(map);
        MemberDTO myInfo = myBookCartService.getMemberInfo(m_pid);
       
        model.addAttribute("list", list);
        model.addAttribute("myInfo", myInfo);
        
        Map count_temp = sqlSession.selectOne("mapper.carpedm.mypage.bookCartCount", map);
		System.out.println(count_temp);
		int cart_count = Integer.parseInt(String.valueOf(count_temp.get("count")));
		logger.info("북카운트:" + cart_count);
		
		// 페이지 처리를 위한 계산(모델에 넣어야함)
		int startPage = Math.max(currentPage - 2, 1);
		int totalPages = cart_count > 0 ? (int) Math.ceil(cart_count / Double.parseDouble(perPage)) : 1;
		int endPage = Math.min(startPage + 4, totalPages); 
	    startPage = Math.max(1, endPage - 4);
	    
	    model.addAttribute("page", page);
		model.addAttribute("perPage", perPage);
	    model.addAttribute("cart_count", cart_count);
	    model.addAttribute("start_page", startPage);
		model.addAttribute("end_page", endPage);
		model.addAttribute("total_pages", totalPages);

        return "mypages/mypage_bookcart.jsp";
    }

    // 취소 메소드
    @RequestMapping(value = "/mypage_bookcart", method = RequestMethod.POST)
    protected String reservation_cancle(Locale locale, Model model, HttpServletRequest request,
            @RequestParam(value = "ids", defaultValue = "") String bc_id) throws ServletException, IOException {

        HttpSession session = request.getSession();
        String m_pid = (String) session.getAttribute("m_pid") + "";
        logger.info("로그인 id : " + m_pid);
        
        Map<String, String> map = new HashMap();
        map.put("m_pid", m_pid);
        map.put("bc_id", bc_id);
        
        int delete = myBookCartService.bookCartDelete(map);
        logger.info("딜리트 성공 :" + delete);
        

        return "mypages/mypage_bookcart.jsp";
    }
}
