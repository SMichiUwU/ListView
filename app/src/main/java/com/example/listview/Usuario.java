package com.example.listview;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.Serializable; // <--- IMPORTANTE
import java.util.ArrayList;

public class Usuario implements Serializable { // <--- IMPLEMENTAR Serializable

    // AÑADIR ESTO PARA Serializable (buena práctica)
    private static final long serialVersionUID = 1L;

    private String category;
    private String price;
    private String description;
    private String image;
    private String title;

    public Usuario(JSONObject jsonObject) {
        this.title = jsonObject.optString("title", "N/A");
        this.category = jsonObject.optString("category", "");
        this.price = jsonObject.optString("price", "0.00");
        this.description = jsonObject.optString("description", "");
        this.image = jsonObject.optString("image", "");
    }

    // Getters (como los tenías)
    public String getTitle() { return title; }
    public String getCategory() { return category; }
    public String getPrice() { return price; }
    public String getDescription() { return description; }
    public String getImage() { return image; }

    public static ArrayList<Usuario> JsonObjectsBuild(JSONArray datosJsonArray) {
        ArrayList<Usuario> listaDeItems = new ArrayList<>();
        if (datosJsonArray == null) {
            return listaDeItems;
        }
        int limite = Math.min(datosJsonArray.length(), 200);
        for (int i = 0; i < limite; i++) {
            try {
                JSONObject jsonItem = datosJsonArray.getJSONObject(i);
                if (jsonItem != null) {
                    listaDeItems.add(new Usuario(jsonItem));
                }
            } catch (JSONException e) {
                // Log.e("Usuario", "Error parseando un item del JSON: " + e.getMessage());
            }
        }
        return listaDeItems;
    }
}
