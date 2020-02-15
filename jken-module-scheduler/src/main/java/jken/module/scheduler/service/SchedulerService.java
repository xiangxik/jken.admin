package jken.module.scheduler.service;

import com.google.common.base.Strings;
import jken.module.scheduler.model.JobModel;
import jken.security.CorpCodeHolder;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SchedulerService {

    @Autowired
    private Scheduler scheduler;

    public List<JobModel> findAllJobs() throws SchedulerException {
        Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.groupEquals(CorpCodeHolder.getCurrentCorpCode()));
        List<JobDetail> executingJobs = scheduler.getCurrentlyExecutingJobs().stream().map(JobExecutionContext::getJobDetail).collect(Collectors.toList());

        return jobKeys.stream().map(jobKey -> {
            try {
                JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                JobModel jobModel = JobModel.from(jobDetail);
                jobModel.setExecuting(executingJobs.contains(jobDetail));
                return jobModel;
            } catch (SchedulerException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }

    public JobModel findByJobKey(String name) throws SchedulerException {
        JobDetail jobDetail = scheduler.getJobDetail(JobKey.jobKey(name, CorpCodeHolder.getCurrentCorpCode()));
        JobModel jobModel = JobModel.from(jobDetail);
        return jobModel;
    }

    public boolean existsJob(String name) throws SchedulerException {
        return scheduler.checkExists(JobKey.jobKey(name, CorpCodeHolder.getCurrentCorpCode()));
    }

    public void saveJob(JobModel jobModel) throws SchedulerException {
        String corpCode = CorpCodeHolder.getCurrentCorpCode();
        if (Strings.isNullOrEmpty(jobModel.getGroup())) {
            jobModel.setGroup(corpCode);
        }
        if (!Objects.equals(jobModel.getGroup(), corpCode)) {
            throw new RuntimeException("could not change group");
        }
        scheduler.addJob(jobModel.toJobDetail(), true);
    }

    public void deleteJob(JobModel jobModel) throws SchedulerException {
        scheduler.deleteJob(JobKey.jobKey(jobModel.getName(), jobModel.getGroup()));
    }

    public void batchDeleteJobs(List<JobModel> jobModels) throws SchedulerException {
        scheduler.deleteJobs(jobModels.stream().map(jobModel -> JobKey.jobKey(jobModel.getName(), jobModel.getGroup())).collect(Collectors.toList()));
    }

    public void execJob(JobModel jobModel) throws SchedulerException {
        scheduler.triggerJob(JobKey.jobKey(jobModel.getName(), jobModel.getGroup()));
    }

}
