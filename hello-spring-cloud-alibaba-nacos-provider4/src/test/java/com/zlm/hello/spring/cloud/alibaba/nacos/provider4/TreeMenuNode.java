package com.zlm.hello.spring.cloud.alibaba.nacos.provider4;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreeMenuNode implements Serializable {
    private String id;
    private String pid;
    private String name;
    private List<TreeMenuNode> children;

}
