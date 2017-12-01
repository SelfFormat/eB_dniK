package com.veriqus.savoirvivre;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class LearningFragment extends Fragment {

    public LearningFragment(){}

    OnHeadlineSelectedListener mCallback;
    View rootView;
    Spinner spinner;

    // Container Activity must implement this interface
    public interface OnHeadlineSelectedListener {
        public void onSubCategorySelected(String name, String mode);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.category_list, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();


        Resources r = getResources();

        List<Category> subCat_1 = new ArrayList<>();
        subCat_1.add(0, new Category("1", getContext().getString(R.string.subCat1_1_phone), R.drawable.icon_phone));
        subCat_1.add(1, new Category("2", getContext().getString(R.string.subCat1_2_mail), R.drawable.icon_email));
        subCat_1.add(2, new Category("3", getContext().getString(R.string.subCat1_3_socialmedia), R.drawable.icon_socialmedia));

        List<Category> subCat_2 = new ArrayList<>();
        subCat_2.add(0, new Category("1", getContext().getString(R.string.subCat2_1_restaurant), R.drawable.icon_restaurant));
        subCat_2.add(1, new Category("2", getContext().getString(R.string.subCat2_2_home), R.drawable.icon_home));
        subCat_2.add(2, new Category("3", getContext().getString(R.string.subCat2_3_servingalcohol), R.drawable.icon_servingalcohol));

        List<Category> subCat_3 = new ArrayList<>();
        subCat_3.add(0, new Category("1", getContext().getString(R.string.subCat3_1_appearance), R.drawable.icon_appearance));
        subCat_3.add(1, new Category("2", getContext().getString(R.string.subCat3_2_gifts), R.drawable.icon_gifts));
        subCat_3.add(2, new Category("3", getContext().getString(R.string.subCat3_3_greetings), R.drawable.icon_greetings));
        subCat_3.add(3, new Category("4", getContext().getString(R.string.subCat3_4_guests), R.drawable.icon_guests));
        subCat_3.add(4, new Category("5", getContext().getString(R.string.subCat3_5_dance), R.drawable.icon_dance));
        subCat_3.add(5, new Category("6", getContext().getString(R.string.subCat3_6_date), R.drawable.icon_date));
        subCat_3.add(6, new Category("7", getContext().getString(R.string.subCat3_7_job), R.drawable.icon_job));
        subCat_3.add(7, new Category("8", getContext().getString(R.string.subCat3_8_disabled), R.drawable.icon_disabled));
        subCat_3.add(8, new Category("9", getContext().getString(R.string.subCat3_10_conversation), R.drawable.icon_conversation));
        subCat_3.add(9, new Category("10", getContext().getString(R.string.subCat3_9_cigarettes), R.drawable.icon_cigarettes));

        List<Category> subCat_4 = new ArrayList<>();
        subCat_4.add(0, new Category("1", getContext().getString(R.string.subCat4_2_wedding), R.drawable.icon_wedding));
        subCat_4.add(1, new Category("2", getContext().getString(R.string.subCat4_3_funeral), R.drawable.icon_funeral));

        List<Category> subCat_5 = new ArrayList<>();
        subCat_5.add(0, new Category("1", getContext().getString(R.string.subCat5_1_religion), R.drawable.icon_religion));
        subCat_5.add(1, new Category("2", getContext().getString(R.string.subCat5_2_shop), R.drawable.icon_shopping));
        subCat_5.add(2, new Category("3", getContext().getString(R.string.subCat5_3_gym), R.drawable.icon_gym));
        subCat_5.add(3, new Category("4", getContext().getString(R.string.subCat5_4_sauna), R.drawable.icon_sauna));
        subCat_5.add(4, new Category("5", getContext().getString(R.string.subCat5_5_events), R.drawable.icon_events));
        subCat_5.add(5, new Category("6", getContext().getString(R.string.subCat5_6_publictransport), R.drawable.icon_publictransport));
        subCat_5.add(6, new Category("7", getContext().getString(R.string.subCat5_7_journey), R.drawable.icon_journey));
        subCat_5.add(7, new Category("8", getContext().getString(R.string.subCat5_8_hospital), R.drawable.icon_hospital));
        subCat_5.add(4, new Category("9", getContext().getString(R.string.subCat5_8_swimmingpool), R.drawable.icon_swimmingpool));

        List<Category> subCat_6 = new ArrayList<>();
        subCat_6.add(0, new Category("1", getContext().getString(R.string.subCat6_1_dictionary), R.drawable.icon_dictionary));
        subCat_6.add(1, new Category("2", getContext().getString(R.string.subCat6_2_animals), R.drawable.icon_animals));
        subCat_6.add(2, new Category("3", getContext().getString(R.string.subCat6_3_children), R.drawable.icon_children));
        subCat_6.add(3, new Category("4", getContext().getString(R.string.subCat6_4_others), R.drawable.icon_others));

        List<Category> subCat_7 = new ArrayList<>();
        subCat_7.add(0, new Category("1", getContext().getString(R.string.subCat7_1_punctuation), R.drawable.icon_punctuation));


        final List<Category> categories = new ArrayList<>();
        categories.add(0, new Category("1", getContext().getString(R.string.category_1), subCat_1, R.string.category_1));
        categories.add(1, new Category("2", getContext().getString(R.string.category_2), subCat_2, R.string.category_2));
        categories.add(2, new Category("3", getContext().getString(R.string.category_3), subCat_3, R.string.category_3));
        categories.add(3, new Category("5", getContext().getString(R.string.category_5), subCat_5, R.string.category_5));
        categories.add(4, new Category("6", getContext().getString(R.string.category_6), subCat_6, R.string.category_6));
        categories.add(5, new Category("4", getContext().getString(R.string.category_4), subCat_4, R.string.category_4));
        categories.add(6, new Category("7", getContext().getString(R.string.category_7), subCat_7, R.string.category_7));



        Spinner spinner = (Spinner) rootView.findViewById(R.id.categoryListSpinner);
        final ImageView categoryImage = (ImageView) rootView.findViewById(R.id.imageCategorySwap);
        final CardView showCategoryCardView = (CardView) rootView.findViewById(R.id.selectImageCategory);
        final ImageView learningImage = (ImageView) rootView.findViewById(R.id.imagLearning);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0: showCategoryCardView.setVisibility(View.INVISIBLE);
                            learningImage.setVisibility(View.VISIBLE);
                            break;
                    case 1: categoryImage.setImageDrawable(getResources().getDrawable(R.drawable.icon_restaurant));
                            showCategoryCardView.setVisibility(View.VISIBLE);
                            learningImage.setVisibility(View.INVISIBLE);
                            break;
                    case 2: categoryImage.setImageDrawable(getResources().getDrawable(R.drawable.icon_hospital));
                            showCategoryCardView.setVisibility(View.VISIBLE);
                            learningImage.setVisibility(View.INVISIBLE);
                            break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        return rootView;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }



}
