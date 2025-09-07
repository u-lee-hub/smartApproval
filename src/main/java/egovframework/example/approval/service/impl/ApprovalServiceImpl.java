package egovframework.example.approval.service.impl;

import java.util.List;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import egovframework.example.approval.service.ApprovalDocumentVO;
import egovframework.example.approval.service.ApprovalService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApprovalServiceImpl extends EgovAbstractServiceImpl implements ApprovalService {

	private final ApprovalMapper approvalMapper;
    
	/**
	 * 결재 문서 등록
	 */
    @Override
    public void insertDocument(ApprovalDocumentVO document) throws Exception {
        // 기본 상태를 PENDING(결재대기)로 설정
        document.setStatus("PENDING");
        approvalMapper.insertDocument(document);
    }
    
    /**
     * 결재 문서 목록 조회 (기안자별)
     */
    @Override
    public List<ApprovalDocumentVO> getDocumentListByAuthor(String authorId) throws Exception {
        return approvalMapper.selectDocumentListByAuthor(authorId);
    }
    
    /**
     * 결재 문서 상세 조회
     */
    @Override
    public ApprovalDocumentVO getDocumentById(int documentId) throws Exception {
        return approvalMapper.selectDocumentById(documentId);
    }
}
