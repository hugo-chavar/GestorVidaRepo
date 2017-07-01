package ar.com.fiuba.tddp1.gestorvida.contactos;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Set;

import ar.com.fiuba.tddp1.gestorvida.R;
import ar.com.fiuba.tddp1.gestorvida.dominio.Contacto;

/**
 * Created by User on 01/07/2017.
 */

class ContactoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    public View contactoView;
    public TextView nombreContacto;
    public ImageView fotoPerfil;
    public Contacto contacto;

    private Set<Contacto> participantesAgregados;

    public ContactoViewHolder(View contactoView, Set<Contacto> participantesAgregados) {
        super(contactoView);

        this.contactoView = contactoView;
        this.fotoPerfil = (ImageView) contactoView.findViewById(R.id.imageViewFotoPerfil);
        this.nombreContacto = (TextView) contactoView.findViewById(R.id.textViewNombreContacto);



        this.participantesAgregados = participantesAgregados;


    }

    @Override
    public void onClick(View v) {
        if ( this.participantesAgregados.contains(this.contacto) ) {
            this.participantesAgregados.remove(this.contacto);
            this.contactoView.setBackgroundColor(Color.WHITE);
        }
        else {
            this.participantesAgregados.add(this.contacto);
            this.contactoView.setBackgroundColor(Color.RED);
        }
    }
}