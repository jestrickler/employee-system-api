package com.example.employeesystem.api.employee.business;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
}
