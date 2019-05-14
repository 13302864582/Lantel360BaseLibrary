package com.xiao360.baselibrary.dragMenu;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;


/***
 *
 * item拖动事件的回调接口实现
 */

public class DefaultItemCallback extends ItemTouchHelper.Callback {
    ItemTouchHelperAdapter touchHelperAdapter;

    public DefaultItemCallback(ItemTouchHelperAdapter touchHelperAdapter) {
        this.touchHelperAdapter = touchHelperAdapter;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        //允许上下左右的拖动
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        return makeMovementFlags(dragFlags, 0);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        //viewHolder:正在拖动的     target:被拖动到其上的
        //当前正在移动，执行委托回调
        touchHelperAdapter.onItemMove(viewHolder,viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        //当前选择项状态改变，执行委托回调
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE)
            touchHelperAdapter.onItemSelect(viewHolder);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        //绘制完成。执行委托回调
        if (!recyclerView.isComputingLayout())
            touchHelperAdapter.onItemClear(viewHolder);
    }


    @Override
    public boolean isLongPressDragEnabled() {
        //长按可以拖动
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        //屏蔽滑动
        return false;
    }

}
