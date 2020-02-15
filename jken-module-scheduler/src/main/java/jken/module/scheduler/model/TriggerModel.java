package jken.module.scheduler.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jken.module.scheduler.support.BooleanMap;
import org.quartz.*;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TriggerModel {

    public enum Type {
        Simple,
        Cron,
        DailyTimeInterval,
        CalendarInterval
    }

    @NotNull
    private String name;

    private String group;

    private String jobName;

    private String jobGroup;

    private String description;

    private String calendarName;

    private Type type;

    private final Map<String, Object> objectMap = new HashMap<>();
    private final BooleanMap booleanMap = new BooleanMap();

    // Cron
    private String cronExpression;

    // Simple
    private int repeatCount = 0;
    private long repeatInterval = 0;
    private int timesTriggered = 0;

    private int priority = 0;

    public boolean mayFireAgain;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date endTime;

    public Date nextFireTime;
    public Date previousFireTime;
    public Date finalFireTime;

    public int misfireInstruction;

    public TriggerKey triggerKey() {
        return TriggerKey.triggerKey(name, group);
    }

    public JobKey jobKey() {
        return JobKey.jobKey(this.jobName, this.jobGroup);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCalendarName() {
        return calendarName;
    }

    public void setCalendarName(String calendarName) {
        this.calendarName = calendarName;
    }

    public Map<String, Object> getObjectMap() {
        return objectMap;
    }

    public BooleanMap getBooleanMap() {
        return booleanMap;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isMayFireAgain() {
        return mayFireAgain;
    }

    public void setMayFireAgain(boolean mayFireAgain) {
        this.mayFireAgain = mayFireAgain;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getNextFireTime() {
        return nextFireTime;
    }

    public void setNextFireTime(Date nextFireTime) {
        this.nextFireTime = nextFireTime;
    }

    public Date getPreviousFireTime() {
        return previousFireTime;
    }

    public void setPreviousFireTime(Date previousFireTime) {
        this.previousFireTime = previousFireTime;
    }

    public Date getFinalFireTime() {
        return finalFireTime;
    }

    public void setFinalFireTime(Date finalFireTime) {
        this.finalFireTime = finalFireTime;
    }

    public int getMisfireInstruction() {
        return misfireInstruction;
    }

    public void setMisfireInstruction(int misfireInstruction) {
        this.misfireInstruction = misfireInstruction;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public int getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
    }

    public long getRepeatInterval() {
        return repeatInterval;
    }

    public void setRepeatInterval(long repeatInterval) {
        this.repeatInterval = repeatInterval;
    }

    public int getTimesTriggered() {
        return timesTriggered;
    }

    public void setTimesTriggered(int timesTriggered) {
        this.timesTriggered = timesTriggered;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public static TriggerModel from(Trigger trigger) {
        TriggerModel model = new TriggerModel();
        model.setName(trigger.getKey().getName());
        model.setGroup(trigger.getKey().getGroup());
        model.setJobName(trigger.getJobKey().getName());
        model.setJobGroup(trigger.getJobKey().getGroup());
        model.setDescription(trigger.getDescription());
        model.setStartTime(trigger.getStartTime());
        model.setEndTime(trigger.getEndTime());
        model.setFinalFireTime(trigger.getFinalFireTime());
        model.setMayFireAgain(trigger.mayFireAgain());
        model.setNextFireTime(trigger.getNextFireTime());
        model.setPreviousFireTime(trigger.getPreviousFireTime());
        model.setMisfireInstruction(trigger.getMisfireInstruction());
        model.setPriority(trigger.getPriority());
        model.setCalendarName(trigger.getCalendarName());

        if (trigger instanceof CronTrigger) {
            model.setType(Type.Cron);
            model.setCronExpression(((CronTrigger) trigger).getCronExpression());
        } else if (trigger instanceof SimpleTrigger) {
            model.setType(Type.Simple);
            model.setRepeatCount(((SimpleTrigger) trigger).getRepeatCount());
            model.setRepeatInterval(((SimpleTrigger) trigger).getRepeatInterval());
            model.setTimesTriggered(((SimpleTrigger) trigger).getTimesTriggered());
        }

        return model;
    }

    public Trigger toTrigger() {
        switch (type) {
            case Cron:
                return buildCronTrigger(this);
            default:
                return buildSimpleTrigger(this);
        }

    }

    public static Trigger buildCronTrigger(TriggerModel model) {
        CronTriggerImpl trigger = new CronTriggerImpl();
        try {
            trigger.setCronExpression(model.getCronExpression());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        trigger.setStartTime(model.getStartTime());
        trigger.setEndTime(model.getEndTime());
        trigger.setName(model.getName());
        trigger.setGroup(model.getGroup());
        trigger.setJobName(model.getJobName());
        trigger.setJobGroup(model.getJobGroup());
        trigger.setDescription(model.getDescription());
        trigger.setPriority(model.getPriority());

        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.putAll(model.getObjectMap());
        jobDataMap.putAll(model.getBooleanMap());
        trigger.setJobDataMap(jobDataMap);

        return trigger;
    }

    public static Trigger buildSimpleTrigger(TriggerModel model) {
        SimpleTriggerImpl trigger = new SimpleTriggerImpl();
        trigger.setRepeatInterval(model.getRepeatInterval());
        trigger.setRepeatCount(model.getRepeatCount());

        trigger.setStartTime(model.getStartTime());
        trigger.setEndTime(model.getEndTime());
        trigger.setName(model.getName());
        trigger.setGroup(model.getGroup());
        trigger.setJobName(model.getJobName());
        trigger.setJobGroup(model.getJobGroup());
        trigger.setDescription(model.getDescription());
        trigger.setPriority(model.getPriority());

        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.putAll(model.getObjectMap());
        jobDataMap.putAll(model.getBooleanMap());
        trigger.setJobDataMap(jobDataMap);

        return trigger;
    }
}
