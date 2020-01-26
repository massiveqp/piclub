package com.lifeschool.piclub.service;

import com.lifeschool.piclub.dao.ActivityDao;
import com.lifeschool.piclub.enums.ErrorMessage;
import com.lifeschool.piclub.exception.BizException;
import com.lifeschool.piclub.model.Activity;
import com.lifeschool.piclub.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityService {
    @Autowired
    private ActivityDao activityDao;

    public Activity getActivity(String activityId) {
        Activity activity = activityDao.selectActivityById(activityId);

        return activity;
    }

    public List<Activity> getActivities() {
        return activityDao.selectActivities();
    }

    public Activity createActivity(Activity activity) {

        activity.setStatus(0);

        activityDao.insertActivity(activity);

        return activityDao.selectActivityByName(activity.getActivityName());
    }

    public void deleteActivity(String activityId) {
        if (StringUtils.isBlank(activityId)) throw new BizException(ErrorMessage.request_param_error);

        activityDao.deleteActivity(activityId);
    }

    public Activity updateActivity(Activity activity) {
        if (StringUtils.isBlank(activity.getActivityId())
        || StringUtils.isBlank(activity.getActivityName())
        || StringUtils.isBlank(activity.getStartTime())
        || null == activity.getPlace()) throw new BizException(ErrorMessage.request_param_error);

        activityDao.updateActivity(activity);

        return activityDao.selectActivityById(activity.getActivityId());
    }
}
