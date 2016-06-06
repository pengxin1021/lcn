<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>LCN</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="<%=request.getContextPath()%>/media/css/bootstrap.css" rel='stylesheet' type='text/css' />
<link href="<%=request.getContextPath()%>/media/css/teal.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/media/css/style.css" rel='stylesheet' type='text/css' />
<link href="<%=request.getContextPath()%>/media/css/custom.css" rel='stylesheet' type='text/css' />
<script src="<%=request.getContextPath()%>/media/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/media/js/bootstrap.js"></script>

</head>
<body>
	<!-- 顶部导航栏 Teal Starts Here -->
	<%-- <div class="navbar">
		<div class="navbar-inner nav-teal">
			<a class="teal-brand" href="<%=request.getContextPath()%>/jsp/index.jsp">云盘搜索</a> 
			<!-- <span class="brand-describe">基于Lucene的百度云盘搜索引擎</span> -->
			<form action="<%=request.getContextPath()%>/jsp/index.do"
				method="get" class="navbar-form nacbar-style" role="search">
				<div class="form-group">
					<input name="searchkey" type="text" class="form-control"
						placeholder="输入关键词">
				</div>
				<button id="submitsearch" type="submit" class="btn btn-default">搜索</button>
			</form>
		</div>
	</div> --%>
	<!-- 顶部导航栏 Teal Ends Here -->

	<div class="header" id="home">
		<nav class="navbar navbar-default">
			<div class="container-fluid">
				<a class="teal-brand" href="<%=request.getContextPath()%>/jsp/home.do">云盘搜索</a>
				<div class="collapse navbar-collapse"
					id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav">
						<li class="active"><a href="home.do" class="hvr-bounce-to-top">首页 <span class="sr-only">(current)</span></a></li>
						<li><a href="home.do?sort=1" class="hvr-bounce-to-top">视频</a></li>
						<li><a href="home.do?sort=2" class="hvr-bounce-to-top">音乐</a></li>
						<li><a href="home.do?sort=3" class="hvr-bounce-to-top">图片</a></li>
						<li><a href="home.do?sort=4" class="hvr-bounce-to-top">文档</a></li>
						<li><a href="home.do?sort=5" class="hvr-bounce-to-top">软件</a></li>
						<li><a href="home.do?sort=6" class="hvr-bounce-to-top">其它</a></li>
					</ul>
					<form action="<%=request.getContextPath()%>/jsp/index.do" method="get" class="navbar-form nacbar-style" role="search">
						<div class="form-group">
							<input name="searchkey" type="text" class="form-control" placeholder="输入关键词">
							<input name="sort" type="hidden" value="${sort}">
						</div>
						<button id="submitsearch" type="submit" class="btn btn-default">搜索</button>
					</form>
					<div class="clearfix">
						
					</div>
				</div>
				
				<!-- /.navbar-collapse -->
			</div>
			<!-- /.container-fluid -->
		</nav>
	</div>
	
	<div class="panel panel-default search-result-div">
		<div class="panel-heading">搜索结果</div>
		<table class="table">
			<thead>
				<tr>
					<th align="center">分享资源</th>
					<th align="center">分享用户</th>
					<th align="center">资源分类</th>
					<th align="center">获取时间</th>
				</tr>
			</thead>
			<c:forEach items="${requestScope.shareResourceList}" var="shareResourceList" varStatus="srl">
			<c:if test="${srl.index < 30}">
				<tr>
					<td align="left">
						<a target="blank" href="${shareResourceList.resourceUrl}">${shareResourceList.resourceName}</a>
					</td>
					<td align="left">
						<a target="blank" href="${shareResourceList.user.userHomeUrl}">${shareResourceList.user.userName}</a>
					</td>
					<td align="left">
						<c:if test="${shareResourceList.resourceSort == 6}">
							其他
						</c:if>
						<c:if test="${shareResourceList.resourceSort == 1}">
							视频
						</c:if>
						<c:if test="${shareResourceList.resourceSort == 2}">
							音乐
						</c:if>
						<c:if test="${shareResourceList.resourceSort == 3}">
							图片
						</c:if>
						<c:if test="${shareResourceList.resourceSort == 4}">
							文档
						</c:if>
						<c:if test="${shareResourceList.resourceSort == 5}">
							软件
						</c:if>
					</td>
					<td align="left">
						<fmt:formatDate value="${shareResourceList.createTime}" pattern="yyyy/MM/dd"/>
					</td>
				</tr>
			</c:if>
		</c:forEach>
		</table>
		<nav>
		<!-- <ul class="pagination">
			<li>
				<a href="#" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a>
			</li>
			<li><a href="#">1</a></li>
			<li><a href="#">2</a></li>
			<li><a href="#">3</a></li>
			<li><a href="#">4</a></li>
			<li><a href="#">5</a></li>
			<li>
				<a href="#" aria-label="Next"> <span aria-hidden="true">&raquo;</span></a>
			</li>
		</ul>
	</nav> -->
	</div>
	
	<!--start-footer-->
	<div class="footer">
		<div class="container">
			<div class="footer-main">
				<div class="col-md-4 footer-left">
					<!-- <span class="glyphicon glyphicon-map-marker map-marker"
						aria-hidden="true"></span> -->
					<p>
						<span>基于Lucene的云盘搜索引擎</span>
					</p>
				</div>
				<div class="col-md-4 footer-left">
					<!-- <span class="glyphicon glyphicon-phone map-marker"
						aria-hidden="true"></span>
					<p> </p> -->
				</div>
				<div class="col-md-4 footer-left">
					<p class="footer-class">
						<a href="#" target="_blank" title="联系我们">联系我们</a><br>
						<a href="#" target="_blank" title="联系我们">pengxin1021@foxmail.com</a>
					</p>
					<ul>
						<li><a href="#"><span class="fb"></span></a></li>
						<li><a href="#"><span class="twit"></span></a></li>
						<li><a href="#"><span class="rss"></span></a></li>
						<li><a href="#"><span class="ggl"></span></a></li>
					</ul>
				</div>
				<div class="clearfix"></div>
			</div>
		</div>
		<script type="text/javascript">
			$(document).ready(function() {
				var defaults = {
					containerID: 'toTop', // fading element id
					containerHoverID: 'toTopHover', // fading element hover id
					scrollSpeed: 1200,
					easingType: 'linear' 
				};

			});
		</script>
		<a href="#home" id="toTop" class="scroll" style="display: block;">
			<span id="toTopHover" style="opacity: 1;"> </span>
		</a>
	</div>
	<!--end-footer-->
</body>
</html>