package egovframework.example.approvalrule.service;

/**
 * @Class Name : ApprovalRuleVO.java
 * @Description : 자동 결재선 규칙 정보를 담는 VO 클래스
 */
public class ApprovalRuleVO {
	/** 규칙 ID */
    private int ruleId;
    
    /** 문서 타입 (ALL=전체적용, 특정타입=해당타입만) */
    private String documentType;
    
    /** 최소 금액 */
    private int minAmount;
    
    /** 최대 금액 */
    private int maxAmount;
    
    /** 결재자 개수 (1:팀장, 2:팀장+부서장, 3:팀장+부서장+대표) */
    private int approverCount;
    
    /** 규칙 설명 */
    private String ruleDescription;
    
    /** 활성화 여부 */
    private boolean isActive;
    
    /** 생성일 */
    private String createdAt;
    
    /** 수정일 */
    private String updatedAt;

	public int getRuleId() {
		return ruleId;
	}

	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public int getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(int minAmount) {
		this.minAmount = minAmount;
	}

	public int getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(int maxAmount) {
		this.maxAmount = maxAmount;
	}

	public int getApproverCount() {
        return approverCount;
    }
    
    public void setApproverCount(int approverCount) {
        this.approverCount = approverCount;
    }

	public String getRuleDescription() {
		return ruleDescription;
	}

	public void setRuleDescription(String ruleDescription) {
		this.ruleDescription = ruleDescription;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
}
