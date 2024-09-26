package com.vn.controller;


import com.vn.model.Vaccine;
import com.vn.model.VaccineType;
import com.vn.repository.VaccineRepository;
import com.vn.repository.VaccineTypeRepository;
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
public class VaccineController {

    @Autowired
    VaccineRepository vaccineRepository;

    @Autowired
    VaccineTypeRepository vaccineTypeRepository;

    @GetMapping("/admin/vaccine/create")
    public String showCreateVaccine(Model model) {
        List<VaccineType> ListVaccineType = vaccineTypeRepository.findAll();
        model.addAttribute("vaccinetypes", ListVaccineType);
        model.addAttribute("vaccine", new Vaccine());
        return "admin/vaccine/create";
    }


    @PostMapping("/admin/vaccine/create")
    public String createVaccine(@Validated @ModelAttribute("vaccine") Vaccine vaccine, BindingResult result, Model model) {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
            model.addAttribute("vaccine", vaccine);
            List<VaccineType> ListVaccineType = vaccineTypeRepository.findAll();
            model.addAttribute("vaccinetypes", ListVaccineType);


            Vaccine oldVaccine = vaccineRepository.findById(vaccine.getVaccineId()).orElse(null);
            if(oldVaccine == null) {
                return "admin/vaccine/create";
            }else{
                return "admin/vaccine/update";
            }

        }

        vaccineRepository.save(vaccine);
        return "redirect:/admin/vaccine/list";
    }


    @GetMapping("/admin/vaccine/list")
    public String listVaccine( @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "5") int size,
                                   @RequestParam(defaultValue = "") String keyword,
                                   Model model) {
        Page<Vaccine> posts = vaccineRepository.searchVaccine(keyword, PageRequest.of(page, size));

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

        return "admin/vaccine/list";
    }

    @GetMapping("/admin/vaccine/update/{id}")
    public String showUpdateVaccineForm(@PathVariable String id, Model model) {
        List<VaccineType> vaccineTypes = vaccineTypeRepository.findAll();
        model.addAttribute("vaccinetypes", vaccineTypes);
        Vaccine vaccine = vaccineRepository.findById(id).get();
        model.addAttribute("vaccine", vaccine);
        return "admin/vaccine/update";
    }


    @GetMapping("/admin/vaccine/update")
    public String showUpdateVaccine(Model model) {
        List<VaccineType> vaccineTypes = vaccineTypeRepository.findAll();
        model.addAttribute("vaccinetypes", vaccineTypes);
        model.addAttribute("vaccine", new Vaccine());
        return "admin/vaccine/update";
    }

    @PostMapping("/admin/vaccine/inactive")
    public String makeInActiveVaccineType(@RequestParam("selectVaccine") List<String> selectedIds) {
        if (selectedIds != null) {
            for (String id : selectedIds) {
                Vaccine vaccine = vaccineRepository.findById(id).get();
                if (vaccine != null) {
                    vaccine.setStatus(false);
                    vaccineRepository.save(vaccine);
                }
            }
        }
        return "redirect:/admin/vaccine/list";
    }


}
