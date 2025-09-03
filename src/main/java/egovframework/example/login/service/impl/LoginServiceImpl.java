package egovframework.example.login.service.impl;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import egovframework.example.login.service.LoginService;
import egovframework.example.login.service.LoginVO;
import lombok.RequiredArgsConstructor;

/**
 * @Class Name : LoginServiceImpl.java
 */
@Service
@RequiredArgsConstructor
public class LoginServiceImpl extends EgovAbstractServiceImpl implements LoginService {
//	private static final Logger LOGGER = LoggerFactory.getLogger(LoginServiceImpl.class);

    private final LoginMapper loginMapper;
    
    /**
     * 아이디로 사용자 조회
     * @param userId - 조회할 아이디
     * @return 조회한 사용자 정보가 담긴 LoginVO 객체
     * @exception Exception
     */
    @Override
    public LoginVO findUserById(String userId) throws Exception {
        LoginVO resultVO = loginMapper.findUserById(userId);
        // 데이터 없을 때 예외 처리
        if (resultVO == null) {
            throw processException("info.nodata.msg");
        }
        return resultVO;
    }
    
    /**
     * 사용자 등록
     */
    @Override
    public void registerUser(LoginVO user) throws Exception {
        
        try {
            // 아이디 중복 체크
            LoginVO existingUser = loginMapper.findUserById(user.getUserId());
            if (existingUser != null) {
                throw processException("user.duplicate.msg"); // 중복 사용자 예외
            }
            
            loginMapper.insertUser(user); // 사용자 등록
            
        } catch (Exception e) {
            // DB 제약조건 위반이나 기타 오류 처리
            throw processException("user.register.error", e);
        }
    }
}
