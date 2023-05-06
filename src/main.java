import java.util.Map;

public class main {
    public static void main(String[] args) {
        // 设置分页参数
        int page = 1;
        int perPage = 10;

        // 调用接口查询订单列表
        Map<String, Object> result = PagingInterfaceAPI.getOrderList(page, perPage);

        // 输出结果
        System.out.println(result);

        System.out.println("___________________________________");

        Map<String,Object> r = LastOrderOfUsers.getLastOrderOfUsers();
        // 输出结果
        System.out.println(r);

    }
}