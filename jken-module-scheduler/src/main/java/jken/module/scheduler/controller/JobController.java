package jken.module.scheduler.controller;

import jken.module.scheduler.service.SchedulerService;
import jken.support.web.BaseController;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/job")
public class JobController extends BaseController {

    @Autowired
    private SchedulerService SchedulerService;

    /**
     * 显示列表页面
     *
     * @param model
     * @return
     */
    @GetMapping(produces = "text/html")
    public String showList(Model model) {
        return "/job/list";
    }

    /**
     * 获取列表数据
     *
     * @return
     */
    @GetMapping(produces = "application/json")
    @ResponseBody
    public List<JobDetail> list() throws SchedulerException {
        return SchedulerService.findAllJobs();
    }

}
