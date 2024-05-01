package carpedm.mypages;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

    private static final Logger logger = LoggerFactory.getLogger(My_Loan_statusController.class);

    @Autowired
    private My_Loan_statusService myLoanStatusService;

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

        List<Map<String, String>> list = myLoanStatusService.getLoanStatus(m_pid);
        MemberDTO myInfo = myLoanStatusService.getMemberInfo(m_pid);
        // 날짜 계산 등의 비즈니스 로직은 서비스 클래스에서 처리
        // ...

        model.addAttribute("list", list);
        model.addAttribute("myInfo", myInfo);

        return "mypages/mypage_loan_status.jsp";
    }

    @ResponseBody
    @RequestMapping(value = "/mypage_loan_status", method = RequestMethod.POST)
    public Map<String, String> handleFormSubmit(@RequestParam("l_id") String l_id, HttpServletRequest request) {

        HttpSession session = request.getSession();
        String m_pid = (String) session.getAttribute("m_pid") + "";
        logger.info("로그인 id : " + m_pid);
        // 폼 데이터 처리 로직 작성
        Map<String, String> weapon = new HashMap();

        weapon.put("l_id", l_id);
        weapon.put("m_pid", m_pid);

        Map<String, String> response = new HashMap();

        boolean success = myLoanStatusService.insertWeapon(weapon);
        if (success) {
            response.put("message", "success");
        } else {
            response.put("message", "fail");
        }

        return response;
    }

}
