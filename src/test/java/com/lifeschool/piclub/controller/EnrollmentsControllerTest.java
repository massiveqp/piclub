package com.lifeschool.piclub.controller;


import com.lifeschool.piclub.dao.EnrollmentDao;
import com.lifeschool.piclub.model.Enrollment;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EnrollmentsControllerTest {
    @Autowired
    private EnrollmentDao enrollmentDao;

    @Autowired
    private EnrollmentsController controller;

    private static final String activity_ID_1 = "98765401";
    private static final String activity_ID_2 = "98765402";
    private static final String activity_ID_ENROLL = "98765403";

    @Before
    public void setUp() {
        Enrollment enrollment = new Enrollment();
        enrollment.setActivityId(activity_ID_1);
        enrollment.setUserId("666");
        enrollment.setPayStatus(0);
        enrollment.setCheckedIn(0);
        enrollment.setEnrollStatus(1);
        enrollment.setUsername("RockLee");

        enrollmentDao.insertEnrollment(enrollment);

        enrollment.setActivityId(activity_ID_2);
        enrollmentDao.insertEnrollment(enrollment);

    }

    @After
    public void tearDown() {
        enrollmentDao.deleteEnrollmentByActId(activity_ID_2);
        enrollmentDao.deleteEnrollmentByActId(activity_ID_1);
    }

    @Test
    public void testGetEnrollByActId() {
        List<Enrollment> enrollmentList = controller.getEnrollmentsByActId(activity_ID_2, false);

        assertEquals(1, enrollmentList.size());

        assertEquals(activity_ID_2, enrollmentList.get(0).getActivityId());
    }

    @Test
    public void testEnroll() {
        //clean data
        enrollmentDao.deleteEnrollmentByActId(activity_ID_ENROLL);

        Enrollment enrollment = new Enrollment();
        enrollment.setActivityId(activity_ID_ENROLL);
        enrollment.setUsername("Naruto");

        ResponseEntity resp = controller.doEnroll(enrollment);
        assertEquals(HttpStatus.CREATED, resp.getStatusCode());

        //verify
        List<Enrollment> enrollments = enrollmentDao.selectEnrollmentByAct(activity_ID_ENROLL);
        Enrollment created = enrollments.get(0);

        assertNotNull(created);
        assertEquals("Naruto", created.getUsername());
        assertEquals("666", created.getUserId());
        assertEquals(1, created.getEnrollStatus().intValue());
        assertEquals(0, created.getPayStatus().intValue());
        assertEquals(0, created.getCheckedIn().intValue());

        //tear down
        enrollmentDao.deleteEnrollmentByActId(activity_ID_ENROLL);
    }

    //negative cases
    @Test(expected = Exception.class)
    public void testEnrollWithNoneParam() {
        Enrollment enrollment = new Enrollment();
        controller.doEnroll(enrollment);
    }

    @Test(expected = Exception.class)
    public void testEnrollWithoutUsername() {
        Enrollment enrollment = new Enrollment();
        enrollment.setActivityId(activity_ID_1);

        controller.doEnroll(enrollment);
    }
}
