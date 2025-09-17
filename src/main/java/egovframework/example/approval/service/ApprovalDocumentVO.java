package egovframework.example.approval.service;

/**
 * @Class Name : ApprovalDocumentVO.java
 * @Description : 결재 문서 정보를 담는 VO 클래스
 */
public class ApprovalDocumentVO {
	
	/** 문서 ID */
    private int documentId;
    
    /** 문서 제목 */
    private String title;
    
    /** 문서 내용 */
    private String content;
    
    /** 문서 타입 (출장비신청서, 휴가신청서, 구매요청서 등) */
    private String documentType;
    
    /** 기안자 ID */
    private String authorId;
    
    /** 기안자 부서 ID */
    private String deptId;
    
    /** 결재 상태 (DRAFT: 임시저장, PENDING: 결재대기, APPROVED: 승인, REJECTED: 반려) */
    private String status;
    
    /** 작성일 */
    private String createdAt;
    
    /** 수정일 */
    private String updatedAt;
    
    /** 지출 금액 */
    private int expenseAmount;

	public int getDocumentId() {
		return documentId;
	}

	public void setDocumentId(int documentId) {
		this.documentId = documentId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public int getExpenseAmount() {
		return expenseAmount;
	}

	public void setExpenseAmount(int expenseAmount) {
		this.expenseAmount = expenseAmount;
	}

}
