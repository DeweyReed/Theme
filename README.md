# [WIP] Theme

> Q: How to add theme feature to your already amazing Android app to make it more amazing?  
A: Simply tint every view with a color.

So Theme does all those tedious work for you.

**This library is highly inspired and mostly borrowed from [aesthetic](https://github.com/afollestad/aesthetic). You may want to check it first.**

What Theme can do:

- Adds theme feature to an app with minimal code
- Supports any color tint
- No need to create many styles in `style.xml` any more
- Simple and easy to integrate
- Doesn't require too many third-party dependencies(kotlin, appcompat, recyclerview for core module, material for material module)
- AndroidX support

What Theme CANNOT do:

- Change theme dynamically. You'll have to recreate Activity and their back stack.
- Support non-AndroidX project

## Screenshots

Just image your app turns into any color theme.

## Usage

1. Install dependency

    Not available yet. Create an AAR on your own and use it for now.

1. Extends `ThemeActivity` as your base activity:

    ```Kotlin
    class MainActivity : ThemeActivity() {
        ...
    }
    ```

    Or manually:

    **The order here is important.**

    ```Kotlin
    class MyActivity : AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            Theme.attach(this)
            super.onCreate(savedInstanceState)
        }

        override fun onResume() {
            super.onResume()
            Theme.resume(this)
        }

        override fun onPause() {
            Theme.pause(this)
            super.onPause()
        }
    }
    ```

1. In your `App`:

    ```Kotlin
    class App : Application() {
        override fun onCreate() {
            super.onCreate()
            // 1
            Theme.init(this) {
                setColorPrimaryRes(R.color.colorPrimaryDefault)
                setColorPrimaryDarkRes(R.color.colorPrimaryDarkDefault, true)
                setColorAccentRes(R.color.colorAccentDefault)
            }
            // 2 Optional
            Theme.get().addDelegate(MaterialInflationDelegate())
        }
    }
    ```

    1: Initialize `Theme` and set a default theme if this is the first run.

    2: [Optional] Let `Theme` also tint views from Material Components.

## Change Theme

```Kotlin
Theme.edit(context) {
    setColorPrimaryRes(R.color.md_amber_500, true)
    setColorAccentRes(R.color.md_deep_purple_400)
    setColorNavigationBarRes(R.color.md_amber_500)
}
// 1
activity.recreate()
```

**1: After `Theme.edit`, you must recreate current activity to make it visible.** This is when you wish your app is using single activity and multiple fragments framework.

## Retrieve Theme Colors

```Kotlin
Theme.get().run {
    isDark
    colorPrimary
    colorPrimaryDark
    colorAccent
    colorStatusBar
    colorNavigationBar
    attribute("custom attribute")
}
```

`ColorInt` is returned.

## Tint Custom View or Ignore Some Views

Use `Theme.get().addDelegate()` and put your delegate to first position.

## Contribute

Just remember this library is supposed to be simple and easy.

## License

aesthetic: [Apache License 2.0](https://github.com/afollestad/aesthetic/blob/master/LICENSE.md)

Theme: [Apache License 2.0](https://github.com/DeweyReed/Theme/blob/master/LICENSE)