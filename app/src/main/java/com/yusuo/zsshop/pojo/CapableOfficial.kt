package com.yusuo.zsshop.pojo

class CapableOfficial {
    constructor(
        name: String,
        type: String,
        level: Int,
        sex: String,
        tags: List<String>,
        hidden: Boolean,
        nameEn: String
    ) {
        this.name = name
        this.type = type
        this.level = level
        this.sex = sex
        this.tags = tags
        this.hidden = hidden
        this.nameEn = nameEn
    }

    var name:String = ""
    set(value) {field = value}
    get() = field

    var type:String = ""
        set(value) {field = value}
        get() = field

    var level:Int = 0
        set(value) {field = value}
        get() = field

    var sex:String = ""
        set(value) {field = value}
        get() = field

    var tags:List<String> = ArrayList()
        set(value) {field = value}
        get() = field

    var hidden:Boolean = false
        set(value) {field = value}
        get() = field

    var nameEn:String = ""
        set(value) {field = value}
        get() = field

}