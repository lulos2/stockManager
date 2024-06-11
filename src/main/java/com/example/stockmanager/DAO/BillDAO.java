package com.example.stockmanager.DAO;

import com.example.stockmanager.model.Bill;

import java.util.List;

public interface BillDAO {
    void addBill(Bill bill);
    List<Bill> getAllBills();
    void updateBill(Bill bill);
    void deleteBill(Bill bill);
    List<Bill> searchBill(String text);
}
