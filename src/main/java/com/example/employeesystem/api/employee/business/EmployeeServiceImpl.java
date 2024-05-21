package com.example.employeesystem.api.employee.business;

import com.example.employeesystem.api.employee.data.EmployeeEntity;
import com.example.employeesystem.api.employee.data.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Employee createEmployee(Employee employee) {
        EmployeeEntity employeeEntity = modelMapper.map(employee, EmployeeEntity.class);
        employeeEntity = employeeRepository.save(employeeEntity);

        employee.setId(employeeEntity.getId());
        return employee;
    }

    @Override
    public List<Employee> getEmployees() {
        List<EmployeeEntity> employeeEntityEntities = employeeRepository.findAll();
        return employeeEntityEntities
                .stream()
                .map(e -> new Employee(
                        e.getId(),
                        e.getFirstName(),
                        e.getLastName(),
                        e.getEmail()
                )).collect(Collectors.toList());
    }

    @Override
    public Employee getEmployee(Long id) throws EmployeeNotFoundException {
        EmployeeEntity employeeEntity = employeeRepository.findById(id).orElseThrow(EmployeeNotFoundException::new);
        return modelMapper.map(employeeEntity, Employee.class);
    }

    @Override
    public Employee updateEmployee(Long id, Employee employee) throws EmployeeNotFoundException {
        EmployeeEntity employeeEntity = employeeRepository.findById(id).orElseThrow(EmployeeNotFoundException::new);

        employeeEntity.setFirstName(employee.getFirstName());
        employeeEntity.setLastName(employee.getLastName());
        employeeEntity.setEmail(employee.getEmail());
        employeeRepository.save(employeeEntity);

        return modelMapper.map(employeeEntity, Employee.class);
    }

    @Override
    public void deleteEmployee(Long id) throws EmployeeNotFoundException {
        EmployeeEntity employeeEntity = employeeRepository.findById(id).orElseThrow(EmployeeNotFoundException::new);
        employeeRepository.delete(employeeEntity);
    }
}
