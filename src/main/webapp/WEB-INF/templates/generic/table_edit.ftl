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
                    ${replace-tableEdit.property}

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