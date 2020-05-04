package com.cmh.item.biz.dao.db.mapper;

import com.cmh.item.biz.dao.db.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author：初明昊
 * @data：2020/04/24
 * @description：UserMapper接口
 */
@Mapper
public interface UserMapper {
    //新增
    @Insert("insert into cmh_user(id,user_name,password,user_email) values(#{id},#{userName},#{password},#{email})")
    int insert(User user);

    @Select("select * from cmh_user where user_email=#{email} and password=#{password}")
    User select(User user);
}
