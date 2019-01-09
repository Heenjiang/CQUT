package servlet;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;

import dao.TeacherDao;
import teacherUI.Teacher;


/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("username");
		String PWD = request.getParameter("PWD");
		
		if( "root".equals(userName) && "root".equals(PWD))
		{
			HttpSession httpSession = request.getSession();
			httpSession.setAttribute("username",userName);
			System.out.print("欢迎");
			try {
				List<Teacher> teachers = TeacherDao.queryAll();
				request.setAttribute("teachers", JSON.toJSONString(teachers));
			} catch (ClassNotFoundException | SQLException e) {
				System.out.println("从数据库获取数据失败");
			}
			request.getRequestDispatcher("./main.jsp").forward(request, response);
		}
		else
		{
			response.sendRedirect("./Login.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
