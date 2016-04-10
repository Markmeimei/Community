package com.example.community.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.community.R;
import com.example.community.activity.Information_Overseas;
import com.example.community.bean.Overseas_Dependent;
import com.example.community.dialog.ActionSheetDialog;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 林炜智 on 2016/4/10.
 * 随行家属情况 ListView Adapter
 */
public class Information_Overseas_ListView extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    private List<Overseas_Dependent> list;

    public Information_Overseas_ListView(Context context, List<Overseas_Dependent> list) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;
    }

    public List<Overseas_Dependent> getList() {
        return list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_information_overseas, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.itemInformationOverseasRelation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(Information_Overseas.Tag, "弹框");
                new ActionSheetDialog(context)
                        .builder()
                        .setCancelable(false)
                        .setTitle("关系")
                        .setCanceledOnTouchOutside(false)
                        .addSheetItem("父亲", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        holder.itemInformationOverseasRelation.setText("父亲");
                                        list.get(position).setRelation("父亲");
                                    }
                                }).show();
            }
        });
        holder.itemInformationOverseasName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                list.get(position).setName("");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                list.get(position).setName(holder.itemInformationOverseasName.getText().toString());
            }
        });

        holder.itemInformationOverseasSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == holder.itemInformationOverseasMen.getId()) {
                    list.get(position).setSex("男");
                } else if (checkedId == holder.itemInformationOverseasWomen.getId()) {
                    list.get(position).setSex("女");
                }
            }
        });
        holder.itemInformationOverseasIdentityNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                list.get(position).setName("");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                list.get(position).setName(holder.itemInformationOverseasIdentityNumber.getText().toString());
            }
        });
        holder.itemInformationOverseasAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                list.get(position).setName("");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                list.get(position).setName(holder.itemInformationOverseasAddress.getText().toString());
            }
        });
        holder.itemInformationOverseasReduceItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(position);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.item_information_overseas_Relation)
        TextView itemInformationOverseasRelation;
        @Bind(R.id.item_information_overseas_Name)
        EditText itemInformationOverseasName;
        @Bind(R.id.item_information_overseas_Sex)
        RadioGroup itemInformationOverseasSex;
        @Bind(R.id.item_information_overseas_Men)
        RadioButton itemInformationOverseasMen;
        @Bind(R.id.item_information_overseas_Women)
        RadioButton itemInformationOverseasWomen;
        @Bind(R.id.item_information_overseas_IdentityNumber)
        EditText itemInformationOverseasIdentityNumber;
        @Bind(R.id.item_information_overseas_Address)
        EditText itemInformationOverseasAddress;
        @Bind(R.id.item_information_overseas_ReduceItem)
        ImageView itemInformationOverseasReduceItem;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
