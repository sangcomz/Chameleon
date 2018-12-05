
# Chameleon 


<img src="/pic/logo.png" width = 20%> 

[![](https://jitpack.io/v/sangcomz/chameleon.svg)](https://jitpack.io/#sangcomz/chameleon)

Chameleon deals with the Status of RecyclerView.

## What's New in 0.1.1? :tada:
- [New Feature] change text and icons dynamically
- [New Feature] Chameleon is available in all views

## How to Use

### Gradle
```groovy
    repositories {
        maven { url 'https://jitpack.io' }
    }

    dependencies {
        compile 'com.github.sangcomz:Chameleon:v0.1.1'
    }
```
### Usage
```xml
<xyz.sangcomz.chameleon.Chameleon xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/root"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:emptyButtonBackgroundColor="@color/colorPrimary"
    app:emptyButtonText="+Add Chameleon"
    app:emptyButtonTextColor="#ffffff"
    app:emptyButtonTextSize="12sp"
    app:emptyDrawable="@drawable/ic_empty"
    app:emptySubText="@string/sub_empty"
    app:emptyText="@string/empty"
    app:errorButtonBackgroundColor="@color/colorPrimary"
    app:errorButtonText="Retry"
    app:errorButtonTextColor="#ffffff"
    app:errorButtonTextSize="12sp"
    app:errorDrawable="@drawable/ic_error"
    app:errorSubText="@string/sub_error"
    app:errorText="@string/error"
    app:isLargeProgress="true"
    app:progressDrawable="@drawable/drawable_progress"
    app:useEmptyButton="true"
    app:useErrorButton="true"
    app:defaultState="LOADING"
    tools:context="xyz.sangcomz.chameleonsample.MainActivity">

    <android.support.v7.widget.RecyclerView
        android:id="@+id/rv_main_list"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

</xyz.sangcomz.chameleon.Chameleon>
```

#### STATUS
|  Status  | Description       |
|:--------:|-------------------|
|  CONTENT | show content      |
|  LOADING | show progress     |
|  EMPTY   | show empty view   |
|  ERROR   | show error view   |
|  NONE    | show RecyclerView |

##### usage
```kotlin
// Set state using showState
chameleon.showState(Chameleon.STATE.CONTENT)
chameleon.showState(Chameleon.STATE.LOADING)
chameleon.showState(Chameleon.STATE.EMPTY)
chameleon.showState(Chameleon.STATE.ERROR)
chameleon.showState(Chameleon.STATE.NONE)

// Or use the extension functions
chameleon.setContent()
chameleon.setLoading()
chameleon.setEmpty()
chameleon.setError()
chameleon.setNone()

// As well as toggling between states with a boolean
viewModel.isLoading.observe(this, Observer {
    chameleon.loadingOrContent(it) // true = LOADING, false = CONTENT
})

chameleon.contentOrEmpty(true) // CONTENT
chameleon.contentOrEmpty(false) // EMPTY

chameleon.setEmptyButtonClickListener { Toast.makeText(this, "Empty Button!", Toast.LENGTH_LONG).show() }
chameleon.setErrorButtonClickListener { Toast.makeText(this, "Error Button!", Toast.LENGTH_LONG).show() }
chameleon.setStateChangeListener { newState, oldState -> Log.d("Main", "Was $oldState is now $newState") }
```

#### attribute

|      Attribute Name        | Description                               |    Default Value    |
|:--------------------------:|-------------------------------------------|:-------------------:|
|          emptyText         | empty view Change text                    |       "empty"       |
|       emptyTextColor       | empty view Change text color              |       #808080       |
|        emptyTextSize       | empty view Change text size               |         24sp        |
|       emptyTextGravity     | empty view Change text gravity            |          0          |
|        emptySubText        | empty view Change sub text                |   "empty content"   |
|      emptySubTextColor     | empty view Change sub text color          |       #b7b7b7       |
|      emptySubTextSize      | empty view Change sub text size           |         16sp        |
|     emptySubTextGravity    | empty view Change sub text gravity        |          0          |
|        emptyDrawable       | empty view Change drawable                | R.drawable.ic_empty |
|       emptyButtonText      | empty view Change button text             |       "retry"       |
|    emptyButtonTextColor    | empty view Change button text color       |       #808080       |
|     emptyButtonTextSize    | empty view Change button text size        |         24sp        |
| emptyButtonBackgroundColor | empty view Change button background color |       #b7b7b7       |
|       useEmptyButton       | Change whether to use empty view Button   |        false        |
|          errorText         | error view Change text                    |        "error"      |
|       errorTextColor       | error view Change text color              |       #808080       |
|        errorTextSize       | error view Change text size               |         24sp        |
|       errorTextGravity     | empty view Change text gravity            |          0          |
|        errorSubText        | error view Change sub text                |    "error content"  |
|      errorSubTextColor     | error view Change sub text color          |       #b7b7b7       |
|      errorSubTextSize      | error view Change sub text size           |         16sp        |
|     errorSubTextGravity    | error view Change sub text gravity        |          0          |
|        errorDrawable       | error view Change drawable                | R.drawable.ic_error |
|       errorButtonText      | error view Change button text             |        "retry"      |
|    errorButtonTextColor    | error view Change button text color       |       #808080       |
|     errorButtonTextSize    | error view Change button text size        |         24sp        |
| errorButtonBackgroundColor | error view Change button background color |       #808080       |
|       useErrorButton       | Change whether to use error view Button   |        false        |
|      progressDrawable      | progress drawable setting                 |          -          |
|       isLargeProgress      | Whether to use large progress             |        false        |
|        defaultState        | Sets the initial state for Chameleon      |         NONE        |

Note - A state of `NONE` means Chameleon won't do anything, and will just show the RecyclerView.


## Result Screen

| Project Name | Result Screen   |
|:---------:|---|
| Sample  <p style="float:left;"> <a href="https://play.google.com/store/apps/details?id=xyz.sangcomz.chameleonsample"> <img HEIGHT="40" WIDTH="135" alt="Get it on Google Play" src="https://play.google.com/intl/en_us/badges/images/apps/en-play-badge.png" /></a></p> |  <img src="/pic/sample.gif"> |

# Contribute
We welcome any contributions.

# Inspired by
 * [Kennyc1012/MultiStateView](https://github.com/Kennyc1012/MultiStateView), I was inspired by his code.

# License

    Copyright 2018 Jeong Seok-Won

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
