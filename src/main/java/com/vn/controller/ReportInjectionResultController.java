package com.vn.controller;

import com.vn.model.VaccineType;
import com.vn.repository.InjectionResultRepository;
import com.vn.repository.VaccineTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class ReportInjectionResultController {

@Autowired
  private  InjectionResultRepository injectionResultRepository;

@Autowired
private VaccineTypeRepository vaccineTypeRepository;

    @GetMapping("/admin/report/injection-result")
    public String listCustomer( @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "5") int size,
                                @RequestParam(defaultValue = "") String prevention,
                                @RequestParam(defaultValue = "") LocalDate fromDate,
                                @RequestParam(defaultValue = "") LocalDate toDate,
                                @RequestParam(defaultValue = "") String vaccineTypeId,
                                Model model) {

        model.addAttribute("vaccineTypeId", vaccineTypeId);

        if(prevention.trim().equals("")){
            prevention = null;
        }

        if(vaccineTypeId.trim().equals("")){
            vaccineTypeId = null;
        }

        Page<Object[]> posts = injectionResultRepository.sumInjectionResultFilter(
            prevention,
            fromDate,
            toDate,
            vaccineTypeId,
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

        return "admin/report/injection-result";
    }
}
