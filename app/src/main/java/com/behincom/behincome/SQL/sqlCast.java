package com.behincom.behincome.SQL;

import java.util.List;

class sqlCast {

    static <T extends List<?>> T cast(Object obj) {
        return (T) obj;
    }

}
