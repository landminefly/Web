
//数据库前后端交互————后端部分

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.google.gson.Gson;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

@WebServlet("/com/test")
public class Response extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        StringBuilder sb = new StringBuilder();
        String temp = null;
        while((temp = reader.readLine()) != null){
            sb.append(temp);
        }
        User u = new Gson().fromJson(sb.toString(),User.class);
        if(!Objects.equals(u.getUser(), "root") || !Objects.equals(u.getPwd(), "073412")){
            throw new RuntimeException();
        }
        // Data diana = new Data("diana", 18, 98);
        // Data ava = new Data("ava", 17, 60);
        // DataList dataList = new DataList(new Data[]{diana, ava});

        DataList dataList = null;
        List<Data> query = null;
        try {
            Connection conn = JDBCUtils.getConnectionByDruid();
            String sql = "SELECT * FROM webTest1";
            QueryRunner queryRunner = new QueryRunner();
            BeanListHandler<Data> beanListHandler = new BeanListHandler<>(Data.class);
            query = queryRunner.query(conn,sql,beanListHandler);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        dataList = new DataList(query.toArray(new Data[0]));
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write(new Gson().toJson(dataList));
    }
}

class User{
    private String user;
    private String pwd;

    public User(String user, String pwd) {
        this.user = user;
        this.pwd = pwd;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}

class DataList{
    private Data[] list;

    public DataList(Data[] list) {
        this.list = list;
    }

    public Data[] getList() {
        return list;
    }

    public void setList(Data[] list) {
        this.list = list;
    }
}


class JDBCUtils
{
    //将连接池dataSource声明为属性
    private static DruidDataSource dataSource = null;
    //在静态代码块中创建连接池dataSource，因为连接池只需一个即可
    static
    {
        try
        {
            //加载配置文件
            Properties properties = new Properties();

            //原来的这种写法会报错！！！会导致读不到配置文件，is为null！！！
            // InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");

            //必须改为以下这种写法！！！
            InputStream is =  JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties");


            properties.load(is);
            //使用连接池工厂DruidDataSourceFactory传入配置信息，创建连接池dataSource
            dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    //使用该方法获取连接
    public static Connection getConnectionByDruid() throws SQLException
    {
        //直接调用连接池dataSource的getConnection()即可
        return dataSource.getConnection();
    }

    public static void closeResource(Connection connection, Statement statement, ResultSet resultSet)
    {
        //方式1：无需try/catch环绕，方法内已将异常处理
        DbUtils.closeQuietly(connection);
        DbUtils.closeQuietly(statement);
        DbUtils.closeQuietly(resultSet);

    }
}