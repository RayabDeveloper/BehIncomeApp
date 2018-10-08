package com.behincom.behincome.SQL;

import java.util.List;

class sqlCast {

    @SuppressWarnings("unchecked")
    static <T extends List<?>> T cast(Object obj) {
        return (T) obj;
    }

}
