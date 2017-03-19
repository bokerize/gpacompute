package com.bok.gpacomputer.entity;

import android.content.ContentValues;
import android.provider.BaseColumns;
import android.util.StringBuilderPrinter;

import com.bok.gpacomputer.db.SqlHelper;

/**
 * Created by JerichoJohn on 3/18/2017.
 */

public class TranscriptLine {

    public static final String TABLE_NAME = "transcript_line";
    public static final String COL_ID = BaseColumns._ID;
    public static final String COL_COURSE_NO = "course_no";
    public static final String COL_COURSE_DESC = "course_desc";
    public static final String COL_GRADE ="grade";
    public static final String COL_CREDIT = "credit";

    public static final String CREATE_TABLE_SQL = getCreateTableSql();

    private static String getCreateTableSql() {
        StringBuilder sb = new StringBuilder();
        sb.append(SqlHelper.CREATE_TABLE).append(TABLE_NAME);
        sb.append("(").append(COL_ID).append(SqlHelper.ID_TYPE).append(", ");
        sb.append(COL_COURSE_NO).append(SqlHelper.TEXT_TYPE).append(", ");
        sb.append(COL_COURSE_DESC).append(SqlHelper.TEXT_TYPE).append(", ");
        sb.append(COL_GRADE).append(SqlHelper.TEXT_TYPE).append(", ");
        sb.append(COL_CREDIT).append(SqlHelper.REAL_TYPE).append(");");

        return sb.toString();
    }

    public static final String[] ALL_COLUMNS = {COL_ID,
        COL_COURSE_NO,
        COL_COURSE_DESC,
        COL_GRADE,
        COL_CREDIT
    };

    private Long id;
    private String courseNo;
    private String courseDesc;
    private String grade;
    private Double credit;

    public TranscriptLine() {
    }

    public TranscriptLine(String courseNo, String courseDesc, String grade, Double credit) {
        this.courseNo = courseNo;
        this.courseDesc = courseDesc;
        this.grade = grade;
        this.credit = credit;
    }

    public ContentValues toContentValues() {

        ContentValues contentValues = new ContentValues();
        if (getId() != null) {
            contentValues.put(COL_ID, getId());
        }
        contentValues.put(COL_COURSE_NO, getCourseNo());
        contentValues.put(COL_COURSE_DESC, getCourseDesc());
        contentValues.put(COL_GRADE, getGrade());
        contentValues.put(COL_CREDIT, getCredit());

        return contentValues;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(String courseNo) {
        this.courseNo = courseNo;
    }

    public String getCourseDesc() {
        return courseDesc;
    }

    public void setCourseDesc(String courseDesc) {
        this.courseDesc = courseDesc;
    }



    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }


    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }
}
