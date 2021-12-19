![](mybatis.assets/QQ截图20210805174955.png)

# mybatis

```
mybatis是什么
为什么有mybatis
mybatis实现crud
	xml实现
	注解实现
常用配置
对象关系映射
分页插件
动态sql
缓存
懒加载
事务并发处理
逆向工程
```

## mybatis是什么

mybatis是我们学的第一个java的框架

框架是让业务实现起来更加简单的一个项目

mybatis是一个基于JDBC，反射，代理模式等技术封装出来的一个持久层框架

mybatis是一个orm框架，实际上只能算是半ORM框架，真正的全ORM框架应该是Hibernate框架

面试题：ORM是什么？

O Object R relation M mapping 对象关系映射 对象就是在java中的实体，关系就是关系型数据库，映射就是将实体和关系型数据库进行映射 类和表映射 属性和字段映射

## 为什么会有mybatis

在开发的时候因为直接使用jdbc，会很繁琐。在开发的时候一般我们会考虑两种效率，开发效率和运行效率。这两个效率是成反比的，在实际的开发中一般会找这两个效率的平衡点。在操作数据库方面mybatis的开发效率和运行效率两者都不差。

## mybatis实现crud

添加依赖

```xml
<!-- https://mvnrepository.com/artifact/org.mybatis/mybatis -->
    <dependency>
      <groupId>org.mybatis</groupId>
      <artifactId>mybatis</artifactId>
      <version>3.4.6</version>
    </dependency>
    <!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
    <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>5.1.38</version>
    </dependency>

```

### xml实现crud

添加主配置文件 config.xml，存在于resources目录下

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
  <!--  mybatis支持多个数据源 default 指向默认使用的数据源-->
    <environments default="development">
        <!--创建一个数据源 叫做development-->
        <environment id="development">
            <!--事务管理器-->
            <transactionManager type="JDBC"></transactionManager>
            <!--数据源 -->
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql:///weibo"/>
                <property name="username" value="kinglee"/>
                <property name="password" value="root"/>
            </dataSource>
        </environment>
    </environments>

    <mappers>
        <!--有了对应的映射文件后 需要添加映射-->
        <mapper resource="com/zlt/dao/UserMapper.xml"/>
    </mappers>
</configuration>
```

创建一个实体类

```java
package com.zlt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    private int uid;

    private String username;

    private String password;

    private String nickname;

    private String email;

    private String photo;

    private Date regTime;

    private Date loginTime;


}

```

创建一个接口

```java
package com.zlt.dao;

import com.zlt.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    int insertUser(User user);

    int deleteById(int uid);

    int updateUser(User user);

    User selectUserById(int uid);

//    User selectUserByUsernameAndPassword(Map<String ,String > map);
    /*多个参数的时候建议添加注解@Param()*/
    User selectUserByUsernameAndPassword(@Param("username") String username, @Param("password")String password);

    List<User> selectUser();
}

```

添加个映射文件 存在于 resources/com/zlt/dao/UserMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!--namespace命名空间  一般来说就是持久层接口的全路径 -->
<mapper namespace="com.zlt.dao.UserMapper">

    <!--下面的标签就是用来执行增删改查的操作 id一般来说和方法名需要相同 parameterType 表示方法的参数类型
        需要注意的是增删改默认是没有返回类型的  默认都是返回影响行数
        查询 如果查询一个对象  那么返回类型resultType 可以直接去写结果类型
        如果查询的是一个集合  那么resultType 其实是对应的泛型
    -->

    <insert id="insertUser" parameterType="com.zlt.entity.User" >
        insert into user (uid,username,password,nickname,email,photo,regTime,loginTime) values
        (#{uid},#{username},#{password},#{nickname},#{email},#{photo},#{regTime},#{loginTime})
    </insert>

    <delete id="deleteById" parameterType="int" >
        delete from user where uid=#{uid}
    </delete>

    <update id="updateUser" parameterType="com.zlt.entity.User">
        update user set password=#{password},nickname=#{nickname} where uid=#{uid}
    </update>

    <!--如果类中的属性名和 表中的字段名不一致的时候可以使用别名的方式让他一致，就可以自动的封装成对象-->
    <!--<select id="selectUserById" parameterType="int" resultType="com.zlt.entity.User">
        select tb_uid uid,tb_username username,tb_password password,tb_nickname nickname,tb_email email,tb_photo photo,
        tb_regTime regTime,tb_loginTime loginTime from tb_user where tb_uid=#{uid}
    </select>-->

    <!--创建一个结果集-->
    <resultMap id="UserResultMap" type="com.zlt.entity.User">
        <!--主键映射-->
        <id property="uid" column="tb_uid" />
        <!--普通字段-->
        <result column="tb_username" property="username"/>
        <result column="tb_password" property="password"/>
        <result column="tb_nickname" property="nickname"/>
        <result column="tb_email" property="email"/>
        <result column="tb_photo" property="photo"/>
        <result column="tb_regTime" property="regTime"/>
        <result column="tb_loginTime" property="loginTime"/>
    </resultMap>

    <select id="selectUserById" parameterType="int" resultMap="UserResultMap" >
        select tb_uid,tb_username,tb_password,tb_nickname,tb_email,tb_photo,
        tb_regTime,tb_loginTime from tb_user where tb_uid=#{uid}
    </select>


    <select id="selectUserByUsernameAndPassword" resultType="com.zlt.entity.User">
        select uid,username,password,nickname,email,photo,regTime,loginTime from user where username=#{username} and password=#{password}
    </select>

    <select id="selectUser"  resultType="com.zlt.entity.User">
        select uid,username,password,nickname,email,photo,regTime,loginTime from user
    </select>
</mapper>
```

