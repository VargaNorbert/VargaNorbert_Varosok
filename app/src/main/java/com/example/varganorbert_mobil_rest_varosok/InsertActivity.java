package com.example.varganorbert_mobil_rest_varosok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;


public class InsertActivity extends AppCompatActivity {
    private Button backButton;
    private Button addButton;
    private EditText nev;
    private EditText orszag;
    private EditText lakossag;
    private String URL = "https://retoolapi.dev/Cb6JVg/Varosok";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        init();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InsertActivity.this, MainActivity.class);
                InsertActivity.this.startActivity(intent);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addVaros();
            }
        });
    }

    public void init() {
        backButton = findViewById(R.id.backButton);
        addButton = findViewById(R.id.addButton);
        nev = findViewById(R.id.nev);
        orszag = findViewById(R.id.orszag);
        lakossag = findViewById(R.id.lakossag);
    }

    private void addVaros() {
        String nev = this.nev.getText().toString();
        String orszag = this.orszag.getText().toString();
        String lakossag = this.lakossag.getText().toString();

        boolean valid = ellenorzes();

        if (valid) {
            Toast.makeText(this,
                    "Minden mezőt ki kell tölteni", Toast.LENGTH_SHORT).show();
            return;
        }

        int emberek = Integer.parseInt(lakossag);
        Varos varos = new Varos(0, nev, orszag, emberek);
        Gson converter = new Gson();
        RequestTask task = new RequestTask(URL, "POST", converter.toJson(varos));
        task.execute();
    }

    private boolean ellenorzes() {
        if (nev.getText().toString().isEmpty() || orszag.getText().toString().isEmpty() || lakossag.getText().toString().isEmpty()) {
            return true;
        } else {
            return false;
        }

    }

    private class RequestTask extends AsyncTask<Void, Void, Response> {
        private String requestUrl;
        private String requestMethod;
        private String requestBody;


        public RequestTask(String requestUrl, String requestMethod, String requestBody) {
            this.requestUrl = requestUrl;
            this.requestMethod = requestMethod;
            this.requestBody = requestBody;
        }

        @Override
        protected Response doInBackground(Void... voids) {
            Response response = null;
            try {
                switch (requestMethod) {
                    case "POST":
                        response = RequestHandler.post(requestUrl, requestBody);
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

    }
}