package edu.pdx.cs.joy.xiangqz;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView readmeIcon = findViewById(R.id.readme_icon);

        readmeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showReadmeDialog();
            }
        });

        Button addButton = findViewById(R.id.addbutton);
        Button searchButton = findViewById(R.id.searchbutton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddPhoneCallActivity.class);
                startActivity(intent);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchPhoneCallActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showReadmeDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_readme, null);

        TextView readmeText = dialogView.findViewById(R.id.readme_text);
        String readmeContent = "Student Name: Xiangqian Zhang\n\n" +
                "Project Description\n\n" +
                "This project is an Android application that allows users to create and manage phone bills and phone calls. " +
                "The application provides a user-friendly interface where users can input details for each phone call, " +
                "validate the input, and view the information in a structured and readable format. " +
                "The app also includes functionality to display a README with detailed instructions on how to use the application correctly.\n\n" +
                "Features\n\n" +
                "Add Phone Call: Users can add a new phone call by entering the customer name, caller's phone number, callee's phone number, and the start and end times of the call.\n" +
                "Search Phone Calls: Users can search for phone calls within a specific date range.\n" +
                "View README: An icon is available in the Toolbar to view this README content directly within the application.\n\n" +
                "Input Fields and Formats\n\n" +
                "1. Customer Name:\n" +
                "   - Field: Customer Name\n" +
                "   - Format: Alphanumeric characters (e.g., \"Eli Zhang\")\n\n" +
                "2. Phone Numbers:\n" +
                "   - Field: Caller Number and Callee Number\n" +
                "   - Format: Valid 10-digit phone number (e.g., \"503-555-1234\")\n\n" +
                "3. Date and Time:\n" +
                "   - Field: Start Time and End Time\n" +
                "   - Format: Date and time must be entered in the following format:\n" +
                "       - Date: MM/DD/YYYY (e.g., \"07/15/2023\")\n" +
                "       - Time: HH:MM AM/PM (e.g., \"02:30 PM\")";

        readmeText.setText(readmeContent);

        new AlertDialog.Builder(this)
                .setTitle("README")
                .setView(dialogView)
                .setPositiveButton("OK", null)
                .show();
    }
}