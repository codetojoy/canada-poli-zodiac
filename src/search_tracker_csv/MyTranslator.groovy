
// ------ main

def file = new File(args[0])

println "OK"

file.eachLine { line ->
    def tokens = line.split(",")
    def first = tokens[1]
    def last = tokens[2]
    println "$first $last,"
}
