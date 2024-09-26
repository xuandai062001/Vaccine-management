package com.vn.repository;


import com.vn.model.Vaccine;
import com.vn.model.VaccineType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VaccineRepository extends JpaRepository<Vaccine, String > {
    @Query("SELECT v FROM Vaccine v WHERE  v.vaccineName LIKE CONCAT('%', :keyword, '%')")
    Page<Vaccine> searchVaccine(@Param("keyword") String keyword, Pageable pageable);
}
