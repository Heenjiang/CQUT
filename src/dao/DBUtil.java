package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class DBUtil {
	private static PreparedStatement parment = null;//预定义语句
	private static ResultSet set = null;//结果集
	static Connection conn;//数据库连接
	
	//获取数据库链接
	public static Connection getConnection() throws ClassNotFoundException{

		String url = ("jdbc:mysql://localhost:3306/cqut?useSSL=false");
		String user = "root";
		String password = "120788";
		Class.forName("com.mysql.jdbc.Driver");
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			System.out.printf("获取数据库连接失败，请检查路径和令牌！");
			e.printStackTrace();
		}
		return conn;
	}
	//关闭数据库连接
	public static void close() {
		try {
			if (!conn.isClosed()) {
					set.close();
					set = null;
					parment.close();
					parment = null;
					conn.close();
			}
		} catch (SQLException e) {
			System.out.printf("数据库连接关闭失败！");
			e.printStackTrace();
		}
	}
	
}
