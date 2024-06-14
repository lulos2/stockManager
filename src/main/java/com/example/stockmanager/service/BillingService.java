package com.example.stockmanager.service;

import com.example.stockmanager.DAO.BillDAO;
import com.example.stockmanager.DAO.BillDAOImpl;
import com.example.stockmanager.model.Bill;

public class BillingService {
    private final BillDAO billDAO = new BillDAOImpl();
    public void addBill(Bill bill) {
        this.billDAO.addBill(bill);

    }
}
//TODO: implement the methods getAllBills, updateBill, deleteBill and searchBill,
