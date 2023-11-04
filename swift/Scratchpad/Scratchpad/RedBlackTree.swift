//
//  RedBlackTree.swift
//  Scratchpad
//
//  https://en.wikipedia.org/wiki/Red%E2%80%93black_tree
//
//  In addition to the requirements imposed on a binary search tree the following must be satisfied by a red–black tree:
//  1. Every node is either red or black.
//  2. All NIL nodes (figure 1) are considered black.
//  3. A red node does not have a red child.
//  4. Every path from a given node to any of its descendant NIL nodes goes through the same number of black nodes.
//  5. (Conclusion) If a node N has exactly one child, it must be a red child, because if it were black, its NIL descendants would sit at a different black depth than N's NIL child, violating requirement 4.
//

import Foundation

enum Traversal {
    case inorder, preorder, postorder
}

private enum NodeColor {
    case red, black
}
private enum Direction {
    case left, right
    
    func opposite() -> Direction {
        return self == .left ? .right : .left
    }
}

class Node<T: Comparable>: CustomStringConvertible {
    var left: Node?
    var right: Node?
    var parent: Node?
    fileprivate var color: NodeColor = .black
    var key: T
    
    var description: String {
        return "Node(key=\(String(describing: key)), color=\(color), left=\(String(describing: left?.key)), right=\(String(describing: right?.key)), parent=\(String(describing: parent?.key)))"
    }

    init(left: Node?, right: Node?, parent: Node?, key: T) {
        self.left = left
        self.right = right
        self.parent = parent
        self.key = key
    }
}

struct RedBlackTree<T: Comparable> {
    var root: Node<T>?
    
    init() {
        self.root = nil
    }
    
    mutating func insert(_ key: T) {
        var newNode = Node<T>(left: nil, right: nil, parent: nil, key: key)
        newNode.color = .red
        
        var parent: Node<T>? = nil
        var node: Node<T>? = root
        
        while node != nil {
            parent = node
            node = newNode.key < node!.key ? node!.left : node!.right
        }
        
        newNode.parent = parent
        
        if parent == nil {
            root = newNode
        } else if newNode.key < parent!.key {
            parent!.left = newNode
        } else {
            parent!.right = newNode
        }
        
        // new node is root
        if newNode.parent == nil {
            newNode.color = .black
            return
        }
        
        if newNode.parent!.parent == nil {
            return
        }
        
        rebalance(&newNode)
    }
    
    private mutating func rebalance(_ node: inout Node<T>) {
        while node.parent?.color == .red {
            if node.parent === node.parent?.parent?.right {
                let uncle: Node<T>? = node.parent?.parent?.left
                if uncle?.color == .red {
                    uncle!.color = .black
                    node.parent!.color = .black
                    node.parent!.parent!.color = .red
                    node = node.parent!.parent!
                } else {
                    if node === node.parent?.left {
                        node = node.parent!
                        rotate(node, direction: .right)
                    }
                    node.parent!.color = .black
                    node.parent!.parent!.color = .red
                    rotate(node, direction: .left)
                }
            } else {
                let uncle: Node<T>? = node.parent?.parent?.right
                if uncle?.color == .red {
                    uncle!.color = .black
                    node.parent!.color = .black
                    node.parent!.parent!.color = .red
                    node = node.parent!.parent!
                } else {
                    if node === node.parent?.right {
                        node = node.parent!
                        rotate(node, direction: .left)
                    }
                    node.parent!.color = .black
                    node.parent!.parent!.color = .red
                    rotate(node, direction: .right)
                }
            }
            if node === root {
                break
            }
        }
        root!.color = .black
    }
    
    private mutating func rotate(_ node: Node<T>, direction: Direction) {
        switch direction {
        case .left:
            let other: Node<T>? = node.right
            node.right = other?.left
            if other?.left != nil {
                other?.left?.parent = node
            }
            other?.parent = node.parent
            if node.parent == nil {
                root = other
            } else if node === node.parent?.left {
                node.parent?.left = other
            } else {
                node.parent?.right = other
            }
            other?.left = node
            node.parent = other
        case .right:
            let other: Node<T>? = node.left
            node.left = other?.right
            if other?.right != nil {
                other?.right?.parent = node
            }
            other?.parent = node.parent
            if node.parent == nil {
                root = other
            } else if node === node.parent?.right {
                node.parent?.right = other
            } else {
                node.parent?.left = other
            }
            other?.right = node
            node.parent = other
        }
    }

    func printNodes() {
        traverse(root, order: .inorder, handler: {(node) in print("\(String(describing: node!))")})
    }

    func traverse(_ node: Node<T>?, order: Traversal, handler: (Node<T>?) -> Void) {
        if node == nil {
            return
        }
        switch order {
        case .inorder:
            traverse(node?.left, order: order, handler: handler)
            handler(node)
            traverse(node?.right, order: order, handler: handler)
        case .postorder:
            traverse(node?.left, order: order, handler: handler)
            traverse(node?.right, order: order, handler: handler)
            handler(node)
        case .preorder:
            handler(node)
            traverse(node?.left, order: order, handler: handler)
            traverse(node?.right, order: order, handler: handler)
        }
    }
}
