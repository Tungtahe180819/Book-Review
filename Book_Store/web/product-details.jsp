<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String pageParam = request.getParameter("page");
    int currentPage = (pageParam != null && !pageParam.isEmpty()) ? Integer.parseInt(pageParam) : 1;
    int reviewCoint = (Integer) request.getAttribute("reviewCoint");
    request.setAttribute("currentPage", currentPage);
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>RING! - ${book.title}</title>
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/font-awesome.min.css" rel="stylesheet">
        <link href="css/prettyPhoto.css" rel="stylesheet">
        <link href="css/price-range.css" rel="stylesheet">
        <link href="css/animate.css" rel="stylesheet">
        <link href="css/main.css" rel="stylesheet">
        <link href="css/responsive.css" rel="stylesheet">
        <link rel="shortcut icon" href="images/ico/favicon.ico">
        <link rel="apple-touch-icon-precomposed" sizes="144x144" href="images/ico/apple-touch-icon-144-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="114x114" href="images/ico/apple-touch-icon-114-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="72x72" href="images/ico/apple-touch-icon-72-precomposed.png">
        <link rel="apple-touch-icon-precomposed" href="images/ico/apple-touch-icon-57-precomposed.png">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-icons/1.10.0/font/bootstrap-icons.min.css">

    </head><!--/head-->

    <body>

        <jsp:include page="navigation.jsp"></jsp:include>

            <section>
                <div class="container">
                    <div class="row">
                        <h2>${book.title}</h2>
                    <section>
                        <section class="info-header">
                            <i class="bi bi-person"></i>
                            <p><strong>${book.author}</strong></p>
                            <p><fmt:formatDate value="${bookDetail.date}" pattern="dd 'Tháng' MM, yyyy" /></p>
                            <p>${reviewCount} đánh giá</p>  

                        </section>
                        <section>
                            <p>${book.description}</p>
                        </section>
                        <hr/>
                        <c:if test = "${sessionScope.loginedUser != null}">
                            <p><b>Để lại đánh giá của bạn</b></p>

                            <form action="review">
                                <textarea name="content" required></textarea>
                                <input type="hidden" name="pid" value="${book.id}"></input>
                                <span class="pull-left">
                                    <b>Đánh giá:  </b> 
                                    <select class="rating-control" id="rating-dropdown" name="rating">
                                        <option value = "1" id = "rating-option">1</option> 
                                        <option value = "2" id = "rating-option">2</option>
                                        <option value = "3" id = "rating-option">3</option>
                                        <option value = "4" id = "rating-option">4</option>
                                        <option value = "5"  id = "rating-option" selected>5</option>
                                    </select>
                                </span>
                                <button type="submit" class="btn btn-default pull-right">
                                    Gửi đánh giá
                                </button>
                            </form>
                        </c:if>

                        <c:if test = "${sessionScope.loginedUser == null}">
                            <button type="button" onclick="location.href = 'login'" class="btn btn-default pull-center">
                                Đăng nhập để đánh giá sản phẩm
                            </button>
                        </c:if>


                        <div class="tab-pane fade active in" id="reviews" >
                        
                            <hr/>
                            <div class="col-sm-12">
                                <c:forEach items = "${listReview}" var = "review">
                                    <ul>
                                        <li><a href=""><i class="fa fa-user"></i>${review.username}</a></li>
                                        <li><a><i class="fa fa-clock-o"></i>${review.getTimeString()}</a></li>
                                        <li><a><i class="fa fa-calendar-o"></i>${review.getDateString()}</a></li>
                                        <li class="pull-right"><a><i class="fa fa-star"></i>${review.rating}</a></li>
                                    </ul>
                                    <p>${review.content}</p>
                                    <br><br>
                                    <hr/>
                                </c:forEach>

                                <div class="pagination-container text-center">
                                    <ul class="pagination">
                                        <li class="page-item">
                                            <a class="page-link" href="./details?page=${currentPage - 1 <= 1? 1:currentPage - 1}&pid=${bookID}""id="prevBtn">Previous</a>
                                        </li>
                                        <c:forEach begin="1" end="${(reviewCount + 5) / 6}" var="page">
                                            <li class="page-item">
                                                <a class="page-link" href="./details?page=${page}&pid=${bookID}">${page}</a>
                                            </li>
                                        </c:forEach>
                                        <li class="page-item">
                                            <a class="page-link" href="./details?page=${ currentPage + 1}&pid=${bookID}"  id="nextBtn">Next</a>
                                        </li>
                                    </ul>
                                </div>


                            </div>
                        </div>
                    </section>
                </div>
            </div>
        </section>

        <jsp:include page="footer.jsp"></jsp:include>

        <script src="js/jquery.js"></script>
        <script src="js/price-range.js"></script>
        <script src="js/jquery.scrollUp.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.prettyPhoto.js"></script>
        <script src="js/main.js"></script>

        <script type="text/javascript">
                                var i = 1;
                                function quantityChange(n) {
                                    var quantity = document.getElementById("quantityInput");
                                    i = i + n;
                                    if (i < 1) {

                                        i = 1;
                                    }
                                    quantity.value = i;
                                }
                                ;

                                function addToCart(pid, pquantity) {
                                    $.ajax({
                                        type: 'GET',
                                        url: 'addtocart?pid=' + pid + '&pquantity=' + pquantity + '&type=ajax',
                                        header: {
                                            Accept: "application/json; charset=utf-8",
                                            "Cotent-Type": "application/json; charset=utf-8"
                                        },
                                        success: function (result) {
                                            var status = $.parseJSON(result);
                                            $("#cart-icon").html('<i class="fa fa-shopping-cart"></i> Giỏ hàng (' + status + ')');
                                            $(".popup").show();
                                            $(window).scrollTop(0);
                                        }
                                    });
                                }
        </script>
    </body>
</html>