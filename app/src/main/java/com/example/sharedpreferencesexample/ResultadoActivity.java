package com.example.sharedpreferencesexample;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultadoActivity extends AppCompatActivity {
    private SharedPreferences preferences;

    private TextView textoResultado;

    private Button volverButton;

    public static String decrypt(String paramString) {
        return new String(Base64.decode(paramString, 0));
    }

    public static String encrypt(String paramString) {
        return Base64.encodeToString(paramString.getBytes(), 0);
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_resultado);
        this.textoResultado = (TextView)findViewById(R.id.textoResultado);
        this.volverButton = (Button)findViewById(R.id.volverButton);
        this.preferences = PreferenceManager.getDefaultSharedPreferences((Context)this);
        String str = decrypt(this.preferences.getString(encrypt("texto"), encrypt("texto por defecto")));
        int i = this.preferences.getInt("tamano", 32);
        this.textoResultado.setText(str);
        this.textoResultado.setTextSize(i);
        this.volverButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {
                ResultadoActivity.this.finish();
            }
        });
    }
}
