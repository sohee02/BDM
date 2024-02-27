package com.test.bdm.beforeMain.dao;

import java.sql.SQLException;

import com.test.bdm.user.domain.UserVO;

public interface BeforeMainDao {
	
	int idCheck(UserVO inVO)throws SQLException;
	
	int idPassCheck(UserVO inVO)throws SQLException;
	
	UserVO doSelectOne(UserVO inVO) throws SQLException;
}