package com.company.实例.基本CRUD;

import com.company.entity.Major;
import com.company.mapper.MajorMapper;
import com.company.实例.ssf;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

import java.io.IOException;

public class Delete {
    /**
     *       simpleDelete:   delete from major where majorId=?
     *
     * @throws IOException
     */
    @Test
    public void simpleDeleteById() throws IOException {
        //--------------------
        SqlSessionFactory sqlSessionFactory = ssf.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        MajorMapper mapper = sqlSession.getMapper(MajorMapper.class);
        //--------------------
        try{
            mapper.simpleDeleteById(18);

        }finally {
            sqlSession.commit();
            sqlSession.close();

        }

    }
}
