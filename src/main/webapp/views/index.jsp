<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />

<link href="<%=path%>/css/layout.css" type="text/css" rel="stylesheet" />

<script type="text/javascript">
	
</script>
</head>

<body>
	<jsp:include flush="true" page="/views/layout/header.jsp"></jsp:include>
	<div class="page_row">
		<div class="page_main_msg left">
			<jsp:include flush="true" page="/views/test/testlist.jsp"></jsp:include>
		</div>

		<div class="page_other_msg right">
			<jsp:include flush="true" page="/views/sidebar.jsp"></jsp:include>
		</div>
	</div>

	<div class="foot">
		<jsp:include flush="true" page="/views/layout/footer.jsp"></jsp:include>
	</div>
</body>
</html>
