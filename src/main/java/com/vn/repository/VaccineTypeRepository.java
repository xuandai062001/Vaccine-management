package com.vn.repository;


import com.vn.model.VaccineType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VaccineTypeRepository extends JpaRepository<VaccineType, String> {
    @Query("SELECT vt FROM VaccineType vt WHERE  vt.vaccineTypeName LIKE CONCAT('%', :keyword, '%')")
    Page<VaccineType> searchVaccineType(@Param("keyword") String keyword, Pageable pageable);
}
