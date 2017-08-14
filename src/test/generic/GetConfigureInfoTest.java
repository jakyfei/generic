package generic;

import com.alibaba.fastjson.JSON;
import com.topsec.tsm.generic.core.Configure;
import com.topsec.tsm.generic.core.Generator;
import org.junit.Test;

import java.util.List;

/**
 * Created by yue_tf on 2017/3/29
 */
public class GetConfigureInfoTest {

    @Test
    public  void test() {
        List<Configure> list = Generator.getConfigureInfo();
        System.out.println(JSON.toJSONString(list));
    }

}
