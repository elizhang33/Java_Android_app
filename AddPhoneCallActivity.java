package edu.pdx.cs.joy.xiangqz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.appcompat.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;

public class AddPhoneCallActivity extends AppCompatActivity {

    private EditText editCustomer, editCaller, editCallee, editBegin, editEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phone_call);

        editCustomer = findViewById(R.id.editCustomer);
        editCaller = findViewById(R.id.editCaller);
        editCallee = findViewById(R.id.editCallee);
        editBegin = findViewById(R.id.edit_begin);
        editEnd = findViewById(R.id.edit_end);

        findViewById(R.id.add_call_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPhoneCall();
            }
        });

        findViewById(R.id.back_button_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void addPhoneCall() {
        String customer = editCustomer.getText().toString();
        String caller = editCaller.getText().toString();
        String callee = editCallee.getText().toString();
        String begin = editBegin.getText().toString();
        String end = editEnd.getText().toString();

        if (!isValidPhoneNumber(caller) || !isValidPhoneNumber(callee)) {
            Toast.makeText(this, "Invalid phone number format. Please use XXX-XXX-XXXX.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidDateTime(begin) || !isValidDateTime(end)) {
            Toast.makeText(this, "Invalid date and time format. Please use MM/DD/YYYY HH:MM AM/PM.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (customer.isEmpty() || caller.isEmpty() || callee.isEmpty() || begin.isEmpty() || end.isEmpty()) {
            Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show();
            return;
        }

        String fileName = customer + "_phonebill.txt";

        try (FileOutputStream fos = openFileOutput(fileName, Context.MODE_APPEND);
             OutputStreamWriter osw = new OutputStreamWriter(fos)) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy h:mm a");
            LocalDateTime beginDateTime = LocalDateTime.parse(begin, formatter);
            LocalDateTime endDateTime = LocalDateTime.parse(end, formatter);

            long durationMinutes = ChronoUnit.MINUTES.between(beginDateTime, endDateTime);
            String duration = String.format("%d minutes", durationMinutes);
            osw.write(caller + " " + callee + " " + begin + " " + end + " " + duration + "\n");
            Toast.makeText(this, "Phone call added successfully", Toast.LENGTH_SHORT).show();
            finish();

        } catch (IOException e) {
            Toast.makeText(this, "Error saving phone call", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        String phonePattern = "\\d{3}-\\d{3}-\\d{4}";
        return phoneNumber.matches(phonePattern);
    }

    private boolean isValidDateTime(String dateTime) {
        String dateTimePattern = "(1[0-2]|0?[1-9])/([1-9]|[12][0-9]|3[01])/\\d{4} (0?[1-9]|1[0-2]):[0-5][0-9] (AM|PM)";
        return dateTime.matches(dateTimePattern);
    }


}