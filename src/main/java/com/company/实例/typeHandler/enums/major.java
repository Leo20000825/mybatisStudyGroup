package com.company.实例.typeHandler.enums;

public enum major {
    COMPUTER(1,"计算机学院"),FORIGEN_LANGUAGE(2,"外语学院");

    private  Integer code;
    private  String msg;
    private major(Integer code,String msg) {
        this.code=code;
        this.msg=msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    //按照状态码返回msg
    public static String codeToMsg(Integer code){
        switch (code){
            case 1:
                return "计算机学院";
            case 2:
                return "外语学院";
        }
        return "未知学院";
    }
}
