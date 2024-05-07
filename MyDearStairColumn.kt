package app.source.sample.customlayout

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable

@Composable
fun MyDearStairColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Layout(
        content = content,
        modifier = Modifier.then(modifier),
    ) { measurables, constraints ->

        // map parameter List<Measurable> to List<Placeable>
        val placeables: List<Placeable> = measurables.map { measurable ->
            measurable.measure(constraints)
        }

        // calculate our layout width and height
        val itemsTotalWidth = placeables.sumOf { placeable -> placeable.width }
        val ourLayoutTotalWidth = if (itemsTotalWidth > constraints.maxWidth) constraints.maxWidth else itemsTotalWidth
        val ourLayoutTotalHeight = placeables.sumOf { placeable -> placeable.height }

        // place child items
        layout(width = ourLayoutTotalWidth, height = ourLayoutTotalHeight) {
            var y = 0
            var x = 0

            placeables.forEach { placeable ->
                val itemHorizontalEndCoordinate = x + placeable.width
                if (itemHorizontalEndCoordinate > constraints.maxWidth) {
                    x = 0
                }

                placeable.place(x = x, y = y)

                y += placeable.height
                x += placeable.width
            }
        }
    }
}
