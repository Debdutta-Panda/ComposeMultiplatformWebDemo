import androidx.compose.runtime.*
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.css.keywords.auto
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.renderComposable
import org.w3c.dom.HTMLElement

external fun helloWorld()

@JsName("hljs")
@JsModule("highlight.js")
@JsNonModule
external class HighlightJs {
    companion object {
        fun highlightElement(block: HTMLElement)
    }
}

private fun HTMLElement.setHighlightedCode(code: String) {
    innerText = code
    HighlightJs.highlightElement(this)
}

@Composable
fun HighlightedCode(code: String) {
    Pre({
        style {
            maxHeight(25.em)
            overflow("auto")
            height(auto)
        }
    }) {
        Code({
            classes("language-kotlin", "hljs")
            style {
                property("font-family", "'JetBrains Mono', monospace")
                property("tab-size", 4)
                fontSize(10.pt)
                backgroundColor(Color("transparent"))
            }
        }) {
            DisposableEffect(code) {
                scopeElement.setHighlightedCode(code)
                onDispose {  }
            }
        }
    }
}

fun main() {
    var text by mutableStateOf(0)
    var mode by mutableStateOf(false)
    renderComposable(rootElementId = "root") {
        Button(
            attrs = {
                onClick {
                    mode = mode.not()
                }
            }
        ) {
            Text("Mode")
        }
        if(mode){
            Button(
                attrs = {
                    onClick {
                        console.log("Hello")
                        helloWorld()
                        text++
                    }
                    style {
                        background(Color.red.toString())
                    }
                }
            ) {
                Text("$text")
            }
        }
        else{
            HighlightedCode("""
                val a = 2;
                val b = 3;
            """.trimIndent())
        }
    }
}

