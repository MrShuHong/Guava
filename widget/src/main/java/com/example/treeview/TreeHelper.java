package com.example.treeview;

import com.example.treeview.annotation.TreeNodeId;
import com.example.treeview.annotation.TreeNodeLabel;
import com.example.treeview.annotation.TreeNodePid;
import com.example.widget.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TreeHelper {
    /**
     * 传入我们的普通bean，转化为我们排序后的Node
     *
     * @param datas
     * @param defaultExpandLevel
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static <T> List<TreeNode<T>> getSortedNodes(List<T> datas,
                                                int defaultExpandLevel) throws IllegalArgumentException,
            IllegalAccessException {
        List<TreeNode<T>> result = new ArrayList<TreeNode<T>>();
        //将用户数据转化为List<Node>以及设置Node间关系
        List<TreeNode<T>> nodes = convetData2Node(datas);
        //拿到根节点
        List<TreeNode<T>> rootNodes = getRootNodes(nodes);
        //排序
        for (TreeNode node : rootNodes) {
            addNode(result, node, defaultExpandLevel, 1);
        }
        return result;
    }

    /**
     * 过滤出所有可见的Node
     *
     * @param nodes
     * @return
     */
    public static <T> List<TreeNode<T>> filterVisibleNode(List<TreeNode<T>> nodes) {
        List<TreeNode<T>> result = new ArrayList<TreeNode<T>>();

        for (TreeNode<T> node : nodes) {
            // 如果为跟节点，或者上层目录为展开状态
            if (node.isRoot() || node.isParentExpand()) {
                setNodeIcon(node);
                result.add(node);
            }
        }
        return result;
    }

    /**
     * 将我们的数据转化为树的节点
     *
     * @param datas
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    private static <T> List<TreeNode<T>> convetData2Node(List<T> datas)
            throws IllegalArgumentException, IllegalAccessException {
        List<TreeNode<T>> nodes = new ArrayList<TreeNode<T>>();
        TreeNode<T> node = null;

        for (T t : datas) {
            int id = -1;
            int pId = -1;
            String label = null;
            Class<? extends Object> clazz = t.getClass();
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field f : declaredFields) {
                if (f.getAnnotation(TreeNodeId.class) != null) {
                    f.setAccessible(true);
                    id = f.getInt(t);
                }
                if (f.getAnnotation(TreeNodePid.class) != null) {
                    f.setAccessible(true);
                    pId = f.getInt(t);
                }
                if (f.getAnnotation(TreeNodeLabel.class) != null) {
                    f.setAccessible(true);
                    label = (String) f.get(t);
                }
                if (id != -1 && pId != -1 && label != null) {
                    break;
                }
            }
            node = new TreeNode<T>(id, pId, label);
            node.setBean(t);
            nodes.add(node);
        }

        /**
         * 设置Node间，父子关系;让每两个节点都比较一次，即可设置其中的关系
         */
        for (int i = 0; i < nodes.size(); i++) {
            TreeNode n = nodes.get(i);
            for (int j = i + 1; j < nodes.size(); j++) {
                TreeNode m = nodes.get(j);
                if (m.getpId() == n.getId()) {
                    n.getChildren().add(m);
                    m.setParent(n);
                } else if (m.getId() == n.getpId()) {
                    m.getChildren().add(n);
                    n.setParent(m);
                }
            }
        }

        // 设置图片
        for (TreeNode n : nodes) {
            setNodeIcon(n);
        }
        return nodes;
    }

    private static <T> List<TreeNode<T>> getRootNodes(List<TreeNode<T>> nodes) {
        List<TreeNode<T>> root = new ArrayList<TreeNode<T>>();
        for (TreeNode node : nodes) {
            if (node.isRoot())
                root.add(node);
        }
        return root;
    }

    /**
     * 把一个节点上的所有的内容都挂上去
     */
    private static <T> void addNode(List<TreeNode<T>> nodes, TreeNode<T> node,
                                int defaultExpandLeval, int currentLevel) {

        nodes.add(node);
        if (defaultExpandLeval >= currentLevel) {
            node.setExpand(true);
        }

        if (node.isLeaf())
            return;
        List<TreeNode<T>> children = node.getChildren();
        int size = children.size();
        for (int i = 0; i < size; i++) {
            addNode(nodes, children.get(i), defaultExpandLeval,
                    currentLevel + 1);
        }
    }

    /**
     * 设置节点的图标
     *
     * @param node
     */
    private static void setNodeIcon(TreeNode node) {
        if (node.getChildren().size() > 0 && node.isExpand()) {
            node.setIcon(R.drawable.tree_ex);
        } else if (node.getChildren().size() > 0 && !node.isExpand()) {
            node.setIcon(R.drawable.tree_ec);
        } else
            node.setIcon(-1);
    }

}
