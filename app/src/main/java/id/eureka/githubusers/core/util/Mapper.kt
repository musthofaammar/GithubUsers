package id.eureka.githubusers.core.util

interface Mapper<in I, out O> {
    fun map(input : I) : O
}