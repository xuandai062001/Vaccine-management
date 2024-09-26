package com.vn.controller;
import com.vn.repository.InjectionResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Controller
public class ReportCustomerController {


    @Autowired
    private InjectionResultRepository injectionResultRepository;

    @GetMapping("/admin/report/customer")
    public String listCustomer( @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "5") int size,
                                @RequestParam(defaultValue = "") String fullName,
                               @RequestParam(defaultValue ="") String address,
                                @RequestParam(defaultValue = "") LocalDate fromDate,
                               @RequestParam(defaultValue = "") LocalDate toDate,
                                Model model) {

        if(fullName.trim().equals("")){
            fullName = null;
        }

        if(address.trim().equals("")){
            address = null;
        }

        Page<Object[]> posts = injectionResultRepository.countInjectionsForEachCustomer(
                fullName,
                address,
                fromDate,
                toDate,
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

        return "admin/report/customer";
    }


}


