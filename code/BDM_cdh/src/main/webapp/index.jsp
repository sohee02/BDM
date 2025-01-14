<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="CP" value="${pageContext.request.contextPath}" />     
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<meta name="viewport"  content="width=device-width, initial-scale=1">
<%-- <link rel="stylesheet" href="${CP}/resources/css/user.css"> --%>
<title>게시등록</title>
<script src="${CP}/resources/js/jquery-3.7.1.js"></script>
<script src="${CP}/resources/js/eUtil.js"></script>
</head>
<body>
<c:redirect url="/beforeMain/moveToMain.do" />
0
 <ul class="nav">
  <li class="nav-item">
    <a class="nav-link active" aria-current="page" href="<c:url value='/beforeMain/moveToMain.do'/>">메인페이지</a>
  </li>
  
  <li class="nav-item">
    <a class="nav-link" href="<c:url value='/nutrient/doRetrieve.do'/>">음식 등록</a>
  </li>   
  <li class="nav-item">
    <a class="nav-link" href="c:url value='/user/doRetrieve.do'/>">회원목록</a>
  </li>  
  <li class="nav-item">
    <a class="nav-link" href="<c:url value='/board/doRetrieve.do'/>">게시목록</a>
  </li>
  <li class="nav-item">
    <a class="nav-link" href="<c:url value='/board/moveToReg.do'/>">게시등록</a>
  </li>
  <li class="nav-item">
    <a class="nav-link" href="<c:url value='/chart/viewPie.do'/>">파이차트</a>
  </li>
  <li class="nav-item">
    <a class="nav-link" href="<c:url value='/chart/viewLineChart.do'/>">라인차트</a>
  </li>
  <li class="nav-item">
    <a class="nav-link" href="<c:url value='/file/uploadView.do'/>">파일up/down</a>
  </li> 
  <li class="nav-item">
    <a class="nav-link" href="<c:url value='/file/dragAndDropView.do'/>">파일_drag/drop</a>
  </li> 
        
  <li class="nav-item">
    <a class="nav-link" href="<c:url value='/template/viewBlank.do'/>">blank</a>
  </li>  
      
  <li class="nav-item">
    <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Disabled</a>
  </li>
</ul>
</body>
</html>