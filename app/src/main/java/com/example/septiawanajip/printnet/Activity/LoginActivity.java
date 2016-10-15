package com.example.septiawanajip.printnet.Activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.septiawanajip.printnet.Object.User;
import com.example.septiawanajip.printnet.R;
import com.example.septiawanajip.printnet.Tab.TabMainACtivity;

/**
 * Created by Septiawan Aji P on 10/12/2016.
 */
public class LoginActivity extends AppCompatActivity {
    private EditText nimEditText,passwordEditText;
    private Button loginButton;
    private TextView daftarTv;
    User user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        user = new User();
        nimEditText = (EditText)findViewById(R.id.input_nim);
        passwordEditText = (EditText)findViewById(R.id.input_password);
        daftarTv = (TextView)findViewById(R.id.daftarTv);
        loginButton = (Button)findViewById(R.id.login);

        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/Gaitera_Ball-demo-FFP.ttf");
        daftarTv.setTypeface(type);
        nimEditText.setTypeface(type);
        passwordEditText.setTypeface(type);
        loginButton.setTypeface(type);

        daftarTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Melakukan Daftar", Toast.LENGTH_SHORT).show();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setNim(nimEditText.getText().toString());
                user.setPassword(passwordEditText.getText().toString());
                if(user.getNim().isEmpty()|| user.getPassword().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Masukan nim dan password dengan benar", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(getApplicationContext(), TabMainACtivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}
