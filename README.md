说明：
1、基于用于android json编解码的一套java bean生成工具；
2、使用xml配置java bean属性，自动生成java代码；
3、自动的java代码是基于 org.json 完成json解析的，所以需要有 org.json；

使用步骤：
1、通过 json_bean.xml 配置java bean 相关属性；
 json_bean.xml的使用注意点：
 a、包含三种标签：root、bean、attr：
 root：跟标签标识；
 bean：java bean 标识；
 attr：java bean 属性标识
 b、属性及其含义：
 |---------------------------------------------------------------------------------------|
 | 属性     | 所在标签 | 含义                                               | 是否必须值 |
 |---------------------------------------------------------------------------------------|
 | name     | bean     | java bean 的名称，也是对应的java类名               | 是         |
 | name     | attr     | 该属性在json中的名称                               | 是         |
 | alias    | attr     | 该属性在java bean中的名称，没有配置默认使用name    | 否         |
 | type     | attr     | 该属性的类型，同java的类型，如int,int[],String[]等 | 是         |
 | nullable | attr     | 改属性是否可空                                     | 否         |
 |---------------------------------------------------------------------------------------|
 
2、进入JsonMain.java文件，修改输出的路径和输出类的包名，修改位置如下两个变量值：
   String outDir = "JsonCodecer\\src\\com\\jejay\\android\\jsoncodec\\output";
   String packageName = "com.jejay.android.jsoncodec.output";
   
3、运行JsonMain.java的main方法，就可以在目标输出路径自动生成java代码；

4、使用者通过生成的java bean的 decode(String jsonStr)方法参数传入 json字符串就能解析出目标对象；
