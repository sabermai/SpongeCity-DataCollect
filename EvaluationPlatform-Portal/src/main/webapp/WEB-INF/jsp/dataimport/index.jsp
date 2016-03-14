<%--
  Created by IntelliJ IDEA.
  User: saber
  Date: 2016/3/14
  Time: 0:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery/jquery-1.7.1.js"></script>
    <link href="${pageContext.request.contextPath}/style/authority/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/style/authority/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/authority/commonAll.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/scripts/fancybox/jquery.fancybox-1.3.4.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/scripts/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/authority/jquery.fancybox-1.3.4.css"
          media="screen">
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/scripts/artDialog/artDialog.js?skin=default"></script>
    <title>信息管理系统</title>
    <script type="text/javascript">
        $(document).ready(function () {
            /** 新增   **/
            $("#addBtn").fancybox({
                'href': 'house_edit.html',
                'width': 733,
                'height': 530,
                'type': 'iframe',
                'hideOnOverlayClick': false,
                'showCloseButton': false,
                'onClosed': function () {
                    window.location.href = 'house_list.html';
                }
            });

            /** 导入  **/
            $("#importBtn").fancybox({
                'href': '/xngzf/archives/importFangyuan.action',
                'width': 633,
                'height': 260,
                'type': 'iframe',
                'hideOnOverlayClick': false,
                'showCloseButton': false,
                'onClosed': function () {
                    window.location.href = 'house_list.html';
                }
            });

            /**编辑   **/
            $("a.edit").fancybox({
                'width': 733,
                'height': 530,
                'type': 'iframe',
                'hideOnOverlayClick': false,
                'showCloseButton': false,
                'onClosed': function () {
                    window.location.href = 'house_list.html';
                }
            });
        });
        /** 用户角色   **/
        var userRole = '';

        /** 模糊查询来电用户  **/
        function search() {
            $("#submitForm").attr("action", "house_list.html?page=" + 1).submit();
        }

        /** 新增   **/
        function add() {
            $("#submitForm").attr("action", "/xngzf/archives/luruFangyuan.action").submit();
        }

        /** Excel导出  **/
        function exportExcel() {
            if (confirm('您确定要导出吗？')) {
                var fyXqCode = $("#fyXq").val();
                var fyXqName = $('#fyXq option:selected').text();
//	 		alert(fyXqCode);
                if (fyXqCode == "" || fyXqCode == null) {
                    $("#fyXqName").val("");
                } else {
//	 			alert(fyXqCode);
                    $("#fyXqName").val(fyXqName);
                }
                $("#submitForm").attr("action", "/xngzf/archives/exportExcelFangyuan.action").submit();
            }
        }

        /** 删除 **/
        function del(fyID) {
            // 非空判断
            if (fyID == '') return;
            if (confirm("您确定要删除吗？")) {
                $("#submitForm").attr("action", "/xngzf/archives/delFangyuan.action?fyID=" + fyID).submit();
            }
        }

        /** 批量删除 **/
        function batchDel() {
            if ($("input[name='IDCheck']:checked").size() <= 0) {
                art.dialog({icon: 'error', title: '友情提示', drag: false, resize: false, content: '至少选择一条', ok: true,});
                return;
            }
            // 1）取出用户选中的checkbox放入字符串传给后台,form提交
            var allIDCheck = "";
            $("input[name='IDCheck']:checked").each(function (index, domEle) {
                bjText = $(domEle).parent("td").parent("tr").last().children("td").last().prev().text();
// 			alert(bjText);
                // 用户选择的checkbox, 过滤掉“已审核”的，记住哦
                if ($.trim(bjText) == "已审核") {
// 				$(domEle).removeAttr("checked");
                    $(domEle).parent("td").parent("tr").css({color: "red"});
                    $("#resultInfo").html("已审核的是不允许您删除的，请联系管理员删除！！！");
// 				return;
                } else {
                    allIDCheck += $(domEle).val() + ",";
                }
            });
            // 截掉最后一个","
            if (allIDCheck.length > 0) {
                allIDCheck = allIDCheck.substring(0, allIDCheck.length - 1);
                // 赋给隐藏域
                $("#allIDCheck").val(allIDCheck);
                if (confirm("您确定要批量删除这些记录吗？")) {
                    // 提交form
                    $("#submitForm").attr("action", "/xngzf/archives/batchDelFangyuan.action").submit();
                }
            }
        }

        /** 普通跳转 **/
        function jumpNormalPage(page) {
            $("#submitForm").attr("action", "house_list.html?page=" + page).submit();
        }

        /** 输入页跳转 **/
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
                        content: '请输入合适的页数，\n自动为您跳到首页',
                        ok: true,
                    });
                    pageNum = 1;
                }
                $("#submitForm").attr("action", "house_list.html?page=" + pageNum).submit();
            } else {
                // “跳转页数”为空
                art.dialog({
                    icon: 'error',
                    title: '友情提示',
                    drag: false,
                    resize: false,
                    content: '请输入合适的页数，\n自动为您跳到首页',
                    ok: true,
                });
                $("#submitForm").attr("action", "house_list.html?page=" + 1).submit();
            }
        }
    </script>
    <style>
        .alt td {
            background: black !important;
        }
    </style>
