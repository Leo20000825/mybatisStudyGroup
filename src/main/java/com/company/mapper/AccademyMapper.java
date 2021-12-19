package com.company.mapper;

import com.company.entity.Accademy;
import com.company.entity.Major;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AccademyMapper {
    //测试修改123123
    public Accademy getAccademyById(int academyId);
    public Accademy getAccademyAndMajors(Integer academyId);
    public List<Major> getMajorListByIds(Accademy accademy);
    public List<Major> getMajorListByIds_2(List<Integer> ids);

    public List<Accademy> getAccademyBycriteria(@Param("id") Integer id,@Param("name") String name);
}
