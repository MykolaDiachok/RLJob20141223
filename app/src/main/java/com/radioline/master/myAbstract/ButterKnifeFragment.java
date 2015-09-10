package com.radioline.master.myAbstract;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by master on 08.09.2015.
 */
public abstract class ButterKnifeFragment extends Fragment {


    private final List<ButterKnifeViewHolder> viewHolders = Collections.emptyList();


    @Nullable
    private View fragmentView;


    /**
     * @return the inflated fragment view that was created in onCreate
     *
     * @throws IllegalStateException if {@link #onCreateView} was not called
     */
    public View getFragmentView() {
        if (fragmentView!=null)
            return fragmentView;//Preconditions.checkNotNull(fragmentView, "OnCreateView must be called first");
        else
            throw new IllegalStateException("OnCreateView must be called first");
        //return null;
    }


    /**
     * Injects a child view into a parent view on the fragmentView's hierarchy. This allows a composite view to be
     * inflated and its component views referenced via view injection annotation. NOTE: All annotated views must be
     * located in the view holder and not in another class.
     *
     * @param inflater the inflater that will inflate the child view
     * @param parent the parent view that this layout should be inflated into
     * @param viewHolder the viewHolder where the injected fields will be injected into
     * @param layoutId the layout id for the child view that is to be inflated
     *
     * @return view
     */
    public View injectChild(LayoutInflater inflater, ViewGroup parent, ButterKnifeViewHolder viewHolder, int layoutId) {
        final View view = inflater.inflate(layoutId, parent, false);
        //ButterKnife.inject(viewHolder, view);
        ButterKnife.bind(viewHolder, view);
        viewHolders.add(viewHolder);
        return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        fragmentView = inflater.inflate(getFragmentLayoutId(), container, false);
        //ButterKnife.inject(this, fragmentView);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //ButterKnife.reset(this);
        ButterKnife.unbind(this);
        for (ButterKnifeViewHolder viewHolder : viewHolders) {
            viewHolder.reset();
        }
        viewHolders.clear();
        fragmentView = null;
    }


    /**
     * @return the fragment layout id
     */
    @LayoutRes
    protected abstract int getFragmentLayoutId();
}
