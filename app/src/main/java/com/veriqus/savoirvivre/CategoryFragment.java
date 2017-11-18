package com.veriqus.savoirvivre;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {

    public CategoryFragment(){}

    OnHeadlineSelectedListener mCallback;
    View rootView;

    // Container Activity must implement this interface
    public interface OnHeadlineSelectedListener {
        public void onSubCategorySelected(String name);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_category, container, false);

        LinearLayout mainLinear = (LinearLayout) rootView.findViewById(R.id.mainCategory_LinView);

        Resources r = getResources();

        int dip1 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, r.getDisplayMetrics());
        int px20 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 20, r.getDisplayMetrics());
        int px24 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 24, r.getDisplayMetrics());
        int dip10 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, r.getDisplayMetrics());
        int dip15 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, r.getDisplayMetrics());
        int dip60 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, r.getDisplayMetrics());
        int dip100 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, r.getDisplayMetrics());
        int dip125 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 125, r.getDisplayMetrics());

        List<Category> subCat_1 = new ArrayList<>();
        subCat_1.add(0, new Category("1", getContext().getString(R.string.subCat1_1_phone), R.drawable.icon_phone));
        subCat_1.add(1, new Category("2", getContext().getString(R.string.subCat1_2_mail), R.drawable.icon_email));
        subCat_1.add(2, new Category("3", getContext().getString(R.string.subCat1_3_socialmedia), R.drawable.icon_social_media));

        List<Category> subCat_2 = new ArrayList<>();
        subCat_2.add(0, new Category("1", getContext().getString(R.string.subCat2_1_restaurant), R.drawable.icon_restaurant));
        subCat_2.add(1, new Category("2", getContext().getString(R.string.subCat2_2_home), R.drawable.icon_athome));
        subCat_2.add(2, new Category("3", getContext().getString(R.string.subCat2_3_servingalcohol), R.drawable.icon_alcohol));

        List<Category> subCat_3 = new ArrayList<>();
        subCat_3.add(0, new Category("1", getContext().getString(R.string.subCat3_1_appearance), R.drawable.icon_look));
        subCat_3.add(1, new Category("2", getContext().getString(R.string.subCat3_2_gifts), R.drawable.icon_gifts));
        subCat_3.add(2, new Category("3", getContext().getString(R.string.subCat3_3_greetings), R.drawable.icon_greetings));
        subCat_3.add(3, new Category("4", getContext().getString(R.string.subCat3_4_guests), R.drawable.icon_company));
        subCat_3.add(4, new Category("5", getContext().getString(R.string.subCat3_5_dance), R.drawable.icon_dancing));
        subCat_3.add(5, new Category("6", getContext().getString(R.string.subCat3_6_date), R.drawable.icon_date));
        subCat_3.add(6, new Category("7", getContext().getString(R.string.subCat3_7_job), R.drawable.icon_work_monster));
        subCat_3.add(7, new Category("8", getContext().getString(R.string.subCat3_8_disabled), R.drawable.icon_disability));
        subCat_3.add(8, new Category("9", getContext().getString(R.string.subCat3_10_conversation), R.drawable.oval));
        subCat_3.add(9, new Category("10", getContext().getString(R.string.subCat3_9_cigarettes), R.drawable.icon_cigarettes));

        List<Category> subCat_4 = new ArrayList<>();
        subCat_4.add(0, new Category("1", getContext().getString(R.string.subCat4_2_wedding), R.drawable.icon_wedding));
        subCat_4.add(1, new Category("2", getContext().getString(R.string.subCat4_3_funeral), R.drawable.icon_funeral));

        List<Category> subCat_5 = new ArrayList<>();
        subCat_5.add(0, new Category("1", getContext().getString(R.string.subCat5_1_religion), R.drawable.oval));
        subCat_5.add(1, new Category("2", getContext().getString(R.string.subCat5_2_shop), R.drawable.oval));
        subCat_5.add(2, new Category("3", getContext().getString(R.string.subCat5_3_gym), R.drawable.oval));
        subCat_5.add(3, new Category("4", getContext().getString(R.string.subCat5_4_sauna), R.drawable.icon_sauna));
        subCat_5.add(4, new Category("5", getContext().getString(R.string.subCat5_5_events), R.drawable.icon_artevents));
        subCat_5.add(5, new Category("6", getContext().getString(R.string.subCat5_6_publictransport), R.drawable.icon_bus));
        subCat_5.add(6, new Category("7", getContext().getString(R.string.subCat5_7_journey), R.drawable.oval));
        subCat_5.add(7, new Category("8", getContext().getString(R.string.subCat5_8_hospital), R.drawable.oval));
        subCat_5.add(4, new Category("9", getContext().getString(R.string.subCat5_8_swimmingpool), R.drawable.icon_swimmingpool));

        List<Category> subCat_6 = new ArrayList<>();
        subCat_6.add(0, new Category("1", getContext().getString(R.string.subCat6_1_dictionary), R.drawable.icon_glossary));
        subCat_6.add(1, new Category("2", getContext().getString(R.string.subCat6_2_animals), R.drawable.icon_animals));
        subCat_6.add(2, new Category("3", getContext().getString(R.string.subCat6_3_children), R.drawable.icon_kids));
        subCat_6.add(3, new Category("4", getContext().getString(R.string.subCat6_4_others), R.drawable.oval));

        List<Category> subCat_7 = new ArrayList<>();
        subCat_7.add(0, new Category("1", getContext().getString(R.string.subCat7_1_punctuation), R.drawable.oval));

        List<Category> categories = new ArrayList<>();
        categories.add(0, new Category("1", getContext().getString(R.string.category_1), subCat_1));
        categories.add(1, new Category("2", getContext().getString(R.string.category_2), subCat_2));
        categories.add(2, new Category("3", getContext().getString(R.string.category_3), subCat_3));
        categories.add(3, new Category("4", getContext().getString(R.string.category_4), subCat_4));
        categories.add(4, new Category("5", getContext().getString(R.string.category_5), subCat_5));
        categories.add(5, new Category("6", getContext().getString(R.string.category_6), subCat_6));
        categories.add(4, new Category("7", getContext().getString(R.string.category_7), subCat_7));

        for (int i = 0; i < categories.size(); i++) {

            TextView categoryText = new TextView(getContext());
            categoryText.setText(categories.get(i).getName());
            categoryText.setTextSize(px24);
            categoryText.setTextColor(Color.BLACK);
            categoryText.setTypeface(null, Typeface.NORMAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(dip15, dip15, dip15, 10);

            mainLinear.addView(categoryText, params);

            HorizontalScrollView horizonScrl = new HorizontalScrollView(getContext());
            horizonScrl.setHorizontalScrollBarEnabled(false);
            horizonScrl.setPadding(0,0, 0, dip15);
            horizonScrl.setOverScrollMode(View.OVER_SCROLL_NEVER);
            mainLinear.addView(horizonScrl);

            LinearLayout categoryItems = new LinearLayout(getContext());
            categoryItems.setOrientation(LinearLayout.HORIZONTAL);
            categoryItems.setPadding(dip15, 0, dip15, 0);
            horizonScrl.addView(categoryItems);

            for (int j = 0; j < categories.get(i).getSubCategories().size() ; j++) {

                ImageView subCategoryImage = new ImageView(getContext());
                LinearLayout.LayoutParams paramsImg = new LinearLayout.LayoutParams(dip125, dip125);
                subCategoryImage.setImageResource(categories.get(i).getSubCategories().get(j).getDrawable());


                final String subCategoryName = categories.get(i).getSubCategories().get(j).getName();

                CardView cardView = new CardView(getContext());
                //cardView.setLayoutParams(new LinearLayout.LayoutParams(dip150, dip150));
                cardView.setUseCompatPadding(true);
                cardView.setPreventCornerOverlap(false);
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCallback.onSubCategorySelected(subCategoryName);
                    }
                });
                cardView.addView(subCategoryImage, paramsImg);
                categoryItems.addView(cardView);
            }


            // Line separator:

//            View line = new View(getContext());
//            LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dip1);
//            line.setBackgroundColor(Color.parseColor("#CCCCCC"));
//            mainLinear.addView(line, lineParams);

        }

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