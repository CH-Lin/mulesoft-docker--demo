package com.employee.web.api;

import com.employee.app.service.EmployeeService;
import com.employee.common.EmployeeExistException;
import com.employee.common.EmployeeNotFoundException;
import com.employee.common.reply.EmployeeInfoResponse;
import com.employee.common.reply.ReimbursementInfoResponse;
import com.employee.common.request.AddReimbursementRequest;
import com.employee.domain.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.Collection;
import java.util.List;

@RestController
public class EmployeeSystem {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping(value = "/add/employee")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addEmployee(@RequestBody final Employee employee, final @Context HttpServletResponse response) {
        if (employee.getSalary() == null || employee.getSalary() < 0 ||
                employee.getEmployeeName() == null || employee.getEmployeeName().isBlank() ||
                employee.getEmployeeEmail() == null || employee.getEmployeeEmail().isBlank()) {
            response.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return Response.status(Response.Status.BAD_REQUEST).entity(new String("Parameters incorrect!")).build();
        }
        try {
            employeeService.addEmployee(employee);
        } catch (EmployeeExistException e) {
            response.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return Response.status(Response.Status.BAD_REQUEST).entity(new String("Employee already exist!")).build();
        }
        return Response.status(Response.Status.OK).entity(new String("OK")).build();
    }

    @GetMapping(value = "/get/salary")
    @Produces(MediaType.APPLICATION_JSON)
    public Long getSalary(final @QueryParam("name") String name, final @QueryParam("email") String email,
                          final @Context HttpServletResponse response) {
        if ((name == null && email != null) || name != null && email == null) {
            response.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return null;
        }
        try {
            return employeeService.getEmployee(name, email).getSalary();
        } catch (EmployeeNotFoundException e) {
            e.printStackTrace();
            response.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
        }
        return null;
    }

    @GetMapping(value = "/get/salary/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<EmployeeInfoResponse> getSalaries(final @Context HttpServletResponse response) {
        return employeeService.getAllEmployee();
    }

    @PostMapping(value = "/add/reimbursement")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addReimbursement(@RequestBody final AddReimbursementRequest req, @Context final HttpServletResponse response) {
        if (req.getEmployeeName() == null || req.getEmployeeName().isBlank() || req.getEmployeeEmail() == null ||
                req.getEmployeeEmail().isBlank() || req.getItems() == null || req.getItems().size() == 0) {
            response.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return Response.status(Response.Status.BAD_REQUEST).entity(new String("Parameters incorrect!")).build();
        }
        try {
            employeeService.addReimbursement(req.getEmployeeName(), req.getEmployeeEmail(), req.getItems());
        } catch (EmployeeNotFoundException e) {
            response.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return Response.status(Response.Status.BAD_REQUEST).entity(new String("Employee name not found!")).build();
        }
        return Response.status(Response.Status.OK).entity(new String("OK")).build();
    }

    @GetMapping(value = "/get/reimbursement")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<ReimbursementInfoResponse> /*Response*/ getReimbursement(final @QueryParam("name") String name,
                                                                               final @QueryParam("email") String email,
                                                                               final @QueryParam("year") Integer year,
                                                                               final @QueryParam("month") Integer month,
                                                                               final @Context HttpServletResponse response) {
        if ((name == null && email != null) || name != null && email == null) {
            response.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return null;
        }
        List<ReimbursementInfoResponse> result = employeeService.getReimbursement(name, email, year, month == null ? -1 : month);
        return result;
        /* return Response.status(Response.Status.OK).entity(result).build(); */
    }

}
