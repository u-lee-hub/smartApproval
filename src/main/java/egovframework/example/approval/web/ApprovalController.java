package egovframework.example.approval.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import egovframework.example.approval.service.ApprovalDocumentVO;
import egovframework.example.approval.service.ApprovalLineVO;
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
		
		// 로그인 상태 확인
	    boolean isLoggedIn = (user != null);
	    model.addAttribute("isLoggedIn", isLoggedIn);
		
		if (isLoggedIn) {
			// 로그인된 사용자 
			try {
	            // 기본 사용자 정보
	            model.addAttribute("userName", user.getUserName());
	            
	            // 내가 올린 최근 문서 목록 (최근 5건)
	            List<ApprovalDocumentVO> myRecentDocuments = approvalService.getRecentDocumentsByAuthor(user.getUserId());
	            model.addAttribute("myRecentDocuments", myRecentDocuments);
	            
	            // 내가 결재해야 할 문서 목록 (최근 5건)
	            List<ApprovalDocumentVO> pendingDocuments = approvalService.getPendingDocumentsForApprover(user.getUserId());
	            model.addAttribute("pendingDocuments", pendingDocuments);
	            
	            // 내가 올린 문서 상태별 개수
	            Map<String, Integer> statusCount = approvalService.getDocumentStatusCount(user.getUserId());
	            model.addAttribute("pendingCount", statusCount.getOrDefault("pendingCount", 0));
	            model.addAttribute("approvedCount", statusCount.getOrDefault("approvedCount", 0));
	            model.addAttribute("rejectedCount", statusCount.getOrDefault("rejectedCount", 0));
	            model.addAttribute("totalCount", statusCount.getOrDefault("totalCount", 0));
	            
	            // 결재 대기 문서 개수
	            model.addAttribute("pendingApprovalCount", pendingDocuments.size());
	            
	        } catch (Exception e) {
	            // 오류 발생 시 기본값 설정
	            model.addAttribute("myRecentDocuments", new ArrayList<ApprovalDocumentVO>());
	            model.addAttribute("pendingDocuments", new ArrayList<ApprovalDocumentVO>());
	            model.addAttribute("pendingCount", 0);
	            model.addAttribute("approvedCount", 0);
	            model.addAttribute("rejectedCount", 0);
	            model.addAttribute("totalCount", 0);
	            model.addAttribute("pendingApprovalCount", 0);
	            
	            System.err.println("Dashboard data loading error: " + e.getMessage());
	        }
		} else {
	        // 로그아웃된 사용자 - 기본값 설정
	        model.addAttribute("userName", "게스트");
	        model.addAttribute("myRecentDocuments", new ArrayList<ApprovalDocumentVO>());
	        model.addAttribute("pendingDocuments", new ArrayList<ApprovalDocumentVO>());
	        model.addAttribute("pendingCount", 0);
	        model.addAttribute("approvedCount", 0);
	        model.addAttribute("rejectedCount", 0);
	        model.addAttribute("totalCount", 0);
	        model.addAttribute("pendingApprovalCount", 0);
		}
        return "/approval/dashboard";
    }
	
	/**
	 * 결재 문서 작성 폼 (결재선 지정 포함)
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
        
        // 같은 부서 사용자 목록 조회
        List<LoginVO> deptUsers = approvalService.getUserListByDept(user.getDeptId());
        model.addAttribute("deptUsers", deptUsers);

        return "/approval/documentForm";
    }
    
    /**
     * 결재 문서 등록 처리 (결재선 포함)
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
        String[] approverIds = request.getParameterValues("approverIds");  //뷰에서 전송되는 데이터 배열로 받기 
        
        // 문서 정보 설정 
        ApprovalDocumentVO document = new ApprovalDocumentVO();
        document.setTitle(title);
        document.setContent(content);
        document.setDocumentType(documentType);
        document.setAuthorId(user.getUserId());
        document.setDeptId(user.getDeptId());
        
        try {
        	// 결재선 정보 배열 처리 (뷰에서 HTML 배열 형태로 전송받아옴) 
        	List<ApprovalLineVO> approvalLines = new ArrayList<>();
            if (approverIds != null && approverIds.length > 0) {
                for (int i = 0; i < approverIds.length; i++) {
                    ApprovalLineVO line = new ApprovalLineVO();
                    line.setApproverId(approverIds[i]);
                    line.setApprovalOrder(i + 1);
                    line.setApprovalType("APPROVAL");
                    approvalLines.add(line);
                }
            } else {
            	System.out.println("결재선 데이터가 없습니다!");
            }
            
            if (approvalLines.isEmpty()) {
                model.addAttribute("error", "결재자를 최소 1명 이상 지정해주세요.");
                List<LoginVO> deptUsers = approvalService.getUserListByDept(user.getDeptId());
                model.addAttribute("deptUsers", deptUsers);
                return "/approval/documentForm";
            }
            
            // 문서와 결재선 함께 등록
            approvalService.insertDocumentWithApprovalLines(document, approvalLines);
            //approvalService.insertDocument(document);

            model.addAttribute("success", "결재 문서가 성공적으로 등록되었습니다.");
            return "redirect:/dashboard.do";
        } catch (Exception e) {
            model.addAttribute("error", "문서 등록 중 오류가 발생했습니다." + e.getMessage());
            
            // 같은 부서 사용자 목록 다시 조회
            List<LoginVO> deptUsers = approvalService.getUserListByDept(user.getDeptId());
            model.addAttribute("deptUsers", deptUsers);
            
            return "/approval/documentForm";
        }
    }
    
    /**
     * 문서 상세 조회 (결재 처리 화면)
     * @param documentId
     * @param session
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/document/detail.do")
    public String documentDetail(@RequestParam("documentId") int documentId, HttpSession session, Model model) throws Exception {
        LoginVO user = (LoginVO) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login.do";
        }
        
        // 문서 정보 조회
        ApprovalDocumentVO document = approvalService.getDocumentById(documentId);
        if (document == null) {
            model.addAttribute("error", "문서를 찾을 수 없습니다.");
            return "redirect:/dashboard.do";
        }
        
        // 결재선 목록 조회
        List<ApprovalLineVO> approvalLines = approvalService.getApprovalLinesByDocumentId(documentId);
        
        // 현재 결재 대기 중인 라인 조회
        ApprovalLineVO currentPendingLine = approvalService.getCurrentPendingLine(documentId);
        
        // 현재 사용자가 결재 권한이 있는지 확인
        boolean canApprove = false;
        if (currentPendingLine != null && user.getUserId().equals(currentPendingLine.getApproverId())) {
            canApprove = true;
        }
        
        model.addAttribute("document", document);
        model.addAttribute("approvalLines", approvalLines);
        model.addAttribute("currentPendingLine", currentPendingLine);
        model.addAttribute("canApprove", canApprove);
        
        return "/approval/documentDetail";
    }

    /**
     * 결재 승인 처리
     * @param documentId
     * @param comment
     * @param session
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/document/approve.do")
    public String approveDocument(@RequestParam("documentId") int documentId, 
                                 @RequestParam(value = "comment", required = false) String comment,
                                 HttpSession session, Model model) throws Exception {
        LoginVO user = (LoginVO) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login.do";
        }
        
        try {
            approvalService.approveDocument(documentId, user.getUserId(), comment);
            model.addAttribute("success", "결재 승인이 완료되었습니다.");
        } catch (Exception e) {
            model.addAttribute("error", "결재 승인 중 오류가 발생했습니다: " + e.getMessage());
        }
        
        return "redirect:/document/detail.do?documentId=" + documentId;
    }

    /**
     * 결재 반려 처리
     * @param documentId
     * @param comment
     * @param session
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/document/reject.do")
    public String rejectDocument(@RequestParam("documentId") int documentId,
                                @RequestParam("comment") String comment,
                                HttpSession session, Model model) throws Exception {
        LoginVO user = (LoginVO) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login.do";
        }
        
        // 반려 시에는 의견이 필수
        if (comment == null || comment.trim().isEmpty()) {
            model.addAttribute("error", "반려 사유를 입력해주세요.");
            return "redirect:/document/detail.do?documentId=" + documentId;
        }
        
        try {
            approvalService.rejectDocument(documentId, user.getUserId(), comment);
            model.addAttribute("success", "결재 반려가 완료되었습니다.");
        } catch (Exception e) {
            model.addAttribute("error", "결재 반려 중 오류가 발생했습니다: " + e.getMessage());
        }
        
        return "redirect:/document/detail.do?documentId=" + documentId;
    }

    /**
     * 나의 결재함
     * @param session
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/approval/inbox.do")
    public String myApprovalInbox(HttpSession session, Model model) throws Exception {
        LoginVO user = (LoginVO) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login.do";
        }
        
        // 나에게 온 결재 대기 문서 목록 조회
        List<ApprovalDocumentVO> inboxDocuments = approvalService.getMyApprovalInbox(user.getUserId());
        
        model.addAttribute("inboxDocuments", inboxDocuments);
        model.addAttribute("userName", user.getUserName());
        
        return "/approval/myApprovalInbox";
    }
    
    /**
     * 내가 작성한 문서 목록
     * @param session
     * @param model
     * @param page
     * @return
     * @throws Exception
     */
    @GetMapping("/document/myDocuments.do")
    public String myDocuments(HttpSession session, Model model, @RequestParam(value = "page", defaultValue = "1") int page) throws Exception {
        LoginVO user = (LoginVO) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login.do";
        }
        
        try {
        	// 페이징 설정
        	int pageSize = 10; // 한 페이지당 문서 수
            int offset = (page - 1) * pageSize;
            
            // 전체 문서 수 조회
            int totalDocuments = approvalService.getDocumentCountByAuthor(user.getUserId());
            
            // 페이징된 문서 목록 조회
            List<ApprovalDocumentVO> myDocuments = approvalService.getDocumentListByAuthorWithPaging(
                user.getUserId(), offset, pageSize);
            
            // 페이징 계산
            int totalPages = (int) Math.ceil((double) totalDocuments / pageSize);
            int startPage = Math.max(1, page - 2);
            int endPage = Math.min(totalPages, page + 2);
            
            // 모델에 데이터 추가
            model.addAttribute("myDocuments", myDocuments);
            model.addAttribute("userName", user.getUserName());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("totalDocuments", totalDocuments);
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);
            
        } catch (Exception e) {
            model.addAttribute("error", "문서 목록 조회 중 오류가 발생했습니다: " + e.getMessage());
            model.addAttribute("myDocuments", new ArrayList<ApprovalDocumentVO>());
        }
//        List<ApprovalDocumentVO> myDocuments = approvalService.getDocumentListByAuthor(user.getUserId());
//        model.addAttribute("myDocuments", myDocuments);
//        model.addAttribute("userName", user.getUserName());

        return "/approval/myDocuments";
    }

}
