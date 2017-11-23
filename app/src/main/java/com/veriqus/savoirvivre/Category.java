package com.veriqus.savoirvivre;

import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Category {

    private String id;
    private String name;
    private List<Category> subCategories;
    private int drawable;

    public Category(String id, String name, List<Category> subCategories) {
        this.id = id;
        this.name = name;
        this.subCategories = subCategories;
    }

    public Category(String id, String name, int drawable) {
        this.id = id;
        this.name = name;
        this.drawable = drawable;
        this.subCategories = new List<Category>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @NonNull
            @Override
            public Iterator<Category> iterator() {
                return null;
            }

            @NonNull
            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @NonNull
            @Override
            public <T> T[] toArray(@NonNull T[] a) {
                return null;
            }

            @Override
            public boolean add(Category category) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(@NonNull Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(@NonNull Collection<? extends Category> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, @NonNull Collection<? extends Category> c) {
                return false;
            }

            @Override
            public boolean removeAll(@NonNull Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(@NonNull Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public Category get(int index) {
                return null;
            }

            @Override
            public Category set(int index, Category element) {
                return null;
            }

            @Override
            public void add(int index, Category element) {

            }

            @Override
            public Category remove(int index) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @Override
            public ListIterator<Category> listIterator() {
                return null;
            }

            @NonNull
            @Override
            public ListIterator<Category> listIterator(int index) {
                return null;
            }

            @NonNull
            @Override
            public List<Category> subList(int fromIndex, int toIndex) {
                return null;
            }
        };

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Category> getSubCategories() {
        return subCategories;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public void setSubCategories(List<Category> subCategories) {
        this.subCategories = subCategories;
    }
}