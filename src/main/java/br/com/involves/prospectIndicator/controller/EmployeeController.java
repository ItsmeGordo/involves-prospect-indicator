package br.com.involves.prospectIndicator.controller;

import br.com.involves.prospectIndicator.model.Employee;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @RequestMapping("/getEmployees")
    public List<Employee> getEmployees() {
        return null;
    }
}
