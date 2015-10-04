# Popular-Movies

Android application which  shows information about the movies by two categories with the movies title,releasing date,rating ,trailer and comments. It uses the api provided by the [themoviedb](http://api.themoviedb.org). 

1.  Most popular
2.  Top reated


### How to use this application?

For running this application you have to keep your api key provided by the themoviedb in the projects  data/Constants.java of field **API_KEY** as :


```
public class Constants {

    public static final String MOVIE_BASE_URL = "http://api.themoviedb.org/";
    public static final String API_KEY="***your_api_key***";

    public static final String YOUTUBE_INTENT_BASE_URI="vnd.youtube://";


}
```

##Library Used

1. [ButterKnife](http://jakewharton.github.io/butterknife/) for view injection
2. [Piccaso](http://square.github.io/picasso/) for caching and dowloading the image from the web.
3. [Retrofit](http://square.github.io/retrofit/) version 2.0 for fetching data from the web
4. [Otto](http://square.github.io/otto/) as a event bus.
5. [SelectableRoundedImageView](https://github.com/pungrue26/SelectableRoundedImageView) used for supporting the differnt raddi in the imageview's corner.


### Dependencies Used
```
    compile 'com.android.support:appcompat-v7:23.0.1'
    compile 'com.android.support:recyclerview-v7:22.2.0'
    compile 'com.android.support:cardview-v7:21.0.+'
    compile 'com.android.support:preference-v7:23.0.0'
    compile 'com.android.support:design:23.0.1'
    compile 'com.android.support:palette-v7:23.0.0'
    
    //for view injection
    compile 'com.jakewharton:butterknife:7.0.1'
    
    //for the different radii of imageview
    compile 'com.joooonho:selectableroundedimageview:1.0.1'

    //for image loading
    compile 'com.squareup.picasso:picasso:2.5.2'
    
    // for HTTP request
    compile 'com.squareup.retrofit:retrofit:2.0.0-beta1'
    
    //for the converting JSON string to an equivalent Java object
    compile 'com.squareup.retrofit:converter-gson:2.0.0-beta1'
    
    //for event
    compile 'com.squareup:otto:1.3.8'
```


### Android 
```
 compileSdkVersion 23
 buildToolsVersion "23.0.1"
 minSdkVersion 21
 targetSdkVersion 21
 
```
