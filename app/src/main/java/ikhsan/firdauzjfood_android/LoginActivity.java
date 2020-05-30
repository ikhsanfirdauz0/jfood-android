package ikhsan.firdauzjfood_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity
{

//    SharedPreferenceManager sharedPreferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
//        sharedPreferenceManager = new SharedPreferenceManager(this);
//
//        if(sharedPreferenceManager.getSPAlreadyLogin())
//        {
//            startActivity(new Intent(LoginActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
//                    Intent.FLAG_ACTIVITY_NEW_TASK));
//            finish();
//        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText inputEmail = findViewById(R.id.inputEmaIl);
        final EditText inputPassword = findViewById(R.id.inputPassword);
        Button buttonLogin = findViewById(R.id.buttonLogin);
        TextView linkRegisterNow = findViewById(R.id.linkRegisterNow);


        buttonLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            JSONObject objJSON = new JSONObject(response);
                            if (objJSON != null)
                            {
//                                sharedPreferenceManager.saveSPBoolean(SharedPreferenceManager.SP_ALREADY_LOGIN, true);
                                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                                mainIntent.putExtra("currentUserId", objJSON.getInt("id"));
                                mainIntent.putExtra("currentUserName", objJSON.getString("name"));
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(mainIntent);
                                finish();
                            }
                        }
                        catch(JSONException e)
                        {
                            Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(email, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);

            }
        });

        linkRegisterNow.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
}