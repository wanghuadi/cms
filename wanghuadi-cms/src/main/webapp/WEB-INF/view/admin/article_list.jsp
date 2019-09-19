<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">

  <head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="howsun">

    <title>CMS后台管理系统</title>

    <!-- Bootstrap core CSS-->
    <link href="/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom fonts for this template-->
    <link href="/libs/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">

    <!-- Custom styles for this template-->
    <link href="/libs/sb-admin/sb-admin.css" rel="stylesheet">

	<script type="text/javascript">
		function shenhe(id,status){
			$.post("/admin/blog/updateByStatus",{"id":id,"status":status},function(data){
				
				if(data){
					alert("文章审核成功!");
					location.reload();
				}
			},"json");
		}
	</script>

  </head>

  <body id="page-top">

	<!-- 后台管理系统顶部 -->
 	<jsp:include page="_inc_top.jsp"/>

    <div id="wrapper">

 		<!-- 后台管理系统左部菜单 -->
 		<jsp:include page="_inc_left.jsp"/>

      <div id="content-wrapper">

        <div class="container-fluid">

          <!-- Breadcrumbs-->
          <ol class="breadcrumb">
            <li class="breadcrumb-item">
              <a href="/admin/index">后台首页</a>
            </li>
            <li class="breadcrumb-item active">概览</li>
          </ol>

          <!-- Icon Cards-->
          <br/>
          <br/>
         
         	
         <!-- 文章审核列表页面 -->
         <table  class="table" style="width: 1150px;font-size: 15px">
         	<tr>
         		<td>文章编号</td>
         		<td>文章标题</td>
         		<td>文章内容</td>
         		<td>发布人</td>
         		<td>发布时间</td>
         		<td>审核状态</td>
         		<td>操作</td>
         	</tr>
         
         <c:forEach items="${articleList }" var="a">
			   <tr>
         		<td>${a.id }</td>
         		<td>${a.title }</td>
         		<td>${a.content }</td>
         		<td>${a.author.nickname }</td>
         		<td>${a.created }</td>
         		<td>${a.status== null?"待审核":a.status>0?"已审核":"未通过" }</td>
         		<td>
         			<c:choose>
         				<c:when test="${a.status == 1 }">
         					<input type="button" class="btn btn-success" style="font-size: x-small;" value="上热门">
         					<input type="button" class="btn btn-danger" style="font-size: x-small;" value="取消热门">
         				</c:when>
         				<c:otherwise>
         					<input type="button" class="btn btn-success" style="font-size: x-small;" value="通过" onclick="shenhe('${a.id }',1)">
         					<input type="button" class="btn btn-danger"style="font-size: x-small;"  value="未通过" onclick="shenhe('${a.id }',0)">
         				</c:otherwise>
         			</c:choose>
         		</td>
         	</tr>
         </c:forEach>
         
         </table>
         
         
         
         <a>首页</a>
         <a href="?page=${page.pageIndex-1 }">上一页</a>
         <a href="?page=${page.pageIndex+1 }">下一页</a>
         <a>尾页</a>
         

        </div>
        <!-- /.container-fluid -->

        <!-- Sticky Footer -->
        <footer class="sticky-footer">
          <div class="container my-auto">
            <div class="copyright text-center my-auto">
              <span>Copyright Â© Your Website 2019</span>
            </div>
          </div>
        </footer>

      </div>
      <!-- /.content-wrapper -->

    </div>
    <!-- /#wrapper -->

    <!-- Scroll to Top Button-->
    <a class="scroll-to-top rounded" href="#page-top">
      <i class="fas fa-angle-up"></i>
    </a>

    <!-- Bootstrap core JavaScript-->
    <script src="/libs/jquery/jquery.min.js"></script>
    <script src="/libs/bootstrap/js/bootstrap.bundle.min.js"></script>

    <!-- Custom scripts for all pages-->
    <script src="/libs/sb-admin/sb-admin.min.js"></script>
  </body>

</html>
