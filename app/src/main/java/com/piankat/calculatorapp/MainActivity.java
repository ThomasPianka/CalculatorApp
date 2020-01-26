package com.piankat.calculatorapp;

// Import Dependencies
import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

//MainActivity Class
public class MainActivity extends AppCompatActivity {
    //Initialize default answer to null
    Integer answer = null;
    @Override
    //OnCreate method runs when app is initialized
    protected void onCreate(Bundle savedInstanceState) {
        //Creates new onclick listener to listen for button presses
        View.OnClickListener callbackListener = new View.OnClickListener() {
            @Override
            //Method to run code when buttons are clicked.
            public void onClick(View v) throws ArithmeticException {
                //Creates variables for the EditTexts and TextView
                EditText firstNumEditText = findViewById(R.id.firstNumEditText);
                EditText secondNumEditText = findViewById(R.id.secondNumEditText);

                //Hide keyboard when value is entered into text box
                View view = getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

                //Error checking, returns message if one or more text boxes are left blank.
                if (firstNumEditText.getText().toString().matches("") || secondNumEditText.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), "Please enter numbers in both fields.",
                            Toast.LENGTH_SHORT).show();

                }
                //Otherwise, adds or subtracts values depending on what button was pressed.
                else {
                    //Creates variables for the values passed into the EditTexts and the sum
                    int num1 = Integer.parseInt(firstNumEditText.getText().toString());
                    int num2 = Integer.parseInt(secondNumEditText.getText().toString());
                    int result;
                    if (v.getId() == R.id.addBtn) {
                        result = num1 + num2;
                        answer = result;
                    }
                    else if (v.getId() == R.id.multBtn) {
                        result = num1 * num2;
                        answer = result;
                    }
                    else if (v.getId() == R.id.divBtn) {
                        try {
                            result = num1 / num2;
                            throw new ArithmeticException("You can't divide by zero!");
                        }
                        catch (ArithmeticException e) {
                            Toast.makeText(getApplicationContext(), "Please enter a non-zero denominator.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        result = num1 - num2;
                        answer = result;
                    }
                    displayAnswer();

                }
            }
        };
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Add the variables that correspond to xml features and added the onClick Listener
        Button addBtn = findViewById(R.id.addBtn);
        Button subBtn = findViewById(R.id.subBtn);
        Button clrBtn = findViewById(R.id.clrBtn);
        Button multBtn = findViewById(R.id.multBtn);
        Button divBtn = findViewById(R.id.divBtn);

        addBtn.setOnClickListener(callbackListener);
        subBtn.setOnClickListener(callbackListener);
        multBtn.setOnClickListener(callbackListener);
        divBtn.setOnClickListener(callbackListener);

        //Initializing the clear button's click listener
        clrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initializing variables
                EditText firstNumEditText = findViewById(R.id.firstNumEditText);
                EditText secondNumEditText = findViewById(R.id.secondNumEditText);
                RadioButton decimalRadioButton = findViewById(R.id.decimalRadioButton);
                TextView resultTextView = findViewById(R.id.resultTextView);

                //Hide keyboard when value is entered into text box.
                View view = getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                //Clears text boxes and the result label, while also resetting the radio buttons
                firstNumEditText.setText("");
                secondNumEditText.setText("");
                resultTextView.setText("");
                decimalRadioButton.setChecked(true);
                answer = null;
            }
        });
    }

    //Method attached to the RadioButton as an onClick method.
    public void onChangeBase(View view) {
        displayAnswer();
    }

    public void displayAnswer() {
        //Creates variables for the RadioGroup and TextView
        TextView resultTextView = findViewById(R.id.resultTextView);
        RadioGroup rg = findViewById(R.id.radioGroup);
        int button_id = rg.getCheckedRadioButtonId();

        //Switch to determine what base to display result in depending on RadioButtons
        switch (button_id) {
            case R.id.decimalRadioButton:
                if (answer != null) {
                    resultTextView.setText(String.format("%s", Integer.toString(answer)));
                }
                else {
                    resultTextView.setText("");
                }
                break;
            case R.id.hexRadioButton:
                if (answer != null) {
                    resultTextView.setText(String.format("%s", Integer.toHexString(answer)));
                }
                else {
                    resultTextView.setText("");
                }
                break;
            case R.id.binaryRadioButton:
                if (answer != null) {
                    resultTextView.setText(String.format("%s", Integer.toBinaryString(answer)));
                }
                else {
                    resultTextView.setText("");
                }
                break;
            case R.id.octalRadioButton:
                if (answer != null) {
                    resultTextView.setText(String.format("%s", Integer.toOctalString(answer)));
                }
                else {
                    resultTextView.setText("");
                }
                break;
            case -1: //button_id returns -1 if radio group is empty.
                rg.check(R.id.decimalRadioButton);
                if (answer != null) {
                    resultTextView.setText(String.format("%s", Integer.toString(answer)));
                }
                else {
                    resultTextView.setText("");
                }
                break;
        }
    }
}
