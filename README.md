![效果图](F:\Blog\images\自定义TabLayout底部倒三角Indicaotor\preview.gif)



本篇是通过自定义系统自带的`material`库里面的`TabLayout`来实现上图效果。



通过分析，可以分为以下几个步骤：

- 绘制矩形
- 绘制倒三角
- 合并矩形和倒三角
- 设置indicator



整体效果用 `xml` 的 `layer-list` 来绘制。



整体背景是一个圆角矩形，一个正方形旋转45度角，放置底部，正方形上面部分与圆角矩形重叠，颜色设置成一致就能达到倒三角的效果。



![image-20200727114230178](F:\Blog\images\自定义TabLayout底部倒三角Indicaotor\image-20200727114230178.png)



![image-20200727114333311](F:\Blog\images\自定义TabLayout底部倒三角Indicaotor\image-20200727114333311.png)



在 ` drawable` 文件夹中创建一个 `layer_indicator.xml` 文件



###### 绘制矩形

```xml
<layer-list xmlns:android="http://schemas.android.com/apk/res/android">
    <item>
    	<shape>
        	<solid android:color="@color/blue"/>
            <corners android:radius="8dp"/>
        </shape>
    </item>
</layer-list>
```




![image-20200727114856176](F:\Blog\images\自定义TabLayout底部倒三角Indicaotor\image-20200727114856176.png)

`item` 不指定宽高，默认充满被设置`View`，因为不能确定`TabLayout`的`Tab`的宽高，因此不设置 `width` 和`height` 属性。



###### 绘制三角形

其实就是绘制一个旋转45度的正方向。

```xml
<item android:width="10dp" android:height="10dp" android:bottom="2dp"
      android:gravity="bottom|center_horizontal">
	<rotate android:fromDegress="45" 
            android:pivotX="50%"
            android:pivotY="50%">
    	<shape android:shape="rectangle">
        	<solid android:color="@color/red"/>
        </shape>
    </rotate>
</item>
```



![image-20200727115910854](F:\Blog\images\自定义TabLayout底部倒三角Indicaotor\image-20200727115910854.png)



###### 合并矩形和倒三角

```xml
<?xml version="1.0" encoding="utf-8"?>
<layer-list xmlns:android="http://schemas.android.com/apk/res/android">
    <!-- 绘制圆角矩形 -->
    // 1.
    <item android:bottom="5dp">
        <shape
            android:layout_height="match_parent"
            android:shape="rectangle">
            <solid android:color="@android:color/holo_blue_light" />
            <corners android:radius="8dp" />
        </shape>
    </item>

    <!-- 绘制正方形，旋转45度 -->
    // 2.
    <item
        android:width="10dp"
        android:height="10dp"
        android:bottom="2dp"
        android:gravity="bottom|center_horizontal">
        // 3.  4.
        <rotate
            android:fromDegrees="45"
            android:pivotX="50%"
            android:pivotY="50%">
            <shape android:shape="rectangle">
                <solid android:color="@android:color/holo_red_light" />
            </shape>
        </rotate>
    </item>
</layer-list>
```



代码解释：

1. `android:bottom="5dp"` 

   设置圆角矩形距离底部5dp，否则矩形底部与正方形底部重合就看不到倒三角的效果了。


<center class = "half">
	<img src = "F:\Blog\images\自定义TabLayout底部倒三角Indicaotor\no_bottom_5dp.png" width = "50%"  height = "300" align = left>
    <img src = "F:\Blog\images\自定义TabLayout底部倒三角Indicaotor\bottom_5dp.png" width = "50%" height = "300"  align = right>
</center>

















2. `android:gravity="bottom|center_horizontal"`

   正方形放置在底部，并且水平居中。



3. ` android:fromDegrees="45" `

   旋转的起始角度

   

4. `android:pivotX="50%" android:pivotY="50%"`

   设置正方形的旋转中心为正中心。默认旋转就是正方形中心，可以不设置。

   

###### 设置indicator

 // app:tabMode="scrollable" 设置TabLayout宽度超过屏幕可滚动
```xml
<com.google.android.material.tabs.TabLayout
	android:id="@+id/tabLayout"
 	android:layout_width="match_parent"
 	android:layout_height="wrap_content"
	android:background="#FFFFFF"
    android:elevation="5dp"
    android:paddingStart="10dp"
    android:paddingTop="5dp"
    android:paddingEnd="10dp"
    app:tabIndicator="@drawable/layer_indicator"
    app:tabMode="scrollable"
/>
```

`app:tabIndicator="@drawable/layer_indicator"`设置Indicator，进行绘制.



来看下效果图

![image-20200727135859395](F:\Blog\images\自定义TabLayout底部倒三角Indicaotor\image-20200727135859395.png)



并没有达到我们想要的效果，这是因为`indicator`的默认位置(`app:tabIndicatorGravity="bottom"`)在底部，所以绘制图形的时候只在底部绘制。



`app:tabIndicatorGravity`有4个属性：

- bottom  - 底部
- top - 顶部
- center - 中间
- stretch - 扩张，拉伸



<center class = "half">
	<img src = "F:\Blog\images\自定义TabLayout底部倒三角Indicaotor\image-20200727140450885.png" width = "25%" align = left>
    <img src = "F:\Blog\images\自定义TabLayout底部倒三角Indicaotor\image-20200727140537908.png" width = "25%"  align = right>
    <img src = "F:\Blog\images\自定义TabLayout底部倒三角Indicaotor\image-20200727140604889.png" width = "25%" align = left>
    <img src = "F:\Blog\images\自定义TabLayout底部倒三角Indicaotor\image-20200727141112177.png" width = "25%"  align = right>
