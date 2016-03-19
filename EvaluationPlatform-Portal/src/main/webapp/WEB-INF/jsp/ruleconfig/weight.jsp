<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: saber
  Date: 2016/3/18
  Time: 2:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
