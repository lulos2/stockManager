package app.service;

import app.DAO.BillDAO;
import app.DAO.BillDAOImpl;
import app.model.Bill;

import java.util.ArrayList;

public class BillingService {
    private final BillDAO billDAO = new BillDAOImpl();
    public void addBill(Bill bill) {
        this.billDAO.addBill(bill);
    }

    public ArrayList<Bill> getAllBills() {
        return new ArrayList<Bill>(this.billDAO.getAllBills());
    }

    public Bill getBillById(Long id) {
        return this.billDAO.getBillById(id);
    }
}