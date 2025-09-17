package egovframework.example.approvalrule.service;

import java.util.List;

/**
 * @Class Name : ApprovalRuleService.java
 * @Description : 자동 결재선 규칙 관리 서비스 인터페이스
 */
public interface ApprovalRuleService {
	
// 자동 결재선 계산 //////////////////////////////////////////////////////////////////////////////////////
	/**
     * 금액과 문서타입에 따른 자동 결재선 계산
     * @param amount 지출 금액
     * @param documentType 문서 타입
     * @param deptId 부서 ID
     * @return 결재자 ID 리스트
     * @throws Exception
     */
    List<String> getAutoApprovalLine(int amount, String documentType, String deptId) throws Exception;
    
    /**
     * 규칙 미리보기
     * @param amount 지출 금액  
     * @param documentType 문서 타입
     * @return 적용될 규칙 정보
     * @throws Exception
     */
    ApprovalRuleVO previewRule(int amount, String documentType) throws Exception;
    
// 규칙 관리 (관리자 전용) //////////////////////////////////////////////////////////////////////////////////////
    
    /**
     * 모든 활성 규칙 조회
     * @return
     * @throws Exception
     */
    List<ApprovalRuleVO> getAllRules() throws Exception;
    
    /**
     * 특정 규칙 조회
     * @param ruleId
     * @return
     * @throws Exception
     */
    ApprovalRuleVO getRuleById(int ruleId) throws Exception;
    
    /**
     * 규칙 수정
     * @param rule
     * @return
     * @throws Exception
     */
    boolean updateRule(ApprovalRuleVO rule) throws Exception;
    
    /**
     * 새 규칙 추가
     * @param rule
     * @return
     * @throws Exception
     */
    boolean addRule(ApprovalRuleVO rule) throws Exception;
    
    /**
     * 규칙 삭제
     * @param ruleId
     * @return
     * @throws Exception
     */
    boolean deleteRule(int ruleId) throws Exception;
    
    /**
     * 규칙 유효성 검증
     * @param rule
     * @return 검증 결과 메시지 (null이면 통과)
     * @throws Exception
     */
    String validateRule(ApprovalRuleVO rule) throws Exception;
}
