package com.inamul.ledgers;

public class Ledger {

    String ledgerName, totalAmount;

    public Ledger() {    }

    public Ledger(String ledgerName, String totalAmount) {
        this.ledgerName = ledgerName;
        this.totalAmount = totalAmount;
    }

    public String getLedgerName() {
        return ledgerName;
    }

    public void setLedgerName(String ledgerName) {
        this.ledgerName = ledgerName;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}
