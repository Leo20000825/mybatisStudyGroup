package com.company.entity;


import com.company.实例.typeHandler.enums.major;
import lombok.*;
import org.apache.ibatis.type.Alias;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias(value = "Major")
public class Major {
    private int majorId;
    private String majorName;
    private int accademy;
    private Accademy ady;
    private major enumTest;

    public Major(int majorId, String majorName, int accademy) {
        this.majorId = majorId;
        this.majorName = majorName;
        this.accademy = accademy;
    }public Major(int majorId, String majorName, int accademy,Accademy ady) {
        this.majorId = majorId;
        this.majorName = majorName;
        this.accademy = accademy;
    }
}
