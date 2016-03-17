<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery/jquery-1.7.1.js"></script>
    <link href="${pageContext.request.contextPath}/css/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/css/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/authority/commonAll.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/fancybox/jquery.fancybox-1.3.4.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/scripts/fancybox/jquery.fancybox-1.3.4.pack.js" media="screen">
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/artDialog/artDialog.js?skin=default"></script>

    <script src="${pageContext.request.contextPath}/uploadify/jquery.uploadify.min.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/uploadify/uploadify.css">
    <title>数据导入平台</title>
    <script type="text/javascript">
        $(document).ready(function () {
            checkLogPageIndex();
            var timestamp = new Date().getTime();
            $('#file_upload').uploadify({
                'formData': {
                    'timestamp': timestamp,
                    'token': 'unique_salt' + timestamp
                },// 设置想后台传递的参数 如果设置该参数，那么method应该设置为get，才能得到参数
                'swf': '${pageContext.request.contextPath}/uploadify/uploadify.swf',// 指定swf文件
                'uploader': 'UpFileServlet',// 后台处理的页面
                'cancelImg': '${pageContext.request.contextPath}/uploadify/uploadify-cancel.png',// 取消按钮图片路径
                "queueID": 'queue',// 上传文件页面中，你想要用来作为文件队列的元素的id, 默认为false  自动生成,  不带#
                'method': 'get',// 设置上传格式
                'auto': false,// 当选中文件后是否自动提交
                'multi': true,// 是否支持多个文件上传
                'simUploadLimit': 2,
                'buttonText': '选择文件',// 按钮显示的文字
                'onUploadSuccess': function (file, data, response) {// 上传成功后执行
                    $('#' + file.id).find('.data').html(' 上传完毕');
                }
            });
        });

        function checkLogPageIndex() {
            var curPage = $("#currentPage").text();
            if (curPage == 1) {
                $("#prevPage").attr("disabled", "disabled");
            } else {
                $("#prevPage").removeAttr("disabled");
            }
            if (curPage == ${logPageCount}) {
                $("#nextPage").attr("disabled", "disabled");
            } else {
                $("#nextPage").removeAttr("disabled");
            }
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
                            $("#Mea").append("<option value='-1'>--请选择--</option>")
                            $.each(data, function (i, item) {
                                $("#Mea").append("<option value=" + item.id + ">" + item.name + "</option>");
                            });
                        }
                    }
            )
        }

        function deleteData(logId, tableName) {
            // 非空判断
            if (logId == '') return;
            if (confirm("您确定要删除吗？")) {
                $.ajax({
                            url: "/dataimport/deletedata?logId=" + logId + "&tableName=" + tableName,
                            type: "post",
                            async: true,
                            dataType: "text",
                            success: function (data) {
                                if (data != -1) {
                                    var id = "#log" + logId;
                                    $(id).remove();
                                }
                            }
                        }
                )
            }
        }

        function jumpFirstPage() {
            loadLogData(1);
        }
        function jumpLastPage() {
            loadLogData(${logPageCount});
        }
        function jumpPrevPage() {
            var curPage = new Number($("#currentPage").text()) - 1;
            loadLogData(curPage);
        }
        function jumpNextPage() {
            var curPage = new Number($("#currentPage").text()) + 1;
            loadLogData(curPage);
        }

        function loadLogData(curPage) {
            $("#currentPage").text(curPage);
            $.ajax({
                        url: "/log/index?pageIndex=" + curPage,
                        type: "get",
                        async: true,
                        dataType: "json",
                        success: function (data) {
                            $(".trLog").remove();
                            $.each(data, function (i, item) {
                                $("#logTable").append("<tr id=" + item.id + " class='trLog'><td>" + item.importtime + "</td><td>" + item.filename + "</td><td>" + item.taxname + "</td><td>" + item.measurename + "</td><td width='15%'><input type='button' value='删除' class='ui_input_btn03' onClick=deleteData('" + item.id + "','" + item.tablename + "')></td></tr>");
                            });
                            checkLogPageIndex();
                        }
                    }
            )
        }

        function jumpInputPage(totalPage) {
            // 如果“跳转页数”不为空
            if ($("#jumpNumTxt").val() != '') {
                var pageNum = parseInt($("#jumpNumTxt").val());
                // 如果跳转页数在不合理范围内，则置为1
                if (pageNum < 1 | pageNum > totalPage) {
                    art.dialog({
                        icon: 'error',
                        title: '友情提示',
                        drag: false,
                        resize: false,
                        content: '请输入合适的页数!',
                        ok: true,
                    });
                } else {
                    loadLogData(pageNum);
                }
            } else {
                // “跳转页数”为空
                art.dialog({
                    icon: 'error',
                    title: '友情提示',
                    drag: false,
                    resize: false,
                    content: '请输入合适的页数!',
                    ok: true,
                });
            }
        }
    </script>
