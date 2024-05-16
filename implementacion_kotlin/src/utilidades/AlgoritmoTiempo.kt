package utilidades

class AlgoritmoTiempo(val nombre: String, val tiempo: Double) : Comparable<AlgoritmoTiempo> {
    override fun compareTo(otro: AlgoritmoTiempo): Int {
        return java.lang.Double.compare(otro.tiempo, this.tiempo)
    }
}