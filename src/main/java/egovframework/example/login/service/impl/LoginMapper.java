package egovframework.example.login.service.impl;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import egovframework.example.login.service.LoginVO;

/**
 * login에 관한 데이터처리 매퍼 클래스
 * 관련 쿼리문 1개라 Login_SQL.xml 대신 @Select로 대체
*/
@Mapper
public interface LoginMapper {

	/**
	 * 아이디로 사용자 조회
	 * @param userId - 조회할 아이디
	 * @return 조회한 사용자 정보가 담긴 LoginVO 객체
	 * @throws Exception
	 */
	@Select("SELECT user_id AS userId, password, user_name AS userName, role_id AS roleId, dept_id AS deptId FROM USERS WHERE user_id = #{userId}")
	LoginVO findUserById(String userId) throws Exception;
	
	/**
	 * 사용자 등록
	 * @param user - 등록할 사용자 정보가 담긴 LoginVO 객체
	 * @throws Exception
	 */
	@Insert("INSERT INTO USERS (user_id, password, user_name, email, phone, role_id, dept_id) VALUES (#{userId}, #{password}, #{userName}, #{email}, #{phone}, #{roleId}, #{deptId})")
    void insertUser(LoginVO user) throws Exception;

}
