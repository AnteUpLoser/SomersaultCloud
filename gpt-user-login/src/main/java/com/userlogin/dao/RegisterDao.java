package com.userlogin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.userlogin.pojo.dto.RegisterDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RegisterDao extends BaseMapper<RegisterDto> {

    //查找有无相同用户名
    @Select("select COUNT(username) from user_info where username = #{username};")
    boolean selectSameUsername(String username);

    //查找有无相同注册邮箱
    @Select("select COUNT(user_email) from user_info where user_email = #{email};")
    boolean selectSameEmail(String email);
}
