 package com.bloodbird.lifescheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bloodbird.lifescheduler.Models.Job;
import com.bloodbird.lifescheduler.Schedulers.Base;

 /**
  * Adding Job Activity
  */
 public class AddJob extends AppCompatActivity {

    EditText name,priority,time;
    Button add;
    Base base;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job);
        base=Base.getInstance();
        name=findViewById(R.id.name);
        priority=findViewById(R.id.priority);
        time=findViewById(R.id.time);
        add=findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                base.jobs.add(new Job(name.getText().toString(),Integer.parseInt(priority.getText().toString()),Integer.parseInt(time.getText().toString())));
                base.clockPulse();
                Toast.makeText(AddJob.this, "item Added", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}