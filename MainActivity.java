package com.longkaca.playupdater;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MainActivity extends Activity {

    private TextView status;
    private boolean startedCheck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout box = new LinearLayout(this);
        box.setOrientation(LinearLayout.VERTICAL);
        box.setGravity(Gravity.CENTER);
        box.setPadding(40, 40, 40, 40);

        status = new TextView(this);
        status.setTextSize(22);
        status.setTextColor(Color.BLACK);
        status.setGravity(Gravity.CENTER);
        status.setText("Đang kiểm tra Google Play services…");
        box.addView(status);

        setContentView(box);
        checkPlayServicesNow();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        startedCheck = false;
        checkPlayServicesNow();
    }

    private void checkPlayServicesNow() {
        if (startedCheck) return;
        startedCheck = true;

        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int result = api.isGooglePlayServicesAvailable(this);

        if (result == ConnectionResult.SUCCESS) {
            status.setText("Google Play services đang hoạt động.\nĐang mở CH Play…");
            openPlayStore();
            return;
        }

        if (api.isUserResolvableError(result)) {
            status.setText("Google Play services cần cập nhật/sửa.\nĐang mở luồng của Google…");
            api.getErrorDialog(this, result, 9001, dialog -> {
                status.setText("Đã hủy luồng cập nhật.");
            }).show();
        } else {
            status.setText("Không thể xử lý Google Play services trên ROM này.\nMã lỗi: " + result);
        }
    }

    private void openPlayStore() {
        try {
            Intent market = new Intent(
                Intent.ACTION_VIEW,
                Uri.parse("market://details?id=com.google.android.gms")
            );
            market.setPackage("com.android.vending");
            startActivity(market);
        } catch (ActivityNotFoundException e) {
            try {
                startActivity(new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.gms")
                ));
            } catch (Exception ignored) {
                status.setText("Không mở được Google Play.");
            }
        }
    }
}
