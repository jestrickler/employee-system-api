package com.example.employeesystem.api.employee.presentation;

import com.example.employeesystem.api.employee.business.Employee;
import com.example.employeesystem.api.employee.business.EmployeeNotFoundException;
import com.example.employeesystem.api.employee.business.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController implements EmployeeApi {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.createEmployee(employee);
    }

    @Override
    public List<Employee> getEmployees() {
        return employeeService.getEmployees();
    }

    @Override
    public ResponseEntity<Employee> getEmployee(Long id) throws EmployeeNotFoundException {
        return ResponseEntity.ok(employeeService.getEmployee(id));
    }

    @Override
    public ResponseEntity<Employee> updateEmployee(Long id, Employee employee) throws EmployeeNotFoundException {
        return ResponseEntity.ok(employeeService.updateEmployee(id, employee));
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable Long id) throws EmployeeNotFoundException {
        employeeService.deleteEmployee(id);
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    protected ResponseEntity<String> handleNotFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
