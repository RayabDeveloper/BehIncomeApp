package com.behincom.behincome.SQL;

public enum RSType {

    //SQLite Type
    ID,
    PRIMARY,
    NORMAL,
    INTEGER,//int, integer, tinyint, smallint, mediumint, bigint
    TEXT,//character(20), varchar(255), nchar(55), native character(70), nvarchar(100), text, clob
    REAL,//real, double, float
    NUMERIC,//numeric, decimal(10,5), boolean, date, datetime
    DatabaseName,
    DatabaseVersion,
    Tables,
    CSharpDateTime,

}
