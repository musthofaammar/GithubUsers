package id.eureka.githubusers.core.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    fun isDateAfter(firstDateString: String, secondDateString: String): Boolean {
        val firstDate = stringToDate(firstDateString)
        val secondDate = stringToDate(secondDateString)

        if (firstDate != null && secondDate != null) {
            return firstDate.after(secondDate)
        }

        return false
    }

    private fun stringToDate(date: String): Date? {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())

        return simpleDateFormat.parse(date)
    }
}