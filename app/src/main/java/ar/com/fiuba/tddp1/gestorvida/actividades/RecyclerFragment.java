package ar.com.fiuba.tddp1.gestorvida.actividades;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ar.com.fiuba.tddp1.gestorvida.R;


public abstract class RecyclerFragment extends Fragment implements View.OnClickListener /*implements AdapterView.OnItemClickListener */ {

    private RecyclerView mList;
    private RecyclerView.Adapter mAdapter;

    /** Required Overrides for Sample Fragments */

    protected abstract RecyclerView.LayoutManager getLayoutManager();
    //protected abstract RecyclerView.ItemDecoration getItemDecoration();
    //protected abstract int getDefaultItemCount();
    //protected abstract RecyclerView.Adapter getAdapter();
    protected abstract void configureAdapter();

    public RecyclerView.Adapter getAdapter() {
        return mAdapter;
    }

    public void setConfiguredAdapter(RecyclerView.Adapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recycler, container, false);


        FloatingActionButton fabAgregarElemento = (FloatingActionButton) rootView.findViewById(R.id.fabAgregarElemento);

        fabAgregarElemento.setOnClickListener(this);

        /*
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
        */

        mList = (RecyclerView) rootView.findViewById(R.id.section_list);
        mList.setLayoutManager(getLayoutManager());
        mList.addItemDecoration(getItemDecoration());

        mList.getItemAnimator().setAddDuration(1000);
        mList.getItemAnimator().setChangeDuration(1000);
        mList.getItemAnimator().setMoveDuration(1000);
        mList.getItemAnimator().setRemoveDuration(1000);

        //Esto agrega la linea separadora entre items
        DividerItemDecoration divisor = new DividerItemDecoration(mList.getContext(), DividerItemDecoration.VERTICAL);
        //Si se quisiera usar una imagen como separador
        //divisor.setDrawable(this.getActivity().getDrawable(R.drawable.mercurio));
        mList.addItemDecoration(divisor);


        /*
        mAdapter = getAdapter();
        mAdapter.setItemCount(getDefaultItemCount());
        mAdapter.setOnItemClickListener(this);
        */
        configureAdapter();
        mList.setAdapter(getAdapter());

        return rootView;
    }

    @Override
    public void onClick(View v) {
        this.goToAgregarElemento();
    }

    protected abstract void goToAgregarElemento();

    public RecyclerView.ItemDecoration getItemDecoration() {
        return new InserDecoration(getActivity());
    }

    /*@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.grid_options, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        NumberPickerDialog dialog;
        switch (item.getItemId()) {
            case R.id.action_add:
                dialog = new NumberPickerDialog(getActivity());
                dialog.setTitle("Position to Add");
                dialog.setPickerRange(0, mAdapter.getItemCount());
                dialog.setOnNumberSelectedListener(new NumberPickerDialog.OnNumberSelectedListener() {
                    @Override
                    public void onNumberSelected(int value) {
                        mAdapter.addItem(value);
                    }
                });
                dialog.show();

                return true;
            case R.id.action_remove:
                dialog = new NumberPickerDialog(getActivity());
                dialog.setTitle("Position to Remove");
                dialog.setPickerRange(0, mAdapter.getItemCount()-1);
                dialog.setOnNumberSelectedListener(new NumberPickerDialog.OnNumberSelectedListener() {
                    @Override
                    public void onNumberSelected(int value) {
                        mAdapter.removeItem(value);
                    }
                });
                dialog.show();

                return true;
            case R.id.action_empty:
                mAdapter.setItemCount(0);
                return true;
            case R.id.action_small:
                mAdapter.setItemCount(5);
                return true;
            case R.id.action_medium:
                mAdapter.setItemCount(25);
                return true;
            case R.id.action_large:
                mAdapter.setItemCount(196);
                return true;
            case R.id.action_scroll_zero:
                mList.scrollToPosition(0);
                return true;
            case R.id.action_smooth_zero:
                mList.smoothScrollToPosition(0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

    /*@Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(),
                "Clicked: " + position + ", index " + mList.indexOfChild(view),
                Toast.LENGTH_SHORT).show();
    }*/
}
