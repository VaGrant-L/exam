import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface LastOrderOfUsers {
    public static Map<String, Object> getLastOrderOfUsers() {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 10000);
        result.put("message", "操作成功");

        // 数据库连接信息
        String url = "jdbc:mysql://localhost:3306/exam";
        String user = "root";
        String password = "root";

        // 查询SQL
        String sql = "SELECT o.*, u.nickname FROM p_order o " +
                "JOIN (" +
                "    SELECT user_id, MAX(created_at) AS max_created_at " +
                "    FROM p_order " +
                "    GROUP BY user_id" +
                ") t ON o.user_id = t.user_id AND o.created_at = t.max_created_at " +
                "JOIN p_user u ON o.user_id = u.id";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // 执行查询
            ResultSet rs = stmt.executeQuery();
            List<Map<String, Object>> data = new ArrayList<>();
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("id", rs.getString("id"));
                row.put("created_at", rs.getString("created_at"));
                row.put("updated_at", rs.getString("updated_at"));
                row.put("nickname", rs.getString("nickname"));
                row.put("product_name", rs.getString("product_name"));
                row.put("total_price", rs.getString("total_price"));
                row.put("count", rs.getString("count"));
                row.put("unit_price", rs.getString("unit_price"));
                row.put("status", rs.getInt("status"));
                row.put("pay_type", rs.getInt("pay_type"));
                data.add(row);
            }

            // 设置返回数据
            result.put("data", data);

        } catch (SQLException e) {
            e.printStackTrace();
            result.put("code", 10001);
            result.put("message", "操作失败");
        }

        return result;
    }
}
