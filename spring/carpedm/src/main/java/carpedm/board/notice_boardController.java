package carpedm.board;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.map.HashedMap;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import carpedm.dto.MemberDTO;
import carpedm.dto.NoticeBoardDTO;

@Controller // MVC컨트롤러로 선언
public class notice_boardController extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(notice_boardController.class);

	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	notice_boardService nbService;

	// notice_board 페이지 시작
//  @RequestMapping이 NoticeBoardDTO를 new(생성)를 하고 필드에 값을 넣어줌
//	그래서 notice_boardController 파일 안에서 new를 따로 적지 않은 것
	@RequestMapping(value = "/notice_board", method = RequestMethod.GET)
	protected String notice_board(Locale locale, Model model, @ModelAttribute NoticeBoardDTO dto,
			@RequestParam(value = "search", defaultValue = "") String search,
			@RequestParam(value = "n_search", defaultValue = "제목") String type,
			@RequestParam(value = "page", defaultValue = "1") String page,
			@RequestParam(value = "perPage", defaultValue = "10") String perPage,
			HttpServletRequest request) throws ServletException, IOException {
		// 페이징시작
		// 게시물 총 개수
		int count = nbService.noticeCount(dto);
		int currentPage = Integer.parseInt(page);
		int itemsPerPage = Integer.parseInt(perPage);
		int startRow = (currentPage - 1) * itemsPerPage + 1;
		int endRow = currentPage * itemsPerPage;
		dto.setStartrow(startRow);
		dto.setEndrow(endRow);
		
		int startPage = Math.max(currentPage - 2, 1);	
		int totalPages = count > 0 ? (int) Math.ceil(count / Double.parseDouble(perPage)) : 1;
		int endPage = Math.min(startPage + 4, totalPages);
		startPage = Math.max(1, endPage - 4);

		
		System.out.println("총개수개수 : "+count);
		model.addAttribute("allcount", count);
		model.addAttribute("page", page);
		model.addAttribute("perPage", perPage);
		model.addAttribute("start_page", startPage);
		model.addAttribute("end_page", endPage);
		model.addAttribute("total_pages", totalPages);
		// 페이징 끝
		
//		전달인자 확인
//		System.out.println("타입 : " + type);
//		System.out.println("검색어 : " + search);
		HashMap map = nbService.listNotice(dto, search, type, request);
		
		List list= (List) map.get("list");
		MemberDTO member= (MemberDTO)map.get("memberDTO");
		model.addAttribute("list", list);
		model.addAttribute("member", member);

//		forward
		return "board/notice_board.jsp";
	}
	
	// notice_board 페이지 끝
	
	
	// notice_detaile 페이지 시작
	
	@RequestMapping(value = "/notice_detail", method = RequestMethod.GET)
	// @RequestParam("N_ID") int N_ID : @RequestParam("N_ID")생략 가능
	// N_ID 라는 안에 내용이 일치해서 생략이 가능 그 외에 불가능
	protected String notice_detail(Locale locale, Model model, @RequestParam("N_ID") int N_ID,
			HttpServletRequest request) throws ServletException, IOException {
		List list=nbService.noticeDetail(N_ID);

//		로그인이 되어있을때만 model에 넣기	
//		session에서 안빼면 단점이 memberDTO (DB)를 한번더 갔다와서 효율이 떨어질 수 있음
//		HttpSession session = request.getSession();
//		String mpid = (String) session.getAttribute("m_pid");
		MemberDTO memDTO=nbService.loginID(request);
		if (memDTO != null) {
			model.addAttribute("m_pid", memDTO);
		}

		model.addAttribute("notice", list);

		return "board/notice_detail.jsp";
	}

	// 딜리트 메소드
	@RequestMapping(value = "/notice_delete", method = RequestMethod.GET)
	protected String noticeDelete(Locale locale, Model model, 
			@RequestParam("N_ID") int N_ID,
			@RequestParam("n_mpid") String mpid,
			HttpServletRequest request) throws ServletException, IOException {
		MemberDTO memberDTO=nbService.loginID(request);
		String login_mpid = (String) memberDTO.getM_pid();
		
		if (mpid.equals(login_mpid)) {
			nbService.noticeDeleteCount(N_ID);
		}
		return "redirect:/notice_board";

	}
	// notice_detaile 페이지 끝

}
