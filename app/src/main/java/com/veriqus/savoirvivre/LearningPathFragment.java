package com.veriqus.savoirvivre;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class LearningPathFragment extends Fragment {

    View rootView;
    SubCatListAdapter.OnLearningSubSelected mCallback;


    public LearningPathFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_learning_path, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        Bundle bundle = getArguments();
        int category = bundle.getInt("CATEGORY");

        final List<SubClass> subClassList = new ArrayList<>();
        switch (category){
            case R.string.category_1:
                subClassList.add(new SubClass("Pisanie wiadomości", R.drawable.ic_favorite_black_36dp));
                subClassList.add(new SubClass("Odbieranie telefonów", R.drawable.ic_favorite_black_36dp));
                subClassList.add(new SubClass("Używanie telefonu w miejscach publicznych", R.drawable.ic_favorite_black_36dp));
                subClassList.add(new SubClass("Początek maila", R.drawable.ic_favorite_black_36dp));
                subClassList.add(new SubClass("Rozwinięcie maila", R.drawable.ic_favorite_black_36dp));
                subClassList.add(new SubClass("Zakończenie maila", R.drawable.ic_favorite_black_36dp));
                subClassList.add(new SubClass("Sklepy i aukcje internetowe", R.drawable.ic_favorite_black_36dp));
                subClassList.add(new SubClass("Komentarze w internecie", R.drawable.ic_favorite_black_36dp));
                break;
            case R.string.category_2:
                break;
            case R.string.category_3:
                break;
            case R.string.category_4:
                break;
            case R.string.category_5:
                break;
            case R.string.category_6:
                break;
            case R.string.category_7:
                break;
        }


        RecyclerView rc = (RecyclerView) rootView.findViewById(R.id.rvSub);
        SubCatListAdapter adapter = new SubCatListAdapter(getContext(), subClassList);
        adapter.setOnItemClickListener(new SubCatListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Toast.makeText(getContext(), subClassList.get(position).getName(), Toast.LENGTH_SHORT).show();
                mCallback.onLearningSubSelected("subCat1_1_phone");
            }
        });
        rc.setAdapter(adapter);
        rc.setLayoutManager(new LinearLayoutManager(getContext()));


        TextView categoryTextView = (TextView) rootView.findViewById(R.id.category_text);
        categoryTextView.setText(getString(category));


        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (SubCatListAdapter.OnLearningSubSelected) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnLearningSubSelected");
        }
    }

}
