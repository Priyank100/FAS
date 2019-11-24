package com.priyank.fas.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.priyank.fas.Adapter.MyListAdapter;
import com.priyank.fas.Constant.MyConstant;
import com.priyank.fas.Constant.SwipeToDeleteCallback;
import com.priyank.fas.Model.FestListModel;
import com.priyank.fas.R;
import com.priyank.fas.SQLite.CRUD;
import com.priyank.fas.SQLite.DatabaseHelper;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public TextView toolbarTitle;
    public TextView toolbarSubTitle;
    public ImageView toolbarAddImg;
    public ImageView toolbarSaveImg;
    public RecyclerView recyclerView;
    public ImageView noRecord;
    public SwipeRefreshLayout swipeRefreshLayout;

    public CRUD crud;
    public DatabaseHelper myDb;
    public ArrayList<FestListModel> list;
    LinearLayout main_linear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarSubTitle = findViewById(R.id.toolbar_sub_title);
        toolbarAddImg = findViewById(R.id.toolbar_add_icon);
        toolbarSaveImg = findViewById(R.id.toolbar_save_icon);
        recyclerView = findViewById(R.id.recycler_view);
        noRecord = findViewById(R.id.no_record);
        swipeRefreshLayout = findViewById(R.id.swipeToRefresh);
        main_linear = findViewById(R.id.main_linear);

        toolbarTitle.setText("FAS");
        toolbarAddImg.setVisibility(View.VISIBLE);
        toolbarSubTitle.setVisibility(View.GONE);
        toolbarSaveImg.setVisibility(View.GONE);

        crud = new CRUD(MainActivity.this);
        myDb = new DatabaseHelper(MainActivity.this);
        list = new ArrayList<>();

        viewAll();

        toolbarAddImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewFest();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recreate();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void viewAll() {
        Cursor res = myDb.getAllT1Data();
        if (res.getCount() == 0) {
            recyclerView.setVisibility(View.GONE);
            noRecord.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            noRecord.setVisibility(View.GONE);

            while (res.moveToNext()) {
                int id = res.getInt(0);
                String name = res.getString(1);
                String date = res.getString(2);

                FestListModel model = new FestListModel(id, name, date);
                list.add(0, model);

                LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);
                recyclerView.setLayoutManager(manager);
                MyListAdapter adapter = new MyListAdapter(MainActivity.this, list);
                adapter.notifyItemInserted(0);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);

                swipeToDelete();
            }
        }
    }

    public void addNewFest() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_add_fest);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        final EditText festName = dialog.findViewById(R.id.fest_name);
        final TextView festDate = dialog.findViewById(R.id.fest_date);
        Button addButton = dialog.findViewById(R.id.add_button);

        festDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(festDate);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (festName.getText().toString().trim().isEmpty()) {
                    MyConstant.shakeView(festName);
                    return;
                }
                if (festDate.getText().toString().trim().equals("Select Date")) {
                    MyConstant.shakeView(festDate);
                    return;
                }
                crud.addT1Data(capitalize(festName.getText().toString().trim()), festDate.getText().toString().trim());
                dialog.dismiss();
                recreate();
            }
        });
        dialog.show();
    }

    public void setDate(final TextView tv) {
        final Calendar myCalendar = Calendar.getInstance();
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

        new DatePickerDialog(MainActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public String capitalize(String title) {
        String str = "";
        String[] strArray = title.split(" ");
        StringBuilder builder = new StringBuilder();
        for (String s : strArray) {
            String cap = s.substring(0, 1).toUpperCase() + s.substring(1);
            builder.append(cap + " ");
        }
        str = builder.toString();
        return str;
    }

    public void swipeToDelete() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
                super.onSwiped(viewHolder, position);
                int id = list.get(viewHolder.getAdapterPosition()).getFestId();
                crud.deleteT1Data(id);
                recreate();
            }
        };
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }
}
