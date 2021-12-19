package com.company.实例.cache;

import com.company.entity.Accademy;
import com.company.entity.Major;
import com.company.mapper.AccademyMapper;
import com.company.mapper.MajorMapper;
import com.company.实例.ssf;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

import java.io.IOException;

//1级缓存和2级缓存
public class cacheTest {
    @Test
    public void cache1() throws IOException {
        //测试二级缓存（针对mapper）需在mapper 中配置第三方缓存
        //Cache Hit Ratio [com.company.mapper.AccademyMapper]: 0.5
        SqlSessionFactory sqlSessionFactory = ssf.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        SqlSession sqlSession1 = sqlSessionFactory.openSession();
        try{
            AccademyMapper mapper = sqlSession.getMapper(AccademyMapper.class);
            Accademy accademyById = mapper.getAccademyById(1);
            System.out.println(accademyById);
            sqlSession.close();

            AccademyMapper mapper1 = sqlSession1.getMapper(AccademyMapper.class);
            Accademy accademyById1 = mapper1.getAccademyById(1);
//            Accademy accademyById1 = mapper.getAccademyById(1);
            System.out.println(accademyById1);
            sqlSession1.close();

        }finally {
            sqlSession.close();

        }
    }
    @Test
    public void cache2() throws IOException {
        //测试1级缓存(正对SQLSession)
        //[main] [com.company.mapper.AccademyMapper.getAccademyById]-[DEBUG] ==> Parameters: 1(Integer)
        //[main] [com.company.mapper.AccademyMapper.getAccademyById]-[DEBUG] <==      Total: 1
        //Accademy(accademyId=1, accademyName=计算机学院, profile=计算机学院包含, location=a6, majors=null, majorIds=null)
        //Accademy(accademyId=1, accademyName=计算机学院, profile=计算机学院包含, location=a6, majors=null, majorIds=null)
        SqlSessionFactory sqlSessionFactory = ssf.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            AccademyMapper mapper = sqlSession.getMapper(AccademyMapper.class);
            AccademyMapper mapper1 = sqlSession.getMapper(AccademyMapper.class);
            Accademy accademyById = mapper.getAccademyById(1);
            Accademy accademyById1 = mapper1.getAccademyById(1);
            System.out.println(accademyById);
            System.out.println(accademyById1);
            sqlSession.commit();

        }finally {

            sqlSession.close();

        }
    }
}
