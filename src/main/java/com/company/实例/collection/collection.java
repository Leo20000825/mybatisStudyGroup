package com.company.实例.collection;

import com.company.entity.Accademy;
import com.company.entity.Major;
import com.company.mapper.MajorMapper;
import com.company.实例.ssf;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class collection {
    @Test
    public void collection() throws IOException {
        //--------------------
        SqlSessionFactory sqlSessionFactory = ssf.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        MajorMapper mapper = sqlSession.getMapper(MajorMapper.class);
        //--------------------
//        批量操作(拼接2)
        try{
            List<Accademy> accademyAndMajors = mapper.getAccademyAndMajors(2);
            for (Accademy Accademy:accademyAndMajors){
                System.out.println(Accademy);
            }

        }finally {
            sqlSession.commit();
            sqlSession.close();

        }

    }
}
