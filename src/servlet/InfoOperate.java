package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import dao.TeacherDao;
import teacherUI.Teacher;
/**
 * Servlet implementation class InfoOperate
 */
public class InfoOperate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InfoOperate() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("deprecation")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 request.setCharacterEncoding("UTF-8");  
			String idString  =  request.getParameter("id");
			int id = Integer.parseInt(idString);
			String name = request.getParameter("name");
			String sex = request.getParameter("sex");
			String birthDay = request.getParameter("birthDay");
			//将字符串类型的日期转成sql日期类
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date DateObj = dateFormat.parse(birthDay);
				int month = DateObj.getMonth()+1;
				DateObj.setMonth(month);
				java.sql.Date sqlTime = new java.sql.Date(DateObj.getTime());
				String sarlay = request.getParameter("sarlay");
				//将字符串类型转成Double类型
				double sarlay1 = Double.parseDouble(sarlay);
				String college = request.getParameter("college");
				String major = request.getParameter("major");
				Teacher teacher = new Teacher(id,name, sex, sqlTime, sarlay1, college, major);
				if(id == 0 ) {
					teacher.insert();
				}
				else {
					teacher.update();
				}
			} catch (ParseException | ClassNotFoundException e) {
				System.out.println("字符串构造日期类失败");
			}
			try {
				List<Teacher> teachers = TeacherDao.queryAll();
				request.setAttribute("teachers", JSON.toJSONString(teachers));
			} catch (ClassNotFoundException | SQLException e) {
				System.out.println("从数据库获取数据失败");
			}
			request.getRequestDispatcher("./main.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 request.setCharacterEncoding("UTF-8");  
		doGet(request, response);
	}

}
