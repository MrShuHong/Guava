package com.example.treeview.test;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.treeview.TreeListViewAdapter;
import com.example.treeview.TreeNode;
import com.example.widget.R;

import java.util.List;

public class OrgTreeAdapter extends TreeListViewAdapter<OrgNode> {


    /**
     * @param mTree
     * @param datas
     * @param defaultExpandLevel 默认展开几级树
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public OrgTreeAdapter(ListView mTree,
                          List<OrgNode> datas,
                          int defaultExpandLevel) throws IllegalArgumentException, IllegalAccessException {
        super(mTree, datas, defaultExpandLevel);
    }

    @Override
    public View getConvertView(TreeNode<OrgNode> node, int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.test_item_org, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.icon = (ImageView) convertView
                    .findViewById(R.id.id_treenode_icon);
            viewHolder.label = (TextView) convertView
                    .findViewById(R.id.id_treenode_label);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (node.getIcon() == -1) {
            viewHolder.icon.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.icon.setVisibility(View.VISIBLE);
            viewHolder.icon.setImageResource(node.getIcon());
        }
        viewHolder.label.setText(node.getName());

        return convertView;
    }

    private final class ViewHolder {
        ImageView icon;
        TextView label;
    }
}
