package com.company.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Alias("Accademy")
public class Accademy implements Serializable {
    private int accademyId;
    private String accademyName;
    private String profile;
    private String location;
    private List<Major> majors;
    private List<Integer> majorIds;
}
