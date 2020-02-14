package jken.module.scheduler.model;

import jken.module.scheduler.support.BooleanMap;
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

    private final Map<String, Object> objectMap = new HashMap<>();
    private final BooleanMap booleanMap = new BooleanMap();

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

    public Map<String, Object> getObjectMap() {
        return objectMap;
    }

    public Map<String, Boolean> getBooleanMap() {
        return booleanMap;
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

        for (Map.Entry<String, Object> entry : jobDetail.getJobDataMap().entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof Boolean) {
                model.getBooleanMap().put(key, (Boolean) value);
            } else {
                model.getObjectMap().put(key, value);
            }
        }

        model.setDescription(jobDetail.getDescription());
        return model;
    }

    public JobDetail toJobDetail() {
        JobDetailImpl jobDetail = new JobDetailImpl();
        jobDetail.setName(this.name);
        jobDetail.setGroup(this.group);
        jobDetail.setJobClass(this.jobClass);

        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.putAll(this.objectMap);
        jobDataMap.putAll(this.booleanMap);
        jobDetail.setJobDataMap(jobDataMap);

        jobDetail.setDescription(this.description);
        jobDetail.setDurability(true);
        return jobDetail;
    }
}
