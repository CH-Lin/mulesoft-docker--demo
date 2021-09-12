package com.financial.web.api;

import com.financial.app.service.BudgetService;
import com.financial.common.exception.BudgetExistException;
import com.financial.common.exception.BudgetNotEnoughException;
import com.financial.common.exception.BudgetNotFoundException;
import com.financial.common.request.AddBudgetRequest;
import com.financial.common.request.PaymentRequest;
import com.financial.common.request.SubtractBudgetRequest;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RestController
public class FinancialSystem {

    @Autowired
    private BudgetService budgetService;

    @PostMapping(value = "/add/budget")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addBudget(final @RequestBody AddBudgetRequest req, @Context final HttpServletResponse response) {
        try {
            budgetService.addBudget(req.getYear(), req.getAmount());
        } catch (BudgetExistException e) {
            response.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return Response.status(Response.Status.BAD_REQUEST).entity(new String("Duplicate budget for " + req.getYear())).build();
        }
        return Response.status(Response.Status.OK).entity("OK").build();
    }

    @GetMapping(value = "/get/budget")
    @Produces(MediaType.APPLICATION_JSON)
    public Long getBudget(@Context final HttpServletResponse response) {
        long amount = 0;
        try {
            amount = budgetService.getBudget();
        } catch (BudgetNotFoundException e) {
            response.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
        }
        return amount;
    }

    @GetMapping(value = "/get/budget/{year}")
    @Produces(MediaType.APPLICATION_JSON)
    public Long getBudget(final @PathVariable("year") Integer year, @Context final HttpServletResponse response) {
        long amount = 0;
        try {
            amount = budgetService.getBudget(year);
        } catch (BudgetNotFoundException e) {
            response.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
        }
        return amount;
    }

    @PatchMapping(value = "/subtract/budget")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBudget(@RequestBody final SubtractBudgetRequest req, @Context final HttpServletResponse response) {
        try {
            budgetService.subtractBudget(req.getYear(), req.getAmount());
        } catch (BudgetNotFoundException e) {
            response.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return Response.status(Response.Status.BAD_REQUEST).entity(new String("There is no budget available for " + req.getYear())).build();
        }
        return Response.status(Response.Status.OK).entity(new String("OK")).build();
    }

    @PatchMapping(value = "/employee/payment")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response payment(@RequestBody final PaymentRequest req, @Context final HttpServletResponse response) {
        try {
            budgetService.payment(req.getYear(), req.getAmount());
        } catch (BudgetNotEnoughException e) {
            response.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return Response.status(Response.Status.BAD_REQUEST).entity(new String(req.getYear() + "'s budget is not enough for payment " + req.getAmount())).build();
        } catch (BudgetNotFoundException e) {
            response.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return Response.status(Response.Status.BAD_REQUEST).entity(new String("There is no budget available for " + req.getYear())).build();
        }
        return Response.status(Response.Status.OK).entity(new String("OK")).build();
    }

    @PostMapping(value = "/cleanup")
    @Produces(MediaType.APPLICATION_JSON)
    public Response cleanup() {
        budgetService.cleanup();
        return Response.status(Response.Status.OK).entity("OK").build();
    }

}
