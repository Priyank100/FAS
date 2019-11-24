package com.priyank.fas.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.priyank.fas.Activity.MainActivity;
import com.priyank.fas.Constant.AppUtils;
import com.priyank.fas.Constant.MyConstant;
import com.priyank.fas.Constant.SharedPref;
import com.priyank.fas.R;

public class LoginFragment extends Fragment implements View.OnClickListener {
    public ImageView c1, c2, c3, c4, backImg, settingImg;
    public TextView t1, t2, t3, t4, t5, t6, t7, t8, t9, t0;
    public int counter = 0;
    public String digits = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v1 = inflater.inflate(R.layout.fragment_login, container, false);

        c1 = v1.findViewById(R.id.circle_img1);
        c2 = v1.findViewById(R.id.circle_img2);
        c3 = v1.findViewById(R.id.circle_img3);
        c4 = v1.findViewById(R.id.circle_img4);
        t1 = v1.findViewById(R.id.tv1);
        t2 = v1.findViewById(R.id.tv2);
        t3 = v1.findViewById(R.id.tv3);
        t4 = v1.findViewById(R.id.tv4);
        t5 = v1.findViewById(R.id.tv5);
        t6 = v1.findViewById(R.id.tv6);
        t7 = v1.findViewById(R.id.tv7);
        t8 = v1.findViewById(R.id.tv8);
        t9 = v1.findViewById(R.id.tv9);
        t0 = v1.findViewById(R.id.tv0);
        backImg = v1.findViewById(R.id.img_back);
        settingImg = v1.findViewById(R.id.img_setting);

        t1.setOnClickListener(this);
        t2.setOnClickListener(this);
        t3.setOnClickListener(this);
        t4.setOnClickListener(this);
        t5.setOnClickListener(this);
        t6.setOnClickListener(this);
        t7.setOnClickListener(this);
        t8.setOnClickListener(this);
        t9.setOnClickListener(this);
        t0.setOnClickListener(this);
        backImg.setOnClickListener(this);
        settingImg.setOnClickListener(this);

        checkCounter();

        return v1;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv1:
                counter++;

                if (counter == 1) {
                    c1.setImageResource(R.drawable.fill_circle);
                    digits = digits + t1.getText().toString();
                }

                if (counter == 2) {
                    c2.setImageResource(R.drawable.fill_circle);
                    digits = digits + t1.getText().toString();
                }

                if (counter == 3) {
                    c3.setImageResource(R.drawable.fill_circle);
                    digits = digits + t1.getText().toString();
                }

                if (counter == 4) {
                    c4.setImageResource(R.drawable.fill_circle);
                    digits = digits + t1.getText().toString();
                }

                checkCounter();
                break;

            case R.id.tv2:
                counter++;
                c1.setImageResource(R.drawable.fill_circle);

                if (counter == 1) {
                    c1.setImageResource(R.drawable.fill_circle);
                    digits = digits + t2.getText().toString();
                }

                if (counter == 2) {
                    c2.setImageResource(R.drawable.fill_circle);
                    digits = digits + t2.getText().toString();
                }

                if (counter == 3) {
                    c3.setImageResource(R.drawable.fill_circle);
                    digits = digits + t2.getText().toString();
                }

                if (counter == 4) {
                    c4.setImageResource(R.drawable.fill_circle);
                    digits = digits + t2.getText().toString();
                }

                checkCounter();
                break;

            case R.id.tv3:
                counter++;
                c1.setImageResource(R.drawable.fill_circle);

                if (counter == 1) {
                    c1.setImageResource(R.drawable.fill_circle);
                    digits = digits + t3.getText().toString();
                }

                if (counter == 2) {
                    c2.setImageResource(R.drawable.fill_circle);
                    digits = digits + t3.getText().toString();
                }

                if (counter == 3) {
                    c3.setImageResource(R.drawable.fill_circle);
                    digits = digits + t3.getText().toString();
                }

                if (counter == 4) {
                    c4.setImageResource(R.drawable.fill_circle);
                    digits = digits + t3.getText().toString();
                }

                checkCounter();
                break;

            case R.id.tv4:
                counter++;
                c1.setImageResource(R.drawable.fill_circle);

                if (counter == 1) {
                    c1.setImageResource(R.drawable.fill_circle);
                    digits = digits + t4.getText().toString();
                }

                if (counter == 2) {
                    c2.setImageResource(R.drawable.fill_circle);
                    digits = digits + t4.getText().toString();
                }

                if (counter == 3) {
                    c3.setImageResource(R.drawable.fill_circle);
                    digits = digits + t4.getText().toString();
                }

                if (counter == 4) {
                    c4.setImageResource(R.drawable.fill_circle);
                    digits = digits + t4.getText().toString();
                }

                checkCounter();
                break;

            case R.id.tv5:
                counter++;
                c1.setImageResource(R.drawable.fill_circle);

                if (counter == 1) {
                    c1.setImageResource(R.drawable.fill_circle);
                    digits = digits + t5.getText().toString();
                }

                if (counter == 2) {
                    c2.setImageResource(R.drawable.fill_circle);
                    digits = digits + t5.getText().toString();
                }

                if (counter == 3) {
                    c3.setImageResource(R.drawable.fill_circle);
                    digits = digits + t5.getText().toString();
                }

                if (counter == 4) {
                    c4.setImageResource(R.drawable.fill_circle);
                    digits = digits + t5.getText().toString();
                }

                checkCounter();
                break;

            case R.id.tv6:
                counter++;
                c1.setImageResource(R.drawable.fill_circle);

                if (counter == 1) {
                    c1.setImageResource(R.drawable.fill_circle);
                    digits = digits + t6.getText().toString();
                }

