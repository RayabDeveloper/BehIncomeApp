package com.behincom.behincome.Adapters.Customer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.behincom.behincome.Accesories.DateConverter;
import com.behincom.behincome.Activityes.Activities.actActivities;
import com.behincom.behincome.Activityes.Activities.fragAddFactor;
import com.behincom.behincome.Activityes.Activities.fragAddTask;
import com.behincom.behincome.Activityes.Customer.fragCustomerShow;
import com.behincom.behincome.Adapters.Activities.adapFactors;
import com.behincom.behincome.Datas.Activityes.Activities;
import com.behincom.behincome.Datas.Activityes.Invoice;
import com.behincom.behincome.Datas.BaseData.Basic_ActResults;
import com.behincom.behincome.Datas.BaseData.Basic_Acts;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.Marketing.MarketingProducts;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.devsmart.android.ui.HorizontalListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.behincom.behincome.WebRequest.Retrofite.BASE;

public class adapCustomerInvoice extends RecyclerView.Adapter<adapCustomerInvoice.AdapterMember>{

    private Context context;
    private RSQLite SQL = new RSQLite();
    private RSQLGeter geter = new RSQLGeter();
    private Dialog piDialog, largePicDialog;
    private actActivities act = new actActivities();

    public List<Invoice> lList;
    public adapCustomerInvoice(Context mContext, List<Invoice> lList){
        this.context = mContext;
        this.lList = lList;
    }

    @Override
    public int getItemCount() {
        int a = 1;
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
                .inflate(R.layout.items_customer_invoice, parent, false);

        return new AdapterMember(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final AdapterMember holder, @SuppressLint("RecyclerView") final int position) {
        TextView lblNumber = holder.lblNumber;
        TextView lblProduct = holder.lblProduct;
        TextView lblPrice = holder.lblPrice;
        TextView lblDescription = holder.lblDescription;
        HorizontalListView lstInvoiceImages = holder.lstInvoiceImages;
        LinearLayout linDetails = holder.linDetails;
        CardView cardViewMain = holder.cardViewMain;

        lblNumber.setText(lList.get(position).InvoiceNumber);
        RSQLGeter geter = new RSQLGeter();
        List<MarketingProducts> lProduct = geter.getList(MarketingProducts.class, "WHERE MarketingProductID='" + lList.get(position).InvoiceMarketingProductID + "'");
        if(lProduct.size() > 0)
            lblProduct.setText(lProduct.get(0).MarketingProductTitle);
        else
            lblProduct.setText("خطا");

        String Price = Integer.toString(lList.get(position).InvoicePrice).replace(".", "");
        try {
            String value = Price.replace(",", "");
            String reverseValue = new StringBuilder(value).reverse().toString();
            StringBuilder finalValue = new StringBuilder();
            for (int i = 1; i <= reverseValue.length(); i++) {
                char val = reverseValue.charAt(i - 1);
                finalValue.append(val);
                if (i % 3 == 0 && i != reverseValue.length() && i > 0) {
                    finalValue.append(",");
                }
            }
            Price = finalValue.reverse().toString();
        } catch (Exception Ex) {
            String Er = Ex.getMessage();
        }
        lblPrice.setText(Price);
        String Description = "";
        if(lList.get(position).InvoiceDescription != null) {
            Description = lList.get(position).InvoiceDescription;
            if (Description.length() > 0) {
                linDetails.setVisibility(View.VISIBLE);
            } else {
                linDetails.setVisibility(View.GONE);
            }
            lblDescription.setText(Description);
        }else{
            linDetails.setVisibility(View.GONE);
        }
        if(lList.get(position).InvoiceImage.size() > 0){
            lstInvoiceImages.setVisibility(View.VISIBLE);
            adapInvoiceImages adapter = new adapInvoiceImages(context, lList.get(position).InvoiceImage);
            lstInvoiceImages.setAdapter(adapter);
        }else{
            lstInvoiceImages.setVisibility(View.GONE);
        }
        lstInvoiceImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int positionn, long id) {
                largePicDialog = new Dialog(context);
                largePicDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                largePicDialog.setCancelable(true);
                largePicDialog.setCanceledOnTouchOutside(true);
                largePicDialog.setContentView(R.layout.dialog_customer_gallery_big);
                Objects.requireNonNull(largePicDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);


                TextView btnCancell = largePicDialog.findViewById(R.id.lblCancell);
                TextView btnAccept = largePicDialog.findViewById(R.id.lblAccept);
                final ImageView img = largePicDialog.findViewById(R.id.imgBig);

                btnCancell.setVisibility(View.GONE);
                btnAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        largePicDialog.dismiss();
                    }
                });
                String PhotoURL = "";
                try {
                    PhotoURL = lList.get(position).InvoiceImage.get(positionn).ImageFilename;
                    if (PhotoURL.length() > 5)
                        PhotoURL = BASE + "Uploads/CustomerImages/" + PhotoURL;
                    else
                        PhotoURL = Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.logo).toString();
                } catch (Exception e) {
                    PhotoURL = Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.logo).toString();
                }
                Glide.with(context).load(PhotoURL).asBitmap().centerCrop().into(new BitmapImageViewTarget(img) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        img.setImageDrawable(circularBitmapDrawable);
                    }
                });
                largePicDialog.show();
            }
        });

        cardViewMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                fragAddFactor.lFactorImage = lList.get(position).pic();//todo todo todo
                fragAddFactor.name = lList.get(position).InvoiceNumber;
                String mPricer = "";
                try {
                    String value = Double.toString(lList.get(position).InvoicePrice).replace(".", "");
                    String reverseValue = new StringBuilder(value).reverse().toString();
                    StringBuilder finalValue = new StringBuilder();
                    for (int i = 1; i <= reverseValue.length(); i++) {
                        char val = reverseValue.charAt(i - 1);
                        finalValue.append(val);
                        if (i % 3 == 0 && i != reverseValue.length() && i > 0) {
                            finalValue.append(",");
                        }
                    }
                    mPricer = finalValue.reverse().toString();
                } catch (Exception Ex) {
                    String Er = Ex.getMessage();
                }
                fragAddFactor.price = mPricer;
                fragAddFactor.des = lList.get(position).InvoiceDescription;
                fragAddFactor.ProductId = lList.get(position).InvoiceMarketingProductID;
                fragAddFactor.position = position;
                fragAddFactor.toEdit = true;//todo factor todo



