<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>   
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>
<script src="http://code.jquery.com/jquery-3.1.1.min.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/mainClup.css">
<title>동아리 공지사항 작성</title>
</head>
<body>
	<div><jsp:include page="menu.jsp" /></div>
	<div class="all_page4">
		<div>
			<img id="mainImge" class="mainImg" src="display?fileName=${cvo.c_pic}">
		</div>
		<div class="up_left_page">
			<p class="mainTitle">동아리정보</p>
			<table class="c_Information">
				<tr>
					<td class="c_master"><i class="fas fa-crown"
						style="font-size: 25px; color: yellow;"></i></td>
					<td>${cvo.c_pid}</td>
				</tr>
				<tr>
					<td class="c_people"><i class="fas fa-male"
						style="font-size: 35px; margin-left: 8px; margin-right: 13px;"></i></td>
					<td>${maincount}</td>
				</tr>
				<tr>
					<td class="c_Area"><i class="fas fa-map-marker-alt"
						style="font-size: 25px; margin-left: 5px; margin-right: 5px;"></i></td>
					<td>${cvo.c_spot}</td>
				</tr>
			</table>
			<div id="btnJoin"></div>
		</div>
		<div class="right_page2">
		<form name="frm" action="createNotice" method="post">
		   	<div class="head-title">
				<textarea class="head-title-title"  placeholder="공지사항 제목을 입력해 주세요." name="cn_title" ></textarea>
				<h5 class="head-title-username">${id}</h5>
                <div style="text-align:right; ">
				    <span class="head-title-no">중요공지사항 <input type="checkbox" id="cn_bold" name="cn_bold" ></span> 
                    <span class="head-title-no">댓글 허용 게시물 <input type="checkbox" id="cn_rchk" name="cn_rchk"></span>
			    </div>
			</div>
			<div class="right_page2-cot">
				<textarea class="right_page2-cot-cot" placeholder="내용을 입력해 주세요." name="cn_content"></textarea>
			</div>
			<div class="btncnUpdate">
			<input type="submit" value="저장" class="cnUpdate"> 
			<input type="reset" value="취소" class="cnUpdate"> 
			</div>
		</form>
	</div>
	
	<div class="down_left_page">
		<p class="mainTitle">메뉴</p>
		<ul class="Kategorie">
			<li>
				<button onClick="location.href='club_first?c_code=${c_code}'" class="c_notice">동아리 메인</button>
			</li>
			<li>
				<button onClick="location.href='club_notice?c_code=${c_code}'" class="c_notice">공지사항</button>
			</li>
			<li>
				<button onClick="location.href='club_board?c_code=${c_code}'" class="c_free">자유게시판</button>
			</li>
			<c:if test="${clubMaster == 1}">
				<li>
					<button onClick="location.href='club_user'" class="c_notice">회원관리</button>
				</li>
				<li>
					<button onClick="location.href='updateClub'" class="c_free">동아리관리</button>
				</li>
			</c:if>
		</ul>
	</div>
	</div>
	<div><jsp:include page="footer.jsp" /></div>
</body>
<script>
      $("#cn_bold").change(function() {
         if ($("#cn_bold").is(":checked")) {
            $("#cn_bold").val(1);
         }else {
            $("#cn_bold").val(0);
         }
      });
   
      $("#cn_rchk").change(function() {
         if ($("#cn_rchk").is(":checked")) {
            $("#cn_rchk").val(1);
         }else {
            $("#cn_rchk").val(0);
         }
      });

  //서밋했을때
    $(frm).submit(function(e) {
       e.preventDefault();
       var cn_title=$(frm.cn_title).val();
       var cn_bold=$(frm.cn_bold).val();
       var cn_rchk=$(frm.cn_rchk).val();
       var cn_content=$(frm.cn_content).val();
       
       
       if ($(frm.cn_title).val() == "") {
          alert("글제목을 입력해주세요");
          $(frm.cn_title).focus();
       } else if ($(frm.cn_content).val() == "") {
          alert("내용을 입력해주세요");
          $(frm.cn_content).focus();
       } else {
          if (!confirm("입력하시겟습니까"))  return;
          frm.submit();
       }

    });
</script>


</html>

