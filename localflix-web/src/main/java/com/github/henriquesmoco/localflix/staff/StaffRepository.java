package com.github.henriquesmoco.localflix.staff;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StaffRepository extends JpaRepository<Staff, Integer> {

    @Query("select s from Staff s where trim(s.id) like ?1 or s.firstName like ?1 or s.surname like ?1 " +
            "or s.email like ?1 or trim(s.sex) like ?1")
    Page<Staff> findByAllFieldsContaining(String searchText, Pageable pageable);
}
