<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
@import url("https://cdn.jsdelivr.net/gh/wanteddev/wanted-sans@v1.0.1/packages/wanted-sans/fonts/webfonts/variable/split/WantedSansVariable.min.css");

@font-face {
    font-family: 'KNUTRUTHTTF';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2312-1@1.1/KNUTRUTHTTF.woff2') format('woff2');
    font-weight: normal;
    font-style: normal;
}

#header {
    font-family: 'KNUTRUTHTTF';
    text-align: center;
    margin: auto;
    width: 80%; 
}

#header .headerbox {
    font-size: 30px;
    width: 48%;
    text-align: center;
    margin: auto;
    display: inline-block;
    cursor: pointer;
}

#header .custom-hr {
    background-color: rgba(168, 156, 200, 0.6);
    height: 2px;
    border: none;
    width: 100%;
    margin-top: 20px;
}

#header .flex-header {
    background-color: rgba(155, 178, 225, 0.6);
    color: #000000;
    height: 45px;
    line-height: 40px;
    display: inline-block;
    border-radius: 5px;
    width: 150px;
    font-size: 20px;
    margin-top: 10px;
    /* 추가된 스타일 */
}

#header .flex-header:hover {
    color: black;
    background-color: rgba(140, 201, 240, 0.6);
    /* 마우스 오버 시 글씨 색상을 검은색으로 변경 */
}

#header .blank_space {
    display: inline-block;
    width: 20%;
}

#header .sign_up_in {
    text-align: right;
    font-size: 18px;
    width: 20%;
    display: inline-block;
}

#header .sign_in {
    display: inline-block;
    border-right: black solid 2px;
}

#header .sign_in:hover {
    color: rgba(140, 201, 240, 1.0);
}

#header .sign_up {
    display: inline-block;
}

#header .sign_up:hover {
    color: rgba(239, 168, 176, 1.0);
}

#header .sign_up_in a {
    color: inherit;    /* 텍스트 색상을 상위 요소와 동일하게 설정 */
    text-decoration: none;    /* 밑줄 제거 */
}

#header .dropdown {
    position: relative;
    display: inline-block;
}

#header .dropdown-content {
    display: none;
    position: absolute;
    background-color: rgba(140, 201, 240, 1.0);
    min-width: 150px;
    box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);

    z-index: 1;
}

#header .dropdown-content a {
    color: black;
    padding: 12px 16px;
    text-decoration: none;
    border-bottom: 1px dotted #020202;
    border-top: 1px dotted #020202;
    display: block;
}

#header .dropdown:hover .dropdown-content {
    display: block;
}

/* section */
#section .s_section {
    text-align: center;
    width: 70%;
    margin: auto;
}

/* 왼쪽 section */
#section .left_section {
    border-right: 2px solid rgba(168, 156, 200, 0.6);
    display: inline-block;
    width: 20%;
    vertical-align: top;
}

/* 오른쪽 section */
#section .right_section {
    display: inline-block;
    width: 79%;
    text-align: left;
    font-family: "Wanted Sans Variable";
}

/* 공지사항, Q&A, 희망도서신청 */
#section .sub_but {
    width: 80%;
    font-size: 20px;
    font-family: 'KNUTRUTHTTF';
    margin-bottom: 10px;
    background-color: rgba(199, 156, 200, 0.6);
    border-radius: 5px;
    border: 0;
    padding: 10px;
    cursor: pointer;
    /* height: 40px; */
}

/* 왼쪽 section  마우스 오버 시 변경이벤트 */
#section .sub_but:hover {
    background-color: rgba(96, 59, 199, 0.6);
    color: white;
}
</style>

<%-- <title><tiles:insertAttribute name="title" /></title> --%>

</head>
<body>
	<div id="container">
		<div id="header">
			<tiles:insertAttribute name="header" />
		</div>

		<div id="section">
			<tiles:insertAttribute name="body" />
		</div>
	</div>
</body>
</html>