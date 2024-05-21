package com.innovativesolutions.iotcontroller;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationPage extends AppCompatActivity {


    private FirebaseAuth auth;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.registration_activity);
        EditText username = findViewById(R.id.usernameEditTxt);
        EditText email = findViewById(R.id.emailEditTxt);
        EditText password = findViewById(R.id.passwordEditTxt);
        EditText confirmPassword = findViewById(R.id.confirmPasswordEditTxt);
        Button registerButton = findViewById(R.id.registerBtn);
        auth = FirebaseAuth.getInstance();
        updateButtonColor(registerButton,username,email,password,confirmPassword);
        username.addTextChangedListener(textWatcher);
        email.addTextChangedListener(textWatcher);
        password.addTextChangedListener(textWatcher);
        confirmPassword.addTextChangedListener(textWatcher);
        registerButton.setOnClickListener(v -> {
            boolean allFieldsFilled = !(username.getText().toString().isEmpty())&&!(email.getText().toString().isEmpty())&&!(password.getText().toString().isEmpty())&&!(confirmPassword.getText().toString().isEmpty());
            if(!allFieldsFilled) {
                Toast.makeText(getApplicationContext(), "Please fill out all the fields!", Toast.LENGTH_SHORT).show();
            }
            else{
                if(password.getText().toString().equals(confirmPassword.getText().toString())){

                    auth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString());
                    Toast.makeText(getApplicationContext(),"Registration successful",Toast.LENGTH_SHORT).show();
                    intentNewActivity();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Passwords didn't match! Try again",Toast.LENGTH_SHORT).show();
                }
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainLayoutSignUp), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}
        @Override
        public void afterTextChanged(Editable s) {
            updateButtonColor(findViewById(R.id.registerBtn),findViewById(R.id.usernameEditTxt),findViewById(R.id.emailEditTxt),findViewById(R.id.passwordEditTxt),findViewById(R.id.confirmPasswordEditTxt));
        }
    };
    public void updateButtonColor(Button registerButton, EditText username, EditText email, EditText password, EditText confirmPassword) {
        boolean allFieldsFilled = !(username.getText().toString().isEmpty()) &&
                !(email.getText().toString().isEmpty()) &&
                !(password.getText().toString().isEmpty()) &&
                !(confirmPassword.getText().toString().isEmpty());
        if (allFieldsFilled) {
            registerButton.setBackgroundColor(getResources().getColor(R.color.buttonEnabled, getTheme()));
        } else {
            registerButton.setBackgroundColor(getResources().getColor(R.color.buttonDisabled, getTheme()));
        }
    }
    public void intentNewActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

}
