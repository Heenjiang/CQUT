var changeIndex = 0;//记录修改对象的下标
//初始化的对象信息
var allData = obj.allData;
for (var i = 0; i < allData.length; i++) {
	var birthdayMilliseconds = parseInt(obj.allData[i].birthDay);
	var birthday = new Date(birthdayMilliseconds);
    obj.allData[i].birthDay = birthday.getFullYear()+"-"+birthday.getMonth()+"-"+birthday.getDate(); 
}

// 点击新建页面中的确定按钮后执行添加数据
function addData() {
    if (!isFull()) {
        alert("有信息没有录入");
    } else {
    	document.getElementById("MyFrom").submit();
        var id = 0;
        var name = document.getElementById("info2").value;
        var sex = document.getElementById("info3").value;
        var birthday = document.getElementById("info4").value;
        var sarlay = document.getElementById("info5").value;
        var cllege = document.getElementById("info6").value;
        var major = document.getElementById("info7").value;
        //使用新增学生的信息新建一个学生对象，并将其添加进所有学生信息组成的对象数组中
        allData.push(new Stu(id, name,sex, birthDay, sarlay, college, major));
        //将更新后的学生信息对象数组添加进表格中
        document.getElementById('tableBody').innerHTML = addDataToTable(allData);
        //添加成功后将新增学生页面隐藏
        document.getElementById("createStuBottom").style.display = "none";
        document.getElementById("createStu").style.display = "none";
       
        setStuInfoNull();
    }
}

// 当点击新建按钮时，将新建页面中的各个div的display属性设置成block（意味着此时新增页面出现）
function displayCreate() {
    setStuInfoNull();
    document.getElementById("createStu").style.display = "block";
    document.getElementById("info1").disabled = true;
    //将修改学生信息的尾部按钮模块设置为隐藏
    document.getElementById("editorStuBottom").style.display = "none";
    document.getElementById("createStuBottom").style.display = "block";
    //将页面头部的文字内容设置为“新增学生信息”
    document.getElementById("titleOfEditor").innerHTML = "新增教师信息";

}

// 隐藏新建子页面并清空数据
function hiddenCreate() {
    document.getElementById("createStu").style.display = "none";
    
    for (var i = 1; i <= 1; i++) {
        document.getElementById("isNumer" + i).style.display = "none";
    }
    setStuInfoNull();
}

// 删除勾选栏对应行的数据
function deleteData() {

    //返回勾选栏对象数组
    var deleteArray = document.getElementsByName("select");
    //标记变量
    var flag = false;
    //记录被选择的ID
    var idArray = "";
    //循环遍历数组，筛选被选中的勾选栏
    for (var i = deleteArray.length - 1; i >= 0; i--) {
        if (deleteArray[i].checked == true) {
        	idArray = idArray+deleteArray[i].id+",";
            flag = true;
        }
    }
    //有被勾选的勾选栏
    if (flag) {
        //弹出提示信息框
        var weatherDelete = confirm("确定要选中教师数据?");
        //执行删除数据步骤（从数组中删除被勾选的学生对象）
        if (weatherDelete) {
            //循环遍历数组，前台删除被勾选的学生对象
            for (var i = deleteArray.length - 1; i >= 0; i--) {
                if (deleteArray[i].checked == true) {
                    var m = i + (curPage - 1) * pageSize;
                    allData.splice(m, 1);
                }
            }

            //删除判断是否需要减页
            if (page > Math.ceil(allData.length / pageSize)) {
                page = Math.ceil(allData.length / pageSize);

                //如果整体页数减少后 页数还大于一页 则需要减页
                if (curPage > 1)
                    curPage--;
            }
             alert(allData.length);
            //删除后重新加载表格模块
            window.onload();
            //向后台发送id字符串
            var xmlhttp;
            if (window.XMLHttpRequest)
              {// code for IE7+, Firefox, Chrome, Opera, Safari
              xmlhttp=new XMLHttpRequest();
              }
            else
              {// code for IE6, IE5
              xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
              }
            xmlhttp.onreadystatechange=function()
              {
              if (xmlhttp.readyState==4 && xmlhttp.status==200)
                {
                document.getElementById("myDiv").innerHTML=xmlhttp.responseText;
                }
              }
            xmlhttp.open("POST","./delete",true);
            xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
            xmlhttp.send(idArray);
            return;
        }
    } else
    //用户此时没有勾选任何行
     {
        alert("至少选择一项");
        return;
    }

}

// 点击上方标题栏中的勾选栏时，将所有的勾选栏属性设置为被选
function clickSelectAll() {
    var deleteArray = document.getElementsByName("select");
    for (var i = 0; i < deleteArray.length; i++) {
        deleteArray[i].checked = selectAll.checked;
    }
}

//重新加载表格模块函数
window.onload = function() {
    tableBegin();
    movedIntoTr();
};

//设置鼠标移入行的背景色
function movedIntoTr(object) {
    object.style.background = '#e2e2e1';
}

