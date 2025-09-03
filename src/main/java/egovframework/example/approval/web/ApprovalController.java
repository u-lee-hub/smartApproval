package egovframework.example.approval.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import egovframework.example.login.service.LoginVO;

@Controller
public class ApprovalController {
	
//	@GetMapping(value = "/dashboard.do")
//	public String dashboard(Model model) throws Exception {
//		return "/approval/dashboard";
//	}
	
	/**
	 * 대시보드
	 * @param session
	 * @param model
	 * @return dashboard 뷰
	 * @throws Exception
	 */
	@GetMapping("/dashboard.do")
    public String dashboard(HttpSession session, Model model) throws Exception {
		System.out.println("Session ID: " + session.getId());
	    System.out.println("User in session: " + session.getAttribute("user"));
		
		
		// 세션에서 로그인 사용자 가져오기
		LoginVO user = (LoginVO) session.getAttribute("user");
        if (user == null) {
        	
        	System.out.println("No user in session, redirecting to login");
        	
            return "redirect:/login.do";
        }
        model.addAttribute("userName", user.getUserName());
        return "/approval/dashboard";
    }
	
}
