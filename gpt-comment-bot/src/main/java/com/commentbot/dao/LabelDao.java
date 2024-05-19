package com.commentbot.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.commentbot.pojo.bo.Label;
import com.commentbot.pojo.vo.LabelVo;
import org.apache.ibatis.annotations.Mapper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Mapper
public interface LabelDao extends BaseMapper<Label> {


    //根据botId查询最大父标签
    ArrayList<LabelVo> getMaxLabels(int botId);

    //根据父id查询其所有子标签
    ArrayList<LabelVo> getChildrenByParentId(int parentId);

    //根据labelIds批量查询labelContent
    List<String> getLabelContentList(ArrayList<Integer> labelIds);


}
