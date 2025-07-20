/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Model.Account;
import Model.Cart;
import Model.Item;
import Model.Order;
import Model.OrderDetail;
import dao.BookDAO;
import dao.OrderDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class OrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        Account user = (Account) request.getSession().getAttribute("loginedUser");
        Cart cart = (Cart) request.getSession().getAttribute("cart");

        String firstName = "";
        String lastName = "";
        String fullName = "";
        String email = "";
        String phone = "";
        String city = "";
        String state = "";
        String address = "";
        String fullAddress = "";
        String message = "";

        firstName = request.getParameter("fName");
        lastName = request.getParameter("lName");
        email = request.getParameter("email");
        phone = request.getParameter("phone");
        city = request.getParameter("city");
        state = request.getParameter("state");
        address = request.getParameter("address");
        message = request.getParameter("message");

        if (request.getParameter("submit-checkout") != null && cart != null) {

            if (firstName == "" || lastName == "" || email == "" || phone == ""
                    || city == "" || state == "" || address == "") {

                request.setAttribute("checkOutMessage", "Vui lòng nhận thông tin người nhận !");
                request.getRequestDispatcher("/checkout.jsp").forward(request, response);
            } else {

                fullName = firstName + " " + lastName;
                fullAddress = city + "/ " + state + "/ " + address;

                Date date = new Date();
                Order order = null;

                if (user != null) {

                    order = new Order(-1, user, fullName, email, phone, fullAddress, message, new Timestamp(date.getTime()), cart.getCartTotalPrice() + cart.getCartTotalPrice() * 0.1);
                } else {

                    order = new Order(-1, null, fullName, email, phone, fullAddress, message, new Timestamp(date.getTime()), cart.getCartTotalPrice() + cart.getCartTotalPrice() * 0.1);
                }

                for (Item i : cart.getItems()) {

                    OrderDetail orderDetail = new OrderDetail(i.getBook(), i.getQuantity(), i.getPrice());
                    BookDAO bookDAO=new BookDAO();
                    try {
                        bookDAO.updateQuantityProduct(i.getBook().getId(), i.getQuantity());
                    } catch (Exception ex) {
                        Logger.getLogger(OrderServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    order.addDetails(orderDetail);
                }

                OrderDAO orderDAO = new OrderDAO();
                orderDAO.checkout(order);

                cart.clearItems();

                
                try {

                } catch (Exception e) {
                    e.printStackTrace();
                }

                request.setAttribute("checkOutMessage", "Đặt hàng thành công !");
                request.getRequestDispatcher("/cart.jsp").forward(request, response);
            }
        } else {

            response.sendRedirect("cart"); //tráº£ vá»� trang cart
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
