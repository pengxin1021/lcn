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
				</div>
			</div>
		</nav>
	</div>

	<div class="search-div">
		<div class="col-lg-6">
			<form action="<%=request.getContextPath()%>/jsp/index.do" method="get" role="search">
				<div class="input-group">
					<input name="searchkey" type="text" class="form-control" placeholder="Search for...">
					<input name="sort" type="hidden" value="${sort}">
					<span class="input-group-btn">
						<button id="submitsearch" class="btn btn-default" type="submit">搜索</button>
					</span>
				</div>
			</form>
		</div>
	</div>
	
	<div class="panel panel-default search-result-div">
		<div class="panel-heading">最新资源</div>
		<table class="table">
			<thead>
				<tr>
					<th align="center">分享资源</th>
					<th align="center">分享用户</th>
					<th align="center">资源分类</th>
					<th align="center">获取时间</th>
				</tr>
			</thead>
			<c:forEach items="${requestScope.newList}" var="newList" varStatus="nl">
			<c:if test="${nl.index < 30}">
				<tr>
					<td align="left">
						<a target="blank" href="${newList.resourceUrl}">${newList.resourceName}</a>
					</td>
					<td align="left">
						<a target="blank" href="${newList.user.userHomeUrl}">${newList.user.userName}</a>
					</td>
					<td align="left">
						<c:if test="${newList.resourceSort == 6}">
							其他
						</c:if>
						<c:if test="${newList.resourceSort == 1}">
							视频
						</c:if>
						<c:if test="${newList.resourceSort == 2}">
							音乐
						</c:if>
						<c:if test="${newList.resourceSort == 3}">
							图片
						</c:if>
						<c:if test="${newList.resourceSort == 4}">
							文档
						</c:if>
						<c:if test="${newList.resourceSort == 5}">
							软件
						</c:if>
					</td>
					<td align="left">
						<fmt:formatDate value="${newList.createTime}" pattern="yyyy-MM-dd"/>
					</td>
				</tr>
			</c:if>
		</c:forEach>
		</table>
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