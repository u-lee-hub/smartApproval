package egovframework.example.approval.service;

/**
 * @Class Name : ApprovalLineVO.java
 * @Description : 결재선 정보를 담는 VO 클래스
 */
public class ApprovalLineVO {
	
	/** 결재선 ID */
    private int lineId;
    
    /** 문서 ID */
    private int documentId;
    
    /** 결재자 ID */
    private String approverId;
    
    /** 결재 순서 */
    private int approvalOrder;
    
    /** 결재 유형 (APPROVAL: 결재, REVIEW: 검토, REFERENCE: 참조) */
    private String approvalType;
    
    /** 결재 상태 (PENDING: 대기, APPROVED: 승인, REJECTED: 반려) */
    private String status;
    
    /** 결재일시 */
    private String approvedAt;
    
    /** 결재 의견 */
    private String comment;
    
    /** 생성일 */
    private String createdAt;
    
    // 조인용 추가 필드 (결재자 정보)
    /** 결재자 이름 (조인으로 가져오는 필드) */
    private String approverName;

    
	public int getLineId() {
		return lineId;
	}

	public void setLineId(int lineId) {
		this.lineId = lineId;
	}

	public int getDocumentId() {
		return documentId;
	}

	public void setDocumentId(int documentId) {
		this.documentId = documentId;
	}

	public String getApproverId() {
		return approverId;
	}

	public void setApproverId(String approverId) {
		this.approverId = approverId;
	}

	public int getApprovalOrder() {
		return approvalOrder;
	}

	public void setApprovalOrder(int approvalOrder) {
		this.approvalOrder = approvalOrder;
	}

	public String getApprovalType() {
		return approvalType;
	}

	public void setApprovalType(String approvalType) {
		this.approvalType = approvalType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getApprovedAt() {
		return approvedAt;
	}

	public void setApprovedAt(String approvedAt) {
		this.approvedAt = approvedAt;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getApproverName() {
		return approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}
    
    
}
