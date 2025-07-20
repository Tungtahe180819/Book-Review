package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import Model.Account;
import Model.Book;
import Model.Order;
import Model.OrderDetail;
import dal.DBContext;

public class OrderDAO {

    Connection conn = null; //kết nối sql
    PreparedStatement ps = null; //thực hiện lệnh
    ResultSet rs = null; //kết quả

    public void insertOrder(Order order) {

        String query = "insert into OrderReceipt values(?, ?, ?, ?, ?, ?, ?, getdate(), ?);";

        try {

            conn = new DBContext().getConnection(); //mở kết nối sql server
            ps = conn.prepareStatement(query);

            Account user = order.getUser();
            if (user != null) {

                ps.setInt(1, order.getUser().getId());
                ps.setString(2, order.getUser().getUsername());
            } else {

                ps.setNull(1, Types.INTEGER);
                ps.setString(2, null);
            }

            ps.setString(3, order.getFullName());
            ps.setString(4, order.getEmail());
            ps.setString(5, order.getPhone());
            ps.setString(6, order.getAddress());
            ps.setString(7, order.getMessage());
            ps.setDouble(8, order.getTotal());
            ps.executeUpdate();

        } catch (Exception e) {

        }
    }

    public void insertOrderDetail(OrderDetail orderDetail, int orderId) {

        String query = "insert into OrderDetail values(?, ?, ?, ?);";

        try {

            conn = new DBContext().getConnection(); //mở kết nối sql server
            ps = conn.prepareStatement(query);
            ps.setInt(1, orderId);
            ps.setInt(2, orderDetail.getBook().getId());
            ps.setInt(3, orderDetail.getQuantity());
            ps.setDouble(4, orderDetail.getPrice());
            ps.executeUpdate();

        } catch (Exception e) {

        }
    }

    public void checkout(Order order) {

        String query = "select IDENT_CURRENT('OrderReceipt')"; //chọn id của order vừa dc insert vào
        insertOrder(order);
        int insertedId = -1;

        try {

            conn = new DBContext().getConnection(); //mở kết nối sql server
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {

                insertedId = rs.getInt(1);
            }

            for (OrderDetail o : order.getDetails()) {

                insertOrderDetail(o, insertedId);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public List<Book> getOrderByBooks() { //for admin

        List<Book> list = new ArrayList<>();
        String query = "select top 5 sum(r.rating) as rating, count(r.bookID), b.title from Book b join Review r ON b.id = r.bookID group by b.id, b.title order by rating";
        try {

            conn = new DBContext().getConnection(); //mở kết nối sql server
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {

                Book temp = new Book();

                temp.setTitle(rs.getString(3));
                temp.setTotalReview(rs.getInt(2));

                int totalRating = rs.getInt(1);
                int totalReview = rs.getInt(2);
                int averageReview = 0;
                if (totalRating != 0 && totalReview != 0) {
                    averageReview = totalRating / totalReview;
                }

                temp.setAverageReview(averageReview);

                list.add(temp);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }

        return list;
    }

    public List<OrderDetail> getOrderByMonths() {

        List<OrderDetail> list = new ArrayList<>();
        String query = "SELECT \n"
                + "    YEAR(bd.bDate) AS Year, \n"
                + "    MONTH(bd.bDate) AS Month, \n"
                + "    COUNT(bd.bDate) AS date_count\n"
                + "FROM \n"
                + "    Book b \n"
                + "JOIN \n"
                + "    BookDetail bd ON b.id = bd.bookID \n"
                + "GROUP BY \n"
                + "    YEAR(bd.bDate), MONTH(bd.bDate)\n"
                + "ORDER BY \n"
                + "    Year, Month;";

        try {

            conn = new DBContext().getConnection(); //mở kết nối sql server
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {

                OrderDetail temp = new OrderDetail();

                temp.setMonth(rs.getInt(2));
                temp.setQuantity(rs.getInt(3));

                list.add(temp);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }

        return list;
    }
}
