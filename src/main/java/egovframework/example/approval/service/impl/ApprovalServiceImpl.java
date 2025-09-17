package egovframework.example.approval.service.impl;

import java.util.List;
import java.util.Map;

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
    
// 결재 처리 관련 메소드 //////////////////////////////////////////////////////////////////////////////////////

    /**
     * 현재 결재 대기 중인 라인 조회 (문서별)
     */
    @Override
    public ApprovalLineVO getCurrentPendingLine(int documentId) throws Exception {
        return approvalMapper.selectCurrentPendingLine(documentId);
    }

    /**
     * 결재 승인 처리
     */
    @Override
    @Transactional
    public void approveDocument(int documentId, String approverId, String comment) throws Exception {
        // 1. 현재 결재 대기 중인 라인 조회
        ApprovalLineVO currentLine = approvalMapper.selectCurrentPendingLine(documentId);
        
        if (currentLine == null) {
            throw new IllegalStateException("결재 대기 중인 라인이 없습니다.");
        }
        
        // 2. 권한 검증 (현재 결재자와 요청자가 일치하는지 확인)
        if (!approverId.equals(currentLine.getApproverId())) {
            throw new SecurityException("결재 권한이 없습니다.");
        }
        
        // 3. 결재선 승인 처리
        int updated = approvalMapper.updateLineApproved(currentLine.getLineId(), comment);
        if (updated == 0) {
            throw new IllegalStateException("결재선 업데이트에 실패했습니다.");
        }
        
        // 4. 다음 결재 대기 라인이 있는지 확인
        ApprovalLineVO nextLine = approvalMapper.selectCurrentPendingLine(documentId);
        
        // 5. 더 이상 결재할 라인이 없으면 문서 상태를 APPROVED로 변경
        if (nextLine == null) {
            approvalMapper.updateDocumentStatus(documentId, "APPROVED");
        }
    }

    /**
     * 결재 반려 처리
     */
    @Override
    @Transactional
    public void rejectDocument(int documentId, String approverId, String comment) throws Exception {
        // 1. 현재 결재 대기 중인 라인 조회
        ApprovalLineVO currentLine = approvalMapper.selectCurrentPendingLine(documentId);
        
        if (currentLine == null) {
            throw new IllegalStateException("결재 대기 중인 라인이 없습니다.");
        }
        
        // 2. 권한 검증
        if (!approverId.equals(currentLine.getApproverId())) {
            throw new SecurityException("결재 권한이 없습니다.");
        }
        
        // 3. 결재선 반려 처리
        int updated = approvalMapper.updateLineRejected(currentLine.getLineId(), comment);
        if (updated == 0) {
            throw new IllegalStateException("결재선 업데이트에 실패했습니다.");
        }
        
        // 4. 문서 상태를 REJECTED로 변경 (반려 시에는 즉시 전체 문서 반려)
        approvalMapper.updateDocumentStatus(documentId, "REJECTED");
    }

    /**
     * 나의 결재함 조회
     */
    @Override
    public List<ApprovalDocumentVO> getMyApprovalInbox(String approverId) throws Exception {
        return approvalMapper.selectMyApprovalInbox(approverId);
    }
    
// 대시보드 관련 메소드 //////////////////////////////////////////////////////////////////////////////////////
    /**
     * 내가 올린 최근 결재 문서 조회 (최근 5건)
     */
    @Override
    public List<ApprovalDocumentVO> getRecentDocumentsByAuthor(String authorId) throws Exception {
        return approvalMapper.selectRecentDocumentsByAuthor(authorId);
    }

    /**
     * 내가 결재해야 할 문서 조회 (최근 5건)
     */
    @Override
    public List<ApprovalDocumentVO> getPendingDocumentsForApprover(String approverId) throws Exception {
        return approvalMapper.selectPendingDocumentsForApprover(approverId);
    }

    /**
     * 내가 올린 문서 상태별 개수 조회
     */
    @Override
    public Map<String, Integer> getDocumentStatusCount(String authorId) throws Exception {
        return approvalMapper.selectDocumentStatusCount(authorId);
    }
    
// 페이징 처리용 메소드 //////////////////////////////////////////////////////////////////////////////////////
    /**
     * 내가 작성한 문서 총 개수
     */
    @Override
    public int getDocumentCountByAuthor(String authorId) throws Exception {
        return approvalMapper.selectDocumentCountByAuthor(authorId);
    }

    /**
     * 내가 작성한 문서 목록 (페이징)
     */
    @Override
    public List<ApprovalDocumentVO> getDocumentListByAuthorWithPaging(String authorId, int offset, int pageSize) throws Exception {
        return approvalMapper.selectDocumentListByAuthorWithPaging(authorId, offset, pageSize);
    }
  
}
