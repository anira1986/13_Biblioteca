/**
 *@author:<ANA PAULA DE OLIVEIRA SILVA>
 *RA1110482123028
 *ANA PAULA DE OLIVEIRA SILVA
 */

package br.edu.fateczl.biblioteca.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import br.edu.fateczl.biblioteca.R;

public class PaginaInicialFragment extends Fragment {

    public PaginaInicialFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pagina_inicial, container, false);



        TextView textViewTituloApp = view.findViewById(R.id.textViewTituloApp);
        TextView textViewDescricaoApp = view.findViewById(R.id.textViewDescricaoApp);

        return view;
    }
}