</head>
<body>
<div id="uploadFile" name="uploadFile">
    <input type="hidden" name="allIDCheck" value="" id="allIDCheck"/>
    <input type="hidden" name="fangyuanEntity.fyXqName" value="" id="fyXqName"/>

    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_top">
                        <p style="font-weight:bold; font-family:'微软雅黑'; font-size: 16.5px;" align="center">文件上传
                            <a href="#" class="button white"> 登出 </a>
                        </p>

                        <p>
                            <select name="taxnonmy" id="Tax" class="ui_select01" onchange="taxChange()">
                                <option value="-1">--请选择--</option>
                                <c:forEach items="${taxs}" var="tax">
                                    <option value="${tax.id}">${tax.name}</option>
                                </c:forEach>
                            </select>

                            <select name="measures" id="Mea" class="ui_select01">
                                <option value='-1'>--请选择--</option>
                            </select>
                            <%--<a href="javascript:;" class="a-upload">
                                <input type="file" name="" id="">
                            </a>
                            <input type="button" value="上传" class="ui_input_btn01" onClick="search();"/>--%>

                            <input id="file_upload" name="file_upload" type="file" multiple="true"/>
                            <a href="javascript:$('#file_upload').uploadify('upload')">开始上传</a>&nbsp;
                            <a href="javascript:$('#file_upload').uploadify('cancel')">取消所有上传</a>

                        </p>
                    </div>
                    <div id="box_center">
                    </div>
                </div>
            </div>
        </div>
        <div class="ui_content">
            <div class="ui_tb"></div>
            <table class="table" id="logTable" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
                <p>数据上传日志</p>

                <div style="padding-bottom:10px;">
                </div>

                <tr>
                    <th width="17%" style="font-weight:bold ; color:#000">上传时间</th>
                    <th width="36%" style="font-weight:bold">文件名
                    </td>
                    <th width="16%" style="font-weight:bold">指标类型
                    </td>
                    <th width="16%" style="font-weight:bold">指标名称
                    </td>
                    <th width="15%" style="font-weight:bold">操作
                    </td>
                </tr>
                <c:forEach items="${logs}" var="log">
                    <tr id="log${log.id}" class="trLog">
                        <td>${log.importtime}</td>
                        <td>${log.filename}</td>
                        <td>${log.taxname}</td>
                        <td>${log.measurename}</td>
                        <td width="15%"><input type="button" value="删除" class="ui_input_btn03"
                                               onClick="deleteData('${log.id}','${log.tablename}')"></td>
                    </tr>
                </c:forEach>
            </table>
            <div id="box_centery">

            </div>
        </div>
        <div class="ui_tb_h30">
            <div class="ui_flt" style="height: 30px; line-height: 30px;">

            </div>
            <div class="ui_frt">
                <!--    如果是第一页，则只显示下一页、尾页 -->

                <input type="button" value="首页" id="firstPage" class="ui_input_btn01"
                       onclick="jumpFirstPage()"/>
                <input type="button" value="上一页" id="prevPage" class="ui_input_btn01"
                       onclick="jumpPrevPage()"/>
                <input type="button" value="下一页" id="nextPage" class="ui_input_btn01"
                       onclick="jumpNextPage()"/>
                <input type="button" value="尾页" id="lastPage" class="ui_input_btn01"
                       onclick="jumpLastPage()"/>

                <!--     如果是最后一页，则只显示首页、上一页 -->

                当前第<label id="currentPage">1</label>页 共${logPageCount}页 转到第<input type="text" id="jumpNumTxt"
                                                                                  class="ui_input_txt01"/>页
                <input type="button" class="ui_input_btn01" value="跳转" onclick="jumpInputPage('${logPageCount}');"/>
            </div>
        </div>
    </div>
</div>
<div style="display:none">
    <script src='http://v7.cnzz.com/stat.php?id=155540&web_id=155540' language='JavaScript' charset='gb2312'></script>
</div>
</body>
</html>