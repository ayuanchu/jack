import com.kancy.mybatisplus.generator.core.xmind.XMind;
import com.kancy.mybatisplus.generator.core.xmind.XMindNode;
import com.kancy.mybatisplus.generator.utils.FileUtils;

import java.util.ArrayList;

/**
 * <p>
 * XMindTests
 * <p>
 *
 * @author: kancy
 * @date: 2020/6/16 15:01
 **/

public class XMindTests {
    public static void main(String[] args) {

        String databaseName = "test";

        XMind xMind = new XMind(String.format("数据库（%s）表设计", databaseName));
        xMind.setFile(FileUtils.getFile(databaseName + ".xmind"));

        ArrayList<XMindNode> xMindNodes = new ArrayList<>();
        xMind.setTopics(xMindNodes);

        xMindNodes.add(new XMindNode("A"));
        xMindNodes.add(new XMindNode("B"));
        xMindNodes.add(new XMindNode("C"));
        xMindNodes.add(new XMindNode("D"));

        XMindNode xMindNode = new XMindNode("C");
        ArrayList<XMindNode> xMindNodes2 = new ArrayList<>();
        xMindNodes2.add(new XMindNode("是"));
        xMindNodes2.add(new XMindNode("否"));
        xMindNode.setChildNodes(xMindNodes2);
        xMindNodes.add(xMindNode);

        xMind.save();
    }

}
