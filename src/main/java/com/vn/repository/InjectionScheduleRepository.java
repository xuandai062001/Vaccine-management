package com.vn.repository;


import com.vn.model.InjectionSchedule;
import com.vn.model.Vaccine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InjectionScheduleRepository extends JpaRepository<InjectionSchedule, Integer> {
    @Query("SELECT isc FROM InjectionSchedule isc WHERE  isc.place LIKE CONCAT('%', :keyword, '%')" +
            "OR isc.vaccineSchedule.vaccineName LIKE CONCAT('%', :keyword, '%')")
    Page<InjectionSchedule> searchInjectionSchedule(@Param("keyword") String keyword, Pageable pageable);
}
