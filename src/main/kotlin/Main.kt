import java.io.BufferedReader
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation

fun main(args: Array<String>) {
    print("Enter day number:\n")
    val day = readln().toInt()

    val className = "days.Day$day"
    val kClass: KClass<*> = try {
        Class.forName(className).kotlin
    } catch (e: ClassNotFoundException) {
        println("Invalid class found for $className")
        return
    }

    val instance = kClass.constructors
        .firstOrNull { it.parameters.isEmpty() }
        ?.call()
        ?: run {
            println("No empty constructor found for $className")
            return
        }

    val partFunctions: List<KFunction<*>> =
        kClass.members
            .filterIsInstance<KFunction<*>>()
            .filter { it.hasAnnotation<Part>() }
            .sortedBy { it.name }


    if (partFunctions.isEmpty()) {
        println("No @Part methods found in $className")
        return
    }

    println("Select method to run:\n")
    partFunctions.forEachIndexed { index, fn ->
        val ann = fn.findAnnotation<Part>()
        val displayName = when {
            ann != null && ann.name.isNotBlank() -> ann.name
            else -> fn.name
        }
        println("${index + 1}) $displayName")
    }

    println("\nEnter Part Number:\n")
    val choice = readln().toIntOrNull()

    if (choice == null || choice !in 1..partFunctions.size) {
        println("Invalid Part Number")
        return
    }

    val functionToCall = partFunctions[choice - 1]

    functionToCall.call(instance, getPuzzleInputFile(day))
}


fun getPuzzleInputFile(day: Int): BufferedReader {
    val stream = object {}.javaClass.getResourceAsStream("/day$day.txt") ?: error("No input file found for day $day")
    return stream.bufferedReader()
}