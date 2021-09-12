package com.employee.app.dao;

import com.employee.common.BaseDao;
import com.employee.domain.model.Reimbursement;

import java.util.List;

public interface ReimbursementDao extends BaseDao<Reimbursement> {

    public void addReimbursement(Reimbursement reimbursement);

    public List<Reimbursement> getReimbursement(String name, String email, int year, int month);

    public int cleanup();

}
