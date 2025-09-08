package egovframework.example.approval.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import egovframework.example.approval.service.ApprovalDocumentVO;
import egovframework.example.approval.service.ApprovalLineVO;
import egovframework.example.login.service.LoginVO;

@Mapper
public interface ApprovalMapper {
	
	// 문서 관련 쿼리 
	/**
	 * 결재 문서 등록
	 * @param document
	 * @throws Exception
	 */
	@Insert("INSERT INTO DOCUMENTS (title, content, document_type, author_id, dept_id, status) " +
            "VALUES (#{title}, #{content}, #{documentType}, #{authorId}, #{deptId}, #{status})")
	@Options(useGeneratedKeys = true, keyProperty = "documentId") //자동생성된 키값 가져오기(방금 생성된 문서의 ID가 필요) 
	void insertDocument(ApprovalDocumentVO document) throws Exception;
    
	/**
	 * 결재 문서 목록 조회 (기안자별)
	 * @param authorId
	 * @return
	 * @throws Exception
	 */
    @Select("SELECT document_id AS documentId, title, content, document_type AS documentType, " +
            "author_id AS authorId, dept_id AS deptId, status, " +
            "created_at AS createdAt, updated_at AS updatedAt " +
            "FROM DOCUMENTS WHERE author_id = #{authorId} ORDER BY created_at DESC")
    List<ApprovalDocumentVO> selectDocumentListByAuthor(String authorId) throws Exception;
    
    /**
     * 결재 문서 상세 조회
     * @param documentId
     * @return
     * @throws Exception
     */
    @Select("SELECT document_id AS documentId, title, content, document_type AS documentType, " +
            "author_id AS authorId, dept_id AS deptId, status, " +
            "created_at AS createdAt, updated_at AS updatedAt " +
            "FROM DOCUMENTS WHERE document_id = #{documentId}")
    ApprovalDocumentVO selectDocumentById(int documentId) throws Exception;
    
    // 결재선 관련 쿼리 
    /**
     * 결재선 등록
     * @param approvalLine
     * @throws Exception
     */
    @Insert("INSERT INTO APPROVAL_LINES (document_id, approver_id, approval_order, approval_type, status) " +
            "VALUES (#{documentId}, #{approverId}, #{approvalOrder}, #{approvalType}, #{status})")
    void insertApprovalLine(ApprovalLineVO approvalLine) throws Exception;
    
    /**
     * 결재선 목록 조회 (문서별) 
     * @param documentId
     * @return
     * @throws Exception
     */
    @Select("SELECT al.line_id AS lineId, al.document_id AS documentId, al.approver_id AS approverId, " +
            "u.user_name AS approverName, al.approval_order AS approvalOrder, " +
            "al.approval_type AS approvalType, al.status, al.approved_at AS approvedAt, " +
            "al.comment, al.created_at AS createdAt " +
            "FROM APPROVAL_LINES al " +
            "JOIN USERS u ON al.approver_id = u.user_id " +
            "WHERE al.document_id = #{documentId} ORDER BY al.approval_order")
    List<ApprovalLineVO> selectApprovalLinesByDocumentId(int documentId) throws Exception;
    
    /**
     * 사용자 목록 조회 (부서별)
     * @param deptId
     * @return
     * @throws Exception
     */
    @Select("SELECT user_id AS userId, user_name AS userName, dept_id AS deptId, role_id AS roleId " +
            "FROM USERS WHERE dept_id = #{deptId} ORDER BY user_name")
    List<LoginVO> selectUserListByDept(String deptId) throws Exception;
}
