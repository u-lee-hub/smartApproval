package egovframework.example.approval.service;

import java.util.List;

import egovframework.example.login.service.LoginVO;

/**
 * @Class Name : ApprovalService.java
 */
public interface ApprovalService {
	
// 문서 관련 메소드 
	// 결재 문서 등록
    void insertDocument(ApprovalDocumentVO document) throws Exception;
    
    // 결재 문서 목록 조회 (기안자별)
    List<ApprovalDocumentVO> getDocumentListByAuthor(String authorId) throws Exception;
    
    // 결재 문서 상세 조회
    ApprovalDocumentVO getDocumentById(int documentId) throws Exception;
    
// 결재선 관련 메소드 
    // 결재선 등록
    void insertApprovalLines(int documentId, List<ApprovalLineVO> approvalLines) throws Exception;
    
    // 결재선 목록 조회 (문서별)
    List<ApprovalLineVO> getApprovalLinesByDocumentId(int documentId) throws Exception;
    
    // 사용자 목록 조회 (부서별)
    List<LoginVO> getUserListByDept(String deptId) throws Exception;
    
    // 결재 문서(결재선 포함) 등록 
    void insertDocumentWithApprovalLines(ApprovalDocumentVO document, List<ApprovalLineVO> approvalLines) throws Exception;

//    // 결재선 정보 파싱 헬퍼
//    List<ApprovalLineVO> parseApprovalLinesFromJson(String json) throws Exception;


}
