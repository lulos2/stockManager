package app.DAO;

import app.model.Bill;

import java.util.List;

public interface BillDAO {
    void addBill(Bill bill);
    List<Bill> getAllBills();
    Bill getBillById(Long id);
    void updateBill(Bill bill);
    void deleteBill(Bill bill);
    List<Bill> searchBill(String text);
}
