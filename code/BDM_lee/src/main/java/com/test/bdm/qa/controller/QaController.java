package com.test.bdm.qa.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.test.bdm.cmn.MessageVO;
import com.test.bdm.cmn.PcwkLogger;
import com.test.bdm.cmn.StringUtil;
import com.test.bdm.code.domain.CodeVO;
import com.test.bdm.code.service.CodeService;
import com.test.bdm.notice.domain.NoticeVO;
import com.test.bdm.qa.domain.QaVO;
import com.test.bdm.qa.service.QaService;
import com.test.bdm.user.domain.UserVO;

@Controller
@RequestMapping("qa")
public class QaController implements PcwkLogger {
	
	@Autowired
	QaService service;
	
	@Autowired
	CodeService codeService;
	
	@Autowired
	MessageSource messageSource;
	
	public QaController() {}
	
	@GetMapping(value="/moveReg.do")
	public String moveToReg(Model model, QaVO inVO) throws SQLException {
		String viewName = "";
		LOG.debug("─────────────────────────────────────");
		LOG.debug("moveToReg"                            );
		LOG.debug("inVO: " + inVO                        );
		LOG.debug("─────────────────────────────────────");
		
		viewName = "qa/qa_reg";
		return viewName;
	}
	
	@GetMapping(value = "/doRetrieve.do")
	public ModelAndView doRetrieve(QaVO inVO, ModelAndView modelAndView) throws SQLException{
		LOG.debug("─────────────────────────────────────");
		LOG.debug("doRetrieve"                           );
		LOG.debug("qaVO: " + inVO                        );
		LOG.debug("─────────────────────────────────────");
		//Default처리
		//페이지 사이즈:10
		if(null != inVO && inVO.getPageSize() == 0) {
			inVO.setPageSize(10L);
		}

		//페이지 번호:1
		if(null != inVO && inVO.getPageNo() == 0) {
			inVO.setPageNo(1L);
		}
		
		//검색구분:""
		if(null != inVO && null == inVO.getSearchDiv()) {
			inVO.setSearchDiv(StringUtil.nvl(inVO.getSearchDiv()));
		}
		//검색어:""
		if(null != inVO && null == inVO.getSearchWord()) {
			inVO.setSearchDiv(StringUtil.nvl(inVO.getSearchWord()));
		}
		LOG.debug("QaVO Default처리: " + inVO);
		//코드목록 조회 : 'PAGE_SIZE','BOARD_SEARCH'
		Map<String, Object> codes =new HashMap<String, Object>();
		String[] codeStr = {"PAGE_SIZE","QA_SEARCH"};
		
		codes.put("code", codeStr);
		List<CodeVO> codeList = this.codeService.doRetrieve(codes);
		
		List<CodeVO> qaSearchList=new ArrayList<CodeVO>();
		List<CodeVO> pageSizeList=new ArrayList<CodeVO>();
		
		
		for(CodeVO vo :codeList) {
			if(vo.getCategory().equals("QA_SEARCH")) {
				qaSearchList.add(vo);
			}
			
			if(vo.getCategory().equals("PAGE_SIZE")) {
				pageSizeList.add(vo);
			}	
			//LOG.debug(vo);
		}
		//목록조회
		List<QaVO>  list = service.doRetrieve(inVO);
		
		
		long totalCnt = 0;
		//총글수 
		for(QaVO vo: list) {
			if(totalCnt == 0) {
				totalCnt = vo.getTotalCnt();
				break;
			}
		}
		modelAndView.addObject("totalCnt", totalCnt);
		
		//뷰
		modelAndView.setViewName("qa/qa_list");//  /WEB-INF/views/board/board_list.jsp
		//Model
		modelAndView.addObject("list", list);
		//검색데이터
		modelAndView.addObject("paramVO", inVO);  
		
		//검색조건
		modelAndView.addObject("qaSearch", qaSearchList);
		
		//페이지 사이즈
		modelAndView.addObject("pageSize",pageSizeList);
		
		//페이징
		long bottomCount = StringUtil.BOTTOM_COUNT;//바닥글
		String html = StringUtil.renderingPager(totalCnt, inVO.getPageNo(), inVO.getPageSize(), bottomCount,
				"/bdm/qa/doRetrieve.do", "pageDoRerive");
		modelAndView.addObject("pageHtml", html);
			
		return modelAndView;   
	}
	
