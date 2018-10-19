package com.behincom.behincome.Adapters.Customer;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.behincom.behincome.Activityes.Customer.fragAddCustomer;
import com.behincom.behincome.Adapters.SpinAdapter;
import com.behincom.behincome.Datas.BaseData.Basic_ContactTypes;
import com.behincom.behincome.Datas.BaseData.Basic_PersonRoles;
import com.behincom.behincome.Datas.Customer.CustomerPersonnel;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class adapStoreContact extends RecyclerView.Adapter<adapStoreContact.AdapterMember>{

    Context context;
    RSQLite SQL = new RSQLite();
    RSQLGeter geter = new RSQLGeter();

    List<Basic_PersonRoles> lPersonRole = new ArrayList<>();
    List<Basic_ContactTypes> lContactType = new ArrayList<>();
    SpinAdapter spinAdapRole, spinAdapType;

    public List<CustomerPersonnel> lList;
    public adapStoreContact(List<CustomerPersonnel> lList, Context mContext){
        this.lList = lList;
        this.context = mContext;

        lPersonRole = geter.getList(Basic_PersonRoles.class);
        lContactType = geter.getList(Basic_ContactTypes.class);

        spinAdapRole = new SpinAdapter(context, lPersonRole, "PersonRoleTitle");
        spinAdapType = new SpinAdapter(context, lContactType, "ContactTypeTitle");
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
                .inflate(R.layout.items_addstore_contacts, parent, false);

        return new AdapterMember(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final AdapterMember holder, final int position) {
        final TextView lblName = holder.lblName;
        final TextView lblNumber = holder.lblNumber;
        final ImageView imgDelete = holder.imgDelete;
        final ImageView imgCallLogo = holder.imgCallLogo;
        final CardView cardViewMain = holder.cardViewMain;

//        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
//        lblName.setTypeface(tf);lblNumber.setTypeface(tf);

        List<Basic_ContactTypes> lContact = geter.getList(Basic_ContactTypes.class, "WHERE ContactTypeID='" + lList.get(position).ContactTypeID + "'");

        lblName.setText(lList.get(position).Name);
        lblNumber.setText(lList.get(position).ContactInfo);
        imgCallLogo.setImageBitmap(getLogo(lContact.get(0).AndroidKeyboardTypeID));
        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragAddCustomer act = new fragAddCustomer();
                act.onClick_Delete(lList, position);
            }
        });
        cardViewMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog cDialog;
                cDialog = new Dialog(context);
                cDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                cDialog.setCancelable(true);
                cDialog.setCanceledOnTouchOutside(true);
                cDialog.setContentView(R.layout.dialog_add_contact);
                Objects.requireNonNull(cDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

                TextView lblAccept = cDialog.findViewById(R.id.lblAccept);
                TextView lblCancell = cDialog.findViewById(R.id.lblCancell);
                final EditText txtName = cDialog.findViewById(R.id.txtName);
                final EditText txtInfo = cDialog.findViewById(R.id.txtInfo);
                final Spinner spinRole = cDialog.findViewById(R.id.spinRole);
                final Spinner spinType = cDialog.findViewById(R.id.spinType);

//                Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
//                txtName.setTypeface(tf);
//                txtInfo.setTypeface(tf);

                List<Basic_PersonRoles> lPersonRoles = new ArrayList<>();
                List<Basic_ContactTypes> lContactType = new ArrayList<>();
                lPersonRoles = geter.getList(Basic_PersonRoles.class);
                lContactType = geter.getList(Basic_ContactTypes.class);
                spinAdapRole = new SpinAdapter(context, lPersonRoles, "PersonRoleTitle");
                spinAdapType = new SpinAdapter(context, lContactType, "ContactTypeTitle");
                spinRole.setAdapter(spinAdapRole);
                spinType.setAdapter(spinAdapType);

                txtInfo.setInputType(getType(1));
                txtInfo.setSelection(txtInfo.getText().length());

                txtName.setText(lList.get(position).Name);
                txtInfo.setText(lList.get(position).ContactInfo);
                spinRole.setSelection(spinAdapRole.getItemPosition("PersonRoleID", Integer.toString(lList.get(position).PersonnelRoleID)));
                spinType.setSelection(spinAdapType.getItemPosition("ContactTypeID", Integer.toString(lList.get(position).ContactTypeID)));

                lblAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (txtName.getText().toString().length() > 0 && txtInfo.getText().toString().length() > 0) {
                            int ContactTypeID = Integer.parseInt(spinAdapType.getItemString(spinType.getSelectedItemPosition(), "ContactTypeID"));
                            int PersonnelRoleID = Integer.parseInt(spinAdapRole.getItemString(spinRole.getSelectedItemPosition(), "PersonRoleID"));
                            String PersonnelRoleName = spinAdapRole.getItemString(spinRole.getSelectedItemPosition(), "PersonRoleTitle");

                            lList.get(position).ContactInfo = txtInfo.getText().toString();
                            lList.get(position).Name = txtName.getText().toString();
                            lList.get(position).ContactTypeID = ContactTypeID;
                            lList.get(position).PersonnelRoleID = PersonnelRoleID;
                            lList.get(position).PersonnelRoleName = txtName.getText().toString();

                            fragAddCustomer.lContact.get(position).ContactInfo = txtInfo.getText().toString();
                            fragAddCustomer.lContact.get(position).Name = txtName.getText().toString();
                            fragAddCustomer.lContact.get(position).ContactTypeID = ContactTypeID;
                            fragAddCustomer.lContact.get(position).PersonnelRoleID = PersonnelRoleID;
                            fragAddCustomer.lContact.get(position).PersonnelRoleName = txtName.getText().toString();

                            notifyDataSetChanged();

                            cDialog.dismiss();
                        } else
                            Toast.makeText(context, "مقادیر نباید خالی باشند.", Toast.LENGTH_SHORT).show();
                    }
                });
                lblCancell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cDialog.dismiss();
                    }
                });

                spinType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        txtInfo.setInputType(getType(Integer.parseInt(spinAdapType.getItemString(spinType.getSelectedItemPosition(), "AndroidKeyboardTypeID"))));
                        txtInfo.setSelection(txtInfo.getText().length());
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                cDialog.show();
            }
        });
    }
    private int getType(int AndroidKey){
        switch (AndroidKey){
            case 1:
                return InputType.TYPE_CLASS_TEXT;
            case 2:
                return InputType.TYPE_CLASS_NUMBER;
            case 3:
                return InputType.TYPE_CLASS_DATETIME;
            case 4:
                return InputType.TYPE_CLASS_DATETIME;
            case 5:
                return InputType.TYPE_NUMBER_FLAG_DECIMAL;
            case 6:
                return InputType.TYPE_CLASS_PHONE;
            case 7:
                return InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;
            case 8:
                return InputType.TYPE_TEXT_VARIATION_URI;
            case 9:
                return InputType.TYPE_CLASS_DATETIME;
        }
        return InputType.TYPE_CLASS_TEXT;
    }
    private Bitmap getLogo(int androidKey){
        switch (androidKey){
            case 1:
                return BitmapFactory.decodeResource(context.getResources(),R.drawable.mobile);
            case 2:
                return BitmapFactory.decodeResource(context.getResources(),R.drawable.mobile);
            case 3:
                return BitmapFactory.decodeResource(context.getResources(),R.drawable.mobile);
            case 4:
                return BitmapFactory.decodeResource(context.getResources(),R.drawable.mobile);
            case 5:
                return BitmapFactory.decodeResource(context.getResources(),R.drawable.mobile);
            case 6:
                return BitmapFactory.decodeResource(context.getResources(),R.drawable.mobile);
            case 7:
                return BitmapFactory.decodeResource(context.getResources(),R.drawable.mobile);
            case 8:
                return BitmapFactory.decodeResource(context.getResources(),R.drawable.mobile);
            case 9:
                return BitmapFactory.decodeResource(context.getResources(),R.drawable.mobile);
        }
        return BitmapFactory.decodeResource(context.getResources(),R.drawable.mobile);
    }
    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static class AdapterMember extends RecyclerView.ViewHolder {

        public TextView lblName;
        public TextView lblNumber;
        public ImageView imgDelete;
        public ImageView imgCallLogo;
        public CardView cardViewMain;

        public AdapterMember(View itemView){
            super(itemView);
            lblName = itemView.findViewById(R.id.lblName);
            lblNumber = itemView.findViewById(R.id.lblNumber);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            imgCallLogo = itemView.findViewById(R.id.imgCallLogo);
            cardViewMain = itemView.findViewById(R.id.cardViewMain);
        }

    }
}
