package com.paysera.lib.savings.entities

import com.google.gson.annotations.SerializedName

class MetadataAwareResponse <T> (
    val items: List<T>,
    @SerializedName("_metadata")
    val metadata: Metadata
)