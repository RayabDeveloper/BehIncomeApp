package com.behincom.behincome.WebRequest;

import com.behincom.behincome.Datas.Activityes.Invoice;
import com.behincom.behincome.Datas.BaseData.BasicDatas;
import com.behincom.behincome.Datas.BaseData.Basic_Cities;
import com.behincom.behincome.Datas.BaseData.Basic_Provinces;
import com.behincom.behincome.Datas.Customer.Customers;
import com.behincom.behincome.Datas.Customer.MyCustomers;
import com.behincom.behincome.Datas.Marketing.MarketingDatas;
import com.behincom.behincome.Datas.Profile.BussinessManagerMarketing;
import com.behincom.behincome.Datas.Profile.Marketers;
import com.behincom.behincome.Datas.Result.Loginer;
import com.behincom.behincome.Datas.Result.SimpleResponse;
import com.behincom.behincome.Datas.Roles.User_Roles;
import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RWInterface<T> {

    final String ContentType = "Content-Type: application/json";
    final String ContentType2 = "Content-Type: application/x-www-form-urlencoded";
    final String HeaderToken = "Authorization";

    final String Geocode = "geocode";
    final String json = "json";
    final String ControllerActionManager_Geocode_1 = Geocode + "/" + json;


    final String Insert = "Insert";
    final String Update = "Update";
    final String Delete = "Delete";
    final String ChangePosition = "ChangePosition";


    final String Account = "account";
//    final String LoginByPhone = "ResendMobileVerificationCodeAsync";
    final String LoginByPhone = "LoginByPhone";
    final String RequestPhoneVerificationCodeCall = "RecallMobileVerificationCodeAsync";
    final String RequestVerificationPhoneNUmber = "VerifyPhoneNumber";
    final String register = "register";
    final String login = "login";
    final String RequestForgotPasswordToken = "RequestForgotPasswordToken";
    final String ForgotPassword = "ForgotPassword";
//    final String ControllerActionManager_Account_1 = Account + "/" + LoginByPhone;
    final String ControllerActionManager_Account_1 = Account + "/" + LoginByPhone;
    final String ControllerActionManager_Account_7 = Account + "/" + RequestPhoneVerificationCodeCall;
    final String ControllerActionManager_Account_2 = Account + "/" + RequestVerificationPhoneNUmber;
    final String ControllerActionManager_Account_3 = Account + "/" + register;
    final String ControllerActionManager_Account_4 = Account + "/" + login;
    final String ControllerActionManager_Account_5 = Account + "/" + RequestForgotPasswordToken;
    final String ControllerActionManager_Account_6 = Account + "/" + ForgotPassword;

    final String Profile = "profile";
    final String PostUserImage = "PostUserImage";
    final String GetMyBusinessManagers = "GetMyBusinessManagers";
    final String GetMyUserID = "GetMyUserID";
    final String GetUserProfile = "GetUserProfile";
    final String GetUserProfileByCode = "GetUserProfileByCode";
    final String EditProfile = "EditProfile";
    final String EditProfileAdmin = "EditProfileAdmin";
    final String AddUserToBussinessManager = "AddUserToBussinessManager";
    final String GetBusinessManagerRoles = "GetBusinessManagerRoles";
    final String AddUserRole = "AddUserRole";
    final String EditUserRole = "EditUserRole";
    final String GetBusinessManagerMarketers = "GetBusinessManagerMarketers";
    final String AddMarketer = "AddMarketer";
    final String ChangeMarketerCondition = "ChangeMarketerCondition";
    final String ChangeMarketerRole = "ChangeMarketerRole";
    final String AddMarketerUsersAccess = "AddMarketerUsersAccess";
    final String ControllerActionManager_1 = Profile + "/" + PostUserImage;
    final String ControllerActionManager_2 = Profile + "/" + GetMyBusinessManagers;
    final String ControllerActionManager_3 = Profile + "/" + GetMyUserID;
    final String ControllerActionManager_4 = Profile + "/" + GetUserProfile;
    final String ControllerActionManager_5 = Profile + "/" + EditProfile;
    final String ControllerActionManager_6 = Profile + "/" + EditProfileAdmin;
    final String ControllerActionManager_7 = Profile + "/" + GetUserProfileByCode;
    final String ControllerActionManager_8 = Profile + "/" + AddUserToBussinessManager;
    final String ControllerActionManager_9 = Profile + "/" + GetBusinessManagerRoles;
    final String ControllerActionManager_10 = Profile + "/" + AddUserRole;
    final String ControllerActionManager_11 = Profile + "/" + AddMarketerUsersAccess;
    final String ControllerActionManager_12 = Profile + "/" + GetBusinessManagerMarketers;
    final String ControllerActionManager_13 = Profile + "/" + AddMarketer;
    final String ControllerActionManager_14 = Profile + "/" + ChangeMarketerCondition;
    final String ControllerActionManager_15 = Profile + "/" + ChangeMarketerRole;

    final String BaseData = "basedata";
    final String GetAllBaseData = "GetAllBaseData";
    final String GetAllBaseData_Ack = "GetAllBaseData_Ack";
    final String GetServerDateTime = "GetServerDateTime";
    final String GetProvinces = "GetProvinces";
    final String GetCities = "GetCities";
    final String ControllerActionManager_BaseData_1 = BaseData + "/" + GetAllBaseData;
    final String ControllerActionManager_BaseData_2 = BaseData + "/" + GetAllBaseData_Ack;
    final String ControllerActionManager_BaseData_3 = BaseData + "/" + GetServerDateTime;
    final String ControllerActionManager_BaseData_4 = BaseData + "/" + GetProvinces;
    final String ControllerActionManager_BaseData_5 = BaseData + "/" + GetCities;


    final String Marketing = "Marketing";
    final String BasicArchiveTypes = "BasicArchiveTypes";
    final String BasicContactTypes = "BasicContactTypes";
    final String BasicPersonRoles = "BasicPersonRoles";
    final String BasicCustomerStates = "BasicCustomerStates";
    final String BasicActivityFields = "BasicActivityFields";
    final String BasicActivityFieldGroups = "BasicActivityFieldGroups";
    final String BasicTags = "BasicTags";
    final String BasicTagGroups = "BasicTagGroups";
    final String BasicProperties = "BasicProperties";
    final String BasicPropertyGroups = "BasicPropertyGroups";
    final String AddTags = "AddTags";
    final String RemoveTags = "RemoveTags";
    final String GetAll = "GetAll";
    final String AddActivityFields = "AddActivityField";
    final String RemoveActivityField = "RemoveActivityField";
    final String AddCities = "AddCities";
    final String RemoveCities = "RemoveCities";
    final String AddActivityResult = "AddActResult";
    final String RemoveActResult = "RemoveActResult";
    final String AddActResultPoint = "ChangePoint";
    final String AddProduct = "AddProduct";
    final String AddProductCommission = "AddProductCommission";
    final String AddCommissionPeriod = "AddCommissionPeriod";
    final String AddVisitTour = "AddVisitTour";
    final String AddProperties = "AddProperties";
    final String RemoveProperties = "RemoveProperties";
    final String DeleteVisitTour = "DeleteVisitTour";
    final String DeletedVisitTour = "DeletedVisitTour";
    final String DeleteCommissionPeriod = "DeleteCommissionPeriod";
    final String DeletedCommissionPeriod = "DeletedCommissionPeriod";
    final String EditActivityResult = "EditActivityResult";
    final String EditProduct = "EditProduct";
    final String EditVisitTour = "EditVisitTour";
    final String EditCommissionPeriod = "EditCommissionPeriod";
    final String EditProductCommission = "EditProductCommission";
    final String ControllerActionManager_Marketing_1 = Marketing + "/" + GetAll;
    final String ControllerActionManager_Marketing_2 = Marketing + "/" + AddTags;
    final String ControllerActionManager_Marketing_23 = Marketing + "/" + RemoveTags;
    final String ControllerActionManager_Marketing_3 = Marketing + "/" + AddActivityFields;
    final String ControllerActionManager_Marketing_20 = Marketing + "/" + RemoveActivityField;
    final String ControllerActionManager_Marketing_4 = Marketing + "/" + AddCities;
    final String ControllerActionManager_Marketing_22 = Marketing + "/" + RemoveCities;
    final String ControllerActionManager_Marketing_5 = Marketing + "/" + AddActivityResult;
    final String ControllerActionManager_Marketing_55 = Marketing + "/" + RemoveActResult;
    final String ControllerActionManager_Marketing_56 = Marketing + "/" + AddActResultPoint;
    final String ControllerActionManager_Marketing_8 = Marketing + "/" + AddProduct;
    final String ControllerActionManager_Marketing_9 = Marketing + "/" + AddProductCommission;
    final String ControllerActionManager_Marketing_10 = Marketing + "/" + AddCommissionPeriod;
    final String ControllerActionManager_Marketing_11 = Marketing + "/" + AddVisitTour;
    final String ControllerActionManager_Marketing_15 = Marketing + "/" + AddProperties;
    final String ControllerActionManager_Marketing_21 = Marketing + "/" + RemoveProperties;
    final String ControllerActionManager_Marketing_16 = Marketing + "/" + DeleteVisitTour;
    final String ControllerActionManager_Marketing_17 = Marketing + "/" + DeletedVisitTour;
    final String ControllerActionManager_Marketing_18 = Marketing + "/" + DeleteCommissionPeriod;
    final String ControllerActionManager_Marketing_19 = Marketing + "/" + DeletedCommissionPeriod;
    final String ControllerActionManager_Marketing_6 = Marketing + "/" + EditActivityResult;
    final String ControllerActionManager_Marketing_7 = Marketing + "/" + EditProduct;
    final String ControllerActionManager_Marketing_12 = Marketing + "/" + EditVisitTour;
    final String ControllerActionManager_Marketing_13 = Marketing + "/" + EditCommissionPeriod;
    final String ControllerActionManager_Marketing_14 = Marketing + "/" + EditProductCommission;

    final String ControllerActionManager_Marketing_24 = BasicActivityFields + "/" + Insert;
    final String ControllerActionManager_Marketing_25 = BasicActivityFields + "/" + Update;
    final String ControllerActionManager_Marketing_26 = BasicActivityFields + "/" + Delete;
    final String ControllerActionManager_Marketing_27 = BasicTags + "/" + Insert;
    final String ControllerActionManager_Marketing_28 = BasicTags + "/" + Update;
    final String ControllerActionManager_Marketing_29 = BasicTags + "/" + Delete;
    final String ControllerActionManager_Marketing_30 = BasicProperties + "/" + Insert;
    final String ControllerActionManager_Marketing_31 = BasicProperties + "/" + Update;
    final String ControllerActionManager_Marketing_32 = BasicProperties + "/" + Delete;
    final String ControllerActionManager_Marketing_33 = BasicPropertyGroups + "/" + Insert;
    final String ControllerActionManager_Marketing_34 = BasicPropertyGroups + "/" + Update;
    final String ControllerActionManager_Marketing_35 = BasicPropertyGroups + "/" + Delete;
    final String ControllerActionManager_Marketing_36 = BasicTagGroups + "/" + Insert;
    final String ControllerActionManager_Marketing_37 = BasicTagGroups + "/" + Update;
    final String ControllerActionManager_Marketing_38 = BasicTagGroups + "/" + Delete;
    final String ControllerActionManager_Marketing_39 = BasicActivityFieldGroups + "/" + Insert;
    final String ControllerActionManager_Marketing_40 = BasicActivityFieldGroups + "/" + Update;
    final String ControllerActionManager_Marketing_41 = BasicActivityFieldGroups + "/" + Delete;
    final String ControllerActionManager_Marketing_42 = BasicCustomerStates + "/" + Insert;
    final String ControllerActionManager_Marketing_43 = BasicCustomerStates + "/" + Update;
    final String ControllerActionManager_Marketing_54 = BasicCustomerStates + "/" + ChangePosition;
    final String ControllerActionManager_Marketing_44 = BasicCustomerStates + "/" + Delete;
    final String ControllerActionManager_Marketing_45 = BasicArchiveTypes + "/" + Insert;
    final String ControllerActionManager_Marketing_46 = BasicArchiveTypes + "/" + Update;
    final String ControllerActionManager_Marketing_47 = BasicArchiveTypes + "/" + Delete;
    final String ControllerActionManager_Marketing_48 = BasicContactTypes + "/" + Insert;
    final String ControllerActionManager_Marketing_49 = BasicContactTypes + "/" + Update;
    final String ControllerActionManager_Marketing_50 = BasicContactTypes + "/" + Delete;
    final String ControllerActionManager_Marketing_51 = BasicPersonRoles + "/" + Insert;
    final String ControllerActionManager_Marketing_52 = BasicPersonRoles + "/" + Update;
    final String ControllerActionManager_Marketing_53 = BasicPersonRoles + "/" + Delete;

    final String Customer = "customer";
    final String AddCustomer = "AddCustomer";
    final String EditCustomer = "EditCustomer";
    final String DeleteCustomer = "DeleteCustomer";
    final String ChangeCustomerState = "ChangeCustomerState";
    final String ChangeCustomerStatues = "ChangeCustomerStatues";
    final String ChangeCustomersStatues = "ChangeCustomersStatues";
    final String GetMyCustomer = "GetMyCustomer";
    final String GetCustomersArchived = "GetCustomersArchived";
    final String GetCustomer = "GetCustomer";
    final String GetInCirclePointCustomer = "GetInCirclePointCustomer";
    final String AddCustomerToArchive = "AddCustomerToArchive";
    final String AddCustomersToArchive = "AddCustomersToArchive";
    final String AddCustomerToExitArchive = "AddCustomerToExitArchive";
    final String AddCustomersToExitArchive = "AddCustomersToExitArchive";
    final String AssignCustomersToMarketers = "AssignCustomersToMarketers";
    final String PostCustomerImage = "PostCustomerImage";
    final String GetCustomerFactors = "GetCustomerFactors";
    final String ControllerActionManager_Customer_1 = Customer + "/" + AddCustomer;
    final String ControllerActionManager_Customer_2 = Customer + "/" + EditCustomer;
    final String ControllerActionManager_Customer_3 = Customer + "/" + DeleteCustomer;
    final String ControllerActionManager_Customer_4 = Customer + "/" + ChangeCustomerState;
    final String ControllerActionManager_Customer_5 = Customer + "/" + ChangeCustomerStatues;
    final String ControllerActionManager_Customer_9 = Customer + "/" + AddCustomerToArchive;
    final String ControllerActionManager_Customer_11 = Customer + "/" + AddCustomersToArchive;
    final String ControllerActionManager_Customer_12 = Customer + "/" + AddCustomerToExitArchive;
    final String ControllerActionManager_Customer_13 = Customer + "/" + AddCustomersToExitArchive;
    final String ControllerActionManager_Customer_6 = Customer + "/" + GetMyCustomer;
    final String ControllerActionManager_Customer_10 = Customer + "/" + GetCustomersArchived;
    final String ControllerActionManager_Customer_7 = Customer + "/" + GetCustomer;
    final String ControllerActionManager_Customer_8 = Customer + "/" + GetInCirclePointCustomer;
    final String ControllerActionManager_Customer_14 = Customer + "/" + AssignCustomersToMarketers;
    final String ControllerActionManager_Customer_15 = Customer + "/" + PostCustomerImage;
    final String ControllerActionManager_Customer_16 = Customer + "/" + GetCustomerFactors;

    final String Activities = "activities";
    final String GetActivities = "GetActivities";
    final String GetTasks = "GetTasks";
    final String GetTasksByNowDate = "GetTasksByNowDate";
    final String GetTasksByDate = "GetTasksByDate";
    final String GetActivityByID = "GetActivityByID";
    final String GetTaskByID = "GetTaskByID";
    final String GetActivitiesByCustomerID = "GetActivitiesByCustomerID";
    final String GetTasksByCustomerID = "GetTasksByCustomerID";
    final String GetInvoiceByCustomerID = "GetInvoiceByCustomerID";
    final String AddActivityEnter = "AddActivityEnter";
    final String AddActivityEnterForTask = "AddActivityEnterForTask";
    final String AddActivityExit = "AddActivityExit";
    final String ConfirmActivity = "ConfirmActivity";
    final String SendActivity = "SendActivity";
    final String AddTask = "AddTask";
    final String AddTasks = "AddTasks";
    final String PostInvoiceImage = "PostInvoiceImage";
    final String EditTask = "EditTask";
    final String DeleteTask = "DeleteTask";
    final String ControllerActionManager_Activities_1 = Activities + "/" + GetActivities;
    final String ControllerActionManager_Activities_2 = Activities + "/" + GetTasks;
    final String ControllerActionManager_Activities_3 = Activities + "/" + GetTasksByNowDate;
    final String ControllerActionManager_Activities_4 = Activities + "/" + GetTasksByDate;
    final String ControllerActionManager_Activities_5 = Activities + "/" + GetActivityByID;
    final String ControllerActionManager_Activities_6 = Activities + "/" + GetTaskByID;
    final String ControllerActionManager_Activities_7 = Activities + "/" + GetActivitiesByCustomerID;
    final String ControllerActionManager_Activities_8 = Activities + "/" + GetTasksByCustomerID;
    final String ControllerActionManager_Activities_9 = Activities + "/" + GetInvoiceByCustomerID;
    final String ControllerActionManager_Activities_10 = Activities + "/" + AddActivityEnter;
    final String ControllerActionManager_Activities_17 = Activities + "/" + AddActivityEnterForTask;
    final String ControllerActionManager_Activities_11 = Activities + "/" + AddActivityExit;
    final String ControllerActionManager_Activities_12 = Activities + "/" + ConfirmActivity;
    final String ControllerActionManager_Activities_13 = Activities + "/" + SendActivity;
    final String ControllerActionManager_Activities_15 = Activities + "/" + AddTask;
    final String ControllerActionManager_Activities_16 = Activities + "/" + AddTasks;
    final String ControllerActionManager_Activities_14 = Activities + "/" + PostInvoiceImage;
    final String ControllerActionManager_Activities_18 = Activities + "/" + EditTask;
    final String ControllerActionManager_Activities_19 = Activities + "/" + DeleteTask;



    @GET(ControllerActionManager_Geocode_1)
    Call<SimpleResponse> RQGetLocationAddress(@Query("latlng")LatLng point, @Query("key") String Key);
    //=========================================================================================================
    //Login & Register :                            - Account -
    //=========================================================================================================
    @Headers(ContentType)
    @POST(ControllerActionManager_Account_1)
    Call<SimpleResponse> RQSendPhoneNumber(@Body HashMap<String, Object> PhoneNumber);

    @Headers(ContentType)
    @POST(ControllerActionManager_Account_7)
    Call<SimpleResponse> RQSendPhoneNumberCall(@Body HashMap<String, Object> PhoneNumber);

    @Headers(ContentType)
    @POST(ControllerActionManager_Account_2)
    Call<SimpleResponse> RQSendDigitCode(@Body HashMap<String, String> Data);

    @Headers(ContentType)
    @POST(ControllerActionManager_Account_3)
    Call<SimpleResponse> RQRegister(@Body HashMap<String, String> Data);

    @Headers(ContentType)
    @FormUrlEncoded
    @POST(ControllerActionManager_Account_4)
    Call<Loginer> RQLogin(@Header("IMEI") String IMEI, @Header("DeviceFullname") String DeviceFullname, @Header("OsVersion") String OsVersion, @Header("ProfileTypeID") String ProfileTypeID, @Field("grant_type") String grant_type, @Field("username") String username, @Field("password") String password);//grant_type=password&username=Mehdi.K&password=Password_123

    @Headers(ContentType)
    @FormUrlEncoded
    @POST(ControllerActionManager_Account_4)
    Call<Loginer> RQLogin(@Header ("AuthenticationMethod") String LoginHeader, @Header("IMEI") String IMEI, @Header("DeviceFullname") String DeviceFullname, @Header("OsVersion") String OsVersion, @Header("ProfileTypeID") String ProfileTypeID, @Field("grant_type") String grant_type, @Field("username") String username, @Field("password") String password);//grant_type=password&username=Mehdi.K&password=Password_123

    @Headers(ContentType)
    @POST(ControllerActionManager_Account_5)
    Call<SimpleResponse> RQRequestForgotPasswordToken(@Body HashMap<String, Object> Parameters);

    @Headers(ContentType)
    @POST(ControllerActionManager_Account_6)
    Call<SimpleResponse> RQForgotPassword(@Body HashMap<String, Object> Parameters);

    //=========================================================================================================
    //Profile :                                     - Profile -
    //=========================================================================================================
    @Multipart
    @POST(ControllerActionManager_1)
    Call<SimpleResponse> RQProfilePic(@Header(HeaderToken) String Token, @Part MultipartBody.Part body);

    @GET(ControllerActionManager_3)
    Call<Integer> RQGetUserID(@Header(HeaderToken) String Token);

    @GET(ControllerActionManager_2)
    Call<List<BussinessManagerMarketing>> RQGetBMM(@Header(HeaderToken) String Token);

    @GET(ControllerActionManager_4)
    Call<com.behincom.behincome.Datas.Profile.Profile> RQGetProfile(@Header(HeaderToken) String Token);

    @Headers(ContentType)
    @POST(ControllerActionManager_5)
    Call<SimpleResponse> RQEditProfile(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//Edit Profile Information, Using Profile Object  : {"GenderTypeID":1,"MaritalStatusID":1,"ProvinceID":1,"CityID":1,"NationalCode":"123123","Firstname":"name","Lastname":"lastName","BirthDate":"2018-01-01T00:00:00","Address":"Address","PhotoFilename":"https://www.hostname.domain/filePathAddress"}

    @Headers(ContentType)
    @POST(ControllerActionManager_6)
    Call<SimpleResponse> RQEditProfileAdmin(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//Edit Profile Information, Using Profile Object  : {"UserID":1,"CityID":1,"ActivityFieldID":1,"LogoFilename":"PathOfImage Like https://www.example.domain/Image.jpg","CompanyTitle":"title","ManagerName":"name","EconomicalNumber":"123123123","NationalCode":"123123123","OtherActivitieyFields":"text","ActivityExperience":"text","Phone":"12312312","Mobile":"123123","Email":"email@domain.com","Website":"www.google.com","Address":"Addresseee","Description":"Desc","Latitude":1.1,"Longitude":1.1}

    @Headers(ContentType)
    @POST(ControllerActionManager_7)
    Call<com.behincom.behincome.Datas.Profile.GetProfile> RQGetProfileByCode(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//Edit Profile Information, Using Profile Object  : {"UserID":1}

    @Headers(ContentType)
    @POST(ControllerActionManager_8)
    Call<SimpleResponse> RQAddUserForAdmin(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//Edit Profile Information, Using Profile Object  : {"UserID":1}

    @GET(ControllerActionManager_9)
    Call<List<User_Roles>> RQGetBusinessManagerRoles(@Header(HeaderToken) String Token);//Edit Profile Information, Using Profile

    @Headers(ContentType)
    @POST(ControllerActionManager_10)
    Call<SimpleResponse> RQAddUserRole(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//Edit Profile Information, Using Profile : {"UserID":1,"RoleID":1,"MarketerName":"MehdiSafavie"}
//
//    @Headers(ContentType)
//    @POST(ControllerActionManager_11)
//    Call<SimpleResponse> RQEditUserRole(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//Edit Profile Information, Using Profile : {"UserID":1,"LastRoleID":1,"RoleID":1,"MarketerName":"MehdiSafavie"}

    @Headers(ContentType)
    @POST(ControllerActionManager_12)
    Call<List<Marketers>> RQGetMarketers(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);

    @Headers(ContentType)
    @POST(ControllerActionManager_11)
    Call<SimpleResponse> RQAddMarketerUsersAccess(@Header(HeaderToken) String Token, @Body List<HashMap<String, Object>> Parameters);//Edit Profile Information, Using Profile : [{"MainUserID":1,"SubUserID":1}]

    @Headers(ContentType)
    @POST(ControllerActionManager_13)
    Call<SimpleResponse> RQAddMarketer(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//Edit Profile Information, Using Profile : [{"MainUserID":1,"SubUserID":1}]

    @Headers(ContentType)
    @POST(ControllerActionManager_14)
    Call<SimpleResponse> RQChangeMarketerCondition(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//Edit Profile Information, Using Profile : [{"MainUserID":1,"SubUserID":1}]

    @Headers(ContentType)
    @POST(ControllerActionManager_15)
    Call<SimpleResponse> RQChangeMarketerRole(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//Edit Profile Information, Using Profile : [{"MainUserID":1,"SubUserID":1}]

    //=========================================================================================================
    //BaseData :                                     - BaseData -
    //=========================================================================================================
    @Headers(ContentType)
    @POST(ControllerActionManager_BaseData_1)
    Call<BasicDatas> RQBaseData(@Body HashMap<String, Object> Parameters);//{"ReloadAll":false,"DeviceImei":"123456"} - ReloadAll : true = FristGet ( getAll Data ) , false = OnlyGetUpdating Data

    @Headers(ContentType)
    @POST(ControllerActionManager_BaseData_2)
    Call<SimpleResponse> RQSubmitBaseData(@Body HashMap<String, Object> Parameters);//{"DeviceImei":"123456"} - Call This Method for Sugmit you Get All BaseData And Updated in SQLite

    @GET(ControllerActionManager_BaseData_3)
    Call<String> RQGetServerDateTime();//Get Server Date Time : 2018-01-01T12:12:12 +4:30 ( +4:30 is between your time to GEM Time )

    @GET(ControllerActionManager_BaseData_4)
    Call<List<Basic_Provinces>> RQGetProvinces();//Get Provinces

    @GET(ControllerActionManager_BaseData_5)
    Call<List<Basic_Cities>> RQGetCities();//Get Cities

    //=========================================================================================================
    //Marketing :                                     - Marketing -
    //=========================================================================================================
    //GetAll
    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_1)
    Call<MarketingDatas> RQGetMarketingAllData(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);

    //Adding
    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_2)
    Call<SimpleResponse> RQAddMarketingTag(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//{"TagIdList":[13,14,15]} - Add Your Integer Array into MarketingTaks Table

    //Adding
    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_23)
    Call<SimpleResponse> RQRemoveMarketingTag(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//{"TagIdList":[13,14,15]} - Add Your Integer Array into MarketingTaks Table

    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_3)
    Call<SimpleResponse> RQAddMarketingActivityFields(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//{"ActivityFieldIdList":[9,11,12]} - Add Your Integer Array into MarketingActivityFields Table

    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_20)
    Call<SimpleResponse> RQRemoveMarketingActivityFields(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//{"ActivityFieldIdList":[9,11,12]} - Add Your Integer Array into MarketingActivityFields Table

    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_4)
    Call<SimpleResponse> RQAddMarketingCities(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//{"CityIdList":[1,2,3,4]} - Add Your Integer Array into MarketingCities Table

    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_22)
    Call<SimpleResponse> RQRemoveMarketingCities(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//{"CityIdList":[1,2,3,4]} - Add Your Integer Array into MarketingCities Table

    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_5)
    Call<SimpleResponse> RQAddMarketingActivityResults(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//{"ActResultID":1,"Point":10} - Add Your Integer Array into MarketingActResults Table

    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_55)
    Call<SimpleResponse> RQRemoveMarketingActivityResults(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//{"ActResultID":1,"Point":10} - Add Your Integer Array into MarketingActResults Table

    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_56)
    Call<SimpleResponse> RQAddPointMarketingActivityResults(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//{"ActResultID":1,"Point":10} - Add Your Integer Array into MarketingActResults Table

    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_8)
    Call<SimpleResponse> RQAddMarketingProducts(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//{"CommissionTypeID":1,"MarketingProductTitle":"title","MarketingProductDescription":"description"} - Add Your Object into MarketingProduct Table

    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_9)
    Call<SimpleResponse> RQAddMarketingProductCommission(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//{"MarketingProductID":1,"CommissionPriceFrom":1.1,"CommissionPriceTo":1.1,"CommissionPercent":1} - Add Object into MarketingProductCommission Table

    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_10)
    Call<SimpleResponse> RQAddMarketingCommissionPeriod(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//{"MarketingCommissionPeriodTitle":"title","MarketingCommissionPeriodDescription":"description,"MarketingCommissionPeriodDateFrom":"dateTime","MarketingCommissionPeriodDateTo":"dateTime"} - Add Object into MarketingCommissionPeriod Table

    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_11)
    Call<SimpleResponse> RQAddMarketingVisitTour(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//{"VisitTourTitle":"title","VisitTourDescription":"description,"DateFrom":"dateTime","DateTo":"dateTime"} - Add Object into MarketingVisitTour Table

    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_15)
    Call<SimpleResponse> RQAddMarketingProperties(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//{"PropertyIdList":[1, 2, 3]} - Add Integer Array into MarketingProperties Table

    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_21)
    Call<SimpleResponse> RQRemoveMarketingProperties(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//{"PropertyIdList":[1, 2, 3]} - Add Integer Array into MarketingProperties Table

    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_16)
    Call<SimpleResponse> RQDeleteVisitTour(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//{"PropertyIdList":[1, 2, 3]} - Add Integer Array into MarketingProperties Table

    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_17)
    Call<SimpleResponse> RQDeletedVisitTour(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//{"PropertyIdList":[1, 2, 3]} - Add Integer Array into MarketingProperties Table

    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_18)
    Call<SimpleResponse> RQDeleteCommissionPriod(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//{"PropertyIdList":[1, 2, 3]} - Add Integer Array into MarketingProperties Table

    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_19)
    Call<SimpleResponse> RQDeletedCommissionPriod(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//{"PropertyIdList":[1, 2, 3]} - Add Integer Array into MarketingProperties Table
    //Editing
    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_6)
    Call<SimpleResponse> RQEditMarketingActivityResults(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//{"MarketingActResultID":3,"ActResultID":1,"Point":13} - Edit Your Object into MarketingActResults Table

    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_7)
    Call<SimpleResponse> RQEditMarketingProducts(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//{"MarketingProductID":3,"CommissionTypeID":1,"MarketingProductTitle":"title","MarketingProductDescription":"description"} - Edit Your Object into MarketingProduct Table

    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_12)
    Call<SimpleResponse> RQEditMarketingVisitTour(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//{"VisitTourID":1,"VisitTourTitle":"title","VisitTourDescription":"description,"DateFrom":"dateTime","DateTo":"dateTime"} - Add Object into MarketingVisitTour Table

    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_13)
    Call<SimpleResponse> RQEditMarketingCommissionPeriod(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//{"MarketingCommissionPeriodID":1,"MarketingCommissionPeriodTitle":"title","MarketingCommissionPeriodDescription":"description,"MarketingCommissionPeriodDateFrom":"dateTime","MarketingCommissionPeriodDateTo":"dateTime"} - Add Object into MarketingVisitTour Table

    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_14)
    Call<SimpleResponse> RQEditMarketingProductCommission(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//{"ProductCommissionID":1,"MarketingProductID":1,"CommissionPriceFrom":1.1,"CommissionPriceTo":1.1,"CommissionPercent":1} - Add Object into MarketingVisitTour Table



    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_24)
    Call<SimpleResponse> RQInsertBasicActivityFields(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//{"ProductCommissionID":1,"MarketingProductID":1,"CommissionPriceFrom":1.1,"CommissionPriceTo":1.1,"CommissionPercent":1} - Add Object into MarketingVisitTour Table

    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_25)
    Call<SimpleResponse> RQUpdateBasicActivityFields(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//{"ProductCommissionID":1,"MarketingProductID":1,"CommissionPriceFrom":1.1,"CommissionPriceTo":1.1,"CommissionPercent":1} - Add Object into MarketingVisitTour Table

    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_26)
    Call<SimpleResponse> RQDeleteBasicActivityFields(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//{"ProductCommissionID":1,"MarketingProductID":1,"CommissionPriceFrom":1.1,"CommissionPriceTo":1.1,"CommissionPercent":1} - Add Object into MarketingVisitTour Table

    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_27)
    Call<SimpleResponse> RQInsertBasicTags(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//{"ProductCommissionID":1,"MarketingProductID":1,"CommissionPriceFrom":1.1,"CommissionPriceTo":1.1,"CommissionPercent":1} - Add Object into MarketingVisitTour Table

    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_28)
    Call<SimpleResponse> RQUpdateBasicTags(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//{"ProductCommissionID":1,"MarketingProductID":1,"CommissionPriceFrom":1.1,"CommissionPriceTo":1.1,"CommissionPercent":1} - Add Object into MarketingVisitTour Table

    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_29)
    Call<SimpleResponse> RQDeleteBasicTags(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//{"ProductCommissionID":1,"MarketingProductID":1,"CommissionPriceFrom":1.1,"CommissionPriceTo":1.1,"CommissionPercent":1} - Add Object into MarketingVisitTour Table

    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_30)
    Call<SimpleResponse> RQInsertBasicProperties(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//{"ProductCommissionID":1,"MarketingProductID":1,"CommissionPriceFrom":1.1,"CommissionPriceTo":1.1,"CommissionPercent":1} - Add Object into MarketingVisitTour Table

    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_31)
    Call<SimpleResponse> RQUpdateBasicProperties(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//{"ProductCommissionID":1,"MarketingProductID":1,"CommissionPriceFrom":1.1,"CommissionPriceTo":1.1,"CommissionPercent":1} - Add Object into MarketingVisitTour Table

    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_32)
    Call<SimpleResponse> RQDeleteBasicProperties(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//{"ProductCommissionID":1,"MarketingProductID":1,"CommissionPriceFrom":1.1,"CommissionPriceTo":1.1,"CommissionPercent":1} - Add Object into MarketingVisitTour Table

    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_33)
    Call<SimpleResponse> RQInsertBasicPropertieGroups(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//{"ProductCommissionID":1,"MarketingProductID":1,"CommissionPriceFrom":1.1,"CommissionPriceTo":1.1,"CommissionPercent":1} - Add Object into MarketingVisitTour Table

    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_34)
    Call<SimpleResponse> RQUpdateBasicPropertieGroups(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//{"ProductCommissionID":1,"MarketingProductID":1,"CommissionPriceFrom":1.1,"CommissionPriceTo":1.1,"CommissionPercent":1} - Add Object into MarketingVisitTour Table

    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_35)
    Call<SimpleResponse> RQDeleteBasicPropertieGroups(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//{"ProductCommissionID":1,"MarketingProductID":1,"CommissionPriceFrom":1.1,"CommissionPriceTo":1.1,"CommissionPercent":1} - Add Object into MarketingVisitTour Table

    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_36)
    Call<SimpleResponse> RQInsertBasicTagGroups(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//{"ProductCommissionID":1,"MarketingProductID":1,"CommissionPriceFrom":1.1,"CommissionPriceTo":1.1,"CommissionPercent":1} - Add Object into MarketingVisitTour Table

    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_37)
    Call<SimpleResponse> RQUpdateBasicTagGroups(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//{"ProductCommissionID":1,"MarketingProductID":1,"CommissionPriceFrom":1.1,"CommissionPriceTo":1.1,"CommissionPercent":1} - Add Object into MarketingVisitTour Table

    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_38)
    Call<SimpleResponse> RQDeleteBasicTagGroups(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//{"ProductCommissionID":1,"MarketingProductID":1,"CommissionPriceFrom":1.1,"CommissionPriceTo":1.1,"CommissionPercent":1} - Add Object into MarketingVisitTour Table

    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_39)
    Call<SimpleResponse> RQInsertBasicActivityFieldGroups(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//{"ProductCommissionID":1,"MarketingProductID":1,"CommissionPriceFrom":1.1,"CommissionPriceTo":1.1,"CommissionPercent":1} - Add Object into MarketingVisitTour Table

    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_40)
    Call<SimpleResponse> RQUpdateBasicActivityFieldGroups(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//{"ProductCommissionID":1,"MarketingProductID":1,"CommissionPriceFrom":1.1,"CommissionPriceTo":1.1,"CommissionPercent":1} - Add Object into MarketingVisitTour Table

    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_41)
    Call<SimpleResponse> RQDeleteBasicActivityFieldGroups(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//{"ProductCommissionID":1,"MarketingProductID":1,"CommissionPriceFrom":1.1,"CommissionPriceTo":1.1,"CommissionPercent":1} - Add Object into MarketingVisitTour Table

    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_42)
    Call<SimpleResponse> RQInsertBasicCustomerStates(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//{"ProductCommissionID":1,"MarketingProductID":1,"CommissionPriceFrom":1.1,"CommissionPriceTo":1.1,"CommissionPercent":1} - Add Object into MarketingVisitTour Table

    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_43)
    Call<SimpleResponse> RQUpdateBasicCustomerStates(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//{"ProductCommissionID":1,"MarketingProductID":1,"CommissionPriceFrom":1.1,"CommissionPriceTo":1.1,"CommissionPercent":1} - Add Object into MarketingVisitTour Table

    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_54)
    Call<SimpleResponse> RQChangePositionBasicCustomerStates(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//{"ProductCommissionID":1,"MarketingProductID":1,"CommissionPriceFrom":1.1,"CommissionPriceTo":1.1,"CommissionPercent":1} - Add Object into MarketingVisitTour Table

    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_44)
    Call<SimpleResponse> RQDeleteBasicCustomerStates(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//{"ProductCommissionID":1,"MarketingProductID":1,"CommissionPriceFrom":1.1,"CommissionPriceTo":1.1,"CommissionPercent":1} - Add Object into MarketingVisitTour Table

    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_45)
    Call<SimpleResponse> RQInsertBasicArchiveTypes(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//{"ProductCommissionID":1,"MarketingProductID":1,"CommissionPriceFrom":1.1,"CommissionPriceTo":1.1,"CommissionPercent":1} - Add Object into MarketingVisitTour Table

    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_46)
    Call<SimpleResponse> RQUpdateBasicArchiveTypes(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//{"ProductCommissionID":1,"MarketingProductID":1,"CommissionPriceFrom":1.1,"CommissionPriceTo":1.1,"CommissionPercent":1} - Add Object into MarketingVisitTour Table

    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_47)
    Call<SimpleResponse> RQDeleteBasicArchiveTypes(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//{"ProductCommissionID":1,"MarketingProductID":1,"CommissionPriceFrom":1.1,"CommissionPriceTo":1.1,"CommissionPercent":1} - Add Object into MarketingVisitTour Table

    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_48)
    Call<SimpleResponse> RQInsertBasicContactTypes(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//{"ProductCommissionID":1,"MarketingProductID":1,"CommissionPriceFrom":1.1,"CommissionPriceTo":1.1,"CommissionPercent":1} - Add Object into MarketingVisitTour Table

    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_49)
    Call<SimpleResponse> RQUpdateBasicContactTypes(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//{"ProductCommissionID":1,"MarketingProductID":1,"CommissionPriceFrom":1.1,"CommissionPriceTo":1.1,"CommissionPercent":1} - Add Object into MarketingVisitTour Table

    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_50)
    Call<SimpleResponse> RQDeleteBasicContactTypes(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//{"ProductCommissionID":1,"MarketingProductID":1,"CommissionPriceFrom":1.1,"CommissionPriceTo":1.1,"CommissionPercent":1} - Add Object into MarketingVisitTour Table

    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_51)
    Call<SimpleResponse> RQInsertBasicPersonRoles(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//{"ProductCommissionID":1,"MarketingProductID":1,"CommissionPriceFrom":1.1,"CommissionPriceTo":1.1,"CommissionPercent":1} - Add Object into MarketingVisitTour Table

    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_52)
    Call<SimpleResponse> RQUpdateBasicPersonRoles(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//{"ProductCommissionID":1,"MarketingProductID":1,"CommissionPriceFrom":1.1,"CommissionPriceTo":1.1,"CommissionPercent":1} - Add Object into MarketingVisitTour Table

    @Headers(ContentType)
    @POST(ControllerActionManager_Marketing_53)
    Call<SimpleResponse> RQDeleteBasicPersonRoles(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//{"ProductCommissionID":1,"MarketingProductID":1,"CommissionPriceFrom":1.1,"CommissionPriceTo":1.1,"CommissionPercent":1} - Add Object into MarketingVisitTour Table

    //=========================================================================================================
    //Customer :                                     - Customer -
    //=========================================================================================================
    @Headers(ContentType)
    @POST(ControllerActionManager_Customer_1)
    Call<SimpleResponse> RQAddCustomer(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//Add New Customer, Using Customer Object

    @Headers(ContentType)
    @POST(ControllerActionManager_Customer_2)
    Call<SimpleResponse> RQEditCustomer(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//Edit Customer, Using Customer Object

    @Headers(ContentType)
    @POST(ControllerActionManager_Customer_3)
    Call<SimpleResponse> RQDeleteCustomer(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//Delete Customer, Using CustomerID : {"CustomerID":2}

    @Headers(ContentType)
    @POST(ControllerActionManager_Customer_4)
    Call<SimpleResponse> RQChangeCustomerState(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//Change Customer State Using : {"CustomerID":8,"CustomerStateID":2}

    @Headers(ContentType)
    @POST(ControllerActionManager_Customer_5)
    Call<SimpleResponse> RQChangeCustomerStatues(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//Change Customer Status Using : {"CustomerID":8,"CustomerStatuesID":2}

    @Headers(ContentType)
    @POST(ControllerActionManager_Customer_9)
    Call<SimpleResponse> RQAddCustomerToArchive(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//Change Customer Status Using : {"CustomerID":8,"CustomerStateID":2}

    @Headers(ContentType)
    @POST(ControllerActionManager_Customer_11)
    Call<SimpleResponse> RQAddCustomersToArchive(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//Change Customer Status Using : {"CustomerID":8,"CustomerStateID":2}

    @Headers(ContentType)
    @POST(ControllerActionManager_Customer_12)
    Call<SimpleResponse> RQAddCustomerToExitArchive(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//Change Customer Status Using : {"CustomerID":8,"CustomerStateID":2}

    @Headers(ContentType)
    @POST(ControllerActionManager_Customer_13)
    Call<SimpleResponse> RQAddCustomersToExitArchive(@Header(HeaderToken) String Token, @Body List<HashMap<String, Object>> Parameters);//Change Customer Status Using : {"CustomerID":8,"CustomerStateID":2}

    @GET(ControllerActionManager_Customer_6)
    Call<List<Customers>> RQGetCustomerAllData(@Header(HeaderToken) String Token);//Get All Customer ( Assigned To You ) Without Need To Any Parameter ( Token Have Your UserID )

    @Headers(ContentType)
    @POST(ControllerActionManager_Customer_6)
    Call<List<MyCustomers>> RQGetCustomerAllData(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//Get All Customer ( Assigned To You ) Without Need To Any Parameter ( Token Have Your UserID )

    @GET(ControllerActionManager_Customer_10)
    Call<List<Customers>> RQGetCustomerAllDataArchived(@Header(HeaderToken) String Token);//Get All Customer ( Assigned To You ) Without Need To Any Parameter ( Token Have Your UserID )

    @Headers(ContentType)
    @POST(ControllerActionManager_Customer_7)
    Call<Customers> RQGetCustomer(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//Get Customer Using CustomerID ( Reterned Only One CustomerObject ) : {"CustomerID":3}

    @Headers(ContentType)
    @POST(ControllerActionManager_Customer_8)
    Call<Customers> RQGetCustomerInCirclePoint(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//Get All Customer Between Two LatLng Point ( But Work Only With One LatLng, This Point Always Mus be You CameraCenterPoint in GoogleMap ) : {"Latutide":1.0,"Longitude":1.0}

    @Headers(ContentType)
    @POST(ControllerActionManager_Customer_14)
    Call<SimpleResponse> RQAssignCustomersToUsers(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//Get All Customer Between Two LatLng Point ( But Work Only With One LatLng, This Point Always Mus be You CameraCenterPoint in GoogleMap ) : {"CustomerIDs":[1,2,3,4],"MarketerIDs":[1,2,3,4,5]}

    @Multipart
    @POST(ControllerActionManager_Customer_15)
    Call<SimpleResponse> RQAddCustomerPic(@Header(HeaderToken) String Token, @Part MultipartBody.Part body);
    @Multipart
    @POST(ControllerActionManager_Customer_15)
    Call<SimpleResponse> RQAddCustomerPic(@Header(HeaderToken) String Token, @Part MultipartBody.Part[] body);

    @Multipart
    @POST(ControllerActionManager_Customer_16)
    Call<List<Invoice>> RQGetCustomerInvoice(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);

    //=========================================================================================================
    //Activities :                                     - Activities -
    //=========================================================================================================
    @GET(ControllerActionManager_Activities_1)
    Call<List<com.behincom.behincome.Datas.Activityes.Activities>> RQGetActivitiesAllData(@Header(HeaderToken) String Token);//Get All Activities ( Assigned To You ) Without Need To Any Parameter ( Token Have Your UserID )

    @GET(ControllerActionManager_Activities_2)
    Call<List<com.behincom.behincome.Datas.Activityes.Activities>> RQGetTasksAllData(@Header(HeaderToken) String Token);//Get All Tasks ( Assigned To You ) Without Need To Any Parameter ( Token Have Your UserID )

    @GET(ControllerActionManager_Activities_3)
    Call<List<com.behincom.behincome.Datas.Activityes.Activities>> RQGetTasksToDay(@Header(HeaderToken) String Token);//Get All Tasks ( For ToDay Assigned To You ) Without Need To Any Parameter ( Token Have Your UserID )

    @Headers(ContentType)
    @POST(ControllerActionManager_Activities_4)
    Call<List<com.behincom.behincome.Datas.Activityes.Activities>> RQGetTasksByDate(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//Get All Tasks ( ByDate Assigned To You ) Need to Parameter : {"DateTime":"2018-01-01T22:22:22"}

    @Headers(ContentType)
    @POST(ControllerActionManager_Activities_5)
    Call<com.behincom.behincome.Datas.Activityes.Activities> RQGetActivityByID(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//Get Activity ( ByID Assigned To You ) Need to Parameter : {"ActID":1}

    @Headers(ContentType)
    @POST(ControllerActionManager_Activities_6)
    Call<com.behincom.behincome.Datas.Activityes.Activities> RQGetTaskByID(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//Get All Tasks ( ByID Assigned To You ) Need to Parameter : {"TaskID":1}

    @Headers(ContentType)
    @POST(ControllerActionManager_Activities_7)
    Call<List<com.behincom.behincome.Datas.Activityes.Activities>> RQGetActivitiesByCustomerID(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//Get All Activities ( ByCustomerID Assigned To You ) Need to Parameter : {"CustomerID":1}

    @Headers(ContentType)
    @POST(ControllerActionManager_Activities_8)
    Call<List<com.behincom.behincome.Datas.Activityes.Activities>> RQGetTasksByCustomerID(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//Get All Tasks ( ByCustomerID Assigned To You ) Need to Parameter : {"CustomerID":1}

    @Headers(ContentType)
    @POST(ControllerActionManager_Activities_9)
    Call<List<Invoice>> RQGetInvoiceByCustomerID(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//Get All Tasks ( ByCustomerID Assigned To You ) Need to Parameter : {"CustomerID":1}

    @Headers(ContentType)
    @POST(ControllerActionManager_Activities_10)
    Call<SimpleResponse> RQAddActivityEnter(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//Add Activity For Enter Details, Need to Parameters : {"ParentID":1,"ActivityType":1,"ActivityAddedByUserID":1,"ActivityOwnerUserID":1,"CustomerID":1,"Title":"title","ActivityFieldID":1,"ActivityDescription":"description","EnterDate":"2018-01-01T11:11:11","EnterLatutide":1.1,"EnterLongitude":1.1}

    @Headers(ContentType)
    @POST(ControllerActionManager_Activities_17)
    Call<SimpleResponse> RQAddActivityEnterForTask(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//Add Activity For Enter Details, Need to Parameters : {"ActID":1,"Title":"title","ActivityFieldID":1,"ActivityDescription":"description","EnterDate":"2018-01-01T11:11:11","EnterLatutide":1.1,"EnterLongitude":1.1}

    @Headers(ContentType)
    @POST(ControllerActionManager_Activities_11)
    Call<SimpleResponse> RQAddActivityExit(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//Add Activity For Exit Details, Need to Parameters : {"ActID":1,"ActResultID":1,"ActivityDescription":"description","ExitDate":"2018-01-01T11:11:11","ExitLatutide":1.1,"ExitLongitude":1.1,"Invoices":[{"InvoiceNumber":"123asd123","InvoiceMarketingProductID":1,"InvoiceActivityID":1,"InvoicePrice":123123.11,"InvoiceDescription":"description}]}

    @Headers(ContentType)
    @POST(ControllerActionManager_Activities_12)
    Call<SimpleResponse> RQConfirmActivity(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//Add Activity For Confirm Details, Need to Parameters : {"ActID":1,"ActResultID":1,"Description":"description","Invoices":[{"InvoiceNumber":"123asd123","InvoiceMarketingProductID":1,"InvoiceActivityID":1,"InvoicePrice":123123.11,"InvoiceDescription":"description}]}

    @Headers(ContentType)
    @POST(ControllerActionManager_Activities_13)
    Call<SimpleResponse> RQSendActivity(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//Add Activity For Send Details, Need to Parameters : {"ActID":1,"ActResultID":1,"Description":"description","Invoices":[{"InvoiceNumber":"123asd123","InvoiceMarketingProductID":1,"InvoiceActivityID":1,"InvoicePrice":123123.11,"InvoiceDescription":"description}]}

    @Headers(ContentType)
    @POST(ControllerActionManager_Activities_15)
    Call<SimpleResponse> RQAddTask(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);//Add Activity For Enter Details, Need to Parameters : {"ActivityType":1,"ActivityAddedByUserID":1,"ActivityOwnerUserID":1,"CustomerID":1,"Title":"title","ActivityFieldID":1,"ActivityDescription":"description","TaskDate":"2018-01-01T11:11:11","DurationDate":"2018-01-01T11:11:11","VisitTourID":1}

    @Headers(ContentType)
    @POST(ControllerActionManager_Activities_16)
    Call<SimpleResponse> RQAddTasks(@Header(HeaderToken) String Token, @Body List<HashMap<String, Object>> Parameters);//Add Activity For Enter Details, Need to Parameters : {"ActivityType":1,"ActivityAddedByUserID":1,"ActivityOwnerUserID":1,"CustomerID":1,"Title":"title","ActivityFieldID":1,"ActivityDescription":"description","TaskDate":"2018-01-01T11:11:11","DurationDate":"2018-01-01T11:11:11","VisitTourID":1}

    @Multipart
    @POST(ControllerActionManager_Activities_14)
    Call<SimpleResponse> RQAddInvoicePic(@Header(HeaderToken) String Token, @Part MultipartBody.Part[] body);

    @Headers(ContentType)
    @POST(ControllerActionManager_Activities_18)
    Call<SimpleResponse> RQEditTask(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);

    @Headers(ContentType)
    @POST(ControllerActionManager_Activities_19)
    Call<SimpleResponse> RQDeleteTask(@Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);







    @GET("{Controller}/{Action}")
    Call<Object> GET(@Path("Controller") String Controller, @Path("Action") String Action);
    @GET("{Controller}/{Action}")
    Call<Object> GET(@Path("Controller") String Controller, @Path("Action") String Action, @Header(HeaderToken) String Token);

    @Headers(ContentType)
    @POST("{Controller}/{Action}")
    Call<Object> POST(@Path("Controller") String Controller, @Path("Action") String Action, @Header(HeaderToken) String Token);
    @Headers(ContentType)
    @POST("{Controller}/{Action}")
    Call<Object> POST(@Path("Controller") String Controller, @Path("Action") String Action, @Header(HeaderToken) String Token, @Body HashMap<String, Object> Parameters);
    @Headers(ContentType)
    @POST("{Controller}/{Action}")
    Call<Object> POST(@Path("Controller") String Controller, @Path("Action") String Action, @Body HashMap<String, Object> Parameters);
    @Headers(ContentType)
    @POST("{Controller}/{Action}")
    Call<Object> POST(@Path("Controller") String Controller, @Path("Action") String Action, @Header(HeaderToken) String Token, @Body List<HashMap<String, Object>> Parameters);
    @Headers(ContentType)
    @POST("{Controller}/{Action}")
    Call<Object> POST(@Path("Controller") String Controller, @Path("Action") String Action, @Body List<HashMap<String, Object>> Parameters);

    @Multipart
    @POST("{Controller}/{Action}")
    Call<Object> POSTFile(@Path("Controller") String Controller, @Path("Action") String Action, @Header(HeaderToken) String Token, @Part MultipartBody.Part[] body);
    @Multipart
    @POST("{Controller}/{Action}")
    Call<Object> POSTFile(@Path("Controller") String Controller, @Path("Action") String Action, @Header(HeaderToken) String Token, @Part MultipartBody.Part body);

}
