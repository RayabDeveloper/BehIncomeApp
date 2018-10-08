package com.behincom.behincome.WebRequest.Keys;

public enum RWAction {

    Null,

    //Account API
    RequestPhoneVerificationCode,
    VerifyPhoneNumber,
    register,
    login,

    //Profile API
    PostUserImage,
    GetMyBusinessManagers,
    GetMyUserID,
    GetUserProfile,
    GetUserProfileByCode,
    EditProfile,
    EditProfileAdmin,
    AddUserToBussinessManager,
    GetBusinessManagerRoles,
    AddUserRole,
    EditUserRole,
    GetBusinessManagerMarketers,
    AddMarketer,
    ChangeMarketerCondition,
    ChangeMarketerRole,
    AddMarketerUsersAccess,

    //BaseData API
    GetAllBaseData,
    GetAllBaseData_Ack,
    GetServerDateTime,
    GetProvinces,
    GetCities,

    //Marketing API
    AddTags,
    GetAll,
    AddActivityFields,
    AddCities,
    AddActivityResult,
    AddProduct,
    AddProductCommission,
    AddCommissionPeriod,
    AddVisitTour,
    AddProperties,
    DeleteVisitTour,
    DeletedVisitTour,
    DeleteCommissionPeriod,
    DeletedCommissionPeriod,
    EditActivityResult,
    EditProduct,
    EditVisitTour,
    EditCommissionPeriod,
    EditProductCommission,

    //Customer API
    AddCustomer,
    EditCustomer,
    DeleteCustomer,
    ChangeCustomerState,
    ChangeCustomerStatues,
    ChangeCustomersStatues,
    GetMyCustomer,
    GetCustomersArchived,
    GetCustomer,
    GetInCirclePointCustomer,
    AddCustomerToArchive,
    AddCustomersToArchive,
    AddCustomerToExitArchive,
    AddCustomersToExitArchive,
    AssignCustomersToMarketers,
    PostCustomerImage,
    GetCustomerFactors,

    //Activities API
    GetActivities,
    GetTasks,
    GetTasksByNowDate,
    GetTasksByDate,
    GetActivityByID,
    GetTaskByID,
    GetActivitiesByCustomerID,
    GetTasksByCustomerID,
    GetInvoiceByCustomerID,
    AddActivityEnter,
    AddActivityEnterForTask,
    AddActivityExit,
    ConfirmActivity,
    SendActivity,
    AddTask,
    AddTasks,
    PostInvoiceImage,
    EditTask,
    DeleteTask

}
