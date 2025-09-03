package egovframework.example.login.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import egovframework.example.login.service.LoginService;
import egovframework.example.login.service.LoginVO;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginController {
	
//	@GetMapping("/login.do")
//	public String loginPage(Model model) throws Exception {
//		System.out.println("LoginPage");
//		
//		return "/login/login";
//	}
	
	private final LoginService loginService;
	
	/**
	 * 로그인 화면
	 * @return login.jsp 뷰
	 */
	@GetMapping("/login.do")
    public String loginPage() {
        return "login/login";  
	}
	
	/**
	 * 로그인 
	 * @param request
	 * @param model
	 * @return "dashboard.do"
	 * @throws Exception
	 */
	@PostMapping("/login.do")
    public String doLogin(HttpServletRequest request, Model model) throws Exception {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        
//        System.out.println("Login attempt for userId: " + userId);

        LoginVO user = loginService.findUserById(userId);
        if (user == null || !user.getPassword().equals(password)) {
//        	System.out.println("Login failed for userId: " + userId);
            model.addAttribute("error", "아이디 또는 비밀번호가 올바르지 않습니다.");
            return "login/login";
        }

        // 로그인 성공: 세션에 사용자 정보 저장
        HttpSession session = request.getSession();
//        System.out.println("Before saving - Session ID: " + session.getId());
        session.setAttribute("user", user);
//        System.out.println("After saving - User in session: " + session.getAttribute("user"));
        
        return "redirect:/dashboard.do";  // 로그인 성공 후 대시보드로 리다이렉트
    }
	
	/**
	 * 로그아웃
	 * @param request
	 * @return "login.do"
	 */
	@GetMapping("/logout.do")
    public String doLogout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login.do";
    }
	

	/**
	 * 회원가입 폼 호출
	 * @return register 뷰
	 */
    @GetMapping("/register.do")
    public String registerPage() {
        return "login/register";
    }

    /**
     * 회원가입 처리
     * @param request
     * @param model
     * @return login 뷰
     * @throws Exception
     */
    @PostMapping("/register.do")
    public String doRegister(HttpServletRequest request, Model model) throws Exception {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        String userName = request.getParameter("userName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String roleId = request.getParameter("roleId");
        String deptId = request.getParameter("deptId");

        LoginVO newUser = new LoginVO();
        newUser.setUserId(userId);
        newUser.setPassword(password);
        newUser.setUserName(userName);
        newUser.setEmail(email);
        newUser.setPhone(phone);
        newUser.setRoleId(roleId);
        newUser.setDeptId(deptId);

        try {
            loginService.registerUser(newUser);
            model.addAttribute("success", "회원가입이 완료되었습니다. 로그인 해주세요.");
            return "login/login";
        } catch (Exception e) {
            model.addAttribute("error", "회원가입 중 오류가 발생했습니다.");
            return "login/register";
        }
    }
}
