package com.interworks.inspektar.annotations.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class KeywordItemDecoration extends RecyclerView.ItemDecoration{

    private int mItemOffset;

    public KeywordItemDecoration(int itemOffset) {
        mItemOffset = itemOffset;
    }

    public KeywordItemDecoration(@NonNull Context context, @DimenRes int itemOffsetId) {
        this(context.getResources().getDimensionPixelSize(itemOffsetId));
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(0, 0, 0, 0);
//        outRect.left = mItemOffset;
//        outRect.bottom = mItemOffset;
//        outRect.top = mItemOffset;
//        outRect.right = mItemOffset;
    }
}
