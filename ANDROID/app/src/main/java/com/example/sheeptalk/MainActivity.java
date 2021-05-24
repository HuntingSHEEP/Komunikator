package com.example.sheeptalk;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sheeptalk.logic.klient.AsyncClient;
import com.example.sheeptalk.logic.klient.Klient;
import com.example.sheeptalk.logic.klient.Nadawaj;
import com.example.sheeptalk.logic.klient.Odbior;

public class MainActivity extends AppCompatActivity {
    Klient klient;
    TextView textHello;
    private Button button;
    private EditText login, password;
    private String loginS, passS;
    boolean zalogowano = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Klient klient = new Klient();
        new AsyncClient().execute(klient);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.logInButt);
        login = (EditText) findViewById(R.id.Login);
        password = (EditText) findViewById(R.id.Password);




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginS = login.getText().toString();
                passS = password.getText().toString();

                try{
                    int intLog = Integer.parseInt(loginS);
                    if (passS.length()<1){throw new Exception("Brak hasła");}
                    boolean zalogowano = klient.logIn(intLog,passS);

                }catch (Exception e){
                    //TODO: Chmurka z komunikatem
                }
                if(zalogowano){
                    LogIn();
                }
            }
        });

    }
    private void LogIn(){
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }
}