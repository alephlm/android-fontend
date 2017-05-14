package br.gov.ce.sda.androidsda.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.gov.ce.sda.androidsda.R;
import br.gov.ce.sda.androidsda.model.User;
import br.gov.ce.sda.androidsda.rest.ServiceAPI;
import br.gov.ce.sda.androidsda.rest.ServiceGenerator;
import okhttp3.Credentials;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NovaContaActivity extends AppCompatActivity {

    private static final String TAG = NovaContaActivity.class.getSimpleName();
    EditText username;
    EditText password;
    NovaContaActivity nc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_conta);
        nc = this;

        Button botaoCadastrar = (Button) findViewById(R.id.bCadastrar);
        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                username = (EditText) findViewById(R.id.etUsername_cadastro);
                password = (EditText) findViewById(R.id.etPassword_cadastro);

                if( username.getText().toString().length() == 0 ) {
                    username.setError( "Nome de usuário deve ser preenchido." );
                    return;
                }

                if( password.getText().toString().length() < 3 ) {
                    password.setError( "Senha deve conter mais de 3 caracteres" );
                    return;
                }

                User u = new User();
                u.setUsername(username.getText().toString());
                u.setPassword(password.getText().toString());

                ServiceAPI serviceAPI = ServiceGenerator.createService(ServiceAPI.class);

                Call<String> call = serviceAPI.createUser(u);

                Log.d(TAG, "User " + u.getUsername());
                Log.d(TAG, "Call " + call);

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.code() == 200) {
                            login();
                        } else {
                            Context context = getApplicationContext();
                            CharSequence text = "Nome de Usuário já cadastrado.";
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.e(TAG, "onFailure: ", t);
                    }
                });

            }
        });
    }


    private void login() {
        ServiceAPI serviceAPI = ServiceGenerator.createService(ServiceAPI.class);
        String authToken = Credentials.basic(username.getText().toString(), password.getText().toString());

        SharedPreferences gameSettings = getSharedPreferences("myPreferences", MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = gameSettings.edit();
        prefEditor.putString("token", authToken);
        prefEditor.commit();

        Call<String> call = serviceAPI.login(getSharedPreferences("myPreferences", 0).getString("token", ""));

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if(response.code() == 200){
                    Log.d(TAG, "onResponse: " + response.body().toString());
                    Intent i = new Intent(nc, MainActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Log.e(TAG, "onResponse: Usuário não autenticado.");
                    Context context = getApplicationContext();
                    CharSequence text = "Usuário ou senha inválidos";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }
}
