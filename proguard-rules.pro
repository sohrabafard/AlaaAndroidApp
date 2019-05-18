

-dontwarn com.google.errorprone.annotations.**
-dontwarn javax.annotation.**
-dontwarn androidx.annotation.**
-keepattributes InnerClasses -keep class **.R -keep class **.R$* { <fields>; }

#keep public class *extends java.lang.annotation.Annotation


#crash 2
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-keep public class * extends java.lang.Exception
-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**
#-printmapping mapping.txt

-dontwarn com.sothree.**
-keep class com.sothree.**
-keep interface com.sothree.**