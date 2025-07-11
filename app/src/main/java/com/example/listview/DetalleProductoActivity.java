package com.example.listview; // Asegúrate de que sea tu paquete

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class DetalleProductoActivity extends AppCompatActivity {

    private ImageView imgDetalleProducto;
    private TextView txtDetalleTitulo;
    private TextView txtDetalleCategoria;
    private TextView txtDetallePrecio;
    private TextView txtDetalleDescripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalle_producto); // Usa tu nuevo layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainDetalle), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar los Views del layout
        imgDetalleProducto = findViewById(R.id.imgDetalleProducto);
        txtDetalleTitulo = findViewById(R.id.txtDetalleTitulo);
        txtDetalleCategoria = findViewById(R.id.txtDetalleCategoria);
        txtDetallePrecio = findViewById(R.id.txtDetallePrecio);
        txtDetalleDescripcion = findViewById(R.id.txtDetalleDescripcion);

        // Obtener el Intent que inició esta actividad
        Intent intent = getIntent();

        // Comprobar si el Intent tiene el extra "PRODUCTO_SELECCIONADO"
        if (intent != null && intent.hasExtra("PRODUCTO_SELECCIONADO")) {
            // Obtener el objeto Usuario del Intent
            // Es importante hacer el cast a (Usuario) y que Usuario sea Serializable
            Usuario producto = (Usuario) intent.getSerializableExtra("PRODUCTO_SELECCIONADO");

            if (producto != null) {
                // Poblar los Views con los datos del producto
                if (getSupportActionBar() != null) { // Para poner el título en la ActionBar
                    getSupportActionBar().setTitle(producto.getTitle());
                }

                txtDetalleTitulo.setText(producto.getTitle());
                txtDetalleCategoria.setText("Categoría: " + producto.getCategory());
                txtDetallePrecio.setText("Precio: $" + producto.getPrice());
                txtDetalleDescripcion.setText(producto.getDescription());

                if (producto.getImage() != null && !producto.getImage().isEmpty()) {
                    Glide.with(this)
                            .load(producto.getImage())
                            .placeholder(R.drawable.ic_launcher_background) // Opcional
                            .error(R.drawable.ic_launcher_foreground)       // Opcional
                            .into(imgDetalleProducto);
                }
            } else {
                Toast.makeText(this, "No se pudieron cargar los detalles del producto.", Toast.LENGTH_LONG).show();
                // Opcional: finalizar la actividad si no hay datos
                // finish();
            }
        } else {
            Toast.makeText(this, "No se recibieron datos del producto.", Toast.LENGTH_LONG).show();
            // Opcional: finalizar la actividad si no hay datos
            // finish();
        }
    }
}
