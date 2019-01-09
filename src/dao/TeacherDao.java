package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import teacherUI.Teacher;


public class TeacherDao {
	private static String[] preparsql = { "insert into teacherInfo(name,sex,birthDay,salary,college,major) values(?,?,?,?,?,?)",
										  "delete from teacherInfo where id=?",
										  "update teacherInfo set name=?, sex=?, birthDay=?, salary=?, college=?, major=? where id=?",
										  "select * from teacherInfo where ID=?" ,
										  "select * from teacherinfo"};
	private static ResultSet reset = null;
	public static void update(int id, String name, Date birthDay,String sex, 
			double salary, String college, String major) throws ClassNotFoundException {
		Connection conn = DBUtil.getConnection();
		try {
			PreparedStatement parment = conn.prepareStatement(preparsql[2]);
			
			parment.setString(1, name);
			parment.setString(2, sex);
			parment.setDate(3, birthDay);
			parment.setDouble(4, salary);
			parment.setString(5, college);
			parment.setString(6, major);
			parment.setInt(7, id);
			System.out.println(parment);
			parment.executeUpdate();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void insert(int id, String name, Date birthDay,String sex, 
			double salary, String college, String major) throws ClassNotFoundException {
		Connection conn = DBUtil.getConnection();
		try {
			PreparedStatement parment = conn.prepareStatement(preparsql[0]);
			parment.setString(1, name);
			parment.setString(2, sex);
			parment.setDate(3, birthDay);
			parment.setDouble(4, salary);
			parment.setString(5, college);
			parment.setString(6, major);
			parment.execute();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static Teacher query(int id) throws ClassNotFoundException, SQLException {
		Connection conn = DBUtil.getConnection();
		PreparedStatement parment;
		Teacher teacher = new Teacher();
		try {
			parment = conn.prepareStatement(preparsql[3]);
			parment.setInt(1, id);
			reset = parment.executeQuery();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			while(reset.next()) {
				teacher.setId(reset.getInt(1));
				teacher.setName(reset.getString(2));
				teacher.setSex(reset.getString(3));
				teacher.setBirthDay(reset.getDate(4));
				teacher.setSarlay(reset.getDouble(5));
				teacher.setCollege(reset.getString(6));
				teacher.setMajor(reset.getString(7));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conn.close();
		return teacher;
	}
	public static List<Teacher> queryAll() throws SQLException, ClassNotFoundException {
		Connection conn = DBUtil.getConnection();
		PreparedStatement parment;
		ArrayList<Teacher> allTeacher = new ArrayList<>();
		
			parment = conn.prepareStatement(preparsql[4]);
			reset = parment.executeQuery();
			
			while(reset.next()) {
				Teacher teacher = new Teacher();
				teacher.setId(reset.getInt(1));
				teacher.setName(reset.getString(2));
				teacher.setSex(reset.getString(3));
				teacher.setBirthDay(reset.getDate(4));
				teacher.setSarlay(reset.getDouble(5));
				teacher.setCollege(reset.getString(6));
				teacher.setMajor(reset.getString(7));
				allTeacher.add(teacher);
			}
			conn.close();
		return allTeacher;
	}
	
	public static void delete(int id) throws ClassNotFoundException, SQLException {
		Connection conn = DBUtil.getConnection();
		PreparedStatement parment;
		
			parment = conn.prepareStatement(preparsql[1]);
			parment.setInt(1, id);
			parment.execute();
			conn.close();
		
	}
}
