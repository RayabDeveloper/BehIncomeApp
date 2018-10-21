package com.behincom.behincome.Activityes.Main.Filter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.behincom.behincome.Accesories.DateConverter;
import com.behincom.behincome.Activityes.Main.actMain;
import com.behincom.behincome.Activityes.Main.fragCustomers;
import com.behincom.behincome.Datas.BaseData.Basic_ActivityFields;
import com.behincom.behincome.Datas.BaseData.Basic_ArchiveTypes;
import com.behincom.behincome.Datas.BaseData.Basic_CustomerStates;
import com.behincom.behincome.Datas.BaseData.Basic_CustomerStatus;
import com.behincom.behincome.Datas.BaseData.Basic_NamePrefixes;
import com.behincom.behincome.Datas.BaseData.Basic_TagGroups;
import com.behincom.behincome.Datas.BaseData.Basic_Tags;
import com.behincom.behincome.Datas.Customer.CustomerActivityFields;
import com.behincom.behincome.Datas.Customer.CustomerFilter;
import com.behincom.behincome.Datas.Customer.CustomerTags;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.Keys.TagType;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.R;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.util.ArrayList;
import java.util.List;

import saman.zamani.persiandate.PersianDate;

public class fragAddCustomerFilter extends Fragment {

    static Context context;
    actMain act = new actMain();
    RSQLGeter geter = new RSQLGeter();
    private PersianCalendar PC = new PersianCalendar();
    private DatePickerDialog DateDialog;

    ImageView btnCheck, imgBack;
    TextView lblPrefix, lblActivityField, lblTag, lblCustomerState, lblCustomerStatus, lblArchiveType, lblTitle;
    LinearLayout btnNamePrefix, btnActivityField, btnTag, btnCustomerState, btnCustomerStatus, btnArchiveType, viewArchive;
    TextView lblFromCreateDate, lblToCreateDate, lblFromArchiveDate, lblToArchiveDate, lblFromReturnDate, lblToReturnDate;
    TextView lblLongFromCreateDate, lblLongToCreateDate, lblLongFromArchiveDate, lblLongToArchiveDate, lblLongFromReturnDate, lblLongToReturnDate;

    public static CustomerFilter Filter = new CustomerFilter();
    protected static List<Basic_ActivityFields> lActivityFields = new ArrayList<>();
    protected static List<Basic_Tags> lTags = new ArrayList<>();
    protected static List<Basic_ArchiveTypes> lArchiveTypes = new ArrayList<>();
    protected static List<Basic_CustomerStates> lCustomerStates = new ArrayList<>();
    protected static List<Basic_CustomerStatus> lCustomerStatus = new ArrayList<>();
    protected static List<Basic_NamePrefixes> lNamePrefixes = new ArrayList<>();
    private DateType DateDialogChoiser;
    private boolean isCreateFrom = false, isCreateTo = false, isArchiveFrom = false, isArchiveTo = false, isReturnFrom = false, isReturnTo = false;
    private String cCreateFrom = "", cCreateTo = "", cArchiveFrom = "", cArchiveTo = "", cReturnFrom = "", cReturnTo = "";

    public static fragAddCustomerFilter newInstance(Context mContext, CustomerFilter lList) {
        Filter = lList;
        fragAddCustomerFilter fragment = new fragAddCustomerFilter();
        context = mContext;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_customer_filter, container, false);

