package br.com.involves.prospectIndicator.controller;

import br.com.involves.prospectIndicator.facade.EmployeeFacade;
import br.com.involves.prospectIndicator.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    private EmployeeFacade employeeFacade;

    @GetMapping("/getEmployees")
    public List<Employee> getEmployees() {
        return employeeFacade.getEmployees();
    }
}
