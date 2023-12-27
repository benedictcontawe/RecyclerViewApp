package com.example.recyclerviewapp

data class IconModel (
    public val icon : Int,
    private val name : String,
    private val detail : String,
) : CustomModel() {

    companion object {
        private val TAG = IconModel::class.java.getSimpleName()
    }

    override fun getName() : String {
        return name
    }

    override fun getDetail() : String {
        return detail
    }

    override fun equals(other : Any?) : Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as IconModel

        if (name != other.name) return false
        if (detail != other.detail) return false

        return true
    }

    override fun hashCode() : Int {
        return super.hashCode()
    }

    override fun toString() : String {
        return "$TAG($icon, $name, $detail)" ?: super.toString()
    }
}