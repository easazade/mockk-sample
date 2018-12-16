package ir.alirezaisazade.mockk

import kotlin.random.Random

object SimpleSingle {

    fun randomNumberBelow1000():Int = Random(1000).nextInt()

}