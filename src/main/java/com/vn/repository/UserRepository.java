package com.vn.repository;

import com.vn.model.Role;
import com.vn.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

    @Query("SELECT u FROM Users u WHERE u.role = 'Customer' AND u.fullName LIKE CONCAT('%', :keyword, '%')")
    Page<Users> searchCustomer(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT u FROM Users u WHERE u.role = 'Employee' AND u.fullName LIKE CONCAT('%', :keyword, '%')")
    Page<Users> searchEmployee(@Param("keyword") String keyword, Pageable pageable);

    Users findByUserIdAndRole(Integer id, Role role);

    @Query("SELECT u FROM Users u WHERE u.role = 'Customer'")
    List<Users> findAllCustomers();

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    boolean existsByIdentityCard(String identityCard);

    Users findByUsername(String username);


//    @Query("SELECT u FROM Users u WHERE u.role = 'Customer' AND u.fullName LIKE %:fullName% AND u.address LIKE %:address% AND u.dateOfBirth BETWEEN :fromDate AND :toDate")
//    List<Users> searchCustomerReport(
//            @Param("fromDate") Date fromDate,
//            @Param("toDate") Date toDate,
//            @Param("fullName") String fullName,
//            @Param("address") String address);
}
