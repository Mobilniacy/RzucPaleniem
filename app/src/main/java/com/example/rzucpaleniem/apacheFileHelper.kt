import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.example.rzucpaleniem.R
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVPrinter
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.FileWriter
import java.io.InputStreamReader

class apacheFileHelper {

    fun findUserByUsernameAndPassword(context: Context, desiredUsername: String, desiredPassword: String): Int {

        //Returns:  0 - nie znaleziono w ogóle
        //          1 - błędne hasło
        //          2 - dane się zgadzają

        val inputStream = context.resources.openRawResource(R.raw.users)
        val reader = BufferedReader(InputStreamReader(inputStream))
        val csvParser = CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())

        Log.d(TAG, "findUserByUsernameAndPassword: przedpetla")

        for (csvRecord in csvParser) {
            val username = csvRecord.get("username")
            if (username == desiredUsername) {
                // Znaleziono użytkownika o podanym loginie
                val password = csvRecord.get("password")
                Log.d(TAG, "csvpass: $password")
                Log.d(TAG, "desiredPass: $desiredPassword")

                if (password == desiredPassword) {
                    //Znaleziono użytkownika o podanym loginie oraz haśle
                    return 2
                }
                return 1
            }
            Log.d(TAG, "findUserByUsernameAndPassword: po if")
        }
        Log.d(TAG, "findUserByUsernameAndPassword: koniec")
        return 0
    }

    fun addEntryToCSV(context: Context, username: String, password: String, email: String) {
        val file = FileWriter("raw/users.csv", true) // Ścieżka do Twojego pliku CSV
        val bufferedWriter = BufferedWriter(file)
        val csvPrinter = CSVPrinter(bufferedWriter, CSVFormat.DEFAULT)

        // Dodaj nowy wpis do pliku CSV
        csvPrinter.printRecord(username, password, email)

        // Zamykanie strumieni
        csvPrinter.close()
        bufferedWriter.close()
        file.close()
    }
}