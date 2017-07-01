package ar.com.fiuba.tddp1.gestorvida;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by User on 01/07/2017.
 */

class ContactoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView nombreContacto;
    public ImageView fotoPerfil;

    public ContactoViewHolder(View contactoView) {
        super(contactoView);
        this.fotoPerfil = (ImageView) contactoView.findViewById(R.id.imageViewFotoPerfil);
        this.nombreContacto = (TextView) contactoView.findViewById(R.id.textViewNombreContacto);
        //TODO: hacer que al clickear a uno se agregue, la otra es que tenga un checkbox
    }

    @Override
    public void onClick(View v) {
        //Si se le hace click al usuario
    }
}
