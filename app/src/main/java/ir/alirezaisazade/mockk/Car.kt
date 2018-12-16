package ir.alirezaisazade.mockk

class Car(private val driverId: String) {

    companion object {
        fun getModel(): String = "pride"
    }

    fun wheelsCount(): Int = 4

    fun startEngine(driverId: String): Boolean = this.driverId == driverId

    fun setSpeed(value: Int): Int = value

    fun go() {}

}