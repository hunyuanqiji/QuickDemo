package cn.plugin.core.widgets.pinnedheader.expand;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;

import java.util.List;

import cn.plugin.core.widgets.pinnedheader.PinnedHeaderAdapter;


public abstract class RecyclerExpandBaseAdapter<G, C, VH extends RecyclerView.ViewHolder> extends PinnedHeaderAdapter<VH> {

    protected static final int VIEW_TYPE_EMPTY_VIEW = -1;
    protected static final int VIEW_TYPE_ITEM_TIME = 0;
    protected static final int VIEW_TYPE_ITEM_CONTENT = 1;
    protected static final int VIEW_TYPE_FOOTER_VIEW = 2;
    public boolean isShowEmpty = false;
    protected List<ExpandGroupItemEntity<G, C>> mDataList;
    protected SparseArray<ExpandGroupIndexEntity> mIndexMap;

    public RecyclerExpandBaseAdapter() {
        this(null);
    }

    public RecyclerExpandBaseAdapter(List<ExpandGroupItemEntity<G, C>> dataList) {
        mDataList = dataList;
        mIndexMap = new SparseArray<>();
    }

    public void setData(List<ExpandGroupItemEntity<G, C>> dataList) {
        mDataList = dataList;
        mIndexMap.clear();
        notifyDataSetChanged();
    }

    public void setShowEmptyView(boolean isShowEmpty) {
        this.isShowEmpty = isShowEmpty;
    }

    public List<ExpandGroupItemEntity<G, C>> getData() {
        return mDataList;
    }

    @Override
    public boolean isPinnedPosition(int position) {
        return getItemViewType(position) == VIEW_TYPE_ITEM_TIME;
    }

    @Override
    public int getItemViewType(int position) {
        if (mDataList == null || mDataList.isEmpty()){
            return VIEW_TYPE_EMPTY_VIEW;
        }
        //判断是不是底部，如果是底部加上100dp空布局
        int childlistsize = 0;
        for (ExpandGroupItemEntity<G, C> item : mDataList) {
            if (item.getChildList() != null && item.isExpand()) {
                childlistsize = childlistsize + item.getChildList().size();
            }
        }
        if (position == mDataList.size() + childlistsize) {
            childlistsize = 0;
            return VIEW_TYPE_FOOTER_VIEW;
        }
        int count = 0;
        for (ExpandGroupItemEntity<G, C> item : mDataList) {
            count = count + 1;
            if (position == count - 1) {
                return VIEW_TYPE_ITEM_TIME;
            }
            if (item.getChildList() != null && item.isExpand()) {
                count = count + item.getChildList().size();
            }
            if (position < count) {
                return VIEW_TYPE_ITEM_CONTENT;
            }
        }
        throw new IllegalArgumentException("getItemViewType exception");
    }

    @Override
    public int getItemCount() {
        if (mDataList == null || mDataList.isEmpty()) {
            return 1;
        }
        int count = 0;
        for (int group = 0; group < mDataList.size(); group++) {
            ExpandGroupItemEntity<G, C> item = mDataList.get(group);
            //标题
            count = count + 1;

            mIndexMap.put(count - 1, new ExpandGroupIndexEntity(group, -1, item.getChildList() == null ? 0 : item.getChildList().size()));
            int childStartPosition = count;
            if (item.getChildList() != null && item.isExpand()) {
                //sub
                count = count + item.getChildList().size();
            }
            int childEndPosition = count;
            for (int loop = childStartPosition; loop < childEndPosition; loop++) {
                mIndexMap.put(loop, new ExpandGroupIndexEntity(group, loop - childStartPosition,
                        item.getChildList() == null ? 0 : item.getChildList().size()));
            }
        }
        count = isShowEmpty ? count + 1 : count;
        return count;
    }

}
