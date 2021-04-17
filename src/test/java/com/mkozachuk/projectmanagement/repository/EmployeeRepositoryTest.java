package com.mkozachuk.projectmanagement.repository;

import com.mkozachuk.projectmanagement.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testSaveEmployee() {

        Employee employee = employeeRepository.save(new Employee("John", "Doe", "jonh.doe@company.com", "12345678901"));

        assertThat(employee).isNotNull();
        assertThat(employee.getEmployeeId()).isGreaterThan(0);
    }

}