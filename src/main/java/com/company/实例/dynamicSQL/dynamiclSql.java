package com.company.实例.dynamicSQL;

import com.company.entity.Accademy;
import com.company.mapper.AccademyMapper;
import com.company.mapper.MajorMapper;
import com.company.实例.ssf;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class dynamiclSql {
    @Test
    public void collection() throws IOException {
        //--------------------
        SqlSessionFactory sqlSessionFactory = ssf.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        AccademyMapper mapper = sqlSession.getMapper(AccademyMapper.class);
        //--------------------
//        动态拼接sql
        try{
            //select * from accademy where accademyId=?
            List<Accademy> accademyBycriteria = mapper.getAccademyBycriteria(1,null);
            for (Accademy accademy:accademyBycriteria){
                System.out.println(accademy);
            }
            //select * from accademy
            List<Accademy> accademyBycriteria1 = mapper.getAccademyBycriteria(null,null);
            for (Accademy accademy:accademyBycriteria1){
                System.out.println(accademy);
            }
            //select * from accademy where accademyName=?
            List<Accademy> accademyBycriteria2 = mapper.getAccademyBycriteria(null,"计算机学院");
            for (Accademy accademy:accademyBycriteria2){
                System.out.println(accademy);
            }
        }finally {
            sqlSession.commit();
            sqlSession.close();

        }

    }
}
