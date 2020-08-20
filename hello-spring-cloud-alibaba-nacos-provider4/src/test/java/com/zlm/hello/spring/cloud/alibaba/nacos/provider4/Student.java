package com.zlm.hello.spring.cloud.alibaba.nacos.provider4;

/**
 * 学生信息
 */
public class Student {
    /** 姓名 */
    private String name;
    /** 总分 */
    private int totalScore;
    /** 是否本地人 */
    private boolean local;
    /** 年级 */
    private GradeType gradeType;

    /**
     * 年级类型
     */
    public enum GradeType {ONE,TWO,THREE}

    public Student(String name, int totalScore, boolean local, GradeType gradeType) {
        this.name = name;
        this.totalScore = totalScore;
        this.local = local;
        this.gradeType = gradeType;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", totalScore=" + totalScore +
                ", local=" + local +
                ", gradeType=" + gradeType +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public boolean isLocal() {
        return local;
    }

    public void setLocal(boolean local) {
        this.local = local;
    }

    public GradeType getGradeType() {
        return gradeType;
    }

    public void setGradeType(GradeType gradeType) {
        this.gradeType = gradeType;
    }
}
