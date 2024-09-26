package com.vn.controller;


import com.vn.model.InjectionResult;
import com.vn.model.Users;
import com.vn.model.Vaccine;
import com.vn.model.VaccineType;
import com.vn.repository.InjectionResultRepository;
import com.vn.repository.VaccineRepository;
import com.vn.repository.VaccineTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class ReportVaccineController {

    @Autowired
    InjectionResultRepository injectionResultRepository;

    @Autowired
    VaccineTypeRepository vaccineTypeRepository;

    @GetMapping("/admin/report/vaccine")
    public String listCustomer( @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "5") int size,
                            @RequestParam (defaultValue = "") String vaccineTypeId,
                           @RequestParam(defaultValue ="") LocalDate beginDate,
                           @RequestParam(defaultValue ="") LocalDate endDate,
                           @RequestParam(defaultValue ="") String origin,
                            Model model) {
        model.addAttribute("vaccineTypeId", vaccineTypeId);

        if(origin.trim().equals("")){
            origin = null;
        }

        if(vaccineTypeId.trim().equals("")){
            vaccineTypeId = null;
        }

        Page<Object[]> posts = injectionResultRepository.sumVaccineFilter(
                vaccineTypeId,
                beginDate,
                endDate,
                origin,
                PageRequest.of(page, size)
        );

        int totalPages = posts.getTotalPages();

        if (totalPages > 0) {
            int start = Math.max(1, page - 1);
            int end = Math.min(totalPages, page + 3);

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


        List<VaccineType> vaccineTypes = vaccineTypeRepository.findAll();
        model.addAttribute("vaccinetypes", vaccineTypes);

        return "admin/report/vaccine";
    }
}
