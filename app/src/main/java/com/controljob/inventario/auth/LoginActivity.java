package com.controljob.inventario.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.controljob.inventario.MainActivity;
import com.controljob.inventario.R;
import com.controljob.inventario.inventario_modulo.ApiService;
import com.controljob.inventario.inventario_modulo.RetrofitClient;
import com.controljob.inventario.model.Usuario;
import com.controljob.inventario.model.RespuestaServer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsuario, etPassword;
    private Button btnIngresar, btnRegistrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 1. Conexión con la vista XML
        etUsuario = findViewById(R.id.etUsuario);
        etPassword = findViewById(R.id.etPassword);
        btnIngresar = findViewById(R.id.btnIngresar);
        btnRegistrarse = findViewById(R.id.btnRegistrarse);

        // 2. Acción del Botón Azul (Ingresar)
        btnIngresar.setOnClickListener(v -> loginUsuario());

        // 3. Acción del Botón Verde (Registrarse)
        btnRegistrarse.setOnClickListener(v -> registrarUsuario());
    }

    private void loginUsuario() {
        String user = etUsuario.getText().toString().trim();
        String pass = etPassword.getText().toString().trim();

        if (user.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Por favor escribe usuario y contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = RetrofitClient.getApiService();
        // Mandamos los datos al servidor
        apiService.login(new Usuario(user, pass)).enqueue(new Callback<RespuestaServer>() {
            @Override
            public void onResponse(Call<RespuestaServer> call, Response<RespuestaServer> response) {
                // Verificamos si el servidor respondió success: true
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("Acceso")
                            .setMessage("Autenticación satisfactoria ✔️")
                            .setCancelable(false)
                            .setPositiveButton("Aceptar", (dialog, which) -> {
                                // Salto a la pantalla de Productos
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }).show();
                } else {
                    // Si el servidor mandó success: false
                    new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("Error")
                            .setMessage("Error en la autenticación ❌")
                            .setPositiveButton("Reintentar", null).show();
                }
            }

            @Override
            public void onFailure(Call<RespuestaServer> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void registrarUsuario() {
        String user = etUsuario.getText().toString().trim();
        String pass = etPassword.getText().toString().trim();

        if (user.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Escribe datos para el registro", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = RetrofitClient.getApiService();
        // Usamos el constructor de registro que creamos en Usuario.java
        apiService.registrarUsuario(new Usuario(user, pass, "usuario")).enqueue(new Callback<RespuestaServer>() {
            @Override
            public void onResponse(Call<RespuestaServer> call, Response<RespuestaServer> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("Registro")
                            .setMessage("¡Registro exitoso! Ya puedes ingresar ✔️")
                            .setPositiveButton("Aceptar", null).show();
                } else {
                    Toast.makeText(LoginActivity.this, "No se pudo registrar", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RespuestaServer> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
