package com.example.employeesystem.api.employee.business;

import com.example.employeesystem.api.employee.domain.EmployeeEntity;
import com.example.employeesystem.api.employee.domain.EmployeeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    public void createEmployee_returnsEmployee() {
        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .build();
        EmployeeEntity employeeEntity = EmployeeEntity.builder()
                .id(10L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .build();
        Mockito.when(modelMapper.map(employee, EmployeeEntity.class)).thenReturn(employeeEntity);
        Mockito.when(employeeRepository.save(employeeEntity)).thenReturn(employeeEntity);

        Employee savedEmployee = employeeService.createEmployee(employee);

        Assertions.assertThat(savedEmployee).isNotNull();
        Assertions.assertThat(savedEmployee.getId()).isEqualTo(10L);
    }

    @Test
    public void getEmployees_returnsAllEmployee() {
        List<EmployeeEntity> employeeEntityList = List.of(
                EmployeeEntity.builder()
                        .firstName("John")
                        .lastName("Doe")
                        .email("john.doe@example.com")
                        .build(),
                EmployeeEntity.builder()
                        .firstName("Jane")
                        .lastName("Doe")
                        .email("jane.doe@example.com")
                        .build()
        );
        Mockito.when(employeeRepository.findAll()).thenReturn(employeeEntityList);

        List<Employee> employeeList = employeeService.getEmployees();

        Assertions.assertThat(employeeList).isNotNull();
        Assertions.assertThat(employeeList.size()).isEqualTo(2);
    }

    @Test
    public void getEmployee_returnsEmployee() throws EmployeeNotFoundException {
        EmployeeEntity employeeEntity = EmployeeEntity.builder()
                .id(20L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .build();
        Mockito.when(employeeRepository.findById(20L)).thenReturn(Optional.of(employeeEntity));
        Employee employee = Employee.builder()
                .id(20L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .build();
        Mockito.when(modelMapper.map(employeeEntity, Employee.class)).thenReturn(employee);

        Employee foundEmployee = employeeService.getEmployee(20L);

        Assertions.assertThat(foundEmployee).isEqualTo(employee);
    }

    @Test
    public void getEmployee_throwsEmployeeNotFound() {
        Mockito.when(employeeRepository.findById(25L)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> {
            employeeService.getEmployee(25L);
        }).isInstanceOf(EmployeeNotFoundException.class);
    }

    @Test
    public void updateEmployee_returnsEmployee() throws EmployeeNotFoundException {
        EmployeeEntity employeeEntity = EmployeeEntity.builder()
                .id(30L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .build();
        Mockito.when(employeeRepository.findById(30L)).thenReturn(Optional.of(employeeEntity));
        Mockito.when(employeeRepository.save(employeeEntity)).thenReturn(employeeEntity);
        Employee employee = Employee.builder()
                .id(30L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .build();
        Mockito.when(modelMapper.map(employeeEntity, Employee.class)).thenReturn(employee);

        Employee updatedEmployee = employeeService.updateEmployee(employee.getId(), employee);

        Assertions.assertThat(updatedEmployee).isEqualTo(employee);
    }

    @Test
    public void updateEmployee_throwsEmployeeNotFound() {
        Mockito.when(employeeRepository.findById(35L)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> {
            employeeService.updateEmployee(35L, Employee.builder().build());
        }).isInstanceOf(EmployeeNotFoundException.class);
    }

    @Test
    public void deleteEmployee_removesEmployee() throws EmployeeNotFoundException {
        EmployeeEntity employeeEntity = EmployeeEntity.builder()
                .id(40L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .build();
        Mockito.when(employeeRepository.findById(40L)).thenReturn(Optional.of(employeeEntity));

        employeeService.deleteEmployee(employeeEntity.getId());

        Mockito.verify(employeeRepository).delete(employeeEntity);
    }

    @Test
    public void deleteEmployee_throwsEmployeeNotFound() {
        Mockito.when(employeeRepository.findById(45L)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> {
            employeeService.deleteEmployee(45L);
        }).isInstanceOf(EmployeeNotFoundException.class);
    }
}
