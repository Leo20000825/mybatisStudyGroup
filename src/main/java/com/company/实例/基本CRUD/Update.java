package com.company.实例.基本CRUD;

import com.company.mapper.MajorMapper;
import com.company.实例.ssf;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

import java.io.IOException;

public class Update {
    /**
     *  simpleUpdate:  update major set...where...
     */
    @Test
    public void simpleUpdate() throws IOException {
        SqlSessionFactory sqlSessionFactory = ssf.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        MajorMapper mapper = sqlSession.getMapper(MajorMapper.class);
        //--------------------
        try{
            mapper.simpleUpdate("计算机科学与技术",1,3);

        }finally {
            sqlSession.commit();
            sqlSession.close();

        }

    }
}
