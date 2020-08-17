package com.employee.controllers;

import com.employee.exceptions.ResourceNotFoundException;
import com.employee.models.Employee;
import com.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    //get all employees

    @GetMapping("employees")
    public List<Employee> getAllEmployees(){
        return this.employeeRepository.findAll();
    }

    //get employee by id

    @GetMapping("employees/{id}")
    public Employee getEmployeeById(@PathVariable (value = "id") long employeeId){
        return this.employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with Id " + employeeId));
    }

    //create new employee

    @PostMapping("employees")
    public Employee createEmployee(@RequestBody Employee employee){
        return this.employeeRepository.save(employee);
    }

    //update employee

    @PutMapping("employees/{id}")
    public Employee updateEmployee(@RequestBody Employee emp, @PathVariable(value = "id") long employeeId){
        Employee employeeExist = this.employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with Id " + employeeId));

        employeeExist.setFirstName(emp.getFirstName());
        employeeExist.setLastName(emp.getLastName());
        employeeExist.setEmail(emp.getEmail());

        return this.employeeRepository.save(employeeExist);
    }

    //delete employee

    public ResponseEntity<Employee> deleteEmployee(@PathVariable(value = "id") long employeeId){
        Employee employee = this.employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + employeeId));

        this.employeeRepository.delete(employee);

        return ResponseEntity.ok().build();
    }
}