//                fragAddFactor.Name = fragAddTask.Namee;
//                fragAddFactor.Details = fragAddTask.Details;
//                fragAddFactor.enterTime = fragAddTask.lblTimeCondition.getText().toString();
//                fragAddFactor.spin1 = fragAddTask.ActSelected;
//                fragAddFactor.spin2 = fragAddTask.ResultSelected;
//                fragAddFactor.currentId = fragAddTask.currentId;
//                fragAddFactor.lFactores = fragAddTask.lFactor;
//                fragAddFactor.ActType = fragAddTask.Type;



                act.getFragByState(FragmentState.AddFactor);
            }
        });
        cardViewMain.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                piDialog = new Dialog(context);
                piDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                piDialog.setCancelable(true);
                piDialog.setCanceledOnTouchOutside(true);
                piDialog.setContentView(R.layout.dialog_accept_canncell);
                Objects.requireNonNull(piDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

                TextView Title = piDialog.findViewById(R.id.lblTitle);
                TextView lblCancell = piDialog.findViewById(R.id.lblCancell);
                TextView lblAccept = piDialog.findViewById(R.id.lblAccept);

//                Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
//                Title.setTypeface(tf);lblCancell.setTypeface(tf);lblAccept.setTypeface(tf);

                lblCancell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        piDialog.dismiss();
                    }
                });
                lblAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        piDialog.show();
//                        fragAddTask.lFactor.remove(position);
                        notifyDataSetChanged();
                        piDialog.dismiss();
                    }
                });
                return false;
            }
        });
    }

    public static class AdapterMember extends RecyclerView.ViewHolder{

        public TextView lblNumber;
        public TextView lblProduct;
        public TextView lblPrice;
        public TextView lblDescription;
        public HorizontalListView lstInvoiceImages;
        public LinearLayout linDetails;
        public CardView cardViewMain;

        public AdapterMember(View itemView){
            super(itemView);
            lblNumber = itemView.findViewById(R.id.lblNumber);
            lblProduct = itemView.findViewById(R.id.lblProduct);
            lblPrice = itemView.findViewById(R.id.lblPrice);
            lblDescription = itemView.findViewById(R.id.lblDescription);
            lstInvoiceImages = itemView.findViewById(R.id.lstInvoiceImages);
            linDetails = itemView.findViewById(R.id.linDetails);
            cardViewMain = itemView.findViewById(R.id.cardViewMain);
        }

    }

}
