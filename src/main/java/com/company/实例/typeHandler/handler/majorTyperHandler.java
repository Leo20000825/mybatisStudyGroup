package com.company.实例.typeHandler.handler;

import com.company.entity.Major;
import com.company.实例.typeHandler.enums.major;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
*           第一步实现typerHandler接口,泛型内填入枚举
*           第二步实现方法
*           第三部配置typerHandler 到xml
* */
public class majorTyperHandler implements TypeHandler<major> {

//    定义当前数据如何保存到数据库中
    public void setParameter(PreparedStatement preparedStatement, int i, major major, JdbcType jdbcType) throws SQLException {
        //执行对参数的处理
        System.out.println("major enum的code 为"+major.getCode()+"msg:"+major.getMsg());
        preparedStatement.setString(i,major.getCode().toString());
    }
//定义获取数据时的情况
    public major getResult(ResultSet resultSet, String s) throws SQLException {
        //从数据库拿到的状态码
        int code = resultSet.getInt(s);
        System.out.println("拿到的code"+code);
        String msg = major.codeToMsg(code);
        System.out.println("对应的msg"+msg);
        return null;
    }

    public major getResult(ResultSet resultSet, int i) throws SQLException {
        int code = resultSet.getInt(i);
        System.out.println("拿到的code"+code);
        String msg = major.codeToMsg(code);
        System.out.println("对应的msg"+msg);
        return null;
    }

    public major getResult(CallableStatement callableStatement, int i) throws SQLException {
        int code = callableStatement.getInt(i);
        System.out.println("拿到的code"+code);
        String msg = major.codeToMsg(code);
        System.out.println("对应的msg"+msg);
        return null;
    }
}
