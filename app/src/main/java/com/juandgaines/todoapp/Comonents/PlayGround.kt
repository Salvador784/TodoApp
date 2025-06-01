package com.juandgaines.todoapp.Comonents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.juandgaines.todoapp.ui.theme.AppTheme

@Composable
fun HelloWord(modifier: Modifier = Modifier) {
    Text(
        text = "Hello Word",
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.headlineLarge,
        maxLines = 1,
        fontWeight = FontWeight.Bold,

        )
}

@Preview(
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES or android.content.res.Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun HelloWordPreview() {
    AppTheme {
        HelloWord()
    }
}

@Composable
fun ClickableText(modifier: Modifier = Modifier) {
    var txt by remember { mutableStateOf("Texto original") }
    Text(
        text = txt,
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .clickable {
                txt = "Texto cambiado"
            },
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.headlineLarge,
        maxLines = 1,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.SansSerif
    )
}

@Preview(
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES or android.content.res.Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun ClcikableTextPreview() {
    AppTheme {
        ClickableText()
    }
}

@Composable
fun IconExample(modifier: Modifier = Modifier, iconContainer: IconContainer) {
    Icon(
        imageVector = iconContainer.icon,
        contentDescription = "Favorite icon",
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .size(48.dp)
            .clickable {
                // Acción al hacer clic en el ícono
            }
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.small
            )
    )
}

@Preview(
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES or android.content.res.Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun IconExamplePreview(
    @PreviewParameter(IconProvider::class) icon :IconContainer
) {
    AppTheme {
        IconExample(
            iconContainer = icon,
        )
    }
}

@Composable
fun RowView(modifier: Modifier = Modifier){
    Row (
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        IconExample(iconContainer = IconContainer(Icons.Default.Favorite) )
        //Spacer(Modifier.size(9.dp)) Espaciado entre elementos
        HelloWord()
//        Spacer(Modifier.size(9.dp))
//        ClickableText()
    }
}

@Preview
@Composable
fun RowViewPreview(){
    AppTheme {
        RowView()
    }
}

@Composable
fun ColumnView(modifier: Modifier = Modifier){
    Column (
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        HelloWord()
        ClickableText()
    }
}

@Preview
@Composable
fun ColumnViewPreview(){
    AppTheme {
        ColumnView()
    }
}