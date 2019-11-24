package com.priyank.fas.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.priyank.fas.Constant.AppUtils;
import com.priyank.fas.Constant.MyConstant;
import com.priyank.fas.Constant.SharedPref;
import com.priyank.fas.R;

public class PwResetFragment extends Fragment implements View.OnClickListener {
    public EditText textOldPin, textNewPin1, textNewPin2;
    public TextView forgetPin;
    public Button saveBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v2 = inflater.inflate(R.layout.fragment_pw_reset, container, false);

        textOldPin = v2.findViewById(R.id.et_old_pin);
        textNewPin1 = v2.findViewById(R.id.et_new_pin);
        textNewPin2 = v2.findViewById(R.id.et_new_pin2);
        forgetPin = v2.findViewById(R.id.forget_pin_text);
        saveBtn = v2.findViewById(R.id.btn_save);

        saveBtn.setOnClickListener(this);
        forgetPin.setOnClickListener(this);

        if (SharedPref.getPrefrences(getContext()).contains("PIN")) {
            textOldPin.setVisibility(View.VISIBLE);
        } else {
            textOldPin.setVisibility(View.GONE);
        }

        return v2;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_save:
                MyConstant.hideKeyboard(getContext());
                if (validateOldPin() && validateNewPin()) {
                    SharedPref.enterPrefrences(getContext(), "PIN", textNewPin1.getText().toString());
                    MyConstant.changeFragment(getActivity(), R.id.frame_layout, new LoginFragment());
                }
                break;

            case R.id.forget_pin_text:
                SharedPref.cleanPrefrences(getContext(), "PIN");
                textOldPin.setVisibility(View.GONE);
                forgetPin.setVisibility(View.GONE);
                break;
        }

    }

    public boolean validateOldPin() {
        if (SharedPref.getPrefrences(getContext()).contains("PIN")) {
            if (textOldPin.getText().toString().isEmpty()) {
                MyConstant.shakeView(textOldPin);
                return false;
            }
            if (textOldPin.getText().toString().length() < 4) {
                MyConstant.shakeView(textOldPin);
                AppUtils.Toast(getContext(), "Enter 4-Digit PIN");
                return false;
            }
            if (!textOldPin.getText().toString().equals(SharedPref.getPrefrencesValues(getContext(), "PIN"))) {
                MyConstant.shakeView(textOldPin);
                AppUtils.Toast(getContext(), "Enter Correct Old PIN");
                return false;
            }
            return true;
        } else {
            return true;
        }
    }

    public boolean validateNewPin() {
        if (textNewPin1.getText().toString().isEmpty()) {
            MyConstant.shakeView(textNewPin1);
            return false;
        }
        if (textNewPin1.getText().toString().length() < 4) {
            MyConstant.shakeView(textNewPin1);
            AppUtils.Toast(getContext(), "Enter 4-Digit PIN");
            return false;
        }
        if (textNewPin2.getText().toString().isEmpty()) {
            MyConstant.shakeView(textNewPin2);
            return false;
        }
        if (textNewPin2.getText().toString().length() < 4) {
            MyConstant.shakeView(textNewPin2);
            AppUtils.Toast(getContext(), "Enter 4-Digit PIN");
            return false;
        }
        if (!textNewPin1.getText().toString().equalsIgnoreCase(textNewPin2.getText().toString())) {
            MyConstant.shakeView(textNewPin2);
            AppUtils.Toast(getContext(), "Confirm PIN is not match");
            return false;
        }
        return true;
    }

}
