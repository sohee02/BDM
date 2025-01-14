<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<c:set var="CP" value = "${pageContext.request.contextPath}" scope = "page" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
</style>
<title></title>
<script src="${CP }/resources/js/jquery-3.7.1.js"></script>
<script src="${CP }/resources/js/eUtil.js"></script>  
<script>
document.addEventListener("DOMContentLoaded", function(){
	const cancleBtn = document.querySelector("#cancle");
	const doFindIdBtn = document.querySelector("#doFindId");
	const findIdForm = document.querySelector("#findIdFrm");
	
	cancleBtn.addEventListener("click", function(e){
		window.close();
	});
	
	doFindIdBtn.addEventListener("click", function(e){
		let name = findIdForm.name.value;
        if(eUtil.isEmpty(name)==true){
            alert('이름을 입력 하세요.');
            findIdForm.name.focus();
            return;
        }
        let birth = findIdForm.birth.value;
        if(eUtil.isEmpty(birth)==true){
            alert('생년월일 6자리를 입력 하세요.');
            findIdForm.birth.focus();
            return;
        }
        let email = findIdForm.email.value;
        if(eUtil.isEmpty(email)==true){
            alert('이메일을 입력 하세요.');
            findIdForm.email.focus();
            return;
        }
        
        $.ajax({
            type: "GET",
            url:"/bdm/user/doFindId.do",
            asyn:"true",
            dataType:"html",
            data:{
            	name: name,
            	birth: birth,
                email: email
            },
            success:function(data){//통신 성공     
               console.log("data:" + data);
               let parsedJSON = JSON.parse(data);
               if("1" === parsedJSON.msgId){
                   alert(parsedJSON.msgContents);
                   window.close();
               }else{
                   alert(parsedJSON.msgContents);
               }
            
            },
            error:function(data){//실패시 처리
                console.log("error:"+data);
            },
            complete:function(data){//성공/실패와 관계없이 수행!
                console.log("complete:"+data);
            }
        });
	});
});
</script>
</head>
<body>
    <div class = "container">
       <div class ="row">
           <div class = "col-lg-12">
               <h1 class = "page-header">아이디 찾기</h1>
           </div>
       </div>
         
         <!-- 회원 등록영역 -->  
         <div>
           <form action="#" name="findIdFrm" id = "findIdFrm">
               <div class = "p-div">
                   <label for="name" class="form-label">이름</label>
                   <input type="text"  class="form-control"  name="name" id="name" placeholder="이름을 입력 하세요." size="20"  maxlength="21">
               </div>         
               <div class="p-div">
                   <label for="login" class="form-label">생년월일</label>
                   <input type="text"  class="form-control" name="birth" id="birth" placeholder="앞 6자리만 입력하세요." size="20"  maxlength="6">
               </div>                 
               <div class="p-div">
                   <label for="email" class="form-label">이메일</label>
                   <input type="text"  class="form-control" name="email" id="email" placeholder="이메일을 입력하세요" size="20"  maxlength="320">
               </div>
           </form>
         </div>
         <!--// 회원 등록영역 ------------------------------------------------------>
         
           <!-- Button영역 -->
       <div class = "row justify-content-end">
           <div class = "col-auto">
               <input type="button" class="btn btn-primary" value="취소" id="cancle" >
               <input type="button" class="btn btn-primary" value="입력 완료" id="doFindId">
           </div>
       </div>
         <!--// Button영역 ------------------------------------------------------>
     </div>
</body>
</html>