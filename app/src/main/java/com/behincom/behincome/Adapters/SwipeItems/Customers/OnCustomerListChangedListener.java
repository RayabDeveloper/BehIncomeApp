package com.behincom.behincome.Adapters.SwipeItems.Customers;

import com.behincom.behincome.Datas.BaseData.Basic_CustomerStates;

import java.util.List;

//public interface OnCustomerListChangedListener<T>{
//    void onNoteListChanged(List<T> customers);
//}
public interface OnCustomerListChangedListener{
    void onNoteListChanged(List<Basic_CustomerStates> customers, int fromPosition, int toPosition);
}