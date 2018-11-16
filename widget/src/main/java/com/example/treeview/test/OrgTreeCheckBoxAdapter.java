package com.example.treeview.test;

import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.treeview.TreeNode;
import com.example.treeview.select.SelectTreeAdapter;
import com.example.widget.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class OrgTreeCheckBoxAdapter extends SelectTreeAdapter<OrgNode> {


    private final Map<Integer,OrgNode> mSelectMap;

    /**
     * @param mTree
     * @param datas
     * @param defaultExpandLevel 默认展开几级树
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public OrgTreeCheckBoxAdapter(ListView mTree,
                                  List<OrgNode> datas,
                                  int defaultExpandLevel) throws IllegalArgumentException, IllegalAccessException {
        super(mTree, datas, defaultExpandLevel);
        mSelectMap = new HashMap();
    }

    @Override
    public View getConvertView(final TreeNode<OrgNode> node, int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.test_item_org_select, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.icon = (CheckBox) convertView
                    .findViewById(R.id.id_treenode_check);
            viewHolder.label = (TextView) convertView
                    .findViewById(R.id.id_treenode_label);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                OrgNode bean = node.getBean();
                if (isChecked) {
                    mSelectMap.put(bean.getId(), bean);
                    List<TreeNode<OrgNode>> children = node.getChildren();

                    addSelectNodeToMap(children);
                } else {
                    mSelectMap.remove(bean.getId());
                    List<TreeNode<OrgNode>> children = node.getChildren();
                    removeSelectNodeToMap(children);
                }

                notifyDataSetChanged();
            }

            private void removeSelectNodeToMap(List<TreeNode<OrgNode>> children) {
                if (children != null && children.size() > 0) {
                    for (TreeNode<OrgNode> child : children) {
                        OrgNode bean = child.getBean();
                        mSelectMap.remove(bean.getId());
                        addSelectNodeToMap(child.getChildren());
                    }
                }
            }

            private void addSelectNodeToMap(List<TreeNode<OrgNode>> children) {
                if (children != null && children.size() > 0) {
                    for (TreeNode<OrgNode> child : children) {
                        OrgNode bean = child.getBean();
                        mSelectMap.put(bean.getId(), bean);
                        addSelectNodeToMap(child.getChildren());
                    }
                }
            }
        };
        viewHolder.icon.setOnClickListener(null);
        viewHolder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                OrgNode bean = node.getBean();
                if (checkBox.isChecked()){
                    mSelectMap.put(bean.getId(), bean);
                    List<TreeNode<OrgNode>> children = node.getChildren();

                    addSelectNodeToMap(children);
                }else{
                    mSelectMap.remove(bean.getId());
                    List<TreeNode<OrgNode>> children = node.getChildren();
                    removeSelectNodeToMap(children);
                }
                notifyDataSetChanged();
            }
            private void removeSelectNodeToMap(List<TreeNode<OrgNode>> children) {
                if (children != null && children.size() > 0) {
                    for (TreeNode<OrgNode> child : children) {
                        OrgNode bean = child.getBean();
                        mSelectMap.remove(bean.getId());
                        removeSelectNodeToMap(child.getChildren());
                    }
                }
            }

            private void addSelectNodeToMap(List<TreeNode<OrgNode>> children) {
                if (children != null && children.size() > 0) {
                    for (TreeNode<OrgNode> child : children) {
                        OrgNode bean = child.getBean();
                        mSelectMap.put(bean.getId(), bean);
                        addSelectNodeToMap(child.getChildren());
                    }
                }
            }
        });
        //viewHolder.icon.setOnCheckedChangeListener(onCheckedChangeListener);

        int id = node.getId();
        viewHolder.icon.setChecked(mSelectMap.containsKey(id));
        viewHolder.label.setText(node.getName());

        return convertView;
    }

    public List<OrgNode> getSelectNodes(){


        if (mSelectMap != null && !mSelectMap.isEmpty()){
            List<OrgNode> orgNodes = new ArrayList<>();
            Set<Map.Entry<Integer, OrgNode>> entries = mSelectMap.entrySet();
            for (Map.Entry<Integer, OrgNode> entry : entries) {
                orgNodes.add(entry.getValue());
            }
            return orgNodes;
        }
        return null;
    }

    private final class ViewHolder {
        CheckBox icon;
        TextView label;
    }
}
