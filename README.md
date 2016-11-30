TimelyView
==============

[![](https://jitpack.io/v/iballan/TimelyView.svg)](https://jitpack.io/#iballan/TimelyView)

## Animated Time View like Timely app

Screenshots:
--------

![Screenshots/screenshot1](https://raw.githubusercontent.com/iballan/TimelyView/master/Screenshots/TimelyTimeView_screenshot.gif) 	 ![Screenshots/screenshot2](https://raw.githubusercontent.com/iballan/TimelyView/master/Screenshots/TimelyView_Screenshot.gif)



Usage :

XML Layout:
``` xml
<com.mbh.timelyview.TimelyTimeView
   android:id="@+id/ttv"
   android:layout_width="wrap_content"
   android:layout_height="60dp"
   app:rounded_corner="true"
   app:text_color="true"
   app:seperatorsTextSize="50"
   android:layout_gravity="center"/>
	   
	   
<com.mbh.timelyview.TimelyShortTimeView
	android:id="@+id/tstv_hours"
	android:layout_width="match_parent"
	android:layout_height="wrap_content"
	app:rounded_corner="true"
	app:text_color="true"
	app:seperatorsTextSize="50"/>
```

Java:
``` java
    public class MainActivity extends Activity {
			private TimelyTimeView ttv; 
			TimelyShortTimeView tstv_hours;
			@Override
			protected void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				setContentView(R.layout.activity_main);
				// TimelyTimeView
				ttv = (TimelyTimeView) findViewById(R.id.ttv);
				ttv.setTextColor(Color.WHITE);
				ttv.setSeperatorsTextSize(50);
				
				// Then whenever you want to set the time and animate to it
				ttv.setTime("00:00:00"); // As formatted String 
				// OR
				ttv.setTime(new int[]{12:12:12});
				// OR
				ttv.setTime(new Date());
				
				// ------------------
				// TimelyShortTimeView for (hour:min)
				tstv_hours = (TimelyShortTimeView) findViewById(R.id.tstv_hours);
				tstv_hours.setTextColor(Color.BLACK);
				tstv_hours.setTimeFormat(TimelyShortTimeView.FORMAT_HOUR_MIN); // can be set as TimelyShortTimeView.FORMAT_MIN_SEC
				tstv_hours.setSeperatorsTextSize(50);
				
				// Then whenever you want to set the time and animate to it
				ttv.setTime("00:00"); // As formatted String 
				// OR
				ttv.setTime(new int[]{20:20});
				// OR
				ttv.setTime(new Date());
				// OR
				long timeAsMilliseconds = new Date().getTime();
				ttv.setTime(timeAsMilliseconds);
			}
   }
```

Install
--------

You can install using Gradle:

```gradle
	repositories {
	    maven { url "https://jitpack.io" }
	}
	dependencies {
	    compile 'com.github.iballan:TimelyView:1.0.2'
	}
```

Contact me:
--------

Twitter: [@mbh01t](https://twitter.com/mbh01t)

Github: [iballan](https://github.com/iballan)

Website: [www.mbh01.com](http://mbh01.com)

Credits:
--------

TimelyTextView : https://github.com/adnan-SM/TimelyTextView

Sriram Ramani article : http://sriramramani.wordpress.com/2013/10/14/number-tweening/

License
--------

    Copyright 2014 Mohamad Ballan.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.