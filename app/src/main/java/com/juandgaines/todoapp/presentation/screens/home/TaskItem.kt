package com.juandgaines.todoapp.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.juandgaines.todoapp.domain.Category
import com.juandgaines.todoapp.domain.Task

@Composable
fun TaskItem(
    task: Task,
    onClickItem: (String) -> Unit,
    onDeleteClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    onToggleCompleted: (Task) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickable {
                onClickItem(task.id.toString())
            }
            .background(
                color = MaterialTheme.colorScheme.surfaceContainer

            )
            .padding(horizontal = 8.dp),
    ) { //Componentes a nivel de fila
        Checkbox(
            checked = task.isCompleted,
            onCheckedChange = {
                onToggleCompleted(task)
            }
        )
        Column(
            modifier = Modifier.padding(8.dp).weight(1f),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(
                4.dp
            )
        ) {
            Text(
                task.title,
                style = MaterialTheme.typography.titleSmall.copy(
                    textDecoration = if (task.isCompleted) {
                        androidx.compose.ui.text.style.TextDecoration.LineThrough
                    } else {
                        androidx.compose.ui.text.style.TextDecoration.None
                    }
                ),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
            )
            if(!task.isCompleted){
                task.desc?.let{
                    Text(
                        it,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2
                    )
                }
                task.category?.let{
                    Text(
                        it.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

        }
        Box{
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete task",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 8.dp)
                    .clickable {
                        onDeleteClick(task.id.toString())
                    }
            )
        }
    }
}
@Composable
@Preview(showBackground = true)
fun TaskPreview(){
    MaterialTheme{
        TaskItem(
            task = Task(
                id = 1.toString(),
                title = "Task 1",
                desc = "Description 1",
                category = Category.PERSONAL,
                isCompleted = false,
            ),
            onClickItem = {},
            onDeleteClick = {},
            onToggleCompleted = {}
        )
    }
}