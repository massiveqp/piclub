package com.lifeschool.piclub.controller;

import com.lifeschool.piclub.model.Enrollment;
import com.lifeschool.piclub.service.EnrollmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/enrollment/{enrollmentId}")
public class EnrollmentController {
    private static Logger logger = LoggerFactory.getLogger(EnrollmentController.class);

    @Autowired
    private EnrollmentService enrollmentService;

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelEnroll(@PathVariable String enrollmentId) {
        logger.info("cancelEnroll...");

        enrollmentService.cancelEnroll(enrollmentId);
    }

    @PostMapping(value = "/payStatus")
    public void modifyPayStatus(@RequestBody Enrollment enrollment) {
        logger.info("update pay status");

        enrollmentService.updatePaymentStatus(enrollment.getEnrollmentId(), enrollment.getPayStatus());
    }

    @PostMapping(value = "/checkedIn")
    public void modifyCheckedIn(@RequestBody Enrollment enrollment) {
        logger.info("update checked in");

        enrollmentService.updateCheckedInStatus(enrollment.getEnrollmentId(), enrollment.getCheckedIn());
    }
}
