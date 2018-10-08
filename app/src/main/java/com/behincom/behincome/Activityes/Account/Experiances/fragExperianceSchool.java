package com.behincom.behincome.Activityes.Account.Experiances;


import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.behincom.behincome.Datas.BaseData.Basic_Provinces;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.util.ArrayList;
import java.util.List;

import ir.mirrajabi.persiancalendar.helpers.DateConverter;

public class fragExperianceSchool extends Fragment {

    static Context context;
    RSQLite SQL = new RSQLite();
    RSQLGeter geter = new RSQLGeter();
    PersianCalendar PC = new PersianCalendar();
    DatePickerDialog DateDialog;

    TextView lblTitle, lblAccept, lbltxtField, lbltxtCenter, lbltxtSum, lbltxtFrom, lbltxtTo, txtFromDate, txtToDate,
            lbltxtAbout, lbltxtOstan, lbltxtMaghta;
    Spinner spinSchoolLevel, spinOstan;
    EditText txtSchoolLevel, txtField, txtSchoolCenterName, txtSum1, txtSum2, txtAbout;
    AppCompatCheckBox chSchooling;
    LinearLayout linToDate, linFromDate;
    CardView cardView;
    ImageView imgBack, btnCheck;

    List<String> Ostan = new ArrayList<>();
    List<Basic_Provinces> lOstan = new ArrayList<>();

    boolean isFrom = false, isTo = false, isEmpty = false;

    public static int Type = 0, position = 0;
    private int DateFocusType = 0;
    String DateFrom = "0000-00-00T00:00:00", DateTo = "0000-00-00T00:00:00";
    String cDateFrom = "0000-00-00", cDateTo = "0000-00-00";

    public static fragExperianceSchool newInstance(Context mContext){
        fragExperianceSchool fragment = new fragExperianceSchool();
        context = mContext;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_experiance_school, container, false);

        linToDate = view.findViewById(R.id.linToDate);
        btnCheck = view.findViewById(R.id.btnCheck);
        linFromDate = view.findViewById(R.id.linFromDate);
        lbltxtMaghta = view.findViewById(R.id.lbltxtMaghta);
        lbltxtField = view.findViewById(R.id.lbltxtField);
        lbltxtCenter = view.findViewById(R.id.lbltxtCenter);
        lbltxtOstan = view.findViewById(R.id.lbltxtOstan);
        lbltxtSum = view.findViewById(R.id.lbltxtSum);
        lbltxtFrom = view.findViewById(R.id.lbltxtFrom);
        lbltxtTo = view.findViewById(R.id.lbltxtTo);
        lbltxtAbout = view.findViewById(R.id.lbltxtAbout);
        lblTitle = view.findViewById(R.id.lblTitle);
        spinSchoolLevel = view.findViewById(R.id.spinSchoolLevel);
        txtSchoolLevel = view.findViewById(R.id.txtSchoolLevel);
        txtField = view.findViewById(R.id.txtField);
        txtSchoolCenterName = view.findViewById(R.id.txtSchoolCenterName);
        spinOstan = view.findViewById(R.id.spinOstan);
        txtSum1 = view.findViewById(R.id.txtSum1);
        txtSum2 = view.findViewById(R.id.txtSum2);
        txtFromDate = view.findViewById(R.id.txtFromDate);
        txtToDate = view.findViewById(R.id.txtToDate);
        txtAbout = view.findViewById(R.id.txtAbout);
        chSchooling = view.findViewById(R.id.chSchooling);
        cardView = view.findViewById(R.id.cardView);
        imgBack = view.findViewById(R.id.imgBack);

        txtSum1.setTransformationMethod(null);
        txtSum2.setTransformationMethod(null);

        lblTitle.setText("سوابق تحصیلی");
        imgBack.setVisibility(View.VISIBLE);

        Ostan.add("استان تحصیل را انتخاب کنید");
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                todo finish();
            }
        });

        lOstan = SQL.Select("SELECT * FROM TB_Ostan", Basic_Provinces.class);
        for (Basic_Provinces data : lOstan) {
            String oName = data.ProvinceTitle;
            Ostan.add(oName);
        }
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, Ostan){
//            @NonNull
//            public View getView(int position, View convertView, @NonNull ViewGroup parent){
//                View v = super.getView(position, convertView, parent);
//
//                Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
//                ((TextView) v).setTypeface(tf);
//
//                return v;
//            }
//
//            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
//                View v =super.getDropDownView(position, convertView, parent);
//
//                Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
//                ((TextView) v).setTypeface(tf);
//                ((TextView) v).setTextColor(getResources().getColor(R.color.txtGray4));
//                v.setBackgroundColor(getResources().getColor(R.color.bgTitle));
//
//                return v;
//            }
//        };
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinOstan.setAdapter(adapter);
        spinOstan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0) {
//                String item = (String) parent.getItemAtPosition(position);
                    ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.txtGray2));
                }else {
                    ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.txtGray4));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinSchoolLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0) {
//                String item = (String) parent.getItemAtPosition(position);
                    ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.txtGray2));
                }else {
                    ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.txtGray4));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        List<String> Maghta = new ArrayList<>();
        Maghta.add("مدرک را انتخاب کنید");