使用的时候可以有两种方式来使用

```java
package com.zlt;

import static org.junit.Assert.assertTrue;

import com.zlt.dao.UserMapper;
import com.zlt.entity.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.util.Date;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    SqlSession sqlSession;

    UserMapper mapper;


    @Before
    public void before() throws IOException {
        Reader resourceAsReader = Resources.getResourceAsReader("config.xml");

        //拿到会话工厂
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsReader);
        //打开会话

        sqlSession = sqlSessionFactory.openSession();
        mapper = sqlSession.getMapper(UserMapper.class);
    }


    @After
    public void after(){
        sqlSession.commit();//执行增删改一定要提交事务
        sqlSession.close();
    }


    @Test
    public void test(){
        //sqlSession可以直接去执行Mapper文件中对应的方法  第一个参数是命名空间.id  第二个参数就是标签运行所需的参数
        Object o = sqlSession.selectOne("com.zlt.dao.UserMapper.selectUserById", 10);
        System.out.println(o);

    }


    @Test
    public void insert() throws IOException {
        try {
            User user = new User(0,"lisi","123456","李四","123@123.com",null,new Date(),new Date());
            int i = mapper.insertUser(user);
            System.out.println(i);
        }catch (Exception e){
            sqlSession.rollback();
        }
    }

    @Test
    public void select1() throws IOException {
        try {
            User lisi = mapper.selectUserById(10);
            System.out.println(lisi);
        }catch (Exception e){
            sqlSession.rollback();
        }
    }
}

```

第一种方式获取接口的对象，然后去调用接口的方法即可

第二种方式通过sqlSession去调用对应的增删改查方法即可

### 注解实现crud

在上面的基础之上把接口修改一下，如下

```java
package com.zlt.dao;

import com.zlt.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {


    @Select("select tb_uid,tb_username,tb_password,tb_nickname,tb_email,tb_photo,\\n\" +\n" +
            "            \"        tb_regTime,tb_loginTime from tb_user")
    @Results(
            id = "UserResults",
            value={
                    @Result(property = "uid",column = "tb_uid",id = true),
                    @Result(property = "username",column = "tb_username"),
                    @Result(property = "password",column = "tb_password"),
                    @Result(property = "nickname",column = "tb_nickname"),
                    @Result(property = "email",column = "tb_email"),
                    @Result(property = "photo",column = "tb_photo"),
                    @Result(property = "regTime",column = "tb_regTime"),
                    @Result(property = "loginTime",column = "tb_loginTime")

            }
    )
    User getUser();

    @Insert("insert into user (uid,username,password,nickname,email,photo,regTime,loginTime) values\n" +
            "        (#{uid},#{username},#{password},#{nickname},#{email},#{photo},#{regTime},#{loginTime})")
    int insertUser(User user);

    @Delete("delete from user where uid=#{uid}")
    int deleteById(int uid);

    @Update("update user set password=#{password},nickname=#{nickname} where uid=#{uid}")
    int updateUser(User user);

    @Select("select tb_uid,tb_username,tb_password,tb_nickname,tb_email,tb_photo,\n" +
            "        tb_regTime,tb_loginTime from tb_user where tb_uid=#{uid}")

    @ResultMap("UserResults")
    User selectUserById(int uid);

//    User selectUserByUsernameAndPassword(Map<String ,String > map);
    /*多个参数的时候建议添加注解@Param()*/
    @Select("select uid,username,password,nickname,email,photo,regTime,loginTime from user where username=#{username} and password=#{password}")
    User selectUserByUsernameAndPassword(@Param("username") String username, @Param("password")String password);

    @Select("select uid,username,password,nickname,email,photo,regTime,loginTime from user")
    List<User> selectUser();
}

```

在主配置文件config.xml文件中修改映射文件的路径

```java
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"></transactionManager>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql:///weibo"/>
                <property name="username" value="kinglee"/>
                <property name="password" value="root"/>
            </dataSource>
        </environment>
    </environments>

    <mappers>
<!--        <mapper resource="com/zlt/dao/UserMapper.xml_bak"/>-->
        <mapper class="com.zlt.dao.UserMapper"/>
    </mappers>
</configuration>
```

### 注意

当接口中的方法有多个参数的时候，需要在每个参数上添加注解@Param("名称") 来确定参数和sql中的对应关系

```java
User selectUserByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
```

```xml
<select id="selectUserByUsernameAndPassword" resultType="com.zlt.entity.User">
        select uid,username,password,nickname,email,photo,regTime,loginTime from user where username=#{username} and password=#{password}
    </select>
```

### #和$的区别

面试题：#和$的区别

#{} 在mybatis进行处理的时候会变成? 可以防止sql注入攻击

${} 是直接拼接到sql中，是无法防止sql注入攻击的，也不会处理类型的问题，都当做字符串来进行处理

```java
User selectUserByUsernameAndPassword(@Param("username") String username, @Param("password") String password,@Param("table") String table);
```

```xml
<select id="selectUserByUsernameAndPassword" resultMap="UserResultMap">
        select tb_uid,tb_username,tb_password,tb_nickname,tb_email,tb_photo,
        tb_regTime,tb_loginTime from ${table} where tb_username=#{username} and tb_password=#{password}
    </select>
```

最后生成的sql

```sql
select tb_uid,tb_username,tb_password,tb_nickname,tb_email,tb_photo, tb_regTime,tb_loginTime from tb_user where tb_username=? and tb_password=? 
```

${} 可以让sql变成动态，但是这个值一般不建议使用用户输入的值

