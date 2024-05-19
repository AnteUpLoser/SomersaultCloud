package com.commentbot.service;

import com.commentbot.pojo.vo.LabelVo;
import java.util.List;

public interface LabelService {

    List<LabelVo> getBotLabels(int botId);

}
