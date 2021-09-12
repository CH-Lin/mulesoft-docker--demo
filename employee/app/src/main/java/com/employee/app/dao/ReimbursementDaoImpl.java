package com.employee.app.dao;

import com.employee.common.BaseDaoImpl;
import com.employee.common.Config;
import com.employee.domain.model.Reimbursement;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.Calendar;
import java.util.List;

@Repository
public class ReimbursementDaoImpl extends BaseDaoImpl<Reimbursement> implements ReimbursementDao {

    @Transactional
    public void addReimbursement(Reimbursement reimbursement) {
        persistObject(reimbursement);
    }

    @Transactional
    public List<Reimbursement> getReimbursement(String name, String email, int year, int month) {
        StringBuilder queryStr = new StringBuilder("SELECT r FROM Reimbursement as r JOIN r.employee as e WHERE r.submissionDate >= :after AND r.submissionDate <= :before");
        if (name != null) {
            queryStr.append(" AND e.employeeName = :employeeName");
        }
        if (Config.isEmailMatching() && email != null) {
            queryStr.append(" AND e.employeeEmail = :employeeEmail");
        }
        TypedQuery<Reimbursement> query = getEntityManager().createQuery(queryStr.toString(), Reimbursement.class);
        Calendar after = Calendar.getInstance();
        Calendar before = Calendar.getInstance();
        if (month >= 0) {
            after.set(year, month, 1, 0, 0, 0);
            before.set(year, month + 1, 1, 0, 0, 0);
        } else {
            after.set(year, 0, 1);
            before.set(year, 11, 31);
        }
        if (name != null) {
            query.setParameter("employeeName", name);
        }
        if (Config.isEmailMatching() && name != null) {
            query.setParameter("employeeEmail", email);
        }
        query.setParameter("after", after.getTime());
        query.setParameter("before", before.getTime());
        return query.getResultList();
    }

    @Transactional
    public int cleanup() {
        int deletedCount = getEntityManager().createQuery("DELETE FROM Reimbursement").executeUpdate();
        return deletedCount;
    }

}
