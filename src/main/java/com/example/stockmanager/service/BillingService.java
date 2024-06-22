package com.example.stockmanager.service;

import com.example.stockmanager.DAO.BillDAO;
import com.example.stockmanager.DAO.BillDAOImpl;
import com.example.stockmanager.model.Bill;
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