package com.behincom.behincome.Activityes.Activities;

import android.widget.Toast;

import com.behincom.behincome.Accesories.Setting;
import com.behincom.behincome.Datas.Activityes.Invoice;
import com.behincom.behincome.Datas.Activityes.InvoiceImage;
import com.behincom.behincome.Datas.Keys.ResponseMessageType;
import com.behincom.behincome.Datas.Result.SimpleResponse;
import com.behincom.behincome.WebRequest.RWInterface;
import com.behincom.behincome.WebRequest.Retrofite;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class csInvoiceImageUploader {

    RWInterface rInterface = Retrofite.getClient().create(RWInterface.class);

    public void setOnUploadListener(onUploaded onLoad, onFailUpload onFailed) {
        this.onUploadListener = onLoad;
        this.onFaileUploadListener = onFailed;
    }
    public interface onUploaded{
        void onUpload(List<HashMap<String, Object>> Invoices);
    }
    public interface onFailUpload {
        void onFailed(String Error);
    }
    private onUploaded onUploadListener;
    private onFailUpload onFaileUploadListener;
    private void UploadListener(List<HashMap<String, Object>> Invoices){
        onUploadListener.onUpload(Invoices);
    }
    private void FailedLoad(String Error){
        onFaileUploadListener.onFailed(Error);
    }

    private List<Invoice> lInvoice = new ArrayList<>();
    private int InvoiceSize = 0;
    private int CurrentInvoice = 0;
    private String Err = "";
    private boolean isError = false;
    private List<HashMap<String, Object>> lMapInvoice = new ArrayList<>();
    protected csInvoiceImageUploader(List<Invoice> lInvoicer){
        InvoiceSize = lInvoicer.size();
        lInvoice.addAll(lInvoicer);
        Uploader(lInvoicer.get(CurrentInvoice));
    }
    private void Uploader(Invoice invoice){
        Invoice mInvoice = invoice;
        MultipartBody.Part[] body = new MultipartBody.Part[mInvoice.InvoiceImage.size()];
        for (int i = 0; i < mInvoice.InvoiceImage.size(); i++) {
            File file = new File(mInvoice.InvoiceImage.get(i).ImageFilename);
            RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"), file);
            body[i] = MultipartBody.Part.createFormData("image" + i, file.getName(), surveyBody);
        }

//        mInvoice.InvoiceImage = new ArrayList<>();

        Call<SimpleResponse> addInvoiceImage = rInterface.RQAddInvoicePic(Setting.getToken(), Setting.getBMMUserID(), body);
        addInvoiceImage.enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                if (response.isSuccessful()) {
                    SimpleResponse simple = response.body();
                    if (simple.Type.equalsIgnoreCase(ResponseMessageType.Success.toString())) {
                        try {
                            List<HashMap<String, Object>> lMapURLs = new ArrayList<>();
                            Object[] keys = response.body().AdditionalData.keySet().toArray();
                            for (Object data : keys) {
                                String val = response.body().AdditionalData.get(data.toString()).toString();
//                                InvoiceImage lImage = new InvoiceImage();
//                                lImage.ImageFilename = val;
//                                mInvoice.InvoiceImage.add(lImage);
                                HashMap<String, Object> mapURLs = new HashMap<>();
                                mapURLs.put("InvoiceFileName", val);
                                lMapURLs.add(mapURLs);
                            }
                            HashMap<String, Object> mapInvoice = new HashMap<>();
                            mapInvoice.put("InvoiceNumber", lInvoice.get(CurrentInvoice).InvoiceNumber);
                            mapInvoice.put("InvoiceMarketingProductID", lInvoice.get(CurrentInvoice).InvoiceMarketingProductID);
                            mapInvoice.put("InvoiceActivityID", lInvoice.get(CurrentInvoice).InvoiceActivityID);
                            mapInvoice.put("InvoicePrice", lInvoice.get(CurrentInvoice).InvoicePrice);
                            mapInvoice.put("InvoiceDescription", lInvoice.get(CurrentInvoice).InvoiceDescription);
                            mapInvoice.put("InvoiceImages", lMapURLs);
                            lMapInvoice.add(mapInvoice);

                            CurrentInvoice++;
                            if(CurrentInvoice < InvoiceSize){
                                Uploader(lInvoice.get(CurrentInvoice));
                            }else
                                UploadListener(lMapInvoice);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (simple.Type.equalsIgnoreCase(ResponseMessageType.Error.toString())) {
                        for (Map.Entry<String, Object> entry : simple.Errors.entrySet()) {
                            Err = Err + entry.getValue().toString() + ", ";
                        }
                        if (Err.length() > 2)
                            Err = Err.substring(0, Err.length() - 2);
                        isError = true;
                    }
                }
                if(isError)
                    FailedLoad(Err);
            }

            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {
                Err = Err + ", " + t.getMessage();
                isError = true;
                CurrentInvoice++;
                if(CurrentInvoice < InvoiceSize){
                    Uploader(lInvoice.get(CurrentInvoice));
                }else
                    UploadListener(lMapInvoice);
            }
        });
    }

}
