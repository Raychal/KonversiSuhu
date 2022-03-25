package com.raychal.testkonvesisuhu.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.raychal.testkonvesisuhu.R;
import com.raychal.testkonvesisuhu.databinding.ActivityMainBinding;
import com.raychal.testkonvesisuhu.uitls.SharedPrefsTemp;
import com.raychal.testkonvesisuhu.uitls.Temperatures;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private AppCompatEditText edit1;
    private AppCompatEditText edit2;
    private Temperatures temperature;
    private LinearLayout layout_formula;
    private TextView text_formula;
    private Animation rotate_zoom_out;

    private final String[] temperatures = new String[]{
            "\u00B0C",
            "\u00B0R",
            "\u00B0F",
            "K"
    };

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.raychal.testkonvesisuhu.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        temperature = new Temperatures(this);

        rotate_zoom_out = AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate_zoom_out);

        setupToolbar();
        edit1 = binding.edit1;
        edit2 = binding.edit2;
        layout_formula = binding.layoutFormula;
        text_formula = binding.textFormula;
        Spinner spinner1 = binding.spinner1;
        Spinner spinner2 = binding.spinner2;

        //edit text temp to conversion 1
        edit1.setHint(SharedPrefsTemp.getTempSymbol1(MainActivity.this));

        //edit text temp to conversion 2
        edit2.setHint(SharedPrefsTemp.getTempSymbol2(MainActivity.this));
        edit2.setKeyListener(null);
        edit2.setClickable(false);

        //Spinner & Adapter 1
        ArrayAdapter<String> arrayAdapter1 =
                new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, temperatures);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(arrayAdapter1);

        //set selection
        spinner1.setSelection(SharedPrefsTemp.getTempIndex1(MainActivity.this));
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String temp_symbol1 = temperatures[position];
                SharedPrefsTemp.setTemperature1(MainActivity.this, temp_symbol1, position);
                edit1.setHint(temperatures[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //spinner & adapter 2
        ArrayAdapter<String> arrayAdapter2 =
                new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, temperatures);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(arrayAdapter2);
        spinner2.setSelection(SharedPrefsTemp.getTempIndex2(MainActivity.this));
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String temp_symbol2 = temperatures[position];
                SharedPrefsTemp.setTemperature2(MainActivity.this, temp_symbol2, position);
                edit2.setHint(temperatures[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Button Count
        binding.count.setOnClickListener(v -> {
            if (Objects.requireNonNull(edit1.getText()).toString().isEmpty()) {

                FancyToast.makeText(MainActivity.this,
                        "Masukan nilai suhu yang ingin di konversi", Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();

            } else {
                layout_formula.startAnimation(rotate_zoom_out);
                if (layout_formula.getVisibility() == View.GONE) {
                    layout_formula.setVisibility(View.VISIBLE);
                }
                String symbol_temp1 = SharedPrefsTemp.getTempSymbol1(MainActivity.this);
                String symbol_temp2 = SharedPrefsTemp.getTempSymbol2(MainActivity.this);
                double value_to_conversion = Double.parseDouble(edit1.getText().toString());

                // C to R
                if (symbol_temp1.equals("\u00B0C") && symbol_temp2.equals("\u00B0R")) {
                    edit2.setText(temperature.CelciusToReamur(value_to_conversion));
                    text_formula.setText(temperature.getFormula("\u00B0C", "\u00B0R",
                            value_to_conversion, temperature.CelciusToReamur(value_to_conversion)));
                }
                // C to F
                else if (symbol_temp1.equals("\u00B0C") && symbol_temp2.equals("\u00B0F")) {
                    edit2.setText(temperature.CelciusToFahrenheit(value_to_conversion));
                    text_formula.setText(temperature.getFormula("\u00B0C", "\u00B0F",
                            value_to_conversion, temperature.CelciusToFahrenheit(value_to_conversion)));
                }
                // C to K
                else if (symbol_temp1.equals("\u00B0C") && symbol_temp2.equals("K")) {
                    edit2.setText(temperature.CelciusToKelvin(value_to_conversion));
                    text_formula.setText(temperature.getFormula("\u00B0C", "K",
                            value_to_conversion, temperature.CelciusToKelvin(value_to_conversion)));
                }

                // R to C
                else if (symbol_temp1.equals("\u00B0R") && symbol_temp2.equals("\u00B0C")) {
                    edit2.setText(temperature.ReamurToCelcius(value_to_conversion));
                    text_formula.setText(temperature.getFormula("\u00B0R", "\u00B0C",
                            value_to_conversion, temperature.ReamurToCelcius(value_to_conversion)));
                }
                // R to F
                else if (symbol_temp1.equals("\u00B0R") && symbol_temp2.equals("\u00B0F")) {
                    edit2.setText(temperature.ReamurToFahrenheit(value_to_conversion));
                    text_formula.setText(temperature.getFormula("\u00B0R", "\u00B0F",
                            value_to_conversion, temperature.ReamurToFahrenheit(value_to_conversion)));
                }
                // R to K
                else if (symbol_temp1.equals("\u00B0R") && symbol_temp2.equals("K")) {
                    edit2.setText(temperature.ReamurToKelvin(value_to_conversion));
                    text_formula.setText(temperature.getFormula("\u00B0R", "K",
                            value_to_conversion, temperature.ReamurToKelvin(value_to_conversion)));
                }
                // F to C
                else if (symbol_temp1.equals("\u00B0F") && symbol_temp2.equals("\u00B0C")) {
                    edit2.setText(temperature.FahrenheitToCelcius(value_to_conversion));
                    text_formula.setText(temperature.getFormula("\u00B0F", "\u00B0C",
                            value_to_conversion, temperature.FahrenheitToCelcius(value_to_conversion)));
                }
                // F to R
                else if (symbol_temp1.equals("\u00B0F") && symbol_temp2.equals("\u00B0R")) {
                    edit2.setText(temperature.FahrenheitToReamur(value_to_conversion));
                    text_formula.setText(temperature.getFormula("\u00B0F", "\u00B0R",
                            value_to_conversion, temperature.FahrenheitToReamur(value_to_conversion)));
                }
                // F to K
                else if (symbol_temp1.equals("\u00B0F") && symbol_temp2.equals("K")) {
                    edit2.setText(temperature.FahrenheitToKelvin(value_to_conversion));
                    text_formula.setText(temperature.getFormula("\u00B0F", "K",
                            value_to_conversion, temperature.FahrenheitToKelvin(value_to_conversion)));
                }
                // K to C
                else if (symbol_temp1.equals("K") && symbol_temp2.equals("\u00B0C")) {
                    edit2.setText(temperature.KelvinToCelcius(value_to_conversion));
                    text_formula.setText(temperature.getFormula("K", "\u00B0C",
                            value_to_conversion, temperature.KelvinToCelcius(value_to_conversion)));
                }
                // K to R
                else if (symbol_temp1.equals("K") && symbol_temp2.equals("\u00B0R")) {
                    edit2.setText(temperature.KelvinToReamur(value_to_conversion));
                    text_formula.setText(temperature.getFormula("K", "\u00B0R",
                            value_to_conversion, temperature.KelvinToReamur(value_to_conversion)));
                }
                // K to F
                else if (symbol_temp1.equals("K") && symbol_temp2.equals("\u00B0F")) {
                    edit2.setText(temperature.KelvinToFahrenheit(value_to_conversion));
                    text_formula.setText(temperature.getFormula("K", "\u00B0F",
                            value_to_conversion, temperature.KelvinToFahrenheit(value_to_conversion)));
                }
                //if C equals C
                else if (symbol_temp1.equals("\u00B0C") && symbol_temp2.equals("\u00B0C")) {
                    edit2.setText(temperature.check_after_decimal_point(value_to_conversion));
                    text_formula.setText("\u00B0C  =  " + temperature.check_after_decimal_point(value_to_conversion));
                }
                //if R equals R
                else if (symbol_temp1.equals("\u00B0R") && symbol_temp2.equals("\u00B0R")) {
                    edit2.setText(temperature.check_after_decimal_point(value_to_conversion));
                    text_formula.setText("\u00B0R  =  " + temperature.check_after_decimal_point(value_to_conversion));
                }
                //if F equals F
                else if (symbol_temp1.equals("\u00B0F") && symbol_temp2.equals("\u00B0F")) {
                    edit2.setText(temperature.check_after_decimal_point(value_to_conversion));
                    text_formula.setText("\u00B0F  =  " + temperature.check_after_decimal_point(value_to_conversion));
                }
                //if K equals K
                else if (symbol_temp1.equals("K") && symbol_temp2.equals("K")) {
                    edit2.setText(temperature.check_after_decimal_point(value_to_conversion));
                    text_formula.setText("K  =  " + temperature.check_after_decimal_point(value_to_conversion));
                }
            }
        });
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.app_name);
    }
}