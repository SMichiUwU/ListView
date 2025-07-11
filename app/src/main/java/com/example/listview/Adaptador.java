package com.example.listview;

// QUITA ESTA LÍNEA SI AÚN LA TIENES: import static java.security.AccessController.getContext;

import android.content.Context;
import android.util.Log; // Para depuración
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Adaptador extends ArrayAdapter<Usuario> {

    public Adaptador(Context context, ArrayList<Usuario> datos) {
        super(context, R.layout.ly_item, datos); // ly_item debe de ser el layout del item
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtenemos el contexto del ArrayAdapter (o del parent)
        LayoutInflater inflater = LayoutInflater.from(getContext()); // o LayoutInflater.from(parent.getContext());

        // Inflamos la vista para cada item.
        // Es mejor usar (R.layout.ly_item, parent, false) pero para mantenerlo como lo tenías:
        View item = inflater.inflate(R.layout.ly_item, null); // ADVERTENCIA: Pasar null aquí no es ideal.

        // Obtenemos el objeto Usuario para esta posición
        Usuario productoActual = getItem(position);

        if (productoActual == null) {
            // Si el producto es nulo, podrías devolver la vista vacía o un placeholder
            Log.e("Adaptador", "El producto en la posición " + position + " es nulo.");
            return item; // O manejarlo de otra forma
        }

        // Referencias a los Views de tu layout ly_item.xml
        // ¡ASEGÚRATE DE QUE ESTOS IDs COINCIDAN CON TU XML!

        // Para la CATEGORÍA (asumiendo ID lblNombre)
        TextView txtCategoria = item.findViewById(R.id.lblNombre);
        if (txtCategoria != null) {
            txtCategoria.setText(productoActual.getCategory());
        } else {
            Log.w("Adaptador", "TextView para CATEGORÍA (lblNombre) no encontrado.");
        }

        // Para el PRECIO (asumiendo ID lblEmail)
        TextView txtPrecio = item.findViewById(R.id.lblEmail);
        if (txtPrecio != null) {
            // Formato simple para el precio
            txtPrecio.setText("Precio: $" + productoActual.getPrice());
        } else {
            Log.w("Adaptador", "TextView para PRECIO (lblEmail) no encontrado.");
        }

        // Para la DESCRIPCIÓN (asumiendo ID lblWeb o el que uses)
        // *** ¡ESTE ES EL PUNTO CRÍTICO QUE CAUSABA EL PROBLEMA ANTES! ***
        // *** DEBES TENER UN TEXTVIEW DIFERENTE EN TU XML PARA LA DESCRIPCIÓN ***
        TextView txtDescripcion = item.findViewById(R.id.lblweb); // CAMBIA R.id.lblWeb AL ID CORRECTO
        if (txtDescripcion != null) {
            txtDescripcion.setText(productoActual.getDescription());
        } else {
            Log.w("Adaptador", "TextView para DESCRIPCIÓN (lblWeb o tu ID) no encontrado.");
        }

        // Para la IMAGEN (asumiendo ID imgUsr)
        ImageView imgProducto = item.findViewById(R.id.imgUsr);
        if (imgProducto != null) {
            if (productoActual.getImage() != null && !productoActual.getImage().isEmpty()) {
                Glide.with(getContext()) // Usar getContext() del ArrayAdapter
                        .load(productoActual.getImage())
                        .placeholder(R.drawable.ic_launcher_background) // Opcional
                        .error(R.drawable.ic_launcher_foreground)       // Opcional
                        .into(imgProducto);
            } else {
                // Opcional: limpiar la imagen si no hay URL
                imgProducto.setImageDrawable(null);
                Log.w("Adaptador", "URL de imagen vacía o nula para: " + productoActual.getCategory());
            }
        } else {
            Log.w("Adaptador", "ImageView (imgUsr) no encontrado.");
        }

        return item;
    }
}

