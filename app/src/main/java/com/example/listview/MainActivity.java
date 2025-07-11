package com.example.listview;

import android.content.Intent; // <--- IMPORTANTE
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView; // <--- IMPORTANTE
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.listview.WebServices.Asynchtask;
import com.example.listview.WebServices.WebService;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements Asynchtask {

    private ListView lstUsuarios; // Mueve la declaración aquí para que sea accesible
    private Adaptador adaptadorUsuario; // Guarda una referencia al adaptador
    private ArrayList<Usuario> listaActualDeProductos; // Guarda la lista de productos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lstUsuarios = findViewById(R.id.List); // Asignar aquí
        listaActualDeProductos = new ArrayList<>(); // Inicializar la lista
        adaptadorUsuario = new Adaptador(this, listaActualDeProductos); // Crear adaptador con lista vacía
        lstUsuarios.setAdapter(adaptadorUsuario); // Establecer adaptador una vez

        // Configurar el OnItemClickListener para el ListView
        lstUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Obtener el producto seleccionado de la lista actual
                Usuario productoSeleccionado = listaActualDeProductos.get(position);

                if (productoSeleccionado != null) {
                    // Crear un Intent para iniciar DetalleProductoActivity
                    Intent intent = new Intent(MainActivity.this, DetalleProductoActivity.class);

                    // Poner el objeto Usuario completo como extra (porque es Serializable)
                    intent.putExtra("PRODUCTO_SELECCIONADO", productoSeleccionado);

                    // Iniciar la nueva actividad
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "No se pudo obtener el producto.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Llamar al WebService
        Map<String, String> datos = new HashMap<String, String>();
        WebService ws = new WebService("https://fakestoreapi.com/products",
                datos, MainActivity.this, MainActivity.this);
        ws.execute("GET");
    }

    @Override
    public void processFinish(String result) {
        Log.d("MainActivity_DEBUG", "Respuesta cruda de la API: " + result);
        try {
            JSONArray jsonArrayProductos = new JSONArray(result);
            Log.d("MainActivity_DEBUG", "JSONArray tiene " + jsonArrayProductos.length() + " elementos.");

            ArrayList<Usuario> nuevosProductos = Usuario.JsonObjectsBuild(jsonArrayProductos);
            Log.d("MainActivity_DEBUG", "nuevosProductos procesada tiene " + nuevosProductos.size() + " elementos.");

            // Actualizar la lista del adaptador en lugar de crear uno nuevo
            listaActualDeProductos.clear();
            listaActualDeProductos.addAll(nuevosProductos);
            adaptadorUsuario.notifyDataSetChanged(); // Notificar al adaptador que los datos cambiaron

            if (listaActualDeProductos.isEmpty() && jsonArrayProductos.length() > 0) {
                Log.w("MainActivity_DEBUG", "¡Alerta! JsonObjectsBuild devolvió una lista vacía pero el JSONArray original no lo estaba.");
                Toast.makeText(this, "No se pudieron procesar los datos de los productos.", Toast.LENGTH_LONG).show();
            } else if (listaActualDeProductos.isEmpty()) {
                Toast.makeText(this, "No se encontraron productos.", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            Log.e("MainActivity_DEBUG", "Error CRÍTICO al parsear el JSONArray principal: " + e.getMessage(), e);
            Toast.makeText(this, "Error al obtener datos: Respuesta del servidor no válida.", Toast.LENGTH_LONG).show();
        }
    }
}
