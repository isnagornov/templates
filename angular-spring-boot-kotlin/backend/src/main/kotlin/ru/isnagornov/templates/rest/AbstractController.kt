package ru.isnagornov.templates.rest

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.isnagornov.templates.mapper.CommonMapper
import ru.isnagornov.templates.service.AbstractService

abstract class AbstractController<T, M : CommonMapper<T>, S : AbstractService<T, M>>(protected var service: S) {

    val all: List<T>
        @GetMapping("/getAll")
        get() = this.service.all

    @PostMapping("/add")
    fun add(@RequestBody entity: T): ResponseEntity<HttpStatus> {
        this.service.add(entity)

        return ResponseEntity(HttpStatus.OK)
    }

    @GetMapping(path = ["/{id}"])
    fun getById(@PathVariable("id") id: Long?): T {
        return this.service.getById(id)
    }


    @PutMapping(path = ["/update"])
    fun update(@RequestBody entity: T): ResponseEntity<HttpStatus> {
        this.service.update(entity)

        return ResponseEntity(HttpStatus.OK)
    }

    @DeleteMapping(path = ["/{id}"])
    fun delete(@PathVariable("id") id: Long?): ResponseEntity<HttpStatus> {
        this.service.delete(id)

        return ResponseEntity(HttpStatus.OK)
    }

}
