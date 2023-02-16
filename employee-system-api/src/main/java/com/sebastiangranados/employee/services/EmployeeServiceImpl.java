package com.sebastiangranados.employee.services;

import com.sebastiangranados.employee.entities.EmployeeEntity;
import com.sebastiangranados.employee.models.Employee;
import com.sebastiangranados.employee.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee createEmployee(Employee employee) {
        EmployeeEntity employeeEntity = new EmployeeEntity();

        BeanUtils.copyProperties(employee, employeeEntity);
        employeeRepository.save(employeeEntity);
        return null;
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<EmployeeEntity> employeeEntities
                = employeeRepository.findAll();

        return employeeEntities
                .stream()
                .map(emp -> new Employee(
                        emp.getId(),
                        emp.getFirstName(),
                        emp.getLastName(),
                        emp.getEmailId()))
                .toList();
    }

    @Override
    public boolean deleteEmployee(Long id) {
        EmployeeEntity employee = employeeRepository.findById(id).get();
        employeeRepository.delete(employee);
        return true;
    }

    @Override
    public Employee getEmployeeById(Long id) {
        EmployeeEntity employeeEntity
                = employeeRepository.findById(id).get();
        return new Employee(
                employeeEntity.getId(),
                employeeEntity.getFirstName(),
                employeeEntity.getLastName(),
                employeeEntity.getEmailId()
        );
    }

    @Override
    public Employee updateEmployee(Long id, Employee employee) {
        EmployeeEntity employeeEntity = employeeRepository.findById(id).get();
        employeeEntity.setEmailId(employee.emailId());
        employeeEntity.setFirstName(employee.firstName());
        employeeEntity.setLastName(employee.lastName());

        employeeRepository.save(employeeEntity);
        return employee;
    }
}
