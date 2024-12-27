package help

data class CommandHelp(
    val command: String,
    val paramsHelp: List<ParamHelp>
) {
    private val commandLength = this.command.length
    private val largestParamLength = this.paramsHelp.maxOfOrNull { it.param.length } ?: 0

    /*
     * Output example:
     *
     * command param1 description1
     *         param2 description2
     *         param3 description3
     */
    fun format(
        commandWidth: Int = commandLength,
        paramWidth: Int = largestParamLength
    ): String {
        val output = StringBuilder("${command.padEnd(commandWidth)} ")
        
        paramsHelp.forEachIndexed { index, paramHelp ->
            if (index != 0) output.append("".padEnd(commandWidth + 1))
            output.append(paramHelp.param.padEnd(paramWidth + 1))
            output.append(paramHelp.description)
            output.appendLine()
        }

        return output.toString()
    }
}
