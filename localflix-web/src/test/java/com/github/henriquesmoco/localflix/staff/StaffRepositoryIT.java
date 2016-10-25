package com.github.henriquesmoco.localflix.staff;


import com.github.henriquesmoco.localflix.config.RepositoryIT;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


public class StaffRepositoryIT extends RepositoryIT {

    @Autowired
    private StaffRepository staffRepo;


    @Test
    public void save_StaffWithUpdatedBirthDate_SaveStaffNormally() throws Exception {
        Staff staff = staffRepo.findOne(1);
        LocalDate updatedStaffBirth = LocalDate.now();

        staff.setBirthDate(updatedStaffBirth);
        staffRepo.saveAndFlush(staff);

        Date result = jdbcTemplate.queryForObject("SELECT birth_date FROM Staff WHERE id = 1", Date.class);
        assertThat(result.toLocalDate(), equalTo(updatedStaffBirth));
    }
}
