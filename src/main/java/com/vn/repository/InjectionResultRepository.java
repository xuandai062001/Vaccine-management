package com.vn.repository;

import com.vn.model.InjectionResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;


@Repository
public interface InjectionResultRepository extends JpaRepository<InjectionResult, Integer> {

    @Query("SELECT u.userId, u.fullName, u.dateOfBirth, u.address, u.identityCard, SUM(ir.numberOfInjection) " +
            "FROM InjectionResult ir JOIN ir.user u WHERE u.role = 'Customer' " +
            "AND (u.fullName LIKE %:fullName% OR :fullName IS NULL) " +
            "AND (u.address LIKE %:address% OR :address IS NULL) " +
            "AND (:fromDate IS NULL OR u.dateOfBirth >= :fromDate) " +
            "AND (:toDate IS NULL OR u.dateOfBirth <= :toDate) " +
            "GROUP BY u.userId, u.fullName, u.dateOfBirth, u.address, u.identityCard")
    Page<Object[]> countInjectionsForEachCustomer(@Param("fullName") String fullName,
                                                  @Param("address") String address,
                                                  @Param("fromDate") LocalDate fromDate,
                                                  @Param("toDate") LocalDate toDate,
                                                  Pageable pageable);




    @Query("SELECT vt.vaccineTypeName, ir.prevention, u.fullName, ir.injectionDate, SUM(ir.numberOfInjection) " +
            "FROM InjectionResult ir JOIN ir.vaccinePKInjection v JOIN v.vaccineVaccineType vt " +
            "JOIN ir.user u " +
            "WHERE u.role = 'Customer' " +
            "AND (:vaccineTypeId IS NULL OR vt.vaccineTypeId = :vaccineTypeId) " +
            "AND (:fromDate IS NULL OR ir.injectionDate >= :fromDate) " +
            "AND (:toDate IS NULL OR ir.injectionDate <= :toDate) " +
            "AND (:prevention IS NULL OR ir.prevention LIKE CONCAT('%', :prevention, '%')) " +
            "GROUP BY vt.vaccineTypeName, ir.prevention, u.fullName, ir.injectionDate " +
            "ORDER BY ir.injectionDate")
    Page<Object[]> sumInjectionResultFilter(
                                            @Param("prevention") String prevention,
                                            @Param("fromDate") LocalDate fromDate,
                                            @Param("toDate") LocalDate toDate,
                                            @Param("vaccineTypeId") String vaccineTypeId,
                                            Pageable pageable);


    @Query("SELECT v.vaccineName, vt.vaccineTypeName, SUM(ir.numberOfInjection), v.timeBeginNextInjection, v.timeEndNextInjection, v.origin " +
            "FROM InjectionResult ir JOIN ir.vaccinePKInjection v JOIN v.vaccineVaccineType vt " +
            "WHERE (:vaccineTypeId IS NULL OR vt.vaccineTypeId = :vaccineTypeId) " +
            "AND (:beginDate IS NULL OR v.timeBeginNextInjection >= :beginDate) " +
            "AND (:endDate IS NULL OR v.timeEndNextInjection <= :endDate) " +
            "AND (:origin IS NULL OR v.origin LIKE CONCAT('%', :origin, '%')) " +
            "GROUP BY v.vaccineName, vt.vaccineTypeName, v.timeBeginNextInjection, v.timeEndNextInjection, v.origin " +
            "ORDER BY v.vaccineName")
    Page<Object[]> sumVaccineFilter(
            @Param("vaccineTypeId") String vaccineTypeId,
            @Param("beginDate") LocalDate beginDate,
            @Param("endDate") LocalDate endDate,
            @Param("origin") String origin,
            Pageable pageable);

    @Query("SELECT ir FROM InjectionResult ir WHERE  ir.vaccinePKInjection.vaccineName LIKE CONCAT('%', :keyword, '%') " +
            "OR ir.prevention LIKE CONCAT('%', :keyword, '%') ")
    Page<InjectionResult> searchInjectionResult(@Param("keyword") String keyword, Pageable pageable);


}
