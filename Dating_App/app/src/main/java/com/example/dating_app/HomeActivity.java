package com.example.dating_app;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dating_app.Api.ApiService;
import com.example.dating_app.Model.DisLikeRequest;
import com.example.dating_app.Model.LikeRequest;
import com.example.dating_app.Model.User;
import com.example.dating_app.adapter.LikedUsersAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    private String authToken;
    private List<User> userList;
    private List<User> likedUsersList;
    private int currentIndex = 0;
    private ImageButton buttonLike;
    private ImageButton buttonDislike;
    private TextView name;
    private TextView age;
    private TextView location;
    private RecyclerView recyclerViewLikedUsers;
    private LikedUsersAdapter likedUsersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        initUI();

        // Get token
        authToken = getIntent().getStringExtra("token");

        // Call API & Show user first
        getUsers();
        getLikedUsers();

        buttonLike.setOnClickListener(v -> {
            likeUser();
            showNextUser();
        });

        buttonDislike.setOnClickListener(v -> {
            disLikeUser();
            showNextUser();
        });
    }

    private void initUI() {
        buttonLike = findViewById(R.id.buttonLike);
        buttonDislike = findViewById(R.id.buttonDislike);
        name = findViewById(R.id.nameHome);
        age = findViewById(R.id.ageHome);
        location = findViewById(R.id.locationHome);
        recyclerViewLikedUsers = findViewById(R.id.recyclerViewLikedUsers);
        recyclerViewLikedUsers.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    private void showNextUser() {
        // Kiểm tra xem đã hiển thị hết danh sách chưa
        if (userList != null && !userList.isEmpty()) {
            // Tăng chỉ số hiện tại để hiển thị thông tin của người dùng tiếp theo
            currentIndex++;
            if (currentIndex < userList.size()) {
                // Hiển thị thông tin của người dùng mới
                displayUserInfo(userList.get(currentIndex));
            } else {
                // Nếu đã hiển thị hết danh sách, bạn có thể thực hiện một hành động nào đó,
                // như thông báo cho người dùng biết rằng họ đã xem hết danh sách.
                Toast.makeText(this, "Bạn đã xem hết danh sách người dùng.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void likeUser() {
        LikeRequest likeRequest = new LikeRequest(userList.get(currentIndex).getId());
        ApiService.apiService.likeUser(authToken, likeRequest).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(HomeActivity.this, response.body(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void disLikeUser() {
        DisLikeRequest disLikeRequest = new DisLikeRequest(userList.get(currentIndex).getId());
        ApiService.apiService.dislikeUser(authToken, disLikeRequest).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(HomeActivity.this, response.body(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayUserInfo(User user) {
        name.setText(user.getName());
        // Temporary email (age, location)
        age.setText(user.getEmail());
        location.setText(user.getEmail());
    }

    private void getUsers() {
        // Thực hiện yêu cầu GET với token
        ApiService.apiService.getUsers(authToken).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Xử lý dữ liệu người dùng và hiển thị lên giao diện
                    userList = response.body();
                    // Show firstUser
                    if (userList != null && !userList.isEmpty()) {
                        displayUserInfo(userList.get(0)); // Hiển thị thông tin của người dùng đầu tiên
                    }
                } else {
                    Toast.makeText(HomeActivity.this, "Hết DL", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getLikedUsers() {
        // Thực hiện yêu cầu GET với token để lấy danh sách người dùng đã like
        ApiService.apiService.getLikedUsers(authToken).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    likedUsersList = response.body();
                    likedUsersAdapter = new LikedUsersAdapter(likedUsersList, HomeActivity.this, authToken);
                    recyclerViewLikedUsers.setAdapter(likedUsersAdapter);
                } else {
                    Toast.makeText(HomeActivity.this, "Không thể tải danh sách người dùng đã like", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