#{} 只可以用来作为参数使用



## 常用配置

### 日志配置

mybatis作为一个成熟的框架，日志是必不可少的，在java中使用日志默认使用较多的是log4j

添加log4j的依赖

```xml
<!-- https://mvnrepository.com/artifact/log4j/log4j -->
<dependency>
    <groupId>log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>1.2.17</version>
</dependency>
```

添加log4j的配置文件

```properties
log4j.properties\uFF0C
log4j.rootLogger=DEBUG, Console
#Console
log4j.appender.Console=org.apache.log4j.ConsoleAppender
log4j.appender.Console.layout=org.apache.log4j.PatternLayout
log4j.appender.Console.layout.ConversionPattern=%d [%t] %-5p [%c] - %m%n
log4j.logger.java.sql.ResultSet=INFO
log4j.logger.org.apache=INFO
log4j.logger.java.sql.Connection=DEBUG
log4j.logger.java.sql.Statement=DEBUG
log4j.logger.java.sql.PreparedStatement=DEBUG

```

Log4j的使用

```java
private static Logger logger = Logger.getLogger(AppTest.class);

//使用的时候调用对应等级的方法即可
logger.debug("信息")
logger.debug("信息",e)
```

Log4j的日志等级

```
DEBUG 调试等级 细粒度信息对调试程序是有帮助的
INFO 提示等级  消息的细粒度突出程序的运行过程
WARN 警告等级 表示可能会出现错误的情况
ERROR 错误等级 指出虽然发生了错误，但是可能不影响程序的正常运行
FATAL 致命错误 发生了错误可能会导致程序的退出
当配置文件配置了低等级的日志时，那么可以输出高等级的日志信息 当配置了等级较高的日志时，低于他的等级日志就不再输出
```

### 别名配置

在映射文件中，当设置参数或者返回类型的时候，如果是对象则需要去配置全路径，在实际的开发中可以使用别名配置来减少重复代码

在核心配置文件config.xml中

```xml
 <!--别名配置-->
    <typeAliases>
        <!--配置单个类型的别名-->
<!--        <typeAlias type="com.zlt.entity.User" alias="u"/>-->
        <!--包别名-->
        <package name="com.zlt.entity"/>
    </typeAliases>
```

