package io.github.kabirnayeem99.mushafease.presentation.common.htmlcompose


import android.text.style.BulletSpan
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import android.text.style.SubscriptSpan
import android.text.style.SuperscriptSpan
import android.text.style.TypefaceSpan
import android.text.style.UnderlineSpan
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat

private const val URL_TAG = "url_tag"

@Composable
fun HtmlText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle.Default,
    softWrap: Boolean = true,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    fontSize: TextUnit = 14.sp,
    lineHeight: TextUnit = 1.5.sp,
    flags: Int = HtmlCompat.FROM_HTML_MODE_COMPACT,
) {
    val content = text.asHTML(fontSize, flags)

    Text(
        modifier = modifier,
        text = content,
        style = style,
        softWrap = softWrap,
        overflow = overflow,
        maxLines = maxLines,
        onTextLayout = onTextLayout,
        lineHeight = lineHeight,
    )
}

@Composable
private fun String.asHTML(
    fontSize: TextUnit,
    flags: Int,
) = buildAnnotatedString {
    val spanned = HtmlCompat.fromHtml(this@asHTML, flags)
    val spans = spanned.getSpans(0, spanned.length, Any::class.java)

    append(spanned.toString())

    spans.filter { it !is BulletSpan }.forEach { span ->
        val start = spanned.getSpanStart(span)
        val end = spanned.getSpanEnd(span)
        when (span) {
            is RelativeSizeSpan -> span.spanStyle(fontSize)
            is StyleSpan -> span.spanStyle()
            is UnderlineSpan -> span.spanStyle()
            is ForegroundColorSpan -> span.spanStyle()
            is TypefaceSpan -> span.spanStyle()
            is StrikethroughSpan -> span.spanStyle()
            is SuperscriptSpan -> span.spanStyle()
            is SubscriptSpan -> span.spanStyle()
            else -> null
        }?.let { spanStyle ->
            addStyle(spanStyle, start, end)
        }
    }
}