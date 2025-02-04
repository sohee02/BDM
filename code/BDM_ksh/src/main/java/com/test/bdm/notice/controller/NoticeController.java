package com.test.bdm.notice.controller;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.test.bdm.cmn.DTO;
import com.test.bdm.cmn.MessageVO;
import com.test.bdm.cmn.PcwkLogger;
import com.test.bdm.cmn.StringUtil;
import com.test.bdm.code.domain.CodeVO;
import com.test.bdm.code.service.CodeService;
import com.test.bdm.notice.domain.NoticeVO;
import com.test.bdm.notice.service.NoticeService;
import com.test.bdm.user.domain.UserVO;

@Controller
@RequestMapping("notice")
public class NoticeController implements PcwkLogger {
	
	@Autowired
	NoticeService service;
	
	@Autowired
	CodeService  codeService;
	
	@Autowired
	MessageSource messageSource;//ResourceBundleMessageSource가 주입됨
	
	
	public NoticeController() {}
	
	@GetMapping(value="/moveToReg.do")
	public String moveToReg(Model model, NoticeVO inVO) throws SQLException {
		String viewName = "";
		LOG.debug("┌───────────────────────────────────┐");
		LOG.debug("│ moveToReg                         │");
		LOG.debug("│ inVO                              │"+inVO);
		LOG.debug("└───────────────────────────────────┘");
		
		//DIV코드 조회
		Map<String, Object> codes=new HashMap<String, Object>();
		String[] codeStr = {"BOARD_DIV"};
		codes.put("code", codeStr);
		
		List<CodeVO> codeList = this.codeService.doRetrieve(codes);
		model.addAttribute("divCode", codeList);
		model.addAttribute("paramVO", inVO);
		
		//공지사항:10, 자유게시판:20
		String title = "";
		if(inVO.getSearchDiv().equals("10")) {
			title = "공지사항-등록";
		}else {
			title = "자유게시판-등록";
		}
		model.addAttribute("title", title);	
		
		
		
		viewName = "notice/notice_reg";///WEB-INF/views/ viewName .jsp
		return viewName;
	}
	
	@GetMapping(value = "/doRetrieve.do")
	public ModelAndView doRetrieve(DTO inVO, ModelAndView modelAndView) throws SQLException{
		LOG.debug("┌───────────────────────────────────┐");
		LOG.debug("│ doRetrieve                        │");
		LOG.debug("│ BoardVO                           │"+inVO);
		LOG.debug("└───────────────────────────────────┘");
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
		LOG.debug("│ NoticeVO Default처리                          │"+inVO);
		//코드목록 조회 : 'PAGE_SIZE','BOARD_SEARCH'
		Map<String, Object> codes =new HashMap<String, Object>();
		String[] codeStr = {"PAGE_SIZE","SEARCH"};
		
		codes.put("code", codeStr);
		List<CodeVO> codeList = this.codeService.doRetrieve(codes);
		
		List<CodeVO> noticeSearchList=new ArrayList<CodeVO>();
		List<CodeVO> pageSizeList=new ArrayList<CodeVO>();
		
		
		for(CodeVO vo :codeList) {
			if(vo.getCategory().equals("SEARCH")) {
				noticeSearchList.add(vo);
			}
			
			if(vo.getCategory().equals("PAGE_SIZE")) {
				pageSizeList.add(vo);
			}	
			//LOG.debug(vo);
		}
		//목록조회
		List<NoticeVO>  list = service.doRetrieve(inVO);
		
		
		long totalCnt = 0;
		//총글수 
		for(NoticeVO vo  :list) {
			if(totalCnt == 0) {
				totalCnt = vo.getTotalCnt();
				break;
			}
		}
		modelAndView.addObject("totalCnt", totalCnt);
		
		//뷰
		modelAndView.setViewName("notice/notice_list");//  /WEB-INF/views/board/board_list.jsp
		//Model
		modelAndView.addObject("list", list);
		//검색데이터
		modelAndView.addObject("paramVO", inVO);  
		
		//검색조건
		modelAndView.addObject("boardSearch", noticeSearchList);
		
		//페이지 사이즈
		modelAndView.addObject("pageSize",pageSizeList);
		
		//페이징
		long bottomCount = StringUtil.BOTTOM_COUNT;//바닥글
		String html = StringUtil.renderingPager(totalCnt, inVO.getPageNo(), inVO.getPageSize(), bottomCount,
				"/bdm/notice/doRetrieve.do", "pageDoRerive");
		modelAndView.addObject("pageHtml", html);
		
		
		//공지사항:10, 자유게시판:20
		String title = "";
		if(inVO.getSearchDiv().equals("10")) {
			title = "공지사항-목록";
		}else {
			title = "자유게시판-목록";
		}
		modelAndView.addObject("title", title);	
			
		return modelAndView;   
	}
	