//        List<Basic_EducationGrades> lBaseData = new ArrayList<>();
//        lBaseData = geter.getList(Basic_EducationGrades.class);
//        for(Basic_EducationGrades data : lBaseData){
//            Maghta.add(data.EducationGradeTitle);
//        }

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(context, R.layout.spinner_item, Maghta){
            @NonNull
            public View getView(int position, View convertView, @NonNull ViewGroup parent){
                View v = super.getView(position, convertView, parent);

                Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
                ((TextView) v).setTypeface(tf);

                return v;
            }

            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
                View v =super.getDropDownView(position, convertView, parent);

                Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
                ((TextView) v).setTypeface(tf);
                ((TextView) v).setTextColor(getResources().getColor(R.color.txtGray4));
                v.setBackgroundColor(getResources().getColor(R.color.bgTitle));

                return v;
            }
        };
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinSchoolLevel.setAdapter(adapter2);

        txtSum2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(txtSum2.getText().toString().length() == 2)
                    txtSum1.requestFocus();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        txtSum1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(txtSum1.getText().toString().length() > 0)
                    isEmpty = false;
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        txtSum1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    if(txtSum1.getText().toString().length() == 1){
                        isEmpty = true;
                    }
                    if(txtSum1.getText().toString().length() == 1) {
                        if (isEmpty) {
                            txtSum2.requestFocus();
                        }
                    }
                }
                return false;
            }
        });
        txtSum1.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    txtFromDate.requestFocus();
                    return true;
                }
                return false;
            }
        });
        txtFromDate.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    txtToDate.requestFocus();
                    return true;
                }
                return false;
            }
        });
        txtToDate.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    txtAbout.requestFocus();
                    return true;
                }
                return false;
            }
        });

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Manager())
                    if(Type == 0)
                        Submit();
                    else
                        Submit(position);
                else
                    Toast("مقادیر .... نباید خالی باشند");
            }
        });

        if(Type == 1){
//            String Avrage = Double.toString(actProfileSubmiter.lEducationalExperience.get(position).average());
//            String[] mAvg = Avrage.split("\\.");
//            String avg1 = mAvg[0];
//            String avg2 = mAvg[1];
//
//            spinSchoolLevel.setSelection(fragProfileSubmiter.lEducationalExperience.get(position).grade());
//            txtField.setText(fragProfileSubmiter.lEducationalExperience.get(position).field());
//            txtSchoolCenterName.setText(fragProfileSubmiter.lEducationalExperience.get(position).learning_center());
//            spinOstan.setSelection(Integer.parseInt(fragProfileSubmiter.lEducationalExperience.get(position).place_of_education()));
//            txtSum2.setText(avg1);
//            txtSum1.setText(avg2);
//            DateConverter DC = new DateConverter();
//            cDateFrom = actProfileSubmiter.lEducationalExperience.get(position).start_date();
//            cDateTo = actProfileSubmiter.lEducationalExperience.get(position).end_date();
//            txtFromDate.setText(DC.getToHijri(actProfileSubmiter.lEducationalExperience.get(position).start_date()));
//            if(actProfileSubmiter.lEducationalExperience.get(position).is_student())
//                txtToDate.setText("");
//            else
//                txtToDate.setText(DC.getToHijri(actProfileSubmiter.lEducationalExperience.get(position).end_date()));
//            txtAbout.setText(actProfileSubmiter.lEducationalExperience.get(position).description());
//            if(actProfileSubmiter.lEducationalExperience.get(position).is_student()) {
//                chSchooling.setChecked(true);
//                lbltxtTo.setVisibility(View.GONE);
//                txtToDate.setVisibility(View.GONE);
//                chSchooling.setTextColor(getResources().getColor(R.color.txtGray4));
//            }else {
//                chSchooling.setChecked(false);
//                lbltxtTo.setVisibility(View.VISIBLE);
//                txtToDate.setVisibility(View.VISIBLE);
//                chSchooling.setTextColor(getResources().getColor(R.color.txtGray1));
//            }
        }

        chSchooling.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(chSchooling.isChecked()) {
                    lbltxtTo.setVisibility(View.GONE);
                    txtToDate.setVisibility(View.GONE);
                    chSchooling.setTextColor(getResources().getColor(R.color.txtGray4));
                }else {
                    lbltxtTo.setVisibility(View.VISIBLE);
                    txtToDate.setVisibility(View.VISIBLE);
                    chSchooling.setTextColor(getResources().getColor(R.color.txtGray1));
                }
            }
        });

        txtFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isFrom) {
                    isFrom = true;
                    DateFocusType = 1;
                    DateDialog.show(getActivity().getFragmentManager(), "From");
                }
            }
        });
        txtToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isTo) {
                    isTo = true;
                    DateFocusType = 2;
                    DateDialog.show(getActivity().getFragmentManager(), "To");
                }
            }
        });
