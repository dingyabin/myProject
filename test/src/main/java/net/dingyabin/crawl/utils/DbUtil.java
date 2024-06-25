package net.dingyabin.crawl.utils;

import cn.hutool.core.net.NetUtil;
import cn.hutool.db.sql.SqlExecutor;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.function.Function;

/**
 * @author 丁亚宾
 * Date: 2024/6/25.
 * Time:23:43
 */
public class DbUtil {

    private static volatile Long workId = null;

    private static final String QUERY_WORK_ID_SQL = "SELECT id FROM snowflak_worker_id WHERE ip = ?";
    private static final String INSERT_WORK_ID_SQL = "INSERT INTO snowflak_worker_id (ip) VALUES (?)";




    public static Long getWorkIdByDb() {
        if (workId != null) {
            return workId;
        }
        String localhostStr = NetUtil.getLocalhostStr();
        if (StringUtils.isBlank(localhostStr)) {
            return null;
        }
        workId = doInJdbcConnection("jdbc:mysql://localhost:3306/weight_manager?useSSL=false", "root", "12345678", connection -> {
            Long id = null;
            try {
                id = SqlExecutor.query(connection, QUERY_WORK_ID_SQL, rs -> rs.next() ? rs.getLong("id") : null, localhostStr);
                if (id == null) {
                    id = SqlExecutor.executeForGeneratedKey(connection, INSERT_WORK_ID_SQL, localhostStr);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return id;
        });
        return workId;
    }


    private static <T> T doInJdbcConnection(String url, String uname, String pwd, Function<Connection, T> function) {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, uname, pwd);
            return function.apply(connection);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                closeQuietly(connection);
            }
        }
        return null;
    }


    private static void closeQuietly(final Connection closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (Exception ioe) {
            // ignore
        }
    }


}
