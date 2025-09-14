package egovframework.example.approval.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import egovframework.example.approval.service.ApprovalDocumentVO;
import egovframework.example.approval.service.ApprovalLineVO;
import egovframework.example.login.service.LoginVO;

@Mapper
public interface ApprovalMapper {
	
// 문서 관련 쿼리 //////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 결재 문서 등록
	 * @param document
	 * @throws Exception
	 */
	@Insert("INSERT INTO DOCUMENTS (title, content, document_type, author_id, dept_id, status) "
			+ "VALUES (#{title}, #{content}, #{documentType}, #{authorId}, #{deptId}, #{status})")
	@Options(useGeneratedKeys = true, keyProperty = "documentId") //자동생성된 키값 가져오기(방금 생성된 문서의 ID가 필요) 
	void insertDocument(ApprovalDocumentVO document) throws Exception;
    
	/**
	 * 결재 문서 목록 조회 (기안자별)
	 * @param authorId
	 * @return
	 * @throws Exception
	 */
    @Select("SELECT document_id AS documentId, title, content, document_type AS documentType, "
    		+ "author_id AS authorId, dept_id AS deptId, status, created_at AS createdAt, updated_at AS updatedAt "
            + "FROM DOCUMENTS "
            + "WHERE author_id = #{authorId} "
            + "ORDER BY created_at DESC")
    List<ApprovalDocumentVO> selectDocumentListByAuthor(String authorId) throws Exception;
    
    /**
     * 결재 문서 상세 조회
     * @param documentId
     * @return
     * @throws Exception
     */
    @Select("SELECT document_id AS documentId, title, content, document_type AS documentType, "
    		+ "author_id AS authorId, dept_id AS deptId, status, "
    		+ "created_at AS createdAt, updated_at AS updatedAt "
    		+ "FROM DOCUMENTS "
    		+ "WHERE document_id = #{documentId}")
    ApprovalDocumentVO selectDocumentById(int documentId) throws Exception;
    
// 결재선 관련 쿼리 //////////////////////////////////////////////////////////////////////////////////////
    /**
     * 결재선 등록
     * @param approvalLine
     * @throws Exception
     */
    @Insert("INSERT INTO APPROVAL_LINES (document_id, approver_id, approval_order, approval_type, status) "
    		+ "VALUES (#{documentId}, #{approverId}, #{approvalOrder}, #{approvalType}, #{status})")
    void insertApprovalLine(ApprovalLineVO approvalLine) throws Exception;
    
    /**
     * 결재선 목록 조회 (문서별) 
     * @param documentId
     * @return
     * @throws Exception
     */
    @Select("SELECT al.line_id AS lineId, al.document_id AS documentId, al.approver_id AS approverId, "
    		+ "u.user_name AS approverName, al.approval_order AS approvalOrder, al.approval_type AS approvalType, "
            + "al.status, al.approved_at AS approvedAt, al.comment, al.created_at AS createdAt "
            + "FROM APPROVAL_LINES al "
            + "JOIN USERS u "
            + "ON al.approver_id = u.user_id "
            + "WHERE al.document_id = #{documentId} "
            + "ORDER BY al.approval_order")
    List<ApprovalLineVO> selectApprovalLinesByDocumentId(int documentId) throws Exception;
    
    /**
     * 사용자 목록 조회 (부서별)
     * @param deptId
     * @return
     * @throws Exception
     */
    @Select("SELECT user_id AS userId, user_name AS userName, dept_id AS deptId, role_id AS roleId "
    		+ "FROM USERS "
    		+ "WHERE dept_id = #{deptId} "
    		+ "ORDER BY user_name")
    List<LoginVO> selectUserListByDept(String deptId) throws Exception;
    
// 결재 처리 관련 쿼리 //////////////////////////////////////////////////////////////////////////////////////
    /**
     * 현재 결재 대기 중인 라인 조회 (문서별)
     * @param documentId
     * @return
     * @throws Exception
     */
    @Select("SELECT al.line_id AS lineId, al.document_id AS documentId, al.approver_id AS approverId, "
    		+ "u.user_name AS approverName, al.approval_order AS approvalOrder, al.approval_type AS approvalType, "
            + "al.status, al.approved_at AS approvedAt, al.comment, al.created_at AS createdAt "
            + "FROM APPROVAL_LINES al "
            + "JOIN USERS u "
            + "ON al.approver_id = u.user_id "
            + "WHERE al.document_id = #{documentId} AND al.status = 'PENDING' "
            + "ORDER BY al.approval_order LIMIT 1")
    ApprovalLineVO selectCurrentPendingLine(int documentId) throws Exception;

