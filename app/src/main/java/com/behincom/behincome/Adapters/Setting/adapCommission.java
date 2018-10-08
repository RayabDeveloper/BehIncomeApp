package com.behincom.behincome.Adapters.Setting;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.behincom.behincome.Activityes.Setting.Pdoructs.fragAddProducts;
import com.behincom.behincome.Datas.Marketing.MarketingProductCommissions;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;

import java.util.List;

public class adapCommission extends RecyclerView.Adapter<adapCommission.AdapterMember> {

    Context context;
    RSQLite SQL = new RSQLite();

    public List<MarketingProductCommissions> lList;

    public adapCommission(List<MarketingProductCommissions> lList) {
        this.lList = lList;
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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_add_product_commission, parent, false);

        return new AdapterMember(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final AdapterMember holder, final int position) {
        TextView lblFromTitle = holder.lblFromTitle;
        TextView lblFromNumber = holder.lblFromNumber;
        TextView lblToTitle = holder.lblToTitle;
        TextView lblToNumber = holder.lblToNumber;
        TextView lblPercent = holder.lblPercent;
        final EditText txtToNumber = holder.txtToNumber;
        final EditText txtPercent = holder.txtPercent;
        ImageView imgDelete = holder.imgDelete;

//        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
//        lblFromTitle.setTypeface(tf);
//        lblFromNumber.setTypeface(tf);
//        lblToTitle.setTypeface(tf);
//        lblToNumber.setTypeface(tf);
//        txtToNumber.setTypeface(tf);
//        lblPercent.setTypeface(tf);
//        txtPercent.setTypeface(tf);

        txtToNumber.setTransformationMethod(null);
        txtPercent.setTransformationMethod(null);
        txtToNumber.requestFocus();
        InputMethodManager imm = (InputMethodManager) txtToNumber.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(txtToNumber, 0);

        if (position == 0) lblFromNumber.setText("0");
        else {
            Double dPrice = (double)(lList.get(position - 1).CommissionPriceTo + 1);
            double ppPrice = dPrice;
            lList.get(position).CommissionPriceFrom = (long)ppPrice;
            int Price = dPrice.intValue();
            String Number = Integer.toString(Price);
            try {
                String value = Number.replace(",", "");
                String reverseValue = new StringBuilder(value).reverse().toString();
                StringBuilder finalValue = new StringBuilder();
                for (int i = 1; i <= reverseValue.length(); i++) {
                    char val = reverseValue.charAt(i - 1);
                    finalValue.append(val);
                    if (i % 3 == 0 && i != reverseValue.length() && i > 0) {
                        finalValue.append(",");
                    }
                }
                Number = finalValue.reverse().toString();
            } catch (Exception ignored) {
            }
            lblFromNumber.setText(Number);
        }
        if (position == lList.size() - 1) {
            lblToNumber.setVisibility(View.GONE);
            txtToNumber.setVisibility(View.VISIBLE);
            imgDelete.setVisibility(View.VISIBLE);
            lblPercent.setVisibility(View.GONE);
            txtPercent.setVisibility(View.VISIBLE);

            Double dPrice = Double.parseDouble(Long.toString((lList.get(position).CommissionPriceTo)));
            int Price = dPrice.intValue();
            String Number = Integer.toString(Price);
            try {
                String value = Number.replace(",", "");
                String reverseValue = new StringBuilder(value).reverse().toString();
                StringBuilder finalValue = new StringBuilder();
                for (int i = 1; i <= reverseValue.length(); i++) {
                    char val = reverseValue.charAt(i - 1);
                    finalValue.append(val);
                    if (i % 3 == 0 && i != reverseValue.length() && i > 0) {
                        finalValue.append(",");
                    }
                }
                Number = finalValue.reverse().toString();
            } catch (Exception ignored) {
            }
            if (Integer.parseInt(Number.replace(",", "")) == 0) Number = "";
            txtToNumber.setText(Number);
            int per = lList.get(position).CommissionPercent;
            String Perc = "";
            if (per > 0) Perc = Integer.toString(per);
            txtPercent.setText(Perc);
        } else {
            lblToNumber.setVisibility(View.VISIBLE);
            txtToNumber.setVisibility(View.GONE);
            imgDelete.setVisibility(View.GONE);
            lblPercent.setVisibility(View.VISIBLE);
            txtPercent.setVisibility(View.GONE);

            Double dPrice = Double.parseDouble(Long.toString((lList.get(position).CommissionPriceTo)));
            int Price = dPrice.intValue();
            String Number = Integer.toString(Price);
            try {
                String value = Number.replace(",", "");
                String reverseValue = new StringBuilder(value).reverse().toString();
                StringBuilder finalValue = new StringBuilder();
                for (int i = 1; i <= reverseValue.length(); i++) {
                    char val = reverseValue.charAt(i - 1);
                    finalValue.append(val);
                    if (i % 3 == 0 && i != reverseValue.length() && i > 0) {
                        finalValue.append(",");
                    }
                }
                Number = finalValue.reverse().toString();
            } catch (Exception ignored) {
            }
            lblToNumber.setText(Number);
            lblPercent.setText(Integer.toString(lList.get(position).CommissionPercent) + "%");
        }
        txtPercent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int Percent = 0;
                if (txtPercent.getText().toString().length() == 0) Percent = 0;
                else Percent = Integer.parseInt(txtPercent.getText().toString());
                lList.get(position).CommissionPercent = (Percent);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        txtToNumber.addTextChangedListener(new TextWatcher() {
            boolean isManualChange = false;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isManualChange) {
                    isManualChange = false;
                    return;
                }
                try {
                    String value = txtToNumber.getText().toString().replace(",", "");
                    String reverseValue = new StringBuilder(value).reverse().toString();
                    StringBuilder finalValue = new StringBuilder();
                    for (int i = 1; i <= reverseValue.length(); i++) {
                        char val = reverseValue.charAt(i - 1);
                        finalValue.append(val);
                        if (i % 3 == 0 && i != reverseValue.length() && i > 0) {
                            finalValue.append(",");
                        }
                    }
                    isManualChange = true;
                    txtToNumber.setText(finalValue.reverse());
                    txtToNumber.setSelection(finalValue.length());
                } catch (Exception e) {
                    // Do nothing since not a number
                }


                double Price = 0;
                if (txtToNumber.getText().toString().length() == 0) Price = 0;
                else Price = Double.parseDouble(txtToNumber.getText().toString().replace(",", ""));
                fragAddProducts.lCommission.get(position).CommissionPriceTo = (long)Price;
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragAddProducts.lCommission.remove(position);
                fragAddProducts act = new fragAddProducts();
                act.RefreshAdapter();
            }
        });
    }

    public static class AdapterMember extends RecyclerView.ViewHolder {

        public TextView lblFromTitle;
        public TextView lblFromNumber;
        public TextView lblToTitle;
        public TextView lblToNumber;
        public TextView lblPercent;
        public EditText txtToNumber;
        public EditText txtPercent;
        public ImageView imgDelete;

        public AdapterMember(View itemView) {
            super(itemView);
            lblFromTitle = itemView.findViewById(R.id.lblFromTitle);
            lblFromNumber = itemView.findViewById(R.id.lblFromNumber);
            lblToTitle = itemView.findViewById(R.id.lblToTitle);
            lblToNumber = itemView.findViewById(R.id.lblToNumber);
            lblPercent = itemView.findViewById(R.id.lblPercent);
            txtToNumber = itemView.findViewById(R.id.txtToNumber);
            txtPercent = itemView.findViewById(R.id.txtPercent);
            imgDelete = itemView.findViewById(R.id.imgDelete);
        }

    }
}
