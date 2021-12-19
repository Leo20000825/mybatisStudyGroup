package com.company.实例.batch;

import com.company.entity.Major;
import com.company.mapper.MajorMapper;
import com.company.实例.ssf;
import com.company.实例.typeHandler.enums.major;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class batchTest {
    @Test
    public void batch1() throws IOException {
        //--------------------
        SqlSessionFactory sqlSessionFactory = ssf.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        MajorMapper mapper = sqlSession.getMapper(MajorMapper.class);
        //--------------------
//        批量操作(拼接1)
        try{
            List<Major> majors= new ArrayList();
            majors.add(new Major(0,"物联网",4,null));
            majors.add(new Major(0,"自动化",4,null));
            majors.add(new Major(0,"机器人开发",4,null));

            mapper.batchAddcc1(majors);


        }finally {
            sqlSession.commit();
            sqlSession.close();

        }

    }
    @Test
    public void batch2() throws IOException {
        //--------------------
        SqlSessionFactory sqlSessionFactory = ssf.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        MajorMapper mapper = sqlSession.getMapper(MajorMapper.class);
        //--------------------
//        批量操作(拼接2)
        try{
            List<Major> association = mapper.getMajorListByIds(Arrays.asList(1,2,4));
            for (Major major:association){
                System.out.println(major);
            }

        }finally {
            sqlSession.commit();
            sqlSession.close();

        }

    }
    @Test
    public void batch3() throws IOException {
        //--------------------
        SqlSessionFactory sqlSessionFactory = ssf.getSqlSessionFactory();
        //------------配置batch
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        MajorMapper mapper = sqlSession.getMapper(MajorMapper.class);
        //--------------------
//        批量操作--------通过修改ExcutorType 为BATCH变为批量操作
        try{
            for (int i=0;i<90;i++) {
                mapper.simpleInsert(new Major(0, "batch", 2,null));
            }


        }finally {
            sqlSession.commit();
            sqlSession.close();

        }

    }
}
