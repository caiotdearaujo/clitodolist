import java.text.SimpleDateFormat

object DateFormatter: SimpleDateFormat("HH:mm yyyy-MM-dd") {
    private fun readResolve(): Any = DateFormatter
}