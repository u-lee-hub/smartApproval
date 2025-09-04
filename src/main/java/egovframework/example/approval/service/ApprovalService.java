package egovframework.example.approval.service;

import java.util.List;

/**
 * @Class Name : ApprovalService.java
 */
public interface ApprovalService {
	
	// 결재 문서 등록
    void insertDocument(ApprovalDocumentVO document) throws Exception;
    
    // 결재 문서 목록 조회 (기안자별)
    List<ApprovalDocumentVO> getDocumentListByAuthor(String authorId) throws Exception;
    
    // 결재 문서 상세 조회
    ApprovalDocumentVO getDocumentById(int documentId) throws Exception;
}
