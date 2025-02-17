/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0
 */

package aws.smithy.kotlin.runtime.util

import aws.smithy.kotlin.runtime.InternalApi

/**
 * Convenience type for treating a list like stack
 */
@InternalApi
public typealias ListStack<T> = MutableList<T>

/**
 * Push an item to top of stack
 */
@InternalApi
public fun <T> ListStack<T>.push(item: T): Boolean = add(item)

/**
 * Pop the top of the stack or throw [NoSuchElementException]
 */
@InternalApi
public fun <T> ListStack<T>.pop(): T = removeLast()

/**
 * Pop the top of the stack or return null if stack is empty
 */
@InternalApi
public fun <T> ListStack<T>.popOrNull(): T? = removeLastOrNull()

/**
 * Return top of stack or throws exception if stack is empty
 */
@InternalApi
public fun <T> ListStack<T>.top(): T = this[count() - 1]

/**
 * Return top of stack or null if stack is empty
 */
@InternalApi
public fun <T> ListStack<T>.topOrNull(): T? = if (isNotEmpty()) top() else null

/**
 * Pop the top of the stack and push a [item]
 */
@InternalApi
public fun <T> ListStack<T>.replaceTop(item: T): T? {
    val lastTop = popOrNull()
    push(item)
    return lastTop
}
