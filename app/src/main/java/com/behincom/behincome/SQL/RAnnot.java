package com.behincom.behincome.SQL;

import static com.behincom.behincome.SQL.RSType.PRIMARY;

public @interface RAnnot {

    RSType Key() default PRIMARY;

}
