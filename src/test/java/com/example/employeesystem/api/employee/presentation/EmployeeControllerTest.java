package com.example.employeesystem.api.employee.presentation;

import com.example.employeesystem.api.employee.business.Employee;
import com.example.employeesystem.api.employee.business.EmployeeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @Test
    public void createEmployee_returnsCreatedEmployee() {
        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .build();
        Mockito.when(employeeService.createEmployee(employee)).thenReturn(employee);

        Employee savedEmployee = employeeController.createEmployee(employee);

        Assertions.assertThat(savedEmployee).isEqualTo(employee);
    }

    @Test
    public void getEmployees_returnsAllEmployee() {
        List<Employee> employeeList = List.of(
                Employee.builder()
                        .id(10L)
                        .firstName("John")
                        .lastName("Doe")
                        .email("john.doe@example.com")
                        .build(),
                Employee.builder()
                        .id(11L)
                        .firstName("Jane")
                        .lastName("Doe")
                        .email("jane.doe@example.com")
                        .build()
        );
        Mockito.when(employeeService.getEmployees()).thenReturn(employeeList);

        List<Employee> foundEmployeeList = employeeController.getEmployees();

        Assertions.assertThat(foundEmployeeList).isNotNull();
        Assertions.assertThat(foundEmployeeList.size()).isEqualTo(2);
    }

}
