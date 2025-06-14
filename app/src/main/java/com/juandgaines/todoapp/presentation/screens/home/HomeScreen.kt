@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class
)

package com.juandgaines.todoapp.presentation.screens.home

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.juandgaines.todoapp.R
import com.juandgaines.todoapp.presentation.screens.home.providers.HomeScreenProvider
import com.juandgaines.todoapp.ui.theme.AppTheme

@Composable
fun HomeScreenRoot(
    navigateToTaskScren: (
        String?,
    ) -> Unit,
    viewModel: HomeScreenViewModel
) {
    val state = viewModel.state
    val event = viewModel.events

    val context = LocalContext.current

    LaunchedEffect(true) {
        event.collect { e ->
            when (e) {
                HomeScreenEvent.TaskAllDeleted -> Toast.makeText(
                    context,
                    context.getString(R.string.all_task_deleted),
                    Toast.LENGTH_SHORT
                ).show()

                HomeScreenEvent.TaskDeleted -> Toast.makeText(
                    context,
                    context.getString(R.string.task_deleted),
                    Toast.LENGTH_SHORT
                ).show()

                HomeScreenEvent.UpdatedTask -> Toast.makeText(
                    context,
                    context.getString(R.string.task_updated),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    HomeScreen(
        state = state,
        onAction = { action ->
            when (action) {
                HomeScreenAction.OnAddTask -> {
                    navigateToTaskScren(null)
                }
                is HomeScreenAction.OnClickTask ->{
                    navigateToTaskScren(action.taskId)
                }

                else -> {
                    viewModel.onAction(action)
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeDataState,
    onAction: (HomeScreenAction) -> Unit,
    navigateToEditScreen: (String) -> Unit = {}
) {
    var isExpanded by remember { mutableStateOf(false) }
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                isExpanded = true
                            }
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Add Task",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                        DropdownMenu(
                            expanded = isExpanded,
                            modifier = Modifier.background(
                                color = MaterialTheme.colorScheme.surfaceContainerHighest
                            ),
                            onDismissRequest = { isExpanded = false }
                        ) {
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        stringResource(R.string.delete_all),
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                },
                                onClick = {
                                    isExpanded = false
                                    onAction(HomeScreenAction.OnDeleteAllTasks)
                                }
                            )
                        }
                    }
                }
            )
        },
        content = { paddingValues ->

            if (state.pendingTasks.isEmpty() && state.completedTasks.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ){
                    Text(stringResource(R.string.no_tasks),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)

            ) {
                item {
                    SummaryInfo(
                        date = state.date.toString(),
                        taskSummary = state.summary.toString(),
                        completedTasks = state.completedTasks.size,
                        totalTask = state.pendingTasks.size + state.completedTasks.size
                    )
                }
                // tareas Pendientes
                stickyHeader {
                    SectionTitle(
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .background(
                                color = MaterialTheme.colorScheme.surface
                            ),
                        title = stringResource(R.string.pending_tasks)
                    )
                }
                items(
                    state.pendingTasks,
                    key = { task -> task.id }
                ) { task ->
                    TaskItem(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .animateItem(),
                        onToggleCompleted = {
                            onAction(HomeScreenAction.OnToggleTask(it))
                        },
                        onDeleteClick = {
                            onAction(HomeScreenAction.OnDeleteTask(task))
                        },
                        onClickItem = {
                            onAction(HomeScreenAction.OnClickTask(task.id))
                        },
                        task = task
                    )
                }


                // tareas Completadas
                stickyHeader {
                    SectionTitle(
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .background(
                                color = MaterialTheme.colorScheme.surface
                            ),
                        title = stringResource(R.string.completed_tasks)
                    )
                }
                items(
                    state.completedTasks,
                    key = { task -> task.id }
                ) { task ->
                    TaskItem(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .animateItem(),
                        onToggleCompleted = {
                            onAction(HomeScreenAction.OnToggleTask(it))
                        },
                        onDeleteClick = {
                            onAction(HomeScreenAction.OnDeleteTask(task))
                        },
                        onClickItem = {
                            onAction(HomeScreenAction.OnClickTask(task.id))
                        },
                        task = task
                    )
                }

            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAction(HomeScreenAction.OnAddTask)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    )
}


@Preview
@Composable
fun HomeScreenPreviewLight(
    @PreviewParameter(HomeScreenProvider::class) state: HomeDataState,
) {
    AppTheme {
        HomeScreen(
            state = state,
            onAction = {}
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun HomeScreenPreviewDark(
    @PreviewParameter(HomeScreenProvider::class) state: HomeDataState,
) {
    AppTheme {
        HomeScreen(
            state = state,
            onAction = {}
        )
    }
}