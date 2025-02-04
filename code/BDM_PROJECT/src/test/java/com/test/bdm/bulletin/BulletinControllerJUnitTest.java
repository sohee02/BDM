//package com.test.bdm.bulletin;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.fail;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.util.Arrays;
//import java.util.List;
//
//import org.junit.Before;
//import org.junit.FixMethodOrder;
//import org.junit.Ignore;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.MethodSorters;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.google.gson.Gson;
//import com.test.bdm.bulletin.dao.BulletinDao;
//import com.test.bdm.bulletin.domain.BulletinVO;
//import com.test.bdm.cmn.MessageVO;
//import com.test.bdm.cmn.PcwkLogger;
//import com.test.bdm.code.domain.CodeVO;
//
//@WebAppConfiguration
//@RunWith(SpringJUnit4ClassRunner.class) //스프링 테스트 컨텍스트 프레임웤그의 JUnit의 확장기능 지정
//@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"
//		,"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//public class BulletinControllerJUnitTest implements PcwkLogger {
//	
//	@Autowired
//	BulletinDao dao;
//	
//	@Autowired
//	WebApplicationContext webApplicationContext;
//	//브라우저 대역
//	MockMvc  mockMvc;
//	List<BulletinVO>  bulletinList;
//	BulletinVO   searchVO;
//
//	@Before
//	public void setUp() throws Exception {
//		LOG.debug("┌───────────────────────────────────────────┐");
//		LOG.debug("│ setUp()                                   │");		
//		LOG.debug("└───────────────────────────────────────────┘");	
//		String title = "자유게시판";
//		String contents = "게시글";
//		String regDt    = "사용 하지 않음";
//		String modDt    = "사용 하지 않음";
//		int    readCnt  = 0;
//		String id    = "hsm";
//		String modId    = "hsm";
//		
//		mockMvc  = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//		bulletinList = Arrays.asList(
//				 new BulletinVO(dao.getBulletinSeq(), title+"제목1", contents+"내용1", regDt, modDt, readCnt, id, modId)
//		);
//		
//		searchVO = new BulletinVO();
//		searchVO.setTitle(title);
//	}
//	@Ignore
//	@Test
//	public void doRetrieve() throws Exception{
//		//검색
//		LOG.debug("┌───────────────────────────────────────────┐");
//		LOG.debug("│ doRetrieve()                              │");		
//		LOG.debug("└───────────────────────────────────────────┘");
//		
//		MockHttpServletRequestBuilder  requestBuilder = MockMvcRequestBuilders.get("/board/doRetrieve.do")
//				.param("pageSize",   "0")
//				.param("pageNo",     "0")
//				.param("searchDiv",  "")
//				.param("searchWord", "")
//				;		
//		
//		//호출 : ModelAndView      
//		MvcResult mvcResult=  mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn() ;
//		//호출결과
//		ModelAndView modelAndView = mvcResult.getModelAndView();
//		List<BulletinVO>  list  = (List<BulletinVO>) modelAndView.getModel().get("list");
//		BulletinVO  paramVO  = (BulletinVO) modelAndView.getModel().get("paramVO");
//		
//		
//		List<CodeVO> bulletinSearchList=(List<CodeVO>) modelAndView.getModel().get("bulletinSearch");
//		List<CodeVO> pageSizeList=(List<CodeVO>) modelAndView.getModel().get("pageSize");
//		
//		for(BulletinVO vo  :list) {
//			LOG.debug(vo);
//		}
//		
//		assertNotNull(bulletinSearchList);
//		assertNotNull(pageSizeList);
//		assertNotNull(list);
//		assertNotNull(paramVO);
//		
//	}
//	
//	
//	@Ignore
//	@Test
//	public void doUpdate() throws Exception{
//
//		
//		BulletinVO bulletin01 = bulletinList.get(0);
//		int flag = dao.doSave(bulletin01);
//		assertEquals(1, flag);
//
//		BulletinVO vo = dao.doSelectOne(bulletin01);
//		
//		String upStr = "_P";
//		vo.setTitle(vo.getTitle()+upStr);
//		vo.setContents(vo.getContents()+upStr);
//		vo.setModId(vo.getModId()+upStr);
//		
//		
//		MockHttpServletRequestBuilder  requestBuilder = MockMvcRequestBuilders.post("/board/doUpdate.do")
//				.param("postNo",     vo.getPostNo()+"")
//				.param("title",   vo.getTitle())
//				.param("contents",vo.getContents())
//				.param("modId",   vo.getModId())
//				;
//		//호출        
//		ResultActions resultActions=  mockMvc.perform(requestBuilder).andExpect(status().isOk());
//		//호출결과
//		String result = resultActions.andDo(print()).andReturn().getResponse().getContentAsString();
//		LOG.debug("│ result                                │"+result);		
//		
//		MessageVO messageVO=new Gson().fromJson(result, MessageVO.class);
//		assertEquals("1", messageVO.getMsgId());
//		
//		BulletinVO updateResult = dao.doSelectOne(vo);
//		
//		isSameBoard(updateResult,vo);
//	}
//	private void isSameBoard(BulletinVO vo, BulletinVO board) {
//		assertEquals(vo.getPostNo(), board.getPostNo());
//		assertEquals(vo.getTitle(), board.getTitle());
//		assertEquals(vo.getContents(), board.getContents());
//		assertEquals(vo.getModId(), board.getModId());
//	}
//	
//	
//	@Ignore
//	@Test
//	public void doSelectOne()throws Exception{
//		LOG.debug("┌───────────────────────────────────────────┐");
//		LOG.debug("│ doSelectOne()                             │");		
//		LOG.debug("└───────────────────────────────────────────┘");
//		
//		int flag = dao.doSave(bulletinList.get(0));
//		assertEquals(1, flag);
//		BulletinVO vo = bulletinList.get(0);
//		
//		MockHttpServletRequestBuilder  requestBuilder  =
//				MockMvcRequestBuilders.get("/board/doSelectOne.do")
//				.param("postNo",     vo.getPostNo()+"")
//				.param("id",   vo.getId())
//				;		
//		
//		//호출 : ModelAndView      
//		MvcResult mvcResult=  mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn() ;
//		//호출결과
//		ModelAndView modelAndView = mvcResult.getModelAndView();
//
//		BulletinVO outVO = (BulletinVO) modelAndView.getModel().get("vo");
//		LOG.debug("│ outVO                                │"+outVO);
//		assertNotNull(outVO);
//
//	}
//	
//	@Ignore
//	@Test
//	public void doSave()throws Exception{
//		LOG.debug("┌───────────────────────────────────────────┐");
//		LOG.debug("│ doSave  ()                                │");		
//		LOG.debug("└───────────────────────────────────────────┘");		
//		
//		BulletinVO vo = bulletinList.get(0);
//		//url, 호출방식(get), seq
//		MockHttpServletRequestBuilder  requestBuilder  =
//				MockMvcRequestBuilders.post("/board/doSave.do")
//				.param("postNo",     vo.getPostNo()+"")
//				.param("title",     vo.getTitle())
//				.param("contents",   vo.getContents())
//				//.param("regDt",vo.getRegDt())
//				//.param("modDt", vo.getModDt())
//				.param("readCnt",   vo.getReadCnt()+"")
//				.param("id",   vo.getId())
//				.param("modId",   vo.getModId())
//				;
//		//호출        
//		ResultActions resultActions=  mockMvc.perform(requestBuilder).andExpect(status().isOk());
//		//호출결과
//		String result = resultActions.andDo(print()).andReturn().getResponse().getContentAsString();
//		LOG.debug("│ result                                │"+result);		
//		MessageVO messageVO=new Gson().fromJson(result, MessageVO.class);
//		LOG.debug("│ messageVO                                │"+messageVO);
//		assertEquals("1", messageVO.getMsgId());
//	}
//	
////	@Ignore
//	@Test
//	public void doDelete()throws Exception{
//		LOG.debug("┌───────────────────────────────────────────┐");
//		LOG.debug("│ doDelete()                                │");		
//		LOG.debug("└───────────────────────────────────────────┘");
//		
////		int flag = dao.doSave(bulletinList.get(0));
////		assertEquals(3, flag);
//		
//		MockHttpServletRequestBuilder  requestBuilder = MockMvcRequestBuilders.get("/board/doDelete.do")
//				.param("postNo", 0+"");
//		
//		ResultActions resultActions=  mockMvc.perform(requestBuilder).andExpect(status().isOk());
//		String result = resultActions.andDo(print()).andReturn().getResponse().getContentAsString();
//		LOG.debug("│ result                                │"+result);		
//		MessageVO messageVO=new Gson().fromJson(result, MessageVO.class);
//		LOG.debug("│ messageVO                                │"+messageVO);
//		assertEquals("1", messageVO.getMsgId());
//		
//	}
//	
//
//	@Test
//	public void beans() {
//		LOG.debug("┌───────────────────────────────────────────┐");
//		LOG.debug("│ webApplicationContext                     │"+webApplicationContext);		
//		LOG.debug("│ mockMvc                                   │"+mockMvc);
//		LOG.debug("│ dao                                       │"+dao);
//		LOG.debug("└───────────────────────────────────────────┘");			
//		assertNotNull(webApplicationContext);
//		assertNotNull(mockMvc);
//		assertNotNull(dao);
//	}
//
//}