package generic;

import com.topsec.tsm.generic.core.Configure;
import com.topsec.tsm.generic.core.Generator;
import com.topsec.tsm.generic.pojo.Property;
import com.topsec.tsm.generic.pojo.Table;
import org.junit.Test;

import java.util.List;

/**
 * Created by Coffee on 2017/6/13.
 */
public class GenTemplatesTest {

    @Test
    public void test(){
        List<Configure> list = Generator.getConfigureInfo();
        for (Configure configure : list) {
            List<Property> properties = configure.getPropertys();
            Table table = configure.getTableInfo();
            StringBuilder replaceList = new StringBuilder();
            for (Property property : properties) {
                replaceList.append("\n\t\t\t\t\t\t\t<td>${tableData." + property.getEngName() + "!\"\"}</td>");
            }
            Generator.genListTemplate(replaceList.toString(), table.getClassShortName());
            StringBuilder replaceEdit = new StringBuilder();
            for(int i = 0; i < properties.size(); i++){
                if(i == 0){
                    replaceEdit.append("\n\t\t\t\t\t\t<div class=\"add_1\" style=\"margin-top:38px;\">");
                    replaceEdit.append("\n\t\t\t\t\t\t\t<p class=\"add_1_p1\">").append(properties.get(i).getCnName()).append("：</p>");
                    replaceEdit.append("\n\t\t\t\t\t\t\t<input type=\"text\" name=\"").append(properties.get(i).getEngName()).append("\" value=\"${tableData.").append(properties.get(i).getEngName()).append("!\"\"}\" class=\"must tabulation-input\" id=\"").append(properties.get(i).getEngName()).append("\" placeholder=\"请输入").append(properties.get(i).getCnName()).append("\" />");
                    replaceEdit.append("\n\t\t\t\t\t\t\t<span class=\"prompt\">* 请输入").append(properties.get(i).getCnName()).append("</span>");
                    replaceEdit.append("\n\t\t\t\t\t\t</div>");
                }else{
                    replaceEdit.append("\n");
                    replaceEdit.append("\n\t\t\t\t\t\t<div class=\"add_1\">");
                    replaceEdit.append("\n\t\t\t\t\t\t\t<p class=\"add_1_p1\">").append(properties.get(i).getCnName()).append("：</p>");
                    replaceEdit.append("\n\t\t\t\t\t\t\t<input type=\"text\" name=\"").append(properties.get(i).getEngName()).append("\" value=\"${tableData.").append(properties.get(i).getEngName()).append("!\"\"}\" class=\"must tabulation-input\" id=\"").append(properties.get(i).getEngName()).append("\" placeholder=\"请输入").append(properties.get(i).getCnName()).append("\" />");
                    replaceEdit.append("\n\t\t\t\t\t\t\t<span class=\"prompt\">* 请输入").append(properties.get(i).getCnName()).append("</span>");
                    replaceEdit.append("\n\t\t\t\t\t\t</div>");
                }
            }
            Generator.genEditTemplate(replaceEdit.toString().toString(), table.getClassShortName());
            System.out.println(table.getTableName());
        }
    }
}
