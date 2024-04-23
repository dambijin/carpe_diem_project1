package carpedm.mainpages;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class Main_PageController{
	
	private final Logger logger = LoggerFactory.getLogger(Main_PageController.class);
	
    @Autowired	
    private Main_PageService main_PageService;
	
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	protected String main(Locale locale, Model model)
			throws ServletException, IOException {
		String now_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        
		model.addAttribute("library_list", main_PageService.getLibraryList());		
        model.addAttribute("notice_list", main_PageService.getNoticeList());		
        model.addAttribute("book_list", main_PageService.getBookList());
        model.addAttribute("banner_list", main_PageService.getBannerList(now_date));
        model.addAttribute("recommend_list", main_PageService.getRecommendBookList());
        
		
		return "mainpages/main.jsp";
	}			
}
