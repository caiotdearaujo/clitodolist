package help

data class ParamHelp(
    val param: String,
    val description: String
) {
    constructor(description: String): this("-", description)
}
