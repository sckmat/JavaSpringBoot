package com.yegorov.Lab7.controller;

import com.yegorov.Lab7.dao.StudyGroupRepository;
import com.yegorov.Lab7.entity.StudyGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@Controller
public class StudyGroupController {

    private final StudyGroupRepository studyGroupRepository;

    @Autowired
    public StudyGroupController(StudyGroupRepository studyGroupRepository) {
        this.studyGroupRepository = studyGroupRepository;
    }

    @GetMapping("/groups")
    public ModelAndView getAllGroups() {
        ModelAndView mav = new ModelAndView("list-groups");
        mav.addObject("groups", studyGroupRepository.findAll());
        return mav;
    }

    @GetMapping("/addGroupForm")
    public ModelAndView addGroupForm() {
        ModelAndView mav = new ModelAndView("add-group-form");
        mav.addObject("group", new StudyGroup());
        return mav;
    }

    @PostMapping("/saveGroup")
    public RedirectView saveGroup(@ModelAttribute StudyGroup group) {
        studyGroupRepository.save(group);
        return new RedirectView("groups");
    }

    @GetMapping("/showGroupUpdateForm")
    public ModelAndView showUpdateForm(@RequestParam int groupId) {
        ModelAndView mav = new ModelAndView("add-group-form");

        Optional<StudyGroup> optionalGroup = studyGroupRepository.findById(groupId);
        StudyGroup group = optionalGroup.orElse(new StudyGroup());

        mav.addObject("group", group);
        return mav;
    }

    @GetMapping("/deleteGroup")
    public RedirectView deleteGroup(@RequestParam int groupId) {
        studyGroupRepository.deleteById(groupId);
        return new RedirectView("groups");
    }
}
