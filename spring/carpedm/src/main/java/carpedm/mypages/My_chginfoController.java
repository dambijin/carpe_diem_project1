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

@Controller
public class My_chginfoController {

    @Autowired
    private My_chginfoService mychginfoService;

    private static final Logger logger = LoggerFactory.getLogger(My_chginfoController.class);

    @RequestMapping(value = "/mypage_chginfo", method = RequestMethod.GET)
    protected String mypage_chginfo(Locale locale, Model model,HttpServletRequest request) throws ServletException, IOException{
		
    	//로그인 값 받아옴
    	HttpSession session = request.getSession();
		String m_pid = (String) session.getAttribute("m_pid") + "";
		logger.info("로그인 id : " + m_pid);
		
		if (m_pid == null || "".equals(m_pid) || "null".equals(m_pid)) {
			
			return "sign/sign_in.jsp";
		}
		
		//내정보 받아옴
        List<Map<String, Object>> myInfo = mychginfoService.getMyInfo(m_pid);
        model.addAttribute("myInfo", myInfo);
        
        return "mypages/mypage_chginfo.jsp.noTiles";
    }

    @RequestMapping(value = "/mypage_chginfo", method = RequestMethod.POST)
    public String handleFormSubmit(
    					HttpServletRequest request,
			    		@RequestParam("password") String password,
			            @RequestParam("phonenumber") String phoneNumber, 
			            @RequestParam("email_id") String emailId,
			            @RequestParam("email_domain") String emailDomain, 
			            @RequestParam("email") String emailAgree,
			            @RequestParam("sample6_postcode") String postcode, 
			            @RequestParam("sample6_address") String address,
			            @RequestParam("sample6_address2") String address2) {
        // 폼 데이터 처리 로직 작성
    	HttpSession session = request.getSession();
		String m_pid = (String) session.getAttribute("m_pid") + "";
		logger.info("로그인 id : " + m_pid);
		
		if (m_pid == null || "".equals(m_pid) || "null".equals(m_pid)) {
			
			return "sign/sign_in.jsp";
		}
        Map<String, String> chgInfo = new HashMap();
        System.out.println("여기까진 들어오니 ? ? ? ? ?? ? ? ? ? ? ?");
        chgInfo.put("password", password);
        chgInfo.put("phonenumber", phoneNumber);
        chgInfo.put("email_id", emailId);
        chgInfo.put("email_domain", emailDomain);
        chgInfo.put("email", emailAgree);
        chgInfo.put("sample6_postcode", postcode);
        chgInfo.put("sample6_address", address);
        chgInfo.put("sample6_address2", address2);
        chgInfo.put("m_pid", m_pid);

        int succhk = mychginfoService.updateInfo(chgInfo);
        
        System.out.println("이것이 궁금하다 나는 ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ" + succhk);

        return null;
    }

}