</center>







可以看出，stretch 满足需求。



```
app:tabIndicatorGravity="stretch"
```



到这里，大致的功能基本上已经实现了，剩下的只有一些细节上的调整了。



###### TabLayout的一些API

**app:tabIndicatorFullWidth="true"** : 指示器是否填充宽度

<center>
	<img src = "F:\Blog\images\自定义TabLayout底部倒三角Indicaotor\image-20200727143431874.png" width = "50%" align = "left">
	<img src = "F:\Blog\images\自定义TabLayout底部倒三角Indicaotor\image-20200727143515184.png" width = "50%" align = "right">
</center>









**app:tabMode="scrollable"** : 模式 => scrollable 可滚动 | fixed => 固定屏幕宽度，超出屏幕宽度会压缩 | auto => 自动，在tab数量少的时候能看出区别

**为了效果对比明显，使用2个Tab来演示。**

<center>
	<img src = "F:\Blog\images\自定义TabLayout底部倒三角Indicaotor\image-20200727144013059.png" width = "33%" align = "left">
	<img src = "F:\Blog\images\自定义TabLayout底部倒三角Indicaotor\image-20200727144044334.png" width = "33%" align = "center">
	<img src = "F:\Blog\images\自定义TabLayout底部倒三角Indicaotor\image-20200727144111733.png" width = "33%" align = "right">
</center>



**- scrollable** : `Tab`靠左侧，当`Tab`超过屏幕宽度时可滑动；

**- fixed** : 铺满屏幕宽度，当`Tab`过多时，压缩每个`Tab`来占满屏幕宽度；

**- auto** : 屏幕宽度居中，当`Tab`过多时，效果与`scrollable`一致. 



**app:tabUnboundedRipple** : 无边界水波纹，当设置false时，水波纹有边界，为Tab所在的矩形边界

<center>
	<img src = "F:\Blog\images\自定义TabLayout底部倒三角Indicaotor\image-20200727144620613.png" width = "50%" align = "left">
	<img src = "F:\Blog\images\自定义TabLayout底部倒三角Indicaotor\image-20200727144702865.png" width = "50%" align = "right">
</center>







 **app:tabIndicatorColor="@color/colorAccent"** : 设置指示器的背景色



**app:tabPaddingStart / app:tabPaddingTop / a:tabPaddingEnd /  app:tabPaddingBottom** : 设置Tab上下左右内边距





layer_indicator.xml 



```xml
<?xml version="1.0" encoding="utf-8"?>
<layer-list xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:bottom="5dp">
        <shape
            android:layout_height="match_parent"
            android:shape="rectangle">
            <solid android:color="@android:color/holo_blue_light" />
            <corners android:radius="8dp" />
        </shape>
    </item>

    <item
        android:width="10dp"
        android:height="10dp"
        android:bottom="2dp"
        android:gravity="center_horizontal|bottom">
        <rotate
            android:fromDegrees="45"
            android:pivotX="50%"
            android:pivotY="50%">
            <shape android:shape="rectangle">
                <solid android:color="@android:color/holo_red_light" />
            </shape>
        </rotate>
    </item>
</layer-list>
```



activity_main.xml



```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context=".MainActivity">

    <com.google.android.material.tabs.TabLayout
        android:id="@+id/tabLayout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="#FFFFFF"
        android:elevation="5dp"
        android:paddingStart="10dp"
        android:paddingTop="5dp"
        android:paddingEnd="10dp"
        app:tabIndicatorGravity="stretch"
        app:tabIndicatorFullWidth="true"
        app:tabIndicator="@drawable/layer_indicator"
        app:tabIndicatorColor="@color/colorAccent"
        app:tabUnboundedRipple="true"
        app:tabMode="auto" />

    <androidx.viewpager.widget.ViewPager
        android:id="@+id/viewPager"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1" />
</LinearLayout>
```



##### 扩展

学会了上面的自定义，那我们就可以做出下面的效果了（忘记在哪个APP里面看到过这个效果了!!!）

![扩展效果图](F:\Blog\images\自定义TabLayout底部倒三角Indicaotor\gif_preview2.gif)





layer.xml

```xml
<com.google.android.material.tabs.TabLayout
    android:id="@+id/tabLayout"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:background="#FFFFFF"
    android:elevation="5dp"
    android:paddingStart="10dp"
    android:paddingTop="5dp"
    android:paddingEnd="10dp"
    app:tabIndicator="@drawable/layer"
    app:tabIndicatorColor="@android:color/holo_orange_light"
    app:tabIndicatorFullWidth="false"
    app:tabIndicatorGravity="center"
    app:tabMode="auto"
    app:tabUnboundedRipple="true" />
```



activity_main.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context=".MainActivity">

    <com.google.android.material.tabs.TabLayout
        android:id="@+id/tabLayout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="#FFFFFF"
        android:elevation="5dp"
        android:paddingStart="10dp"
        android:paddingTop="5dp"
        android:paddingEnd="10dp"
        app:tabIndicator="@drawable/layer"
        app:tabIndicatorColor="@android:color/holo_orange_light"
        app:tabIndicatorFullWidth="false"
        app:tabIndicatorGravity="center"
        app:tabMode="auto"
        app:tabUnboundedRipple="true" />

    <androidx.viewpager.widget.ViewPager
        android:id="@+id/viewPager"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1" />
</LinearLayout>
```

