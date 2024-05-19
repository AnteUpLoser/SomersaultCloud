package com.commentbot.pojo.dto;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

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
public class InitLabelReqDto {
    private String stuName;
    private String practiceContent;
    private String performance;
    private String studySug;
    private String coopSug;

    public InitLabelReqDto  setStuName(String stuName) {
        this.stuName = "<" + stuName + ">";
        return this;
    }

    public InitLabelReqDto  setPracticeContent(String practiceContent){
        this.practiceContent = "<<" + practiceContent + ">>";
        return this;
    }

    public InitLabelReqDto  setPerformance(String performance) {
        this.performance = "<<<" + performance + ">>>";
        return this;
    }

    public InitLabelReqDto  setStudySug(String studySug) {
        this.studySug = "(" + studySug + ")";
        return this;
    }

    public InitLabelReqDto  setCoopSug(String coopSug) {
        this.coopSug = "[[" + coopSug + "]]";
        return this;
    }
}
