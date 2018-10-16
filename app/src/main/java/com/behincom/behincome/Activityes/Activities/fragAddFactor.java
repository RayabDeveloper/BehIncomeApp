package com.behincom.behincome.Activityes.Activities;


import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.behincom.behincome.Accesories.ItemDecoration;
import com.behincom.behincome.Accesories.Setting;
import com.behincom.behincome.Adapters.Activities.adapFactorPic;
import com.behincom.behincome.Adapters.SpinAdapter;
import com.behincom.behincome.Datas.Activityes.Activities;
import com.behincom.behincome.Datas.Activityes.Invoice;
import com.behincom.behincome.Datas.Activityes.InvoiceImage;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.Marketing.MarketingProducts;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class fragAddFactor extends Fragment {

    static Context context;
    RSQLite SQL = new RSQLite();
    RSQLGeter geter = new RSQLGeter();
    SpinAdapter spinAdapter_Product;
    Dialog mDialog;
    adapFactorPic adapter;
    actActivities act = new actActivities();

    TextView lblTitle;
    TextView lblAccept;
    TextView lblPic;
    ImageView imgBack;
    ImageView btnCheck;
    public static EditText txtFactorNumber;
    public static EditText txtPrice;
    public static EditText txtDetails;
    Spinner spinProduct;
    CardView cardView;
    FloatingActionButton btnTakePic;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView lstMain;

    public static String name = "", price = "", des = "";
    public static Activities Activity = new Activities();
    public static int currentId = 0;
    public static List<Invoice> lFactores = new ArrayList<>();
    public static int position = 0;
    public static boolean toEdit = false;

    public static int ProductId = 0;

    List<MarketingProducts> lProduct = new ArrayList<>();
    public static List<String> lInvoiceImagePath = new ArrayList<>();

    boolean isGrant = false;
    public static String Name = "", Details = "", enterTime = "", exitTime = "";
    public static int spin1 = 0, spin2 = 0, ActType = 0;

    public static fragAddFactor newInstance(Context mContext) {
        fragAddFactor fragment = new fragAddFactor();
        context = mContext;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_add_factor, container, false);

        lblTitle = view.findViewById(R.id.lblTitle);
        lblPic = view.findViewById(R.id.lblPic);
        btnCheck = view.findViewById(R.id.btnCheck);
        txtFactorNumber = view.findViewById(R.id.txtFactorNumber);
        txtPrice = view.findViewById(R.id.txtPrice);
        txtDetails = view.findViewById(R.id.txtDetails);
        lblAccept = view.findViewById(R.id.lblAccept);
        imgBack = view.findViewById(R.id.imgBack);
        spinProduct = view.findViewById(R.id.spinProduct);
        cardView = view.findViewById(R.id.cardView);
        btnTakePic = view.findViewById(R.id.btnTakePic);
        mLayoutManager = new LinearLayoutManager(context);
        lstMain = view.findViewById(R.id.lstMain);

        txtFactorNumber.setTransformationMethod(null);
        txtPrice.setTransformationMethod(null);

        lblTitle.setText("فاکتور");
        imgBack.setVisibility(View.VISIBLE);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragAddTask.Namee = Name;
                fragAddTask.Details = Details;
                fragAddTask.enterTime = enterTime;
                fragAddTask.spin1 = spin1;
                fragAddTask.spin2 = spin2;
                fragAddTask.Type = ActType;
                fragAddTask.lFactor = lFactores;
                fragAddTask.currentId = currentId;
                fragAddTask.fac = false;
                act.getFragByState(FragmentState.AddTask);
            }
        });

        lProduct = geter.getList(MarketingProducts.class);
        MarketingProducts data = new MarketingProducts();
        data.MarketingProductTitle = ("انتخاب محصول");
        lProduct.add(0, data);
        spinAdapter_Product = new SpinAdapter(context, lProduct, "MarketingProductTitle");
        spinProduct.setAdapter(spinAdapter_Product);

        btnTakePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PicManager();
            }
        });
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtPrice.getText().toString().length() > 0) {
                    Invoice factor = new Invoice();
                    factor.InvoiceNumber = (txtFactorNumber.getText().toString());
                    factor.InvoicePrice = (Integer.parseInt(txtPrice.getText().toString().replace(",", "")));
                    List<InvoiceImage> lII = new ArrayList<>();
                    for (String data : lInvoiceImagePath) {
                        InvoiceImage MData = new InvoiceImage();
                        MData.ImageFilename = data;

                        lII.add(MData);
                    }
                    factor.InvoiceImage = (lII);
                    factor.InvoiceDescription = (txtDetails.getText().toString());
                    factor.InvoiceMarketingProductID = (spinAdapter_Product.getIdItem(spinProduct.getSelectedItemPosition(), "MarketingProductID"));
                    if (toEdit) {
                        lFactores.get(position).InvoiceNumber = (txtFactorNumber.getText().toString());
                        lFactores.get(position).InvoicePrice = (Integer.parseInt(txtPrice.getText().toString().replace(",", "")));
                        List<InvoiceImage> lIIt = new ArrayList<>();
                        for (String data : lInvoiceImagePath) {
                            InvoiceImage MData = new InvoiceImage();
                            MData.ImageFilename = data;

                            lIIt.add(MData);
                        }
                        lFactores.get(position).InvoiceImage = (lIIt);
                        lFactores.get(position).InvoiceMarketingProductID = (spinAdapter_Product.getIdItem(spinProduct.getSelectedItemPosition(), "id"));
                        lFactores.get(position).InvoiceDescription = (txtDetails.getText().toString());
                    } else {
                        lFactores.add(factor);
                    }
                }//else Toast
                fragAddTask.Namee = Name;
                fragAddTask.Details = Details;
                fragAddTask.enterTime = enterTime;
                fragAddTask.spin1 = spin1;
                fragAddTask.spin2 = spin2;
                fragAddTask.lFactor = lFactores;
                fragAddTask.fac = true;
                fragAddTask.Type = ActType;
                fragAddTask.currentId = currentId;
                act.getFragByState(FragmentState.AddTask);
            }
        });
        txtPrice.addTextChangedListener(new TextWatcher() {
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
                    String value = txtPrice.getText().toString().replace(",", "");
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
                    txtPrice.setText(finalValue.reverse());
                    txtPrice.setSelection(finalValue.length());
                } catch (Exception Ex) {
                    String Er = Ex.getMessage();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.getFragByState(FragmentState.AddTask);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        RefreshFactorPic();
        isGrant = askForPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 3);
        if (ProductId > 0)
            spinProduct.setSelection(spinAdapter_Product.getItemPosition("MarketingProductID", Integer.toString(ProductId)));
        if (name.length() > 0) txtFactorNumber.setText(name);
        if (price.length() > 0) txtPrice.setText(price);
        if (des != null) {
            if (des.length() > 0) txtDetails.setText(des);
        }
    }

    private void PicManager() {
        mDialog = new Dialog(context);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.setContentView(R.layout.dialog_takepic);
        Objects.requireNonNull(mDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

        TextView lblTakePhoto = mDialog.findViewById(R.id.lblTakePhoto);
        TextView lblChooseFromGallery = mDialog.findViewById(R.id.lblChooseFromGallery);
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/ir_sans.ttf");
        lblTakePhoto.setTypeface(tf);
        lblChooseFromGallery.setTypeface(tf);

        lblTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, 0);//zero can be replaced with any action code
                mDialog.dismiss();
            }
        });
        lblChooseFromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, 1);//one can be replaced with any action code
                mDialog.dismiss();
            }
        });
        mDialog.show();
    }

    private boolean askForPermission(String permission, int rCode) {
        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, rCode);
                return false;
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, rCode);
                return false;
            }
        } else {
            return true;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap photo = (Bitmap) imageReturnedIntent.getExtras().get("data");
                        Uri tempUri = getImageUri(getActivity(), photo);
                        File finalFile = new File(getRealPathFromURI(tempUri));

                        lInvoiceImagePath.add(finalFile.getPath());
                        RefreshFactorPic();
                    } catch (Exception Ex) {
                        String Er = Ex.getMessage();
                    }
                }
                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    try {
                        Uri pickedImage = imageReturnedIntent.getData();
                        String fPath = getPath(context.getApplicationContext(), pickedImage);
                        lInvoiceImagePath.add(fPath);
                        RefreshFactorPic();
                    } catch (Exception Ex) {
                        String Er = Ex.getMessage();
                    }
                }
                break;
        }
    }
    private Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    private String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getActivity().getContentResolver() != null) {
            Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }
    private String getPath(Context context, Uri uri) {
        String result = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(proj[0]);
                result = cursor.getString(column_index);
            }
            cursor.close();
        }
        if (result == null) {
            result = "Not found";
        }
        return result;
    }

    private void RefreshFactorPic() {
        adapter = new adapFactorPic(lInvoiceImagePath, context);
        lstMain.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        lstMain.setLayoutManager(mLayoutManager);
        lstMain.addItemDecoration(ItemDecoration.getDecoration(context));
        lstMain.setItemAnimator(new DefaultItemAnimator());
        lstMain.setAdapter(adapter);
    }
    private void Finisher(){
        name = "";
        price = "";
        des = "";
        position = 0;
        toEdit = false;
        ProductId = 0;
        lProduct = new ArrayList<>();
        lInvoiceImagePath = new ArrayList<>();
        isGrant = false;
        Name = "";
        Details = "";
        enterTime = "";
        spin1 = 0;
        spin2 = 0;
        ActType = 0;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Finisher();
    }

}