	@PostMapping(value = "/doUpdate.do", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public MessageVO doUpdate(NoticeVO inVO) throws SQLException{
		LOG.debug("┌───────────────────────────────────┐");
		LOG.debug("│ doUpdate                          │");
		LOG.debug("│ NoticeVO                          │"+inVO);
		LOG.debug("└───────────────────────────────────┘");
		
		int flag = service.doUpdate(inVO);
		////현재 스레드에서 설정된 Locale이 반환
		Locale  locale= LocaleContextHolder.getLocale();
		
		String message = "";
		if(1==flag) {
			//message = "수정 되었습니다.";
			message = messageSource.getMessage("common.message.update", null, locale);
			LOG.debug("│ message                           │"+message);
			//파라메터 치환
			String update = "수정";
			message = MessageFormat.format(message, update);
			LOG.debug("│ message                           │"+message);
		}else {
			message = "수정 실패.";
		}
		
		MessageVO messageVO=new MessageVO(flag+"",message);
		LOG.debug("│ messageVO                           │"+messageVO);
		return messageVO;
		
	}
	
	@GetMapping(value = "/doSelectOne.do")
	public String doSelectOne(NoticeVO inVO, Model model, HttpSession httpSession) throws SQLException, EmptyResultDataAccessException{
		String view = "notice/board_mng";///WEB-INF/views/+board/board_mng+.jsp ->/WEB-INF/views/board/board_mng.jsp
		LOG.debug("┌───────────────────────────────────┐");
		LOG.debug("│ doSelectOne                       │");
		LOG.debug("│ NoticeVO                          │"+inVO);
		LOG.debug("└───────────────────────────────────┘");			
		if(0 == inVO.getPostNo()) {
			LOG.debug("============================");
			LOG.debug("==nullPointerException===");
			LOG.debug("============================");
			
			throw new NullPointerException("순번을 입력 하세요");
		}
		
		if( null==inVO.getId()) {
			inVO.setId(StringUtil.nvl(inVO.getId(),"Guest"));
		}
		
		//session이 있는 경우
		if(null != httpSession.getAttribute("user")) {
			UserVO user = (UserVO) httpSession.getAttribute("user");
			inVO.setId(user.getId());
		}
		
		NoticeVO  outVO = service.doSelectOne(inVO);
		model.addAttribute("vo", outVO);
		
		//DIV코드 조회
		Map<String, Object> codes=new HashMap<String, Object>();
		String[] codeStr = {"BOARD_DIV"};
		codes.put("code", codeStr);
		
		List<CodeVO> codeList = this.codeService.doRetrieve(codes);
		model.addAttribute("divCode", codeList);
		
		//공지사항:10, 자유게시판:20
//		String title = "";
//		if(inVO.getNo().equals("10")) {
//			title = "공지사항-수정";
//		}else {
//			title = "자유게시판-수정";
//		}
//		model.addAttribute("title", title);	
		
		return view;
	}
	
	
	//@RequestMapping(value = "/doSave.do",method = RequestMethod.POST)
	@PostMapping(value = "/doSave.do", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public MessageVO doSave(NoticeVO inVO) throws SQLException{
		LOG.debug("┌───────────────────────────────────┐");
		LOG.debug("│ doSave                            │");
		LOG.debug("│ NoticeVO                          │"+inVO);
		LOG.debug("└───────────────────────────────────┘");				
		//seq조회
		int seq = service.getNoticeSeq();
		inVO.setPostNo(seq);
		LOG.debug("│ NoticeVO seq                       │"+inVO);
		int flag = service.doSave(inVO);
		
		String message = "";
		if(1 == flag) {
			message = "등록 되었습니다.";
		}else {
			message = "등록 실패.";
		}
		
		MessageVO  messageVO=new MessageVO(flag + "", message);
		LOG.debug("│ messageVO                           │"+messageVO);
		return messageVO;
	}
	

	@GetMapping(value ="/doDelete.do",produces = "application/json;charset=UTF-8" )//@RequestMapping(value = "/doDelete.do",method = RequestMethod.GET)
	@ResponseBody// HTTP 요청 부분의 body부분이 그대로 브라우저에 전달된다.
	public MessageVO doDelete(NoticeVO inVO) throws SQLException{
		LOG.debug("┌───────────────────────────────────┐");
		LOG.debug("│ doDelete                          │");
		LOG.debug("│ NoticeVO                          │"+inVO);
		LOG.debug("└───────────────────────────────────┘");		
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
			message = inVO.getPostNo()+"삭제 되었습니다.";	
		}else {
			message = inVO.getPostNo()+"삭제 실패!";
		}
		
		MessageVO messageVO=new MessageVO(String.valueOf(flag), message);
		
		LOG.debug("│ messageVO                           │"+messageVO);
		return messageVO;
	}
	
	
	

}
