package carpedm.mainpages;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class Main_Libs_InfolistController{
	
	private final Logger logger = LoggerFactory.getLogger(Main_Libs_InfolistController.class);
	
	@Autowired	
	private Main_Libs_InfolistService libInfoService;
	
	@RequestMapping(value = "/libs_infolist", method = RequestMethod.GET)
	protected String libs_infolist(Locale locale, Model model)
			throws ServletException, IOException {
		logger.info("도서관정보 접근");
		model.addAttribute("list", libInfoService.getLibInfoList());		
		return "mainpages/libs_infolist.jsp";
	}		
	
}
