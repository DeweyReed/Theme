# [WIP, Experimental] Theme

With the release of [`material-components-android 1.1.0`](https://github.com/material-components/material-components-android/releases/tag/1.1.0) and Android Q, many android view components can be themed at runtime.

`Theme` retints Android views after their creation to provide dynamic theming support for the app.

## WARNING

**Don't use `Theme` in the production unless you've understood the library and the risk.**

**Jetpack Compose supports dynamic theming out of the box, which should be your choice during the production (in the future).**

## Limitation

Because of the limitaion of Android View system, It's very difficult to tint some widgets(like `TimePicker`) unless we hack or use many reflections. `Theme` also breaks some widgets(like `MaterailDatePicker`). So `Theme` only works partly with `material-components-android 1.1.0`, not 1.0.0 or 1.2.0 and later.

## Usage

### 1. Dependency

[Try a snapshot from JitPack.](https://jitpack.io/#DeweyReed/Theme)

### 2. Initilize

Define theme colors:

```XML
<resources>
    <color name="colorPrimary">#008577</color>
    <color name="colorPrimaryVariant">#00574B</color>
    <color name="colorOnPrimary">#FFFFFF</color>
    <color name="colorSecondary">#D81B60</color>
    <color name="colorSecondaryVariant">#A00037</color>
    <color name="colorOnSecondary">#FFFFFF</color>
</resources>
```

- **Color resources name must be identical to the names above.**
- **Color values must be formatted as `#RRGGBB`. Color references won't work** because of [how `TypedArray.getResourceId` works](https://developer.android.com/reference/android/content/res/TypedArray.html#getResourceId(int,%20int)).

Then add a attribute to your root theme:

```XML
<style name="AppTheme" parent="Theme.MaterialComponents.DayNight.NoActionBar">
    <item name="colorPrimary">@color/colorPrimary</item>
    <item name="colorPrimaryVariant">@color/colorPrimaryVariant</item>
    ...

    <!-- Add this line -->
    <item name="viewInflaterClass">xyz.aprildown.theme.ThemeViewInflater</item>
</style>
```

Then in your App:

```Kotlin
Theme.init(ContextThemeWrapper(this, R.style.AppTheme)) {
    // Optional. Provide initial colors here.
    // The usage is same as the code below.
}
```

- Use a themed context because we need to resolve color resources.

### 3. Edit

```Kotlin
Theme.edit(this) {
    colorPrimaryRes = R.color.md_amber_500
    colorPrimaryVariantRes = R.color.md_amber_800
    colorOnPrimary = on(colorPrimary)
    colorSecondaryRes = R.color.md_blue_500
    colorSecondaryVariantRes = R.color.md_blue_800
    colorOnSecondary = on(colorSecondary)
}
```

- **After editting, you have to recreate activities in the backstack on your own.**
- Variables ending with `Res` expect a `ColorRes`. Other variables expect a `ColorInt`.

## License

[Apache License 2.0](LICENSE)
