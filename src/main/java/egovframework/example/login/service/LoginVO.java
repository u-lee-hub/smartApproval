package egovframework.example.login.service;

/**
 * @Class Name : LoginVO.java
 * @Description : LoginVO Class
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @            최초생성
 *
 * @author 
 * @since 
 * @version 1.0
 * @see
 *
 *  Copyright (C) by MOPAS All right reserved.
 */
public class LoginVO {

	/** 아이디 */
	private String userId;
	
	/** 패스워드 */
	private String password;

	/** 이름 */
	private String userName;
	
	/** 이메일 */
	private String email;
	
	/** 전화번호 */
	private String phone;

	/** 사용자 권한 ID */
	private String roleId;

	/** 소속 부서 ID */
	private String deptId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

}
