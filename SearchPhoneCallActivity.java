package edu.pdx.cs.joy.xiangqz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SearchPhoneCallActivity extends AppCompatActivity {

    private EditText editCustomerName;
    private EditText editBeginDate;
    private EditText editEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_phone_call);

        editCustomerName = findViewById(R.id.editCustomer);
        editBeginDate = findViewById(R.id.edit_begin);
        editEndDate = findViewById(R.id.edit_end);


        Button searchButton = findViewById(R.id.search_call_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String customer = editCustomerName.getText().toString();
                String begin = editBeginDate.getText().toString();
                String end = editEndDate.getText().toString();

                if (customer.isEmpty()) {
                    Toast.makeText(SearchPhoneCallActivity.this, "Customer name is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (begin.length() > 0 ) {
                    if (!isValidDateTime(begin)) {
                        Toast.makeText(SearchPhoneCallActivity.this, "Invalid Begin date and time format. Please use MM/DD/YYYY HH:MM AM/PM.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (end.length() > 0) {
                    if ( !isValidDateTime(end)){
                        Toast.makeText(SearchPhoneCallActivity.this, "Invalid End date and time format. Please use MM/DD/YYYY HH:MM AM/PM.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                Intent intent = new Intent(SearchPhoneCallActivity.this, SearchResult.class);
                intent.putExtra("customer", customer);
                intent.putExtra("begin", begin);
                intent.putExtra("end", end);
                startActivity(intent);
            }
        });

        Button backButton = findViewById(R.id.back_button_search);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private boolean isValidDateTime(String dateTime) {
        String dateTimePattern = "(1[0-2]|0?[1-9])/([1-9]|[12][0-9]|3[01])/\\d{4} (0?[1-9]|1[0-2]):[0-5][0-9] (AM|PM)";
        return dateTime.matches(dateTimePattern);
    }
}