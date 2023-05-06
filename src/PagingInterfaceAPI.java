import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PagingInterfaceAPI {
    public static Map<String, Object> getOrderList(int page, int perPage) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 10000);
        result.put("message", "操作成功");

        // 数据库连接信息
        String url = "jdbc:mysql://localhost:3306/exam";
        String user = "root";
        String password = "root";

        // 分页查询SQL
        String sql = "SELECT o.id, o.created_at, o.updated_at, u.nickname, u.mobile, o.product_name, o.total_price, o.count, o.unit_price, o.status, o.pay_type " +
                "FROM p_order o " +
                "JOIN p_user u ON o.user_id = u.id " +
                "ORDER BY o.created_at DESC " +
                "LIMIT ?, ?";

        // 查询总记录数SQL
        String countSql = "SELECT COUNT(*) FROM p_order";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql);
             PreparedStatement countStmt = conn.prepareStatement(countSql)) {

            // 设置分页参数
            stmt.setInt(1, (page - 1) * perPage);//设置了预处理语句的第一个参数，它表示要跳过的记录数
            stmt.setInt(2, perPage);//设置了预处理语句的第二个参数，它表示要返回的记录数。

            // 执行分页查询
            ResultSet rs = stmt.executeQuery();
            List<Map<String, Object>> data = new ArrayList<>();
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("id", rs.getString("id"));
                row.put("created_at", rs.getString("created_at"));
                row.put("updated_at", rs.getString("updated_at"));
                row.put("nickname", rs.getString("nickname"));
                row.put("mobile", rs.getString("mobile"));
                row.put("product_name", rs.getString("product_name"));
                row.put("total_price", rs.getString("total_price"));
                row.put("count", rs.getString("count"));
                row.put("unit_price", rs.getString("unit_price"));
                row.put("status", rs.getInt("status"));
                row.put("pay_type", rs.getInt("pay_type"));
                data.add(row);
            }

            // 执行总记录数查询
            ResultSet countRs = countStmt.executeQuery();
            int total = 0;
            if (countRs.next()) {
                total = countRs.getInt(1);
            }

            // 设置返回数据
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("total", total);
            dataMap.put("data", data);
            dataMap.put("page", page);
            dataMap.put("pers", perPage);
            dataMap.put("hasNext", page * perPage < total);
            result.put("data", dataMap);

        } catch (SQLException e) {
            e.printStackTrace();
            result.put("code", 10001);
            result.put("message", "操作失败");
        }

        return result;
    }

}