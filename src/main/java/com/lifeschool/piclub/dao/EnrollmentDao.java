package com.lifeschool.piclub.dao;

import com.lifeschool.piclub.model.Enrollment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EnrollmentDao {
    @Results(
            id = "EnrollmentMap", value = {
            @Result(column = "enrollment_id", property = "enrollmentId"),
            @Result(column = "activity_id", property = "activityId"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "pay_status", property = "payStatus"),
            @Result(column = "checked_in", property = "checkedIn"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime"),
            @Result(column = "username", property = "username"),
            @Result(column = "enroll_status", property = "enrollStatus"),
            @Result(column = "user_level", property = "userLevel")
    })
    @Select("select * from enrollment")
    List<Enrollment> selectEnrollments();

    @Select("select * from enrollment where enrollment_id = #{enrollmentId}")
    @ResultMap("EnrollmentMap")
    Enrollment selectEnrollmentById(String enrollmentId);

    @Select("select * from enrollment where activity_id = #{activityId}")
    @ResultMap("EnrollmentMap")
    List<Enrollment> selectEnrollmentByAct(String activityId);

    @Select("select * from enrollment where user_id = #{userId}")
    @ResultMap("EnrollmentMap")
    List<Enrollment> selectEnrollmentByUser(String userId);

    @Select("select * from enrollment where activity_id = #{param1} and username = #{param2}")
    @ResultMap("EnrollmentMap")
    Enrollment findEnrollByActAndUser(String activityId, String username);

    @Insert("insert into enrollment (activity_id, user_id, username, pay_status, checked_in, " +
            "enroll_status, user_level)" +
            "values (#{activityId}, #{userId}, #{username}, #{payStatus}, #{checkedIn}, " +
            "#{enrollStatus}, #{userLevel})")
    void insertEnrollment(Enrollment enrollment);

    @Update("update enrollment set pay_status = #{payStatus} " +
            "where enrollment_id = #{enrollmentId}")
    void updatePayStatus(Enrollment enrollment);

    @Update("update enrollment set checked_in = #{checkedIn} " +
            "where enrollment_id = #{enrollmentId}")
    void updateCheckedIn(Enrollment enrollment);

    @Update("update enrollment set enroll_status = #{enrollStatus} " +
            "where enrollment_id = #{enrollmentId}")
    void updateEnrollStatus(Enrollment enrollment);

    @Delete("delete from enrollment where enrollment_id = #{param1}")
    void deleteEnrollmentById(String enrollmentId);

    // for test use
    @Delete("delete from enrollment where activity_id = #{activityId}")
    void deleteEnrollmentByActId(String activityId);
}
