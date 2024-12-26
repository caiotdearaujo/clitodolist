package errors

class MissingArgumentError(argumentName: String): Exception("Argument of name $argumentName is missing.")