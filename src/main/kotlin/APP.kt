import isel.leic.utils.Time
import java.io.File


object APP {
    /* waiting key */
    fun app() {
        LCD.clear()
        var uin = "0"
        val list = File("USERS.txt").readLines()
        do {
            LCD.clear()
            //Escreve no LCD o dia, horas  e pergunta o UIN
            LCD.write(TUI.getLocalDateTime() + "UIN:???")
            LCD.cursor(1, 4)
            Time.sleep(100)
            //Escreve  a tecla presionada e guarda o uin
            uin = TUI.writeAndSaveUin()
            //Guarda o pin do user
            val pinUsersFile = list[uin.toInt()].split(";")[3]
            // Pergunta o Pin
            Time.sleep(100)
            LCD.cursor(1, 0)
            LCD.write("PIN:????")
            LCD.cursor(1, 4)
            //Escreve  a tecla presionada e guarda o Pin
            val pinWritten = TUI.writeAndSavePin()
            Time.sleep(100)
            //Se o Pin corresponder ao Pin do Uin a porta sera aberta
        } while (pinWritten.toInt() != pinUsersFile.toInt())
        val name = list[uin.toInt()].split(";")[2]
        //Escreve hello e o nome do Uin
        LCD.clear()
        TUI.centerStringAndWrite("Hello", 0)
        TUI.centerStringAndWrite(name, 1)
        Time.sleep(1500)
        //Verifica se é uma entrada ou é uma saida e escreve o devido texto no LCD
        LCD.clear() // esta sempre a dar as horas do ultimo registo
        if(Log.entering(uin) == "->") {
            TUI.centerStringAndWrite("Entrance  " + TUI.getTime(), 0)
            TUI.centerStringAndWrite("Exit      ??:??", 1)
        }
        else{
            TUI.centerStringAndWrite("Entrance  " + Log.lastRegisterTime(uin), 0)
            TUI.centerStringAndWrite("Exit      " + TUI.getTime(), 1)
        }
        //Guarda o acontecimento no LOG.text
        Log.writeLog(uin.toInt(), name)
        //Abertura da porta
        Time.sleep(3000)
        LCD.clear()
        TUI.centerStringAndWrite(name, 0)
        TUI.centerStringAndWrite("Door is Opening", 1)
        Door.open(3)
        //Fecho da porta
        Time.sleep(2000)
        TUI.centerStringAndWrite("Door is Closing", 1)
        Door.close(1)
    }
}