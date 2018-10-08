package com.behincom.behincome.WebRequest;

import java.util.List;

public class RWCast {

    @SuppressWarnings("unchecked")
    static <T extends List<?>> T cast(Object obj) {
        return (T) obj;
    }

}
