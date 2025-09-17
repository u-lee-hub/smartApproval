package egovframework.example.approvalrule.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import egovframework.example.approvalrule.service.ApprovalRuleVO;

@Mapper
public interface ApprovalRuleMapper {
// 사용자 조회용 쿼리 //////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 부서별 역할에 해당하는 사용자 조회
	 * @param deptId
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	@Select("SELECT user_id FROM USERS WHERE dept_id = #{deptId} AND role_id = #{roleId} LIMIT 1")
	String selectManagerByDeptAndRole(@Param("deptId") String deptId, @Param("roleId") String roleId) throws Exception;

	/**
	 * 역할에 해당하는 사용자 조회 (전체)
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	@Select("SELECT user_id FROM USERS WHERE role_id = #{roleId} LIMIT 1") 
	String selectUserByRole(@Param("roleId") String roleId) throws Exception;
	
// 자동 결재 규칙용 쿼리 //////////////////////////////////////////////////////////////////////////////////////
	/**
     * 금액에 해당하는 결재자 개수 조회 (간단한 버전)
     * @param amount
     * @return
     * @throws Exception
     */
    @Select("SELECT approver_count " +
            "FROM APPROVAL_RULES " +
            "WHERE is_active = TRUE " +
            "AND #{amount} >= min_amount AND #{amount} <= max_amount " +
            "ORDER BY min_amount ASC LIMIT 1")
    Integer selectApproverCountByAmount(@Param("amount") int amount) throws Exception;
    
    /**
     * 금액과 문서타입에 해당하는 활성 규칙 조회 (상세 버전)
     * @param amount
     * @param documentType
     * @return
     * @throws Exception
     */
    @Select("SELECT rule_id AS ruleId, document_type AS documentType, " +
            "min_amount AS minAmount, max_amount AS maxAmount, " +
            "approver_count AS approverCount, rule_description AS ruleDescription, " +
            "is_active AS isActive, created_at AS createdAt, updated_at AS updatedAt " +
            "FROM APPROVAL_RULES " +
            "WHERE is_active = TRUE " +
            "AND (document_type = #{documentType} OR document_type = 'ALL') " +
            "AND #{amount} >= min_amount AND #{amount} <= max_amount " +
            "ORDER BY " +
            "  CASE WHEN document_type = #{documentType} THEN 1 ELSE 2 END, " +
            "  min_amount ASC " +
            "LIMIT 1")
    ApprovalRuleVO selectRuleByAmountAndType(@Param("amount") int amount, 
                                           @Param("documentType") String documentType) throws Exception;
    
    /**
     * 모든 활성 규칙 조회
     * @return
     * @throws Exception
     */
    @Select("SELECT rule_id AS ruleId, document_type AS documentType, " +
            "min_amount AS minAmount, max_amount AS maxAmount, " +
            "approver_count AS approverCount, rule_description AS ruleDescription, " +
            "is_active AS isActive, " +
            "DATE_FORMAT(created_at, '%Y-%m-%d %H:%i') AS createdAt, " +
            "DATE_FORMAT(updated_at, '%Y-%m-%d %H:%i') AS updatedAt " +
            "FROM APPROVAL_RULES " +
            "WHERE is_active = TRUE " +
            "ORDER BY document_type, min_amount")
    List<ApprovalRuleVO> selectAllActiveRules() throws Exception;
    
    /**
     * 특정 규칙 조회
     * @param ruleId
     * @return
     * @throws Exception
     */
    @Select("SELECT rule_id AS ruleId, document_type AS documentType, " +
            "min_amount AS minAmount, max_amount AS maxAmount, " +
            "approver_count AS approverCount, rule_description AS ruleDescription, " +
            "is_active AS isActive, created_at AS createdAt, updated_at AS updatedAt " +
            "FROM APPROVAL_RULES WHERE rule_id = #{ruleId}")
    ApprovalRuleVO selectRuleById(@Param("ruleId") int ruleId) throws Exception;
    
    /**
     * 규칙 수정
     * @param rule
     * @return
     * @throws Exception
     */
    @Update("UPDATE APPROVAL_RULES SET " +
            "approver_count = #{approverCount}, " +
            "rule_description = #{ruleDescription}, " +
            "updated_at = NOW() " +
            "WHERE rule_id = #{ruleId} AND is_active = TRUE")
    int updateRule(ApprovalRuleVO rule) throws Exception;
    
    /**
     * 새 규칙 추가
     * @param rule
     * @return
     * @throws Exception
     */
    @Insert("INSERT INTO APPROVAL_RULES (document_type, min_amount, max_amount, " +
            "approver_count, rule_description, is_active) " +
            "VALUES (#{documentType}, #{minAmount}, #{maxAmount}, " +
            "#{approverCount}, #{ruleDescription}, TRUE)")
    int insertRule(ApprovalRuleVO rule) throws Exception;
    
    /**
     * 규칙 비활성화
     * @param ruleId
     * @return
     * @throws Exception
     */
    @Update("UPDATE APPROVAL_RULES SET is_active = FALSE, updated_at = NOW() WHERE rule_id = #{ruleId}")
    int deactivateRule(@Param("ruleId") int ruleId) throws Exception;
    
    /**
     * 중복 규칙 체크
     * @param documentType
     * @param minAmount
     * @param maxAmount
     * @param excludeRuleId
     * @return
     * @throws Exception
     */
    @Select("SELECT COUNT(*) FROM APPROVAL_RULES " +
            "WHERE is_active = TRUE " +
            "AND document_type = #{documentType} " +
            "AND ((#{minAmount} >= min_amount AND #{minAmount} <= max_amount) " +
            "     OR (#{maxAmount} >= min_amount AND #{maxAmount} <= max_amount) " +
            "     OR (#{minAmount} < min_amount AND #{maxAmount} > max_amount)) " +
            "AND rule_id != #{excludeRuleId}")
    int countOverlappingRules(@Param("documentType") String documentType,
                             @Param("minAmount") int minAmount,
                             @Param("maxAmount") int maxAmount,
                             @Param("excludeRuleId") int excludeRuleId) throws Exception;
    
}