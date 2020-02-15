package jken.module.scheduler.model;

import jken.module.scheduler.support.BooleanMap;
import org.quartz.JobKey;
import org.quartz.Trigger;
import org.quartz.TriggerKey;

import javax.validation.constraints.NotNull;
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

    private int priority;

    public boolean mayFireAgain;

    public Date startTime;
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public static TriggerModel from(Trigger trigger) {
        return null;
    }

    public Trigger toTrigger() {
        return null;
    }
}
