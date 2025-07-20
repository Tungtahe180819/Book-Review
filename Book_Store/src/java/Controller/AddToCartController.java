/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Model.Book;
import Model.Cart;
import Model.Item;
import com.google.gson.Gson;
import dao.BookDAO;
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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class AddToCartController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        BookDAO dao = new BookDAO();

        response.setContentType("text/html;charset=UTF-8");

        String bookID = request.getParameter("pid");
        String currentURL = request.getHeader("referer");

        HttpSession session = request.getSession();

        int quantity = 1; // máº·c Ä‘á»‹nh 1

        if (bookID != null) {
            Book book = dao.getBookById(bookID);

            if (book != null) {

                if (request.getParameter("pquantity") != null) { //thÃªm nhiá»�u sÃ¡ch

                    quantity = Integer.parseInt(request.getParameter("pquantity"));
                }

                if (session.getAttribute("cart") == null) { //chÆ°a cÃ³ giá»� hÃ ng

                    Cart cart = new Cart(); //táº¡o giá»�
                    List<Item> list = new ArrayList<Item>();
                    Item item = new Item(book.getId(), book, quantity, book.getPrice());
                    list.add(item);
                    cart.setItems(list); //thêm sản phẩm vào giá

                    cart.updateStatus(); //update sá»‘ lÆ°á»£ng sp
                    session.setAttribute("cart", cart);
                } else { //cÃ³ giá»� rá»“i

                    Cart cart = (Cart) session.getAttribute("cart"); //Láº¥y giá»� tá»« session
                    List<Item> list = cart.getItems();
                    boolean isExist = false; //Báº¯t Ä‘áº§u kiá»ƒm tra sáº£n pháº©m Ä‘ang thÃªm cÃ³ trong giá»� chÆ°a

                    for (Item i : list) {

                        if (i.getId() == book.getId()) {

                            i.setQuantity(i.getQuantity() + quantity);
                            isExist = true;
                        }
                    }

                    if (!isExist) {

                        Item item = new Item(book.getId(), book, quantity, book.getPrice());
                        list.add(item);
                    }

                    cart.updateStatus();
                    session.setAttribute("cart", cart);

                    //Tráº£ vá»� json status ajax
                    PrintWriter out = response.getWriter();
                    Gson gson = new Gson();
                    String type = request.getParameter("type");

                    if (type != null && type.equalsIgnoreCase("ajax")) {
                        out.print(gson.toJson(cart.getStatus()));
                        out.flush();
                        out.close();
                    } else {
                        //KhÃ´ng dÃ¹ng ajax
                        session.setAttribute("addedBook", bookID);
                        response.sendRedirect(currentURL);
                    }
                }
            }

//			session.setAttribute("addedBook", bookID);
//			response.sendRedirect(currentURL);
        } else {

            response.sendRedirect("404.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
