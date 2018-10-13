# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-keepattributes *RAnnot*
-keep public @interface com.behincom.behincome.SQL.RAnnot
#-keep public class com.behincom.behincome.SQL.**
#-keep public class android.database.sqlite { *; }
#-keep public class android.database.sqlite.SQLiteDatabase { *; }
#-keep public class android.database.sqlite.SQLiteOpenHelper { *; }
#-keep public class android.database.Cursor { *; }
#-keep public class android.database.SQLException { *; }
#-keep public class java.lang.reflect.Field { *; }
#-keep public class java.io.* { *; }
#-keep public class java.util.* { *; }
#-keep public class java.nio.channels.FileChannel { *; }
#-keep public class com.behincom.behincome.SQL.sqlObjectHandler.deleteObjectHandler { *; }
#-keep public class com.behincom.behincome.SQL.sqlObjectHandler.insertObjectHandler { *; }
#-keep public class com.behincom.behincome.SQL.sqlObjectHandler.updateObjectHandler { *; }
#
#-keepclassmembers class com.behincom.behincome.SQL.* {
#   public *;
#}