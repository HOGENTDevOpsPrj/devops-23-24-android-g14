package com.hogent.svkapp.presentation.ui.cargoTicketScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hogent.svkapp.domain.entities.Image
import com.hogent.svkapp.presentation.ui.theme.TemplateApplicationTheme

/**
 * A card that displays the route numbers, license plate and images of a cargo ticket.
 */
@Composable
fun CargoTicketCard(
    modifier: Modifier = Modifier,
    routeNumbers: List<String>,
    licensePlate: String,
    images: List<Image>,
) {
    val bullet = "\u2022"
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column {
                Text(text = "Routenummers", fontWeight = FontWeight.Bold)
                val paragraphStyle = ParagraphStyle(textIndent = TextIndent(restLine = 12.sp))
                Text(
                    buildAnnotatedString {
                        routeNumbers.forEach {
                            withStyle(style = paragraphStyle) {
                                append(bullet)
                                append("\t\t")
                                append(it)
                            }
                        }
                    }
                )
            }
            Column {
                Text(text = "Nummerplaat", fontWeight = FontWeight.Bold)
                Text(text = licensePlate)
            }
        }
        ScrollableImageList(images = images)
        Divider(
            modifier = Modifier
                .padding(top = 8.dp, bottom = 2.dp),
            color = Color.Black,
            thickness = 1.dp
        )
    }

}

/**
 * A preview of the [CargoTicketCard].
 */
@Preview(showBackground = true)
@Composable
fun CargoTicketPreview() {
    TemplateApplicationTheme {
        CargoTicketCard(
            routeNumbers = listOf("1234", "5678"),
            licensePlate = "1-ABC-123",
            images = emptyList()
        )
    }
}