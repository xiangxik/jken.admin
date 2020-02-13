package jken.module.scheduler.service;

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

}