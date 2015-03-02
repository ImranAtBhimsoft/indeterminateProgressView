# Indeterminate Progress View

Indeterminate Progress is a light weight custom view.
###Demo
![alt text](https://github.com/ImranAtArbisoft/indeterminateProgressView/blob/master/demo.gif "Demo gif")
###Usage
##### Xml code
 - Default xml code:
```sh
<uk.macrotechnologies.library.IndeterminateProgressView
        android:layout_width="wrap_content"
        android:id="@+id/progressIndicator"
        android:layout_height="wrap_content" />
```
- Custom xml code:
```sh
<uk.macrotechnologies.library.IndeterminateProgressView
        android:layout_width="64dp"
        android:layout_gravity="center_horizontal"
        android:layout_marginTop="16dp"
        app:reverseAnimation="false"
        app:drawCircles="true"
        app:outCircleColor="#0000FF"
        app:outCircle="true"
        app:interpolator="LinearInterpolator"
        android:layout_height="64dp" />
```
##### NOTE:
######You have to use xml schema as below i your .xml file
```sh
 xmlns:app="http://schemas.android.com/apk/res-auto"
```
##### Java Code:
```sh
IndeterminateProgressView progressIndicator=(IndeterminateProgressView)view.findViewById(R.id.progressIndicator);
progressIndicator.setColorScheme(Color.BLACK, Color.BLUE, Color.GREEN, Color.MAGENTA, Color.RED);
```
**For more detail go [here] [1]**

All files in this repository are under the [Apache License][2], Version 2.0 unless noted otherwise.

[1]:http://imranatarbisoft.github.io/indeterminateProgressView/
[2]:https://github.com/ImranAtArbisoft/indeterminateProgressView/blob/master/LICENSE