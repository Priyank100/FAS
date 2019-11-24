package com.priyank.fas.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.priyank.fas.Adapter.DetailListAdapter;
import com.priyank.fas.Constant.MyConstant;
import com.priyank.fas.Model.FestDetailModel;
import com.priyank.fas.R;
import com.priyank.fas.SQLite.CRUD;
import com.priyank.fas.SQLite.DatabaseHelper;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SecondActivity extends AppCompatActivity {
    public TextView toolbarTitle;
    public TextView toolbarSubTitle;
    public ImageView toolbarAddImg;
    public ImageView toolbarSaveImg;

    public CRUD crud;
    public DatabaseHelper myDb;
    public ArrayList<FestDetailModel> list;

    public LinearLayout dataLayout;
    public RecyclerView recyclerView;
    public ImageView noRecord;

    public static int totalCredit = 0;
    public static int totalDebit = 0;

    public String festName = "";
    public String festDate = "";
    public int festId = 0;
    public String itemType = "";
    public int creditAmount = 0;
    public int debitAmount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarSubTitle = findViewById(R.id.toolbar_sub_title);
        toolbarAddImg = findViewById(R.id.toolbar_add_icon);
        toolbarSaveImg = findViewById(R.id.toolbar_save_icon);
        toolbarSubTitle.setVisibility(View.VISIBLE);

        dataLayout = findViewById(R.id.data_layout);
        recyclerView = findViewById(R.id.recycler_view2);
        noRecord = findViewById(R.id.no_record2);

        crud = new CRUD(SecondActivity.this);
        myDb = new DatabaseHelper(SecondActivity.this);
        list = new ArrayList<>();

        int Id = getIntent().getIntExtra("Id", 0);
        Cursor res = myDb.getT1DataById(Id);
        if (res.getCount() == 0) {

        } else {
            while (res.moveToNext()) {
                festId = res.getInt(0);
                festName = res.getString(1);
                festDate = res.getString(2);

                toolbarTitle.setText(festName);
                toolbarSubTitle.setText("(" + festDate + ")");
            }
        }

        toolbarAddImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewItem();
            }
        });

        toolbarSaveImg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                createSavePdf();
            }
        });

        viewAll();
    }

    public void viewAll() {
        Cursor res = myDb.getT2DataByForeignKey(festId);
        if (res.getCount() == 0) {
            dataLayout.setVisibility(View.GONE);
            toolbarSaveImg.setVisibility(View.GONE);
            noRecord.setVisibility(View.VISIBLE);
        } else {
            dataLayout.setVisibility(View.VISIBLE);
            toolbarSaveImg.setVisibility(View.VISIBLE);
            noRecord.setVisibility(View.GONE);

            while (res.moveToNext()) {
                int id = res.getInt(0);
                int fId = res.getInt(1);
                String name = res.getString(2);
                String date = res.getString(3);
                int creditAmount = res.getInt(4);
                int debitAmount = res.getInt(5);
//                AppUtils.LogD("Result>> " + id + "::" + name + "::" + date);

                FestDetailModel model = new FestDetailModel(id, fId, name, date, creditAmount, debitAmount);
                list.add(0, model);

                LinearLayoutManager manager = new LinearLayoutManager(SecondActivity.this);
                manager.setReverseLayout(true);
                recyclerView.setLayoutManager(manager);
                DetailListAdapter adapter = new DetailListAdapter(SecondActivity.this, list);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
            }
            for (int i = 0; i < list.size(); i++) {
                totalCredit = totalCredit + list.get(i).getCredit();
                totalDebit = totalDebit + list.get(i).getDebit();
            }
        }
    }

    public void addNewItem() {
        final Dialog dialog = new Dialog(SecondActivity.this);
        dialog.setContentView(R.layout.add_new_item_layout);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        final TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
        final EditText itemName = dialog.findViewById(R.id.item_name);
        final TextView itemDate = dialog.findViewById(R.id.item_date);
        final LinearLayout typeLayout = dialog.findViewById(R.id.type_layout);
        final RadioButton radioCredit = dialog.findViewById(R.id.radio_credit);
        final RadioButton radioDebit = dialog.findViewById(R.id.radio_debit);
        final TextView itemAmount = dialog.findViewById(R.id.item_amount);
        Button addButton = dialog.findViewById(R.id.add_item_button);
        Button delButton = dialog.findViewById(R.id.del_item_button);

        dialogTitle.setText("Add New Entry");
        addButton.setText("Save");
        delButton.setVisibility(View.GONE);

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
                    itemType = "Credit";
                }
            }
        });

        radioDebit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    radioDebit.setChecked(true);
                    radioCredit.setChecked(false);
                    itemType = "Debit";
                }
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
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
                if (itemType.isEmpty()) {
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
                if (itemType.equals("Credit")) {
                    creditAmount = Integer.parseInt(itemAmount.getText().toString().trim());
                    debitAmount = 0;
                }
                if (itemType.equals("Debit")) {
                    creditAmount = 0;
                    debitAmount = Integer.parseInt(itemAmount.getText().toString().trim());
                }

                crud.addT2Data(festId, MyConstant.capitalize(itemName.getText().toString().trim()), itemDate.getText().toString(), creditAmount, debitAmount);
                dialog.dismiss();
                totalCredit = 0;
                totalDebit = 0;
                recreate();
            }
        });
        dialog.show();
    }

    public void setDate(final TextView tv) {
        final Calendar myCalendar = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        try {
            cal.setTime(sdf.parse(festDate));
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

        DatePickerDialog dpDialog = new DatePickerDialog(SecondActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
        dpDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
        dpDialog.getDatePicker().setMaxDate(myCalendar.getTimeInMillis() + 1);
        dpDialog.show();

    }

    public void createSavePdf() {
        try {
            Document document = new Document();
            PdfPTable table = new PdfPTable(new float[]{2, 1, 1});
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell("Items");
            table.addCell("Credit (+)");
            table.addCell("Debit (-)");
            table.setHeaderRows(1);
            PdfPCell[] cells = table.getRow(0).getCells();

            for (int j = 0; j < cells.length; j++) {
                cells[j].setBackgroundColor(BaseColor.GRAY);
            }

            for (int i = (list.size()-1); i >= 0 ; i--) {

                table.addCell(list.get(i).getItemName() + "(" + list.get(i).getDate() + ")");

                if (list.get(i).getCredit() == 0) {
                    table.addCell("-");
                } else {
                    table.addCell(String.valueOf(list.get(i).getCredit()));
                }
                if (list.get(i).getDebit() == 0) {
                    table.addCell("-");
                } else {
                    table.addCell(String.valueOf(list.get(i).getDebit()));
                }
            }

            int totalCredit = 0;
            int totalDebit = 0;
            for (int i=0; i<list.size(); i++) {
                totalCredit = totalCredit + list.get(i).getCredit();
                totalDebit = totalDebit + list.get(i).getDebit();
            }

            table.addCell("TOTAL (" + (totalCredit-totalDebit) + ")");
            table.addCell(String.valueOf(totalCredit));
            table.addCell(String.valueOf(totalDebit));

            String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

            String fname = "/sdcard/Documents/" + festName + "_" + date + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(fname));
            document.open();
            document.add(table);
            document.close();
            Toast.makeText(this, "PDF is created!!!", Toast.LENGTH_SHORT).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        totalCredit = 0;
        totalDebit = 0;
    }
}