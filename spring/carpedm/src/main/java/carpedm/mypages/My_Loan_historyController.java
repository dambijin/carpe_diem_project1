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
public class My_Loan_historyController {

    private static final Logger logger = LoggerFactory.getLogger(My_Loan_historyController.class);

    @Autowired
    private My_Loan_historyService myLoanHistoryService;
    @Autowired
    private SqlSession sqlSession;

    // 대출 내역 페이지
    @RequestMapping(value = "/mypage_loan_history", method = RequestMethod.GET)
    protected String loan_history(Locale locale, Model model,
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

        int currentPage = Integer.parseInt(page);
        int itemsPerPage = Integer.parseInt(perPage);
        int startRow = (currentPage - 1) * itemsPerPage + 1;
        int endRow = currentPage * itemsPerPage;

        Map<String, String> temp = new HashMap();
        temp.put("keyword", keyword);
        temp.put("m_pid", m_pid);
        temp.put("startRow", startRow + "");
        temp.put("endRow", endRow + "");

        List<Map<String, String>> list = myLoanHistoryService.getLoanHistory(temp);
        MemberDTO myInfo = myLoanHistoryService.getMemberInfo(m_pid);
      
        Map count_temp = sqlSession.selectOne("mapper.carpedm.mypage.loanCount", temp);
		System.out.println(count_temp);
		int loan_count = Integer.parseInt(String.valueOf(count_temp.get("count")));
		logger.info("북카운트:" + loan_count);
		
		// 페이지 처리를 위한 계산(모델에 넣어야함)
		int startPage = Math.max(currentPage - 2, 1);
		int totalPages = loan_count > 0 ? (int) Math.ceil(loan_count / Double.parseDouble(perPage)) : 1;
		int endPage = Math.min(startPage + 4, totalPages); 
	    startPage = Math.max(1, endPage - 4);
	    
	    model.addAttribute("page", page);
		model.addAttribute("perPage", perPage);
	    model.addAttribute("loan_count", loan_count);
	    model.addAttribute("start_page", startPage);
		model.addAttribute("end_page", endPage);
		model.addAttribute("total_pages", totalPages);


        model.addAttribute("myInfo", myInfo);
        model.addAttribute("list", list);

        return "mypages/mypage_loan_history.jsp";
    }
}
