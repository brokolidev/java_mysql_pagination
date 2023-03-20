package com.example.mysql

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class MysqlApplication

fun main(args: Array<String>) {
    runApplication<MysqlApplication>(*args)
}

@Entity
data class Produce(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val name: String,
    val rating: Long
)

data class ProduceResponse(
    val items: Iterable<Produce>,
    val hasNext: Boolean
)

interface ProduceRepository : PagingAndSortingRepository<Produce, Long>

@RestController
class ProduceController(
    val produceRepository: ProduceRepository
) {

    @GetMapping("/top-produce")
    fun topProduce(
        @RequestParam(defaultValue = "1") page: Int,
        @RequestParam(defaultValue = "5") size: Int): ProduceResponse =
        produceRepository.findAll(
            PageRequest.of(
                page - 1,
                size,
                Sort.by("rating").descending().and(Sort.by("id"))
            )
        ).let {
            ProduceResponse(
                items = it.toList(),
                hasNext = it.hasNext()
            )
        }

}