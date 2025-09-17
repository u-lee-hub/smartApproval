package egovframework.example.approvalrule.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import egovframework.example.approvalrule.service.ApprovalRuleService;
import egovframework.example.approvalrule.service.ApprovalRuleVO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApprovalRuleServiceImpl extends EgovAbstractServiceImpl implements ApprovalRuleService {
	private final ApprovalRuleMapper approvalRuleMapper;
    
 // 자동 결재선 계산 //////////////////////////////////////////////////////////////////////////////////////
    /**
     * 금액과 문서타입에 따른 자동 결재선 계산 
     */
    @Override
    public List<String> getAutoApprovalLine(int amount, String documentType, String deptId) throws Exception {
    	// 1. 금액으로 결재자 개수 조회
        Integer approverCount = approvalRuleMapper.selectApproverCountByAmount(amount);
        
        // 2. 규칙이 없으면 기본값 1 (팀장만)
        if (approverCount == null) {
            approverCount = 1;
        }
        
        // 3. 간단한 if문으로 결재자 리스트 구성
        List<String> approvers = new ArrayList<String>();
        
        // 항상 팀장은 포함
        String manager = approvalRuleMapper.selectManagerByDeptAndRole(deptId, "MANAGER");  // ⭐ approvalMapper → approvalRuleMapper
        if (manager != null) {
            approvers.add(manager);
        }
        
        // 2명 이상이면 부서장 추가
        if (approverCount >= 2) {
            String director = approvalRuleMapper.selectManagerByDeptAndRole(deptId, "DIRECTOR");  // ⭐ approvalMapper → approvalRuleMapper
            if (director != null) {
                approvers.add(director);
            }
        }
        
        // 3명이면 대표 추가  
        if (approverCount >= 3) {
            String ceo = approvalRuleMapper.selectUserByRole("CEO");  // ⭐ approvalMapper → approvalRuleMapper
            if (ceo != null) {
                approvers.add(ceo);
            }
        }
        
        // 4. 최소 1명은 있어야 함
        if (approvers.isEmpty() && manager != null) {
            approvers.add(manager);
        }
        
        return approvers;
    }
    
    /**
     * 규칙 미리보기
     */
    @Override
    public ApprovalRuleVO previewRule(int amount, String documentType) throws Exception {
        ApprovalRuleVO rule = approvalRuleMapper.selectRuleByAmountAndType(amount, documentType);
        
        if (rule == null) {
            // 기본 규칙 반환
            rule = new ApprovalRuleVO();
            rule.setRuleDescription("기본 규칙: 팀장만");
            rule.setApproverCount(1);
        }
        
        return rule;
    }
    
// 규칙 관리 (관리자용) //////////////////////////////////////////////////////////////////////////////////////
    
    /**
     * 모든 활성 규칙 조회
     */
    @Override
    public List<ApprovalRuleVO> getAllRules() throws Exception {
        return approvalRuleMapper.selectAllActiveRules();
    }
    
    /**
     * 특정 규칙 조회
     */
    @Override
    public ApprovalRuleVO getRuleById(int ruleId) throws Exception {
        return approvalRuleMapper.selectRuleById(ruleId);
    }
    
    /**
     * 규칙 수정
     */
    @Override
    public boolean updateRule(ApprovalRuleVO rule) throws Exception {
        // 유효성 검증
        String validationMessage = validateRule(rule);
        if (validationMessage != null) {
            throw new IllegalArgumentException(validationMessage);
        }
        
        int updatedRows = approvalRuleMapper.updateRule(rule);
        return updatedRows > 0;
    }
    
    /**
     * 새 규칙 추가
     */
    @Override
    public boolean addRule(ApprovalRuleVO rule) throws Exception {
        // 유효성 검증
        String validationMessage = validateRule(rule);
        if (validationMessage != null) {
            throw new IllegalArgumentException(validationMessage);
        }
        
        int insertedRows = approvalRuleMapper.insertRule(rule);
        return insertedRows > 0;
    }
    
    /**
     * 규칙 삭제 (비활성화)
     */
    @Override
    public boolean deleteRule(int ruleId) throws Exception {
        int updatedRows = approvalRuleMapper.deactivateRule(ruleId);
        return updatedRows > 0;
    }
    
    /**
     * 규칙 유효성 검증 (대폭 간소화)
     */
    @Override
    public String validateRule(ApprovalRuleVO rule) throws Exception {
        // 1. 필수 값 체크
        if (rule.getDocumentType() == null || rule.getDocumentType().trim().isEmpty()) {
            return "문서 타입은 필수입니다.";
        }
        
        if (rule.getMinAmount() < 0) {
            return "최소 금액은 0 이상이어야 합니다.";
        }
        
        if (rule.getMaxAmount() < rule.getMinAmount()) {
            return "최대 금액은 최소 금액보다 크거나 같아야 합니다.";
        }
        
        if (rule.getApproverCount() < 1 || rule.getApproverCount() > 3) {
            return "결재자 개수는 1~3 사이여야 합니다.";
        }
        
        // 2. 중복 규칙 체크
        int overlappingCount = approvalRuleMapper.countOverlappingRules(
            rule.getDocumentType(), 
            rule.getMinAmount(), 
            rule.getMaxAmount(), 
            rule.getRuleId()
        );
        
        if (overlappingCount > 0) {
            return "동일한 문서타입과 겹치는 금액 범위의 규칙이 이미 존재합니다.";
        }
        
        return null; // 통과
    }
}