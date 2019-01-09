<%@page import="teacherUI.Teacher"%>
<%@page import="java.util.List"%>
<%@ page import="com.alibaba.fastjson.JSON"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="css/tableStyle.css" />
<link rel="stylesheet" type="text/css" href="css/create.css" />
<title>Insert title here</title>
</head>
<body>
    <!-- 不可编辑的面板 -->
   
    <div class="container">
        <!-- 上方标题 -->
        <div class="list">
            <div class="top">
                <div class="topLine"></div>
                <div class="topWordsContainer">
                    <p class="topWords">教师信息管理</p>
                </div>
                <div class="topLine"></div>
            </div>
            <!-- 新增删除按钮 -->
            <div class="addDeleteButtonContainer">
                <a class="Button addButton" onclick="displayCreate()">新 增</a>
                <a class="Button deleteButton" onclick="deleteData()">删 除</a>
                <input class="searchInput" type="text" name = "searchInfo" id="searchInfo" />
                <a class="Button searchButton" onclick="searchInfo()">查 询</a>
            </div>
            <div class="tableContainer">
                <table cellpadding="0" cellspacing="0" style="border-bottom: 1px solid #bcc0c3;">
                    <thead>
                        <tr class="tableFirstLine">
                            <td width="39px" style="border-left: 1px solid #bcc0c3;">
                                <input type="checkbox" onclick="clickSelectAll()" id="selectAll">
                            </td>
                            <td width="56px">seqNumber</td>
                            <td width="100px">id</td>
                            <td width="114px">name</td>
                            <td width="114px">sex</td>
                            <td width="111px">birthDay</td>
                            <td width="91px">salary</td>
                             <td width="230px">college</td>
                            <td width="70px">major</td>
                            <td width="150px" style="border-right: 1px solid #bcc0c3;">操作</td>
                        </tr>
                     </thead>
                    <tbody id="tableBody" class="tableNext">
                    
                    </tbody>
                </table>
                <div id="totalNumbers" class="totalNumbers"></div>
                <div id="nowPageAndTotalPages"></div>
                <div id="beginPage"></div>
                <div>
                    <button id="lastPage" class="Button lastPage" type="button" onclick="lastPage()" style="    color: black;">上一页</button>
                </div>
                <div>
                    <button id="nextPage" class="Button nextPage" type="button" onclick="nextPage()" style="    color: black;">下一页</button>
                </div>
                <div id="endPage"></div>
            </div>
        </div>
        <!-- 新增页面  属性说明：td中的id是空id，使document对象能够识别-->
        <div class="all" id="createStu">
            <div class="editorTop">
                <p class="editorTopWords" id="titleOfEditor"></p>
            </div>
            <form action="./InfoOperate" method = "post" id = "MyFrom" >
            <div class="editorText">
                <table>
                    <!-- 新增页面中的学号添加和合法性判断-->
                    <tr>
                        <td>id</td>
                        <td>
                            <input type="text" id="info1" name = "id" onblur="isNumbers(1)" />
                        </td>
                        <td class="warning" id="isNumer1">
                            <div>只可以输入数字</div>
                        </td>
                    </tr>
                    <!-- 新增页面中的姓名信息输入行-->
                    <tr>
                        <td>姓名</td>
                        <td>
                            <input type="text" name = "name" id="info2" />
                        </td>
                    </tr>
                    <!-- 新增页面中的学院信息输入行-->
                    <tr>
                        <td>性别</td>
                        <td>
                            <input type="text" name = "sex" id="info3" />
                        </td>
                    </tr>
                    <!-- 新增页面中的学院信息输入行-->
                    <tr>
                        <td>出生日期</td>
                        <td>
                            <input type="date" name = "birthDay" id="info4" />
                        </td>
                    </tr>
                    <!-- 新增页面中的专业信息输入行-->
                    <tr>
                        <td>薪水</td>
                        <td>
                            <input type="text" name = "sarlay" id="info5" />
                        </td>
                    </tr>
                    <!-- 新增页面中的年级信息输入行与合法性判断-->
                    <tr>
                        <td>学院</td>
                        <td>
                            <input type="text" name = "college" id="info6" />
                        </td>
                    </tr>
                    <!-- 新增页面中的班级信息输入行与合法性判断-->
                     <tr>
                        <td>职位</td>
                        <td>
                            <input type="text"  name = "major"  id="info7"/>
                        </td>
                    </tr>
                </table>
            </div>
            <!-- 新增学生信息界面的底部确定按钮 -->
            <div class="editorBottom" id="createStuBottom">
                <button class="editorButton editorAddButton" id="createAStu" onclick="addData()"  type = "button">保 存</button>
                <a class="editorButton editorDeleteButton" onclick="hiddenCreate()">取 消</a>
            </div>

             <!-- 修改学生信息界面的底部确定按钮 -->
            <div class="editorBottom" id="editorStuBottom">
                <button class="editorButton editorAddButton" id="sure" onclick="changeData()"  type = "button">提 交
                </button>
                <a class="editorButton editorDeleteButton" id="canel" onclick="hiddenChange()">取 消</a>
            </div>
            </form>
        </div>
    </div>
</body>
<script type="text/javascript">
    	function Stu(id, name, sex, birthDay, sarlay, college, major) {
    		this.id = id;
    		this.name = name;
    		this.sex = sex;
    		this.birthDay = birthDay;
    		this.sarlay = sarlay;
    		this.college = college;
    		this.major = major;
    	}
    	var txt = <%=JSON.toJSONString(request.getAttribute("teachers"))%>;
    	txt = '{"allData":' +txt+'}';
    	var obj =  eval("("+ txt +")");
    	</script>
<script type="text/javascript" src="table.js"></script>
</html>