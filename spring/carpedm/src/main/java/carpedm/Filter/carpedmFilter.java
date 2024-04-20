package carpedm.Filter;


import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/*")
public class carpedmFilter extends HttpFilter implements Filter {

    public carpedmFilter() {
        super();
//		System.out.println("필터 생성");
    }

	public void destroy() {
//		System.out.println("필터 소멸");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//		System.out.println("필터 작동");
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		HttpSession session = req.getSession(false);  // 현재 세션 가져오기. 없으면 null 반환
		if (session == null) {
			System.out.println("세션만료 감지");
			Cookie lcCookie = new Cookie("lc", "");
			lcCookie.setMaxAge(0);
			lcCookie.setPath("/");
			resp.addCookie(lcCookie);  // 응답에 쿠키를 추가합니다.
		}
	
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
//		System.out.println("필터 init실행");
	}
}
