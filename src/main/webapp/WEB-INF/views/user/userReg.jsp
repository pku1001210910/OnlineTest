<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base target="_self" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<link href="<%=path%>/css/bootstrap.min.css" rel="stylesheet">

<!-- support ie8 & ie9 & ie10 -->
<script src="<%=path%>/js/html5shiv.js" type="text/javascript"></script>
<script src="<%=path%>/js/respond.min.js" type="text/javascript"></script>
<script src="<%=path%>/js/ie10-viewport-bug-workaround.js" type="text/javascript"></script>
<script src="<%=path%>/js/ie-elements.js" type="text/javascript"></script>

<script language="javascript">
	function closeOpen() {
		window.returnValue = false;
		window.close();
	}
	function check1() {
		if (document.form1.userName.value == "") {
			alert("请输入用户名");
			return false;
		}
		if (document.form1.userPw.value == "") {
			alert("请输入密码");
			return false;
		}
		document.form1.submit();
	}
</script>
</head>
<body>
	<form action="<%=path%>/userReg.action" name="form1" method="post">
		<table width="98%" border="0" align="center" cellpadding="0"
			cellspacing="1" bgcolor="#CCCCCC">
			<tr>
				<th height="40" colspan="2" bgcolor="#FFFFFF" class="f12b-red"
					style="font-size: 11px;">用 户 注 册</th>
			</tr>
			<tr>
				<td width="20%" height="30" align="right" bgcolor="#F9F9F9"
					style="font-size: 11px;">用户名：</td>
				<td width="80%" bgcolor="#FFFFFF">&nbsp; <input type="text"
					name="userName" id="userName" />
				</td>
			</tr>
			<tr>
				<td height="30" align="right" bgcolor="#F9F9F9"
					style="font-size: 11px;">密 码：</td>
				<td bgcolor="#FFFFFF">&nbsp; <input type="password"
					name="userPw" />
				</td>
			</tr>
			<tr>
				<td height="30" align="right" bgcolor="#F9F9F9"
					style="font-size: 11px;">真实姓名：</td>
				<td bgcolor="#FFFFFF">&nbsp; <input type="text"
					name="userRealname" />
				</td>
			</tr>
			<tr>
				<td height="30" align="right" bgcolor="#F9F9F9"
					style="font-size: 11px;">年龄：</td>
				<td bgcolor="#FFFFFF">&nbsp; <input type="text"
					name="userEmail" />
				</td>
			</tr>
			<tr>
				<td height="30" align="right" bgcolor="#F9F9F9"
					style="font-size: 11px;">性别：</td>
				<td bgcolor="#FFFFFF">&nbsp; <input type="radio" name="userSex"
					value="男" checked="checked" />男 &nbsp;&nbsp;&nbsp;&nbsp; <input
					type="radio" name="userSex" value="女" />女
				</td>
			</tr>
			<tr>
				<td height="30" align="right" bgcolor="#F9F9F9"
					style="font-size: 11px;">身份证号码：</td>
				<td bgcolor="#FFFFFF">&nbsp; <input type="text" name="userQq" />
				</td>
			</tr>
			<tr>
				<td height="30" align="right" bgcolor="#F9F9F9"
					style="font-size: 11px;">住址：</td>
				<td bgcolor="#FFFFFF">&nbsp; <input type="text"
					name="userAddress" />
				</td>
			</tr>

			<tr>
				<td height="30" align="right" bgcolor="#F9F9F9"
					style="font-size: 11px;">联系方式：</td>
				<td bgcolor="#FFFFFF">&nbsp; <input type="text" name="userTel" />
				</td>
			</tr>


			<tr>
				<td height="30" align="right" bgcolor="#F9F9F9">&nbsp;</td>
				<td bgcolor="#FFFFFF">&nbsp; <input type="button" value="确定"
					onclick="check1();" /> <input type="button" value="取消"
					onclick="closeOpen()" />
				</td>
			</tr>
		</table>
	</form>

	<!-- JavaScript -->
	<script src="<%=path%>/js/jquery.min.js"></script>
	<script src="<%=path%>/js/bootstrap.min.js"></script>
</body>
</html>
