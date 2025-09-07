package egovframework.example.approval.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import egovframework.example.approval.service.ApprovalDocumentVO;

@Mapper
public interface ApprovalMapper {
	
	/**
	 * 결재 문서 등록
	 * @param document
	 * @throws Exception
	 */
	@Insert("INSERT INTO DOCUMENTS (title, content, document_type, author_id, dept_id, status) " +
            "VALUES (#{title}, #{content}, #{documentType}, #{authorId}, #{deptId}, #{status})")
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
}
