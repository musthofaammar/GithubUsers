package id.eureka.githubusers.core.util

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object DateUtil {
    fun isDateAfter(firstDateString: String, secondDateString: String): Boolean {
        val firstDate = stringToDate(firstDateString)
        val secondDate = stringToDate(secondDateString)

        if (firstDate != null && secondDate != null) {
            return firstDate.after(secondDate)
        }

        return false
    }

    fun formatDate(dateString: String): String {
        val date = stringToDate(dateString)

        if (date != null) {
            val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val dateNow = Calendar.getInstance().timeInMillis

            val difference = dateNow - date.time

            val hoursDifference = TimeUnit.MILLISECONDS.toHours(difference)
            val daysDifference = TimeUnit.MILLISECONDS.toDays(difference)

            return when {
                daysDifference < 1 -> "Updated $hoursDifference ago"
                daysDifference < 7 -> "Updated $daysDifference ago"
                else -> "Updated ${simpleDateFormat.format(date)}"
            }
        }

        return "Wrong Time"
    }

    private fun stringToDate(date: String): Date? {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())

        return simpleDateFormat.parse(date)
    }
}