package jken.module.scheduler.service;

import com.google.common.base.Strings;
import jken.module.scheduler.model.JobModel;
import jken.security.CorpCodeHolder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SchedulerService {

    @Autowired
    private Scheduler scheduler;

    public List<JobDetail> findAllJobs() throws SchedulerException {
        Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.groupEquals(CorpCodeHolder.getCurrentCorpCode()));

        return jobKeys.stream().map(jobKey -> {
            try {
                return scheduler.getJobDetail(jobKey);
            } catch (SchedulerException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }

    public JobDetail findByJobKey(String name, String group) throws SchedulerException {
        return scheduler.getJobDetail(JobKey.jobKey(name, group));
    }

    public void save(JobModel jobModel) throws SchedulerException {
        if (Strings.isNullOrEmpty(jobModel.getGroup())) {
            jobModel.setGroup(CorpCodeHolder.getCurrentCorpCode());
        }
        scheduler.addJob(jobModel.toJobDetail(), true);
    }

    public void delete(JobModel jobModel) throws SchedulerException {
        scheduler.deleteJob(JobKey.jobKey(jobModel.getName(), jobModel.getGroup()));
    }

    public void batchDelete(List<JobModel> jobModels) throws SchedulerException {
        scheduler.deleteJobs(jobModels.stream().map(jobModel -> JobKey.jobKey(jobModel.getName(), jobModel.getGroup())).collect(Collectors.toList()));
    }

    public void exec(JobModel jobModel) throws SchedulerException {
        scheduler.triggerJob(JobKey.jobKey(jobModel.getName(), jobModel.getGroup()));
    }

}
