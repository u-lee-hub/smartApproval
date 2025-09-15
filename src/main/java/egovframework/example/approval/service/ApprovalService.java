package egovframework.example.approval.service;

import java.util.List;
import java.util.Map;

import egovframework.example.login.service.LoginVO;

/**
 * @Class Name : ApprovalService.java
 */
public interface ApprovalService {
	
// 문서 관련 메소드 //////////////////////////////////////////////////////////////////////////////////////
	// 결재 문서 등록
    void insertDocument(ApprovalDocumentVO document) throws Exception;
    
    // 결재 문서 목록 조회 (기안자별)
    List<ApprovalDocumentVO> getDocumentListByAuthor(String authorId) throws Exception;
    
    // 결재 문서 상세 조회
    ApprovalDocumentVO getDocumentById(int documentId) throws Exception;
    
// 결재선 관련 메소드 //////////////////////////////////////////////////////////////////////////////////////
    // 결재선 등록
    void insertApprovalLines(int documentId, List<ApprovalLineVO> approvalLines) throws Exception;
    
    // 결재선 목록 조회 (문서별)
    List<ApprovalLineVO> getApprovalLinesByDocumentId(int documentId) throws Exception;
    
    // 사용자 목록 조회 (부서별)
    List<LoginVO> getUserListByDept(String deptId) throws Exception;
    
    // 결재 문서(결재선 포함) 등록 
    void insertDocumentWithApprovalLines(ApprovalDocumentVO document, List<ApprovalLineVO> approvalLines) throws Exception;

// 결재 처리 관련 메소드 //////////////////////////////////////////////////////////////////////////////////////
    // 현재 결재 대기 중인 라인 조회 (문서별)
    ApprovalLineVO getCurrentPendingLine(int documentId) throws Exception;

    // 결재 승인 처리
    void approveDocument(int documentId, String approverId, String comment) throws Exception;

    // 결재 반려 처리
    void rejectDocument(int documentId, String approverId, String comment) throws Exception;

    // 나의 결재함 조회 
    List<ApprovalDocumentVO> getMyApprovalInbox(String approverId) throws Exception;
    
// 대시보드 관련 메소드 //////////////////////////////////////////////////////////////////////////////////////
    // 내가 올린 최근 결재 문서 조회 (최근 5건)
    List<ApprovalDocumentVO> getRecentDocumentsByAuthor(String authorId) throws Exception;

    // 내가 결재해야 할 문서 조회 (최근 5건)
    List<ApprovalDocumentVO> getPendingDocumentsForApprover(String approverId) throws Exception;

    // 내가 올린 문서 상태별 개수 조회
    Map<String, Integer> getDocumentStatusCount(String authorId) throws Exception;
    
 // 페이징 처리용 메소드 //////////////////////////////////////////////////////////////////////////////////////
    // 내가 작성한 문서 총 개수
    int getDocumentCountByAuthor(String authorId) throws Exception;
    
    // 내가 작성한 문서 목록 (페이징)
    List<ApprovalDocumentVO> getDocumentListByAuthorWithPaging(String authorId, int offset, int pageSize) throws Exception;
    
}
