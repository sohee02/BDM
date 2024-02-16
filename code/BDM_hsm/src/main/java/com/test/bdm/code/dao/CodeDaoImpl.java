package com.test.bdm.code.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.test.bdm.cmn.PcwkLogger;
import com.test.bdm.code.domain.CodeVO;

@Repository
public class CodeDaoImpl implements PcwkLogger, CodeDao {
	
	final String NAMESPACE = "com.test.bdm.code";
	final String DOT = ".";
	
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;

	@Override
	public int doUpdate(CodeVO inVO) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int doDelete(CodeVO inVO) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public CodeVO doSelectOne(CodeVO inVO) throws SQLException, EmptyResultDataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int doSave(CodeVO inVO) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<CodeVO> doRetrieve(CodeVO inVO) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CodeVO> doRetrieve(Map<String, Object> map) throws SQLException {
		//com.test.bdm.code.doRetrieve
		LOG.debug("┌───────────────────────────────────┐");
		LOG.debug("│ doRetrieve                        │");
		LOG.debug("│ BoardVO                           │"+map);
		LOG.debug("│ statement                         │"+NAMESPACE+DOT+"doRetrieve");//
		LOG.debug("└───────────────────────────────────┘");				
				
		return sqlSessionTemplate.selectList(NAMESPACE+DOT+"doRetrieve", map);
	}

}
