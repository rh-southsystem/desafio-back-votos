package br.com.southsystem.desafiobackvotos.usecase.commons

interface Usecase<S,R> {
    fun execute(source: S): R
}