//        DateDialog = DatePickerDialog.newInstance(
//                actRecordsSchool.this,
//                PC.getPersianYear(),
//                PC.getPersianMonth(),
//                PC.getPersianDay()
//        );
        DateDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                if(DateFocusType == 1)
                    isFrom = false;
                if(DateFocusType == 2)
                    isTo = false;
            }
        });

        return view;
    }

    private boolean Manager(){
        return txtField.getText().length() > 0 &&
                txtSchoolCenterName.getText().length() > 0 &&
                txtSum1.getText().length() > 0 &&
                txtSum2.getText().length() > 0 &&
                spinSchoolLevel.getSelectedItemPosition() > 0 &&
                spinOstan.getSelectedItemPosition() > 0;
    }

    private void Submit(){
        DateConverter cDC = new DateConverter();
//        if(cDC.CompareDates(cDateFrom, cDateTo) == 1 && !chSchooling.isChecked()) {
//            DataEducationalExperience data = new DataEducationalExperience();
//            data.grade(spinSchoolLevel.getSelectedItemPosition());
//            data.field(txtField.getText().toString());
//            data.learning_center(txtSchoolCenterName.getText().toString());
//            data.place_of_education(Integer.toString(spinOstan.getSelectedItemPosition()));
//            data.average(Double.parseDouble(txtSum2.getText().toString() + "." + txtSum1.getText().toString()));
//            data.start_date(DateFrom);
//            data.end_date(DateTo);
//            if (chSchooling.isChecked()) data.is_student(true);
//            else data.is_student(false);
//            data.description(txtAbout.getText().toString());
//
//            actProfileSubmiter.lEducationalExperience.add(data);
//            finish();
//        }else if(chSchooling.isChecked()){
//            if(txtFromDate.getText().toString().length() > 0) {
//                DataEducationalExperience data = new DataEducationalExperience();
//                data.grade(spinSchoolLevel.getSelectedItemPosition());
//                data.field(txtField.getText().toString());
//                data.learning_center(txtSchoolCenterName.getText().toString());
//                data.place_of_education(Integer.toString(spinOstan.getSelectedItemPosition()));
//                data.average(Double.parseDouble(txtSum2.getText().toString() + "." + txtSum1.getText().toString()));
//                data.start_date(DateFrom);
//                data.end_date(DateTo);
//                if (chSchooling.isChecked()) data.is_student(true);
//                else data.is_student(false);
//                data.description(txtAbout.getText().toString());
//
//                actProfileSubmiter.lEducationalExperience.add(data);
//                finish();
//            }else
//                Toast("تاریخ شروع را انتخاب کنید");
//        }else
//            Toast("تاریخ شروع نباید بعد از تاریخ پایان ، یا یکی باشد.");
    }
    private void Submit(int position){
        DateConverter cDC = new DateConverter();
//        if(cDC.CompareDates(cDateFrom, cDateTo) == 1 && !chSchooling.isChecked()) {
//            DataEducationalExperience data = new DataEducationalExperience();
//            data.grade(spinSchoolLevel.getSelectedItemPosition());
//            data.field(txtField.getText().toString());
//            data.learning_center(txtSchoolCenterName.getText().toString());
//            data.place_of_education(Integer.toString(spinOstan.getSelectedItemPosition()));
//            data.average(Double.parseDouble(txtSum2.getText().toString() + "." + txtSum1.getText().toString()));
//
//            try {
//                String[] fDate = txtFromDate.getText().toString().split("/");
//                int year = Integer.parseInt(fDate[0]);
//                int monthOfYear = Integer.parseInt(fDate[1]);
//                int dayOfMonth = Integer.parseInt(fDate[2]);
//                DateConverter DC = new DateConverter(year, monthOfYear, dayOfMonth);
//                DateFrom = DC.getCSharp();
//            } catch (Exception ignored) {
//            }
//            try {
//                String[] fDate = txtToDate.getText().toString().split("/");
//                int year = Integer.parseInt(fDate[0]);
//                int monthOfYear = Integer.parseInt(fDate[1]);
//                int dayOfMonth = Integer.parseInt(fDate[2]);
//                DateConverter DC = new DateConverter(year, monthOfYear, dayOfMonth);
//                DateTo = DC.getCSharp();
//            } catch (Exception ignored) {
//            }
//
//            data.start_date(DateFrom);
//            data.end_date(DateTo);
//            if (chSchooling.isChecked())
//                data.is_student(true);
//            else
//                data.is_student(false);
//            data.description(txtAbout.getText().toString());
//
//            actProfileSubmiter.lEducationalExperience.set(position, data);
//            finish();
//        }else if(chSchooling.isChecked()){
//            if(txtFromDate.getText().toString().length() > 0) {
//                DataEducationalExperience data = new DataEducationalExperience();
//                data.grade(spinSchoolLevel.getSelectedItemPosition());
//                data.field(txtField.getText().toString());
//                data.learning_center(txtSchoolCenterName.getText().toString());
//                data.place_of_education(Integer.toString(spinOstan.getSelectedItemPosition()));
//                data.average(Double.parseDouble(txtSum2.getText().toString() + "." + txtSum1.getText().toString()));
//
//                try {
//                    String[] fDate = txtFromDate.getText().toString().split("/");
//                    int year = Integer.parseInt(fDate[0]);
//                    int monthOfYear = Integer.parseInt(fDate[1]);
//                    int dayOfMonth = Integer.parseInt(fDate[2]);
//                    DateConverter DC = new DateConverter(year, monthOfYear, dayOfMonth);
//                    DateFrom = DC.getCSharp();
//                } catch (Exception ignored) {
//                }
//                try {
//                    String[] fDate = txtToDate.getText().toString().split("/");
//                    int year = Integer.parseInt(fDate[0]);
//                    int monthOfYear = Integer.parseInt(fDate[1]);
//                    int dayOfMonth = Integer.parseInt(fDate[2]);
//                    DateConverter DC = new DateConverter(year, monthOfYear, dayOfMonth);
//                    DateTo = DC.getCSharp();
//                } catch (Exception ignored) {
//                }
//
//                data.start_date(DateFrom);
//                data.end_date(DateTo);
//                if (chSchooling.isChecked()) data.is_student(true);
//                else data.is_student(false);
//                data.description(txtAbout.getText().toString());
//
//                actProfileSubmiter.lEducationalExperience.set(position, data);
//                finish();
//            }else
//                Toast("تاریخ شروع را انتخاب کنید");
//        }else
//            Toast("تاریخ شروع نباید بعد از تاریخ پایان ، یا یکی باشد.");
    }

    private void Toast(String Message){
        Toast.makeText(context, Message, Toast.LENGTH_SHORT).show();
    }
//    @Override
//    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
//        if(DateFocusType == 1){
//            txtFromDate.setText(Integer.toString(year) + "/" + Integer.toString(monthOfYear + 1) + "/" + Integer.toString(dayOfMonth));
//            String mDate = Integer.toString(year) + "/" + Integer.toString(monthOfYear + 1) + "/" + Integer.toString(dayOfMonth);
//            DateConverter DC = new DateConverter(year, monthOfYear + 1, dayOfMonth);
//            cDateFrom = DC.getOnlyDate().replace("/", "-");
//            DateFrom = DC.getCSharp();
//            isFrom = false;
//        }else if(DateFocusType == 2){
//            txtToDate.setText(Integer.toString(year) + "/" + Integer.toString(monthOfYear + 1) + "/" + Integer.toString(dayOfMonth));
//            String mDate = Integer.toString(year) + "/" + Integer.toString(monthOfYear + 1) + "/" + Integer.toString(dayOfMonth);
//            DateConverter DC = new DateConverter(year, monthOfYear + 1, dayOfMonth);
//            cDateTo = DC.getOnlyDate().replace("/", "-");
//            DateTo = DC.getCSharp();
//            isTo = false;
//        }
//    }

}
