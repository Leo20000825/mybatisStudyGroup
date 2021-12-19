package com.company.test;

import com.company.entity.Accademy;
import com.company.entity.Major;
import com.company.mapper.AccademyMapper;
import com.company.mapper.MajorMapper;
import jdk.nashorn.internal.ir.CallNode;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {
    public static SqlSessionFactory getSqlSessionFactory()throws IOException {
        String resource = "mybatisConfig.xml";
        InputStream is = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        return sqlSessionFactory;
    }
    //              增删改查测试
    @org.junit.Test
    public void test01() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            MajorMapper mapper = sqlSession.getMapper(MajorMapper.class);
            List<Major> major = mapper.getMajor();
            for (Major m:major
                 ) {
                System.out.println(m);

            }
        }finally {
            sqlSession.close();

        }
    }
    @org.junit.Test
    public void test02() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            MajorMapper mapper = sqlSession.getMapper(MajorMapper.class);
            List<Major> major = mapper.getMajor2();
            for (Major m:major
            ) {
                System.out.println(m);

            }
        }finally {
            sqlSession.close();

        }
    }
    //增
    @org.junit.Test
    public void test03() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            MajorMapper mapper = sqlSession.getMapper(MajorMapper.class);
            mapper.insertMajor(new Major(0,"法语",2));

        }finally {
            sqlSession.commit();
            sqlSession.close();

        }
    }
    //更
    @org.junit.Test
    public void test04() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            MajorMapper mapper = sqlSession.getMapper(MajorMapper.class);
            mapper.updateMajor(new Major(10,"葡萄牙语",2));
        }finally {
            sqlSession.commit();
            sqlSession.close();

        }
    }
    //删
    @org.junit.Test
    public void test05() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            MajorMapper mapper = sqlSession.getMapper(MajorMapper.class);
            mapper.deleteMajorByid(15);
        }finally {
            sqlSession.commit();
            sqlSession.close();

        }
    }
    //insert获得主键自增的值
    @org.junit.Test
    public void test06() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            MajorMapper mapper = sqlSession.getMapper(MajorMapper.class);
            Major m = new Major(0, "法语", 2);
            mapper.insertMajorWithPrimaryKey(m);
            System.out.println(m.getMajorId());
            //18

        }finally {
            sqlSession.commit();
            sqlSession.close();

        }
    }
    //insert中加入selectKey
    @org.junit.Test
    public void test07() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            MajorMapper mapper = sqlSession.getMapper(MajorMapper.class);
            //给一个空的majorName在插入时应赋值为查询出的majorName
            Major m = new Major(0, "", 2);
            //尽管给了majorName也会赋值为select中的result
            Major m1 = new Major(0, "123", 2);
            mapper.insertSelectKey(m1);
            //18

        }finally {
            sqlSession.commit();
            sqlSession.close();

        }
    }
    @org.junit.Test
    public void test08() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            MajorMapper mapper = sqlSession.getMapper(MajorMapper.class);
            List<Major> major = mapper.selectMajorWithAccademy(2);

        }finally {
            sqlSession.commit();
            sqlSession.close();

        }
    }
    //分步查询延迟加载
    //        <setting name="lazyLoadingEnabled" value="true"/>
    //        <setting name="aggressiveLazyLoading" value="false"/>
    @org.junit.Test
    public void test09() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            MajorMapper mapper = sqlSession.getMapper(MajorMapper.class);
            Major major = mapper.selectMajorStep(2);
            //在需要查询association时才进行查询否则只执行一次查询
            System.out.println(major.getMajorId());
            System.out.println(major);
//            AccademyMapper mapper1 = sqlSession.getMapper(AccademyMapper.class);
//            Accademy accademyById = mapper1.getAccademyById(2);
//            System.out.println(accademyById);
        }finally {
            sqlSession.close();

        }
    }
    @org.junit.Test
    public void test10() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            AccademyMapper mapper = sqlSession.getMapper(AccademyMapper.class);
            Accademy accademyAndMajors = mapper.getAccademyAndMajors(2);
            List<Major> majorList = accademyAndMajors.getMajors();
            for (Major m:majorList
                 ) {
                System.out.println(m);

            }


        }finally {
            sqlSession.close();

        }
    }
    @org.junit.Test
    public void test11() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            AccademyMapper mapper = sqlSession.getMapper(AccademyMapper.class);
            Accademy a  = new Accademy();

            //其余情况则按照mybatis封装规则制定1.默认list，array 2.@parm

            // private List<Integer> majorIds;  与collection 与属性名majorIds对应
            a.setMajorIds(Arrays.asList(1, 2, 3));
            List<Major> majorListByIds = mapper.getMajorListByIds(a);
            for (Major m : majorListByIds){
                System.out.println(m);
            }

        }finally {
            sqlSession.close();

        }
    }
    /*
    *  <select id="getAccademyBycriteria" resultType="com.company.entity.Accademy">
        select * from accademy
        <trim prefix="where" suffixOverrides="and" >
            <if test="id!=null">
                accademyId=#{id} and
            </if>
            <if test="name!=null">
                accademyName=#{name} and
            </if>
        </trim>
    </select>
    *
    * */
    @org.junit.Test
    public void test12() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            AccademyMapper mapper = sqlSession.getMapper(AccademyMapper.class);
            List<Accademy> accademyBycriteria = mapper.getAccademyBycriteria(2, "计算机学院");
            //sufixOverride 和 prefixOverride 为 取消最后一次出现的前缀或后缀
            for (Accademy a:accademyBycriteria){
                System.out.println(a);
            }
        }finally {
            sqlSession.close();

        }
    }
    @org.junit.Test
    public void test13() throws IOException {
        //测试二级缓存
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
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
    @org.junit.Test
    public void test14() throws IOException {
        //测试ehcache
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
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

}
