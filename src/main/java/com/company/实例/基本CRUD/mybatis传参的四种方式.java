package com.company.实例.基本CRUD;

public class mybatis传参的四种方式 {
    /*
    *    方式一、顺序传递参数


            public User selectUser(String name, int deptId);


            <select id="selectUser" resultType="com.wyj.entity.po.User">
                select * from user where userName = #{0} and deptId = #{1}
            </select>
            *
            *
            *
        方式二、注解@Param传递参数
        *


                public User selectUser(@Param("userName") String name, int @Param("deptId") id);


                <select id="selectUser" resultType="com.wyj.entity.po.User">
                    select * from user where userName = #{userName} and deptId = #{deptId}
                </select>

                注意：在xml文件中就只能以在@Param注解中声明的参数名称获取参数

*
*

        方式三、使用Map集合传递参数


                public User selectUser(Map<String, Object> params);


                <select id="selectUser" parameterType="java.util.Map" resultType="com.wyj.entity.po.User">
                    select * from user where userName = #{userName} and deptId = #{deptId}
                </select>

            *
            *
         方式四、使用JavaBean实体类传递参数

                public User selectUser(User user);

                <select id="selectUser" parameterType="com.wyj.entity.po.User" resultType="com.wyj.entity.po.User">
                    select * from user where userName = #{userName} and deptId = #{deptId}
                </select>

     */

}
