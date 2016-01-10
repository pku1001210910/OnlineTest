<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
</head>

<body>
	<div class="left_row">
		<div class="list pic_news">
			<div class="list_bar">问卷信息列表</div>
			<div class="list_content" style="height: 445px;">
				<div class="c1-body">
					<s:iterator value="#request.wenjuanList" id="wenjuan">
						<div class="c1-bline" style="padding: 7px 0px;">
							<div class="f-left">
								<img src="<%=path%>/img/head-mark4.gif" align="middle"
									class="img-vm" border="0" /> <a href="#"
									onclick="wenjuanDetailQian(<s:property value="#wenjuan.id"/>)"><s:property
										value="#wenjuan.mingcheng" /></a>
							</div>
							<div class="f-right">
								<s:property value="#wenjuan.shijian" />
							</div>
							<div class="clear"></div>
						</div>
					</s:iterator>
					<div class="pg-3">
						<!--  分页-->
					</div>
				</div>
			</div>
		</div>
		<div style="clear: both;"></div>
	</div>
</body>
</html>
