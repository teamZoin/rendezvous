package com.zoin.rendezvous.api.common

class PageByCursor<T>(
    val elements: List<T>,
    val hasNext: Boolean,
)
