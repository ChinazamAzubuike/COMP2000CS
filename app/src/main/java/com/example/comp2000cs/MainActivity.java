package com.example.comp2000cs;

import android.content.Intent;
import android.os.Bundle;
//import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;



public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main); //Replace activity_main with any page you want to start with
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->

        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;


        });


        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(view ->
        {
            //to go to secondactivity page, which is the employee login
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent); // Start the second activity
        });





        Button btnEmployee = findViewById(R.id.btn_employee);
        btnEmployee.setOnClickListener(v ->
        {
            Intent intent = new Intent(MainActivity.this, AdminEmployees.class);
            startActivity(intent);
        });








    }
}