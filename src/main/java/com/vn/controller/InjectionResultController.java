package com.vn.controller;


import com.vn.model.*;
import com.vn.repository.*;
import org.apache.catalina.User;
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
public class InjectionResultController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    InjectionResultRepository injectionResultRepository;


    @Autowired
    VaccineRepository vaccineRepository;
    @Autowired
    private InjectionScheduleRepository injectionScheduleRepository;

    @GetMapping("/admin/injection-result/create")
    public String showCreateInjectionResult(Model model) {
        List<Users> ListCustomer = userRepository.findAllCustomers();
        List<Vaccine> ListVaccine = vaccineRepository.findAll();
        model.addAttribute("vaccines", ListVaccine);
        model.addAttribute("injectionresult", new InjectionResult());
        model.addAttribute("customers", ListCustomer);
        return "admin/injection-result/create";
    }


    @PostMapping("/admin/injection-result/create")
    public String createInjectionResult(@Validated  @ModelAttribute("injectionresult") InjectionResult injectionresult, BindingResult result,Model model) {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
            model.addAttribute("injectionresult", injectionresult);
            List<Vaccine> ListVaccine = vaccineRepository.findAll();
            model.addAttribute("vaccines", ListVaccine);
            List<Users> ListCustomer = userRepository.findAllCustomers();
            model.addAttribute("customers", ListCustomer);

            if(injectionresult.getInjectionResultId() == null) {
                return "admin/injection-result/create";
            }else{
                return "admin/injection-result/update";
            }

        }

        injectionResultRepository.save(injectionresult);
        return "redirect:/admin/injection-result/list";
    }


    @GetMapping("/admin/injection-result/list")
    public String listInjectionResult( @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "5") int size,
                                @RequestParam(defaultValue = "") String keyword,
                                Model model) {
        Page<InjectionResult> posts = injectionResultRepository.searchInjectionResult(keyword, PageRequest.of(page, size));

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

        return "admin/injection-result/list";
    }


    @PostMapping("/admin/injection-result/delete")
    public String deleteInjectionResult(@RequestParam("injectionResultIds") List<Integer> Idd) {

        injectionResultRepository.deleteAllById(Idd);
        return "redirect:/admin/injection-result/list";
    }

    @GetMapping("/admin/injection-result/update/{id}")
    public String showUpdateInjectioResultForm(@PathVariable Integer id, Model model) {
        List<Vaccine> ListVaccine = vaccineRepository.findAll();
        model.addAttribute("vaccines", ListVaccine);
        List<Users> ListCustomer = userRepository.findAllCustomers();
        model.addAttribute("customers", ListCustomer);
        InjectionResult injectionresult = injectionResultRepository.findById(id).get();
        model.addAttribute("injectionresult", injectionresult);
        return "admin/injection-result/update";
    }

    @GetMapping("/admin/injection-result/update")
    public String showUpdateInjectionResult(Model model) {
        List<Vaccine> ListVaccine = vaccineRepository.findAll();
        model.addAttribute("vaccines", ListVaccine);
        List<Users> ListCustomer = userRepository.findAllCustomers();
        model.addAttribute("customers", ListCustomer);
        model.addAttribute("injectionresult", new InjectionResult());
        return "admin/injection-result/update";
    }

}
