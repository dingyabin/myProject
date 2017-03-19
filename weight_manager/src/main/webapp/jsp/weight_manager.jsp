<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
                                        <title>亚宾体重管理系统</title>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="icon" href="image/ico/cat.ico" type="image/x-icon">
    <link href="js/jquery-easyui-1.5.1/themes/default/easyui.css" rel="stylesheet" type="text/css">
    <link href="js/jquery-easyui-1.5.1/themes/icon.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="js/jquery-easyui-1.5.1/jquery.min.js"></script>
    <script type="text/javascript" src="js/jquery-easyui-1.5.1/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="js/myjs/myjs.js"></script>
    <script type="text/javascript" src="js/jquery-easyui-1.5.1/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript">
        $(function () {
            createDatagrid();
            creadWindow();
        });

        function createDatagrid() {
            var param={
                "fromDate":"2017-03-01"
            };
            $('#divcasebox').datagrid({
                title:'身体状况记录表',
                width:'50%',
                height:$(window).height()-23,
                striped: true,
                url:"http://localhost:8080/weight/query",
                queryParams:param,
                loadMsg:"正在玩命加载中，请稍等........",
                autoRowHeight: true,
                singleSelect: true,
                rownumbers: true,
                pagination: true,
                nowrap: false,
                pageSize: 10,
                pageList: [10, 20, 50],
                columns: [[
                    { field: 'id', title: 'id', halign: 'center', align: 'center' ,hidden:true},
                    { field: 'weight', title: '体重(市斤)', width: '30%', halign: 'center', align: 'center' },
                    { field: 'waist', title: '腰围(厘米)', width: '30%', halign: 'center', align: 'center' },
                    { field: 'createTime', title: '日期', width:'42%', halign: 'center', align: 'center' }
                ]],
                toolbar:'#tb'
            });
        }


        function creadWindow(){
            $('#addOneWindow').dialog({
                title: '请如实填写记录',
                width: 400,
                height: 230,
                closed: true,
                cache: false,
                modal: true,
                onClose:clearInfo
            });
        }


        function openWindow() {
            $("#tips").html("");
            $('#addOneWindow').window('open');;
        }


        function closeWindow() {
            clearInfo();
            $("#tips").html("");
            $('#addOneWindow').window('close');;
        }


        function loadDate() {
            var param={
                    "fromDate":$("#from").datebox('getValue'),
                    "endDate":$("#to").datebox('getValue')
            };
            $('#divcasebox').datagrid('load',param);
        }


        function clears() {
            $("#from").datebox('setValue',"");
            $("#to").datebox('setValue',"");
        }
        
        
        function clearInfo() {
            $("#weight").numberbox("clear");
            $("#waist").numberbox("clear");
        }


        function saveData() {
            var param={
                "weight":$("#weight").numberbox('getValue'),
                "waist": $("#waist").numberbox('getValue')
            };
            if(param.weight==""||param.waist==""){
                $("#tips").html("请将数据填写完整！");
                return;
            }
            jQuery.post("http://localhost:8080/weight/save", param ,function (data){
                if(data.success){
                    closeWindow();
                    loadDate();
                }else{
                    $("#tips").html(data.message);
                }
            })
        }
    </script>
</head>


    <body style="font-family:微软雅黑">
            <div id="tb" style="padding:5px;height:auto" align="center">
                <div>
                    从: <input id="from" class="easyui-datebox" style="width:140px" editable="false">&nbsp;
                    至: <input id="to"   class="easyui-datebox" style="width:140px" editable="false">
                    <a  href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="loadDate()">查询</a>
                    <a  href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="openWindow()">添加</a>
                    <a  href="#" class="easyui-linkbutton" iconCls="icon-clear" onclick="clears()">清空</a>
                </div>
            </div>

            <div id="addOneWindow" style="padding:5px">
                <form style="padding:10px 20px 10px 40px;">
                    <div style="padding:5px;text-align:center">
                        <span id="tips" style="color: red"></span>
                        <p>体重: <input id="weight" type="text" class="easyui-numberbox" data-options="min:0,precision:2"/>&nbsp;市斤</p>
                        <p>腰围: <input id="waist" type="text"  class="easyui-numberbox" data-options="min:0,precision:2"/>&nbsp;厘米</p>
                    </div>
                    <div style="padding:5px;text-align:center">
                        <a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveData()">确定</a>
                        <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeWindow()">取消</a>
                        <a href="#" class="easyui-linkbutton" iconCls="icon-clear" onclick="clearInfo()">清空</a>
                    </div>
                </form>
            </div>
            <table id="divcasebox"></table>
    </body>

</html>
