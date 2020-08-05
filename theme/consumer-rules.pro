# AppCompatViewInflater reads the viewInflaterClass theme attribute which then
# reflectively instantiates MaterialComponentsViewInflater using the no-argument
# constructor. We only need to keep this constructor and the class name if
# AppCompatViewInflater is also being kept.
# https://github.com/material-components/material-components-android/commit/d7a5dc8fb76379e6b1d64c6d5d6c7376665ec653
-if class com.google.android.material.theme.MaterialComponentsViewInflater
-keep class xyz.aprildown.theme.ThemeViewInflater {
    <init>();
}
