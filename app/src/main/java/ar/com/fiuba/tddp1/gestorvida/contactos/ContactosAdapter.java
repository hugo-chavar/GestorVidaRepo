package ar.com.fiuba.tddp1.gestorvida.contactos;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Set;

import ar.com.fiuba.tddp1.gestorvida.R;
import ar.com.fiuba.tddp1.gestorvida.dominio.Contacto;

/**
 * Created by User on 01/07/2017.
 */

class ContactosAdapter extends RecyclerView.Adapter<ContactoViewHolder> {

    private List<Contacto> listaDeContactos;

    private Set<Contacto> participantesAgregados;

    public ContactosAdapter(List<Contacto> contactos, Set<Contacto> participantesAgregados) {
        this.listaDeContactos = contactos;
        this.participantesAgregados = participantesAgregados;
    }

    @Override
    public ContactoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View contactoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacto_layout, parent, false);
        ContactoViewHolder contactoViewHolder = new ContactoViewHolder(contactoView, this.participantesAgregados);
        contactoView.setOnClickListener(contactoViewHolder);

        return contactoViewHolder;
    }



    @Override
    public void onBindViewHolder(ContactoViewHolder holder, int position) {
        Contacto contacto = this.listaDeContactos.get(position);
        holder.fotoPerfil.setImageResource(contacto.getFotoPerfilID());
        holder.nombreContacto.setText(contacto.getNombre());
        holder.contacto = contacto;
    }

    @Override
    public int getItemCount() {
        return this.listaDeContactos.size();
    }
}