        viewArchive = view.findViewById(R.id.viewArchive);
        btnNamePrefix = view.findViewById(R.id.btnNamePrefix);
        btnActivityField = view.findViewById(R.id.btnActivityField);
        btnTag = view.findViewById(R.id.btnTag);
        btnCustomerState = view.findViewById(R.id.btnCustomerState);
        btnCustomerStatus = view.findViewById(R.id.btnCustomerStatus);
        btnArchiveType = view.findViewById(R.id.btnArchiveType);
        btnCheck = view.findViewById(R.id.btnCheck);
        imgBack = view.findViewById(R.id.imgBack);
        lblPrefix = view.findViewById(R.id.lblPrefix);
        lblActivityField = view.findViewById(R.id.lblActivityField);
        lblTag = view.findViewById(R.id.lblTag);
        lblCustomerState = view.findViewById(R.id.lblCustomerState);
        lblCustomerStatus = view.findViewById(R.id.lblCustomerStatus);
        lblArchiveType = view.findViewById(R.id.lblArchiveType);
        lblTitle = view.findViewById(R.id.lblTitle);
        lblFromCreateDate = view.findViewById(R.id.lblFromCreateDate);
        lblToCreateDate = view.findViewById(R.id.lblToCreateDate);
        lblFromArchiveDate = view.findViewById(R.id.lblFromArchiveDate);
        lblToArchiveDate = view.findViewById(R.id.lblToArchiveDate);
        lblFromReturnDate = view.findViewById(R.id.lblFromReturnDate);
        lblToReturnDate = view.findViewById(R.id.lblToReturnDate);
        lblLongFromCreateDate = view.findViewById(R.id.lblLongFromCreateDate);
        lblLongToCreateDate = view.findViewById(R.id.lblLongToCreateDate);
        lblLongFromArchiveDate = view.findViewById(R.id.lblLongFromArchiveDate);
        lblLongToArchiveDate = view.findViewById(R.id.lblLongToArchiveDate);
        lblLongFromReturnDate = view.findViewById(R.id.lblLongFromReturnDate);
        lblLongToReturnDate = view.findViewById(R.id.lblLongToReturnDate);

