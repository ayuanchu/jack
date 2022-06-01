package com.kancy.mybatisplus.generator.core.xmind;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * <p>
 * XMindNode
 * <p>
 *
 * @author: kancy
 * @date: 2020/6/16 14:59
 **/
@Data
@NoArgsConstructor
public class XMindNode {

    private String name;

    private boolean folded;

    private String description;

    private List<XMindNode> childNodes;

    private Set<String> labels;

    public XMindNode(String name) {
        this.name = name;
    }

    public void addLabel(String label){
        if (Objects.isNull(labels)){
            labels = new HashSet<>();
        }
        labels.add(label);
    }
}
