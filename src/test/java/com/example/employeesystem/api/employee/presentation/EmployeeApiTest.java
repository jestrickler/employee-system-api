package com.example.employeesystem.api.employee.presentation;

import com.example.employeesystem.api.employee.business.Employee;
import com.example.employeesystem.api.employee.business.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeApi.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class EmployeeApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void post_returnsCreatedEmployee() throws Exception {
        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .build();
        Mockito.when(employeeService.createEmployee(employee)).thenReturn(employee);

        ResultActions response = mockMvc.perform(post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(employee)));
    }

    @Test
    public void get_returnsAllEmployee() throws Exception {
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

        ResultActions response = mockMvc.perform(get("/api/v1/employees"));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(employeeList)));
    }

    @Test
    public void getById_returnsEmployee() throws Exception {
        Employee employee = Employee.builder()
                .id(20L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .build();
        Mockito.when(employeeService.getEmployee(20L)).thenReturn(employee);

        ResultActions response = mockMvc.perform(get("/api/v1/employees/{id}", 20L));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(employee)));
    }

}