在映射文件中即可如下方式进行使用

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!--namespace命名空间  一般来说就是持久层接口的全路径 -->
<mapper namespace="com.zlt.dao.UserMapper">

    <!--下面的标签就是用来执行增删改查的操作 id一般来说和方法名需要相同 parameterType 表示方法的参数类型
        需要注意的是增删改默认是没有返回类型的  默认都是返回影响行数
        查询 如果查询一个对象  那么返回类型resultType 可以直接去写结果类型
        如果查询的是一个集合  那么resultType 其实是对应的泛型
    -->

    <insert id="insertUser" parameterType="User" >
        insert into user (uid,username,password,nickname,email,photo,regTime,loginTime) values
        (#{uid},#{username},#{password},#{nickname},#{email},#{photo},#{regTime},#{loginTime})
    </insert>

    <delete id="deleteById" parameterType="int" >
        delete from user where uid=#{uid}
    </delete>

    <update id="updateUser" parameterType="User">
        update user set password=#{password},nickname=#{nickname} where uid=#{uid}
    </update>

    <!--如果类中的属性名和 表中的字段名不一致的时候可以使用别名的方式让他一致，就可以自动的封装成对象-->
    <!--<select id="selectUserById" parameterType="int" resultType="com.zlt.entity.User">
        select tb_uid uid,tb_username username,tb_password password,tb_nickname nickname,tb_email email,tb_photo photo,
        tb_regTime regTime,tb_loginTime loginTime from tb_user where tb_uid=#{uid}
    </select>-->

    <!--创建一个结果集-->
    <resultMap id="UserResultMap" type="User">
        <!--主键映射-->
        <id property="uid" column="tb_uid" />
        <!--普通字段-->
        <result column="tb_username" property="username"/>
        <result column="tb_password" property="password"/>
        <result column="tb_nickname" property="nickname"/>
        <result column="tb_email" property="email"/>
        <result column="tb_photo" property="photo"/>
        <result column="tb_regTime" property="regTime"/>
        <result column="tb_loginTime" property="loginTime"/>
    </resultMap>

    <select id="selectUserById" parameterType="int" resultMap="UserResultMap" >
        select tb_uid,tb_username,tb_password,tb_nickname,tb_email,tb_photo,
        tb_regTime,tb_loginTime from ${table} where tb_uid=#{uid}
    </select>


    <select id="selectUserByUsernameAndPassword" resultMap="UserResultMap">
        select tb_uid,tb_username,tb_password,tb_nickname,tb_email,tb_photo,
        tb_regTime,tb_loginTime from ${table} where tb_username=#{username} and tb_password=#{password}
    </select>

    <select id="selectUser"  resultType="User">
        select uid,username,password,nickname,email,photo,regTime,loginTime from user
    </select>
</mapper>
```

### 配置文件

首先创建一个db.properties

```properties
driver=com.mysql.jdbc.Driver
url=jdbc:mysql:///weibo
username=kinglee
password=root
```

在核心配置文件config.xml中引入即可

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>

    <!--引入外部的配置文件-->
    <properties resource="db.properties"/>

    <!--<properties>
        <property name="driver" value="com.mysql.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql:///weibo"/>
                <property name="username" value="kinglee"/>
                <property name="password" value="root"/>
    </properties>-->

    <!--别名配置-->
    <typeAliases>
        <!--配置单个类型的别名-->
<!--        <typeAlias type="com.zlt.entity.User" alias="u"/>-->
        <!--包别名-->
        <package name="com.zlt.entity"/>
    </typeAliases>



    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"></transactionManager>
            <dataSource type="POOLED">
                <property name="driver" value="${driver}"/>
                <property name="url" value="${url}"/>
                <property name="username" value="${username}"/>
                <property name="password" value="${password}"/>
            </dataSource>
        </environment>
    </environments>

    <mappers>
        <mapper resource="com/zlt/dao/UserMapper.xml"/>
<!--        <mapper class="com.zlt.dao.UserMapper_bak"/>-->
    </mappers>
</configuration>
```

## 对象关系映射

在之前的数据库设计时说道，表的映射关系：一对一，一对多，多对一，多对多

在mybatis中，描述对象和对象的关系最后总结下来可以有两种：一对一，一对多

### 一对一映射

自定义的类型中有自定义类型的属性

实体类

```java
package com.zlt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Weibo {

    private int wid;

    private String wcontent;

    private Date sendTime;

    private User user;

    private int state;
}

```

映射文件

```java
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.zlt.dao.WeiBoMapper">

    <sql id="weiboColumn">
        wid,wcontent,sendTime,weibo.uid,state 
    </sql>
    
    <sql id="userColumn">
        user.uid,username,nickname,photo
    </sql>

    <resultMap id="WeiboResultMap" type="Weibo">
        <id property="wid" column="wid"/>
        <result property="wcontent" column="wcontent"/>
        <result property="sendTime" column="sendTime"/>
        <result property="state" column="state"/>
        <!--如果是对象作为属性-->
        <!--<association property="user" javaType="User">
            &lt;!&ndash;主键映射&ndash;&gt;
            <id property="uid" column="uid" />
            &lt;!&ndash;普通字段&ndash;&gt;
            <result column="username" property="username"/>
            <result column="nickname" property="nickname"/>
            <result column="photo" property="photo"/>
        </association>-->
        <association property="user" javaType="User" select="selectUserByUid" column="uid">

        </association>
    </resultMap>

    <select id="selectWeiboById" parameterType="int" resultMap="WeiboResultMap">
        select <include refid="weiboColumn"/>,<include refid="userColumn"/> from weibo
         left join user on weibo.uid=user.uid where weibo.wid=#{wid}
    </select>

    <select id="selectWeiboByWId" parameterType="int" resultMap="WeiboResultMap">
        select <include refid="weiboColumn"/> from weibo
         where weibo.wid=#{wid}
    </select>
    
    <select id="selectUserByUid" parameterType="int" resultType="User">
        select <include refid="userColumn"/> from user where uid=#{uid}
    </select>
</mapper>
```

接口如下

```java
package com.zlt.dao;

import com.zlt.entity.Weibo;

public interface WeiBoMapper {

    Weibo selectWeiboById(int wid);

    Weibo selectWeiboByWId(int wid);
}

```

在一对一的时候可以使用多表查询将多张表的数据查询出来封装成对象，这个时候需要使用resultMap来进行字段和属性的映射，如果类中的属性是自定义的类型则需要使用association 来指定自定义类型中的属性映射

也可以不使用多表查询，而是分开查询因为association  支持select单独查询

### 一对多的映射

自定义类型中有自定义类型的集合

实体类

```java
package com.zlt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Weibo {

    private int wid;

    private String wcontent;

    private Date sendTime;

    private User user;

    private int state;

    private List<Comments> comments;
}

```

映射文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.zlt.dao.WeiBoMapper">

    <sql id="weiboColumn">
        weibo.wid,wcontent,weibo.sendTime,weibo.uid,weibo.state
    </sql>
    
    <sql id="userColumn">
        user.uid,username,nickname,photo
    </sql>

    <resultMap id="WeiboResultMap" type="Weibo">
        <id property="wid" column="wid"/>
        <result property="wcontent" column="wcontent"/>
        <result property="sendTime" column="sendTime"/>
        <result property="state" column="state"/>
        <!--如果是对象作为属性-->
        <!--<association property="user" javaType="User">
            &lt;!&ndash;主键映射&ndash;&gt;
            <id property="uid" column="uid" />
            &lt;!&ndash;普通字段&ndash;&gt;
            <result column="username" property="username"/>
            <result column="nickname" property="nickname"/>
            <result column="photo" property="photo"/>
        </association>-->
        <association property="user" javaType="User" select="selectUserByUid" column="uid">

        </association>
        
<!--        <collection property="comments" select="selectCommentsByWid" ofType="Comments" column="wid"></collection>-->

        <collection property="comments" ofType="Comments" resultMap="CommentsResultMap">
            <!--<id column="cid" property="cid"/>
            <result property="ccontent" column="ccontent"/>
            <result property="sendTime" column="csendTime"/>
            <result property="state" column="cstate"/>-->
        </collection>
    </resultMap>

    <resultMap id="CommentsResultMap" type="Comments">
        <id column="cid" property="cid"/>
        <result property="ccontent" column="ccontent"/>
        <result property="sendTime" column="csendTime"/>
        <result property="state" column="cstate"/>
    </resultMap>

    <select id="selectWeiboById" parameterType="int" resultMap="WeiboResultMap">
        select <include refid="weiboColumn"/>,<include refid="userColumn"/> from weibo
         left join user on weibo.uid=user.uid where weibo.wid=#{wid}
    </select>

    <select id="selectWeiboByWId" parameterType="int" resultMap="WeiboResultMap">
        select <include refid="weiboColumn"/> , cid,ccontent,comments.sendTime csendTime,comments.state cstate from weibo left join comments on weibo.wid=comments.wid
         where weibo.wid=#{wid}
    </select>
    
    <select id="selectUserByUid" parameterType="int" resultType="User">
        select <include refid="userColumn"/> from user where uid=#{uid}
    </select>

    <select id="selectCommentsByWid" parameterType="int" resultMap="CommentsResultMap">
        select cid,ccontent,sendTime,state from comments where wid=#{wid}
    </select>
</mapper>
```

接口

```java
package com.zlt.dao;

import com.zlt.entity.Weibo;

public interface WeiBoMapper {

    Weibo selectWeiboById(int wid);

    Weibo selectWeiboByWId(int wid);
}

```

如果实体中有集合属性那么在映射的时候就需要使用collection  这个标签中要标识集合的泛型需要使用ofType，可以使用多表查询，也可以分开查询，如果是分开查询则需要使用select属性

## 分页插件

在使用mybatis的时候如果涉及到分页，一般都会使用分页插件，PageHelper

添加依赖

```xml
<!-- https://mvnrepository.com/artifact/com.github.pagehelper/pagehelper -->
<dependency>
    <groupId>com.github.pagehelper</groupId>
    <artifactId>pagehelper</artifactId>
    <version>5.2.0</version>
</dependency>

```

在配置文件中添加插件

```xml
 <!--配置插件-->
    <plugins>
        <!--配置分页插件-->
        <plugin interceptor="com.github.pagehelper.PageInterceptor">
            <!--针对分页插件进行配置-->
            <property name="helperDialect" value="mysql"/>

            <property name="reasonable" value="true"/>

            <!--方言
                mybatis可以操作很多种数据库，每一种数据库的分页是有区别的
helperDialect ：分页插件会自动检测当前的数据库链接，自动选择合适的分页方式。 你可以配置 helperDialect 属性来指定分页插件使用哪种方言。配置时，可以使用下面的缩写值：
　　oracle , mysql , mariadb , sqlite , hsqldb , postgresql , db2 , sqlserver , informix , h2 , sqlserver2012 , derby
　　特别注意：使用 SqlServer2012 数据库时，需要手动指定为 sqlserver2012 ，
否则会使用 SqlServer2005 的方式进行分页。 你也可以实现 AbstractHelperDialect ，
然后配置该属性为实现类的全限定名称即可使用自定义的实现方法。


　　　2. offsetAsPageNum ：默认值为 false ，该参数对使用 RowBounds 作为分页参数时有效。 当该参数设置为 true 时，会将 RowBounds 中的 offset 参数当成 pageNum 使用，可以用页码和页面大小两个参数进行分页。
　　　
　　　3. rowBoundsWithCount ：默认值为 false ，该参数对使用 RowBounds 作为分页参数时有效。当该参数设置为 true 时，使用 RowBounds 分页会进行 count 查询。
　　　
　　　4. pageSizeZero ：默认值为 false ，当该参数设置为 true 时，如果 pageSize=0 或者 RowBounds.limit = 0 就会查询出全部的结果（相当于没有执行分页查询，但是返回结果仍然是 Page 类型）。
　　　
　 　5. reasonable ：分页合理化参数，默认值为 false 。当该参数设置为 true 时， pageNum<=0 时会查询第一页， pageNum>pages （超过总数时），会查询最后一页。默认 false 时，直接根据参数进行查询。
　 　
　 　6. params ：为了支持 startPage(Object params) 方法，增加了该参数来配置参数映射，用于从对象中根据属性名取值， 可以配置 pageNum,pageSize,count,pageSizeZero,reasonable ，不配置映射的用默认值， 默认值为 pageNum=pageNum;pageSize=pageSize;count=countSql;reasonable=reasonable;pageSizeZero= pageSizeZero 。
　 　
　　　7. supportMethodsArguments ：支持通过 Mapper 接口参数来传递分页参数，默认值 false ，分页插件会从查询方法的参数值中，自动根据上面 params 配置的字段中取值，查找到合适的
值时就会自动分页。 使用方法可以参考测试代码中的 com.github.pagehelper.test.basic 包
下的 ArgumentsMapTest 和 ArgumentsObjTest 。

　　　8. autoRuntimeDialect ：默认值为 false 。设置为 true 时，允许在运行时根据多数据源自动识别对应方言的分页 （不支持自动选择 sqlserver2012 ，只能使用 sqlserver ），用法和注意事项参考下面的场景五。
　　　
　　　9. closeConn ：默认值为 true 。当使用运行时动态数据源或没有设置 helperDialect 属性自动获取数据库类型时，会自动获取一个数据库连接， 通过该属性来设置是否关闭获取的这个连接，默认 true 关闭，设置为 false 后，不会关闭获取的连接，这个参数的设置要根据自己选择的数据源来决定。
            -->
        </plugin>
    </plugins>
```

在查询之前开启分页

```java
@Test
    public void select2() throws IOException {
        try {
            PageHelper.startPage(2,3);
            if()
            List<User> users = mapper.selectUser();
            System.out.println(users);
            PageInfo<User> pageInfo = new PageInfo<>(users);
            pageInfo.getPageNum();//获取当前页码
            pageInfo.getPages();//总页数
            pageInfo.isHasPreviousPage();//是否有上一页
            pageInfo.getPrePage();//上一页的页码
            pageInfo.isHasNextPage();//是否有下一页
            pageInfo.getNextPage();//下一页的页码
            pageInfo.getTotal();//总条数
            pageInfo.isIsFirstPage();//是否位第一页
            pageInfo.isIsLastPage();//是否为最后一页
            pageInfo.getNavigateFirstPage();//获取第一页
            pageInfo.getNavigateLastPage();//获取最后一页
            int[] navigatepageNums = pageInfo.getNavigatepageNums();//获取所有页码
            logger.debug(pageInfo);
            for (User user : users) {
                System.out.println(user);
            }
        }catch (Exception e){
            logger.debug("查询失败",e);
            sqlSession.rollback();
        }
    }
```

分页插件主要使用了ThreadLocal这个类，当调用了PageHelper.startPage() 后会将第几页，每页多少条数据放入ThreadLocal中，在接下来的一次查询中，拦截器会从ThreadLocal中获取数据进行分页操作。查询完成后的List可以使用PageInfo进行封装，这个对象就可以获取跟分页相关的所有数据

注意：一般来说在开启分页的时候需要先确定针对哪一个查询进行分页，然后在查询之前来调用开启分页，否则可能会出现分页混乱

## 动态sql

举例：在京东上面搜索手机，会出来很多的筛选条件，那些条件排列组合后有很多种可能，那么我们不可能去写对应的那么多的sql语句。如果sql能够根据条件动态的进行调整，那么这个sql就只需要写一个就好了。

mybatis的动态sql是一个比较特色的功能，在实际的开发中使用还是非常多的。

### if

```xml
<select id="selectUser2" parameterType="User"  resultType="User">
        select uid,username,password,nickname,email,photo,regTime,loginTime from user
        where 1=1
        <if test="username != null">
           and username=#{username}
        </if>
        <if test="email != null">
            and email=#{email}
        </if>

    </select>
```

### where

当where标签中包含了内容后，where标签会自动生成一个where关键字，如果where标签里面没有内容，那么where关键字就不会生成。

当where标签包含的sql如果是以and或者or开头的 会自动去除开头的and 和 or

where 标签中的sql 如果条件有多个，后面的条件一定要记得加 and 和 or  一般建议把and 和 or加在前面

```xml
<select id="selectUser2" parameterType="User"  resultType="User">
        select uid,username,password,nickname,email,photo,regTime,loginTime from user

        <where>
            <if test="username != null">
               username=#{username}
            </if>
            <if test="email != null">
                and email=#{email}
            </if>
        </where>
    </select>
```

### choose  when  otherwise

和java中的switch case 类似

多个when和otherwise的时候只会执行一个，如果所有的when都没执行，那么会进入otherwise

```xml
<select id="selectUser2" parameterType="User"  resultType="User">
        select uid,username,password,nickname,email,photo,regTime,loginTime from user
        <where>
            <choose>
                <when test="username != null">
                    username=#{username}
                </when>
                <when test="email != null">
                    email=#{email}
                </when>
                <otherwise>
                    1=1
                </otherwise>
            </choose>
        </where>
    </select>
```

### set

主要用于update语句中，确认修改的字段使用的

如果set标签中以,逗号结尾  会自动去除最后的逗号

```xml
<update id="updateUser" parameterType="User">
        update user
         <set>
             <if test="password != null">
                 password=#{password},
             </if>
             <if test="email != null">
                 email=#{email},
             </if>
             <if test="photo != null">
                 photo=#{photo}
             </if>

         </set>
         where uid=#{uid}
    </update>
```

### foreach

主要用来循环集合使用的 

collection 对应的为集合的对象，open 自动生成的开始部分，close 自动生成的结束部分，separator ，分割部分， item 循环出来的每一个元素  ，index 每一个元素对应的索引

```xml
<select id="selectUser3" parameterType="java.util.List" resultType="User">
        select uid,username,password,nickname,email,photo,regTime,loginTime from user
        <where>
            <if test="ids != null">
                uid in
                <foreach collection="ids" open="(" close=")" separator="," index="i" item="item">
                    #{item}
                </foreach>
            </if>
        </where>
    </select>
```

还可以使用循环做批量添加

```xml
<insert id="insertBatch" parameterType="java.util.List" >
        insert into user (username,password) 
        values
        <foreach collection="users" separator="," item="item">
            (
                #{item.username},
                #{item.password}
            )
        </foreach>
</insert>
```

### trim

自定义去除或者添加内容，一般可以替代set和where

prefix  添加指定的前缀    prefixOverrides 去除指定的前缀   suffix 添加指定的后缀    suffixOverrides 去除指定的后缀

```xml
<select id="selectUser2" parameterType="User"  resultType="User">
        select uid,username,password,nickname,email,photo,regTime,loginTime from user

        <trim prefix="where" prefixOverrides="and | or" suffix=";" suffixOverrides="o">
            <if test="username != null">
                username=#{username}
            </if>
            <if test="email != null">
                and email=#{email}
            </if>
            o
        </trim>
</select>
```

```xml
<update id="updateUser" parameterType="User">
        update user
         <trim  prefix="set"  suffixOverrides=",">
             <if test="password != null">
                 password=#{password},
             </if>
             <if test="email != null">
                 email=#{email},
             </if>
             <if test="photo != null">
                 photo=#{photo}
             </if>

         </trim>
         where uid=#{uid}
    </update>
```

### selectKey

可以让insert得到添加进去数据的主键，需要注意的是 主键的值会放在参数对象中，而不是以方法的返回类型返回回来，

```xml
<insert id="insertUser2" parameterType="User" >
        insert into user (uid,username,password,nickname,email,photo,regTime,loginTime) values
        (#{uid},#{username},#{password},#{nickname},#{email},#{photo},#{regTime},#{loginTime})
        <selectKey resultType="int" keyProperty="uid" order="AFTER">
            select last_insert_id();
        </selectKey>
    </insert>
```

测试方法

```java
 @Test
    public void insert2() throws IOException {
        try {
            User user = new User(0,"lisi2","123456","李四","123@123.com",null,new Date(),new Date());
            int i = mapper.insertUser2(user);
            System.out.println(i);
            //user中的主键已经发生了改变
            System.out.println(user);
        }catch (Exception e){
            logger.debug("插入失败",e);
            sqlSession.rollback();
        }
    }
```

## 缓存

作为一个持久层框架，缓存是必须考虑的一个功能，使用了缓存可以减少数据库的查询次数，提升用户体验

mybatis是有缓存功能的，mybatis缓存主要分为两级，分别是：一级缓存和二级缓存。

### 一级缓存

一级缓存也叫做session级缓存，也就是说在同一个sqlSession上，查询同样的数据的时候，只会查询一次，在第一次查询的时候就将查询的数据放入sqlSession中，那么后面再使用这个sqlSession查询同样的数据的时候，直接从缓存中去找，如果缓存中没有这个数据则去数据库查询

一级缓存默认开启

```java
@Test
    public void test3(){
        User user = new User();
//        user.setUsername("zhangsan");
        user.setEmail("11@qq.com");
        List<User> users = mapper.selectUser2(user);
        List<User> users2 = mapper.selectUser2(user);

    }
```

一级缓存在实际的开发中使用并不多，因为在不同的session中是无法共享的

### 二级缓存

二级缓存又叫做SessionFactory级缓存，可以在多个session中共享数据，那么在实际的开发中使用就会多一点。

使用二级缓存首先需要开启二级缓存

在核心配置文件config.xml中开启

```xml
<!--设置-->
    <settings>
        <!--开启二级缓存-->
        <setting name="cacheEnabled" value="true"/>
    </settings>
```

在需要使用缓存的映射文件中添加配置

```xml
 <!--eviction="LRU"  FIFO 缓存回收算法

        flushInterval=""  缓存刷新时间
        readOnly="true" 只读缓存
        size="5000"  缓存中存储的数据量
     -->
    <cache eviction="LRU"
        flushInterval="60000"
           readOnly="true"
           size="5000"
    />
```

在使用的时候

```java

    @Test
    public void test3(){
        User user = new User();
//        user.setUsername("zhangsan");
        user.setEmail("11@qq.com");
        List<User> users = mapper.selectUser2(user);
        sqlSession.commit();//在提交事务的时候才会把查询的数据放入缓存

        //重新开启一个session
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        List<User> users2 = mapper.selectUser2(user);

    }
```

## 懒加载

懒加载在实际的开发过程中使用的频率还是很高的，当查询一个数据之后，如果这个数据不是立即使用，那么可以先返回一个代理对象，当使用这个代理对象的时候才会去数据库执行查询变成真正的包含数据的对象。

要想使用懒加载按照如下方式进行配置

现在核心配置文件配置如下内容

```xml
<!--设置-->
    <settings>
        <!--开启二级缓存-->
        <setting name="cacheEnabled" value="true"/>
        <!--开启懒加载-->
        <setting name="lazyLoadingEnabled" value="true"/>
        <!--是否按需加载 false 按需加载  true 立即加载 -->
        <setting name="aggressiveLazyLoading" value="false"/>
    </settings>
```

在映射文件中，需要配置懒加载的地方添加fetchType=“lazy”

```xml
<resultMap id="WeiboResultMap" type="Weibo">
        <id property="wid" column="wid"/>
        <result property="wcontent" column="wcontent"/>
        <result property="sendTime" column="sendTime"/>
        <result property="state" column="state"/>
      
        <association property="user" javaType="User" select="selectUserByUid" column="uid" fetchType="lazy">

        </association>
        
        <collection property="comments" ofType="Comments" select="selectCommentsByWid" column="wid" fetchType="lazy">
          
        </collection>
    </resultMap>
```

进行上述的配置后，在查询weibo的时候就不会立即去查询用户和评论内容，只有在使用的时候才会去执行对应的查询

## 事务的并发

事务是针对数据库的一组操作，这一组操作中可以包含多个增删改查的过程，而一些操作会被包含在一起。遵循事务的4个原则

事务的4个原则 ACID

原子性： 一组事务包含的操作，要么全部成功，要么全部失败

一致性： 事务操作完成后，数据库的整体是一致的

隔离性： 事务之间应该是互不影响的，事务和事务之间是相互隔离的

持久性： 事务成功提交以后对数据库的改变是永久的



事务在并发的时候可能会造成一些问题：第一类丢失更新，第二类丢失更新，不可重复读，脏读，虚读等问题。

为了解决上述的问题，解决方案如下：

事务具有隔离性，数据库针对事务的隔离性一般分为4个隔离级别：

为了解决数据库事务并发运行时的各种问题数据库系统提供四种事务隔离级别：

1. Serializable 串行化

2. Repeatable Read 可重复读

3. Read Commited 可读已提交

4. Read Uncommited 可读未提交

这四个隔离级别的并发性能如下图：

![img](mybatis.assets/1603476.png)

针对上述的隔离级别，可能会发生的问题如下：

![img](mybatis.assets/1603477.png)

综合上面的两图，在实际的开发过程选择合适的隔离级别是比较重要的，mysql默认的隔离级别是Repeatable Read 可重复读，oracle默认的隔离级别是Read Commited 可读已提交

在mysql中

查看当前会话的隔离级别

select @@tx_isolation

查看系统的隔离级别

select @@global.tx_isolation

修改当前会话的隔离级别

set SESSION TRANSACTION ISOLATION LEVEL SERIALIZABLE
set SESSION TRANSACTION ISOLATION LEVEL REPEATABLE READ
set SESSION TRANSACTION ISOLATION LEVEL READ COMMITTED
set SESSION TRANSACTION ISOLATION LEVEL READ UNCOMMITTED

修改系统的隔离级别

set GLOBAL TRANSACTION ISOLATION LEVEL SERIALIZABLE
set GLOBAL TRANSACTION ISOLATION LEVEL REPEATABLE READ
set GLOBAL TRANSACTION ISOLATION LEVEL READ COMMITTED
set GLOBAL TRANSACTION ISOLATION LEVEL READ UNCOMMITTED

虽然数据库有隔离级别，其中mysql默认的隔离级别还比较高，但是还是无法解决所有的问题。

如果在不改变隔离级别的情况下，想要解决上述的问题，可以考虑使用乐观锁或者悲观锁来进行解决。



### 悲观锁

悲观锁认为每一次操作数据库的时候都可能会发生问题，就需要每次操作的时候都把数据进行加锁操作，这样的话，事务操作的时候就会将数据加锁，其他的事务就无法进行操作。

悲观锁的实现非常简单，在sql的最后添加一个for update

悲观锁都是加在查询语句上，直接锁表。效率不高

```sql
select * from user for update
```

悲观锁不建议使用

### 乐观锁

乐观锁认为每一次操作数据库的时候都不会发生问题

在设计表的时候会为表添加一个字段叫做version，事务开启的时候先查询version，接下来进行操作，操作完成后，提交事务的时候version+1.

在提交的时候需要再次查询version如果两次version不一致表示在操作期间，其他的事务操作了这个数据，那么就抛出异常，由程序员进行对应的处理，如果提交的时候发现两次version是一致的，那么+1正常提交即可。

在实际开发的时候，我们应该尽可能从业务设计的角度来避免上述问题，这样的话乐观锁和悲观锁都尽可能少用一些，让每次事务都提交成功

## mybatis逆向工程

在使用mybatis的时候，需要有接口，映射的xml文件，实体类等。实现的话有点麻烦，所以出现一个逆向工程。

添加依赖

```xml
<!-- https://mvnrepository.com/artifact/org.mybatis.generator/mybatis-generator-core -->
<dependency>
    <groupId>org.mybatis.generator</groupId>
    <artifactId>mybatis-generator-core</artifactId>
    <version>1.3.7</version>
</dependency>

```

给项目添加以逆向工程的配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
  PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
  "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
<generatorConfiguration>
    <!--数据库驱动-->
    <classPathEntry    location="F:\mybatisreverse\mysql-connector-java-5.1.16-bin.jar"/>
    <context id="DB2Tables"    targetRuntime="MyBatis3">
        <commentGenerator>
            <property name="suppressDate" value="true"/>
            <property name="suppressAllComments" value="true"/>
        </commentGenerator>
        <!--数据库链接地址账号密码-->
        <jdbcConnection driverClass="com.mysql.jdbc.Driver" connectionURL="jdbc:mysql:///weibo" userId="root" password="root">
        </jdbcConnection>
        <javaTypeResolver>
            <property name="forceBigDecimals" value="false"/>
        </javaTypeResolver>
        <!--生成Model类存放位置-->
        <javaModelGenerator targetPackage="com.zlt.entity" targetProject="src/main/java">
            <property name="enableSubPackages" value="true"/>
            <property name="trimStrings" value="true"/>
        </javaModelGenerator>
        <!--生成映射文件存放位置-->
        <sqlMapGenerator targetPackage="mapper" targetProject="src/main/resources">
            <property name="enableSubPackages" value="true"/>
        </sqlMapGenerator>
        <!--生成Dao类存放位置-->
        <javaClientGenerator type="XMLMAPPER" targetPackage="com.zlt.mapper" targetProject="src/main/java">
            <property name="enableSubPackages" value="true"/>
        </javaClientGenerator>
        <!--生成对应表及类名-->
		<table tableName="comments" domainObjectName="Comments" enableCountByExample="true" enableUpdateByExample="true" enableDeleteByExample="true" enableSelectByExample="true" selectByExampleQueryId="true"></table>
		<table tableName="weibo" domainObjectName="Weibo" enableCountByExample="true" enableUpdateByExample="true" enableDeleteByExample="true" enableSelectByExample="true" selectByExampleQueryId="true"></table>
		<table tableName="user" domainObjectName="User" enableCountByExample="true" enableUpdateByExample="true" enableDeleteByExample="true" enableSelectByExample="true" selectByExampleQueryId="true"></table>
		</context>
</generatorConfiguration>
```

添加生成内容的代码

```java
public class App 
{
    public static void main( String[] args ) throws IOException, XMLParserException, InvalidConfigurationException, SQLException, InterruptedException {
        List<String> warn = new ArrayList<>();
        //创建配置解析器
        ConfigurationParser configurationParser = new ConfigurationParser(warn);

        //解析配置信息  获取配置对象
        Configuration configuration = configurationParser.parseConfiguration(Resources.getResourceAsReader("generatorConfi.xml"));

        DefaultShellCallback shellCallback = new DefaultShellCallback(true);

        //创建生成器
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(configuration,shellCallback,warn);
        //执行生成
        myBatisGenerator.generate(null);
    }
}

```

简单的使用

```java
 UserExample userExample = new UserExample();
        userExample.createCriteria().andNicknameLike("张%").andPasswordLike("1%");
        long l = userMapper.countByExample(userExample);
        System.out.println(l);

其中Criteria 包含了这个类型中所有属性的where操作，那么用来执行单表增删改查就会方便
```

![](mybatis.assets/java.png)