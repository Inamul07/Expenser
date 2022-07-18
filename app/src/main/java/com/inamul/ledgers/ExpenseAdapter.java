package com.inamul.ledgers;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        holder.expenseNameText.setText(expense.getName());
        holder.expenseAmountText.setText(expense.getAmount());
        holder.expenseAmountSign.setText(expense.getType());
//        String type = expense.getType();
//        if(type.equals("Credit")) {
//            holder.expenseAmountSign.setText("+ ");
//            holder.expenseAmountSign.setTextColor(Color.GREEN);
//        } else if(type.equals("Debit")) {
//            holder.expenseAmountSign.setText("- ");
//            holder.expenseAmountSign.setTextColor(Color.RED);
//        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView expenseNameText, expenseAmountText, expenseAmountSign;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            expenseNameText = itemView.findViewById(R.id.expenseNameText);
            expenseAmountText = itemView.findViewById(R.id.expenseAmountText);
            expenseAmountSign = itemView.findViewById(R.id.expenseAmountSign);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface != null) {
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemClicked(pos);
                        }
                    }
                }
            });
        }
    }

}
