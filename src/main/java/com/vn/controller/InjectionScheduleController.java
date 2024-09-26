package com.vn.controller;


import com.vn.model.*;
import com.vn.repository.InjectionScheduleRepository;
import com.vn.repository.VaccineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class InjectionScheduleController {

    @Autowired
    InjectionScheduleRepository injectionScheduleRepository;

    @Autowired
    VaccineRepository vaccineRepository;



    @GetMapping("/admin/injection-schedule/create")
    public String showCreateInjectionSchedule(Model model) {
        List<Vaccine> ListVaccine = vaccineRepository.findAll();
        model.addAttribute("vaccines", ListVaccine);
        model.addAttribute("injectionschedule", new InjectionSchedule());
        return "admin/injection-schedule/create";
    }


    @PostMapping("/admin/injection-schedule/create")
    public String createInjectionSchedule(@Validated @ModelAttribute("injectionschedule") InjectionSchedule injectionschedule, BindingResult result, Model model) {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
            model.addAttribute("injectionschedule", injectionschedule);
            List<Vaccine> ListVaccine = vaccineRepository.findAll();
            model.addAttribute("vaccines", ListVaccine);
            if(injectionschedule.getInjectionScheduleId() == null) {
                return "admin/injection-schedule/create";
            }else{
                return "admin/injection-schedule/update";
            }

        }

        injectionScheduleRepository.save(injectionschedule);
        return "redirect:/injection-schedule/list";
    }

    @GetMapping("/injection-schedule/list")
    public String listỊnectionSchedule( @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "5") int size,
                                @RequestParam(defaultValue = "") String keyword,
                                Model model) {
        Page<InjectionSchedule> posts = injectionScheduleRepository.searchInjectionSchedule(keyword, PageRequest.of(page, size));

        int totalPages = posts.getTotalPages();

        if (totalPages > 0) {
            int start = Math.max(1, page - 1);
            int end = Math.min(totalPages, page + 3 );

            if (end - start < 4) {
                if (start == 1) {
                    end = Math.min(totalPages, start + 4);
                } else if (end == totalPages) {
                    start = Math.max(1, end - 4);
                }
            }

            List<Integer> pageNumbers = IntStream.rangeClosed(start, end)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        List<Integer> pageNums = new ArrayList<>();
        for (int i = 1; i <= posts.getTotalPages(); i++) {
            pageNums.add(i);
        }
        model.addAttribute("posts", posts);
        model.addAttribute("size", size);
        model.addAttribute("keyword", keyword);

        return "injection-schedule/list";
    }

    @GetMapping("/admin/injection-schedule/update/{id}")
    public String showUpdateInjectioScheduleForm(@PathVariable Integer id, Model model) {
        List<Vaccine> ListVaccine = vaccineRepository.findAll();
        model.addAttribute("vaccines", ListVaccine);
        InjectionSchedule injectionschedule = injectionScheduleRepository.findById(id).get();
        model.addAttribute("injectionschedule", injectionschedule);
        return "admin/injection-schedule/update";
    }

    @GetMapping("/admin/injection-schedule/update")
    public String showUpdateInjectionSchedule(Model model) {
        List<Vaccine> ListVaccine = vaccineRepository.findAll();
        model.addAttribute("vaccines", ListVaccine);
        model.addAttribute("injectionschedule", new InjectionSchedule());
        return "admin/injection-schedule/update";
    }






}
