package com.behincom.behincome.SQL;

import com.behincom.behincome.Datas.Keys.Tables;

public class RSQLiteCreateTable {

    //Create Tables Here
    protected static String[] getCreatTableQuery = new String[]{
            "CREATE TABLE " + Tables.Basic_Acts +//Table :Basic_Acts
                    " (" +
                    "ActID					                    INTEGER PRIMARY KEY, " +
                    "ActGroupID					                INTEGER, " +
                    "ActTitle					                TEXT, " +
                    "ActFontIcon					            TEXT, " +
                    "ActColor					                TEXT, " +
                    "isCheck                                    INTEGER, " +
                    "ActOrder                                   INTEGER, " +
                    "Deleted					                INTEGER " +
                    ");",
            "CREATE TABLE " + Tables.Basic_ActivityFieldGroups +//Table :Basic_ActivityFieldGroups
                    " (" +
                    "ActivityFieldGroupID					    INTEGER PRIMARY KEY, " +
                    "ActivityFieldGroupOrder					INTEGER, " +
                    "ActivityFieldGroupFontIcon					TEXT, " +
                    "ActivityFieldGroupColor					TEXT, " +
                    "ActivityFieldGroupUserId					INTEGER, " +
                    "AdjustedByAdmin					        INTEGER, " +
                    "ActivityFieldGroupTitle					TEXT " +
                    ");",
            "CREATE TABLE " + Tables.Basic_ActivityFields +//Table :Basic_ActivityFields
                    " (" +
                    "ActivityFieldID					        INTEGER PRIMARY KEY, " +
                    "ActivityFieldGroupID					    INTEGER, " +
                    "ActivityFieldTitle					        TEXT, " +
                    "isCheck                                    INTEGER, " +
                    "ActivityFieldOrder                         INTEGER, " +
                    "ActivityFieldFontIcon                      TEXT, " +
                    "Deleted					                INTEGER " +
                    ");",
            "CREATE TABLE " + Tables.mBasic_ActivityFields +//Table :Basic_ActivityFields
                    " (" +
                    "ActivityFieldID					        INTEGER PRIMARY KEY, " +
                    "ActivityFieldGroupID					    INTEGER, " +
                    "ActivityFieldTitle					        TEXT, " +
                    "isCheck                                    INTEGER, " +
                    "ActivityFieldOrder                         INTEGER, " +
                    "Deleted					                INTEGER " +
                    ");",
            "CREATE TABLE " + Tables.Basic_ActResults +//Table :Basic_ActResults
                    " (" +
                    "ActResultID					            INTEGER PRIMARY KEY, " +
                    "ActID					                    INTEGER, " +
                    "ActResultTitle					            TEXT, " +
                    "ActResultFontIcon					        TEXT, " +
                    "ActResultColor					            TEXT, " +
                    "isCheck                                    INTEGER, " +
                    "ActResultOrder                             INTEGER, " +
                    "Deleted					                INTEGER " +
                    ");",
            "CREATE TABLE " + Tables.Basic_ActivityStates +//Table :Basic_ActivityStates
                    " (" +
                    "ActivityStateID					        INTEGER PRIMARY KEY, " +
                    "isCheck                                    INTEGER, " +
                    "ActivityStateOrder                         INTEGER, " +
                    "ActivityStateTitle					        TEXT " +
                    ");",
            "CREATE TABLE " + Tables.Basic_ActGroups +//Table :Basic_ActGroups
                    " (" +
                    "ActGroupID					                INTEGER PRIMARY KEY, " +
                    "isCheck                                    INTEGER, " +
                    "ActGroupOrder                              INTEGER, " +
                    "ActGroupTitle					            TEXT, " +
                    "ActGroupFontIcon					        TEXT, " +
                    "ActGroupColor					            TEXT " +
                    ");",
            "CREATE TABLE " + Tables.Basic_ArchiveTypes +//Table :Basic_ArchiveTypes
                    " (" +
                    "ArchiveTypeID					            INTEGER PRIMARY KEY, " +
                    "isCheck                                    INTEGER, " +
                    "ArchiveTypeOrder                           INTEGER, " +
                    "ArchiveTypeUserId                          INTEGER, " +
                    "AdjustedByAdmin                            INTEGER, " +
                    "ArchiveTypeTitle					        TEXT, " +
                    "ArchiveTypeFontIcon					    TEXT, " +
                    "ArchiveTypeColor					        TEXT " +
                    ");",
            "CREATE TABLE " + Tables.Basic_BusinessManagerMarketerStates +//Table :Basic_BusinessManagerMarketerStates
                    " (" +
                    "BusinessManagerMarketerStateID				INTEGER PRIMARY KEY, " +
                    "isCheck                                    INTEGER, " +
                    "BusinessManagerMarketerStateOrder          INTEGER, " +
                    "BusinessManagerMarketerStateTitle			TEXT, " +
                    "BusinessManagerMarketerStateFontIcon		TEXT, " +
                    "BusinessManagerMarketerStateColor			TEXT " +
                    ");",
            "CREATE TABLE " + Tables.Basic_Cities +//Table :Basic_Cities
                    " (" +
                    "CityID					                    INTEGER PRIMARY KEY, " +
                    "ProvinceID					                INTEGER, " +
                    "CityTitle					                TEXT, " +
                    "isCheck                                    INTEGER, " +
                    "CityOrder                                  INTEGER, " +
                    "Deleted					                INTEGER " +
                    ");",
            "CREATE TABLE " + Tables.Basic_CommissionTypes +//Table :Basic_CommissionTypes
                    " (" +
                    "CommissionTypeID					        INTEGER PRIMARY KEY, " +
                    "isCheck                                    INTEGER, " +
                    "CommissionTypeOrder                        INTEGER, " +
                    "CommissionTypeTitle					    TEXT, " +
                    "CommissionTypeFontIcon					    TEXT, " +
                    "CommissionTypeColor					    TEXT " +
                    ");",
            "CREATE TABLE " + Tables.Basic_ContactTypes +//Table :Basic_ContactTypes
                    " (" +
                    "ContactTypeID					            INTEGER PRIMARY KEY, " +
                    "isCheck                                    INTEGER, " +
                    "ContactTypeOrder                           INTEGER, " +
                    "AndroidKeyboardTypeID                      INTEGER, " +
                    "ContactTypeUserId                          INTEGER, " +
                    "AdjustedByAdmin                            INTEGER, " +
                    "ContactTypeTitle					        TEXT, " +
                    "ContactTypeFontIcon					    TEXT, " +
                    "ContactTypeColor					        TEXT " +
                    ");",
            "CREATE TABLE " + Tables.Basic_CustomerStates +//Table :Basic_CustomerStates
                    " (" +
                    "CustomerStateID					        INTEGER PRIMARY KEY, " +
                    "isCheck                                    INTEGER, " +
                    "CustomerStateOrder                         INTEGER, " +
                    "CustomerStateUserId                        INTEGER, " +
                    "CustomerStateColor					        TEXT, " +
                    "CustomerStateFontIcon					    TEXT, " +
                    "CustomerStateAdjustedByAdmin				INTEGER, " +
                    "CustomerStateTitle					        TEXT " +
                    ");",
            "CREATE TABLE " + Tables.Basic_CustomerStatus +//Table :Basic_CustomerStatus
                    " (" +
                    "CustomerStatusID					        INTEGER PRIMARY KEY, " +
                    "CustomerStatusTitle                        TEXT, " +
                    "CustomerStatusFontIcon                     TEXT, " +
                    "CustomerStatusColor                        TEXT, " +
                    "Deleted                                    INTEGER, " +
                    "CustomerStatusOrder                        INTEGER, " +
                    "isCheck                                    INTEGER " +
                    ");",
            "CREATE TABLE " + Tables.Basic_GenderTypes +//Table :Basic_GenderTypes
                    " (" +
                    "GenderTypeID					            INTEGER PRIMARY KEY, " +
                    "GenderTypeTitle					        TEXT, " +
                    "GenderTypeFontIcon					        TEXT, " +
                    "GenderTypeColor					        TEXT, " +
                    "isCheck                                    INTEGER, " +
                    "Deleted					                INTEGER " +
                    ");",
            "CREATE TABLE " + Tables.Basic_MaritalStatus +//Table :Basic_MaritalStatus
                    " (" +
                    "MaritalStatusID					        INTEGER PRIMARY KEY, " +
                    "MaritalStatusTitle					        TEXT, " +
                    "MaritalStatusFontIcon					    TEXT, " +
                    "MaritalStatusColor					        TEXT, " +
                    "isCheck                                    INTEGER, " +
                    "Deleted					                INTEGER " +
                    ");",
            "CREATE TABLE " + Tables.Basic_MilitaryStatus +//Table :Basic_MilitaryStatus
                    " (" +
                    "MilitaryStatusID					        INTEGER PRIMARY KEY, " +
                    "MilitaryStatusTitle					    TEXT, " +
                    "isCheck                                    INTEGER, " +
                    "Deleted					                INTEGER " +
                    ");",
            "CREATE TABLE " + Tables.Basic_NamePrefixes +//Table :Basic_NamePrefixes
                    " (" +
                    "NamePrefixID					            INTEGER PRIMARY KEY, " +
                    "NamePrefixTitle					        TEXT, " +
                    "NamePrefixFontIcon					        TEXT, " +
                    "NamePrefixColor					        TEXT, " +
                    "isCheck                                    INTEGER, " +
                    "NamePrefixOrder                            INTEGER, " +
                    "Deleted					                INTEGER " +
                    ");",
            "CREATE TABLE " + Tables.Basic_PersonRoles +//Table :Basic_PersonRoles
                    " (" +
                    "PersonRoleID					            INTEGER PRIMARY KEY, " +
                    "PersonRoleTitle					        TEXT, " +
                    "PersonRoleFontIcon					        TEXT, " +
                    "PersonRoleColor					        TEXT, " +
                    "PersonRoleUserId                           INTEGER, " +
                    "isCheck                                    INTEGER, " +
                    "PersonRoleOrder                            INTEGER, " +
                    "PersonRoleAdjustedByAdmin                  INTEGER, " +
                    "Deleted					                INTEGER " +
                    ");",
            "CREATE TABLE " + Tables.Basic_PersonTypes +//Table :Basic_PersonTypes
                    " (" +
                    "PersonTypeID					            INTEGER PRIMARY KEY, " +
                    "PersonTitleTitle					        TEXT, " +
                    "PersonTypeFontIcon					        TEXT, " +
                    "PersonTypeColor					        TEXT, " +
                    "isCheck                                    INTEGER, " +
                    "PersonRoleOrder                            INTEGER, " +
                    "Deleted					                INTEGER " +
                    ");",
            "CREATE TABLE " + Tables.Basic_Properties +//Table :Basic_Properties
                    " (" +
                    "PropertyID					                INTEGER PRIMARY KEY, " +
                    "PropertyGroupID					        INTEGER, " +
                    "PropertyTitle					            TEXT, " +
                    "PropertyDescription					    TEXT, " +
                    "PersonTypeFontIcon					        TEXT, " +
                    "PersonTypeColor					        TEXT, " +
                    "PropertyTypeKeyBoardId                     INTEGER, " +
                    "isCheck                                    INTEGER, " +
                    "PropertyOrder                              INTEGER, " +
                    "Deleted					                INTEGER " +
                    ");",
            "CREATE TABLE " + Tables.mBasic_Properties +//Table :Basic_Properties
                    " (" +
                    "PropertyID					                INTEGER PRIMARY KEY, " +
                    "PropertyGroupID					        INTEGER, " +
                    "PropertyTitle					            TEXT, " +
                    "PropertyDescription					    TEXT, " +
                    "isCheck                                    INTEGER, " +
                    "PropertyOrder                              INTEGER, " +
                    "Deleted					                INTEGER " +
                    ");",
            "CREATE TABLE " + Tables.Basic_PropertyGroups +//Table :Basic_PropertyGroups
                    " (" +
                    "PropertyGroupID					        INTEGER PRIMARY KEY, " +
                    "PropertyGroupTitle					        TEXT, " +
                    "PropertyGroupFontIcon					    TEXT, " +
                    "PropertyGroupColor					        TEXT, " +
                    "PropertyGroupUserId                        INTEGER, " +
                    "isCheck                                    INTEGER, " +
                    "PropertyGroupOrder                         INTEGER, " +
                    "PropertyGroupAdjustedByAdmin               INTEGER, " +
                    "Deleted					                INTEGER " +
                    ");",
            "CREATE TABLE " + Tables.Basic_Provinces +//Table :Basic_Provinces
                    " (" +
                    "ProvinceID					                INTEGER PRIMARY KEY, " +
                    "ProvinceTitle					            TEXT, " +
                    "isCheck                                    INTEGER, " +
                    "ProvinceOrder                              INTEGER, " +
                    "Deleted					                INTEGER " +
                    ");",
            "CREATE TABLE " + Tables.Basic_TagGroups +//Table :Basic_TagGroups
                    " (" +
                    "TagGroupID					                INTEGER PRIMARY KEY, " +
                    "TagGroupTitle					            TEXT, " +
                    "TagGroupFontIcon					        TEXT, " +
                    "TagGroupColor					            TEXT, " +
                    "TagGroupUserId                             INTEGER, " +
                    "isCheck                                    INTEGER, " +
                    "TagGroupOrder                              INTEGER, " +
                    "TagGroupAdjustedByAdmin                    INTEGER, " +
                    "TagGroupTypeId                             INTEGER, " +
                    "Deleted					                INTEGER " +
                    ");",
            "CREATE TABLE " + Tables.Basic_Tags +//Table :Basic_Tags
                    " (" +
                    "TagID					                    INTEGER PRIMARY KEY, " +
                    "TagGroupID					                INTEGER, " +
                    "TagTitle					                TEXT, " +
                    "isCheck                                    INTEGER, " +
                    "TagOrder                                   INTEGER, " +
                    "Deleted					                INTEGER " +
                    ");",
            "CREATE TABLE " + Tables.mBasic_Tags +//Table :Basic_Tags
                    " (" +
                    "TagID					                    INTEGER PRIMARY KEY, " +
                    "TagGroupID					                INTEGER, " +
                    "TagTitle					                TEXT, " +
                    "isCheck                                    INTEGER, " +
                    "TagOrder                                   INTEGER, " +
                    "Deleted					                INTEGER " +
                    ");",
            "CREATE TABLE " + Tables.Basic_AndroidKeyboardTypes +//Table :Basic_AndroidKeyboardTypes
                    " (" +
                    "AndroidKeyboardTypeID					    INTEGER PRIMARY KEY, " +
                    "AndroidKeyboardTypeTitle					TEXT, " +
                    "Deleted					                INTEGER " +
                    ");",
            "CREATE TABLE " + Tables.MarketingActivityFields +//Table :MarketingActivityFields
                    " (" +
                    "MarketingActivityFieldID				    INTEGER PRIMARY KEY, " +
                    "UserID					                    INTEGER, " +
                    "ActivityFieldID					        INTEGER " +
                    ");",
            "CREATE TABLE " + Tables.MarketingActResults +//Table :MarketingActResults
                    " (" +
                    "MarketingActResultID				        INTEGER PRIMARY KEY, " +
                    "UserID					                    INTEGER, " +
                    "ActResultID					            INTEGER, " +
                    "Point					                    INTEGER " +
                    ");",
            "CREATE TABLE " + Tables.MarketingCities +//Table :MarketingCities
                    " (" +
                    "MarketingCityID					        INTEGER PRIMARY KEY, " +
                    "UserID					                    INTEGER, " +
                    "CityID					                    INTEGER " +
                    ");",
            "CREATE TABLE " + Tables.MarketingCommissionPeriods +//Table :MarketingCommissionPeriods
                    " (" +
                    "MarketingCommissionPeriodID		        INTEGER PRIMARY KEY, " +
                    "UserID					                    INTEGER, " +
                    "MarketingCommissionPeriodTitle			    TEXT, " +
                    "MarketingCommissionPeriodDateFrom			TEXT, " +
                    "MarketingCommissionPeriodDateTo			TEXT, " +
                    "MarketingCommissionPeriodDescription	    TEXT " +
                    ");",
            "CREATE TABLE " + Tables.MarketingProductCommissions +//Table :MarketingProductCommissions
                    " (" +
                    "ProductCommissionID					    INTEGER PRIMARY KEY, " +
                    "MarketingProductID					        INTEGER, " +
                    "CommissionPriceFrom					    INTEGER, " +
                    "CommissionPriceTo					        INTEGER, " +
                    "CommissionPercent					        INTEGER " +
                    ");",
            "CREATE TABLE " + Tables.MarketingProducts +//Table :MarketingProducts
                    " (" +
                    "MarketingProductID					        INTEGER PRIMARY KEY, " +
                    "UserID					                    INTEGER, " +
                    "CommissionTypeID					        INTEGER, " +
                    "MarketingProductTitle					    TEXT, " +
                    "MarketingProductDescription			    TEXT " +
                    ");",
            "CREATE TABLE " + Tables.MarketingProperties +//Table :MarketingProperties
                    " (" +
                    "MarkettingPropertyID					    INTEGER PRIMARY KEY, " +
                    "UserID					                    INTEGER, " +
                    "PropertyID					                INTEGER " +
                    ");",
            "CREATE TABLE " + Tables.MarketingTags +//Table :MarketingTags
                    " (" +
                    "MarketingTagID					            INTEGER PRIMARY KEY, " +
                    "UserID					                    INTEGER, " +
                    "TagID					                    INTEGER " +
                    ");",
            "CREATE TABLE " + Tables.MarketingVisitTours +//Table :MarketingVisitTours
                    " (" +
                    "VisitTourID					            INTEGER PRIMARY KEY, " +
                    "UserID					                    INTEGER, " +
                    "VisitTourTitle					            TEXT, " +
                    "VisitTourDescription					    TEXT, " +
                    "DateFrom					                TEXT, " +
                    "DateTo					                    TEXT, " +
                    "Deleted					                INTEGER " +
                    ");",
            "CREATE TABLE " + Tables.Keyboards +//Table :Keyboards
                    " (" +
                    "KeyboardID					                INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "AndroidKeyboardTypeTitle					TEXT" +
                    ");",
            "CREATE TABLE " + Tables.MarketingSetups +//Table :MarketingSetups
                    " (" +
                    "MarketingSetupId					        INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "PointCustomerAdd					        INTEGER, " +
                    "PointInvoiseAdd					        INTEGER, " +
                    "CustmerEditTime					        TEXT, " +
                    "ActivityEditTime					        TEXT, " +
                    "Deleted					                INTEGER, " +
                    "OwnerId					                INTEGER " +
                    ");"
    };

}
