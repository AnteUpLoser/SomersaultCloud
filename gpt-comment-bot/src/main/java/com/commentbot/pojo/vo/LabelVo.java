package com.commentbot.pojo.vo;

import lombok.Data;

import java.util.ArrayList;

@Data
public class LabelVo {
    private int id;
    private String labelContent;
    private int labelLevel;
    private ArrayList<LabelVo> childLabels;
}
