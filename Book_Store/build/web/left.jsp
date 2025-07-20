<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="col-sm-3">
	<div class="left-sidebar">
		<h2>Danh sách sản phẩm</h2>
		<div class="panel-group category-products" id="accordian"><!--category-productsr-->
		
		<c:forEach items = "${listCategory}" var = "category">
			
			<c:if test="${category.subCate.size() != 0}">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" data-parent="#accordian" href="#${category.cId}">
								<span class="badge pull-right"><i class="fa fa-plus"></i></span>
								<a href="category?cid=${category.cId}">${category.cName}</a>
							</a>
						</h4>
					</div>
					<div id="${category.cId}" class="panel-collapse collapse">
						<div class="panel-body">
							<ul>
								<c:forEach items="${category.subCate}" var="sub">
									<li><a href="category?cid=${category.cId}">${sub.sName}</a></li>
								</c:forEach>
							</ul>
						</div>
					</div>
				</div>
			</c:if>
			
			<c:if test="${category.subCate.size() == 0}">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title"><a href="category?cid=${category.cId}">${category.cName}</a></h4>
					</div>
				</div>
			</c:if>
		</c:forEach>
		</div><!--/category-productsr-->
	
		
		<br><br>
	</div>
</div>