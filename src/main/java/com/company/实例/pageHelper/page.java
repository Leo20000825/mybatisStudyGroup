package com.company.实例.pageHelper;

import com.company.entity.Accademy;
import com.company.mapper.AccademyMapper;
import com.company.实例.ssf;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class page {
    @Test
    public void collection() throws IOException {
        //--------------------
        SqlSessionFactory sqlSessionFactory = ssf.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        AccademyMapper mapper = sqlSession.getMapper(AccademyMapper.class);
        //--------------------
//        动态拼接sql
        try{
            //select * from accademy
            PageHelper.startPage(1,5);
            List<Accademy> accademyBycriteria1 = mapper.getAccademyBycriteria(null,null);
            for (Accademy accademy:accademyBycriteria1){
                System.out.println(accademy);
            }
        }finally {
            sqlSession.commit();
            sqlSession.close();

        }

    }
}
