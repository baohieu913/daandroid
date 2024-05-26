package com.example.dating_app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Loại bỏ màu nền của action bar
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        EditText name = findViewById(R.id.nameR);
        EditText email = findViewById(R.id.emailR);
        EditText userName = findViewById(R.id.usernameR);
        EditText passWord = findViewById(R.id.passwordR);
        EditText confirmPassword = findViewById(R.id.confirmpasswordR);
        findViewById(android.R.id.content).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Kiểm tra xem liệu EditText đang được focus hay không
                if (name.isFocused() || email.isFocused() || userName.isFocused() || passWord.isFocused() || confirmPassword.isFocused()) {
                    // Lấy đối tượng InputMethodManager từ Context
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    // Ẩn bàn phím mềm
                    imm.hideSoftInputFromWindow(name.getWindowToken(), 0);
                    // Mất focus của EditText
                    name.clearFocus();
                    email.clearFocus();
                    userName.clearFocus();
                    passWord.clearFocus();
                    confirmPassword.clearFocus();
                }
                return false;
            }
        });
    }
    public void goToSignIn(View view) {
        Intent intent = new Intent(this, LoginActivity.class); // Thay SignUpActivity bằng tên của Activity bạn muốn chuyển hướng đến
        startActivity(intent);
    }
}