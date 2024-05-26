package com.example.dating_app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dating_app.Api.ApiService;
import com.example.dating_app.Model.LoginBody;
import com.example.dating_app.Model.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText userName;
    private EditText passWord;
    private Button buttonLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Loại bỏ màu nền của action bar
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        initUI();

        findViewById(android.R.id.content).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Kiểm tra xem liệu EditText đang được focus hay không
                if (userName.isFocused() || passWord.isFocused()) {
                    // Lấy đối tượng InputMethodManager từ Context
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    // Ẩn bàn phím mềm
                    imm.hideSoftInputFromWindow(userName.getWindowToken(), 0);
                    // Mất focus của EditText
                    userName.clearFocus();
                    passWord.clearFocus();
                }
                return false;
            }
        });

        // Handle login
        buttonLogin.setOnClickListener(v -> {
            login();
        });

    }

    private void login() {
        LoginBody loginBody = new LoginBody(userName.getText().toString().trim(), passWord.getText().toString().trim());
        ApiService.apiService.login(loginBody).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    String authToken = "Bearer " + response.body().getToken();
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.putExtra("token", authToken);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initUI(){
        userName = findViewById(R.id.UsernameL);
        passWord = findViewById(R.id.PasswordL);
        buttonLogin = findViewById(R.id.buttonLogin);
    }
    public void goToSignUp(View view) {
        Intent intent = new Intent(this, RegisterActivity.class); // Thay SignUpActivity bằng tên của Activity bạn muốn chuyển hướng đến
        startActivity(intent);
    }
}