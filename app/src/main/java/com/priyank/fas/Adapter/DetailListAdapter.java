package com.priyank.fas.Adapter;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import com.priyank.fas.Activity.SecondActivity;
import com.priyank.fas.Constant.MyConstant;
import com.priyank.fas.Model.FestDetailModel;
import com.priyank.fas.R;
import com.priyank.fas.SQLite.CRUD;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class DetailListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public Context context;
    public ArrayList<FestDetailModel> list;
    private final int TYPE_HEADER = 0;
    private final int TYPE_ITEM = 1;

    String startDate = "";

    public DetailListAdapter(Context context, ArrayList<FestDetailModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View itemView = LayoutInflater.from(context).inflate(R.layout.detail_item_layout, parent, false);
            return new DetailListAdapter.ItemViewHolder(itemView);

        } else if (viewType == TYPE_HEADER) {
            View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_footer, parent, false);
            return new DetailListAdapter.HeaderViewHolder(headerView);

        } else return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof ItemViewHolder) {
            int creditAmount = 0;
            int debitAmount = 0;

            final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

            itemViewHolder.item_name.setText(list.get(position - 1).getItemName());
            itemViewHolder.item_date.setText(" (" + list.get(position - 1).getDate() + ")");

            startDate = list.get(list.size()-1).getDate();

            if (list.get(position - 1).getCredit() == 0) {
                itemViewHolder.item_credit_amount.setText("-");
            } else {
                itemViewHolder.item_credit_amount.setText(String.valueOf(list.get(position - 1).getCredit()));
                creditAmount = list.get(position - 1).getCredit();
                debitAmount = 0;
            }

            if (list.get(position - 1).getDebit() == 0) {
                itemViewHolder.item_debit_amount.setText("-");
            } else {
                itemViewHolder.item_debit_amount.setText(String.valueOf(list.get(position - 1).getDebit()));
                creditAmount = 0;
                debitAmount = list.get(position - 1).getDebit();
            }

            final int finalCreditAmount = creditAmount;
            final int finalDebitAmount = debitAmount;
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    openDialogBox(list.get(position - 1).getId(), list.get(position - 1).getItemName(), list.get(position - 1).getDate(), finalCreditAmount, finalDebitAmount);
                    return true;
                }
            });

        } else if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;

            headerViewHolder.creditText.setText(String.valueOf(SecondActivity.totalCredit));
            headerViewHolder.debitText.setText(String.valueOf(SecondActivity.totalDebit));
            headerViewHolder.totalText.setText("TOTAL (" + (SecondActivity.totalCredit - SecondActivity.totalDebit) + ")");
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView item_name, item_date, item_credit_amount, item_debit_amount;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            item_name = itemView.findViewById(R.id.item_name);
            item_date = itemView.findViewById(R.id.item_date);
            item_credit_amount = itemView.findViewById(R.id.item_credit_amount);
            item_debit_amount = itemView.findViewById(R.id.item_debit_amount);
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView totalText, creditText, debitText;

        public HeaderViewHolder(View view) {
            super(view);
            totalText = view.findViewById(R.id.total_text);
            creditText = view.findViewById(R.id.credit_text);
            debitText = view.findViewById(R.id.debit_text);
        }
    }

    public void openDialogBox(final int id, String name, final String date, int cAmt, int dAmt) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.add_new_item_layout);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        final TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
        final EditText itemName = dialog.findViewById(R.id.item_name);
        final TextView itemDate = dialog.findViewById(R.id.item_date);
        final LinearLayout typeLayout = dialog.findViewById(R.id.type_layout);
        final RadioButton radioCredit = dialog.findViewById(R.id.radio_credit);
        final RadioButton radioDebit = dialog.findViewById(R.id.radio_debit);
        final EditText itemAmount = dialog.findViewById(R.id.item_amount);
        Button updateButton = dialog.findViewById(R.id.add_item_button);
        Button delButton = dialog.findViewById(R.id.del_item_button);

        dialogTitle.setText("Update Entry");
        updateButton.setText("Update");
        delButton.setVisibility(View.VISIBLE);

        itemName.setText(name);
        itemDate.setText(date);

        if (dAmt == 0) {
            radioCredit.setChecked(true);
            radioDebit.setChecked(false);
            itemAmount.setText(String.valueOf(cAmt));

        } else {
            radioCredit.setChecked(false);
            radioDebit.setChecked(true);
            itemAmount.setText(String.valueOf(dAmt));
        }

        itemDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(itemDate);
            }
        });

        radioCredit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    radioCredit.setChecked(true);
                    radioDebit.setChecked(false);
                    itemAmount.setText("");
                }
            }
        });

        radioDebit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    radioDebit.setChecked(true);
                    radioCredit.setChecked(false);
                    itemAmount.setText("");
                }
            }
        });

        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CRUD.deleteT2Data(id);
                dialog.dismiss();
                SecondActivity.totalCredit = 0;
                SecondActivity.totalDebit = 0;
                ((SecondActivity)context).recreate();
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            int newCrAmt = 0;
            int newDbAmt = 0;
            @Override
            public void onClick(View view) {
                if (itemName.getText().toString().trim().isEmpty()) {
                    MyConstant.shakeView(itemName);
                    return;
                }
                if (itemDate.getText().toString().trim().equals("Select Date")) {
                    MyConstant.shakeView(itemDate);
                    return;
                }
                if (!radioCredit.isChecked() && !radioDebit.isChecked()) {
                    MyConstant.shakeView(typeLayout);
                    return;
                }
                if (itemAmount.getText().toString().trim().isEmpty()) {
                    MyConstant.shakeView(itemAmount);
                    return;
                }
                if (Integer.parseInt(itemAmount.getText().toString().trim()) == 0) {
                    MyConstant.shakeView(itemAmount);
                    return;
                }
                if (radioCredit.isChecked()) {
                    newCrAmt = Integer.parseInt(itemAmount.getText().toString().trim());
                    newDbAmt = 0;
                }
                if (radioDebit.isChecked()) {
                    newCrAmt = 0;
                    newDbAmt = Integer.parseInt(itemAmount.getText().toString().trim());
                }

                CRUD.updateT2Data(id, MyConstant.capitalize(itemName.getText().toString().trim()), date, newCrAmt, newDbAmt);
                dialog.dismiss();
                SecondActivity.totalCredit = 0;
                SecondActivity.totalDebit = 0;
                ((SecondActivity)context).recreate();
            }
        });

        dialog.show();
    }

    public void setDate(final TextView tv) {
        final Calendar myCalendar = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        try {
            cal.setTime(sdf.parse(startDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                tv.setText(sdf.format(myCalendar.getTime()));
            }

        };

        DatePickerDialog dpDialog = new DatePickerDialog(context, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
        dpDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
        dpDialog.getDatePicker().setMaxDate(myCalendar.getTimeInMillis() + 1);
        dpDialog.show();
    }
}
