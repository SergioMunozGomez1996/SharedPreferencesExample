package com.example.sharedpreferencesexample;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button aplicarButton;

    private Button cerrarButton;

    private SharedPreferences preferences;

    int tamanoLetra;

    private SeekBar tamanoSeekBar;

    private TextView tamanoSharedTextView;

    private TextView tamanoTextView;

    private TextView textSahredTextView;

    private EditText textoeditText;

    public static String decrypt(String paramString) {
        return new String(Base64.decode(paramString, 0));
    }

    public static String encrypt(String paramString) {
        return Base64.encodeToString(paramString.getBytes(), 0);
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_main);
        this.textoeditText = (EditText)findViewById(R.id.editText);
        this.tamanoTextView = (TextView)findViewById(R.id.tamanotextView);
        this.tamanoSeekBar = (SeekBar)findViewById(R.id.seekBar);
        this.aplicarButton = (Button)findViewById(R.id.aplicarbutton);
        this.textSahredTextView = (TextView)findViewById(R.id.textView5);
        this.tamanoSharedTextView = (TextView)findViewById(R.id.textView6);
        this.cerrarButton = (Button)findViewById(R.id.cerrarButton);
        this.tamanoSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar param1SeekBar, int param1Int, boolean param1Boolean) {
                MainActivity mainActivity = MainActivity.this;
                mainActivity.tamanoLetra = param1Int;
                TextView textView = mainActivity.tamanoTextView;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Tama(");
                stringBuilder.append(param1Int);
                stringBuilder.append("/50)");
                textView.setText(stringBuilder.toString());
            }

            public void onStartTrackingTouch(SeekBar param1SeekBar) {}

            public void onStopTrackingTouch(SeekBar param1SeekBar) {}
        });
        this.aplicarButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {
                SharedPreferences.Editor editor = MainActivity.this.preferences.edit();
                editor.putString(MainActivity.encrypt("texto"), MainActivity.encrypt(MainActivity.this.textoeditText.getText().toString()));
                editor.putInt("tamano", MainActivity.this.tamanoLetra);
                editor.commit();
                Intent intent = new Intent((Context)MainActivity.this, ResultadoActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
        this.cerrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void onResume() {
        super.onResume();
        this.preferences = PreferenceManager.getDefaultSharedPreferences((Context)this);
        String str = decrypt(this.preferences.getString(encrypt("texto"), encrypt("texto por defecto")));
        int i = this.preferences.getInt("tamano", 32);
        this.textSahredTextView.setText(str);
        this.tamanoSharedTextView.setText(String.valueOf(i));
        this.textoeditText.setText(str);
        this.tamanoSeekBar.setProgress(i);
    }
}
