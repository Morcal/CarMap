package cn.go.lyqdh.carmap.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.go.lyqdh.carmap.R;
import cn.go.lyqdh.carmap.modle.LeftMenu;

/**
 * Created by lyqdh on 2016/1/10.
 */
public class LeftMenuAdapter extends BaseAdapter {
    private Context context;
    private List<LeftMenu> leftMenus;

    public LeftMenuAdapter(Context context) {
        this.context = context;
    }

    public void setItems(List<LeftMenu> leftMenus) {
        this.leftMenus = leftMenus;
    }

    @Override
    public int getCount() {
        return leftMenus.size();
    }

    @Override
    public Object getItem(int position) {
        return leftMenus.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LeftMenu leftMenu = (LeftMenu) getItem(position);
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_leftmenu, null);
            holder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_menuicon);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_menutitle);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.ivIcon.setImageResource(leftMenu.getIcon());
        holder.tvTitle.setText(leftMenu.getTitle());
        return convertView;
    }

    static class ViewHolder {
        private TextView tvTitle;
        private ImageView ivIcon;
    }
}
