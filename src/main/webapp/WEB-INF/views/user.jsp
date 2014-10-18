<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>User Home</title>
</head>
<body>

	<P> ${cookie.LoginStatus.value} </P>

	<form name=frm1 method=get action=login>
		<input type=submit value="로그인">
	</form>
	
	<form name=frm2 method=get action=logout>
		<input type=submit value="로그아웃">
	</form>
	
	<form name=frm3 method=get action=signup>
		<input type=submit value="회원가입">
	</form>
	
	<form name=frm4 method=get action=signout>
		<input type=submit value="회원탈퇴">
	</form>


</body>
</html>