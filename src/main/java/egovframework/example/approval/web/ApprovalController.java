package egovframework.example.approval.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import egovframework.example.approval.service.ApprovalDocumentVO;
import egovframework.example.approval.service.ApprovalService;
import egovframework.example.login.service.LoginVO;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ApprovalController {
	
	private final ApprovalService approvalService;
	
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
//		System.out.println("Session ID: " + session.getId());
//	    System.out.println("User in session: " + session.getAttribute("user"));
		
		// 세션에서 로그인 사용자 가져오기
		LoginVO user = (LoginVO) session.getAttribute("user");
        if (user == null) {
        	
//        	System.out.println("No user in session, redirecting to login");
        	
            return "redirect:/login.do";
        }
        model.addAttribute("userName", user.getUserName());
        return "/approval/dashboard";
    }
	
	/**
	 * 결재 문서 작성 폼
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 */
    @GetMapping("/document/form.do")
    public String documentForm(HttpSession session, Model model) throws Exception {
        LoginVO user = (LoginVO) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login.do";
        }
        return "/approval/documentForm";
    }
    
    /**
     * 결재 문서 등록 처리
     * @param request
     * @param session
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/document/submit.do")
    public String submitDocument(HttpServletRequest request, HttpSession session, Model model) throws Exception {
        LoginVO user = (LoginVO) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login.do";
        }
        
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String documentType = request.getParameter("documentType");
        
        ApprovalDocumentVO document = new ApprovalDocumentVO();
        document.setTitle(title);
        document.setContent(content);
        document.setDocumentType(documentType);
        document.setAuthorId(user.getUserId());
        document.setDeptId(user.getDeptId());
        
        try {
            approvalService.insertDocument(document);
            model.addAttribute("success", "결재 문서가 성공적으로 등록되었습니다.");
            return "redirect:/dashboard.do";
        } catch (Exception e) {
            model.addAttribute("error", "문서 등록 중 오류가 발생했습니다.");
            return "/approval/documentForm";
        }
    }
	
}
