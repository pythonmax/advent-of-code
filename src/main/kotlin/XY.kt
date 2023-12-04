import kotlin.math.abs

data class XY(val x: Int, val y: Int) {
    fun distanceTo(xy: XY) = abs(x - xy.x) + abs(y - xy.y)
//    fun distanceTo(xy: XY): Int {
//        val dx = abs(x - xy.x)
//        val dy = abs(y - xy.y)
//        return (dx + dy) + (-2) * minOf(dx, dy)
//    }
    fun moveBy(x: Int, y: Int) = XY(this.x + x, this.y + y)

    operator fun plus(xy: XY): XY = moveBy(xy.x, xy.y)
    operator fun minus(xy: XY): XY = moveBy(-xy.x, -xy.y)
}
