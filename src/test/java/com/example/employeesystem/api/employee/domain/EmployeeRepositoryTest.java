package com.example.employeesystem.api.employee.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void save_returnsSavedEmployee() {
        EmployeeEntity employeeEntity = EmployeeEntity.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .build();

        EmployeeEntity savedEmployeeEntity = employeeRepository.save(employeeEntity);

        Assertions.assertThat(savedEmployeeEntity).isNotNull();
        Assertions.assertThat(savedEmployeeEntity.getId()).isGreaterThan(0);
    }

    @Test
    public void findAll_returnsAllEmployee() {
        EmployeeEntity employeeEntity = EmployeeEntity.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .build();
        EmployeeEntity employeeEntity2 = EmployeeEntity.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("jane.doe@example.com")
                .build();

        employeeRepository.save(employeeEntity);
        employeeRepository.save(employeeEntity2);

        List<EmployeeEntity> employeeEntityList = employeeRepository.findAll();

        Assertions.assertThat(employeeEntityList).isNotNull();
        Assertions.assertThat(employeeEntityList.size()).isEqualTo(2);
    }

    @Test
    public void findById_returnsEmployee() {
        EmployeeEntity employeeEntity = EmployeeEntity.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .build();

        employeeRepository.save(employeeEntity);
        EmployeeEntity foundEmployeeEntity = employeeRepository.findById(employeeEntity.getId()).orElseThrow();

        Assertions.assertThat(foundEmployeeEntity).isNotNull();
        Assertions.assertThat(foundEmployeeEntity).isEqualTo(employeeEntity);
    }

    @Test
    public void delete_removesEmployee() {
        EmployeeEntity employeeEntity = EmployeeEntity.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .build();

        employeeRepository.save(employeeEntity);
        employeeRepository.delete(employeeEntity);
        Optional<EmployeeEntity> foundEmployeeEntity = employeeRepository.findById(employeeEntity.getId());

        Assertions.assertThat(foundEmployeeEntity).isEmpty();
    }
}
