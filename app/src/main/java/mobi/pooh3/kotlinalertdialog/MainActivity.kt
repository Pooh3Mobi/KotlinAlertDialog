package mobi.pooh3.kotlinalertdialog

import android.os.Bundle
import android.support.design.widget.Snackbar
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

            showAlert(
                    ttlTxt = "this is title",
                    msgTxt = "this is message",
                    posTxt = "agree!",
                    posAct = {

                        Snackbar.make(view, "you chose positive action", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show()

                    },
                    negTxt = "deny!",
                    negAct = {

                        Snackbar.make(view, "you chose negative action", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show()

                    }
            )

        }
    }
}

fun showAlert(ttlTxt: String, msgTxt: String, posTxt: String, posAct: () -> Unit, negTxt: String, negAct: () -> Unit) {
    alert {
        title {
            label(ttlTxt)
        }
        message {
            label(msgTxt)
        }
        positive {
            label(posTxt){ posAct() }
        }
        negative {
            label(negTxt){ negAct() }
        }
    }
}

fun alert(init: Alert.() -> Unit): Alert {
    val alert = Alert()
    alert.init()
    return alert
}

class Alert {
    fun title(init: Title.() -> Unit): Title {
        val title = Title()
        title.init()
        return title
    }
    fun message(init: Message.() -> Unit): Message {
        val message = Message()
        message.init()
        return message
    }
    fun positive(init: PositiveButton.() -> Unit): PositiveButton {
        val posBtn = PositiveButton()
        posBtn.init()
        return posBtn
    }
    fun negative(init: NegativeButton.() ->Unit): NegativeButton {
        val negBtn = NegativeButton()
        negBtn.init()
        return negBtn
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