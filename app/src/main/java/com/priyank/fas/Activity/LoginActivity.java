package com.priyank.fas.Activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.priyank.fas.Constant.MyConstant;
import com.priyank.fas.Fragment.LoginFragment;
import com.priyank.fas.R;

public class LoginActivity extends AppCompatActivity {
    public FrameLayout frameLayout;
    String[] allPermissions;
    final int MULTIPLE_PERMISSIONS = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        frameLayout = findViewById(R.id.frame_layout);

        allPermissions = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE};

        if (hasPermissions(LoginActivity.this, allPermissions)) {
            MyConstant.changeFragment(LoginActivity.this, R.id.frame_layout, new LoginFragment());

        } else {
            ActivityCompat.requestPermissions(this, allPermissions, MULTIPLE_PERMISSIONS);
        }
    }

    @Override
    public void onBackPressed() {
        Fragment fragmentbyID = getSupportFragmentManager().findFragmentById(R.id.frame_layout);
        if (fragmentbyID instanceof LoginFragment) {
            finish();

        } else {
            super.onBackPressed();
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS: {
                if (grantResults.length > 0) {
                    for (int grantResult : grantResults) {
                        if (grantResult != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(LoginActivity.this, allPermissions, MULTIPLE_PERMISSIONS);
                            return;
                        }
                    }
                    MyConstant.changeFragment(LoginActivity.this, R.id.frame_layout, new LoginFragment());
                } else {
                    ActivityCompat.requestPermissions(LoginActivity.this, allPermissions, MULTIPLE_PERMISSIONS);
                }
            }
        }
    }
}