    /**
     * 결재선 상태 업데이트 (승인)
     * @param lineId
     * @param comment
     * @return
     * @throws Exception
     */
    @Update("UPDATE APPROVAL_LINES SET status = 'APPROVED', approved_at = NOW(), comment = #{comment} "
    		+ "WHERE line_id = #{lineId} AND status = 'PENDING'")
    int updateLineApproved(@Param("lineId") int lineId, @Param("comment") String comment) throws Exception;

    /**
     * 결재선 상태 업데이트 (반려)
     * @param lineId
     * @param comment
     * @return
     * @throws Exception
     */
    @Update("UPDATE APPROVAL_LINES SET status = 'REJECTED', approved_at = NOW(), comment = #{comment}"
    		+ "WHERE line_id = #{lineId} AND status = 'PENDING'")
    int updateLineRejected(@Param("lineId") int lineId, @Param("comment") String comment) throws Exception;

    /**
     * 문서 상태 업데이트
     * @param documentId
     * @param status
     * @return
     * @throws Exception
     */
    @Update("UPDATE DOCUMENTS SET status = #{status} "
    		+ "WHERE document_id = #{documentId}")
    int updateDocumentStatus(@Param("documentId") int documentId, @Param("status") String status) throws Exception;

    /**
     * 나의 결재함 조회 (결재 대기 문서 목록)
     * @param approverId
     * @return
     * @throws Exception
     */
    @Select("SELECT d.document_id AS documentId, d.title, d.content, d.document_type AS documentType, " +
            "d.author_id AS authorId, d.dept_id AS deptId, d.status, d.created_at AS createdAt, d.updated_at AS updatedAt "
            + "FROM DOCUMENTS d "
            + "JOIN APPROVAL_LINES al "
            + "ON d.document_id = al.document_id "
            + "WHERE al.approver_id = #{approverId} AND al.status = 'PENDING' AND d.status = 'PENDING' "
            + "ORDER BY d.created_at DESC")
    List<ApprovalDocumentVO> selectMyApprovalInbox(String approverId) throws Exception;
    
// 대시보드용 쿼리 //////////////////////////////////////////////////////////////////////////////////////
    /**
     * 내가 올린 최근 결재 문서 조회 (최근 5건)
     * @param authorId
     * @return
     * @throws Exception
     */
    @Select("SELECT document_id AS documentId, title, document_type AS documentType, "
    		+ "author_id AS authorId, status, created_at AS createdAt "
            + "FROM DOCUMENTS "
            + "WHERE author_id = #{authorId} "
            + "ORDER BY created_at DESC LIMIT 5")
    List<ApprovalDocumentVO> selectRecentDocumentsByAuthor(String authorId) throws Exception;

    /**
     * 내가 결재해야 할 문서 조회 (최근 5건)
     * @param approverId
     * @return
     * @throws Exception
     */
    @Select("SELECT d.document_id AS documentId, d.title, d.document_type AS documentType, "
    		+ "d.author_id AS authorId, d.status, d.created_at AS createdAt "
    		+ "FROM DOCUMENTS d "
    		+ "JOIN APPROVAL_LINES al "
    		+ "ON d.document_id = al.document_id "
    		+ "WHERE al.approver_id = #{approverId} AND al.status = 'PENDING' AND d.status = 'PENDING' "
    		+ "ORDER BY d.created_at DESC LIMIT 5")
    List<ApprovalDocumentVO> selectPendingDocumentsForApprover(String approverId) throws Exception;

    /**
     * 내가 올린 문서 상태별 개수 조회
     * @param authorId
     * @return
     * @throws Exception
     */
    @Select("SELECT "
    		+ "COUNT(CASE WHEN status = 'PENDING' THEN 1 END) AS pendingCount, "
    		+ "COUNT(CASE WHEN status = 'APPROVED' THEN 1 END) AS approvedCount, "
    		+ "COUNT(CASE WHEN status = 'REJECTED' THEN 1 END) AS rejectedCount, "
    		+ "COUNT(*) AS totalCount "
            + "FROM DOCUMENTS WHERE author_id = #{authorId}")
    Map<String, Integer> selectDocumentStatusCount(String authorId) throws Exception;  
    
}
