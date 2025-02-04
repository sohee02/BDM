package com.test.bdm.beforeMain.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;

import com.test.bdm.cmn.DTO;
import com.test.bdm.user.domain.UserVO;

public interface BeforeMainDao {
	
	int idCheck(UserVO inVO)throws SQLException;
	
	int idPassCheck(UserVO inVO)throws Exception;

	UserVO doSelectOne(UserVO inVO) throws SQLException;

	UserVO doSelectNaverEmail(UserVO inVO) throws SQLException;
	
	int doSaveSearch(int gender, int birth, String words) throws SQLException;
	
	List<DTO> popSearchWord() throws SQLException;
	
	List<DTO> popWeeklySearchWord(HashMap<String, String> map) throws SQLException;

	UserVO doSelectOneByEmail(UserVO inVO) throws SQLException, EmptyResultDataAccessException;
}
