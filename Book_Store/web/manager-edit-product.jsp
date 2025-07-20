<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>RING!-MANAGEMENT - Chỉnh sửa sản phẩm</title>
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/font-awesome.min.css" rel="stylesheet">
        <link href="css/prettyPhoto.css" rel="stylesheet">
        <link href="css/price-range.css" rel="stylesheet">
        <link href="css/animate.css" rel="stylesheet">
        <link href="css/main.css" rel="stylesheet">
        <link href="css/responsive.css" rel="stylesheet">
        <!--[if lt IE 9]>
        <script src="js/html5shiv.js"></script>
        <script src="js/respond.min.js"></script>
        <![endif]-->       
        <link rel="shortcut icon" href="images/ico/favicon.ico">
        <link rel="apple-touch-icon-precomposed" sizes="144x144" href="images/ico/apple-touch-icon-144-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="114x114" href="images/ico/apple-touch-icon-114-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="72x72" href="images/ico/apple-touch-icon-72-precomposed.png">
        <link rel="apple-touch-icon-precomposed" href="images/ico/apple-touch-icon-57-precomposed.png">
        <script src="https://cdn.ckeditor.com/4.16.0/standard/ckeditor.js"></script>
    </head><!--/head-->

    <body>

        <jsp:include page="simpleNavigation.jsp"></jsp:include>

            <br><br><br>
            <div class="container">

            <c:if test="${managerMessage != null}">
                <div class="alert alert-success alert-dismissible" role="alert">
                    <span aria-hidden="true"><i class="fa fa-check"></i></span>
                        ${managerMessage}
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:if>

            <div class="row">
                <div class="col-sm-5 col-md-7">
                    <div class="manager-title">
                        <h2>Chỉnh sửa sản phẩm</h2>
                    </div>
                </div>

                <div class="col-sm-7 col-md-5">
                    <div class="manager_search_box">
                        <form action = "" method = "get">
                            <button type="submit" name="editBtn" form="editproductform" class="btn btn-default main-action pull-right"><i class="fa fa-edit">&nbsp;</i>Xác nhận</button>
                            <button type="button" onclick="location.href = 'manager'" class="btn btn-default main-action pull-right"><i class="fa fa-times">&nbsp;</i>Huỷ</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <br>
        <section id="cart_items">
            <div class="container">

                <div class="add-product-infomations">
                    <div class="row">
                        <div class="bill-to">
                            <div class="col-sm-12 clearfix">
                                <p style="margin-left: 30px;">Thông tin sản phẩm ID: ${book.id}</p>
                                <div class="form-two">
                                    <form id="editproductform" style="width: 200%" action="editproduct" method="post">
                                        <input type="hidden" name="pid" value="${book.id}">
                                        <section class="add-header">
                                            <input type="text" name="title" value="${book.title}" placeholder="Tiêu đề*" required>
                                            <input type="hidden" name="username" value="${sessionScope.loginedUser.username}">
                                            <select name="category" style="height: 150%" required>
                                                <option value="" disabled>-- Thể loại* --</option>
                                                <c:forEach items="${listCategory}" var="lCategory">
                                                    <option ${book.cateId == lCategory.cId ? "selected" : ""} value="${lCategory.cId}">${lCategory.cName}</option>
                                                </c:forEach>
                                            </select>
                                            <input type="text" name="image"  value="${book.image}" placeholder="Ảnh Bìa*" id="imageInput" required>
                                        </section>
                                        <textarea name="description" id="editor" placeholder="Mô tả sản phẩm*" rows="10" required>${book.description}</textarea>

                                    </form>

                                </div>
                            </div>	
                        </div>
                    </div>
                </div>

            </div>
        </section>

        <jsp:include page="footer.jsp"></jsp:include>

        <script src="js/jquery.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.scrollUp.min.js"></script>
        <script src="js/jquery.prettyPhoto.js"></script>
        <script src="js/main.js"></script>

        <script>
                                function myFunction() {
                                    var x = document.getElementById("imageInput").value;
                                    document.getElementById("imageOuput").src = x;
                                }
                                CKEDITOR.replace('editor');
        </script>
    </body>
</html>