package com.nak.userbase.presentation.compose

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun EmployeeImagesGrid() {

    var selectedImages by remember {
        mutableStateOf<List<Uri>>(emptyList())
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(
            maxItems = 12
        )
    ) { uris ->
        val remainingSlots = 12 - selectedImages.size
        val newImages = uris.take(remainingSlots)
        selectedImages = selectedImages + newImages
    }

    Spacer(modifier = Modifier.height(16.dp))

    if (selectedImages.isEmpty()) {

        // Empty State
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(12.dp))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No Images Here\n\nTap to Add",
                textAlign = TextAlign.Center,
                modifier = Modifier.clickable {
                    imagePickerLauncher.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                }
            )
        }

    } else {

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            items(selectedImages) { uri ->

                Box(
                    modifier = Modifier
                        .aspectRatio(1f),
                    contentAlignment = Alignment.TopEnd
                ) {

                    AsyncImage(
                        model = uri,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .matchParentSize()
                            .clip(RoundedCornerShape(8.dp))
                    )

                    Box(
                        modifier = Modifier
                            .offset(x = 8.dp, y = (-8).dp)
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(Color.Black)
                            .clickable {
                                selectedImages = selectedImages - uri
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "✕",
                            color = Color.White
                        )
                    }
                }
            }

            if (selectedImages.size < 12) {

                item {

                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .border(
                                1.dp,
                                Color.Gray,
                                RoundedCornerShape(8.dp)
                            )
                            .clickable {
                                imagePickerLauncher.launch(
                                    PickVisualMediaRequest(
                                        ActivityResultContracts.PickVisualMedia.ImageOnly
                                    )
                                )
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("+ Add")
                    }
                }
            }
        }
    }
}
