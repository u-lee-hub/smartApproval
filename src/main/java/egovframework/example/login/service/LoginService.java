package egovframework.example.login.service;

/**
 * @Class Name : LoginService.java
 */
public interface LoginService {
	
	/**
	 * 아이디로 사용자 조회
	 * @param userId - 조회할 아이디
	 * @return 조회한 사용자 정보가 담긴 LoginVO 객체
	 * @throws Exception
	 */
	LoginVO findUserById(String userId) throws Exception;
	
	/**
	 * 사용자 등록
	 * @param user - 등록할 사용자 정보가 담긴 LoginVO 객체
	 * @throws Exception
	 */
    void registerUser(LoginVO user) throws Exception;
}
