package jken.module.scheduler.controller;

import jken.module.scheduler.model.JobModel;
import jken.module.scheduler.service.SchedulerService;
import jken.support.web.BaseController;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/job")
public class JobController extends BaseController {

    @Autowired
    private SchedulerService schedulerService;

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
    public List<JobModel> list() throws SchedulerException {
        return schedulerService.findAllJobs().stream().map(JobModel::from).collect(Collectors.toList());
    }

    /**
     * 显示添加页面
     *
     * @param entity
     * @param model
     * @return
     */
    @GetMapping(value = "/add", produces = "text/html")
    public String showDetailAdd(JobModel entity, Model model) {
        if (entity == null) {
            entity = new JobModel();
        }
        return showDetailEdit(entity, model);
    }

    /**
     * 显示编辑页面
     *
     * @param entity
     * @param model
     * @return
     * @throws SchedulerException
     */
    @GetMapping(value = "/{id}", produces = "text/html")
    public String showDetailEdit(@PathVariable("id") JobModel entity, Model model) {
        model.addAttribute("entity", entity);
        return "/job/edit";
    }

    /**
     * 添加实体
     *
     * @param entity
     * @param bindingResult
     */
    @PostMapping
    @ResponseBody
    public void create(@ModelAttribute @Valid JobModel entity, BindingResult bindingResult) throws SchedulerException {
        update(entity, bindingResult);
    }

    /**
     * 更新实体
     *
     * @param entity
     * @param bindingResult
     */
    @PutMapping("/{id}")
    @ResponseBody
    public void update(@ModelAttribute("id") @Valid JobModel entity, BindingResult bindingResult) throws SchedulerException {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException("validate error");
        }

        schedulerService.save(entity);
    }

}
