<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery/jquery-1.7.1.js"></script>
    <link href="${pageContext.request.contextPath}/style/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/style/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/authority/commonAll.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/scripts/fancybox/jquery.fancybox-1.3.4.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/scripts/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/scripts/artDialog/artDialog.js?skin=default"></script>
    <title>数据导入平台</title>
    <script type="text/javascript">
        $(function () {
            var paramList = ${paramRuleList};
            radioInit(paramList);
        });

        function radioInit(data) {
            $.each(data, function (i, item) {
                var pid = item.id;
                var areatd = "#areaRule" + pid + " [value='" + item.areaRule + "']";
                $(areatd).attr("checked", "checked");
                var timetd = "#timeRule" + pid + " [value='" + item.timeRule + "']";
                $(timetd).attr("checked", "checked");
                var gn = "#gn" + pid;
                $(gn).val(item.grainnumber);
                var grain = "#select" + pid + " [value='" + item.grain + "']";
                $(grain).attr("selected", true);
            });
        }

        function taxChange() {
            var taxId = $("#Tax").val();
            $.ajax({
                        url: "/measures/measureByTax?taxId=" + taxId,
                        type: "get",
                        async: true,
                        dataType: "json",
                        success: function (data) {
                            $("#Mea").empty();
                            $.each(data, function (i, item) {
                                $("#Mea").append("<option value=" + item.id + ">" + item.name + "</option>");
                            });
                        }
                    }
            )
        }

        function meaChange() {
            var measureId = $("#Mea").val();
            $.ajax({
                        url: "/ruleconfig/paramrules?measureId=" + measureId,
                        type: "get",
                        async: true,
                        dataType: "text",
                        success: function (data) {
                            //$(".rules").remove();
                            $("#ruleTable").html(data);
                        }
                    }
            )
            $.ajax({
                        url: "/ruleconfig/weights?measureId=" + measureId,
                        type: "get",
                        async: true,
                        dataType: "text",
                        success: function (data) {
                            $("#weightTable").html(data);
                        }
                    }
            )
        }

        function saveData() {
            updateRule();
            updateWeight();
        }

        function updateRule() {
            var paramRuleArray = new Array();
            $(".rules").each(function () {
                var pid = $(this).find("td:eq(0)").attr("title");
                var arearule = $(this).find("td:eq(1)").children().find(":checked").attr("value");
                var timerule = $(this).find("td:eq(2)").children().find(":checked").attr("value");
                var grain = $(this).find("td:eq(3)").children().find(":checked").attr("value");
                var gn = $(this).find("td:eq(3)").children().first().val();
                paramRuleArray.push({pid: pid, areaRule: arearule, timeRule: timerule, grain: grain, grainnumber: gn});
            });
            var jsonData = JSON.stringify(paramRuleArray);
            $.ajax({
                url: "/ruleconfig/updaterules",
                type: "POST",
                contentType: 'application/json;charset=utf-8',
                dataType: "json",
                data: jsonData,
                success: function (data) {
                }
            });
        }

        function updateWeight() {
            var weightArray = new Array();
            $(".weis").each(function () {
                var id = $(this).attr("title");
                var weight = $(this).find("input").val();
                alert(id + weight);
                weightArray.push({id: id, weight: weight});
            });
            var jsonData = JSON.stringify(weightArray);
            $.ajax({
                url: "/ruleconfig/updateweights",
                type: "POST",
                contentType: 'application/json;charset=utf-8',
                dataType: "json",
                data: jsonData,
                success: function (data) {
                }
            });
        }

        function dataimport(){
            window.location="/dataimport/index";
        }
    </script>
    <style>
        .alt td {
            background: black !important;
        }
    </style>
