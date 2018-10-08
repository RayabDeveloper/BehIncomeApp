package com.behincom.behincome.WebRequest.Object;

import com.behincom.behincome.WebRequest.Keys.RWAction;
import com.behincom.behincome.WebRequest.Keys.RWController;
import com.behincom.behincome.WebRequest.Keys.RWMethod;
import com.behincom.behincome.WebRequest.Keys.RWType;

public class RWObject {

    public RWMethod Method = com.behincom.behincome.WebRequest.Keys.RWMethod.Null;
    public RWController Controller = com.behincom.behincome.WebRequest.Keys.RWController.Null;
    public RWAction Action = com.behincom.behincome.WebRequest.Keys.RWAction.Null;
    public RWType Type = com.behincom.behincome.WebRequest.Keys.RWType.Null;
    public boolean isToken = false;
    public String Token = "";
    public Object Object = null;
    public Object MultiPartBody = null;

}
