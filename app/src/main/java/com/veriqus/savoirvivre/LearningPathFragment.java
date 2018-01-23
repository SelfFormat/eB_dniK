package com.veriqus.savoirvivre;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

    private static final String TAG = "Saved Cat: ";
    View rootView;
    SubCatListAdapter.OnLearningSubSelected mCallback;
    Context context;
    boolean isDone;

    final List<SubClass> category1 = new ArrayList<>();
    final List<SubClass> category2 = new ArrayList<>();
    final List<SubClass> category3 = new ArrayList<>();
    final List<SubClass> category4 = new ArrayList<>();
    final List<SubClass> category5 = new ArrayList<>();
    final List<SubClass> category6 = new ArrayList<>();
    final List<SubClass> category7 = new ArrayList<>();

    ArrayList<String> completedCategories = new ArrayList<>();
    TinyDB completedCategoriesDatabase;

    public LearningPathFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_learning_path, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        completedCategoriesDatabase = new TinyDB(getContext());



        completedCategories = completedCategoriesDatabase.getListString("COMPLETED_CATEGORIES");
        for (int i = 0; i < completedCategories.size(); i++) {
            Log.i(TAG, completedCategories.get(i));
        }


        Bundle bundle = getArguments();
        final int category = bundle.getInt("CATEGORY");
        generateSubCategories(category);
        restoreFromSaved(category);
        String subCategoryFromBundle = bundle.getString("SUB_CATEGORY");
        Log.i("Subcat recevied:", subCategoryFromBundle+"");
        if(subCategoryFromBundle != null) {
            String recivedSubCatName = ((MainActivity)getActivity()).getSubCategoryNameByID(subCategoryFromBundle);
            for (int i = 0; i < chooseCategory(category).size(); i++) {
                if (recivedSubCatName.equals(chooseCategory(category).get(i).getName())){
                    Toast.makeText(getContext(), subCategoryFromBundle, Toast.LENGTH_SHORT).show();
                    chooseCategory(category).get(i).setDone();
                    completedCategories.add(recivedSubCatName);
                    completedCategoriesDatabase.putListString("COMPLETED_CATEGORIES", completedCategories);

                }
            }
        }

        RecyclerView rc = (RecyclerView) rootView.findViewById(R.id.rvSub);
        SubCatListAdapter adapter = new SubCatListAdapter(getContext(), chooseCategory(category));
        adapter.setOnItemClickListener(new SubCatListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                String categoryID = ((MainActivity)getActivity()).getSubCategoryIDByName(chooseCategory(category).get(position).getName());
                //Toast.makeText(getContext(), chooseCategory(category).get(position).getName(), Toast.LENGTH_SHORT).show();
                mCallback.onLearningSubSelected(categoryID, category);
            }
        });
        rc.setAdapter(adapter);
        rc.setLayoutManager(new LinearLayoutManager(getContext()));

        TextView categoryTextView = (TextView) rootView.findViewById(R.id.category_text);
        categoryTextView.setText(getString(category));

        return rootView;
    }


    private void restoreFromSaved(int category){
        if(!completedCategories.isEmpty() && chooseCategory(category) != null) {
            for (int i = 0; i < completedCategories.size(); i++) {
                for (int j = 0; j < chooseCategory(category).size(); j++) {
                    if (completedCategories.get(i).equals(chooseCategory(category).get(j).getName())) {
                        chooseCategory(category).get(j).setDone();
                    }
                }
            }
        }
    }

    private List<SubClass> chooseCategory(int category) {
        switch (category) {
            case R.string.category_1:
                return category1;
            case R.string.category_2:
                return category2;
            case R.string.category_3:
                return category3;
            case R.string.category_4:
                return category4;
            case R.string.category_5:
                return category5;
            case R.string.category_6:
                return category6;
            case R.string.category_7:
                return category7;
        }
        return null;
    }

    private void generateSubCategories(int category){
        switch (category){
            case R.string.category_1:
                category1.add(new SubClass(getString(R.string.cat1_textmessages)));
                category1.add(new SubClass(getString(R.string.cat1_answeringphone)));
                category1.add(new SubClass(getString(R.string.cat1_phonepublic)));
                category1.add(new SubClass(getString(R.string.cat1_mailsubject)));
                category1.add(new SubClass(getString(R.string.cat1_mailintro)));
                category1.add(new SubClass(getString(R.string.cat1_socialmedia)));
                category1.add(new SubClass(getString(R.string.cat1_onlineshopping)));
                category1.add(new SubClass(getString(R.string.cat1_onlinecomments)));
                category1.add(new SubClass(getString(R.string.cat1_esport)));
                break;
            case R.string.category_2:
                category2.add(new SubClass("Szwedzki stół"));
                category2.add(new SubClass("Kontakt z kelnerem"));
                category2.add(new SubClass("Napiwki"));
                category2.add(new SubClass("Zastawa"));
                category2.add(new SubClass("Sztućce"));
                category2.add(new SubClass("Serwowanie alkoholi"));
                break;
            case R.string.category_3:
                category3.add(new SubClass("Wygląd i prezencja"));
                category3.add(new SubClass("Powitania"));
                category3.add(new SubClass("Pożegnania"));
                category3.add(new SubClass("Precedencja"));
                category3.add(new SubClass("Tytułowanie"));
                category3.add(new SubClass("Przedstawianie"));
                category3.add(new SubClass("Sąsiedzi i współlokatorzy"));
                category3.add(new SubClass("Randka"));
                category3.add(new SubClass("Kwiaty"));
                category3.add(new SubClass("Prezenty"));
                category3.add(new SubClass("Rozmowy biznesowe"));
                category3.add(new SubClass("Rozmowy z nieznajomymi"));
                category3.add(new SubClass("Taniec"));
                category3.add(new SubClass("Niepełnosprawni"));
                category3.add(new SubClass("Goście w domu"));
                category3.add(new SubClass("Zaproszenia"));
                category3.add(new SubClass("Papierosy"));
                break;
            case R.string.category_4:
                category4.add(new SubClass("Ślub i wesele"));
                category4.add(new SubClass("Pogrzeb i stypa"));
                category4.add(new SubClass("Uroczystości rodzinne - rocznice, urodziny, imieniny"));
                break;
            case R.string.category_5:
                category5.add(new SubClass("Sklep"));
                category5.add(new SubClass("Winda"));
                category5.add(new SubClass("Hotel"));
                category5.add(new SubClass("Szpital, przychodnia"));
                category5.add(new SubClass("Plaża"));
                category5.add(new SubClass("Pasażer"));
                category5.add(new SubClass("Kierowca"));
                category5.add(new SubClass("Na statku"));
                category5.add(new SubClass("W samolocie"));
                category5.add(new SubClass("Za granicą"));
                category5.add(new SubClass("Siłownia"));
                category5.add(new SubClass("Sauna"));
                category5.add(new SubClass("Miejsca religijne"));
                category5.add(new SubClass("Teatr, opera, kino, koncert"));
                category5.add(new SubClass("Biblioteka i muzeum"));
                break;
            case R.string.category_6:
                category6.add(new SubClass("Podstawy etykiety"));
                category6.add(new SubClass("O savoir-vivrze"));
                category6.add(new SubClass("Dla rodzica"));
                category6.add(new SubClass("Zwierzęta"));
                category6.add(new SubClass("Sport"));
                category6.add(new SubClass("Recykling"));
                break;
            case R.string.category_7:
                category7.add(new SubClass("Najczęstsze błędy językowe"));
                category7.add(new SubClass("Stosowanie interpunkcji"));
                break;
        }
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
