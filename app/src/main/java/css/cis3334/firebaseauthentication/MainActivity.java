package css.cis3334.firebaseauthentication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {

    private TextView textViewStatus;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private Button buttonGoogleLogin;
    private Button buttonCreateLogin;
    private Button buttonSignOut;
    private FirebaseAuth mAuth;  // instance for FirebaseAuthentification
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setting the views
        textViewStatus = (TextView) findViewById(R.id.textViewStatus);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        //set the buttons
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonGoogleLogin = (Button) findViewById(R.id.buttonGoogleLogin);
        buttonCreateLogin = (Button) findViewById(R.id.buttonCreateLogin);
        buttonSignOut = (Button) findViewById(R.id.buttonSignOut);


        //user login
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Log.d("CIS3334", "normal login ");
                signIn(editTextEmail.getText().toString(), editTextPassword.getText().toString());
            }
        });

        //creates account with email and password
        buttonCreateLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Log.d("CIS3334", "Create Account ");
                createAccount(editTextEmail.getText().toString(), editTextPassword.getText().toString());
            }
        });

        //Listens for authentication state and logs user in
        buttonGoogleLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Log.d("CIS3334", "Google login ");
                googleSignIn();
            }
        });

        //Listens for authentication state and logs user out
        buttonSignOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Log.d("CIS3334", "Logging out - signOut ");
                signOut();
            }
        });


        mAuth = FirebaseAuth.getInstance();  //initializing the FirebaseAuth instance
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            /*
            * checks for current user status in the firebase
            */
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in "CIS3334" REPLACED tag
                    //Log.d("CIS3334", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    // Log.d("CIS3334", "onAuthStateChanged:signed_out");
                    textViewStatus.setText("Signed out");
                }
            }
        };
    }

    private void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                // account creation successful, update UI with the signed-in user's information
                // Log.d("CIS3334", "createUserWithEmail:onComplete:" + task.isSuccessful());
                textViewStatus.setText("Account creation successful");
                /*If not signed in displays error message.
                 *If sign in successful state listener will be notified and
                 *user can be handled in the listener.
                */
                if (!task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Authentication failed",
                            //Toast.LENGTH_SHORT).show(); //display message
                            textViewStatus.setText("Authentication failed. Please try again");
                }
            }
        });
    }


    //sign in existing user with email and password after validating them
    private void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                // Sign in success, update UI with the signed-in user's information
                textViewStatus.setText("Sign in successful!");
                /* If not signed in displays error message.
                * If sign in successful state listener will be notified and
                * user can be handled in the listener.
                */
                if (!task.isSuccessful()) {
                    textViewStatus.setText("Authentication failed. Please try again");
                }
            }
        });
    }

    //sign user from firebase database
    private void signOut () {
        mAuth.signOut();

    }

    private void googleSignIn() {

    }




}
