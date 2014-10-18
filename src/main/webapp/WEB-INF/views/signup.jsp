<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>User Signup Page</title>
</head>
<body>

	<form name=frm1 method=post action=signup>

		<table border="1" bordercolor="#A9A9A9" cellspacing="0"
			cellpadding="3">

			<tr bgcolor="#F5F5F5">

				<td width=100><div align=right>
						<font size=3 color=orange>*</font>이메일
					</div></td>

				<td width=400><input type=text name=email1 size=20 maxlength=20>@
					<select name=email2>
						<option value="naver.com">네이버
						<option value="hanmail.net">다음
						<option value="gmail.com">구글
					</select>
				</td>


			</tr>

			<tr bgcolor="#F5F5F5">

				<td><div align=right>
						<font size=3 color=orange>*</font>비밀번호
					</div></td>

				<td><input type=password name=pw size=20 maxlength=20></td>

			</tr>
			
			<tr bgcolor="#F5F5F5">

				<td><div align=right>
						<font size=3 color=orange>*</font>닉네임
					</div></td>

				<td><input type=text name=nickname size=20 maxlength=15></td>

			</tr>

			<tr bgcolor="#F5F5F5">

				<td><div align=right>
						<font size=3 color=orange>*</font>성별
					</div></td>

				<td><input type=radio name=gender value=male size=20>남
					<input type=radio name=gender value=female size=20>여</td>

			</tr>
			
			<tr bgcolor="#F5F5F5">

				<td><div align=right>
						<font size=3 color=orange>*</font>나이
					</div></td>

				<td><input type=number name=age size=20></td>

			</tr>

			<tr>

				<td colspan="2"><div align=center>
						<input type=submit value="회원가입">
						<input type=reset value="취소">
					</div></td>

			</tr>

		</table>

	</form>

</body>
</html>