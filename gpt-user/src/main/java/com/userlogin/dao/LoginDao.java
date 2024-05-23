package com.userlogin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.userlogin.pojo.dto.LoginDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LoginDao extends BaseMapper<LoginDto> {
    @Select("select password from user_info where user_email = #{email};")
    String findPasswordByUserEmail(String email);

    @Select("select user_id from user_info where user_email = #{email}")
    Integer findUidByEmail(String email);
}
