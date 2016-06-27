# AndroidTag
[![Bless](https://cdn.rawgit.com/LunaGao/BlessYourCodeTag/master/tags/ramen.svg)](http://lunagao.github.io/BlessYourCodeTag/)
[![Build Status](https://travis-ci.org/LunaGao/AndroidTag.svg?branch=master)](https://travis-ci.org/LunaGao/AndroidTag)
[![Dependency Status](https://www.versioneye.com/user/projects/57710b696718940036449187/badge.svg?style=flat-square)](https://www.versioneye.com/user/projects/57710b696718940036449187)

# 效果图
![1](/Image/1.png)

# 如何引入
## Gradle
```
compile 'com.luna.gao.androidtag_core:androidtag-core:0.4'
```
## Maven
```
<dependency>
  <groupId>com.luna.gao.androidtag_core</groupId>
  <artifactId>androidtag-core</artifactId>
  <version>0.4</version>
  <type>pom</type>
</dependency>
```

# 如何使用
别忘了引用
```
xmlns:tag="http://schemas.android.com/apk/res-auto"
```
布局文件如下
```
<com.luna.gao.androidtag_core.AndroidTag
            android:id="@+id/tag_1"
            android:padding="10dp"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            tag:tagBorderType="round"
            tag:tagType="selected"
            tag:tagTitleText="this is the tag title"
            tag:tagBorderColor="#000000"
            tag:tagTitleTextSize="13sp" />
```

# 进阶
* **`tagTitleText`** _string_ tag的标题
* **`tagTitleTextSize`** _dimension_ tag字体大小
* **`tagTitleColor`** _color_ tag字体颜色
* **`tagBorderRadius`** _dimension_ tag边框圆角弧度
* **`tagBorderPaddingTop`** _dimension_ tag边框顶部距离文字的距离
* **`tagBorderPaddingLeft`** _dimension_ tag边框右边距离文字的距离
* **`tagBorderPaddingBottom`** _dimension_ tag边框底部距离文字的距离
* **`tagBorderPaddingRight`** _dimension_ tag边框左边距离文字的距离
* **`tagBackgroundColor`** _color_ tag背景色
* **`tagBorderWidth`** _dimension_ tag边框线条宽度
* **`tagBorderColor`** _color_ tag边框颜色
* **`tagBorderType`** _enum_ tag边框类型，`round`为圆角、 `square`为直角
* **`tagOnClickTitleText`** _string_ tag在点击时的标题文字，不设置时与tagTitleText相同
* **`tagOnClickTitleColor`** _color_ tag在点击时的标题颜色
* **`tagOnClickBackgroundColor`** _color_ tag在点击时的背景颜色
* **`tagOnClickBorderWidth`** _dimension_ tag在点击时的边框线条宽度
* **`tagOnClickBorderColor`** _color_ tag在点击时的颜色
* **`tagSelectedTitleText`** _string_ tag在选中时的标题文字，不设置时与tagTitleText相同
* **`tagSelectedTitleColor`** _color_ tag在选中时的标题颜色
* **`tagSelectedBackgroundColor`** _color_ tag在选中时的背景颜色
* **`tagSelectedBorderWidth`** _dimension_ tag在选中时的边框线条宽度
* **`tagSelectedBorderColor`** _color_ tag在选中时的边框颜色
* **`tagIsSelected`** _boolean_ tag是否选中
* **`tagType`** _enum_ tag可以被选择`selected`，若不可选择则为`normal`


# Demo
本项目中含有Demo，其中包含从xml中使用和从代码中创建两种方式。
