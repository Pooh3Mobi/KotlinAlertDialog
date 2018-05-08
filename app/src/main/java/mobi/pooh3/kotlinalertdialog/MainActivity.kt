package mobi.pooh3.kotlinalertdialog

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            alert(cancelable = false) {
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
            }.show()
        }
    }
}



fun Context.alert(cancelable: Boolean, init: Alert.() -> Unit): Alert {
    val alert = Alert(context = this, cancelable = cancelable)
    alert.init()
    return alert
}

class Alert(private val context: Context, private val cancelable: Boolean = true) {
    private val components = arrayListOf<TextComponent>()
    fun title(init: Title.() -> Unit): Title {
        val title = Title()
        components.add(title)
        title.init()
        return title
    }
    fun message(init: Message.() -> Unit): Message {
        val message = Message()
        components.add(message)
        message.init()
        return message
    }
    fun positive(init: PositiveButton.() -> Unit): PositiveButton {
        val posBtn = PositiveButton()
        components.add(posBtn)
        posBtn.init()
        return posBtn
    }
    fun negative(init: NegativeButton.() ->Unit): NegativeButton {
        val negBtn = NegativeButton()
        components.add(negBtn)
        negBtn.init()
        return negBtn
    }
    fun show() =
            let { AlertDialog.Builder(context) }
                    .apply {
                        setCancelable(cancelable)
                        for (c in components) {
                            when (c) {
                                is Title -> setTitle(c.text)
                                is Message -> setMessage(c.text)
                                is PositiveButton -> setPositiveButton(c.text, { _, _ -> c.action() })
                                is NegativeButton -> setNegativeButton(c.text, { _, _ -> c.action() })
                            }
                        }
                    }
                    .run { create().show() }
}

abstract class TextComponent {
    lateinit var text: String
        private set
    operator fun String.unaryPlus() {
        this@TextComponent.text = this
    }
}

abstract class ButtonComponent: TextComponent() {
    lateinit var action: () -> Unit
        private set
    operator fun Unit.plus(action: () -> Unit) {
        this@ButtonComponent.action = action
    }
}
class Title: TextComponent()
class Message: TextComponent()
class PositiveButton: ButtonComponent()
class NegativeButton: ButtonComponent()