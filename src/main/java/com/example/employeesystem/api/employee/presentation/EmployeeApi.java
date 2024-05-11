package com.example.employeesystem.api.employee.presentation;

import com.example.employeesystem.api.employee.business.Employee;
import com.example.employeesystem.api.employee.business.EmployeeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/employees")
public interface EmployeeApi {

    @PostMapping()
    Employee createEmployee(@RequestBody Employee employee);

    @GetMapping()
    List<Employee> getEmployees();

    @GetMapping("/{id}")
    ResponseEntity<Employee> getEmployee(@PathVariable Long id) throws EmployeeNotFoundException;

    @PutMapping("/{id}")
    ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) throws EmployeeNotFoundException;

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteEmployee(@PathVariable Long id) throws EmployeeNotFoundException;
}