//鼠标移出行的背景色（设置为原来的背景色）
function movedOutTr(object, style) {
        if (style == "background-color:#eef1f8") {
            object.style.background = "#eef1f8";
        } else {
            object.style.background = "#ffffff";
        }
}
//将数组中的对象新建表格添加到表格模块
function tableBegin() {
    document.getElementById('tableBody').innerHTML = addDataToTable(allData);
}
var pageSize = 10; //每页显示的记录条数
var curPage = 1; //当前页
var page; //总页数
var beginIndex;//每一页中第一行的下标
var pageData;//每一页中表格的对象数组
// 新建表格结点，并且返回一个行结点组成的对象数组
function addDataToTable(allData) {
    document.getElementById("selectAll").checked = false;
    //用于存储当前页码的对象数组表格的文档流
     var tableHtml = '';
     
    if (allData.length == 0) {
       tableHtml = "<tr style='"  + "' onmouseover='movedIntoTr(this)'\
             onmouseout=movedOutTr(this,'" + "')> <td style = >" + "" + "</td> </tr>";
        document.getElementById("totalNumbers").innerHTML = " 第" + (curPage) +"页，共 0"+ "条 ," + 
        " （每页显示" + (pageSize) + "条）";
        //返回行数为当前页码对象数组的长度的表格结点
    return tableHtml;

    }
    else{
         //根据当前allData中的对象个数计算需要多少页
    if (allData.length % pageSize == 0) {
        page = (allData.length / pageSize);
    } else {
        page = Math.ceil(allData.length / pageSize);
    }

     //根据当前页码(curPage)提取allData中的子数组
    if (curPage == 1) {
        beginIndex = 0;
        document.getElementById("lastPage").disabled = true;
    } else {
        document.getElementById("lastPage").disabled = false;
        beginIndex = (pageSize * (curPage - 1));
    }
    //当前页码小于总的页码数
    if (curPage < page) {
        pageData = allData.slice(beginIndex, beginIndex + pageSize);
        document.getElementById("nextPage").disabled = false;
        if (curPage != 1)
            document.getElementById("lastPage").disabled = false;
    }
    //当前页码等于总的页码数
    if (curPage == page) {
        var lenthOfEndPage = allData.length - (page - 1) * pageSize;
        document.getElementById("nextPage").disabled = true;
        pageData = allData.slice(beginIndex, beginIndex + lenthOfEndPage);
    }
    
    //循环将当前页码中的对象数组中的属性信息赋值给相应每行表格，并将每行表格添加进tableHtml中
    for (var i = 0; i < pageData.length; i++) {
        if (i % 2 == 0)//控制相邻两个表格不同的颜色
            style = "background-color:#ffffff";
        if (i % 2 == 1)
            style = "background-color:#eef1f8";
        //计算每行表格所对应的序号
        var orderNumber = i + (curPage - 1) * pageSize;
        //每行表格的内容
        var trHtml =
        //在表格的每一行设置鼠标鼠标移入移出的事件
            "<tr style='" + style + "' onmouseover='movedIntoTr(this)'\
            onmouseout=movedOutTr(this,'" + style + "')>" +
            //设置每一行最前方的选择器
            "<td><input type='checkbox' name='select'></td>" +
            "<td>" + (orderNumber + 1) + "</td>" +
            "<td>" + pageData[i].id + "</td>" +
            "<td>" + pageData[i].name + "</td>" +
            "<td>" + pageData[i].sex + "</td>" +
            "<td>" + pageData[i].birthDay+"</td>" +
            "<td>" + pageData[i].sarlay + "</td>" +
            "<td>" + pageData[i].college + "</td>" +
            "<td>" + pageData[i].major + "</td>" +
            //设置每一行的操作选择项（调用loadOperator函数）
            "<td>" + loadOperator(orderNumber) + "</td>" +
            "</tr>";
            //每一次循环完后将一行表格添加都表格中
        tableHtml += trHtml;
    }
    //设置每一页下的统计数据结点
    document.getElementById("totalNumbers").innerHTML = " 第" + (curPage) +"页，共" + allData.length + "条 ," + 
        " （每页显示" + (pageSize) + "条）";
        //返回行数为当前页码对象数组的长度的表格结点
    return tableHtml;
    
    }
   
}
//跳转到下一页
function nextPage() {
    curPage++;
    document.getElementById('tableBody').innerHTML = addDataToTable(allData);
}
//跳转到上一页
function lastPage() {
    curPage--;
    document.getElementById('tableBody').innerHTML = addDataToTable(allData);
}
// 根据每行表格的序号返回带参数的操作模块
function loadOperator(index) {
    var operatorHtml = "<span onclick=checkStudentInfo(" + index + ")>查看&nbsp&nbsp</span>" +
        "<span onclick=changeStudentInfo(" + index + ")>&nbsp&nbsp修改</span>";
    return operatorHtml;
}

