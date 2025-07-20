/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Model.Account;
import Model.Book;
import Model.OrderDetail;
import dao.AccountDAO;
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
import java.util.List;

/**
 *
 * @author admin
 */
public class AdminController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        //b1: lấy data trong DAO
        BookDAO bookDAO = new BookDAO();
        AccountDAO accountDAO = new AccountDAO();
        OrderDAO orderDAO = new OrderDAO();

        String isChart = request.getParameter("isChart");
        String isTable = request.getParameter("isTable");

        HttpSession session = request.getSession();
        Account user = (Account) session.getAttribute("loginedUser");

        if (user != null && user.getIsAdmin() == 1) { //lÃ  admin?

            if (isChart != null && Integer.parseInt(isChart) == 1) { //Chart

                List<Account> listAccount = accountDAO.getTop5Seller();
                List<OrderDetail> listOrderByMonths = orderDAO.getOrderByMonths();
                List<Book> listOrderByBooks = orderDAO.getOrderByBooks();

                request.setAttribute("listAccount", listAccount);
                request.setAttribute("listOrderByMonths", listOrderByMonths);
                request.setAttribute("listOrderByBooks", listOrderByBooks);
                request.getRequestDispatcher("/chart.jsp").forward(request, response);
            } else if (isTable != null && Integer.parseInt(isTable) == 1) { //Table

                List<Book> listBook = bookDAO.getAllBook();
                List<Account> listAccount = accountDAO.getAllAccount();

                request.setAttribute("listBook", listBook);
                request.setAttribute("listAccount", listAccount);
                request.getRequestDispatcher("/table.jsp").forward(request, response);
            } else { //Dashboard

                List<Book> listBook = bookDAO.getAllBook();
                List<Account> listAccount = accountDAO.getAllAccount();
                List<OrderDetail> listOrderByMonths = orderDAO.getOrderByMonths();
                List<Book> listOrderByBooks = orderDAO.getOrderByBooks();

                //b2: set data vào jsp
                request.setAttribute("listBook", listBook);
                request.setAttribute("listAccount", listAccount);
                request.setAttribute("listOrderByMonths", listOrderByMonths);
                request.setAttribute("listOrderByBooks", listOrderByBooks);
                request.getRequestDispatcher("/admin.jsp").forward(request, response);
            }

        } else {

            response.sendRedirect("home");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
