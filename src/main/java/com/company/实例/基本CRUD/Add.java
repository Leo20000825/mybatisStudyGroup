package com.company.实例.基本CRUD;

import com.company.entity.Major;
import com.company.mapper.MajorMapper;
import com.company.实例.ssf;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

import java.io.IOException;

public class Add {

    /**
     *         测试基本的添加
     *         insert into Major (majorId,majorName,accademy,ady) values(?,?,?,?,?)
     *             private int majorId;
             *     private String majorName;
             *     private int accademy;
             *     private Accademy ady;
     * @throws IOException
     */
    @Test
    public void simpleInsert() throws IOException {
        //--------------------
        SqlSessionFactory sqlSessionFactory = ssf.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        MajorMapper mapper = sqlSession.getMapper(MajorMapper.class);
        //--------------------
        try{
            Major major = new Major(0,"电商",1,null);
            mapper.simpleInsert(major);

        }finally {
            sqlSession.commit();
            sqlSession.close();

        }

    }
}
