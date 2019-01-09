package teacherUI;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

import dao.TeacherDao;

public class UI {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		System.out.println("=========欢迎来到理工大学教师管理系统=======");
		while(true) {
			int operateNum;
			System.out.println("1、查询全体教职工信息        2、新教职工信息增添 ");
			System.out.println("3、教职工信息修改               4、教职工信息删除 ");
			System.out.println("请选择所要执行的操作序列号：");
			Scanner input = new Scanner(System.in);
			operateNum = input.nextInt();
			input.nextLine();
			while(true) {
				if(operateNum > 4|| operateNum <1) {
					System.out.println("请选择所要执行的操作序列号：");
					operateNum = input.nextInt();
					input.nextLine();
				}
				else
					break;
			}
			switch (operateNum) {
			case 1:
				try {
					List<Teacher> allTeacher = TeacherDao.queryAll();
					if(allTeacher.size()>0) {
						for(int i = 0; i < allTeacher.size(); i++)
							System.out.println(allTeacher.get(i).toString());
					}
					else
						System.out.println("数据库中没有教师信息");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;
			case 2:
				Teacher teacher = new Teacher();
			
				System.out.print("请输入教师姓名:");
				teacher.setName(input.nextLine());
				System.out.print("请输入教师性别:");
				teacher.setSex(input.nextLine());
				System.out.print("请输入教师生日（年）:");
				int year =input.nextInt();
				input.nextLine();
				System.out.print("请输入教师生日（月）:");
				int month =input.nextInt();
				input.nextLine();
				System.out.print("请输入教师生日（日）:");
				int day =input.nextInt();
				input.nextLine();
				Date date = new Date();
				GregorianCalendar gc = new GregorianCalendar();
				gc.set(Calendar.YEAR,year);//设置年
				gc.set(Calendar.MONTH, month);//这里0是1月..以此向后推
				gc.set(Calendar.DAY_OF_MONTH, day);//设置天
				date = gc.getTime();
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");  
				format.format(date); 
				java.sql.Date sqlTime = new java.sql.Date(date.getTime());
				teacher.setBirthDay(sqlTime);
				System.out.println(teacher.getBirthDay().toString());
				
				System.out.println(teacher.getBirthDay());
				System.out.print("请输入教师薪水:");
				teacher.setSarlay(input.nextDouble());
				input.nextLine();
				System.out.print("请输入教师所在学院:");
				teacher.setCollege(input.nextLine());
				System.out.print("请输入教师职务:");
				teacher.setMajor(input.nextLine());
				
				teacher.insert();
				break;

			
			case 3:
				Teacher updataTeacher = new Teacher();
				System.out.print("请输入修改信息教师的ID:");
				int id = input.nextInt();
				input.nextLine();
				updataTeacher.setId(id);
				if(TeacherDao.query(id) == null)
					System.out.println("不存在此用户！");
				else {
					System.out.print("请输入教师姓名:");
					updataTeacher.setName(input.nextLine());
					System.out.print("请输入教师性别:");
					updataTeacher.setSex(input.nextLine());
					System.out.print("请输入教师生日（年）:");
					int uyear =input.nextInt();
					input.nextLine();
					System.out.print("请输入教师生日（月）:");
					int umonth =input.nextInt();
					input.nextLine();
					System.out.print("请输入教师生日（日）:");
					int uday =input.nextInt();
					input.nextLine();
					Date update = new Date();
					GregorianCalendar upgc = new GregorianCalendar();
					upgc.set(Calendar.YEAR,uyear);//设置年
					upgc.set(Calendar.MONTH, umonth);//这里0是1月..以此向后推
					upgc.set(Calendar.DAY_OF_MONTH, uday);//设置天
					update = upgc.getTime();
					SimpleDateFormat upformat = new SimpleDateFormat("yyyyMMdd");  
					upformat.format(update); 
					java.sql.Date upsqlTime = new java.sql.Date(update.getTime());
					updataTeacher.toString();
					updataTeacher.setBirthDay(upsqlTime);
					System.out.print("请输入教师薪水:");
					updataTeacher.setSarlay(input.nextDouble());
					input.nextLine();
					System.out.print("请输入教师所在学院:");
					updataTeacher.setCollege(input.nextLine());
					System.out.print("请输入教师职务:");
					updataTeacher.setMajor(input.nextLine());
				}
				int sureUpdata = 0;
				System.out.println("确认修改该教师的信息？（确认请输入1）：");
				sureUpdata = input.nextInt();
				if(sureUpdata == 1) {
				    input.nextLine();
					updataTeacher.update();
				}
				break;
			case 4:
				Teacher deleteTeacher = new Teacher();
				System.out.print("请输入删除教师ID:");
				deleteTeacher.setId(input.nextInt());
				input.nextLine();
				int sure = 0;
				System.out.println("确认删除该教师的所有信息？（确认请输入1）：");
				sure = input.nextInt();
				input.nextLine();
				if(sure == 1) {
					deleteTeacher.delete();
				}
				break;
			default:
				break;

			
			}
		
		}
	};

}
