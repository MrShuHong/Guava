package com.example.treeview.select;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.treeview.TreeNode;

import java.util.List;

public abstract class SelectTreeAdapter<T> extends BaseAdapter {

    protected Context mContext;
    /**
     * 存储所有可见的Node
     */
    protected List<TreeNode<T>> mNodes;
    protected LayoutInflater mInflater;
    /**
     * 存储所有的Node
     */
    protected List<TreeNode<T>> mAllNodes;

    /**
     * 点击的回调接口
     */
    private OnTreeNodeClickListener onTreeNodeClickListener;

    public interface OnTreeNodeClickListener {
        void onClick(TreeNode node, int position);
    }

    public void setOnTreeNodeClickListener(
            OnTreeNodeClickListener onTreeNodeClickListener) {
        this.onTreeNodeClickListener = onTreeNodeClickListener;
    }

    /**
     * @param mTree
     * @param datas
     * @param defaultExpandLevel 默认展开几级树
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public SelectTreeAdapter(ListView mTree, List<T> datas,
                             int defaultExpandLevel) throws IllegalArgumentException,
            IllegalAccessException {
        mContext = mTree.getContext();
        /**
         * 对所有的Node进行排序
         */
        mAllNodes = TreeSelectHelper.getSortedNodes(datas, defaultExpandLevel);
        /**
         * 过滤出可见的Node
         */
        mNodes = TreeSelectHelper.filterVisibleNode(mAllNodes);
        mInflater = LayoutInflater.from(mContext);

        /**
         * 设置节点点击时，可以展开以及关闭；并且将ItemClick事件继续往外公布
         */
        mTree.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                expandOrCollapse(position);

                if (onTreeNodeClickListener != null) {
                    onTreeNodeClickListener.onClick(mNodes.get(position),
                            position);
                }
            }

        });

    }

    /**
     * 相应ListView的点击事件 展开或关闭某节点
     *
     * @param position
     */
    public void expandOrCollapse(int position) {
        TreeNode<T> n = mNodes.get(position);

        if (n != null) {// 排除传入参数错误异常
            if (!n.isLeaf()) {
                n.setExpand(!n.isExpand());
                mNodes = TreeSelectHelper.filterVisibleNode(mAllNodes);
                notifyDataSetChanged();// 刷新视图
            }
        }
    }

    @Override
    public int getCount() {
        return mNodes.size();
    }

    @Override
    public Object getItem(int position) {
        return mNodes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        TreeNode<T> node = mNodes.get(position);
        convertView = getConvertView(node, position, convertView, parent);
        // 设置内边距
        convertView.setPadding(node.getLevel() * 30, 3, 3, 3);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandOrCollapse(position);

                if (onTreeNodeClickListener != null) {
                    onTreeNodeClickListener.onClick(mNodes.get(position),
                            position);
                }
            }
        });
        return convertView;
    }

    public abstract View getConvertView(TreeNode<T> node, int position,
                                        View convertView, ViewGroup parent);

}