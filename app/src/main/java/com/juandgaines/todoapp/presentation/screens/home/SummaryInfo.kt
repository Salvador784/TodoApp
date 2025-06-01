package com.juandgaines.todoapp.presentation.screens.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.juandgaines.todoapp.R
import com.juandgaines.todoapp.ui.theme.AppTheme

@Composable
fun SummaryInfo(
    modifier: Modifier = Modifier,
    date: String = "March 9, 2024",
    taskSummary: String = "10 tasks, 4 Incompleted, 6 Completed",
    completedTasks: Int,
    totalTask: Int,
) {
    val angleRatio = remember {
        androidx.compose.animation.core.Animatable(0f)
    }

    LaunchedEffect(key1 = completedTasks, key2 = totalTask) {
        if (totalTask != 0)
            angleRatio.animateTo(
                targetValue = completedTasks / totalTask.toFloat(),
                animationSpec = androidx.compose.animation.core.tween(
                    durationMillis = 1000
                )
            )
        else
            return@LaunchedEffect
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = modifier.padding(16.dp)
                .weight(1.5f)

        ) {
            Text(
                text = date,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(R.string.summary, taskSummary),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
            )

        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .padding(16.dp)
                .aspectRatio(1f)
                .weight(1f)

        ) {
            val colorBase = MaterialTheme.colorScheme.inversePrimary
            val colorProgress = MaterialTheme.colorScheme.primary
            val strokeWith = 16.dp
            Canvas(
                modifier = Modifier.aspectRatio(1f)
            ) {
                drawArc(
                    colorBase,
                    startAngle = 0f, // Desde donde se dibuja
                    sweepAngle = 360f, //Hasta donde se dibuja
                    useCenter = true,
                    size = size, //Tamaño del canvas
                    alpha = 0.5f,
                    style = Stroke(
                        width = strokeWith.toPx(), // Ancho del stroke y conversion a pixeles
                        cap = StrokeCap.Round  // Tipo de linea
                    )
                )
                if (completedTasks <= totalTask) {
                    drawArc(
                        colorProgress,
                        startAngle = 90f, // Desde donde se dibuja
                        sweepAngle = (360f * angleRatio.value),
                        useCenter = false,
                        size = size, //Tamaño del canvas
                        style = Stroke(
                            width = strokeWith.toPx(), // Ancho del stroke y conversion a pixeles
                            cap = StrokeCap.Round  // Tipo de linea
                        )
                    )
                }

            }
            Text(
                "${((completedTasks.toFloat() / totalTask) * 100).toInt()}%",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SummaryInfoPreview() {
    AppTheme {
        SummaryInfo(
            modifier = Modifier,
            completedTasks = 5,
            totalTask = 10
        )
    }
}
@Preview(showBackground = true)
@Composable
fun SummaryInfoPreviewDark() {
    AppTheme {
        SummaryInfo(
            modifier = Modifier,
            completedTasks = 5,
            totalTask = 15
        )
    }
}