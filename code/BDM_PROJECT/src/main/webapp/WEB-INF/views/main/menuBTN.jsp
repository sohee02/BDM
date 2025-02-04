<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.time.LocalDate"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%@ page import="com.test.bdm.nutrient.domain.NutrientVO"%>

<c:set var="CP" value="${pageContext.request.contextPath}" scope="page" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Insert title here</title>
<link rel="stylesheet" href="${CP}/resources/css/bootstrap.min.css">
<link rel="stylesheet" href="${CP}/resources/css/menuBTN.css">
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script>
document.addEventListener("DOMContentLoaded", function(){
	
	let userFilter = 0;
    
	<c:if test = "${not empty user}">
    userFilter = ${user.userFilter}
    </c:if>
    
    // HTML 요소 가져오기
    var userMonitorItem = document.getElementById('userMonitor');

    // userFilter가 1이면 보이고 그렇지 않으면 숨깁니다.
    if (userFilter === 1) {
        userMonitorItem.style.display = 'block'; // 보이기
    } else {
        userMonitorItem.style.display = 'none'; // 숨기기
    }

    
    $("#login").click(function(event) {
        event.preventDefault();
        window.location.href = "${CP}/beforeMain/moveToLogin.do";
    });
});
</script>
<style>

</style>
</head>
<body id="aside">
    <div id="app" class="wrap">
        <div class="aside_header">
            <div class="ah_wrap">
                <h1 class="ah_title">바로가기</h1>
            </div>
        </div>
        <div class="aside_wrap">
            <div class="section_shortcut">
			    <div class="user_area"> <!-- 이 부분을 확인 -->
			        <a href="#" class="link_user" rel="noreferrer">
			            <h2 class="title">
			                <span class="name">로그인</span>
			                <span class="text">하세요</span>
			            </h2>
			        </a>
			    </div>
                <div class="bg_media_wrap">
                    <div class="ss_shortcut_wrap" style="user-select: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
                        <div class="scroll-area">
                            <div class="ss_scroll_list native_scroll comp_snap_list" style="position: relative;">
                                <div class="ss_scroll_item comp_snap_item">
                                    <ol class="ss_shortcut_list">
                                        <li class="ss_shortcut_item" id = "news">
                                            <a class="shortcut_a" href="/bdm/news/doRetrieve.do" target="_blank" rel="noreferrer">
                                                <div class="sa_mw">
                                                    <img src="${CP }/resources/images/new_icon.png" class="sa_m" width="48" height="48" alt="">
                                                </div>
                                                <em class="sa_t">뉴스</em>
                                            </a>
                                        </li>
                                        <li class="ss_shortcut_item" id = "notice">
                                            <a class="shortcut_a" href="/bdm/notice/doRetrieve.do" target="_blank" rel="noreferrer">
                                                <div class="sa_mw">
                                                    <img src="${CP }/resources/images/notice_icon.png" class="sa_m" width="48" height="48" alt="">
                                                </div>
                                                <em class="sa_t">공지사항</em>
                                            </a>
                                        </li>
                                        <li class="ss_shortcut_item" id = "bulletin">
                                            <a class="shortcut_a" href="/bdm/bulletin/doRetrieve.do" target="_blank" rel="noreferrer">
                                                <div class="sa_mw">
                                                    <img src="${CP }/resources/images/board_icon.png" class="sa_m" width="48" height="48" alt="">
                                                </div>
                                                <em class="sa_t">게시판</em>
                                            </a>
                                        </li>
                                        <li class="ss_shortcut_item" id = "qna">
                                            <a class="shortcut_a" href="/bdm/qa/doRetrieve.do" target="_blank" rel="noreferrer">
                                                <div class="sa_mw">
                                                    <img src="${CP }/resources/images/qa_icon.png" class="sa_m" width="48" height="48" alt="">
                                                </div>
                                                <em class="sa_t">QnA</em>
                                            </a>
                                        </li>
                                        <li class="ss_shortcut_item" id = "userMonitor">
                                            <a class="shortcut_a" href="/bdm/beforeMain/moveToUserMonitor.do" target="_blank" rel="noreferrer">
                                                <div class="sa_mw">
                                                    <img src="${CP }/resources/images/user_m_icon.png" class="sa_m" width="48" height="48" alt="">
                                                </div>
                                                <em class="sa_t">유저관리</em>
                                            </a>
                                        </li>
                                        <li class="ss_shortcut_item" id = "mall">
                                            <a class="shortcut_a" href="" target="_blank" rel="noreferrer">
                                                <div class="sa_mw">
                                                    <img src="${CP }/resources/images/mall_icon.png" class="sa_m" width="48" height="48" alt="">
                                                </div>
                                                <em class="sa_t">꼬르륵몰</em>
                                            </a>
                                        </li>
                                    </ol>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>