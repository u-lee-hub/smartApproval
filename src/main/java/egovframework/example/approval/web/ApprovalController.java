package egovframework.example.approval.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApprovalController {
	
	@GetMapping(value = "/dashboard.do")
	public String dashboard(Model model) throws Exception {
		return "/approval/dashboard";
	}
}
