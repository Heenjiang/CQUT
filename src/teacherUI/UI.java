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
		System.out.println("=========��ӭ��������ѧ��ʦ����ϵͳ=======");
		while(true) {
			int operateNum;
			System.out.println("1����ѯȫ���ְ����Ϣ        2���½�ְ����Ϣ���� ");
			System.out.println("3����ְ����Ϣ�޸�               4����ְ����Ϣɾ�� ");
			System.out.println("��ѡ����Ҫִ�еĲ������кţ�");
			Scanner input = new Scanner(System.in);
			operateNum = input.nextInt();
			input.nextLine();
			while(true) {
				if(operateNum > 4|| operateNum <1) {
					System.out.println("��ѡ����Ҫִ�еĲ������кţ�");
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
						System.out.println("���ݿ���û�н�ʦ��Ϣ");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;
			case 2:
				Teacher teacher = new Teacher();
			
				System.out.print("�������ʦ����:");
				teacher.setName(input.nextLine());
				System.out.print("�������ʦ�Ա�:");
				teacher.setSex(input.nextLine());
				System.out.print("�������ʦ���գ��꣩:");
				int year =input.nextInt();
				input.nextLine();
				System.out.print("�������ʦ���գ��£�:");
				int month =input.nextInt();
				input.nextLine();
				System.out.print("�������ʦ���գ��գ�:");
				int day =input.nextInt();
				input.nextLine();
				Date date = new Date();
				GregorianCalendar gc = new GregorianCalendar();
				gc.set(Calendar.YEAR,year);//������
				gc.set(Calendar.MONTH, month);//����0��1��..�Դ������
				gc.set(Calendar.DAY_OF_MONTH, day);//������
				date = gc.getTime();
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");  
				format.format(date); 
				java.sql.Date sqlTime = new java.sql.Date(date.getTime());
				teacher.setBirthDay(sqlTime);
				System.out.println(teacher.getBirthDay().toString());
				
				System.out.println(teacher.getBirthDay());
				System.out.print("�������ʦнˮ:");
				teacher.setSarlay(input.nextDouble());
				input.nextLine();
				System.out.print("�������ʦ����ѧԺ:");
				teacher.setCollege(input.nextLine());
				System.out.print("�������ʦְ��:");
				teacher.setMajor(input.nextLine());
				
				teacher.insert();
				break;

			
			case 3:
				Teacher updataTeacher = new Teacher();
				System.out.print("�������޸���Ϣ��ʦ��ID:");
				int id = input.nextInt();
				input.nextLine();
				updataTeacher.setId(id);
				if(TeacherDao.query(id) == null)
					System.out.println("�����ڴ��û���");
				else {
					System.out.print("�������ʦ����:");
					updataTeacher.setName(input.nextLine());
					System.out.print("�������ʦ�Ա�:");
					updataTeacher.setSex(input.nextLine());
					System.out.print("�������ʦ���գ��꣩:");
					int uyear =input.nextInt();
					input.nextLine();
					System.out.print("�������ʦ���գ��£�:");
					int umonth =input.nextInt();
					input.nextLine();
					System.out.print("�������ʦ���գ��գ�:");
					int uday =input.nextInt();
					input.nextLine();
					Date update = new Date();
					GregorianCalendar upgc = new GregorianCalendar();
					upgc.set(Calendar.YEAR,uyear);//������
					upgc.set(Calendar.MONTH, umonth);//����0��1��..�Դ������
					upgc.set(Calendar.DAY_OF_MONTH, uday);//������
					update = upgc.getTime();
					SimpleDateFormat upformat = new SimpleDateFormat("yyyyMMdd");  
					upformat.format(update); 
					java.sql.Date upsqlTime = new java.sql.Date(update.getTime());
					updataTeacher.toString();
					updataTeacher.setBirthDay(upsqlTime);
					System.out.print("�������ʦнˮ:");
					updataTeacher.setSarlay(input.nextDouble());
					input.nextLine();
					System.out.print("�������ʦ����ѧԺ:");
					updataTeacher.setCollege(input.nextLine());
					System.out.print("�������ʦְ��:");
					updataTeacher.setMajor(input.nextLine());
				}
				int sureUpdata = 0;
				System.out.println("ȷ���޸ĸý�ʦ����Ϣ����ȷ��������1����");
				sureUpdata = input.nextInt();
				if(sureUpdata == 1) {
				    input.nextLine();
					updataTeacher.update();
				}
				break;
			case 4:
				Teacher deleteTeacher = new Teacher();
				System.out.print("������ɾ����ʦID:");
				deleteTeacher.setId(input.nextInt());
				input.nextLine();
				int sure = 0;
				System.out.println("ȷ��ɾ���ý�ʦ��������Ϣ����ȷ��������1����");
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
