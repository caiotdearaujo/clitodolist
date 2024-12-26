package errors

class ArgumentFormatException(argument: String, type: String): Exception(
    "Argument of name $argument must be of type $type"
)