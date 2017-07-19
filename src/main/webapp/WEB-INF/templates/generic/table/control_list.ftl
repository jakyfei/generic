<#include "head.ftl">
<div class="page-body page_ll">
    <div class="row">
        <div class="col-xs-12 col-md-12">
            <div class="widget">
                <div class="widget-header bordered-bottom L_lan">
                    <span class="widget-caption L_yase">${tableInfo.tableCNName!""}</span>
                    <div class="widget-buttons">
                        <a href="#" data-toggle="maximize" style="color: #FFFFFF;">
                            <i class="fa fa-expand"></i>
                        </a>
                    </div>
                </div>
                <input type="hidden" value="${inputValue!""}" id="inputValue"/>

                <div class="widget-body no-padding L_body"
                     style="position: relative; border: 1px #679AC6 solid !important; background: #FFFFFF !important;">
                    <div class="L_bianfd" style="width:98%;">
                        <div class="L_bianfd_left">
                            <a class="L_bianfd_left_x"
                               href='${request.contextPath}/resources/tableDatas/export/${tableInfo.tableName!""}'>导出</a>
                            <form action='${request.contextPath}/resources/tableDatas/import/${tableInfo.tableName!""}'
                                  method="post" id="myForm" style="float: left;" enctype="multipart/form-data">
                                <div class="uploader blue">
                                    <input type="text" class="filename" readonly/>
                                    <input type="button" name="file" class="button_by" value="上传..."/>
                                    <input type="file" name="multipartFile" id="multipartFile" class="by_file"
                                           size="30"/>
                                </div>
                                <input type="submit" id="submit" value="提交" class="L_bianfd_left_v_input"/>
                            </form>
                        </div>

                        <div class="L_bianfd_right" style="float:right;">
                            <input type="text" class="searchinput_1" id="searchInput"/>
                            <i class="searchicon fa fa-search L_weiz" id="searchDatas"></i>
                        </div>
                    </div>

                    <table class="table table-bordered table-hover table-striped" id="searchable">
                        <thead class="bordered-darkorange">
                        <tr role="row">
                        <#list columnNames as columnName>
                            <th class="L_tab_th">${columnName!""}</th>
                        </#list>
                            <th class="L_width" style=" width:109px; border-right:1px #dedddd solid;">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list tableDatas as tableData>
                        <tr>
							<td>${tableData.sip!""}</td>
							<td>${tableData.sport!""}</td>
							<td>${tableData.dip!""}</td>
							<td>${tableData.dport!""}</td>
                            <td>
                                <a href='${request.contextPath}/resources/tableDatas/get/${tableInfo.tableName!""}?dataId=${tableData.id!""}&tabMenu=${tableInfo.tableMenu!""}&pageNo=${pageInfo.pageNo}&pageSize=${pageInfo.pageSize}'
                                   class="L_xiug" title="修改"></a>
                                <m class="L_sm">|</m>
                                <a href="#" id="del" dataId='${tableData.id!""}' tableName='${tableInfo.tableName!""}'
                                   pageNo="${pageInfo.pageNo}" pageSize="${pageInfo.pageSize}" class="L_shanc"
                                   title="删除"></a>
                            </td>
                        <tr>
                        </#list>
                        </tbody>
                    </table>
                    <a href='${request.contextPath}/resources/tableDatas/add/${tableInfo.tableName!""}?tabMenu=${tableInfo.tableMenu!""}&pageNo=${pageInfo.pageNo}&pageSize=${pageInfo.pageSize}'
                       class="L_tianj" title="添加"></a>

                <#if pageInfo??>
                    <!--分页 -->
                    <div class="pages">
                        <div class="pagin-btn">
                            <span class="prev-disabled">
                                <a href='${request.contextPath}/resources/tableDatas/${tableInfo.tableName!""}?pageNo=1&pageSize=${pageInfo.pageSize}'>首页</a>
							</span>
                            <span class="prev-disabled">
                                <#if pageInfo.havePrevious>
                                    <a href='${request.contextPath}/resources/tableDatas/${tableInfo.tableName!""}?pageNo=${pageInfo.previousPageNo}&pageSize=${pageInfo.pageSize}'>上一页</a>
                                <#else>
                                    <a>上一页</a>
                                </#if>
                               
							</span>
                            <#assign tp=pageInfo.totalPage/>
                            <#assign p=pageInfo.pageNo/>
                            <#assign sp=p-3/>
                            <#assign ep=p+4/>
                            <#assign eoff=ep-tp/>
                            <#if (eoff>0)>
                                <#assign sp = sp - eoff/>
                            </#if>
                            <#if (sp<=0)>
                                <#assign ep = ep - sp+1/>
                            </#if>
                            <div class="nums">

                                <#if pageInfo.totalPage lte 3>
                                    <#list 1..pageInfo.pageNo as nav>
                                        <#if nav == pageInfo.pageNo>
                                            <a class="currentpage"
                                               style="font-weight: bold; background: #c5c5c5">${nav}</a>
                                        </#if>
                                        <#if nav != pageInfo.pageNo>
                                            <a class="currentpage"
                                               href='${request.contextPath}/resources/tableDatas/${tableInfo.tableName!""}?pageNo=${nav}&pageSize=${pageInfo.pageSize}'>${nav}</a>
                                        </#if>
                                    </#list>
                                <#elseif  pageInfo.totalPage gt 3>
                                    <#if pageInfo.pageNo lte 3>
                                        <#list 1..3 as nav>
                                            <#if nav == pageInfo.pageNo>
                                                <a class="currentpage"
                                                   style="font-weight: bold; background: #c5c5c5">${nav}</a>
                                            </#if>
                                            <#if nav != pageInfo.pageNo>
                                                <a class="currentpage"
                                                   href='${request.contextPath}/resources/tableDatas/${tableInfo.tableName!""}?pageNo=${nav}&pageSize=${pageInfo.pageSize}'>${nav}</a>
                                            </#if>
                                        </#list>
                                    <#elseif  pageInfo.pageNo gt 3>
                                        <#list pageInfo.pageNo-3..pageInfo.pageNo as nav>
                                            <#if nav == pageInfo.pageNo>
                                                <a class="currentpage"
                                                   style="font-weight: bold; background: #c5c5c5">${nav}</a>
                                            </#if>
                                            <#if nav != pageInfo.pageNo>
                                                <a class="currentpage"
                                                   href='${request.contextPath}/resources/tableDatas/${tableInfo.tableName!""}?pageNo=${nav}&pageSize=${pageInfo.pageSize}'>${nav}</a>
                                            </#if>
                                        </#list>

                                    </#if>
                                </#if>
                            </div>
                            <span class="prev-disabled">
                                <#if pageInfo.haveNext>
                                    <a href='${request.contextPath}/resources/tableDatas/${tableInfo.tableName!""}?pageNo=${pageInfo.nextPageNo}&pageSize=${pageInfo.pageSize}'>下一页</a>
                                <#else>
                                    <a>下一页</a>
                                </#if>
                                    
							   </span>

                            <span class="prev-disabled">
							    <a href='${request.contextPath}/resources/tableDatas/${tableInfo.tableName!""}?pageNo=${pageInfo.totalPage}&pageSize=${pageInfo.pageSize}'>尾页</a>
							   </span>
                            <p style="float:left;">
                                <input id="numberPage" type="text" class="by_inu"/>
                                <a><input id="jumpPage" type="button" value="跳转" class="by_tz"/></a>
                            </p>
                            <p style="line-height: 28px;">总页数：${pageInfo.totalPage}页</p>
                        </div>
                    </div>
                    <!--分页 end -->
                </#if>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${path}/static/assets/js/jquery-1.7.2.js"></script>
