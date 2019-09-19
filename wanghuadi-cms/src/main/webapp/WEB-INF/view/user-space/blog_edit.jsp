<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>发布文章</title>

    <!-- Bootstrap -->
    <link rel="stylesheet" type="text/css" href="/libs/bootstrap/css/bootstrap.min.css"/>
    <script src="//cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.5/umd/popper.js"></script>
	<link href="/libs/bootstrap/summernote/summernote.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="/css/cms.css"/>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
     <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style type="text/css">
    </style>
  </head>
  <body>
    <jsp:include page="/WEB-INF/inc/top.jsp"></jsp:include>
	
	<!-- 横幅 -->
	<div class="container">
		<div class="row">
			<div class="col-xs-12 my_banner">
			</div>
		</div>
	</div>
	<br/>
	<!-- 主体内容区 -->
	<div class="container">
		<div class="row">
			<div class="col-md-3">
				<jsp:include page="/WEB-INF/inc/my_left.jsp"><jsp:param value="blog" name="module"/></jsp:include>
			</div>
			<div class="col-md-9">
				<div class="panel panel-default">
				  <div class="panel-body">
				    	<h1>发布文章</h1>
				    	<hr/>
				    	
				    	<form:form modelAttribute="blog" action="/my/blog/save" enctype="multipart/form-data" method="post" >
				    	<%-- <form:hidden path="id"/>
				    	<p align="center" class="red"><form:errors path="id"/> </p> --%>
				    	<p>
				    		<form:input path="title" class="form-control" placeholder="文章标题"/>
				    		<%-- <span class="red"><form:errors path="title"/></span> --%>
				    	</p>
				    	
				    	<p>
				    		<form:textarea path="content" id="content" rows="20" class="form-control"></form:textarea>
				    		<span class="red"><form:errors path="content"/></span>
				    	</p>
				    	
				    	<p>
				    		<form:textarea path="summary" rows="3" class="form-control" placeholder="摘要"></form:textarea>
				    		<span class="red"><form:errors path="summary"/></span>
				    	</p>
				    	
				    	<p>
				    		<select class="form-control" id="channelId" name="channel.id">
				    			<option value="0">选择栏目</option>
				    			<c:forEach items="${channelList }" var="channel">
				    				<option value="${channel.id }" <c:if test="${channel.id == blog.channel.id  }">selected="selected"</c:if>>${channel.name }</option>
				    			</c:forEach>
				    		</select>
				    	
				    		<select class="form-control" id="cateId" name="category.id">
				    			<option value="0">选择分类</option>
				    			<c:forEach items="${channelList }" var="channel">
					    			<c:forEach items="${channel.categoryList }" var="cate">
					    					<option value="${cate.id }" <c:if test="${cate.id == blog.category.id  }">selected="selected"</c:if>>${cate.name }</option>
					    			</c:forEach>
				    			</c:forEach>
				    		</select>
				    		<span class="red"><form:errors path="category"/></span>
				    	</p>
				    	
				    	<p>上传封面：<input type="file" name="file"/>
				    		<c:if test="${not empty blog.picture}">
					    		<img alt="图片预览" src="lookImg?path=${blog.picture}" height="50">
					    		<form:hidden path="picture"/>
					    	</c:if>
				    	</p>
				    	
				    	
				    	<p>
				    	<input type="checkbox" name="hots">是否上热门
				    	</p>
				    	<p>
				    		<button type="submit" class="btn btn-info btn-block">发布</button> 
				    	</p>
				    	
				    	</form:form>
				  </div>
				</div>
				
			</div>
		</div>
	</div>
	
	<jsp:include page="/WEB-INF/inc/footer.jsp"/>
	
	<script src="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.9/summernote.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$("#content").summernote({
				placeholder:'博客内容',
				height:300
			});
		});
	</script>
	<script type="text/javascript">
		
		$(function(){
			//页面加载时给栏目下拉绑定 onchang事件
			$("#channelId").change(function(){
				var channelid = $(this).val();
				
				//动态遍历分类下拉
				$.post("/my/channelCategory/queryCategoryByChannelId",{"channelId":channelid},function(data){

					//动态添加分类信息
					var str = "<option value='0'>选择分类</option>";
					//遍历分类信息
					for(var i in data){
						str+="<option value='"+data[i].id+"'>"+data[i].name+"</option>";
					}
					
					//将拼接的动态下拉 设置下拉框内容
					$("#cateId").html(str);
					
				},"json");
				
			})
		})
	
	</script>
  </body>
</html>