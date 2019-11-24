package ved.basics.instaclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.LogOutCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUp extends AppCompatActivity {

    private TextView userName, password, emailId;
    private Button signUpButton, loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        ParseInstallation.getCurrentInstallation().saveInBackground();
        userName = findViewById(R.id.userName);
        password = findViewById(R.id.password);
        emailId = findViewById(R.id.emailId);
        if(ParseUser.getCurrentUser() != null)
        {
            transitionTOSocialMediaActivity();
        }
        password.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    loginButtonClicked(loginButton);
                }
                    return false;
            }
        });

    }


    public void loginButtonClicked(View view){
        Toast.makeText(SignUp.this, "login button Clicked", Toast.LENGTH_LONG).show();
        ParseUser currentUser = ParseUser.getCurrentUser();
       /* if(currentUser != null){
                    ParseUser.logOutInBackground(new LogOutCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e ==  null){
                                Toast.makeText(SignUp.this,
                                        "recent user logged out now logging you in",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }*/
       ParseUser.logInInBackground(userName.getText().toString(),
               password.getText().toString(), new LogInCallback() {

           @Override
           public void done(ParseUser user, ParseException e) {
                ProgressDialog progressBar = new ProgressDialog(SignUp.this);
                progressBar.setMessage("Signing up, Please wait");
                progressBar.show();

                if(e == null)
                {
                    Toast.makeText(SignUp.this,
                    "Congrates for Logging in " +
                    ParseUser.getCurrentUser().
                    get("username"),
                    Toast.LENGTH_LONG).show();
                    transitionTOSocialMediaActivity();
                    }

                else{
                    Toast.makeText(SignUp.this,
                    "" + e.getMessage(),
                    Toast.LENGTH_LONG).show();
                    progressBar.dismiss();
                    }
                    progressBar.dismiss();

                    }
                });
            }





    public void signUpButtonClicked(View view) {
        ParseUser user = new ParseUser();
        user.setUsername(userName.getText().toString());
        user.setPassword(password.getText().toString());
        user.setEmail(emailId.getText().toString());
        if (userName.getText().toString().equals("") ||
                emailId.getText().toString().equals("") ||
                userName.getText().toString().equals("")) {
            Toast.makeText(SignUp.this, "fill all 3 info", Toast.LENGTH_SHORT).show();
        } else {

            user.signUpInBackground(new SignUpCallback() {
                 @Override
                 public void done(ParseException e) {
                 if (e == null) {
                 Toast.makeText(SignUp.this,
                 "Congrates for registering",
                 Toast.LENGTH_LONG).show();
                 if (ParseUser.getCurrentUser() != null) {
                 Toast.makeText(SignUp.this,
                 "Logged in user name is " +
                 ParseUser.getCurrentUser().getUsername(),
                 Toast.LENGTH_LONG).show();
                 }
                 } else {
                 Toast.makeText(SignUp.this,
                 "" + e.getMessage(),
                     Toast.LENGTH_LONG).show();
                       }

                     }
                     }
            );
        }

    }

    public void rootLayoutClicked(View v){

        try {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
        private void transitionTOSocialMediaActivity(){

        Intent intent = new Intent(SignUp.this, SocialMediaActivity.class);
            startActivity(intent);
        }

}

