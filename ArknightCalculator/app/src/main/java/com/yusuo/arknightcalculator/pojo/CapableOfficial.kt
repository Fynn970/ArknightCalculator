package com.yusuo.zsshop.pojo


data class CapableOfficial(
    val hidden: Boolean,
    val level: Int,
    val name: String,
    val name_en: String,
    val sex: String,
    val tags: List<String>,
    val type: String
)