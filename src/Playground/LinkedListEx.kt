package Playground

// Linked List Example.

class Node<Item>() {
    var item: Item? = null
    var next: Node<Item>? = null

}

fun main(args: Array<String>) {
    var first: Node<String>? = Node()
    val second: Node<String> = Node()
    var last: Node<String> = Node()

    first?.item = "to"
    second.item = "be"
    last.item = "or"

    // Create Linked List structure.
    first?.next = second
    second.next = last
    // field "next" in Node "third" remains null

    //first = first?.next   // Remove Node from the beginning.
    /*
    // Insert New Node at beginning.
    var oldFirst: Node<String>? = first
    first = Node()
    first.item = "not"
    first.next = oldFirst
     */

    var oldLast: Node<String> = last
    last = Node()
    last.item = "not"
    oldLast.next = last
    //val testList: List<String> = linkedListToSequence(first)
    //println(testList.toString())


}

fun linkedListToSequence(nodeArg: Node<String>?): List<String> {
    var node: Node<String>? = nodeArg
    val sb: StringBuilder = StringBuilder()
    var itemCount: Int = 0
    while (true) {
        itemCount++
        println("Node Called: ${node?.item}")
        if (node?.next == null) {
            sb.append(node?.item)
            break
        }
        sb.append(node.item).append(" ")
        node = node.next
    }

    return sb.toString().split(" ")
}