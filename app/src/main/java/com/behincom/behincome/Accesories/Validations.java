package com.behincom.behincome.Accesories;

import android.content.Context;

import com.behincom.behincome.app.AppController;

public class Validations {

    Context context = AppController.getContext;

    public boolean Nationality(String nCode){
        int Ix = 10;
        int EQ = 0;
        for (int i = 0; i < nCode.length() - 1; i++)
        {
            int asd = Integer.parseInt(nCode.substring(i, i + 1));
            EQ += asd * Ix;
            Ix--;
        }
        EQ = EQ % 11;
        if (EQ >= 2)
        {
            if ((11 - EQ) == Integer.parseInt(nCode.substring(nCode.length() - 1, nCode.length())))
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        else
        {
            if (Integer.parseInt(nCode.substring(nCode.length() - 1, nCode.length())) == EQ)
            {
                return false;
            }
            else
            {
                return true;
            }
        }
    }
    public boolean Phone(String Phone){
        if(Phone.length() == 11) {
            if (Phone.substring(0, 1).equals("0")) {
                if (Phone.substring(1, 2).equalsIgnoreCase("9")) {
                    String aa = Phone.substring(2, 4);
                    if (Integer.parseInt(aa) >= 1) {
                        if (Phone.substring(4).length() == 7) {
                            return true;
                        }else
                            return false;
                    }else
                        return false;
                }else
                    return false;
            }else
                return false;
        }else
            return false;
    }

}
