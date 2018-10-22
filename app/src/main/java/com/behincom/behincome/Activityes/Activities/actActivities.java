package com.behincom.behincome.Activityes.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.behincom.behincome.Activityes.Customer.actCustomer;
import com.behincom.behincome.Activityes.Customer.fragCustomerShow;
import com.behincom.behincome.Activityes.Main.actMain;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.R;

import static com.behincom.behincome.Datas.Keys.FragmentState.AddFactor;
import static com.behincom.behincome.Datas.Keys.FragmentState.AddTask;
import static com.behincom.behincome.Datas.Keys.FragmentState.AddTaskSetTime;
import static com.behincom.behincome.Datas.Keys.FragmentState.AddTasks;
import static com.behincom.behincome.Datas.Keys.FragmentState.AddTasksShow;
import static com.behincom.behincome.Datas.Keys.FragmentState.CustomerShow;
import static com.behincom.behincome.Datas.Keys.FragmentState.SetTimeEdit;
import static com.behincom.behincome.Datas.Keys.FragmentState.TaskShow;

public class actActivities extends AppCompatActivity {

    static FragmentManager manager;
    static Context context;
    RSQLGeter geter = new RSQLGeter<>();

    static FrameLayout frameLayout;
    public static FragmentState STATE = AddTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_activities);

        frameLayout = findViewById(R.id.frameLayout);

        manager = getSupportFragmentManager();
        context = this;

        getFragByState(STATE);
    }

    public void getFragByState(FragmentState mState) {
        switch (mState) {
            case AddFactor:
                addFactor();
                STATE = AddFactor;
                break;
            case AddTasks:
                addTasks();
                STATE = AddTasks;
                break;
            case AddTask:
                addTask();
                STATE = AddTask;
                break;
            case AddTasksShow:
                addTasksShow();
                STATE = AddTasksShow;
                break;
            case AddTaskSetTime:
                addTaskSetTime();
                STATE = AddTaskSetTime;
                break;
            case TaskShow:
                addTaskShow();
                STATE = TaskShow;
                break;
            case SetTimeEdit:
                addSetTimeEdit();
                STATE = SetTimeEdit;
                break;
        }
    }

    private void addSetTimeEdit() {
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragSetTimeEdit.newInstance(context));
        transaction.commit();
    }
    private void addFactor() {
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragAddFactor.newInstance(context));
        transaction.commit();
    }
    private void addTasks() {
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragAddTasks.newInstance(context));
        transaction.commit();
    }
    private void addTask() {
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragAddTask.newInstance(context));
        transaction.commit();
    }
    private void addTasksShow() {
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragAddTasksShow.newInstance(context));
        transaction.commit();
    }
    private void addTaskSetTime() {
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragAddTaskSetTime.newInstance(context));
        transaction.commit();
    }
    private void addTaskShow() {
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragTaskShow.newInstance(context));
        transaction.commit();
    }
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;
    @Override
    public void onBackPressed() {
        switch (STATE) {
            case AddFactor:
//                fragAddTask.Namee = fragAddFactor.Name;
//                fragAddTask.Details = fragAddFactor.Details;
//                fragAddTask.enterTime = fragAddFactor.enterTime;
//                fragAddTask.spin1 = fragAddFactor.spin1;
//                fragAddTask.spin2 = fragAddFactor.spin2;
//                fragAddTask.Type = fragAddFactor.ActType;
//                fragAddTask.lFactor = fragAddFactor.lFactores;
//                fragAddTask.fac = false;
                getFragByState(AddTask);
                break;
            case AddTasks:
                if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
                {
                    Intent intentt = new Intent(context, actMain.class);
                    startActivity(intentt);
                    finish();
                    super.onBackPressed();
                    return;
                }
                else { Toast.makeText(getBaseContext(), "برای خروج دوباره از گزینه برگشت استفاده کنید", Toast.LENGTH_SHORT).show(); }
                mBackPressed = System.currentTimeMillis();
                break;
            case AddTask:
//                if (!fragAddTask.TaskShowType) {
//                    if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
//                    {
//                        Intent intent = new Intent(context, actCustomer.class);
////                        fragCustomerShow.Customer = fragAddTask.mCustomer;
////                        fragCustomerShow.position = fragAddTask.mPosition;
//                        actCustomer.STATE = CustomerShow;
//                        startActivity(intent);
//                        super.onBackPressed();
//                        finish();
//                        return;
//                    }
//                    else { Toast.makeText(getBaseContext(), "برای خروج دوباره از گزینه برگشت استفاده کنید", Toast.LENGTH_SHORT).show(); }
//                    mBackPressed = System.currentTimeMillis();
//                } else {
////                    fragTaskShow.lData = fragAddTask.TaskShowlData;
////                    fragTaskShow.CustomerId = fragAddTask.TaskShowCustomerId;
////                    fragTaskShow.Customer = fragAddTask.TaskShowCustomer;
//                    getFragByState(TaskShow);
//                }
                finish();
                break;
            case AddTasksShow:actActivities act = new actActivities();
                //todo todo todo
                //ye seri Etelaat ro bayad bargardune sare jashun :|
                //Mesle Customer hayi ke Select karde va Baghie chiayi ke dare haml mikone az SetTime
                fragAddTasks.lActTask.addAll(fragAddTasksShow.lActTask);
                fragAddTasks.dTask = fragAddTasksShow.dTask;
                fragAddTasks.CurrentCountNumber = 0;
                act.getFragByState(AddTasks);
                break;
            case AddTaskSetTime:
                if(fragAddTaskSetTime.mType && fragAddTaskSetTime.isForGroup && fragAddTaskSetTime.GroupStartEndType == 0) {
//                    fragAddTask.ActSelected = fragAddTaskSetTime.ActSelected;
//                    fragAddTask.Descriptioned = fragAddTaskSetTime.Descriptioned;
//                    fragAddTask.Nameioned = fragAddTaskSetTime.Nameioned;
                    getFragByState(AddTask);
                }else{
                    if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
                    {
                        Intent intent = new Intent(context, actMain.class);
                        startActivity(intent);
                        finish();
                        return;
                    }
                    else { Toast.makeText(getBaseContext(), "برای خروج دوباره از گزینه برگشت استفاده کنید", Toast.LENGTH_SHORT).show(); }
                    mBackPressed = System.currentTimeMillis();
                }
                break;
            case TaskShow:
                super.onBackPressed();
                finish();
                return;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("STATE_Activities", STATE.toString());
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        getFragByState(FragmentState.valueOf(savedInstanceState.getString("STATE_Activities")));
    }

}
