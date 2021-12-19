package com.company.实例.resultMap;

import com.company.entity.Major;
import com.company.mapper.MajorMapper;
import com.company.实例.ssf;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class rm {
    @Test
    public void simpleSelect() throws IOException {
        //--------------------
        SqlSessionFactory sqlSessionFactory = ssf.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        MajorMapper mapper = sqlSession.getMapper(MajorMapper.class);
        //--------------------
        try{
            List<Major> majorList = mapper.resultMap();
            for (Major major:majorList){
                System.out.println(major);
            }

        }finally {
            sqlSession.commit();
            sqlSession.close();

        }

    }
}
