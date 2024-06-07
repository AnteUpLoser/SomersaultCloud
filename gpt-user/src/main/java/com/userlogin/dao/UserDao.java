package com.userlogin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.userlogin.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseMapper<User> {

}