                if (counter == 2) {
                    c2.setImageResource(R.drawable.fill_circle);
                    digits = digits + t6.getText().toString();
                }

                if (counter == 3) {
                    c3.setImageResource(R.drawable.fill_circle);
                    digits = digits + t6.getText().toString();
                }

                if (counter == 4) {
                    c4.setImageResource(R.drawable.fill_circle);
                    digits = digits + t6.getText().toString();
                }

                checkCounter();
                break;

            case R.id.tv7:
                counter++;
                c1.setImageResource(R.drawable.fill_circle);

                if (counter == 1) {
                    c1.setImageResource(R.drawable.fill_circle);
                    digits = digits + t7.getText().toString();
                }

                if (counter == 2) {
                    c2.setImageResource(R.drawable.fill_circle);
                    digits = digits + t7.getText().toString();
                }

                if (counter == 3) {
                    c3.setImageResource(R.drawable.fill_circle);
                    digits = digits + t7.getText().toString();
                }

                if (counter == 4) {
                    c4.setImageResource(R.drawable.fill_circle);
                    digits = digits + t7.getText().toString();
                }

                checkCounter();
                break;

            case R.id.tv8:
                counter++;
                c1.setImageResource(R.drawable.fill_circle);

                if (counter == 1) {
                    c1.setImageResource(R.drawable.fill_circle);
                    digits = digits + t8.getText().toString();
                }

                if (counter == 2) {
                    c2.setImageResource(R.drawable.fill_circle);
                    digits = digits + t8.getText().toString();
                }

                if (counter == 3) {
                    c3.setImageResource(R.drawable.fill_circle);
                    digits = digits + t8.getText().toString();
                }

                if (counter == 4) {
                    c4.setImageResource(R.drawable.fill_circle);
                    digits = digits + t8.getText().toString();
                }

                checkCounter();
                break;

            case R.id.tv9:
                counter++;
                c1.setImageResource(R.drawable.fill_circle);

                if (counter == 1) {
                    c1.setImageResource(R.drawable.fill_circle);
                    digits = digits + t9.getText().toString();
                }

                if (counter == 2) {
                    c2.setImageResource(R.drawable.fill_circle);
                    digits = digits + t9.getText().toString();
                }

                if (counter == 3) {
                    c3.setImageResource(R.drawable.fill_circle);
                    digits = digits + t9.getText().toString();
                }

                if (counter == 4) {
                    c4.setImageResource(R.drawable.fill_circle);
                    digits = digits + t9.getText().toString();
                }

                checkCounter();
                break;

            case R.id.tv0:
                counter++;
                c1.setImageResource(R.drawable.fill_circle);

                if (counter == 1) {
                    c1.setImageResource(R.drawable.fill_circle);
                    digits = digits + t0.getText().toString();
                }

                if (counter == 2) {
                    c2.setImageResource(R.drawable.fill_circle);
                    digits = digits + t0.getText().toString();
                }

                if (counter == 3) {
                    c3.setImageResource(R.drawable.fill_circle);
                    digits = digits + t0.getText().toString();
                }

                if (counter == 4) {
                    c4.setImageResource(R.drawable.fill_circle);
                    digits = digits + t0.getText().toString();
                }

                checkCounter();
                break;

            case R.id.img_back:
                counter--;
                digits = digits.substring(0, digits.length() - 1);

                if (counter == 0) {
                    c1.setImageResource(R.drawable.blank_circle);
                }

                if (counter == 1) {
                    c2.setImageResource(R.drawable.blank_circle);
                }

                if (counter == 2) {
                    c3.setImageResource(R.drawable.blank_circle);
                }

                if (counter == 3) {
                    c4.setImageResource(R.drawable.blank_circle);
                }

                checkCounter();
                break;

            case R.id.img_setting:
                MyConstant.changeFragment(getActivity(), R.id.frame_layout, new PwResetFragment());
                break;
        }
    }

    public void checkCounter() {

        if (counter >= 4) {
            setClickable(false);
            backImg.setClickable(true);

            if (SharedPref.getPrefrences(getContext()).contains("PIN")) {
                if (digits.equalsIgnoreCase(SharedPref.getPrefrencesValues(getContext(), "PIN"))) {
                    startActivity(new Intent(getContext(), MainActivity.class));
                    getActivity().finish();
                }
                else {
                    AppUtils.Toast(getContext(), "Enter Correct PIN");
                    counter = 0;
                    digits = "";
                    c1.setImageResource(R.drawable.blank_circle);
                    c2.setImageResource(R.drawable.blank_circle);
                    c3.setImageResource(R.drawable.blank_circle);
                    c4.setImageResource(R.drawable.blank_circle);
                }

            } else {
                AppUtils.Toast(getContext(), "Set Your PIN First");
                MyConstant.shakeView(settingImg);
                counter = 0;
                digits = "";
                c1.setImageResource(R.drawable.blank_circle);
                c2.setImageResource(R.drawable.blank_circle);
                c3.setImageResource(R.drawable.blank_circle);
                c4.setImageResource(R.drawable.blank_circle);
            }
        }
        if (counter < 4 && counter >= 0) {
            setClickable(true);
            backImg.setClickable(true);
        }
        if (counter <= 0) {
            backImg.setClickable(false);
        }
    }

    public void setClickable(boolean b) {
        t1.setClickable(b);
        t2.setClickable(b);
        t3.setClickable(b);
        t4.setClickable(b);
        t5.setClickable(b);
        t6.setClickable(b);
        t7.setClickable(b);
        t8.setClickable(b);
        t9.setClickable(b);
        t0.setClickable(b);
    }

}
