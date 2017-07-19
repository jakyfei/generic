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
                <form name="form" id="form"
                      action='${request.contextPath}/resources/tableDatas/update/${tableInfo.tableName}?dataId=${dataId!""}'
                      method="post">
                    <div class="widget-body no-padding L_body"
                         style="position: relative; border: 1px #679AC6 solid !important;">
                        <input type="hidden" name="pageNo" value="${pageInfo.pageNo}">
                        <input type="hidden" name="pageSize" value="${pageInfo.pageSize}">
                    
						<div class="add_1" style="margin-top:38px;">
							<p class="add_1_p1">ip：</p>
							<input type="text" name="ip" value="${tableData.ip!""}" class="must tabulation-input" id="ip" placeholder="请输入ip" />
							<span class="prompt">* 请输入ip</span>
						</div>

						<div class="add_1">
							<p class="add_1_p1">设备名称：</p>
							<input type="text" name="name" value="${tableData.name!""}" class="must tabulation-input" id="name" placeholder="请输入设备名称" />
							<span class="prompt">* 请输入设备名称</span>
						</div>

						<div class="add_1">
							<p class="add_1_p1">设备编号：</p>
							<input type="text" name="num" value="${tableData.num!""}" class="must tabulation-input" id="num" placeholder="请输入设备编号" />
							<span class="prompt">* 请输入设备编号</span>
						</div>

						<div class="add_1">
							<p class="add_1_p1">设备类型：</p>
							<input type="text" name="role" value="${tableData.role!""}" class="must tabulation-input" id="role" placeholder="请输入设备类型" />
							<span class="prompt">* 请输入设备类型</span>
						</div>

						<div class="add_1">
							<p class="add_1_p1">安全域：</p>
							<input type="text" name="domain" value="${tableData.domain!""}" class="must tabulation-input" id="domain" placeholder="请输入安全域" />
							<span class="prompt">* 请输入安全域</span>
						</div>

						<div class="add_1">
							<p class="add_1_p1">部门：</p>
							<input type="text" name="dept" value="${tableData.dept!""}" class="must tabulation-input" id="dept" placeholder="请输入部门" />
							<span class="prompt">* 请输入部门</span>
						</div>

						<div class="add_1">
							<p class="add_1_p1">责任人：</p>
							<input type="text" name="principal" value="${tableData.principal!""}" class="must tabulation-input" id="principal" placeholder="请输入责任人" />
							<span class="prompt">* 请输入责任人</span>
						</div>

                        <div class="button">
                            <div class="button_1">
                                <input type="submit" id="submit" value="立即保存" class="button_1_button"/>
                            </div>
                            <div class="button_1">
                                <input type="button" id="reset" value="重新设置" class="button_2_button"/>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function () {
        (".tabulation-input").focusin(function () {
            $(this).addClass("lll");
        }).focusout(function () {
            $(".tabulation-input").removeClass("lll");
            if ($(this).val() === "" || $(this).val === "undefined") {
                $(this).css("border", "1px solid #ff7d7d");
                $(this).siblings("span").css("color", "#ff7d7d");
            } else {
                $(this).css("border", "1px solid #e2e2e2");
                $(this).siblings("span").css("color", "#888");
            }
        });
    });
</script>
<#include "footer.ftl">
