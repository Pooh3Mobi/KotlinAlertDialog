package mobi.pooh3.kotlinalertdialog

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            alert {
                title {
                    label("this is title")
                }
                message {
                    label("this is message")
                }
                positive {
                    label("agree!") {

                        Snackbar.make(view, "you chose positive action", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show()

                    }
                }
                negative {
                    label("deny!") {

                        Snackbar.make(view, "you chose negative action", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show()

                    }
                }
            }.build(this).show()
        }
    }
}

fun alert(init: Alert.() -> Unit): Alert {
    val alert = Alert()
    alert.init()
    return alert
}

class Alert {
    val components =arrayListOf<TextComponent>()
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
    fun build(context: Context): AlertDialog =
            with(AlertDialog.Builder(context)) {
                for (c in components) {
                    when (c) {
                        is Title -> this.setTitle(c.text)
                        is Message -> this.setMessage(c.text)
                        is PositiveButton -> this.setPositiveButton(c.text, { _, _ -> c.action() })
                        is NegativeButton -> this.setNegativeButton(c.text, { _, _ -> c.action() })
                    }
                }
                this.create()
            }
}

abstract class TextComponent {
    lateinit var text: String
}
fun TextComponent.label(text: String) {
    this.text = text
}

abstract class ButtonComponent: TextComponent() {
    lateinit var action: () -> Unit

}
fun ButtonComponent.label(text: String, action: () -> Unit) {
    this.text = text
    this.action = action
}
class Title: TextComponent()
class Message: TextComponent()
class PositiveButton: ButtonComponent()
class NegativeButton: ButtonComponent()