</head>
<body>
<form id="submitForm" name="submitForm" action="" method="post">
    <input type="hidden" name="allIDCheck" value="" id="allIDCheck"/>
    <input type="hidden" name="fangyuanEntity.fyXqName" value="" id="fyXqName"/>

    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_top">
                        <p>指标惨呼运算规则及区域权重配置 :
                            <select name="fangyuanEntity.fyXqCode" id="fyXq" class="ui_select01"
                                    onchange="getFyDhListByFyXqCode();">
                                <option value=""
                                        >--请选择--
                                </option>
                                <option value="6">水生态</option>
                                　
                                <option value="83">水资源</option>
                                <option value="83">水安全</option>
                            </select>
                            　
                            <select name="fangyuanEntity.fyDhCode" id="fyDh" class="ui_select01">
                                <option value="">--请选择--</option>
                                <option value="6">年径流总量控制率</option>
                                <option value="6"> 地下水</option>
                                <option value="6"> 生态岸线恢复</option>
                                <option value="6">城市热岛效应</option>
                            </select>　
                            <input type="button" value="保存" class="ui_input_btn01" onClick="search();"/>
                        </p>
                    </div>
                    <div id="box_center">

                    </div>

                </div>
            </div>
        </div>
        <div class="ui_content">
            <div class="ui_tb"></div>
            <table class="table" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">

                <tr align="center">
                    <th colspan="4">运算规则配置</th>

                <tr>
                    <td width="12%" style="font-weight:bold">指标参数</td>
                    <td width="39%" style="font-weight:bold">

                        区域维度运算规则
                    </td>
                    <td width="38%" style="font-weight:bold">指标类型</td>
                    <td width="11%" style="font-weight:bold">时间粒度</td>

                </tr>
                <tr>
                    <td style="font-weight:bold">生态岸线长度</td>
                    <td><label class="FengCheck"><input name="radio" type="radio">
                        不运算 <span></span>　</label>
                        <label class="FengCheck"><input name="radio" type="radio">
                            取和值 <span></span></label>
                        　
                        <label class="FengCheck"><input name="radio" type="radio"> 取平均值 <span></span></label>
                        <label class="FengCheck">　
                            <input name="radio" type="radio"> 取最大值 <span></span></label>
                        　
                        <label class="FengCheck"><input name="radio" type="radio"> 取最小值<span></span></label>
                    </td>
                    <td><label class="FengCheck"><input name="radio" type="radio">
                        不运算 <span></span>　</label>
                        <label class="FengCheck"><input name="radio" type="radio">
                            取和值 <span></span></label>
                        　
                        <label class="FengCheck"><input name="radio" type="radio"> 取平均值 <span></span></label>
                        <label class="FengCheck">　
                            <input name="radio" type="radio"> 取最大值 <span></span></label>
                        　
                        <label class="FengCheck"><input name="radio" type="radio"> 取最小值<span></span></label>
                    </td>
                    <td><input type="text" id="fyZldz" name="fangyuanEntity.fyZldz" style="width:35px;height:20px"/>

                        <label>
                            <select id="select" gyuanEntity.fyZldz" style="width:65px; height:21px; "Sel">
                            <option>年</option>
                            <option>月</option>
                            <option>日</option>
                            <option>小时</option>
                            <option>分钟</option>

                            </select>
                        </label>

                        <script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>
                        <script type="text/javascript" src="js/jquery.select.js"></script>
                    </td>

                </tr>

                <tr>
                    <td style="font-weight:bold; ">盖度</td>
                    <td><label class="FengCheck"><input name="radio" type="radio">
                        不运算 <span></span>　</label>
                        <label class="FengCheck"><input name="radio" type="radio">
                            取和值 <span></span></label>
                        　
                        <label class="FengCheck"><input name="radio" type="radio"> 取平均值 <span></span></label>
                        <label class="FengCheck">　
                            <input name="radio" type="radio"> 取最大值 <span></span></label>
                        　
                        <label class="FengCheck"><input name="radio" type="radio"> 取最小值<span></span></label>
                    </td>
                    <td><label class="FengCheck"><input name="radio" type="radio">
                        不运算 <span></span>　</label>
                        <label class="FengCheck"><input name="radio" type="radio">
                            取和值 <span></span></label>
                        　
                        <label class="FengCheck"><input name="radio" type="radio"> 取平均值 <span></span></label>
                        <label class="FengCheck">　
                            <input name="radio" type="radio"> 取最大值 <span></span></label>
                        　
                        <label class="FengCheck"><input name="radio" type="radio"> 取最小值<span></span></label>
                    </td>
                    <td>
                        <input type="text" id="fyZldz2" name="fyZldz" style="width:35px;height:20px"/>


                        <label>
                            <select id="select" gyuanEntity.fyZldz" style="width:65px; height:21px; "Sel">
                            <option>年</option>
                            <option>月</option>
                            <option>日</option>
                            <option>小时</option>
                            <option>分钟</option>

                            </select>
                        </label>

                        <script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>
                        <script type="text/javascript" src="js/jquery.select.js"></script>
                    </td>

                </tr>
                <tr>
                    <td style="font-weight:bold">成活率</td>
                    <td><label class="FengCheck"><input name="radio" type="radio">
                        不运算 <span></span>　</label>
                        <label class="FengCheck"><input name="radio" type="radio">
                            取和值 <span></span></label>
                        　
                        <label class="FengCheck"><input name="radio" type="radio"> 取平均值 <span></span></label>
                        <label class="FengCheck">　
                            <input name="radio" type="radio"> 取最大值 <span></span></label>
                        　
                        <label class="FengCheck"><input name="radio" type="radio"> 取最小值<span></span></label>
                    </td>
                    <td><label class="FengCheck"><input name="radio" type="radio">
                        不运算 <span></span>　</label>
                        <label class="FengCheck"><input name="radio" type="radio">
                            取和值 <span></span></label>
                        　
                        <label class="FengCheck"><input name="radio" type="radio"> 取平均值 <span></span></label>
                        <label class="FengCheck">　
                            <input name="radio" type="radio"> 取最大值 <span></span></label>
                        　
                        <label class="FengCheck"><input name="radio" type="radio"> 取最小值<span></span></label>
                    </td>
                    <td><input type="text" id="fyZldz2" name="fyZldz" style="width:35px;height:20px"/>

                        <label>
                            <select id="select" gyuanEntity.fyZldz" style="width:65px; height:21px; "Sel">
                            <option>年</option>
                            <option>月</option>
                            <option>日</option>
                            <option>小时</option>
                            <option>分钟</option>

                            </select>
                        </label>

                        <script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>
                        <script type="text/javascript" src="js/jquery.select.js"></script>
                    </td>

                </tr>
                <tr>
                    <td style="font-weight:bold">

                        物种丰富度
                    </td>
                    <td><label class="FengCheck"><input name="radio" type="radio">
                        不运算 <span></span>　</label>
                        <label class="FengCheck"><input name="radio" type="radio">
                            取和值 <span></span></label>
                        　
                        <label class="FengCheck"><input name="radio" type="radio"> 取平均值 <span></span></label>
                        <label class="FengCheck">　
                            <input name="radio" type="radio"> 取最大值 <span></span></label>
                        　
                        <label class="FengCheck"><input name="radio" type="radio"> 取最小值<span></span></label>
                    </td>
                    <td><label class="FengCheck"><input name="radio" type="radio">
                        不运算 <span></span>　</label>
                        <label class="FengCheck"><input name="radio" type="radio">
                            取和值 <span></span></label>
                        　
                        <label class="FengCheck"><input name="radio" type="radio"> 取平均值 <span></span></label>
                        <label class="FengCheck">　
                            <input name="radio" type="radio"> 取最大值 <span></span></label>
                        　
                        <label class="FengCheck"><input name="radio" type="radio"> 取最小值<span></span></label>
                    </td>
                    <td><input type="text" id="fyZldz" name="fangyuanEntity.fyZldz" style="width:35px;height:20px"/>


                        <label>
                            <select id="select" gyuanEntity.fyZldz" style="width:65px; height:21px; "Sel">
                            <option>年</option>
                            <option>月</option>
                            <option>日</option>
                            <option>小时</option>
                            <option>分钟</option>

                            </select>
                        </label>

                        <script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>
                        <script type="text/javascript" src="js/jquery.select.js"></script>
                    </td>

                </tr>
                <tr>
                    <td style="font-weight:bold">物种多度</td>
                    <td><label class="FengCheck"><input name="radio" type="radio">
                        不运算 <span></span>　</label>
                        <label class="FengCheck"><input name="radio" type="radio">
                            取和值 <span></span></label>
                        　
                        <label class="FengCheck"><input name="radio" type="radio"> 取平均值 <span></span></label>
                        <label class="FengCheck">　
                            <input name="radio" type="radio"> 取最大值 <span></span></label>
                        　
                        <label class="FengCheck"><input name="radio" type="radio"> 取最小值<span></span></label>
                    </td>
                    <td><label class="FengCheck"><input name="radio" type="radio">
                        不运算 <span></span>　</label>
                        <label class="FengCheck"><input name="radio" type="radio">
                            取和值 <span></span></label>
                        　
                        <label class="FengCheck"><input name="radio" type="radio"> 取平均值 <span></span></label>
                        <label class="FengCheck">　
                            <input name="radio" type="radio"> 取最大值 <span></span></label>
                        　
                        <label class="FengCheck"><input name="radio" type="radio"> 取最小值<span></span></label>
                    </td>
                    <td><input type="text" id="fyZldz" name="fangyuanEntity.fyZldz" style="width:35px;height:20px"/>

                        <label>
                            <select id="select" gyuanEntity.fyZldz" style="width:65px; height:21px; "Sel">
                            <option>年</option>
                            <option>月</option>
                            <option>日</option>
                            <option>小时</option>
                            <option>分钟</option>

                            </select>
                        </label>


                    </td>

                </tr>


            </table>
            <div id="box_center">


            </div>
        </div>
    </div>
    </div>

    <table class="table" cellspacing="0" cellpadding="0" style="padding-left:30px;" width="50%" align="left" border="0">

        <tr align="left">
            <th colspan="4" align="left">权重配置</th>

        <tr>
            <td width="12%" style="font-weight:bold">区域</td>
            <td width="16%" style="font-weight:bold">

                地段
            </td>
            <td width="15%" style="font-weight:bold">单项措施</td>
            <td width="12%" style="font-weight:bold">权重</td>

        </tr>
        <tr>
            <td>4#区域</td>
            <td>
            </td>
            <td>
            </td>
            <td><input type="text" id="fyZldz" name="fangyuanEntity.fyZldz" style="width:35px;height:20px"/>

            </td>

        </tr>

        <tr>
            <td>4#区域</td>
            <td>同德佳苑</td>
            <td>


            </td>
            <td><input type="text" id="fyZldz" name="fangyuanEntity.fyZldz" style="width:35px;height:20px"/>

            </td>
        </tr>
        <tr>
            <td>4#区域</td>
            <td>同德佳苑</td>
            <td>
            </td>
            <td><input type="text" id="fyZldz" name="fangyuanEntity.fyZldz" style="width:35px;height:20px"/>

            </td>
        </tr>
        <tr>
            <td>4#区域</td>
            <td>同德佳苑</td>
            <td>雨水花园
            </td>
            <td><input type="text" id="fyZldz" name="fangyuanEntity.fyZldz" style="width:35px;height:20px"/>

            </td>

        </tr>
        <tr>
            <td>4#区域</td>
            <td>管委会</td>
            <td>雨水花园
            </td>
            <td><input type="text" id="fyZldz" name="fangyuanEntity.fyZldz" style="width:35px;height:20px"/>
            </td>

        </tr>
        <tr>
            <td>4#区域</td>
            <td>管委会</td>
            <td>绿色屋顶
            </td>
            <td><input type="text" id="fyZldz" name="fangyuanEntity.fyZldz" style="width:35px;height:20px"/>
            </td>

        </tr>


    </table>
    <div id="box_center">


    </div>
    </div>
    </div>
    </div>


</form>


</body>
</html>