	@PostMapping(value = "/doSave.do", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public MessageVO doSave(QaVO inVO, HttpSession httpSession) throws SQLException{
		LOG.debug("─────────────────────────────────────");
		LOG.debug("doSave"                               );
		LOG.debug("QaVO: " + inVO                        );
		LOG.debug("─────────────────────────────────────");				
		//seq조회
		int seq = service.getQaSeq();
		inVO.setPostNo(seq);
		
		if(null != httpSession.getAttribute("user")) {
			UserVO user = (UserVO) httpSession.getAttribute("user");
			inVO.setId(user.getId());
		}
		
		if(inVO.getDisclosure() == 0) {
			inVO.setDisclosure(0);
		} else {
			inVO.setDisclosure(1);
		}
		
		LOG.debug("QaVO seq: " + inVO);
		int flag = service.doSave(inVO);
		
		String message = "";
		if(1 == flag) {
			message = "등록 되었습니다.";
		}else {
			message = "등록 실패.";
		}
		
		MessageVO  messageVO=new MessageVO(flag + "", message);
		LOG.debug("messageVO: " + messageVO);
		return messageVO;
	}
	
	@GetMapping(value = "/qaView.do")
	public String doSelectOne(QaVO inVO, Model model, HttpSession httpSession) throws SQLException, EmptyResultDataAccessException{
		String view = "qa/qa_view";
		LOG.debug("────────────────────────────────────────");
		LOG.debug(" doSelectOne"                            );
		LOG.debug(" QaVO: " + inVO                          );
		LOG.debug(" doSelectOne Qa User ID: " + inVO.getId());
		LOG.debug("────────────────────────────────────────");		
		if(0 == inVO.getPostNo()) {
			LOG.debug("============================");
			LOG.debug("==nullPointerException===");
			LOG.debug("============================");
			
			throw new NullPointerException("순번을 입력 하세요");
		}
		
		inVO.setPostNo(inVO.getPostNo());
		if( null==inVO.getId()) {
			inVO.setId(StringUtil.nvl(inVO.getId(),"Guest"));
		}
		
		//session이 있는 경우
		if(null != httpSession.getAttribute("user")) {
			UserVO user = (UserVO) httpSession.getAttribute("user");
			inVO.setId(user.getId());
		}
		
		QaVO  outVO = service.doSelectOne(inVO);
		model.addAttribute("vo", outVO);
		
		//DIV코드 조회
		Map<String, Object> codes=new HashMap<String, Object>();
		String[] codeStr = {"BOARD_DIV"};
		codes.put("code", codeStr);
		
		List<CodeVO> codeList = this.codeService.doRetrieve(codes);
		model.addAttribute("divCode", codeList);
		
		return view;
	}
	
	@GetMapping(value ="/doDelete.do",produces = "application/json;charset=UTF-8" )//@RequestMapping(value = "/doDelete.do",method = RequestMethod.GET)
	@ResponseBody// HTTP 요청 부분의 body부분이 그대로 브라우저에 전달된다.
	public MessageVO doDelete(QaVO inVO) throws SQLException{
		LOG.debug("─────────────────────────────────────");
		LOG.debug("doDelete"                             );
		LOG.debug("NoticeVO: " + inVO                    );
		LOG.debug("─────────────────────────────────────");		
		if(0 == inVO.getPostNo()) {
			LOG.debug("============================");
			LOG.debug("==nullPointerException===");
			LOG.debug("============================");
			MessageVO messageVO=new MessageVO(String.valueOf("3"), "순번을 선택 하세요.");
			return messageVO;
		} 
		
		int flag = service.doDelete(inVO);
		
		String   message = "";
		if(1==flag) {//삭제 성공
			message = "삭제 되었습니다.";	
		}else {
			message = "삭제 실패!";
		}
		
		MessageVO messageVO=new MessageVO(String.valueOf(flag), message);
		
		LOG.debug("messageVO: " + messageVO);
		return messageVO;
	}

}
