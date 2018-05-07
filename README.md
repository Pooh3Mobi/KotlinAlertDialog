# KotlinAlertDialog
Domain Specific Language Alert Dialog



```kotlin
    alert {
        title    { + "this is title" }
        message  { + "this is message" }
        positive { + "agree!" +
                {

                    Snackbar.make(view, "you chose positive action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show()

                }
        }
        negative { + "deny!" +
                {

                    Snackbar.make(view, "you chose negative action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show()

                }
        }
    }.build(this).show()
```