// 判断的正则表达式
function isNumbers(index) {
    var no = document.getElementById("info" + index).value;
    var reg = new RegExp("^[0-9]*$");
    //数据不合法
    if (!reg.test(no)) {
        //数据不合法时将提示单元格设置为可视
        document.getElementById("isNumer" + index).style.display = "block";
        //将新增按钮和确定按钮设置为不可选
        document.getElementById("createAStu").disabled = true;
        document.getElementById("sure").disabled = true;

    }
    //数据合法
    if (reg.test(no)) {
        document.getElementById("isNumer" + index).style.display = "none";
        document.getElementById("createAStu").disabled = false;
        document.getElementById("sure").disabled = false;
    }
}
//判断信息的每一栏是否写入
function isFull() {
    if (document.getElementById("info2").value == '' ||
        document.getElementById("info3").value == '' ||
        document.getElementById("info4").value == '' ||
        document.getElementById("info5").value == '' ||
        document.getElementById("info6").value == '' ||
        document.getElementById("info7").value == '' ) {
        return false;
    } else {
        return true
    }
}

// 点击查询按钮后显示查询子页面
function checkStudentInfo(index) {
    document.getElementById("createStu").style.display = "block";
   
    readDataFromTable(index);
    document.getElementById("editorStuBottom").style.display = "block";
    document.getElementById("sure").style.display = "none";
    document.getElementById("createStuBottom").style.display = "none";
    document.getElementById("titleOfEditor").innerHTML = "查看教师信息";
    // 查询时将所有结点的状态设为不可操作
    setEditorStuDisabledTrue();

}

// 点击修改按钮后显示修改的子页面
function changeStudentInfo(index) {
    //读取鼠标点击行的信息并赋给相应的结点属性
    readDataFromTable(index);
    document.getElementById("createStu").style.display = "block";
    document.getElementById("createStuBottom").style.display = "none";
    document.getElementById("editorStuBottom").style.display = "block";
    document.getElementById("titleOfEditor").innerHTML = "修改教师信息";
    document.getElementById("info1").disabled = true;
    //设置需要修改信息学生对象在数组中的序号
    changeIndex = index;
    //将子页面的所有结点状态设置为可操作
    setEditorStuDisabledFalse();
}

// 按下确定数据按钮后
function changeData() {
     if (!isFull()) {
        alert("有信息没有录入");
        
    } else {
    document.getElementById("MyFrom").submit();
     //跟据用户输入的对象属性新建一个对象并且添加到对象数组中
    changeDataFromForm(changeIndex);
    document.getElementById("createStu").style.display = "none";
    document.getElementById("canNotEditor").style.display = "none";
    document.getElementById("editorStuBottom").style.display = "none";
    //重载表格栏
    document.getElementById('tableBody').innerHTML = addDataToTable(allData);
}
}

// 读取用户输入的对象属性，并添加到对象数组中
function changeDataFromForm(changeIndex) {
    allData[changeIndex].id = document.getElementById("info1").value;
    allData[changeIndex].name = document.getElementById("info2").value;
    allData[changeIndex].sex = document.getElementById("info3").value;
    allData[changeIndex].birthDay = document.getElementById("info4").value;
    allData[changeIndex].sarlay = document.getElementById("info5").value;
    allData[changeIndex].college = document.getElementById("info6").value;
    allData[changeIndex].major = document.getElementById("info7").value;

}
// 根据数组中指定下标的对象属性 更新子页面中的对象信息
function readDataFromTable(index) {
    document.getElementById("info1").value = allData[index].id;
    document.getElementById("info2").value = allData[index].name;
    document.getElementById("info3").value = allData[index].sex;
    document.getElementById("info4").value = allData[index].birthDay;
    document.getElementById("info5").value = allData[index].sarlay;
    document.getElementById("info6").value = allData[index].college;
    document.getElementById("info7").value = allData[index].major;
}
// 隐藏子页面
function hiddenChange() {
   
    document.getElementById("createStu").style.display = "none";
    document.getElementById("sure").style.display = "block";
    for (var i = 1; i <= 1; i++) {
        document.getElementById("isNumer" + i).style.display = "none";
    }
    setStuInfoNull();
    setEditorStuDisabledFalse();

}

// 将子页面中的文本框的属性设置为不可操作
function setEditorStuDisabledTrue() {
    for (var i = 1; i <= 7; i++) {
        document.getElementById("info" + i).disabled = true;
    }
}
// 将子页面中的文本框的属性设置为可操作
function setEditorStuDisabledFalse() {
    for (var i = 1; i <= 7; i++) {
        document.getElementById("info" + i).disabled = false;
    }

}
// 将子页面的文本框内容置为空
function setStuInfoNull() {
    for (var i = 1; i <= 6; i++) {
        document.getElementById("info" + i).value = null;
    }
}
//查询按钮事件
function searchInfo() {
    var searchStus = new Array();
    var info = document.getElementById("searchInfo").value;
    if(info==null){
        alert("请输入有效信息！！");
    }
    else{
        var j = 0;
       for(var i = 0; i < allData.length; i++){
            var StuIn = allData[i];
            if(info == StuIn.name || info == StuIn.id || info == StuIn.birthDay || info == StuIn.sarlay || info == StuIn.college || info == StuIn.major){
                searchStus[j] = allData[i];
                j++;
            }

       }
    }
    document.getElementById('tableBody').innerHTML = addDataToTable(searchStus);
}