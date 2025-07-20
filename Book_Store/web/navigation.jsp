<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<header id="header"><!--header--><!--/header_top-->

    <!--    <div class="header_top">header_top
            <div class="container">
                <div class="row">
                    <div class="col-sm-6 ">
                        <div class="contactinfo">
                            <ul class="nav nav-pills">
                                <li><a href=""><i class="fa fa-phone"></i> +8913662885</a></li>
                                <li><a href=""><i class="fa fa-envelope"></i> baotqhe172425@fpt.edu.vn </a></li>
                            </ul>
                        </div>
                    </div>
                    <div class="col-sm-6">
                        <div class="social-icons pull-right">
                            <ul class="nav navbar-nav">
                                <li><a href="https://www.facebook.com/profile.php?id=100009442934553"><i class="fa fa-facebook"></i></a></li>
                                <li><a href="https://www.instagram.com/baotq0612/"><i class="fa fa-instagram"></i></a></li>
                                <li><a href="https://join.skype.com/invite/vVrqv3cLalXs"><i class="fa fa-skype"></i></a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>-->

    <div class="header-middle"><!--header-middle-->
        <div class="container">
            <div class="MainLogo row">
                <div class="MainSearch">
                    <div class="logo pull-left">
                        <div class="mainlogo">
                            <h2><a href="home"><span><img src="images/home/bell.png" alt="" /> WEB danh gia</span></h2>
                        </div>
                    </div>
                    <div>
                        <div class="search_box pull-right">
                            <form action = "search" method = "get">
                                <input name = "searchBar" type="text" value = "${searchBarString}" placeholder="Tìm kiếm ..."/>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="col-sm-8">
                    <div class="shop-menu pull-right">
                        <ul class="nav navbar-nav">


                            <c:if test = "${sessionScope.loginedUser != null}"> <!-- logined -->
                                <li><a href=""><i class="fa fa-user"></i> ${sessionScope.loginedUser.username}</a></li>
                                <li><a href="logout"><i class="fa fa-sign-out"></i> Đăng xuất</a></li>
                                </c:if>

                            <c:if test = "${sessionScope.loginedUser == null}"> <!-- guest -->
                                <li><a href="login"><i class="fa fa-lock"></i> Đăng nhập</a></li>
                                </c:if>

                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div><!--/header-middle-->

    <div class="header-bottom"><!--header-bottom-->
        <div class="container">
            <div class="row">
                <div class="col-sm-9">
                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                            <span class="sr-only">Toggle navigation</span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                    </div>
                    <div class="mainmenu pull-left">
                        <ul class="nav navbar-nav collapse navbar-collapse">


                            <c:if test = "${sessionScope.loginedUser.isSeller == 1}"> <!-- manager stuff -->
                                <li><a href="manager">Quản lý bài đăng</a></li>
                                </c:if>

                            <c:if test = "${sessionScope.loginedUser.isAdmin == 1}"> <!-- manager stuff -->
                                <li><a href="manageCategory">Quản lý danh mục</a></li>
                                </c:if>

                            <c:if test = "${sessionScope.loginedUser.isAdmin == 1}"> <!-- admin stuff -->
                                <li><a href="admin">Admin</a></li>
                                </c:if>
                        </ul>
                    </div>
                </div>

            </div>
        </div>
    </div>
</header>