package y2022

import resourceAsFile

class Node constructor(name: String, val parent: Node?, size: Int = 0) {
    val path: String = parent?.path?.let { "$it/$name" } ?: name
    val children: ArrayList<Node> by lazy { ArrayList() }
    val dir: Boolean = size < 0
    var size: Int = 0

    init {
        this.parent?.children?.add(this)
        addSize(size)
    }

    private fun addSize(size: Int) {
        if (size > 0) {
            this.size += size
            this.parent?.addSize(size)
        }
    }
}

fun main() {
    val root = Node("", null, -1)
    var node: Node = root
    resourceAsFile("y2022/day7-input.txt").forEachLine { line ->
        when {
            line.startsWith("$ cd ") -> line.substring(5).let {
                node = when (it) {
                    "/" -> root
                    ".." -> node.parent!!
                    else -> Node(it, node, -1)
                }
            }
            line.matches("\\d+\\s+.*".toRegex()) -> line.split(" ").let { (size, name) -> node.apply { Node(name, node, size.toInt()) } }
        }
    }

    fun printTree(node: Node) {
        println("${node.path} -> ${node.size}")
        node.children.forEach { printTree(it) }
    }

//    fun walkTree(node: Node): Int {
//        return (if (node.dir && node.size <= 100000) node.size else 0) + node.children.map { walkTree(it) }.sum()
//    }
//    println(walkTree(root))

    val total = 70000000
    val requiredFreeSpace = 30000000
    val needToFree = requiredFreeSpace - (total - root.size)

    fun findDirs(node: Node): List<Int> {
        return (if (node.dir && node.size >= needToFree) listOf(node.size) else emptyList()) + node.children.map { findDirs(it) }.flatten()
    }

    println(findDirs(root).minOrNull())
}