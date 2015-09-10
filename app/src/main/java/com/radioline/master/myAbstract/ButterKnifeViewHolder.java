package com.radioline.master.myAbstract;

import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by master on 08.09.2015.
 */
public abstract class ButterKnifeViewHolder {


    public ButterKnifeViewHolder() {
    }


    public ButterKnifeViewHolder(View view) {
        //ButterKnife.inject(this, view);
        ButterKnife.bind(this, view);
    }


    public void reset() {
       // ButterKnife.reset(this);
        ButterKnife.unbind(this);
    }
}