        lblTitle.setText("فیلتر مشتری");
        imgBack.setImageResource(0);
        imgBack.setBackground(context.getDrawable(R.drawable.cancell_white));

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lTags = new ArrayList<>();
                lActivityFields = new ArrayList<>();
                lCustomerStates = new ArrayList<>();
                lCustomerStatus = new ArrayList<>();
                lNamePrefixes = new ArrayList<>();
                cCreateFrom = "";
                cCreateTo = "";
                cArchiveFrom = "";
                cArchiveTo = "";
                cReturnFrom = "";
                cReturnTo = "";
                fragCustomers.Filter = getFilters();
                act.getFragByState(FragmentState.MainCustomers);
            }
        });
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Filter = new CustomerFilter();
                Filter = getFilters();
                fragCustomers.Filter = Filter;
                fragCustomers.lCustomer = new ArrayList<>();
                fragCustomers.page = 0;
                act.getFragByState(FragmentState.MainCustomers);
            }
        });
        btnNamePrefix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.getNamePrefixFragment(lNamePrefixes);
            }
        });
        btnActivityField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.getActivityFragment(lActivityFields);
            }
        });
        btnTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.getTagFragment(lTags);
            }
        });
        btnCustomerState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.getCustomerStateFragment(lCustomerStates);
            }
        });
        btnCustomerStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.getCustomerStatusFragment(lCustomerStatus);
            }
        });
        btnArchiveType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.getArchiveFragment(lArchiveTypes);
            }
        });
        lblActivityField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.getActivityFragment(lActivityFields);
            }
        });
        lblTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.getTagFragment(lTags);
            }
        });
        lblArchiveType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.getArchiveFragment(lArchiveTypes);
            }
        });
        lblCustomerState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.getCustomerStateFragment(lCustomerStates);
            }
        });
        lblCustomerStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.getCustomerStatusFragment(lCustomerStatus);
            }
        });
        lblPrefix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.getNamePrefixFragment(lNamePrefixes);
            }
        });
        lblFromCreateDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isCreateFrom) {
                    isCreateFrom = true;
                    DateDialog.show(getActivity().getFragmentManager(), "isCreateFrom");
                    DateDialogChoiser = DateType.FromCreateDate;
                }
            }
        });
        lblToCreateDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isCreateTo) {
                    isCreateTo = true;
                    DateDialog.show(getActivity().getFragmentManager(), "isCreateTo");
                    DateDialogChoiser = DateType.ToCreateDate;
                }
            }
        });
        lblFromArchiveDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isArchiveFrom) {
                    isArchiveFrom = true;
                    DateDialog.show(getActivity().getFragmentManager(), "isArchiveFrom");
                    DateDialogChoiser = DateType.FromArchiveDate;
                }
            }
        });
        lblToArchiveDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isArchiveTo) {
                    isArchiveTo = true;
                    DateDialog.show(getActivity().getFragmentManager(), "isArchiveTo");
                    DateDialogChoiser = DateType.ToArchiveDate;
                }
            }
        });
        lblFromReturnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isReturnFrom) {
                    isReturnFrom = true;
                    DateDialog.show(getActivity().getFragmentManager(), "isReturnFrom");
                    DateDialogChoiser = DateType.FromReturnArchiveDate;
                }
            }
        });
        lblToReturnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isReturnTo) {
                    isReturnTo = true;
                    DateDialog.show(getActivity().getFragmentManager(), "isReturnTo");
                    DateDialogChoiser = DateType.ToReturnArchiveDate;
                }
            }
        });
        DateDialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                ir.mirrajabi.persiancalendar.core.models.PersianDate tPD = new ir.mirrajabi.persiancalendar.core.models.PersianDate(year, monthOfYear + 1, dayOfMonth);
                String mDate = Integer.toString(year) + "/" + Integer.toString(monthOfYear + 1) + "/" + Integer.toString(dayOfMonth);
                DateConverter DC = new DateConverter(year, monthOfYear + 1, dayOfMonth);
                switch (DateDialogChoiser) {
                    case FromCreateDate:
                        isCreateFrom = false;
                        cCreateFrom = DC.getCSharp();
                        String longCreateFrom = DC.getStringLongDate(tPD.getDayOfWeek(), tPD.getDayOfMonth(), tPD.getMonth(), tPD.getYear());

                        lblLongFromCreateDate.setText(longCreateFrom);
                        lblFromCreateDate.setText(mDate);
                        break;
                    case ToCreateDate:
                        isCreateTo = false;
                        cCreateTo = DC.getCSharp();
                        String longCreateTo = DC.getStringLongDate(tPD.getDayOfWeek(), tPD.getDayOfMonth(), tPD.getMonth(), tPD.getYear());

                        lblLongToCreateDate.setText(longCreateTo);
                        lblToCreateDate.setText(mDate);
                        break;
                    case FromArchiveDate:
                        isArchiveFrom = false;
                        cArchiveFrom = DC.getCSharp();
                        String longArchiveFrom = DC.getStringLongDate(tPD.getDayOfWeek(), tPD.getDayOfMonth(), tPD.getMonth(), tPD.getYear());

                        lblLongFromArchiveDate.setText(longArchiveFrom);
                        lblFromArchiveDate.setText(mDate);
                        break;
                    case ToArchiveDate:
                        isArchiveTo = false;
                        cArchiveTo = DC.getCSharp();
                        String longArchiveTo = DC.getStringLongDate(tPD.getDayOfWeek(), tPD.getDayOfMonth(), tPD.getMonth(), tPD.getYear());

                        lblLongToArchiveDate.setText(longArchiveTo);
                        lblToArchiveDate.setText(mDate);
                        break;
                    case FromReturnArchiveDate:
                        isReturnFrom = false;
                        cReturnFrom = DC.getCSharp();
                        String longReturnFrom = DC.getStringLongDate(tPD.getDayOfWeek(), tPD.getDayOfMonth(), tPD.getMonth(), tPD.getYear());

                        lblLongFromReturnDate.setText(longReturnFrom);
                        lblFromReturnDate.setText(mDate);
                        break;
                    case ToReturnArchiveDate:
                        isReturnTo = false;
                        cReturnTo = DC.getCSharp();
                        String longReturnTo = DC.getStringLongDate(tPD.getDayOfWeek(), tPD.getDayOfMonth(), tPD.getMonth(), tPD.getYear());

                        lblLongToReturnDate.setText(longReturnTo);
                        lblToReturnDate.setText(mDate);
                        break;
                }
            }
        }, PC.getPersianYear(), PC.getPersianMonth(), PC.getPersianDay());
        DateDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                if (DateDialogChoiser == DateType.FromCreateDate) isCreateFrom = false;
                else if (DateDialogChoiser == DateType.ToCreateDate) isCreateTo = false;
                else if (DateDialogChoiser == DateType.FromArchiveDate) isArchiveFrom = false;
                else if (DateDialogChoiser == DateType.ToArchiveDate) isArchiveTo = false;
                else if (DateDialogChoiser == DateType.FromReturnArchiveDate) isReturnFrom = false;
                else if (DateDialogChoiser == DateType.ToReturnArchiveDate) isReturnTo = false;
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        String ActivityField = "";
        try {
            if (Filter.ActivityFields.size() > 0) {
                lActivityFields = new ArrayList<>();
                for (int tData : Filter.ActivityFields) {
                    List<Basic_ActivityFields> pList = geter.getList(Basic_ActivityFields.class, " WHERE ActivityFieldID='" + tData + "'");
                    if (pList.size() > 0)
                        lActivityFields.add(pList.get(0));
                }
            }
            try {
                for (Basic_ActivityFields data : lActivityFields) {
                    ActivityField += "☑ - " + data.ActivityFieldTitle + "<br>";
                }
                lblActivityField.setText(Html.fromHtml(ActivityField));
            } catch (Exception Ex) {
                String Er = Ex.getMessage();
            }
            if (ActivityField.length() > 0) lblActivityField.setText(Html.fromHtml(ActivityField));
            else lblActivityField.setText("برای انتخاب زمینه فعالیت اینجا را لمس کنید.");
        } catch (Exception Ex) {
            String Er = Ex.getMessage();
        }
        String Tager = "";
        try {
            if (Filter.Tags.size() > 0) {
                lTags = new ArrayList<>();
                for (int tData : Filter.Tags) {
                    List<Basic_Tags> pList = geter.getList(Basic_Tags.class, " WHERE TagID='" + tData + "'");
                    if (pList.size() > 0)
                        lTags.add(pList.get(0));
                }
            }
            try {
                for (Basic_Tags data : lTags) {
                    List<Basic_TagGroups> lGroup = geter.getList(Basic_TagGroups.class, " WHERE TagGroupID='" + data.TagGroupID + "'");
                    if (lGroup.get(0).TagGroupTypeId == TagType.CheckBox)
                        Tager += "☑ - " + data.TagTitle + "<br>";
                    else
                        Tager += "◉ - " + data.TagTitle + "<br>";
                }
            } catch (Exception Ex) {
                String Er = Ex.getMessage();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (Tager.length() > 0) lblTag.setText(Html.fromHtml(Tager));
        else lblTag.setText("برای انتخاب وضعیت اینجا را لمس کنید.");
        String ArchiveTyper = "";
        try {
            if (Filter.ArchiveType.size() > 0) {
                lArchiveTypes = new ArrayList<>();
                for (int tData : Filter.ArchiveType) {
                    List<Basic_ArchiveTypes> pList = geter.getList(Basic_ArchiveTypes.class, " WHERE ArchiveTypeID='" + tData + "'");
                    if (pList.size() > 0)
                        lArchiveTypes.add(pList.get(0));
                }
            }
            for (Basic_ArchiveTypes data : lArchiveTypes) {
                ArchiveTyper += "☑ - " + data.ArchiveTypeTitle + "<br>";
            }
        } catch (Exception Ex) {
            String Er = Ex.getMessage();
        }
        if (ArchiveTyper.length() > 0) lblArchiveType.setText(Html.fromHtml(ArchiveTyper));
        else lblArchiveType.setText("برای انتخاب نوع بایگانی اینجا را لمس کنید.");
        String CustomerStater = "";
        try {
            if (Filter.CustomerState.size() > 0) {
                lCustomerStates = new ArrayList<>();
                for (int tData : Filter.CustomerState) {
                    List<Basic_CustomerStates> pList = geter.getList(Basic_CustomerStates.class, " WHERE CustomerStateID='" + tData + "'");
                    if (pList.size() > 0)
                        lCustomerStates.add(pList.get(0));
                }
            }
            for (Basic_CustomerStates data : lCustomerStates) {
                CustomerStater += "☑ - " + data.CustomerStateTitle + "<br>";
            }
        } catch (Exception Ex) {
            String Er = Ex.getMessage();
        }
        if (CustomerStater.length() > 0) lblCustomerState.setText(Html.fromHtml(CustomerStater));
        else lblCustomerState.setText("برای انتخاب وضعیت اینجا را لمس کنید.");
        String CustomerStatuser = "";
        try {
            if (Filter.CustomerStatues.size() > 0) {
                lCustomerStatus = new ArrayList<>();
                for (int tData : Filter.CustomerStatues) {
                    List<Basic_CustomerStatus> pList = geter.getList(Basic_CustomerStatus.class, " WHERE CustomerStatusID='" + tData + "'");
                    if (pList.size() > 0)
                        lCustomerStatus.add(pList.get(0));
                }
            }
            for (Basic_CustomerStatus data : lCustomerStatus) {
                CustomerStatuser += "☑ - " + data.CustomerStatusTitle + "<br>";
            }
        } catch (Exception Ex) {
            String Er = Ex.getMessage();
        }
        if (CustomerStatuser.length() > 0)
            lblCustomerStatus.setText(Html.fromHtml(CustomerStatuser));
        else lblCustomerStatus.setText("برای انتخاب نوع وضعیت اینجا را لمس کنید.");
        String NamePrefixer = "";
        try {
            if (Filter.CustomerPrefixID.size() > 0) {
                lNamePrefixes = new ArrayList<>();
                for (int tData : Filter.CustomerPrefixID) {
                    List<Basic_NamePrefixes> pList = geter.getList(Basic_NamePrefixes.class, " WHERE NamePrefixID='" + tData + "'");
                    if (pList.size() > 0)
                        lNamePrefixes.add(pList.get(0));
                }
            }
            for (Basic_NamePrefixes data : lNamePrefixes) {
                NamePrefixer += "☑ - " + data.NamePrefixTitle + "<br>";
            }
        } catch (Exception Ex) {
            String Er = Ex.getMessage();
        }
        if (NamePrefixer.length() > 0) lblPrefix.setText(Html.fromHtml(NamePrefixer));
        else lblPrefix.setText("برای انتخاب نوع پیشوند مشتری اینجا را لمس کنید.");
        //FromTo Create Date
        lblFromCreateDate.setText(getShortDate(Filter.FromCreateDate));
        lblLongFromCreateDate.setText(getLongDate(Filter.FromCreateDate));
        lblToCreateDate.setText(getShortDate(Filter.ToCreateDate));
        lblLongToCreateDate.setText(getLongDate(Filter.ToCreateDate));
        //FromTo Archive Date
        lblFromArchiveDate.setText(getShortDate(Filter.FromArchiveDate));
        lblLongFromArchiveDate.setText(getLongDate(Filter.FromArchiveDate));
        lblToArchiveDate.setText(getShortDate(Filter.ToArchiveDate));
        lblLongToArchiveDate.setText(getLongDate(Filter.ToArchiveDate));
        //FromTo Return Date
        lblFromReturnDate.setText(getShortDate(Filter.FromReturnArchiveDate));
        lblLongFromReturnDate.setText(getLongDate(Filter.FromReturnArchiveDate));
        lblToReturnDate.setText(getShortDate(Filter.ToReturnArchiveDate));
        lblLongToReturnDate.setText(getLongDate(Filter.ToReturnArchiveDate));
        //Visible Gone Archive Views
        boolean isArchive = false;
        for (Basic_CustomerStatus data : lCustomerStatus) {
            if(data.CustomerStatusID == 3) {
                isArchive = true;
                break;
            }
        }
        if(isArchive){
            viewArchive.setVisibility(View.VISIBLE);
        }else{
            viewArchive.setVisibility(View.GONE);
            lArchiveTypes = new ArrayList<>();
            cArchiveFrom = "";
            cReturnFrom = "";
            cArchiveTo = "";
            cReturnTo = "";
        }

        Filter = new CustomerFilter();
        Filter = getFilters();
    }

    private String getShortDate(String Date){
        try {
            PersianDate pDate = new PersianDate();
            String[] DateTime = Date.split("T");
            String[] Dates = DateTime[0].split("-");
            pDate.setGrgYear(Integer.parseInt(Dates[0]));
            pDate.setGrgMonth(Integer.parseInt(Dates[1]));
            pDate.setGrgDay(Integer.parseInt(Dates[2]));
            int shY = pDate.getShYear();
            int shM = pDate.getShMonth();
            int shD = pDate.getShDay();

            return Integer.toString(shY) + "/" + Integer.toString(shM) + "/" + Integer.toString(shD);
        } catch (NumberFormatException e) {
            return "";
        }
    }
    private String getLongDate(String Date){
        try {
            PersianDate pDate = new PersianDate();
            String[] DateTime = Date.split("T");
            String[] Dates = DateTime[0].split("-");
            pDate.setGrgYear(Integer.parseInt(Dates[0]));
            pDate.setGrgMonth(Integer.parseInt(Dates[1]));
            pDate.setGrgDay(Integer.parseInt(Dates[2]));
            int shY = pDate.getShYear();
            int shM = pDate.getShMonth();
            int shD = pDate.getShDay();
            ir.mirrajabi.persiancalendar.core.models.PersianDate tPD = new ir.mirrajabi.persiancalendar.core.models.PersianDate(shY, shM, shD);
            DateConverter DC = new DateConverter(shY, shM, shD);

            return DC.getStringLongDate(tPD.getDayOfWeek(), tPD.getDayOfMonth(), tPD.getMonth(), tPD.getYear());
        } catch (NumberFormatException e) {
            return "";
        }
    }

    private CustomerFilter getFilters() {
        CustomerFilter Filter = new CustomerFilter();
        for (Basic_NamePrefixes data : lNamePrefixes) {
            Filter.CustomerPrefixID.add(data.NamePrefixID);
        }
        for (Basic_ActivityFields data : lActivityFields) {
            Filter.ActivityFields.add(data.ActivityFieldID);
        }
        for (Basic_Tags data : lTags) {
            Filter.Tags.add(data.TagID);
        }
        for (Basic_CustomerStates data : lCustomerStates) {
            Filter.CustomerState.add(data.CustomerStateID);
        }
        for (Basic_CustomerStatus data : lCustomerStatus) {
            Filter.CustomerStatues.add(data.CustomerStatusID);
        }
        for (Basic_ArchiveTypes data : lArchiveTypes) {
            Filter.ArchiveType.add(data.ArchiveTypeID);
        }
        Filter.FromCreateDate = cCreateFrom;
        Filter.ToCreateDate = cCreateTo;
        Filter.FromArchiveDate = cArchiveFrom;
        Filter.ToArchiveDate = cArchiveTo;
        Filter.FromReturnArchiveDate = cReturnFrom;
        Filter.ToReturnArchiveDate = cReturnTo;
        return Filter;
    }

    private enum DateType {
        FromCreateDate,
        ToCreateDate,
        FromArchiveDate,
        ToArchiveDate,
        FromReturnArchiveDate,
        ToReturnArchiveDate
    }

}
