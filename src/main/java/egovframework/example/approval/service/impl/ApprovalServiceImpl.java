package egovframework.example.approval.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import egovframework.example.approval.service.ApprovalDocumentVO;
import egovframework.example.approval.service.ApprovalLineVO;
import egovframework.example.approval.service.ApprovalService;
import egovframework.example.login.service.LoginVO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApprovalServiceImpl extends EgovAbstractServiceImpl implements ApprovalService {

	private final ApprovalMapper approvalMapper;
    
	// 문서 관련 메소드 //////////////////////////////////////////////////////////////////////////////////////
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
    
    // 결재선 관련 메소드 //////////////////////////////////////////////////////////////////////////////////////
    /**
     * 결재선 등록
     */
    @Override
    @Transactional
    public void insertApprovalLines(int documentId, List<ApprovalLineVO> approvalLines) throws Exception {
        for (ApprovalLineVO approvalLine : approvalLines) {
            approvalLine.setDocumentId(documentId);
            approvalLine.setStatus("PENDING");
            approvalMapper.insertApprovalLine(approvalLine); // 여러 번 INSERT
        }
    }
    
    /**
     * 결재선 목록 조회 (문서별)
     */
    @Override
    public List<ApprovalLineVO> getApprovalLinesByDocumentId(int documentId) throws Exception {
        return approvalMapper.selectApprovalLinesByDocumentId(documentId);
    }
    
    /**
     * 사용자 목록 조회 (부서별)
     */
    @Override
    public List<LoginVO> getUserListByDept(String deptId) throws Exception {
        return approvalMapper.selectUserListByDept(deptId);
    }
    
    /**
     * 결재 문서(결재선 포함) 등록 (트랜잭션 처리)
     */
    @Override
    @Transactional
    public void insertDocumentWithApprovalLines(ApprovalDocumentVO document, List<ApprovalLineVO> approvalLines) throws Exception {
        // 1. 문서 등록 (AUTO_INCREMENT로 documentId 자동 생성)
        document.setStatus("PENDING");
        approvalMapper.insertDocument(document);
        
        // 2. 생성된 documentId를 사용하여 결재선 등록
        insertApprovalLines(document.getDocumentId(), approvalLines);
    }
    
}
