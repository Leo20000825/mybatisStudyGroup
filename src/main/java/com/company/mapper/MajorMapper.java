package com.company.mapper;

import com.company.entity.Accademy;
import com.company.entity.Class_1;
import com.company.entity.Major;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MajorMapper {

    public void simpleInsert(Major major);
    public void simpleDeleteById(Integer id);
    public void simpleUpdate(@Param("majorName")String majorName,@Param("accademy")Integer accademyId,@Param("majorId") Integer majorId);
    public List<Major> simpleSelect();
    public List<Major> resultMap();
    public List<Major> association(Integer accademyId);
    public List<Major> selectMajorWithAccademy(int accademyId);
    public Major selectMajorStep(int majorId);
    public void batchAddcc1(@Param("majors") List<Major> majors);
    public List<Major>getMajorListByIds(@Param("majorIds") List majorIds);
    public List<Accademy> getAccademyAndMajors(Integer academyId);

    public List<Major> getMajor();
    @Select("select * from major limit 2")
    public List<Major> getMajor2();
    public void insertMajor(Major major);
    public void updateMajor(Major major);
    public void deleteMajorByid(int id);
    public void insertMajorWithPrimaryKey(Major major);
    public void insertSelectKey(Major major);
    public List<Major> getMajorById(@Param("majorIds") Integer majorId);
    public List<Major> getMajorByAccademyId(Integer accademyId);
    public List<Class_1> getClasses(@Param("majorId") Integer majorId);

}
