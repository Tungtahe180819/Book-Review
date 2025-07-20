package Controller;

import Model.Cart;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "CartController", urlPatterns = {"/CartController"})
public class CartController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null) { //tạo giỏ mới khi chưa có

            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        String rBookID = request.getParameter("removedBook"); //remove
        String dBookID = request.getParameter("decreasedBook"); //decrease
        String iBookID = request.getParameter("increasedBook"); //increase
        String clearCart = request.getParameter("clearCart"); //clear
        String itemQuantity = request.getParameter("itemQuantity");
        String cBookID = request.getParameter("changedBook"); //change quantity

        if (cBookID != null && itemQuantity != null) {
            try {
                int newQuantity = Integer.parseInt(itemQuantity);
                if (newQuantity > 0) {
                    cart.changeItemQuantity(Integer.parseInt(cBookID), newQuantity);
                }
            } catch (Exception e) {
                // Xử lý ngoại lệ nếu cần
            }
        }
        if (dBookID != null) {
            cart.decreaseItem(Integer.parseInt(dBookID));
        } else if (iBookID != null) {
            cart.increaseItem(Integer.parseInt(iBookID));
        } else if (rBookID != null) {
            cart.removeItem(Integer.parseInt(rBookID));
            request.setAttribute("cartMessage", "Đã gỡ sản phẩm khỏi giỏ hàng");
        } else if (clearCart != null) {
            cart.clearItems();
            request.setAttribute("cartMessage", "Đã gỡ tất cả sản phẩm khỏi giỏ hàng");
        }

        request.getRequestDispatcher("/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
