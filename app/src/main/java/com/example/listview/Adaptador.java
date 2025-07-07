package com.example.listview;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Adaptador  extends ArrayAdapter<Usuario> {

    public Adaptador(Context context, ArrayList <Usuario> datos) {
        super(context, R.layout.ly_item, datos); // ly debe de ser el layout del item
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.ly_item, null);

        TextView lblTitulo = (TextView)item.findViewById(R.id.lblNombre);
        lblTitulo.setText(getItem(position).getNombres());

        TextView lblSubtitulo = (TextView)item.findViewById(R.id.lblEmail);
        lblSubtitulo.setText(getItem(position).getEmail());

        TextView lblweb = (TextView)item.findViewById(R.id.lblNombre);
        lblSubtitulo.setText(getItem(position).getWebsite());

        ImageView imageView = (ImageView)item.findViewById(R.id.imgUsr);
        Glide.with(this.getContext())
                .load(getItem(position).getUrlavatar())
                .into(imageView);
        return(item);

    }
}
