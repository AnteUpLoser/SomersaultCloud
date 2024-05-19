package com.commentbot.pojo.dto.req;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * #input#
 *
 * <学生姓名>
 *
 * <<课堂练习>>
 *
 * <<<课堂表现>>>
 *
 * （后续学习建议）
 *
 * [[课后练习及家校合作建议]]
 */

@Data
@Accessors(chain = true)
public class InitLabelReq {
    private long botId;
    private long chatId;
    private String stuName;
    private String practiceContent;
    private ArrayList<Integer> perfLabelIds;
    private ArrayList<Integer> studySugLabelIds;
    private String addiStudySug;
    private ArrayList<Integer> coopSugLabelIds;
    private String addiCoopSug;

}
