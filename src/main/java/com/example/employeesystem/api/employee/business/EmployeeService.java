package com.example.employeesystem.api.employee.business;

import java.util.List;

public interface EmployeeService {

    Employee createEmployee(Employee employee);

    List<Employee> getEmployees();

    Employee getEmployee(Long id) throws EmployeeNotFoundException;

    Employee updateEmployee(Long id, Employee employee) throws EmployeeNotFoundException;

    void deleteEmployee(Long id) throws EmployeeNotFoundException;
}
