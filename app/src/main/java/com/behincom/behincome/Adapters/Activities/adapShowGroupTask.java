package com.behincom.behincome.Adapters.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.behincom.behincome.Accesories.DateConverter;
import com.behincom.behincome.Activityes.Activities.fragAddTasksShow;
import com.behincom.behincome.Datas.Activityes.ToSend.ToSendAddTask;
import com.behincom.behincome.Datas.BaseData.Basic_ActivityFields;
import com.behincom.behincome.Datas.BaseData.Basic_ActResults;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout;
import com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class adapShowGroupTask extends RecyclerView.Adapter<adapShowGroupTask.AdapterMember> {

    Context context;
    private RSQLite SQL = new RSQLite();
    private RSQLGeter geter = new RSQLGeter();
    DateConverter DC = new DateConverter();
    PersianCalendar PC = new PersianCalendar();
    TimePickerDialog TimePicker;

    public List<ToSendAddTask> lList;

    public adapShowGroupTask(List<ToSendAddTask> lList, Context context) {
        this.lList = lList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return lList.size();
    }

    public void Clear() {
        try {
            this.lList.clear();
        } catch (Exception ignored) {
        }
    }

    @Override
    public AdapterMember onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_group_task_show, parent, false);

        return new AdapterMember(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final AdapterMember holder, final int position) {
        CardView cardViewMain = holder.cardViewMain;
        TextView lblName = holder.lblName;
        TextView lblTime = holder.lblTime;
        final ImageView imgAlarm = holder.imgAlarm;
        ImageView imgEdite = holder.imgEdite;

//        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
//        lblName.setTypeface(tf);
//        lblTime.setTypeface(tf);

        DateConverter DC = new DateConverter(lList.get(position).TaskDate, true);
        String Name = "منسوخ" + " " + lList.get(position).Title;

        List<Basic_ActResults> lSubActResult = geter.getList(Basic_ActResults.class, "WHERE isCheck='1'");
        List<Basic_ActivityFields> lSubAct = new ArrayList<>();
        for (Basic_ActResults data : lSubActResult) {
            List<Basic_ActivityFields> lFild = geter.getList(Basic_ActivityFields.class, "WHERE ActivityFieldID='" + data.ActID + "'");
            for (Basic_ActivityFields mData : lFild) {
                lSubAct.add(mData);
            }
        }

        if (lSubAct.size() > 0)
            Name = lSubAct.get(0).ActivityFieldTitle + " " + lList.get(position).Title;
        lblName.setText(Name);
        lblTime.setText(DC.getOnlyTime());
        if (lList.get(position).hasReminder) {
            Drawable myDrawable = context.getResources().getDrawable(R.drawable.alarm_);
            Bitmap myLogo = ((BitmapDrawable) myDrawable).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            myLogo.compress(Bitmap.CompressFormat.PNG, 100, stream);
            Glide.with(context).load(stream.toByteArray()).asBitmap().fitCenter().into(new BitmapImageViewTarget(imgAlarm) {
            });
            lList.get(position).hasReminder = (false);
            fragAddTasksShow.lActTask.get(position).hasReminder = (false);
        } else {
            Drawable myDrawable = context.getResources().getDrawable(R.drawable.alarm_selected);
            Bitmap myLogo = ((BitmapDrawable) myDrawable).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            myLogo.compress(Bitmap.CompressFormat.PNG, 100, stream);
            Glide.with(context).load(stream.toByteArray()).asBitmap().fitCenter().into(new BitmapImageViewTarget(imgAlarm) {
            });
            lList.get(position).hasReminder = (true);
            fragAddTasksShow.lActTask.get(position).hasReminder = (true);
        }

        imgAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lList.get(position).hasReminder) {
                    Drawable myDrawable = context.getResources().getDrawable(R.drawable.alarm_);
                    Bitmap myLogo = ((BitmapDrawable) myDrawable).getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    myLogo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    Glide.with(context).load(stream.toByteArray()).asBitmap().fitCenter().into(new BitmapImageViewTarget(imgAlarm) {
                    });
                    lList.get(position).hasReminder = (false);
                    fragAddTasksShow.lActTask.get(position).hasReminder = (false);
                } else {
                    Drawable myDrawable = context.getResources().getDrawable(R.drawable.alarm_selected);
                    Bitmap myLogo = ((BitmapDrawable) myDrawable).getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    myLogo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    Glide.with(context).load(stream.toByteArray()).asBitmap().fitCenter().into(new BitmapImageViewTarget(imgAlarm) {
                    });
                    lList.get(position).hasReminder = (true);
                    fragAddTasksShow.lActTask.get(position).hasReminder = (true);
                }
            }
        });
        imgEdite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTime(position);
            }
        });
        cardViewMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//TODO ???
//                Toast.makeText(context, "اینجا رو چی باید کنم ؟ کلا ویرایش یا فقط زمان ویرایش؟", Toast.LENGTH_LONG).show();
            }
        });
        cardViewMain.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {//TODO ???
//                Toast.makeText(context, "حذف هم بزارم براش؟", Toast.LENGTH_LONG).show();
                return false;
            }
        });

    }

    private void setTime(final int position) {
        Time now = new Time();
        now.setToNow();
        TimePicker = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
                String Time = DC.getTime(hourOfDay, minute);
                DC = new DateConverter(lList.get(position).TaskDate, true);
                String[] aTime = DC.getCSharp2().split("T");
                String aDate = aTime[0];

                String TodoDateTime = aDate + "T" + Time;

                lList.get(position).TaskDate = (TodoDateTime);
                fragAddTasksShow.lActTask.get(position).TaskDate = (TodoDateTime);
                notifyDataSetChanged();
            }
        }, now.hour, now.minute, false);
        TimePicker.setTitle("زمان را مشخص کنید");
        FragmentManager manager = ((Activity) context).getFragmentManager();
        TimePicker.show(manager, "Time");
    }

    public static class AdapterMember extends RecyclerView.ViewHolder {

        public CardView cardViewMain;
        public TextView lblName;
        public TextView lblTime;
        public ImageView imgAlarm;
        public ImageView imgEdite;

        public AdapterMember(View itemView) {
            super(itemView);
            cardViewMain = itemView.findViewById(R.id.cardViewMain);
            lblName = itemView.findViewById(R.id.lblName);
            imgEdite = itemView.findViewById(R.id.imgEdite);
            imgAlarm = itemView.findViewById(R.id.imgAlarm);
            lblTime = itemView.findViewById(R.id.lblTime);
        }

    }

}
