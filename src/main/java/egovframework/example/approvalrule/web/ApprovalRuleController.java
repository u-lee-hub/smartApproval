package egovframework.example.approvalrule.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import egovframework.example.approvalrule.service.ApprovalRuleService;
import egovframework.example.approvalrule.service.ApprovalRuleVO;
import egovframework.example.login.service.LoginVO;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ApprovalRuleController {
	
	private final ApprovalRuleService approvalRuleService;
	
	/**
	 * 결재 규칙 관리 화면
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 */
    @GetMapping("/admin/rules.do")
    public String ruleManagement(HttpSession session, Model model) throws Exception {
        LoginVO user = (LoginVO) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login.do";
        }

        // 관리자 권한 체크 (선택사항)
        // if (!"ADMIN".equals(user.getRoleId())) {
        //     model.addAttribute("error", "관리자 권한이 필요합니다.");
        //     return "redirect:/dashboard.do";
        // }

        try {
            // 모든 규칙 조회
            List<ApprovalRuleVO> rules = approvalRuleService.getAllRules();
            model.addAttribute("rules", rules);
            model.addAttribute("userName", user.getUserName());
            
        } catch (Exception e) {
            model.addAttribute("error", "규칙 목록 조회 중 오류가 발생했습니다: " + e.getMessage());
        }

        return "/approvalrule/ruleMgmt";
    }

    /**
     * 규칙 수정
     * @param ruleId
     * @param approverCount
     * @param ruleDescription
     * @param session
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/admin/rules/update.do")
    public String updateRule(@RequestParam("ruleId") int ruleId,
                           @RequestParam("approverCount") int approverCount,
                           @RequestParam("ruleDescription") String ruleDescription,
                           HttpSession session, Model model) throws Exception {
        
        LoginVO user = (LoginVO) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login.do";
        }

        try {
            ApprovalRuleVO rule = new ApprovalRuleVO();
            rule.setRuleId(ruleId);
            rule.setApproverCount(approverCount);
            rule.setRuleDescription(ruleDescription);

            boolean success = approvalRuleService.updateRule(rule);
            
            if (success) {
                model.addAttribute("success", "규칙이 성공적으로 수정되었습니다.");
            } else {
                model.addAttribute("error", "규칙 수정에 실패했습니다.");
            }

        } catch (Exception e) {
            model.addAttribute("error", "규칙 수정 중 오류가 발생했습니다: " + e.getMessage());
        }

        return "redirect:/admin/rules.do";
    }
	
}
