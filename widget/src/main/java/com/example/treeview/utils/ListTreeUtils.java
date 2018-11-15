package com.example.treeview.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 代码参考 :
 * https://blog.csdn.net/u011723942/article/details/78247570
 * dingshuhong
 */
public class ListTreeUtils {

    /**
     * listToTree
     * <p>方法说明<p>
     * 将JSONArray数组转为树状结构
     *
     * @param arr   需要转化的数据
     * @param id    数据唯一的标识键值
     * @param pid   父id唯一标识键值
     * @param child 子节点键值
     * @return JSONArray
     */
    public static JSONArray listToTree(JSONArray arr, String id, String pid, String child) {

        try {
            JSONArray r = new JSONArray();
            JSONObject hash = new JSONObject();
            //将数组转为Object的形式，key为数组中的id
            for (int i = 0; i < arr.length(); i++) {
                JSONObject json = arr.optJSONObject(i);

                hash.put(json.optString(id), json);
            }

            //遍历结果集
            for (int j = 0; j < arr.length(); j++) {
                //单条记录
                JSONObject aVal = arr.optJSONObject(j);
                String node_id = aVal.optString(id);
                String node_pid = aVal.optString(pid);

                //在hash中取出key为单条记录中pid的值
                //得到 父节点对象
                JSONObject hashVP = hash.optJSONObject(node_pid);
                //如果记录的pid存在，则说明它有父节点，将她添加到孩子节点的集合中
                if (hashVP != null) {
                    //检查是否有child属性
                    if (hashVP.optJSONArray(child) != null) {
                        JSONArray ch = hashVP.optJSONArray(child);
                        ch.put(aVal);
                        hashVP.put(child, ch);
                    } else {
                        JSONArray ch = new JSONArray();
                        ch.put(aVal);
                        hashVP.put(child, ch);
                    }
                } else {
                    r.put(aVal);
                }
            }
            return r;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 如果没有删除节点的需求，建议使用这个方式
     * @param arr
     * @param id
     * @param pid
     * @param child
     * @return
     */
    public static JSONArray listToTreeAddHierarchy(JSONArray arr, String id, String pid, String child) {

        try {
            JSONArray r = new JSONArray();
            JSONObject hash = new JSONObject();
            //将数组转为Object的形式，key为数组中的id
            for (int i = 0; i < arr.length(); i++) {
                JSONObject json = arr.optJSONObject(i);

                hash.put(json.optString(id), json);
            }
            //遍历结果集
            for (int j = 0; j < arr.length(); j++) {
                //单条记录
                JSONObject aVal = arr.optJSONObject(j);
                //在hash中取出key为单条记录中pid的值
                JSONObject hashVP = hash.optJSONObject(aVal.optString(pid));
                //如果记录的pid存在，则说明它有父节点，将她添加到孩子节点的集合中
                if (hashVP != null) {
                    //检查是否有child属性
                    if (hashVP.optJSONArray(child) != null) {
                        JSONArray ch = hashVP.optJSONArray(child);
                        ch.put(aVal);
                        hashVP.put(child, ch);
                    } else {
                        JSONArray ch = new JSONArray();
                        ch.put(aVal);
                        hashVP.put(child, ch);
                    }
                } else {
                    r.put(aVal);
                }
            }
            int length = r.length();
            for (int index = 0; index < length; index++) {
                JSONObject jsonObject = r.optJSONObject(index);
                jsonObject.put("hierarchy", 0);
                JSONArray jsonArray = jsonObject.optJSONArray(child);
                if (jsonArray != null && jsonArray.length() > 0) {
                    addHierarchy(jsonObject, jsonArray, child);
                }
            }
            return r;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void addHierarchy(JSONObject parentJSON, JSONArray children, String child) {
        try {
            int length = children.length();
            for (int index = 0; index < length; index++) {
                //设置子层级数
                int hierarchy = parentJSON.optInt("hierarchy");
                JSONObject jsonObject = children.optJSONObject(index);
                jsonObject.put("hierarchy", hierarchy + 1);

                //递归设置下一个层级所有元素的层级数
                JSONArray jsonArray = jsonObject.optJSONArray(child);
                if (jsonArray != null && jsonArray.length() > 0) {
                    addHierarchy(jsonObject, jsonArray, child);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
