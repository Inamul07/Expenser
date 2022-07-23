package com.inamul.ledgers;

public class Expense {
    String expenseName, expenseAmount, expenseType, date, key;

    public Expense() {
    }

    public Expense(String expenseName, String expenseAmount, String expenseType, String date, String key) {
        this.key = key;
        this.expenseName = expenseName;
        this.expenseAmount = expenseAmount;
        this.expenseType = expenseType;
        this.date = date;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public String getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(String expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
