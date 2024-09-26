package com.vn.controller;


import com.vn.model.VaccineType;
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
public class VaccineTypeController {

    @Autowired
    VaccineTypeRepository vaccineTypeRepository;

    @GetMapping("/admin/vaccine-type/create")
    public String showCreateVaccineType(Model model) {
        model.addAttribute("vaccinetype", new VaccineType());
        return "admin/vaccine-type/create";
    }


    @PostMapping("/admin/vaccine-type/delete")
    public String deleteVaccineType(@RequestParam("Ids") List<String> Idd) {

        vaccineTypeRepository.deleteAllById(Idd);
        return "redirect:/admin/vaccine-type/list";
    }


    @PostMapping("/admin/vaccine-type/create")
    public String createVaccineType(@Validated @ModelAttribute("vaccinetype") VaccineType vaccinetype, BindingResult result, Model model) {


        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
            model.addAttribute("vaccinetype", vaccinetype);
            return "admin/vaccine-type/create";
        }

        vaccineTypeRepository.save(vaccinetype);
        return "redirect:/admin/vaccine-type/list";
    }


    @GetMapping("/admin/vaccine-type/list")
    public String listVaccineType( @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "5") int size,
                                @RequestParam(defaultValue = "") String keyword,
                                Model model) {
        Page<VaccineType> posts = vaccineTypeRepository.searchVaccineType(keyword, PageRequest.of(page, size));

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

        return "admin/vaccine-type/list";
    }

    @PostMapping("/admin/vaccine-type/inactive")
    public String makeInActiveVaccineType(@RequestParam("selectVaccineType") List<String> selectedIds) {
        if (selectedIds != null) {
            for (String id : selectedIds) {
                VaccineType vaccineType = vaccineTypeRepository.findById(id).get();
                if (vaccineType != null) {
                    vaccineType.setActive(false);
                    vaccineTypeRepository.save(vaccineType);
                }
            }
        }
        return "redirect:/admin/vaccine-type/list";
    }

}
