package com.veriqus.savoirvivre;


import android.app.Activity;
import android.content.SharedPreferences;
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
    boolean isDone;
    final List<SubClass> subClassList = new ArrayList<>();


    public LearningPathFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_learning_path, container, false);

        SharedPreferences settingsIntro = getContext().getSharedPreferences(ArticleFragment.ca1, 0);
        isDone = settingsIntro.getBoolean(ArticleFragment.ca1, false);


        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        Bundle bundle = getArguments();
        int category = bundle.getInt("CATEGORY");

        generateSubCategories(category);

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

    private void generateSubCategories(int category){
        switch (category){
            case R.string.category_1:
                subClassList.add(new SubClass("Pisanie wiadomości tekstowych", isDone));
                subClassList.add(new SubClass("Odbieranie telefonów"));
                subClassList.add(new SubClass("Używanie telefonu w miejscach publicznych"));
                subClassList.add(new SubClass("Początek maila"));
                subClassList.add(new SubClass("Rozwinięcie maila"));
                subClassList.add(new SubClass("Zakończenie maila"));
                subClassList.add(new SubClass("Media społecznościowe"));
                subClassList.add(new SubClass("Sklepy i aukcje internetowe"));
                subClassList.add(new SubClass("Komentarze w internecie"));
                subClassList.add(new SubClass("eSport"));
                break;
            case R.string.category_2:
                subClassList.add(new SubClass("Szwedzki stół"));
                subClassList.add(new SubClass("Kontakt z kelnerem"));
                subClassList.add(new SubClass("Napiwki"));
                subClassList.add(new SubClass("Zastawa"));
                subClassList.add(new SubClass("Sztućce"));
                subClassList.add(new SubClass("Serwowanie alkoholi"));
                break;
            case R.string.category_3:
                subClassList.add(new SubClass("Wygląd i prezencja"));
                subClassList.add(new SubClass("Powitania"));
                subClassList.add(new SubClass("Pożegnania"));
                subClassList.add(new SubClass("Precedencja"));
                subClassList.add(new SubClass("Tytułowanie"));
                subClassList.add(new SubClass("Przedstawianie"));
                subClassList.add(new SubClass("Sąsiedzi i współlokatorzy"));
                subClassList.add(new SubClass("Randka"));
                subClassList.add(new SubClass("Kwiaty"));
                subClassList.add(new SubClass("Prezenty"));
                subClassList.add(new SubClass("Rozmowy biznesowe"));
                subClassList.add(new SubClass("Rozmowy z nieznajomymi"));
                subClassList.add(new SubClass("Taniec"));
                subClassList.add(new SubClass("Niepełnosprawni"));
                subClassList.add(new SubClass("Goście w domu"));
                subClassList.add(new SubClass("Zaproszenia"));
                subClassList.add(new SubClass("Papierosy"));
                break;
            case R.string.category_4:
                subClassList.add(new SubClass("Ślub i wesele"));
                subClassList.add(new SubClass("Pogrzeb i stypa"));
                subClassList.add(new SubClass("Uroczystości rodzinne - rocznice, urodziny, imieniny"));
                break;
            case R.string.category_5:
                subClassList.add(new SubClass("Sklep"));
                subClassList.add(new SubClass("Winda"));
                subClassList.add(new SubClass("Hotel"));
                subClassList.add(new SubClass("Szpital, przychodnia"));
                subClassList.add(new SubClass("Plaża"));
                subClassList.add(new SubClass("Pasażer"));
                subClassList.add(new SubClass("Kierowca"));
                subClassList.add(new SubClass("Na statku"));
                subClassList.add(new SubClass("W samolocie"));
                subClassList.add(new SubClass("Za granicą"));
                subClassList.add(new SubClass("Siłownia"));
                subClassList.add(new SubClass("Sauna"));
                subClassList.add(new SubClass("Miejsca religijne"));
                subClassList.add(new SubClass("Teatr, opera, kino, koncert"));
                subClassList.add(new SubClass("Biblioteka i muzeum"));
                break;
            case R.string.category_6:
                subClassList.add(new SubClass("Podstawy etykiety"));
                subClassList.add(new SubClass("O savoir-vivrze"));
                subClassList.add(new SubClass("Dla rodzica"));
                subClassList.add(new SubClass("Zwierzęta"));
                subClassList.add(new SubClass("Sport"));
                subClassList.add(new SubClass("Recykling"));
                break;
            case R.string.category_7:
                subClassList.add(new SubClass("Najczęstsze błędy językowe"));
                subClassList.add(new SubClass("Stosowanie interpunkcji"));
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
