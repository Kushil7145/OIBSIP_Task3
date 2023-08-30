package com.oasis_infobytes.unitconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView convertFromDropDowntextView, convertToDropdownTextView, conversionUnitText, convertTypeDropDowntextView;
    EditText UnitsToConvert;
    ArrayList<String> arrayList1;
    ArrayList<String> arrayList2;
    Dialog fromDialog;
    Dialog toDialog;
    Button conversionButton;
    String convertFromValue, convertToValue, conversionValue, conversionType, selectType;
    String[] Units = {"Kilogram","Gram","Kilometer","Meter","C","F","Miles"};
    String [] Types ={"Weight","Length","Temperature",};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        convertFromDropDowntextView = findViewById(R.id.convert_from_dropdown_menu);
        convertToDropdownTextView = findViewById(R.id.convert_to_dropdown_menu);
        convertTypeDropDowntextView = findViewById(R.id.convert_Type_dropdown_menu);
        conversionButton = findViewById(R.id.ConversionButton);
        conversionUnitText = findViewById(R.id.ConversionUnitText);
        UnitsToConvert = findViewById(R.id.UnitToConvertValueEditText);

        arrayList1 = new ArrayList<>();
        for(String i: Units){
            arrayList1.add(i);
        }
        arrayList2 = new ArrayList<>();
        for(String i: Types){
            arrayList2.add(i);
        }
        convertTypeDropDowntextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromDialog = new Dialog(MainActivity.this);
                fromDialog.setContentView(R.layout.untitype_spinner);
                fromDialog.getWindow().setLayout(650,800);
                fromDialog.show();

                EditText editText = fromDialog.findViewById(R.id.edit_text);
                ListView listView = fromDialog.findViewById(R.id.list_view);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_list_item_1,arrayList2);
                listView.setAdapter(adapter);

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        adapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        convertTypeDropDowntextView.setText(adapter.getItem(position));
                        fromDialog.dismiss();
                        selectType = adapter.getItem(position);
                        updateFromDropdown(selectType);
                    }
                });
            }
        });
        convertFromDropDowntextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromDialog = new Dialog(MainActivity.this);
                fromDialog.setContentView(R.layout.from_spinner);
                fromDialog.getWindow().setLayout(650,800);
                fromDialog.show();

                EditText editText = fromDialog.findViewById(R.id.edit_text);
                ListView listView = fromDialog.findViewById(R.id.list_view);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_list_item_1,arrayList1);
                listView.setAdapter(adapter);

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        adapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        convertFromDropDowntextView.setText(adapter.getItem(position));
                        fromDialog.dismiss();
                        convertFromValue = adapter.getItem(position);
                    }
                });
            }
        });
        convertToDropdownTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toDialog = new Dialog(MainActivity.this);
                toDialog.setContentView(R.layout.to_spinner);
                toDialog.getWindow().setLayout(650,800);
                toDialog.show();

                EditText editText = toDialog.findViewById(R.id.edit_text);
                ListView listView = toDialog.findViewById(R.id.list_view);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1,arrayList1);
                listView.setAdapter(adapter);

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                       adapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        convertToDropdownTextView.setText(adapter.getItem(position));
                        toDialog.dismiss();
                        convertToValue = adapter.getItem(position);
                    }
                });
            }
        });
        conversionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Double UnitsToConvert = Double.valueOf(MainActivity.this.UnitsToConvert.getText().toString());
                    getConversionUnits(convertFromValue,convertToValue,UnitsToConvert);
                }
                catch(Exception e){

                }
            }
        });
    }
    public static double convert(String conversionFrom, String conversionTo, double value) {
            double result = 0.00;
            if ((conversionFrom.equals("kilogram") && conversionTo.equals("gram"))||(conversionFrom.equals("kilometer")&&(conversionTo.equals("meter")))){
                result = value * 1000;
            } else if ((conversionFrom.equals("gram") && conversionTo.equals("kilogram"))||(conversionFrom.equals("meter")&&(conversionTo.equals("kilometer")))) {
                result = value / 1000;
            } else if((conversionFrom.equals("C"))&&(conversionTo.equals("F"))){
                result = value*(9/5)+32;
            }else if((conversionFrom.equals("F"))&&(conversionTo.equals("C"))) {
                result = (value - 32) / 1.8;
            } else if ((conversionFrom.equals("kilometer"))&&(conversionTo.equals("miles"))) {
                result = (value)/1.609;
            }
            else if ((conversionFrom.equals("miles"))&&(conversionTo.equals("kilometer"))) {
                result = (value)*1.609;
            }else if((conversionFrom.equals("kilometer"))&&(conversionTo.equals("centimeter"))){
                result = (value)*100000;
            }else if((conversionFrom.equals("centimeter"))&&(conversionTo.equals("kilometer"))){
                result = (value)/100000;
            }else if((conversionFrom.equals("meter"))&&(conversionTo.equals("centimeter"))){
                result = (value)*100;
            }else if((conversionFrom.equals("centimeter"))&&(conversionTo.equals("meter"))){
                result = (value)/100;
            }else if((conversionFrom.equals("K"))&&(conversionTo.equals("C"))) {
                result = (value)-273.15;
            }else if((conversionFrom.equals("C"))&&(conversionTo.equals("K"))) {
                result = (value)+273.15;
            }
            else if ((conversionFrom.equals("kilogram") && conversionTo.equals("kilogram"))||(conversionFrom.equals("kilometer")&&(conversionTo.equals("kilometer")))||
                    (conversionFrom.equals("gram") && conversionTo.equals("gram"))||(conversionFrom.equals("meter")&&(conversionTo.equals("meter"))) ||
                    ((conversionFrom.equals("C"))&&(conversionTo.equals("C")))||((conversionFrom.equals("F"))&&(conversionTo.equals("F")))||
                    ((conversionFrom.equals("centimeter"))&&(conversionTo.equals("centimeter")))||((conversionFrom.equals("K"))&&(conversionTo.equals("K")))){
                result = value;
            }

            return result;
        }
    // Inside your activity or class
    public String getConversionUnits(String conversionFrom, String conversionTo, Double UnitstoConvert) {
        double conversionValue = convert(conversionFrom, conversionTo, UnitstoConvert);
        conversionValue = round(conversionValue, 3);
        conversionUnitText.setText(String.valueOf(conversionValue));
        return null;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    private void updateFromDropdown(String type) {
        arrayList1.clear();
        switch (type) {
            case "Weight":
                arrayList1.add("gram");
                arrayList1.add("milligram");
                arrayList1.add("kilogram");
                arrayList1.add("ton");
                arrayList1.add("pound");
                arrayList1.add("ounce");
                arrayList1.add("carrat");
                arrayList1.add("atomic mass unit");
                break;
            case "Length":
                arrayList1.add("meter");
                arrayList1.add("kilometer");
                arrayList1.add("centimeter");
                arrayList1.add("miles");
                arrayList1.add("yard");
                arrayList1.add("foot");
                arrayList1.add("inch");
                arrayList1.add("Light Year");
                break;
            case "Temperature":
                arrayList1.add("C");
                arrayList1.add("F");
                arrayList1.add("K");
                break;
            default:
                break;
        }
    }

}