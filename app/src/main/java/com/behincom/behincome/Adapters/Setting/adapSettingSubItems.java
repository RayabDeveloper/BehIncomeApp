package com.behincom.behincome.Adapters.Setting;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.behincom.behincome.Activityes.Setting.fragBasicData;
import com.behincom.behincome.Datas.BaseData.Basic_TagGroups;
import com.behincom.behincome.Datas.BaseData.Basic_Tags;
import com.behincom.behincome.Datas.Keys.TagType;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;
import com.behincom.behincome.app.AppController;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class adapSettingSubItems<T> extends RecyclerView.Adapter<adapSettingSubItems.AdapterMember>{

    private Context context = AppController.getContext;
    RSQLite SQL = new RSQLite();

    protected List<T> lList = new ArrayList<>();
    private String TitleName = "", IdName = "", MainIdName = "";
    private Object mObject;
    String Id = "";
    public adapSettingSubItems(List<T> lList, String TitleName, String IdName, String MainIdName){
        this.lList = lList;
        this.TitleName = TitleName;
        this.MainIdName = MainIdName;
        this.IdName = IdName;
        try {
            mObject = lList.get(0).getClass().newInstance();
            int t = 3;
        }catch (Exception ignored){}
    }

    public List<T> getList(){
        return lList;
    }

    @Override
    public int getItemCount() {
        return lList.size();
    }

    public void Clear(){
        try{
            this.lList.clear();
        }catch (Exception ignored){}
    }

    @Override
    public AdapterMember onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_setting_sub, parent, false);

        return new AdapterMember(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final AdapterMember holder, final int position) {
        final TextView lblSubTitle = holder.lblSubTitle;
        final AppCompatCheckBox ch = holder.ch;
        final AppCompatCheckBox ch2 = holder.ch2;

        Field[] fields = mObject.getClass().getDeclaredFields();
        for (Field field : fields) {
            if(field.getName().contains(TitleName)){
                try {
                    String Title = field.get(lList.get(position)).toString();
                    lblSubTitle.setText(Title);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(field.getName().contains(IdName)){
                try {
                    Id = field.get(lList.get(position)).toString();
                    String gg = "";
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(field.getName().contains("isCheck")){
                try {
                    boolean aaa = field.getBoolean(lList.get(position));
                    if(aaa){
                        String ggg = "ASD";
                    }
                    ch.setChecked(field.getBoolean(lList.get(position)));
                    if(field.getBoolean(lList.get(position))){
                        fragBasicData.IdsToSend = fragBasicData.IdsToSend.replace(Id + ", ", "");
                        fragBasicData.IdsToSend += Id + ", ";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if(checkFatherType(Integer.parseInt(Id))){
            ch.setVisibility(View.GONE);
            ch2.setVisibility(View.VISIBLE);
        }else{
            ch.setVisibility(View.VISIBLE);
            ch2.setVisibility(View.GONE);
        }

        ch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Field[] fields = mObject.getClass().getDeclaredFields();
                for (Field field : fields) {
                    if(field.getName().contains(IdName)){
                        try {
                            Id = field.get(lList.get(position)).toString();
                            String gg = "";
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                Field[] fieldss = mObject.getClass().getDeclaredFields();
                for (Field field : fieldss) {
                    if(field.getName().contains("isCheck")){
                        try {
                            field.set(lList.get(position), ch.isChecked());
                            fragBasicData.IdsToSend = Id;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                fragBasicData.RequestManager(ch.isChecked(), Integer.parseInt(Id));
            }
        });
        ch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Field[] fields = mObject.getClass().getDeclaredFields();
                for (Field field : fields) {
                    if(field.getName().contains(IdName)){
                        try {
                            Id = field.get(lList.get(position)).toString();
                            String gg = "";
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                Field[] fieldss = mObject.getClass().getDeclaredFields();
                for (Field field : fieldss) {
                    if(field.getName().contains("isCheck")){
                        try {
                            field.set(lList.get(position), ch2.isChecked());
                            fragBasicData.IdsToSend = Id;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                fragBasicData.RequestManager(ch2.isChecked(), Integer.parseInt(Id));
            }
        });
        lblSubTitle.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int sID = 0;
                Field[] fields = mObject.getClass().getDeclaredFields();
                for (Field field : fields) {
                    if(field.getName().contains(IdName)){
                        try {
                            sID = Integer.parseInt(field.get(lList.get(position)).toString());
                            String gg = "";
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                fragBasicData.Choiser(lList.get(position), false, sID);
                return false;
            }
        });

    }

    RSQLGeter geter = new RSQLGeter();

    private boolean checkFatherType(int Id){
        boolean isRad = false;
        if(MainIdName == "TagGroupID") {
            List<Basic_Tags> mList1 = geter.getList(Basic_Tags.class, " WHERE TagID='" + Id + "'");
            List<Basic_TagGroups> mList2 = geter.getList(Basic_TagGroups.class, " WHERE TagGroupID='" + mList1.get(0).TagGroupID + "'");
            isRad = mList2.get(0).TagGroupTypeId == TagType.RadioButton;
        }
        return isRad;
    }

    public static class AdapterMember extends RecyclerView.ViewHolder {

        public TextView lblSubTitle;
        public AppCompatCheckBox ch;
        public AppCompatCheckBox ch2;

        AdapterMember(View itemView){
            super(itemView);
            lblSubTitle = itemView.findViewById(R.id.lblSubTitle);
            ch = itemView.findViewById(R.id.ch);
        }

    }

}