</head>
<body>
<form id="submitForm" name="submitForm">
    <input type="hidden" name="allIDCheck" value="" id="allIDCheck"/>
    <input type="hidden" name="fangyuanEntity.fyXqName" value="" id="fyXqName"/>

    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_top">
                        <p style="font-weight:bold; font-family:'微软雅黑'; font-size: 16.5px;" align="center">
                            指标参数运算规则及区域权重配置
                            <label onclick="dataimport()" class="button white"> 数据导入 </label>
                        </p>

                        <p>
                            <select name="taxnonmy" id="Tax" class="ui_select01" onchange="taxChange()">
                                <c:forEach items="${taxs}" var="tax">
                                    <option value="${tax.id}">${tax.name}</option>
                                </c:forEach>
                            </select>

                            <select name="measures" id="Mea" class="ui_select01" onchange="meaChange()">
                                <c:forEach items="${measures}" var="mea">
                                    <option value="${mea.id}">${mea.name}</option>
                                </c:forEach>
                            </select>
                            　
                            <input type="button" value="保存" class="ui_input_btn01" onClick="saveData();"/>
                        </p>
                    </div>
                    <div id="box_center"></div>
                </div>
            </div>
        </div>
        <div class="ui_content">
            <div class="ui_tb"></div>
            <p>运算规则配置</p>

            <div style="padding-bottom:10px;">
            </div>

            <table class="table" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
                <tr>
                    <td>
                        <table class="table" id="ruleTable" cellspacing="0" cellpadding="0" width="100%" align="center"
                               border="0">
                            <tr>
                                <th width="12%" style="font-weight:bold">指标参数</th>
                                <th width="39%" style="font-weight:bold">区域维度运算规则</th>
                                <th width="38%" style="font-weight:bold">指标类型</th>
                                <th width="11%" style="font-weight:bold">时间粒度</th>
                            </tr>

                            <c:forEach items="${paramList}" var="par">
                                <tr class="rules">
                                    <td style="font-weight:bold" title="${par.id}">${par.displayname}</td>
                                    <td id="areaRule${par.id}">
                                        <label class="FengCheck">
                                            <input name="arearadio${par.id}" type="radio" value="0">
                                            不运算 <span></span> </label>&nbsp;
                                        <label class="FengCheck">
                                            <input name="arearadio${par.id}" type="radio" value="1">
                                            取和值 <span></span></label>&nbsp;
                                        <label class="FengCheck">
                                            <input name="arearadio${par.id}" type="radio" value="2">
                                            取平均值 <span></span></label>&nbsp;
                                        <label class="FengCheck">
                                            <input name="arearadio${par.id}" type="radio" value="3">
                                            取最大值 <span></span></label>&nbsp;
                                        <label class="FengCheck">
                                            <input name="arearadio${par.id}" type="radio" value="4">
                                            取最小值<span></span></label>&nbsp;</td>
                                    <td id="timeRule${par.id}">
                                        <label class="FengCheck">
                                            <input name="timeradio${par.id}" type="radio" value="0">
                                            不运算 <span></span> </label>&nbsp;
                                        <label class="FengCheck">
                                            <input name="timeradio${par.id}" type="radio" value="1">
                                            取和值 <span></span></label>&nbsp;
                                        <label class="FengCheck">
                                            <input name="timeradio${par.id}" type="radio" value="2">
                                            取平均值 <span></span></label>&nbsp;
                                        <label class="FengCheck">
                                            <input name="timeradio${par.id}" type="radio" value="3">
                                            取最大值 <span></span></label>&nbsp;
                                        <label class="FengCheck">
                                            <input name="timeradio${par.id}" type="radio" value="4">
                                            取最小值<span></span></label>&nbsp;</td>
                                    <td id="grain${par.id}">
                                        <input type="text" id="gn${par.id}" style="width:35px;height:20px"  onkeyup="value=this.value.replace(/\D+/g,'')"/>
                                        <label>
                                            <select name="grain${par.id}" id="select${par.id}"
                                                    style="width:65px; height:21px;">
                                                <option value="0">年</option>
                                                <option value="1">月</option>
                                                <option value="2">日</option>
                                                <option value="3">小时</option>
                                                <option value="4">分钟</option>
                                            </select>
                                        </label>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </td>
                </tr>
            </table>
            <div id=" box_centery">

            </div>
        </div>

    </div>
    <div style="padding-left:28px; padding-bottom:-20px;">
        <p> 权重配置</p></div>

    <div style="padding-bottom:10px;">
    </div>

    <table class="table" id="weightTable" cellspacing="0" cellpadding="0" style="padding-left:26px; margin-top:0px;"
           width="50%"
           align="left" border="0">
        <tr>
            <th width="12%" style="font-weight:bold">区域</th>
            <th width="16%" style="font-weight:bold">地段</th>
            <th width="15%" style="font-weight:bold">单项措施</th>
            <th width="12%" style="font-weight:bold">权重</th>
        </tr>
        <c:forEach items="${weightrules}" var="wei">
            <tr class="weis" title="${wei.id}">
                <td>${wei.region}</td>
                <td>${wei.section}</td>
                <td>${wei.device}</td>
                <td><input type="text" value="${wei.weight}" style="width:35px;height:20px" onkeyup="value=this.value.replace(/\D+/g,'')"/></td>
            </tr>
        </c:forEach>
    </table>
    <div id="box_centery"></div>
</form>


</body>
</html>