<script type="text/javascript">
    $(function () {
        $(".by_file").change(function () {
            $(this).parents(".uploader").find(".filename").val($(this).val());
        });
        $(".by_file").each(function () {
            if ($(this).val() == "") {
                $(this).parents(".uploader").find(".filename").val("请选择文件...");
            }
        });
    });
</script>
<script type="application/javascript">
    $().ready(function () {
        var inputValue = $("#inputValue").val();
        if (inputValue != null) {
            $("#searchInput").val(inputValue);
        }
    });

    $("#jumpPage").click(function () {
        var numberPage = $("#numberPage").val();
        window.location.href = '${request.contextPath}/resources/tableDatas/${tableInfo.tableName!""}?pageNo=' + numberPage;
    });

    $(".L_shanc").click(function () {
        var dataId = $(this).attr("dataId");
        var tableName = $(this).attr("tableName");
        var pageNo = $(this).attr("pageNo");
        var pageSize = $(this).attr("pageSize");
        if (confirm("确定要删除数据吗？")) {
            window.location.href = "${request.contextPath}/resources/tableDatas/delete/" + tableName + "/?dataId=" + dataId + "&pageNo=" + pageNo + "&pageSize=" + pageSize;
        }
    });

    $("#searchDatas").click(function () {
        var inputValue = $("#searchInput").val();
        if (inputValue.trim() != "") {
            window.location.href = '${request.contextPath}/resources/tableDatas/search/' + inputValue + '?tableName=${tableInfo.tableName!""}';
        }
    });

    $("#myForm").bind("submit", function () {
        var multipartFile = $("#multipartFile").val();
        var number = multipartFile.lastIndexOf(".");
        numberUrl = multipartFile.substring(number + 1);
        if (multipartFile == "") {
            alert("请选择上传文件");
            return false;
        }
        if (numberUrl == "xls" || numberUrl == "xlsx") {
            return true;
        } else {
            alert("文件格式应该为xls 或者 xlsx");
            return false;
        }
    });
</script>
<#include "footer.ftl">
