package com.kancy.mybatisplus.generator.core.xmind;

import com.kancy.mybatisplus.generator.exception.AlertException;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.xmind.core.*;

import java.io.File;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * XMind
 * <p>
 *
 * @author: kancy
 * @date: 2020/6/16 15:04
 * @desc https://github.com/xmindltd/xmind/wiki/UsingXmindAPI
 **/
@Data
@NoArgsConstructor
public class XMind {

    private String title;

    private File file;

    private List<XMindNode> topics;

    public XMind(String title) {
        this.title = title;
    }

    public void save() {
        save(this);
    }

    public static void save(XMind xMind) {
        try {
            // 创建思维导图的工作空间
            IWorkbookBuilder workbookBuilder = Core.getWorkbookBuilder();
            IWorkbook workbook = workbookBuilder.createWorkbook();

            // 获得默认sheet
            ISheet primarySheet = workbook.getPrimarySheet();

            // 获得根主题
            ITopic rootTopic = primarySheet.getRootTopic();
            // 设置根主题的标题
            rootTopic.setTitleText(xMind.getTitle());

            // 填充内容
            addTopics(workbook, rootTopic, xMind.getTopics());

            // 保存
            workbook.save(xMind.getFile().getAbsolutePath());
        } catch (Exception e) {
            throw new AlertException("保存思维导图失败！", e);
        }
    }

    private static void addTopics(IWorkbook workbook, ITopic rootTopic, List<XMindNode> topics) {
        for (XMindNode xMindNode : topics) {
            ITopic topic = workbook.createTopic();
            topic.setTitleText(xMindNode.getName());
            topic.setFolded(xMindNode.isFolded());
            if (Objects.nonNull(xMindNode.getLabels())
                    && !xMindNode.getLabels().isEmpty()){
                topic.setLabels(xMindNode.getLabels());
            }
            List<XMindNode> childNodes = xMindNode.getChildNodes();
            if (Objects.nonNull(childNodes)){
                addTopics(workbook, topic, childNodes);
            }
            rootTopic.add(topic, ITopic.ATTACHED);
        }
    }
}
