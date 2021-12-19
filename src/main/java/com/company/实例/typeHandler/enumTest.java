package com.company.实例.typeHandler;

import com.company.entity.Major;
import com.company.mapper.MajorMapper;
import com.company.实例.ssf;
import com.company.实例.typeHandler.enums.major;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class enumTest {
    @Test
    public void enumtest() throws IOException {
        //测试枚举
        major computer = major.COMPUTER;
        Integer code = computer.getCode();
        String msg = computer.getMsg();
        System.out.println(computer);
        System.out.println(code);
        System.out.println(msg);

    }

    public static void main(String[] args) throws IOException {
        //测试枚举
        //--------------------
        SqlSessionFactory sqlSessionFactory = ssf.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        MajorMapper mapper = sqlSession.getMapper(MajorMapper.class);
        //--------------------
        try{
            mapper.simpleInsert(new Major(0,"软件架构与设计",1,null,major.COMPUTER));

        }finally {
            sqlSession.commit();
            sqlSession.close();

        }

    }

}
