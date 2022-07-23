package com.inamul.ledgers;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.inamul.expenser.ExpenseActivity;
import com.inamul.expenser.R;

import java.util.ArrayList;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.MyViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<Expense> list;

    public ExpenseAdapter(Context context, ArrayList<Expense> list, RecyclerViewInterface recyclerViewInterface) {
        this.recyclerViewInterface = recyclerViewInterface;
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.expense_item, parent, false);
        return new MyViewHolder(v, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseAdapter.MyViewHolder holder, int position) {
        Expense expense = list.get(position);
        holder.expenseNameText.setText(expense.getExpenseName());
        holder.expenseAmountText.setText(expense.getExpenseAmount());
        String type = expense.getExpenseType();
        if(type.equals("Credit")) {
            holder.expenseAmountSign.setText("+ ");
            holder.expenseAmountText.setTextColor(Color.GREEN);
            holder.expenseAmountSign.setTextColor(Color.GREEN);
            holder.rupeeSign.setTextColor(Color.GREEN);
        } else if(type.equals("Debit")) {
            holder.expenseAmountSign.setText("- ");
            holder.expenseAmountText.setTextColor(Color.RED);
            holder.expenseAmountSign.setTextColor(Color.RED);
            holder.rupeeSign.setTextColor(Color.RED);
        }
        holder.expenseDateText.setText(expense.getDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView expenseNameText, expenseAmountText, expenseAmountSign, expenseDateText, rupeeSign;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            expenseNameText = itemView.findViewById(R.id.expenseNameText);
            expenseAmountText = itemView.findViewById(R.id.expenseAmountText);
            expenseAmountSign = itemView.findViewById(R.id.expenseAmountSign);
            expenseDateText = itemView.findViewById(R.id.expenseDate);
            rupeeSign = itemView.findViewById(R.id.rupeeSign);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if(recyclerViewInterface != null) {
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemLongClicked(pos);
                        }
                    }

                    return true;
                }
            });

        }
    }

}
