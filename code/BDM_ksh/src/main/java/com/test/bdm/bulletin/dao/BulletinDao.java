package com.test.bdm.bulletin.dao;

import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;

import com.test.bdm.bulletin.domain.BulletinVO;
import com.test.bdm.cmn.DTO;
import com.test.bdm.cmn.PcwkLogger;
import com.test.bdm.cmn.WorkDiv;

public interface BulletinDao extends WorkDiv<BulletinVO> {
	
	
	/**
	 * 글 제목으로 삭제: test only
	 * @param inVO
	 * @return int
	 * @throws SQLException
	 */
	int doDeleteAll(BulletinVO inVO) throws SQLException;
	
	
	/**
	 * 조회 건 수 증가
	 * @param inVO
	 * @return int
	 * @throws SQLException
	 */
	int updateReadCnt(BulletinVO inVO) throws SQLException;
	
	BulletinVO bulletinView(BulletinVO inVO) throws SQLException, EmptyResultDataAccessException;


	int getBulletinSeq() throws SQLException;
	
	List<BulletinVO> doRetrieve(DTO inVO)throws SQLException;
}
