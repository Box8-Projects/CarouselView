# CarouselView

The CarouselRecyclerView provides an easy way to display a list of images which shows a smooth carousel effect on scrolling. Features of this library includes -  
*  Dynamic image loading from the internet
*  Auto scrolling within fixed interval
* Provides Infinite scrolling feature
* Customisation for snapping scrolled image position (centre, start, end, etc)

## Implementation
CarouselRecyclerView can be included in any given layout xml file using the following xml tag.
```
<com.poncho.carouselrecyclerview.CarouselRecyclerView  
  android:id="@+id/carousel_recycler_view"  
  android:layout_width="match_parent"  
  android:layout_height="wrap_content" />
```
The view can be accessed from a java file using CarouselRecyclerView class. 
`CarouselRecyclerView` 
```
CarouselRecyclerView carouselRecyclerView = findViewById(R.id.carousel_recycler_view);
```
Various parameters of CarouselRecyclerView can be configured using the CarouselRecyclerView.Builder class.
```
CarouselViewPager.Builder builder = 
    new CarouselViewPager.Builder(ArrayList<String> urls)  // set the list of urls of images 
        .setIsInfinite(boolean isInfinite)                 // set if infinite scrolling is enabled
        .setChangeInterval(long milliSeconds)              // set scroll interval
        .setWidth(int width)                               // set the width of a single banner
        .setAdjustX(int adjustX)                           // set the value in px by which recycler view should be scrolled horizonatally if any adjustment is required
        .setFlingListener(RecyclerView.OnFlingListener flingListener)  // set custom fling listener for recycler view if required
        .setSnapHelper(SnapHelper snapHelper)              // set custom SnapHelper if required
        .setViewGroup(ViewGroup viewGroup)                 // set View Group (eg. Swipe Refresh Layout) to co-ordinate nested scrolling
        .build();                                          // Return builder for config of CarouselRecyclerView

carouselRecyclerView.config(builder);

```

## Future Scope
This library is made for infite views/scrolling of images. As of now there is a specified value of 500 images it would create. This can be made dynamic to show the images with auto scrolling.
