# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\Android studio\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
  -keep public class android.support.design.** {*;}     # 保持哪些类不被混淆
  -keep                        保留类和类中的成员，防止被混淆或移除
  -keepnames                   保留类和类中的成员，防止被混淆，成员没有被引用会被移除
  -keepclassmembers            只保留类中的成员，防止被混淆或移除
  -keepclassmembernames        只保留类中的成员，防止被混淆，成员没有引用会被移除
  -keepclasseswithmembers      保留类和类中的成员，防止被混淆或移除，保留指明的成员
  -keepclasseswithmembernames  保留类和类中的成员，防止被混淆，保留指明的成员，成员没有引用会被移除
  -keepattributes  保留某些属性不被混淆，可选（*Annotation*,InnerClasses，Signature，SourceFile,LineNumberTable）
