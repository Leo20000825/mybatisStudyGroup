package com.company.实例.association;

import com.company.entity.Major;
import com.company.mapper.MajorMapper;
import com.company.实例.ssf;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class association {
    @Test
    public void assocation1() throws IOException {
        //--------------------
        SqlSessionFactory sqlSessionFactory = ssf.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        MajorMapper mapper = sqlSession.getMapper(MajorMapper.class);
        //--------------------
//        级联查询
        try{
            List<Major> association = mapper.association(2);
            for (Major major:association){
                System.out.println(major);
            }

        }finally {
            sqlSession.commit();
            sqlSession.close();

        }

    }
    @Test
    public void assocation2() throws IOException {
        //--------------------
        SqlSessionFactory sqlSessionFactory = ssf.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        MajorMapper mapper = sqlSession.getMapper(MajorMapper.class);
        //--------------------
//        级联查询
        try{
            List<Major> association = mapper.selectMajorWithAccademy(2);
            for (Major major:association){
                System.out.println(major);
            }

        }finally {
            sqlSession.commit();
            sqlSession.close();

        }

    }
    @Test
    public void assocation3() throws IOException {
        //--------------------
        SqlSessionFactory sqlSessionFactory = ssf.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        MajorMapper mapper = sqlSession.getMapper(MajorMapper.class);
        //--------------------
//        级联查询
        try{
            Major  major= mapper.selectMajorStep(2);
            System.out.println(major);


        }finally {
            sqlSession.commit();
            sqlSession.close();

        }

    }
}
