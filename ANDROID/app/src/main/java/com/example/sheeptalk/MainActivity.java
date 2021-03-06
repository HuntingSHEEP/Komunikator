package com.example.sheeptalk;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sheeptalk.logic.klient.AsyncClient;
import com.example.sheeptalk.logic.klient.AsyncLogin;
import com.example.sheeptalk.logic.klient.Klient;
import com.example.sheeptalk.logic.klient.Nadawaj;
import com.example.sheeptalk.logic.klient.Odbior;
import com.example.sheeptalk.logic.klient.Singleton;

public class MainActivity extends AppCompatActivity {
    Singleton singleton;
    Klient klient;
    TextView textHello;
    private Button button;
    private EditText login, password;
    private String loginS, passS;
    public boolean zalogowano = false;
    MainActivity act;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        act = this;
        singleton = Singleton.getInstance();
        klient = singleton.klient;

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
                System.out.println("LOGIN: "+loginS+"; "+passS);

                try{
                    int intLog = Integer.parseInt(loginS);
                    if (passS.length()<1){
                        throw new Exception("Brak hasła");
                    }
                    //AsyncLogin asyncLogin = new AsyncLogin( intLog, passS, act);
                    //asyncLogin.execute();
                    //System.out.println("STATUS: " + asyncLogin.getStatus());
                    zalogowano = klient.logIn(intLog, passS);


                }catch (Exception e){
                    //TODO: Chmurka z komunikatem
                    e.printStackTrace();
                }
                if(zalogowano){
                    singleton.id=loginS;
                    //to nie jest używane >> przeniesiono do asynca
                    LogIn();
                }
            }
        });

    }
    public void LogIn(){
        //przeniesione do asynca
        //już nie!
        Intent intent = new Intent(this, MainMENU.class);
        startActivity(intent);
    }
}