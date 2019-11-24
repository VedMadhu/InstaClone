package ved.basics.instaclone;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.parse.ParseUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

public class Profile extends AppCompatActivity {

    private TextView welcomeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilelayout);
        welcomeText = findViewById(R.id.welcomeText);
        welcomeText.setText("Welcome!!");
        try {
            welcomeText.setText("welcome! " + ParseUser.getCurrentUser().getUsername());
        } catch (Exception e){

        }
        welcomeText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ParseUser currentUser = ParseUser.getCurrentUser();
                ParseUser.logOutInBackground();
                finish();

            }
        });
            }
    }


