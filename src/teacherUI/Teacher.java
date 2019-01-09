package teacherUI;

import java.sql.Date;
import java.sql.SQLException;

import dao.TeacherDao;

public class Teacher {
	private int id ;
	private String name;
	private String sex;
	private Date birthDay;
	private double sarlay;
	private String college;
	private String major;
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBirthDay() {
		return birthDay;
	}
	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}
	public double getSarlay() {
		return sarlay;
	}
	public void setSarlay(double sarlay) {
		this.sarlay = sarlay;
	}
	public String getCollege() {
		return college;
	}
	public void setCollege(String college) {
		this.college = college;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	
	public Teacher(int id,String name, String sex, Date birthDay, double sarlay, String college, String major) {
		super();
		this.id =id;
		this.name = name;
		this.sex = sex;
		this.birthDay = birthDay;
		this.sarlay = sarlay;
		this.college = college;
		this.major = major;
	}
	public Teacher(String name, String sex, Date birthDay, double sarlay, String college, String major) {
		super();
		this.name = name;
		this.sex = sex;
		this.birthDay = birthDay;
		this.sarlay = sarlay;
		this.college = college;
		this.major = major;
	}
	public Teacher() {
		super();
	}
	@Override
	public String toString() {
		return " [id=" + id + ", name=" + name +", sex=" + sex+ ", birthDay=" + birthDay + ", sarlay=" + sarlay
				+ ", college=" + college + ", major=" + major + "]";
	}
	public void update() throws ClassNotFoundException {
		TeacherDao.update(this.id, this.name, this.birthDay, this.sex, this.sarlay, this.college, this.major);
	}
	public void insert() throws ClassNotFoundException {
		TeacherDao.insert(this.id, this.name, this.birthDay,this.sex, this.sarlay, this.college, this.major);
	}
	public boolean queary() throws ClassNotFoundException, SQLException {
		if(TeacherDao.query(this.id) ==null)
			return false;
		else
			return true;
	}
	public void delete() throws ClassNotFoundException, SQLException {
		TeacherDao.delete(this.id);
	}
}
