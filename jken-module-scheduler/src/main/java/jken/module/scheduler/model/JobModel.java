package jken.module.scheduler.model;

import com.google.common.base.MoreObjects;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.impl.JobDetailImpl;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

public class JobModel {
    private String group;

    @NotNull
    private String name;

    @NotNull
    private Class<? extends Job> jobClass;

    private Map<String, Object> jobDataMap;
    private String description;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<? extends Job> getJobClass() {
        return jobClass;
    }

    public void setJobClass(Class<? extends Job> jobClass) {
        this.jobClass = jobClass;
    }

    public Map<String, Object> getJobDataMap() {
        return jobDataMap;
    }

    public void setJobDataMap(Map<String, Object> jobDataMap) {
        this.jobDataMap = jobDataMap;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static JobModel from(JobDetail jobDetail) {
        JobModel model = new JobModel();
        model.setName(jobDetail.getKey().getName());
        model.setGroup(jobDetail.getKey().getGroup());
        model.setJobClass(jobDetail.getJobClass());
        model.setJobDataMap(jobDetail.getJobDataMap());
        model.setDescription(jobDetail.getDescription());
        return model;
    }

    public JobDetail toJobDetail() {
        JobDetailImpl jobDetail = new JobDetailImpl();
        jobDetail.setName(this.name);
        jobDetail.setGroup(this.group);
        jobDetail.setJobClass(this.jobClass);
        jobDetail.setJobDataMap(new JobDataMap(MoreObjects.firstNonNull(this.jobDataMap, new HashMap<>())));
        jobDetail.setDescription(this.description);
        jobDetail.setDurability(true);
        return jobDetail;
    }
}
