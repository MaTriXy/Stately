/*
 * Copyright (C) 2018 Touchlab, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package co.touchlab.stately.concurrency

import co.touchlab.stately.freeze
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class Atomics{
    @Test
    fun atomicBoolean(){
        val ab = AtomicBoolean(false)
        assertFalse(ab.value)
        ab.value = true
        assertTrue(ab.value)

        assertFalse(ab.compareAndSet(false, false))
        assertTrue(ab.value)
        assertTrue(ab.compareAndSet(true, false))
        assertFalse(ab.value)
    }

    @Test
    fun atomicReference(){
        val ref = AtomicReference(ATData("asdf"))
        assertEquals(ref.value, ATData("asdf"))
        val actualData = ATData("qwert")
        ref.value = actualData
        assertEquals(ref.value, ATData("qwert"))
        assertFalse(ref.compareAndSet(ATData("rrr"), ATData("ttt")))
        assertEquals(ref.value, ATData("qwert"))
        assertFalse(ref.compareAndSet(ATData("qwert"), ATData("ttt")))
        assertTrue(ref.compareAndSet(actualData, ATData("ttt")))
        assertEquals(ref.value, ATData("ttt"))
    }

    @Test
    fun atomicInt(){
        val ref = AtomicInt(22)
        assertEquals(22, ref.value)

        ref.increment()

        assertEquals(23, ref.value)

        ref.increment()
        ref.increment()

        assertEquals(25, ref.value)

        ref.decrement()

        assertEquals(24, ref.value)

        assertEquals(29, ref.addAndGet(5))

        assertEquals(29, ref.value)

        assertFalse(ref.compareAndSet(28, 33))

        assertEquals(29, ref.value)

        assertTrue(ref.compareAndSet(29, 33))

        assertEquals(33, ref.value)
    }


    @Test
    fun atomicLong(){
        val ref = AtomicLong(22)
        assertEquals(22, ref.value)

        ref.increment()

        assertEquals(23, ref.value)

        ref.increment()
        ref.increment()

        assertEquals(25, ref.value)

        ref.decrement()

        assertEquals(24, ref.value)

        assertEquals(29, ref.addAndGet(5))

        assertEquals(29, ref.value)

        assertFalse(ref.compareAndSet(28, 33))

        assertEquals(29, ref.value)

        assertTrue(ref.compareAndSet(29, 33))

        assertEquals(33, ref.value)
    }
}

data class ATData(val s:String){
    init {
        freeze()
    }
}