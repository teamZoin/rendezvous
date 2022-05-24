package com.zoin.rendezvous.domain

class PageByCursor<T>(
    val elements: List<T>,
    val hasNext: Boolean,
)
