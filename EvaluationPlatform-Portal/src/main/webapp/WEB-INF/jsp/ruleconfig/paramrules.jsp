<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: saber
  Date: 2016/3/18
  Time: 1:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">
    $(function() {
        var paramList = ${paramrules};
        $.each(paramList, function (i, item) {
            var pid = item.id;
            var areatd = "#areaRule" + pid + " [value='" + item.areaRule + "']";
            $(areatd).attr("checked", "checked");
            var timetd = "#timeRule" + pid + " [value='" + item.timeRule + "']";
            $(timetd).attr("checked", "checked");
            var gn = "#gn" + pid;
            $(gn).val(item.grainnumber);
            var grain = "#select" + pid + " [value='" + item.grain + "']";
            $(grain).attr("selected", true);
        })
    })
</script>
<table class="table" id="ruleTable" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
    <tr>
        <th width="12%" style="font-weight:bold">指标参数</th>
        <th width="39%" style="font-weight:bold">区域维度运算规则</th>
        <th width="38%" style="font-weight:bold">时间维度运算规则</th>
        <th width="11%" style="font-weight:bold">时间粒度</th>
    </tr>

    <c:forEach items="${paramlist}" var="par">
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
                <input type="text" class="grainnumber" id="gn${par.id}" style="width:35px;height:20px" onkeyup="grainnumberchange(this.value)"/>
                <label>
                    <select name="grain${par.id}" class="grain" id="select${par.id}" onchange="grainchange(this.value)"
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
