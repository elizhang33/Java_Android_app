package edu.pdx.cs.joy.xiangqz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SearchResult extends AppCompatActivity {
    private LinearLayout resultLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        resultLayout = findViewById(R.id.result_layout);

        Intent intent = getIntent();
        String customer = intent.getStringExtra("customer");
        String begin = intent.getStringExtra("begin");
        String end = intent.getStringExtra("end");

        List<String> phoneCalls = searchPhoneCalls(customer, begin, end);

        if (phoneCalls.isEmpty()) {
            Toast.makeText(this, "No phone calls found for the customer.", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            displayResults(phoneCalls);
        }

        Button backButton = findViewById(R.id.back_button_result);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // Go back to the SearchPhoneCallActivity
            }
        });
    }

    private List<String> searchPhoneCalls(String customer, String begin, String end) {
        String fileName = customer + "_phonebill.txt";
        List<String> phoneCalls = new ArrayList<>();

        try (FileInputStream fis = openFileInput(fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(fis))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (begin != null && !begin.isEmpty() && end != null && !end.isEmpty()) {
                    String[] parts = line.split(" ");
                    String callBegin = parts[2] + " " + parts[3] + " " + parts[4];
                    String callEnd = parts[5] + " " + parts[6] + " " + parts[7];

                    if (isWithinRange(callBegin, callEnd, begin, end)) {
                        phoneCalls.add(line);
                    }
                } else {
                    phoneCalls.add(line);
                }
            }
        } catch (IOException e) {
            Toast.makeText(this, "Error reading phone calls", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        return phoneCalls;
    }

    private boolean isWithinRange(String callBegin, String callEnd, String begin, String end) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy h:mm a");
        LocalDateTime callBeginTime = LocalDateTime.parse(callBegin, formatter);
        LocalDateTime callEndTime = LocalDateTime.parse(callEnd, formatter);
        LocalDateTime beginTime = LocalDateTime.parse(begin, formatter);
        LocalDateTime endTime = LocalDateTime.parse(end, formatter);

        return !callBeginTime.isBefore(beginTime) && !callEndTime.isAfter(endTime);
    }

    private void displayResults(List<String> phoneCalls) {
        ScrollView scrollView = findViewById(R.id.scrollViewResults);
        LinearLayout linearLayout = (LinearLayout) scrollView.getChildAt(0);

        for (String call : phoneCalls) {
            String[] parts = call.split(" "); // Assuming you stored the data as CSV
            String caller = parts[0];
            String callee = parts[1];
            String begin = parts[2] + " " + parts[3] + " " + parts[4];
            String end = parts[5] + " " + parts[6] + " " + parts[7];
            String duration = parts[8];

            TextView textView = new TextView(this);
            textView.setTextSize(16); // Optional: Set text size for better readability

            SpannableStringBuilder builder = new SpannableStringBuilder();

            SpannableString callerLabel = new SpannableString("Caller: ");
            callerLabel.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, callerLabel.length(), 0);
            builder.append(callerLabel);
            builder.append(caller + "\n");

            SpannableString calleeLabel = new SpannableString("Callee: ");
            calleeLabel.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, calleeLabel.length(), 0);
            builder.append(calleeLabel);
            builder.append(callee + "\n");

            SpannableString beginLabel = new SpannableString("Begin Date Time: ");
            beginLabel.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, beginLabel.length(), 0);
            builder.append(beginLabel);
            builder.append(begin + "\n");

            SpannableString endLabel = new SpannableString("End Date Time: ");
            endLabel.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, endLabel.length(), 0);
            builder.append(endLabel);
            builder.append(end + "\n");

            SpannableString durationLabel = new SpannableString("Duration: ");
            durationLabel.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, durationLabel.length(), 0);
            builder.append(durationLabel);
            builder.append(duration + " minutes\n");

            textView.setText(builder);
            linearLayout.addView(textView);
        }
    }
}